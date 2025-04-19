package dao.impl;

import dao.DonDatPhongDAO;
import entity.DonDatPhong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;


import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;

public class DonDatPhongDAOImpl extends GenericDAOImpl<DonDatPhong, String> implements DonDatPhongDAO {

    public DonDatPhongDAOImpl(Class<DonDatPhong> clazz) {
        super(clazz);
    }

    public DonDatPhongDAOImpl(EntityManager em, Class<DonDatPhong> clazz) {
        super(em, clazz);
    }

    @Override
    public List<DonDatPhong> findPhongByTrangThaiAndSDTDangO(String maDDP){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp " +
                    "FROM DonDatPhong ddp " +
                    "WHERE ddp.trangThai = :trangThai AND ddp.khachHang.maKH = :maKh", DonDatPhong.class);
            query.setParameter("trangThai", "Đang ở");
            query.setParameter("maKh", maDDP);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DonDatPhong> findPhongByTrangThaiAndSDTDaDat(String sdt, String trangThai){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp " +
                    "FROM DonDatPhong ddp " +
                    "WHERE ddp.trangThai = :trangThai AND ddp.khachHang.sDT = :sdt", DonDatPhong.class);
            query.setParameter("N'trangThai'", trangThai);
            query.setParameter("sdt", sdt);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getLatestId(){
        try {
            TypedQuery<String> query = em.createQuery("SELECT ddp.id FROM DonDatPhong ddp ORDER BY ddp.id ASC", String.class);
            return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DonDatPhong getDonDatPhongById(String id){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp FROM DonDatPhong ddp WHERE ddp.id = :id", DonDatPhong.class);
            query.setParameter("id", id).getSingleResult();
            return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DonDatPhong getDonDatPhongByMaTraPhong(String maDDP){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp FROM DonDatPhong ddp WHERE ddp.maDDP = :maDDP", DonDatPhong.class);
            query.setParameter("maDDP", maDDP).getSingleResult();
            return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DonDatPhong getDonDatPhongByRoomId(String roomId){
        try {
           TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp FROM DonDatPhong ddp WHERE ddp.phong.id = :roomId", DonDatPhong.class);
           query.setParameter("roomId", roomId).getSingleResult();
           return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DonDatPhong getDonDatPhongByMaKH(String maKH){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp FROM DonDatPhong ddp WHERE ddp.khachHang.id = :maKh", DonDatPhong.class);
            query.setParameter("maKh", maKH).getSingleResult();
            return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateTinhTrang(String maDDP, String tinhTrang){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            TypedQuery<DonDatPhong> query = em.createQuery(
                    "UPDATE DonDatPhong ddp SET ddp.trangThai = :tinhTrang WHERE ddp.id = :maDDP"
            , DonDatPhong.class);
            query.setParameter("N'tinhTrang'", tinhTrang);
            query.setParameter("maDDP", maDDP);

            int updated = query.executeUpdate();
            tr.commit();

            return updated > 0;
        }catch (Exception e){
            if (tr.isActive()) tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTraPhong(String maDDP, Date traPhong){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            TypedQuery<DonDatPhong> query = em.createQuery("UPDATE DonDatPhong ddp SET ddp.thoiGianTraPhong = :traPhong WHERE ddp.id = :maDDP", DonDatPhong.class);
            query.setParameter("traPhong", traPhong).getSingleResult();
            query.setParameter("maDDP", maDDP);
            int updated = query.executeUpdate();
            tr.commit();
            return updated > 0;
        }catch (Exception e){
            if(tr.isActive()) tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateNhanPhong(String maDDP, Date nhanPhong){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            TypedQuery<DonDatPhong> query = em.createQuery("UPDATE DonDatPhong ddp SET ddp.thoiGianNhanPhong = :nhanPhong WHERE ddp.id = :maDDP", DonDatPhong.class);
            query.setParameter("nhanPhong", nhanPhong).getSingleResult();
            query.setParameter("maDDP", maDDP);
            int updated = query.executeUpdate();
            tr.commit();
            return updated > 0;
        }catch (Exception e){
            if(tr.isActive()) tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isRoomAvailable(String maPhong, Date traPhong, Date nhanPhong){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp " +
                    "FROM DonDatPhong ddp " +
                    "WHERE ddp.phong.id = :maPhong AND (ddp.thoiGianTraPhong < :traPhong AND ddp.thoiGianNhanPhong > :nhanPhong)", DonDatPhong.class);
            query.setParameter("maPhong", maPhong).getSingleResult();
            query.setParameter("traPhong", traPhong).getSingleResult();
            query.setParameter("nhanPhong", nhanPhong).getSingleResult();
            DonDatPhong ddp = query.getSingleResult();
            if(ddp != null){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public List<DonDatPhong> getDonDatPhongByNgayNhanPhong(Date ngayNhanPhong){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp FROM DonDatPhong ddp WHERE ddp.thoiGianNhanPhong = :ngayNhanPhong", DonDatPhong.class);
            query.setParameter("ngayNhanPhong", ngayNhanPhong);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DonDatPhong> timDonDatPhong(String maDDP, String maKH, String maPhong){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp " +
                    "FROM DonDatPhong ddp " +
                    "WHERE ddp.id LIKE :maDDP AND ddp.khachHang.id LIKE %:maKH AND ddp.phong.id LIKE %:maPhong", DonDatPhong.class);
            query.setParameter("maDDP", "%"+maDDP+"%").getSingleResult();
            query.setParameter("maKH", "%"+maKH+"%").getSingleResult();
            query.setParameter("maPhong", "%"+maPhong+"%").getSingleResult();
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getTrangThaiPhongOThoiDiemChon(String maPhong, Date selectedDate){

        String trangThai = "Phòng trống";

        try {
            String jpql = "SELECT d.trangThai FROM DonDatPhong d JOIN d.phong p " +
                    "WHERE p.maPhong = :maPhong " +
                    "AND :selectedDate BETWEEN d.thoiGianNhanPhong AND d.thoiGianTraPhong";

            trangThai = em.createQuery(jpql, String.class)
                    .setParameter("maPhong", maPhong)
                    .setParameter("selectedDate", selectedDate)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse("Phòng trống");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trangThai;
    }

    @Override
    public DonDatPhong getDonDatTruocTheoPhongVaTrangThai(String maPhong, String trangThai){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp FROM DonDatPhong ddp " +
                    "WHERE ddp.phong.id = :maPhong AND ddp.trangThai = :status", DonDatPhong.class);
            query.setParameter("maPhong", maPhong).getSingleResult();
            query.setParameter("N'status'", trangThai).getSingleResult();
            return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int countSLDonDatTruoc(Date ngayDuocChon){
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(ddp) FROM DonDatPhong ddp " +
                    "WHERE ddp.trangThai = :status AND :ngayDuocChon BETWEEN ddp.thoiGianNhanPhong AND ddp.thoiGianTraPhong", Long.class);
            query.setParameter("status", "Đặt trước");
            query.setParameter("ngayDuocChon", ngayDuocChon);
            Long count = query.getSingleResult();
            return count.intValue();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int countSLDonDangO(Date thoiGianChon){
        EntityManager em = JPAUtil.getEntityManager();
        int count = 0;
        try {
            String jpql = "SELECT COUNT(d) FROM DonDatPhong d " +
                    "WHERE d.trangThai = :trangThai " +
                    "AND :thoiGianChon BETWEEN d.thoiGianNhanPhong AND d.thoiGianTraPhong";

            count = ((Long) em.createQuery(jpql)
                    .setParameter("trangThai", "Đang ở")
                    .setParameter("thoiGianChon", thoiGianChon)
                    .getSingleResult())
                    .intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int countSLPhongTrong(Date thoiGianChon){
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(ddp) FROM DonDatPhong ddp " +
                    "WHERE ddp.trangThai = :status AND :ngayDuocChon BETWEEN ddp.thoiGianNhanPhong AND ddp.thoiGianTraPhong", Long.class);
            query.setParameter("status", "N'Phòng trống'");
            query.setParameter("ngayDuocChon", thoiGianChon);
            Long count = query.getSingleResult();
            return count.intValue();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public DonDatPhong getDonDatPhongTheoMaHD(String maHD){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp FROM DonDatPhong ddp " +
                    "WHERE ddp.id = (SELECT hd.donDatPhong.id FROM HoaDon hd WHERE hd.id = :maHD)", DonDatPhong.class);
            query.setParameter("maHD", maHD).getSingleResult();
            return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int countSLDonDangDon(Date ngayDuocChon){
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(ddp) FROM DonDatPhong ddp " +
                    "WHERE ddp.trangThai = :status AND :ngayDuocChon BETWEEN ddp.thoiGianNhanPhong AND ddp.thoiGianTraPhong", Long.class);
            query.setParameter("status", "N'Đang dọn dẹp'");
            query.setParameter("ngayDuocChon", ngayDuocChon);
            Long count = query.getSingleResult();
            return count.intValue();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public DonDatPhong getDonDatTruocTheoPhongVaNgay(String maPhong, java.util.Date thoiGianChon) {
        DonDatPhong result = null;
        try {
            String jpql = "SELECT d FROM DonDatPhong d JOIN d.phong p " +
                    "WHERE p.maPhong = :maPhong " +
                    "AND d.trangThai = :trangThai " +
                    "AND :thoiGianChon BETWEEN d.thoiGianNhanPhong AND d.thoiGianTraPhong";

            result = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("maPhong", maPhong)
                    .setParameter("trangThai", "N'Đặt trước'")
                    .setParameter("thoiGianChon", thoiGianChon)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean insert(DonDatPhong ddp) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;

        try {
            tx.begin();


            em.persist(ddp);

            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close(); // RẤT QUAN TRỌNG
        }

        return success;
    }
}
