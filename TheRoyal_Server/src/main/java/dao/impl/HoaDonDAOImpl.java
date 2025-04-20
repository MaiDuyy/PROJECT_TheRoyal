package dao.impl;

import dao.HoaDonDAO;
import entity.HoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        } finally {
            em.close();
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
        } finally {
            em.close(); // RẤT QUAN TRỌNG
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
        } finally {
            em.close();
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
        } finally {
            em.close();
        }

        return updated;
    }

    // Tính doanh thu theo ngày
    @Override public List<HoaDon> getDoanhThuNgay(LocalDate ngay) {
        TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery(
                "SELECT h FROM HoaDon h WHERE FUNCTION('DATE', h.thoiGianLapHD) = :ngay", HoaDon.class);
        query.setParameter("ngay", ngay);
        return query.getResultList();
    }

    // Lấy số lượng hóa đơn trong ngày
    @Override public int getSoLuongHoaDonNgay(LocalDate ngay) {
        TypedQuery<Long> query = JPAUtil.getEntityManager().createQuery("SELECT COUNT(h) FROM HoaDon h WHERE FUNCTION('DATE', h.thoiGianLapHD) = :ngay", Long.class);
        query.setParameter("ngay", ngay);
        return query.getSingleResult().intValue();
    }

    // Lấy doanh thu theo tháng
    @Override public List<HoaDon> getDoanhThuThang(String thang, String nam) {
        TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery(
                "SELECT h FROM HoaDon h WHERE FUNCTION('MONTH', h.thoiGianLapHD) = :thang AND FUNCTION('YEAR', h.thoiGianLapHD) = :nam", HoaDon.class);
        query.setParameter("thang", thang);
        query.setParameter("nam", nam);
        return query.getResultList();
    }

    // Lấy doanh thu theo năm
    @Override public List<HoaDon> getDoanhThuNam(String nam) {
        TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery(
                "SELECT h FROM HoaDon h WHERE FUNCTION('YEAR', h.thoiGianLapHD) = :nam", HoaDon.class);
        query.setParameter("nam", nam);
        return query.getResultList();
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
        } finally {
            em.close(); // RẤT QUAN TRỌNG
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
}
