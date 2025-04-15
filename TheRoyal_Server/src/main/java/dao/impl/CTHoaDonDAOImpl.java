package dao.impl;

import dao.CTHoaDonDAO;
import entity.CTHoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.sql.Date;
import java.util.List;

public class CTHoaDonDAOImpl extends GenericDAOImpl<CTHoaDon, String> implements CTHoaDonDAO {

    public CTHoaDonDAOImpl(Class<CTHoaDon> clazz) {
        super(clazz);
    }

    public CTHoaDonDAOImpl(EntityManager em, Class<CTHoaDon> clazz) {
        super(em, clazz);
    }

    @Override
    public List<CTHoaDon> getListCTHoaDonByMaHD(String maHD){
        try {
            TypedQuery<CTHoaDon> query = em.createQuery("SELECT cthd FROM CTHoaDon cthd WHERE cthd.id = :maHD", CTHoaDon.class);
            query.setParameter("maHD", maHD);
            return query.getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
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
        try {
            Long count = em.createQuery("""
            SELECT COUNT(cthd) 
            FROM CTHoaDon cthd 
            WHERE cthd.hoaDon.id = :maHD 
              AND (cthd.sanPham.id = :maSP OR cthd.dichVu.id = :maDV)
        """, Long.class)
                    .setParameter("maHD", cthd.getHoaDon().getMaHD())
                    .setParameter("maSP", cthd.getSanPham() != null ? cthd.getSanPham().getMaSP() : null)
                    .setParameter("maDV", cthd.getDichVu() != null ? cthd.getDichVu().getMaDV() : null)
                    .getSingleResult();

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSLSP(CTHoaDon ctHoaDon) {
        try {
            int updatedCount = em.createQuery("""
            UPDATE CTHoaDon c
            SET c.soLuongSP = :soLuongSP,
                c.tongTienSP = :tongTienSP
            WHERE c.id = :maCTHD AND c.sanPham.id = :maSP
        """)
                    .setParameter("soLuongSP", ctHoaDon.getSoLuongSP())
                    .setParameter("tongTienSP", ctHoaDon.getTongTienSP())
                    .setParameter("maCTHD", ctHoaDon.getMaCTHD())
                    .setParameter("maSP", ctHoaDon.getSanPham().getMaSP())
                    .executeUpdate();

            return updatedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSLDV(CTHoaDon ctHoaDon) {
        try {
            int updatedCount = em.createQuery("""
            UPDATE CTHoaDon c
            SET c.soLuongDV = :soLuongDV,
                c.tongTienDV = :tongTienDV
            WHERE c.id = :maCTHD AND c.dichVu.id = :maDV
        """)
                    .setParameter("soLuongDV", ctHoaDon.getSoLuongDV())
                    .setParameter("tongTienDV", ctHoaDon.getTongTienDV())
                    .setParameter("maCTHD", ctHoaDon.getMaCTHD())
                    .setParameter("maDV", ctHoaDon.getDichVu().getMaDV())
                    .executeUpdate();

            return updatedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD) {
        try {
            TypedQuery<CTHoaDon> query = em.createQuery("""
            SELECT cthd 
            FROM CTHoaDon cthd 
            WHERE cthd.dichVu.id = :maDV 
              AND cthd.hoaDon.id = :maHD
        """, CTHoaDon.class);
            query.setParameter("maDV", maDV);
            query.setParameter("maHD", maHD);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD) {
        try {
            TypedQuery<CTHoaDon> query = em.createQuery("""
            SELECT cthd FROM CTHoaDon cthd 
            WHERE cthd.sanPham.id = :maSP AND cthd.hoaDon.id = :maHD
        """, CTHoaDon.class);
            query.setParameter("maSP", maSP);
            query.setParameter("maHD", maHD);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getTongTienNam(int nam) {
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

            return (int) Math.round(tongTatCaTienDVNam);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTongTienThang(int thang, int nam) {
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

            return (int) Math.round(tongTatCaTienDVThang);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getTongDVTienNgay(Date ngay) {
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

            return (int) Math.round(tongTien);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
