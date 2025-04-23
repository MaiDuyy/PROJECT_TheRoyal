package dao.impl;

import dao.KhuyenMaiDAO;
import entity.KhuyenMai;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityTransaction;
import util.JPAUtil;

import java.util.List;

public class KhuyenMaiDAOImpl extends GenericDAOImpl<KhuyenMai, String> implements KhuyenMaiDAO {


    public KhuyenMaiDAOImpl(Class<KhuyenMai> clazz) {
        super(clazz);
    }

    public KhuyenMaiDAOImpl(EntityManager em, Class<KhuyenMai> clazz) {
        super(em, clazz);
    }

    // Lấy Khuyến Mãi theo mã
    @Override public KhuyenMai getKhuyenMaiTheoMa(String maUuDai) {
        TypedQuery<KhuyenMai> query = JPAUtil.getEntityManager().createQuery("SELECT k FROM KhuyenMai k WHERE k.maKM = :maKM", KhuyenMai.class);
        query.setParameter("maKM", maUuDai);
        return query.getResultStream().findFirst().orElse(null);
    }

    // Lấy danh sách Khuyến Mãi sau ngày cụ thể
    @Override public List<KhuyenMai> getKhuyenMaiSauNgay(java.sql.Date time) {
        TypedQuery<KhuyenMai> query = JPAUtil.getEntityManager().createQuery("SELECT k FROM KhuyenMai k WHERE k.thoiGianKetThuc < :time", KhuyenMai.class);
        query.setParameter("time", time);
        return query.getResultList();
    }


    // Cập nhật số lượng Khuyến Mãi
    @Override public boolean updateSoLuong(String maKM) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE KhuyenMai k SET k.soLuong = k.soLuong - 1 " +
                    "WHERE k.maKM = :maKM AND k.soLuong > 0";

            int rowsUpdated = em.createQuery(jpql)
                    .setParameter("maKM", maKM)
                    .executeUpdate();

            tx.commit();
            updated = rowsUpdated > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return updated;
    }

    // Lấy mã Khuyến Mãi mới nhất
    @Override public String getLatestID() {
        TypedQuery<String> query = JPAUtil.getEntityManager().createQuery("SELECT k.maKM FROM KhuyenMai k ORDER BY k.maKM DESC", String.class);
        List<String> result = query.getResultList();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return "";
    }
}
