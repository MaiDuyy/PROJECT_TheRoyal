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
        TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery(
                "SELECT h FROM HoaDon h JOIN h.phong p WHERE p.maPhong = :maPhong AND p.trangThai = 'Đang ở' ORDER BY h.maHD DESC", HoaDon.class);
        query.setParameter("maPhong", maPhong);
        return query.getResultStream().findFirst().orElse(null);
    }

    // Lấy hóa đơn theo mã đặt phòng
    @Override public HoaDon getHoaDonTheoDonDatPhong(String maDDP) {
        TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery(
                "SELECT h FROM HoaDon h JOIN h.donDatPhong d WHERE d.maDDP = :maDDP", HoaDon.class);
        query.setParameter("maDDP", maDDP);
        return query.getResultStream().findFirst().orElse(null);
    }

      



    // Lấy tổng tiền sản phẩm và dịch vụ của hóa đơn
    @Override public double[] getTongTienSanPhamDichVu(String maHD) {
        TypedQuery<Object[]> query = JPAUtil.getEntityManager().createQuery(
                "SELECT SUM(CT.tongTienSP), SUM(CT.tongTienDV) FROM HoaDon h JOIN h.ctHoaDon CT WHERE h.maHD = :maHD", Object[].class);
        query.setParameter("maHD", maHD);
        Object[] result = query.getResultStream().findFirst().orElse(new Object[]{0.0, 0.0});
        return new double[]{(double) result[0], (double) result[1]};
    }

    // Cập nhật tổng tiền sản phẩm và dịch vụ
    @Override public boolean updateTongTienHoaDon(HoaDon hoaDon, double tongTienSP, double tongTienDV) {
        try {
            EntityTransaction transaction = JPAUtil.getEntityManager().getTransaction();
            transaction.begin();
            TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery("UPDATE HoaDon h SET h.tienSanPham = :tienSanPham, h.tienDichVu = :tienDichVu WHERE h.maHD = :maHD", HoaDon.class);
            query.setParameter("tienSanPham", tongTienSP);
            query.setParameter("tienDichVu", tongTienDV);
            query.setParameter("maHD", hoaDon.getMaHD());
            int rowsUpdated = query.executeUpdate();
            transaction.commit();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật trạng thái của hóa đơn
    @Override public boolean updateTinhTrang(String maHD, String trangThai) {
        try {
            EntityTransaction transaction = JPAUtil.getEntityManager().getTransaction();
            transaction.begin();
            TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery("UPDATE HoaDon h SET h.trangThai = :trangThai WHERE h.maHD = :maHD", HoaDon.class);
            query.setParameter("trangThai", trangThai);
            query.setParameter("maHD", maHD);
            int rowsUpdated = query.executeUpdate();
            transaction.commit();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật mã khuyến mãi cho hóa đơn
    @Override public boolean updateKM(String maKM, String maHD) {
        try {
            EntityTransaction transaction = JPAUtil.getEntityManager().getTransaction();
            transaction.begin();
            TypedQuery<HoaDon> query = JPAUtil.getEntityManager().createQuery("UPDATE HoaDon h SET h.khuyenMai.maKM = :maKM WHERE h.maHD = :maHD", HoaDon.class);
            query.setParameter("maKM", maKM);
            query.setParameter("maHD", maHD);
            int rowsUpdated = query.executeUpdate();
            transaction.commit();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1);

            String jpql = "SELECT COUNT(h) FROM HoaDon h " +
                    "WHERE h.thoiGianLapHD >= :startOfDay AND h.thoiGianLapHD < :endOfDay";

            count = ((Long) em.createQuery(jpql)
                    .setParameter("startOfDay", startOfDay)
                    .setParameter("endOfDay", endOfDay)
                    .getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
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
}
