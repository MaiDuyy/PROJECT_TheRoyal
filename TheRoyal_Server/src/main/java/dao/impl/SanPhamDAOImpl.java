package dao.impl;

import dao.SanPhamDAO;
import entity.DichVu;
import entity.SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import util.JPAUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SanPhamDAOImpl  extends GenericDAOImpl<SanPham, String > implements SanPhamDAO {
    String COLUMN_TONG_TIEN_MH_THANG = "TongTatCaTienThang";
    String COLUMN_TONG_TIEN_SP_NAM = "TongTatCaTien";
    public SanPhamDAOImpl(Class<SanPham> clazz) {
        super(clazz);
    }

    public SanPhamDAOImpl(EntityManager em, Class<SanPham> clazz) {
        super(em, clazz);
    }


    @Override public List<SanPham> getSanPhamTheoMa(String maSP) {
        List<SanPham> dssp = new ArrayList<>();
        try {
            TypedQuery<SanPham> query = em.createQuery(
                "SELECT sp FROM SanPham sp WHERE sp.maSP = :maSP OR sp.tenSP LIKE :tenSP", 
                SanPham.class);
            query.setParameter("maSP", maSP);
            query.setParameter("tenSP", "%" + maSP + "%");
            dssp = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dssp;
    }
    
    @Override public List<SanPham> getSanPhamTheoLoai(String tenSP) {
        List<SanPham> dssptt = new ArrayList<>();
        try {
            TypedQuery<SanPham> query = em.createQuery(
                "SELECT sp FROM SanPham sp WHERE sp.tenSP = :tenSP", 
                SanPham.class);
            query.setParameter("tenSP", tenSP);
            dssptt = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dssptt;
    }
    

    @Override public String getLatestID() {
        try {
            TypedQuery<String> query = em.createQuery(
                "SELECT sp.maSP FROM SanPham sp ORDER BY sp.maSP DESC", 
                String.class);
            query.setMaxResults(1);
            List<String> results = query.getResultList();
            
            if (results.isEmpty()) {
                return "";
            }
            
            return results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    @Override public boolean xoaSanPham(SanPham spXoa) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            SanPham sp = em.find(SanPham.class, spXoa.getMaSP());
            if (sp != null) {
                em.remove(sp);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override public boolean suaSanPham(SanPham sp) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            SanPham existingSP = em.find(SanPham.class, sp.getMaSP());
            if (existingSP != null) {
                existingSP.setTenSP(sp.getTenSP());
                existingSP.setMoTa(sp.getMoTa());
                existingSP.setGiaSP(sp.getGiaSP());
                existingSP.setSoLuongSP(sp.getSoLuongSP());
                em.merge(existingSP);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override public boolean updateSLSP(SanPham sp, int sl) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            SanPham existingSP = em.find(SanPham.class, sp.getMaSP());
            if (existingSP != null) {
                existingSP.setSoLuongSP(sl);
                em.merge(existingSP);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override public boolean SanPhamTonTai(SanPham sp) {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(sp) FROM SanPham sp WHERE sp.maSP = :maSP OR sp.tenSP = :tenSP", 
                Long.class);
            query.setParameter("maSP", sp.getMaSP());
            query.setParameter("tenSP", sp.getTenSP());
            Long count = query.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override public SanPham getSanPhamTheoMaHoacTen(String maOrTen) {
        try {
            TypedQuery<SanPham> query = em.createQuery(
                "SELECT sp FROM SanPham sp WHERE sp.maSP = :maOrTen OR sp.tenSP = :maOrTen", 
                SanPham.class);
            query.setParameter("maOrTen", maOrTen);
            List<SanPham> results = query.getResultList();
            
            if (!results.isEmpty()) {
                return results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override public int getTongTienThang(String thang, String nam) {
        try {
            Query query = em.createNativeQuery(
                "SELECT SUM(TongTien) AS TongTatCaTienThang " +
                "FROM ( " +
                "  SELECT ROUND(SUM(od.soLuongSP * sp.giaSP), 2) AS TongTien " +
                "  FROM CTHoaDon od " +
                "  JOIN HoaDon o ON od.maHD = o.maHD " +
                "  JOIN SanPham sp ON od.maSP = sp.maSP " +
                "  WHERE MONTH(o.thoiGianLapHD) = :thang AND YEAR(o.thoiGianLapHD) = :nam " +
                "  GROUP BY sp.maSP, sp.tenSP " +
                ") AS Tong");
            
            query.setParameter("thang", thang);
            query.setParameter("nam", nam);
            
            Object result = query.getSingleResult();
            if (result != null) {
                return ((Number) result).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override public List<String[]> getSPNgay(Date ngay) {
        List<String[]> list = new ArrayList<>();
        try {
            Query query = em.createNativeQuery(
                "SELECT sp.maSP, sp.tenSP, SUM(od.soLuongSP) AS SoLuong, " +
                "ROUND(SUM(od.soLuongSP * sp.giaSP), 2) AS TongTien " +
                "FROM CTHoaDon od " +
                "JOIN HoaDon o ON od.maHD = o.maHD " +
                "JOIN SanPham sp ON od.maSP = sp.maSP " +
                "WHERE (o.thoiGianLapHD) = :ngay " +
                "GROUP BY sp.maSP, sp.tenSP " +
                "ORDER BY sp.maSP ASC");
            
            query.setParameter("ngay", ngay);
            
            List<Object[]> results = query.getResultList();
            for (Object[] row : results) {
                String[] arr = new String[4];
                arr[0] = (String) row[0]; // maSP
                arr[1] = (String) row[1]; // tenSP
                arr[2] = row[2].toString(); // SoLuong
                arr[3] = row[3].toString(); // TongTien
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override public List<String[]> getMHThang(String thang, String nam) {
        List<String[]> list = new ArrayList<>();
        try {
            Query query = em.createNativeQuery(
                "SELECT sp.maSP, sp.tenSP, SUM(od.soLuongSP) AS SoLuong, " +
                "ROUND(SUM(od.soLuongSP * sp.giaSP), 2) AS TongTien " +
                "FROM CTHoaDon od " +
                "JOIN HoaDon o ON od.maHD = o.maHD " +
                "JOIN SanPham sp ON od.maSP = sp.maSP " +
                "WHERE MONTH(o.thoiGianLapHD) = :thang AND YEAR(o.thoiGianLapHD) = :nam " +
                "GROUP BY sp.maSP, sp.tenSP " +
                "ORDER BY sp.maSP ASC");
            
            query.setParameter("thang", thang);
            query.setParameter("nam", nam);
            
            List<Object[]> results = query.getResultList();
            for (Object[] row : results) {
                String[] arr = new String[4];
                arr[0] = (String) row[0]; // maSP
                arr[1] = (String) row[1]; // tenSP
                arr[2] = row[2].toString(); // SoLuong
                arr[3] = row[3].toString(); // TongTien
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override public List<String[]> getMHNam(String nam) {
        List<String[]> list = new ArrayList<>();
        try {
            Query query = em.createNativeQuery(
                "SELECT sp.maSP, sp.tenSP, SUM(od.soLuongSP) AS SoLuong, " +
                "ROUND(SUM(od.soLuongSP * sp.giaSP), 2) AS TongTien " +
                "FROM CTHoaDon od " +
                "JOIN HoaDon o ON od.maHD = o.maHD " +
                "JOIN SanPham sp ON od.maSP = sp.maSP " +
                "WHERE YEAR(o.thoiGianLapHD) = :nam " +
                "GROUP BY sp.maSP, sp.tenSP " +
                "ORDER BY sp.maSP ASC");
            
            query.setParameter("nam", nam);
            
            List<Object[]> results = query.getResultList();
            for (Object[] row : results) {
                String[] arr = new String[4];
                arr[0] = (String) row[0]; // maSP
                arr[1] = (String) row[1]; // tenSP
                arr[2] = row[2].toString(); // SoLuong
                arr[3] = row[3].toString(); // TongTien
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override public int getTongTienSPNam(String nam) {
        try {
            Query query = em.createNativeQuery(
                "SELECT SUM(TongTien) AS TongTatCaTien " +
                "FROM ( " +
                "  SELECT ROUND(SUM(od.soLuongSP * sp.giaSP), 2) AS TongTien " +
                "  FROM CTHoaDon od " +
                "  JOIN HoaDon o ON od.maHD = o.maHD " +
                "  JOIN SanPham sp ON od.maSP = sp.maSP " +
                "  WHERE YEAR(o.thoiGianLapHD) = :nam " +
                "  GROUP BY sp.maSP, sp.tenSP " +
                ") AS Tong");
            
            query.setParameter("nam", nam);
            
            Object result = query.getSingleResult();
            if (result != null) {
                return ((Number) result).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SanPham timSanPhamTheoMaHoacTheoTen(String maOrTen) {
        EntityManager em = JPAUtil.getEntityManager();
        SanPham sp = null;
        try {
            String jpql = "SELECT s FROM SanPham s " +
                    "WHERE LOWER(s.maSP) = LOWER(:maOrTen) " +
                    "OR LOWER(s.tenSP) = LOWER(:maOrTen)";

            sp = em.createQuery(jpql, SanPham.class)
                    .setParameter("maOrTen", maOrTen)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }


} 