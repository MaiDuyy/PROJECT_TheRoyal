package dao.impl;

import dao.KhachHangDAO;
import entity.KhachHang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.util.List;

public class KhachHangDAOImpl extends GenericDAOImpl<KhachHang, String> implements KhachHangDAO {

    public KhachHangDAOImpl(Class<KhachHang> clazz) {
        super(clazz);
    }

    public KhachHangDAOImpl(EntityManager em, Class<KhachHang> clazz) {
        super(em, clazz);
    }


    // Lấy Khách Hàng theo tên
    @Override public List<KhachHang> getKhachHangTheoTen(String ten) {
        TypedQuery<KhachHang> query = JPAUtil.getEntityManager().createQuery("SELECT k FROM KhachHang k WHERE k.tenKH LIKE :ten", KhachHang.class);
        query.setParameter("ten", "%" + ten + "%");
        return query.getResultList();
    }

    // Lấy Khách Hàng theo loại
    @Override public List<KhachHang> getKhachHangTheoLoai(String loaiKH) {
        TypedQuery<KhachHang> query = JPAUtil.getEntityManager().createQuery("SELECT k FROM KhachHang k WHERE k.loaiKH = :loaiKH", KhachHang.class);
        query.setParameter("loaiKH", loaiKH);
        return query.getResultList();
    }

    // Lấy Khách Hàng theo số điện thoại hoặc CCCD
    @Override public KhachHang getKhachHangTheoSDTHoacCCCD(String sdtOrCCCD) {
        TypedQuery<KhachHang> query = JPAUtil.getEntityManager().createQuery("SELECT k FROM KhachHang k WHERE k.sDT = :sdtOrCCCD OR k.CCCD = :sdtOrCCCD", KhachHang.class);
        query.setParameter("sdtOrCCCD", sdtOrCCCD);
        return query.getResultStream().findFirst().orElse(null);
    }

    // Lấy Khách Hàng theo mã
    @Override public KhachHang getKhachHangTheoMa(String maKH) {
        return JPAUtil.getEntityManager().find(KhachHang.class, maKH);
    }





    // Lấy mã Khách Hàng mới nhất (thực hiện theo cách tự sinh mã)
    @Override public String getLatestID() {
        TypedQuery<String> query = JPAUtil.getEntityManager().createQuery("SELECT k.maKH FROM KhachHang k ORDER BY k.maKH DESC", String.class);
        List<String> result = query.getResultList();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return "KH00";
    }

    @Override
    public boolean insert(KhachHang kh) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;

        try {
            tx.begin();

            // Bước 1: Lấy mã khách hàng lớn nhất hiện tại
            String jpql = "SELECT k.maKH FROM KhachHang k ORDER BY k.maKH DESC";
            String latestMaKH = em.createQuery(jpql, String.class)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            // Bước 2: Tạo mã khách hàng mới
            int nextNumber = 1;
            if (latestMaKH != null && latestMaKH.length() >= 4) {
                String numberPart = latestMaKH.substring(2); // Bỏ "KH"
                try {
                    nextNumber = Integer.parseInt(numberPart) + 1;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            String newMaKH = String.format("KH%02d", nextNumber);
            kh.setMaKH(newMaKH);

            // Bước 3: Persist đối tượng
            em.persist(kh);

            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return success;
    }
}
