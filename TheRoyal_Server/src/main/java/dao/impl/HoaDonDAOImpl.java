package dao.impl;

import dao.HoaDonDAO;
import entity.HoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAOImpl extends GenericDAOImpl<HoaDon, String>  implements HoaDonDAO {

    public HoaDonDAOImpl(Class<HoaDon> clazz) {
        super(clazz);
    }

    public HoaDonDAOImpl(EntityManager em, Class<HoaDon> clazz) {
        super(em, clazz);
    }

    // Lấy hóa đơn theo mã
    @Override public HoaDon getHoaDonTheoMa(String maHD) {
        return JPAUtil.getEntityManager().find(HoaDon.class, maHD);
    }

    // Lấy danh sách hóa đơn theo mã
    @Override public List<HoaDon> getHoaDonTheoMaList(String maHD) {
        TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery("SELECT h FROM HoaDon h WHERE h.maHD = :maHD", HoaDon.class);
        query.setParameter("maHD", maHD);
        return query.getResultList();
    }

    // Lấy hóa đơn theo mã phòng
    @Override public HoaDon getHoaDonTheoPhong(String maPhong) {
        EntityManager em = JPAUtil.getEntityManager();
        HoaDon hoaDon = null;

        try {
            String jpql = "SELECT h FROM HoaDon h " +
                    "JOIN h.donDatPhong d " +
                    "WHERE d.phong.maPhong = :maPhong " +
                    "AND d.trangThai = :trangThai " +
                    "ORDER BY h.maHD DESC";

            hoaDon = em.createQuery(jpql, HoaDon.class)
                    .setParameter("maPhong", maPhong)
                    .setParameter("trangThai", "Đang ở")
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hoaDon;
    }

    // Lấy hóa đơn theo mã đặt phòng
    @Override public HoaDon getHoaDonTheoDonDatPhong(String maDDP) {
        EntityManager em = JPAUtil.getEntityManager();
        HoaDon hoaDon = null;

        try {
            String jpql = "SELECT h FROM HoaDon h " +
                    "WHERE h.donDatPhong.maDDP = :maDDP";

            hoaDon = em.createQuery(jpql, HoaDon.class)
                    .setParameter("maDDP", maDDP)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hoaDon;
    }

      



    // Lấy tổng tiền sản phẩm và dịch vụ của hóa đơn
    @Override public double[] getTongTienSanPhamDichVu(String maHD) {
        EntityManager em = JPAUtil.getEntityManager();
        double tongTienSP = 0;
        double tongTienDV = 0;

        try {
            String jpql = "SELECT SUM(c.tongTienSP), SUM(c.tongTienDV) " +
                    "FROM CTHoaDon c " +
                    "WHERE c.hoaDon.maHD = :maHD";

            Object[] result = (Object[]) em.createQuery(jpql)
                    .setParameter("maHD", maHD)
                    .getSingleResult();

            if (result != null) {
                if (result[0] != null) {
                    tongTienSP = ((Number) result[0]).doubleValue();
                }
                if (result[1] != null) {
                    tongTienDV = ((Number) result[1]).doubleValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new double[]{tongTienSP, tongTienDV};
    }

    // Cập nhật tổng tiền sản phẩm và dịch vụ
    @Override public boolean updateTongTienHoaDon(HoaDon hoaDon, double tongTienSP, double tongTienDV) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE HoaDon h SET h.tienSanPham = :tongTienSP, h.tienDichVu = :tongTienDV " +
                    "WHERE h.maHD = :maHD";

            int n = em.createQuery(jpql)
                    .setParameter("tongTienSP", tongTienSP)
                    .setParameter("tongTienDV", tongTienDV)
                    .setParameter("maHD", hoaDon.getMaHD())
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

    // Cập nhật trạng thái của hóa đơn
    @Override public boolean updateTinhTrang(String maHD, String trangThai) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE HoaDon h SET h.trangThai = :trangThai WHERE h.maHD = :maHD";

            int n = em.createQuery(jpql)
                    .setParameter("trangThai", trangThai)
                    .setParameter("maHD", maHD)
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

    // Cập nhật mã khuyến mãi cho hóa đơn
    @Override public boolean updateKM(String maKM, String maHD) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE HoaDon h SET h.khuyenMai.maKM = :maKM WHERE h.maHD = :maHD";

            int n = em.createQuery(jpql)
                    .setParameter("maKM", maKM)
                    .setParameter("maHD", maHD)
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
    public int getTongTienNgay(java.sql.Date ngay) {
        EntityManager em = JPAUtil.getEntityManager();
        int tongTien = 0;

        try {
            LocalDate localDate = ngay.toLocalDate();
            LocalDateTime startOfDay = localDate.atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1);

            String jpql = "SELECT SUM(h.tongTien) FROM HoaDon h " +
                    "WHERE h.thoiGianLapHD >= :startOfDay AND h.thoiGianLapHD < :endOfDay";

            Double result = em.createQuery(jpql, Double.class)
                    .setParameter("startOfDay", Timestamp.valueOf(startOfDay))
                    .setParameter("endOfDay", Timestamp.valueOf(endOfDay))
                    .getSingleResult();

            tongTien = (result != null) ? result.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongTien;
    }

    // Tính doanh thu theo ngày
    @Override
    public List<HoaDon> getDoanhThuNgay(java.sql.Date ngay) {
        EntityManager em = JPAUtil.getEntityManager();
        List<HoaDon> dsHD = new ArrayList<>();

        try {
            LocalDate localDate = ngay.toLocalDate();
            LocalDateTime startOfDay = localDate.atStartOfDay();
            LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay();

            String jpql = "SELECT h FROM HoaDon h " +
                    "WHERE h.thoiGianLapHD >= :startOfDay AND h.thoiGianLapHD < :endOfDay";

            dsHD = em.createQuery(jpql, HoaDon.class)
                    .setParameter("startOfDay", Timestamp.valueOf(startOfDay))
                    .setParameter("endOfDay", Timestamp.valueOf(endOfDay))
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsHD;
    }

    // Lấy số lượng hóa đơn trong ngày
    @Override public int getSoLuongHoaDonNgay(java.sql.Date ngay) {
        EntityManager em = JPAUtil.getEntityManager();
        int count = 0;

        try {
            LocalDate localDate = ngay.toLocalDate();
            LocalDateTime startOfDay = localDate.atStartOfDay();
            LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay();

            String jpql = "SELECT COUNT(h) FROM HoaDon h " +
                    "WHERE h.thoiGianLapHD >= :startOfDay AND h.thoiGianLapHD < :endOfDay";

            count = ((Long) em.createQuery(jpql)
                    .setParameter("startOfDay", Timestamp.valueOf(startOfDay))
                    .setParameter("endOfDay", Timestamp.valueOf(endOfDay))
                    .getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return count;
    }

    // Lấy doanh thu theo tháng
    @Override public List<HoaDon> getDoanhThuThang(String thang, String nam) {
        EntityManager em = JPAUtil.getEntityManager();
        List<HoaDon> dsHD = new ArrayList<>();

        try {
            int year = Integer.parseInt(nam);
            int month = Integer.parseInt(thang);

            // Tạo mốc thời gian từ ngày đầu tháng đến đầu tháng sau
            LocalDateTime startOfMonth = LocalDate.of(year, month, 1).atStartOfDay();
            LocalDateTime startOfNextMonth = startOfMonth.plusMonths(1);

            String jpql = "SELECT h FROM HoaDon h " +
                    "WHERE h.thoiGianLapHD >= :startOfMonth AND h.thoiGianLapHD < :startOfNextMonth";

            dsHD = em.createQuery(jpql, HoaDon.class)
                    .setParameter("startOfMonth", Timestamp.valueOf(startOfMonth))
                    .setParameter("startOfNextMonth", Timestamp.valueOf(startOfNextMonth))
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsHD;
    }

    // Lấy doanh thu theo năm
    @Override public List<HoaDon> getDoanhThuNam(String nam) {
        EntityManager em = JPAUtil.getEntityManager();
        List<HoaDon> dsHD = new ArrayList<>();

        try {
            int year = Integer.parseInt(nam);

            LocalDateTime startOfYear = LocalDate.of(year, 1, 1).atStartOfDay();
            LocalDateTime startOfNextYear = startOfYear.plusYears(1);

            String jpql = "SELECT h FROM HoaDon h " +
                    "WHERE h.thoiGianLapHD >= :startOfYear " +
                    "AND h.thoiGianLapHD < :startOfNextYear";

            dsHD = em.createQuery(jpql, HoaDon.class)
                    .setParameter("startOfYear", Timestamp.valueOf(startOfYear))
                    .setParameter("startOfNextYear", Timestamp.valueOf(startOfNextYear))
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsHD;
    }

    @Override
    public List<Object[]> getDoanhThuTungThangNam(String nam) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Object[]> result = new ArrayList<>();

        try {
            int year = Integer.parseInt(nam);

            String jpql = "SELECT FUNCTION('MONTH', h.thoiGianLapHD), SUM(h.tongTien) " +
                    "FROM HoaDon h " +
                    "WHERE FUNCTION('YEAR', h.thoiGianLapHD) = :nam " +
                    "GROUP BY FUNCTION('MONTH', h.thoiGianLapHD) " +
                    "ORDER BY FUNCTION('MONTH', h.thoiGianLapHD)";

            result = em.createQuery(jpql, Object[].class)
                    .setParameter("nam", year)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result; // Mỗi phần tử là Object[]{tháng, tổng tiền}
    }

    public int soLuongHoaDonTrongNgay() {
        EntityManager em = JPAUtil.getEntityManager();
        int count = 0;
        try {
            // Bước 1: Lấy thời gian đầu ngày và cuối ngày bằng Date
            LocalDate today = LocalDate.now();
            Date startOfDay = java.sql.Timestamp.valueOf(today.atStartOfDay());
            Date endOfDay = java.sql.Timestamp.valueOf(today.plusDays(1).atStartOfDay());

            // Bước 2: Viết JPQL như cũ
            String jpql = "SELECT COUNT(h) FROM HoaDon h " +
                    "WHERE h.thoiGianLapHD >= :startOfDay AND h.thoiGianLapHD < :endOfDay";

            count = ((Long) em.createQuery(jpql)
                    .setParameter("startOfDay", startOfDay)
                    .setParameter("endOfDay", endOfDay)
                    .getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    public String taoMaHoaDonTheoNgay()  {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.format(dateFormatter);

        int soLuongHoaDon = soLuongHoaDonTrongNgay();
        return dateString + String.format("%05d", soLuongHoaDon + 1);
    }

    @Override
    public boolean insert(HoaDon hd) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;

        try {
            tx.begin();
            // Persist the new HoaDon
            em.persist(hd);
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
    public String getLatestID() {
        EntityManager em = JPAUtil.getEntityManager();
        String id = null;
        try {
            String jpql = "SELECT h.maHD FROM HoaDon h ORDER BY h.maHD DESC";

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
    public List<HoaDon> getListHoaDon() {
        EntityManager em = JPAUtil.getEntityManager();
        List<HoaDon> dataList = new ArrayList<>();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Bước 1: Lấy toàn bộ hóa đơn
            String jpql = "SELECT h FROM HoaDon h ORDER BY h.maHD";

            dataList = em.createQuery(jpql, HoaDon.class)
                    .getResultList();

            // Bước 2: Cập nhật tongTien nếu cần
            for (HoaDon hd : dataList) {
                double tongTien = hd.getTienPhong() + hd.getTienPhat() + hd.getTienDichVu() + hd.getTienSanPham() - hd.getTienKhuyenMai();
                hd.setTongTien(tongTien);
                em.merge(hd); // merge lại entity
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return dataList;
    }

    @Override
    public List<String> getNamHoaDon() {
        EntityManager em = JPAUtil.getEntityManager();
        List<String> list = new ArrayList<>();
        try {
            String jpql = "SELECT DISTINCT YEAR(h.thoiGianLapHD) FROM HoaDon h";
            List<Integer> years = em.createQuery(jpql, Integer.class).getResultList();
            for (Integer year : years) {
                list.add(year.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
