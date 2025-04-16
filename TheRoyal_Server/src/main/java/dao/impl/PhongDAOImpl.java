package dao.impl;

import dao.PhongDAO;
import entity.Phong;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class PhongDAOImpl extends GenericDAOImpl<Phong, String> implements PhongDAO {
    public PhongDAOImpl(Class<Phong> clazz) {
        super(clazz);
    }

    public PhongDAOImpl(EntityManager em, Class<Phong> clazz) {
        super(em, clazz);
    }


    @Override 
    public List<Phong> getPhongTheoMaPhong(String maPhong) {
        return em.createQuery("SELECT p FROM Phong p WHERE p.maPhong = :maPhong", Phong.class)
                .setParameter("maPhong", maPhong)
                .getResultList();
    }

    @Override 
    public Phong getPhongByMaPhong(String maPhong) {
        return em.find(Phong.class, maPhong);
    }

    @Override 
    public boolean updateTinhTrang(String maPhong, String trangThaiMoi) {
        try {
            em.getTransaction().begin();
            Phong phong = em.find(Phong.class, maPhong);
            if (phong != null) {
                phong.setTrangThai(trangThaiMoi);
                em.merge(phong);
                em.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override 
    public List<Phong> getPhongByTinhTrang(String tinhTrang) {
        return em.createQuery("SELECT p FROM Phong p WHERE p.trangThai = :tinhTrang", Phong.class)
                .setParameter("tinhTrang", tinhTrang)
                .getResultList();
    }

    @Override 
    public List<Phong> getPhongConLaiTheoNgayNhanPhong(LocalDate ngayNhan) {
        return em.createQuery(
            "SELECT p FROM Phong p WHERE p.maPhong NOT IN " +
            "(SELECT p2.maPhong FROM Phong p2 JOIN p2.dsDonDatPhong ddp WHERE ddp.thoiGianNhanPhong = :ngayNhan)", 
            Phong.class)
            .setParameter("ngayNhan", ngayNhan)
            .getResultList();
    }

    @Override 
    public List<Phong> getPhongDaDat(LocalDate ngayNhan) {
        return em.createQuery(
            "SELECT DISTINCT p FROM Phong p JOIN p.dsDonDatPhong ddp " +
            "WHERE ddp.thoiGianNhanPhong = :ngayNhan AND p.trangThai = 'Đã đặt'", 
            Phong.class)
            .setParameter("ngayNhan", ngayNhan)
            .getResultList();
    }

    @Override 
    public Phong getPhongByMaHoaDon(String maPhong) {
        try {
            return em.createQuery(
                "SELECT p FROM Phong p JOIN p.dsHoaDon hd " +
                "WHERE p.maPhong = :maPhong AND p.trangThai = 'Đang ở'", 
                Phong.class)
                .setParameter("maPhong", maPhong)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override 
    public int getSoPhongTheoTrangThai(String trangThai) {
        Long count = em.createQuery("SELECT COUNT(p) FROM Phong p WHERE p.trangThai = :trangThai", Long.class)
                .setParameter("trangThai", trangThai)
                .getSingleResult();
        return count.intValue();
    }

    @Override 
    public int getTongSoPhong() {
        Long count = em.createQuery("SELECT COUNT(p) FROM Phong p", Long.class)
                .getSingleResult();
        return count.intValue();
    }

    @Override
    public String getLatestID() {
        String id = null;
        try {
            String jpql = "SELECT p.maPhong FROM Phong p ORDER BY p.maPhong DESC";

            id = em.createQuery(jpql, String.class)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            id = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean updateTinhTrangPhong(Phong phong, String trangThai) {

        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();
            String jpql = "UPDATE Phong p SET p.trangThai = :trangThai WHERE p.maPhong = :maPhong";

            int rows = em.createQuery(jpql)
                    .setParameter("trangThai", trangThai)
                    .setParameter("maPhong", phong.getMaPhong())
                    .executeUpdate();

            tx.commit();
            updated = rows > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return updated;
    }


}
