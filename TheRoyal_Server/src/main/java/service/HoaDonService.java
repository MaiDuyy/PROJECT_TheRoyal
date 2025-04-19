package service;

import entity.HoaDon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface HoaDonService extends GenericService<HoaDon, String> , Remote {
    HoaDon getHoaDonTheoMa(String maHD) throws RemoteException;

    // Lấy danh sách hóa đơn theo mã
    List<HoaDon> getHoaDonTheoMaList(String maHD) throws RemoteException;

    // Lấy hóa đơn theo mã phòng
    HoaDon getHoaDonTheoPhong(String maPhong) throws RemoteException;

    // Lấy hóa đơn theo mã đặt phòng
    HoaDon getHoaDonTheoDonDatPhong(String maDDP) throws RemoteException;

    // Lấy tổng tiền sản phẩm và dịch vụ của hóa đơn
    double[] getTongTienSanPhamDichVu(String maHD) throws RemoteException;

    // Cập nhật tổng tiền sản phẩm và dịch vụ
    boolean updateTongTienHoaDon(HoaDon hoaDon, double tongTienSP, double tongTienDV) throws RemoteException;

    // Cập nhật trạng thái của hóa đơn
    boolean updateTinhTrang(String maHD, String trangThai) throws RemoteException;

    // Cập nhật mã khuyến mãi cho hóa đơn
    boolean updateKM(String maKM, String maHD) throws RemoteException;

    // Tính doanh thu theo ngày
    List<HoaDon> getDoanhThuNgay(LocalDate ngay) throws RemoteException;

    // Lấy số lượng hóa đơn trong ngày
    int getSoLuongHoaDonNgay(LocalDate ngay) throws RemoteException;

    // Lấy doanh thu theo tháng
    List<HoaDon> getDoanhThuThang(String thang, String nam) throws RemoteException;

    // Lấy doanh thu theo năm
    List<HoaDon> getDoanhThuNam(String nam) throws RemoteException;

    public String taoMaHoaDonTheoNgay() throws  RemoteException;

    public boolean insert(HoaDon hd) throws  RemoteException;

    public String getLatestID() throws  RemoteException;
}
