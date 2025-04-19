package dao;

import entity.HoaDon;

import java.time.LocalDate;
import java.util.List;

public interface HoaDonDAO extends GenericDAO<HoaDon, String> {
    // Lấy hóa đơn theo mã
    HoaDon getHoaDonTheoMa(String maHD);

    // Lấy danh sách hóa đơn theo mã
    List<HoaDon> getHoaDonTheoMaList(String maHD);

    // Lấy hóa đơn theo mã phòng
    HoaDon getHoaDonTheoPhong(String maPhong);

    // Lấy hóa đơn theo mã đặt phòng
    HoaDon getHoaDonTheoDonDatPhong(String maDDP);

    // Lấy tổng tiền sản phẩm và dịch vụ của hóa đơn
    double[] getTongTienSanPhamDichVu(String maHD);

    // Cập nhật tổng tiền sản phẩm và dịch vụ
    boolean updateTongTienHoaDon(HoaDon hoaDon, double tongTienSP, double tongTienDV);

    // Cập nhật trạng thái của hóa đơn
    boolean updateTinhTrang(String maHD, String trangThai);

    // Cập nhật mã khuyến mãi cho hóa đơn
    boolean updateKM(String maKM, String maHD);

    // Tính doanh thu theo ngày
    List<HoaDon> getDoanhThuNgay(LocalDate ngay);

    // Lấy số lượng hóa đơn trong ngày
    int getSoLuongHoaDonNgay(LocalDate ngay);

    // Lấy doanh thu theo tháng
    List<HoaDon> getDoanhThuThang(String thang, String nam);

    // Lấy doanh thu theo năm
    List<HoaDon> getDoanhThuNam(String nam);

    public String taoMaHoaDonTheoNgay();

    public boolean insert(HoaDon hd) ;

    public String getLatestID() ;
}
