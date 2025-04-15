package service;

import dao.GenericDAO;
import entity.CTHoaDon;

import java.io.Serializable;
import java.rmi.Remote;
import java.sql.Date;
import java.util.List;

public interface CTHoaDonService extends Remote, GenericDAO<CTHoaDon, String> {
    //Lấy danh sách khuyến mãi theo mã hoá đơn
    List<CTHoaDon> getListCTHoaDonByMaHD(String maHD);

    //Lấy mã CTHD mới nhất
    String getLatestID();

    //Check hoá đơn liệu có tồn tại
    boolean checkIfCTHoaDonExists(CTHoaDon cthd);

    //Cập nhật số lượng SP
    boolean updateSLSP(CTHoaDon ctHoaDon);

    //Cập nhật số lượng DV
    boolean updateSLDV(CTHoaDon ctHoaDon);

    //Lấy CTHD từ mã Dịch vụ
    CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD);

    //Lấy CTHD từ mã Sản phẩm
    CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD);

    //Tính tổng tiền năm
    double getTongTienNam(int nam);

    //Tính tổng tiền tháng
    double getTongTienThang(int thang, int nam);

    //Tính tổng tiền ngày
    double getTongDVTienNgay(Date ngay);
}
