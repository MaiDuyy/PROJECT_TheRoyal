package dao;

import entity.CTHoaDon;
import entity.HoaDon;

import java.sql.Array;
import java.sql.Date;
import java.util.List;
import java.util.List;

public interface CTHoaDonDAO extends GenericDAO<CTHoaDon, String> {
    List<CTHoaDon> getListCTHoaDonByMaHD(String maHD);

    String getLatestID();

    boolean checkIfCTHoaDonExists(CTHoaDon cthd);

    boolean updateSLSP(CTHoaDon ctHoaDon);

    boolean updateSLDV(CTHoaDon ctHoaDon);

    CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD);

    CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD);

    double getTongTienNam(int nam);

    double getTongTienThang(int thang, int nam);

    double getTongDVTienNgay(Date ngay);

    public boolean insert(CTHoaDon ctHoaDon);

    List<String[]> getTOPSPNam(String nam);

    List<String[]> getTopDichVuTheoNam(int nam);

    List<String[]> getTKSPNam(String nam);

    List<String[]> getTKDVNam(String nam);

    List<String[]> getTOPSPThang(String thang, String nam);

    List<String[]> getTOPDVThang(String thang, String nam);

    List<String[]> getTOPDVNgay(Date date);

    List<String[]> getTOPSPNgay(Date date);

    double getTongTienSPNgay(Date ngay);

    List<String[]> getTOPDVNam(String nam);
}
