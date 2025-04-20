package dao.impl;

import dao.CTHoaDonDAO;
import entity.CTHoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CTHoaDonDAOImpl extends GenericDAOImpl<CTHoaDon, String> implements CTHoaDonDAO {

    public CTHoaDonDAOImpl(Class<CTHoaDon> clazz) {
        super(clazz);
    }

    public CTHoaDonDAOImpl(EntityManager em, Class<CTHoaDon> clazz) {
        super(em, clazz);
    }

    @Override
    public List<CTHoaDon> getListCTHoaDonByMaHD(String maHDon){
        EntityManager em = JPAUtil.getEntityManager();
        List<CTHoaDon> dataList = new ArrayList<>();
        try {
            String jpql = "SELECT c FROM CTHoaDon c WHERE c.hoaDon.maHD = :maHDon";

            dataList = em.createQuery(jpql, CTHoaDon.class)
                    .setParameter("maHDon", maHDon)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    public String getLatestID() {
        try {
            TypedQuery<String> query = em.createQuery(
                    "SELECT cthd.id FROM CTHoaDon cthd ORDER BY cthd.id DESC",
                    String.class
            );
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean checkIfCTHoaDonExists(CTHoaDon cthd) {
        EntityManager em = JPAUtil.getEntityManager();
        boolean exists = false;

        try {
            String jpql = "SELECT COUNT(c) FROM CTHoaDon c " +
                    "WHERE c.hoaDon.maHD = :maHD " +
                    "AND ( (c.sanPham IS NOT NULL AND c.sanPham.maSP = :maSP) " +
                    "   OR (c.dichVu IS NOT NULL AND c.dichVu.maDV = :maDV) )";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("maHD", cthd.getHoaDon().getMaHD())
                    .setParameter("maSP", cthd.getSanPham() != null ? cthd.getSanPham().getMaSP() : null)
                    .setParameter("maDV", cthd.getDichVu() != null ? cthd.getDichVu().getMaDV() : null)
                    .getSingleResult();

            exists = count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }


    @Override
    public boolean updateSLSP(CTHoaDon ctHoaDon) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE CTHoaDon c " +
                    "SET c.soLuongSP = :soLuongSP, " +
                    "    c.tongTienSP = :tongTienSP " +
                    "WHERE c.maCTHD = :maCTHD " +
                    "AND c.sanPham.maSP = :maSP";

            int n = em.createQuery(jpql)
                    .setParameter("soLuongSP", ctHoaDon.getSoLuongSP())
                    .setParameter("tongTienSP", ctHoaDon.getTongTienSP())
                    .setParameter("maCTHD", ctHoaDon.getMaCTHD())
                    .setParameter("maSP", ctHoaDon.getSanPham().getMaSP())
                    .executeUpdate();

            tx.commit();
            updated = n > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return updated;
    }

    @Override
    public boolean updateSLDV(CTHoaDon ctHoaDon) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE CTHoaDon c " +
                    "SET c.soLuongDV = :soLuongDV, " +
                    "    c.tongTienDV = :tongTienDV " +
                    "WHERE c.maCTHD = :maCTHD " +
                    "AND c.dichVu.maDV = :maDV";

            int n = em.createQuery(jpql)
                    .setParameter("soLuongDV", ctHoaDon.getSoLuongDV())
                    .setParameter("tongTienDV", ctHoaDon.getTongTienDV())
                    .setParameter("maCTHD", ctHoaDon.getMaCTHD())
                    .setParameter("maDV", ctHoaDon.getDichVu().getMaDV())
                    .executeUpdate();

            tx.commit();
            updated = n > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return updated;
    }

    @Override
    public CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD) {
        EntityManager em = JPAUtil.getEntityManager();
        CTHoaDon cthd = null;

        try {
            String jpql = "SELECT c FROM CTHoaDon c " +
                    "WHERE c.dichVu.maDV = :maDV " +
                    "AND c.hoaDon.maHD = :maHD";

            cthd = em.createQuery(jpql, CTHoaDon.class)
                    .setParameter("maDV", maDV)
                    .setParameter("maHD", maHD)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return cthd;
    }

    @Override
    public CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD) {
        EntityManager em = JPAUtil.getEntityManager();
        CTHoaDon cthd = null;

        try {
            String jpql = "SELECT c FROM CTHoaDon c " +
                    "WHERE c.sanPham.maSP = :maSP " +
                    "AND c.hoaDon.maHD = :maHD";

            cthd = em.createQuery(jpql, CTHoaDon.class)
                    .setParameter("maSP", maSP)
                    .setParameter("maHD", maHD)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return cthd;
    }

    @Override
    public double getTongTienNam(int nam) {
        try {
            List<Double> top5TongTien = em.createQuery("""
            SELECT SUM(od.soLuongDV * dv.giaDV) 
            FROM CTHoaDon od
            JOIN od.hoaDon o
            JOIN od.dichVu dv
            WHERE FUNCTION('YEAR', o.thoiGianLapHD) = :nam
            GROUP BY dv.id, dv.tenDV
            ORDER BY SUM(od.soLuongDV) DESC
        """, Double.class)
                    .setParameter("nam", nam)
                    .setMaxResults(5)
                    .getResultList();

            double tongTatCaTienDVNam = top5TongTien.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            return tongTatCaTienDVNam;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double getTongTienThang(int thang, int nam) {
        try {
            List<Double> top5TongTien = em.createQuery("""
            SELECT SUM(od.soLuongDV * dv.giaDV)
            FROM CTHoaDon od
            JOIN od.hoaDon o
            JOIN od.dichVu dv
            WHERE FUNCTION('MONTH', o.thoiGianLapHD) = :thang
              AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam
            GROUP BY dv.id, dv.tenDV
            ORDER BY SUM(od.soLuongDV) DESC
        """, Double.class)
                    .setParameter("thang", thang)
                    .setParameter("nam", nam)
                    .setMaxResults(5)
                    .getResultList();

            double tongTatCaTienDVThang = top5TongTien.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            return tongTatCaTienDVThang;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double getTongDVTienNgay(Date ngay) {
        try {
            List<Double> top5TongTien = em.createQuery("""
            SELECT SUM(od.soLuongDV * dv.giaDV)
            FROM CTHoaDon od
            JOIN od.hoaDon o
            JOIN od.dichVu dv
            WHERE o.thoiGianLapHD = :ngay
            GROUP BY dv.maDV, dv.tenDV
            ORDER BY SUM(od.soLuongDV) DESC
        """, Double.class)
                    .setParameter("ngay", ngay)
                    .setMaxResults(5)
                    .getResultList();

            double tongTien = top5TongTien.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            return tongTien;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean insert(CTHoaDon ctHoaDon) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;

        try {
            tx.begin();
            // Persist the new HoaDon
            em.persist(ctHoaDon);
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
