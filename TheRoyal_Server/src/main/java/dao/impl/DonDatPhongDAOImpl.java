package dao.impl;

import dao.DonDatPhongDAO;
import entity.DonDatPhong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;


import java.time.LocalDateTime;
import java.sql.Date;
import java.util.ArrayList;
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
        List<DonDatPhong> dataList = new ArrayList<>();

        try {
            String jpql = "SELECT d FROM DonDatPhong d " +
                    "WHERE d.trangThai = 'Đang ở' " +
                    "AND d.khachHang.sDT = :maDDP";

            dataList = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("maDDP", maDDP)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    public List<DonDatPhong> findPhongByTrangThaiAndSDTDaDat(String sdt, String trangThai){
        try {
            TypedQuery<DonDatPhong> query = em.createQuery("SELECT ddp " +
                    "FROM DonDatPhong ddp " +
                    "WHERE ddp.trangThai = :trangThai AND ddp.khachHang.sDT = :sdt", DonDatPhong.class);
            query.setParameter("trangThai", trangThai);
            query.setParameter("sdt", sdt);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getLatestId(){
        EntityManager em = JPAUtil.getEntityManager();
        String id = null;

        try {
            String jpql = "SELECT d.maDDP FROM DonDatPhong d ORDER BY d.maDDP DESC";

            id = em.createQuery(jpql, String.class)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public DonDatPhong getDonDatPhongById(String id){
        EntityManager em = JPAUtil.getEntityManager();
        DonDatPhong ddp = null;

        try {
            String jpql = "SELECT d FROM DonDatPhong d WHERE d.maDDP = :id";

            ddp = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("id", id)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ddp;
    }

    @Override
    public DonDatPhong getDonDatPhongByMaTraPhong(String maDDP){
        EntityManager em = JPAUtil.getEntityManager();
        DonDatPhong ddp = null;

        try {
            String jpql = "SELECT d FROM DonDatPhong d " +
                    "WHERE d.phong.maPhong = :maDDP " +
                    "ORDER BY d.maDDP DESC";

            ddp = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("maDDP", maDDP)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ddp;
    }

    @Override
    public DonDatPhong getDonDatPhongByRoomId(String roomId){
        EntityManager em = JPAUtil.getEntityManager();
        DonDatPhong ddp = null;

        try {
            String jpql = "SELECT d FROM DonDatPhong d " +
                    "WHERE d.phong.maPhong = :roomId " +
                    "ORDER BY d.thoiGianNhanPhong DESC";

            ddp = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("roomId", roomId)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ddp;
    }

    @Override
    public DonDatPhong getDonDatPhongByMaKH(String maKH){
        EntityManager em = JPAUtil.getEntityManager();
        DonDatPhong ddp = null;

        try {
            String jpql = "SELECT d FROM DonDatPhong d WHERE d.khachHang.maKH = :maKH";

            ddp = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("maKH", maKH)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ddp;
    }

    @Override
    public boolean updateTinhTrang(String maDDP, String tinhTrang){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE DonDatPhong d SET d.trangThai = :tinhTrang WHERE d.maDDP = :maDDP";

            int n = em.createQuery(jpql)
                    .setParameter("tinhTrang", tinhTrang)
                    .setParameter("maDDP", maDDP)
                    .executeUpdate();

            tx.commit();
            updated = n > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return updated;
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
        EntityManager em = JPAUtil.getEntityManager();
        boolean available = false;

        try {
            String jpql = "SELECT COUNT(d) FROM DonDatPhong d " +
                    "WHERE d.phong.maPhong = :maPhong " +
                    "AND d.thoiGianDatPhong < :nhanPhong " +
                    "AND d.thoiGianNhanPhong > :traPhong";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("maPhong", maPhong)
                    .setParameter("nhanPhong", nhanPhong)
                    .setParameter("traPhong", traPhong)
                    .getSingleResult();

            available = (count == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return available;
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
        EntityManager em = JPAUtil.getEntityManager();
        List<DonDatPhong> dsDonDatPhong = new ArrayList<>();

        try {
            String jpql = "SELECT d FROM DonDatPhong d " +
                    "WHERE d.maDDP LIKE :maDDP " +
                    "AND d.khachHang.maKH LIKE :maKH " +
                    "AND d.phong.maPhong LIKE :maPhong";

            dsDonDatPhong = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("maDDP", "%" + maDDP + "%")
                    .setParameter("maKH", "%" + maKH + "%")
                    .setParameter("maPhong", "%" + maPhong + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsDonDatPhong;
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
        EntityManager em = JPAUtil.getEntityManager();
        DonDatPhong donDatPhong = null;

        try {
            String jpql = "SELECT d FROM DonDatPhong d " +
                    "WHERE d.phong.maPhong = :maPhong " +
                    "AND d.trangThai = :trangThai";

            donDatPhong = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("maPhong", maPhong)
                    .setParameter("trangThai", trangThai)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return donDatPhong;
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
        EntityManager em = JPAUtil.getEntityManager();
        int count = 0;
        try {
            String jpql = "SELECT COUNT(d) FROM DonDatPhong d " +
                    "WHERE d.trangThai = :trangThai " +
                    "AND :thoiGianChon BETWEEN d.thoiGianNhanPhong AND d.thoiGianTraPhong";

            count = ((Long) em.createQuery(jpql)
                    .setParameter("trangThai", "Phòng trống")
                    .setParameter("thoiGianChon", thoiGianChon)
                    .getSingleResult())
                    .intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public DonDatPhong getDonDatPhongTheoMaHD(String maHD){
        EntityManager em = JPAUtil.getEntityManager();
        DonDatPhong ddp = null;

        try {
            String jpql = "SELECT h.donDatPhong FROM HoaDon h WHERE h.maHD = :maHD";

            ddp = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("maHD", maHD)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ddp;
    }

    @Override
    public int countSLDonDangDon(Date thoiGianChon){
        EntityManager em = JPAUtil.getEntityManager();
        int count = 0;
        try {
            String jpql = "SELECT COUNT(d) FROM DonDatPhong d " +
                    "WHERE d.trangThai = :trangThai " +
                    "AND :thoiGianChon BETWEEN d.thoiGianNhanPhong AND d.thoiGianTraPhong";

            count = ((Long) em.createQuery(jpql)
                    .setParameter("trangThai", "Đang dọn dẹp")
                    .setParameter("thoiGianChon", thoiGianChon)
                    .getSingleResult())
                    .intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
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
                    .setParameter("trangThai", "Đặt trước")
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
            em.flush();
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

    @Override
    public List<DonDatPhong> getListDonDatPhongByMaPhong(String maPhong) {
        EntityManager em = JPAUtil.getEntityManager();
        List<DonDatPhong> donDatPhongList = new ArrayList<>();

        try {
            String jpql = "SELECT d FROM DonDatPhong d " +
                    "WHERE d.phong.maPhong = :maPhong " +
                    "ORDER BY d.maDDP DESC";

            donDatPhongList = em.createQuery(jpql, DonDatPhong.class)
                    .setParameter("maPhong", maPhong)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return donDatPhongList;
    }
}
