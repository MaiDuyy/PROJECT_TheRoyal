package service.impl;

import dao.HoaDonDAO;
import entity.HoaDon;
import service.HoaDonService;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

public class HoaDonServiceImpl  extends GenericServiceImpl<HoaDon, String> implements HoaDonService {

    private HoaDonDAO hoaDonDAO ;

    public HoaDonServiceImpl(HoaDonDAO hoaDonDAO)  throws RemoteException {
        super(hoaDonDAO);
        this.hoaDonDAO = hoaDonDAO;
    }

    @Override public HoaDon getHoaDonTheoMa(String maHD) throws RemoteException {
        return hoaDonDAO.getHoaDonTheoMa(maHD);
    }

    // Lấy danh sách hóa đơn theo mã
    @Override public List<HoaDon> getHoaDonTheoMaList(String maHD) throws RemoteException {
        return hoaDonDAO.getHoaDonTheoMaList(maHD);
    }

    // Lấy hóa đơn theo mã phòng
    @Override public HoaDon getHoaDonTheoPhong(String maPhong)throws RemoteException  {
        return hoaDonDAO.getHoaDonTheoPhong(maPhong);
    }

    // Lấy hóa đơn theo mã đặt phòng
    @Override public HoaDon getHoaDonTheoDonDatPhong(String maDDP) throws RemoteException  {
        return hoaDonDAO.getHoaDonTheoDonDatPhong(maDDP);
    }

    // Lấy tổng tiền sản phẩm và dịch vụ của hóa đơn
    @Override public double[] getTongTienSanPhamDichVu(String maHD)throws RemoteException  {
        return hoaDonDAO.getTongTienSanPhamDichVu(maHD);
    }

    // Cập nhật tổng tiền sản phẩm và dịch vụ
    @Override public boolean updateTongTienHoaDon(HoaDon hoaDon, double tongTienSP, double tongTienDV)  throws RemoteException{
        return hoaDonDAO.updateTongTienHoaDon(hoaDon, tongTienSP,tongTienDV);
    }

    // Cập nhật trạng thái của hóa đơn
    @Override public boolean updateTinhTrang(String maHD, String trangThai ) throws RemoteException {
        return hoaDonDAO.updateTinhTrang(maHD, trangThai);
    }

    // Cập nhật mã khuyến mãi cho hóa đơn
    @Override public boolean updateKM(String maKM, String maHD) throws RemoteException {
        return hoaDonDAO.updateKM(maKM, maHD);
    }

    @Override
    public int getTongTienNgay(Date ngay) throws RemoteException {
        return hoaDonDAO.getTongTienNgay(ngay);
    }

    // Tính doanh thu theo ngày
    @Override public List<HoaDon> getDoanhThuNgay(Date ngay) throws RemoteException {
        return hoaDonDAO.getDoanhThuNgay(ngay);
    }

    // Lấy số lượng hóa đơn trong ngày
    @Override public int getSoLuongHoaDonNgay(Date ngay)  throws RemoteException{
        return hoaDonDAO.getSoLuongHoaDonNgay(ngay);
    }

    // Lấy doanh thu theo tháng
    @Override public List<HoaDon> getDoanhThuThang(String thang, String nam) throws RemoteException {
        return hoaDonDAO.getDoanhThuThang(thang, nam);
    }

    // Lấy doanh thu theo năm
    @Override public List<HoaDon> getDoanhThuNam(String nam)throws RemoteException {
        return hoaDonDAO.getDoanhThuNam(nam);
    }

    @Override
    public List<Object[]> getDoanhThuTungThangNam(String nam) throws RemoteException {
        return hoaDonDAO.getDoanhThuTungThangNam(nam);
    }

    @Override
    public String taoMaHoaDonTheoNgay() throws RemoteException {
        return hoaDonDAO.taoMaHoaDonTheoNgay();
    }

    @Override
    public boolean insert(HoaDon hd) throws RemoteException {
        return hoaDonDAO.insert(hd);
    }

    @Override
    public String getLatestID() throws RemoteException {
        return hoaDonDAO.getLatestID();
    }

    @Override
    public List<HoaDon> getListHoaDon() throws RemoteException {
        return hoaDonDAO.getListHoaDon();
    }

    @Override
    public List<String> getNamHoaDon() throws RemoteException{
        return hoaDonDAO.getNamHoaDon();
    }

    @Override
    public int getSoLuongHoaDonThang(String thang, String nam) {
        return hoaDonDAO.getSoLuongHoaDonThang(thang, nam);
    }

    @Override
    public int getSoLuongHoaDonNam(String nam) {
        return hoaDonDAO.getSoLuongHoaDonNam(nam);
    }

    @Override
    public int getTongTienNam(String nam) {
        return hoaDonDAO.getTongTienNam(nam);
    }

    @Override
    public int getTongTienThang(String thang, String nam) {
        return hoaDonDAO.getTongTienThang(thang,nam);
    }
}
