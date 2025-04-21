package dao;

import entity.CTHoaDon;
import entity.HoaDon;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
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

    ArrayList<String[]> getTOPSPNam(String nam);

    ArrayList<String[]>s getTopDichVuTheoNam(int nam);

    ArrayList<String[]> getTKSPNam(String nam);

    ArrayList<String[]> getTKDVNam(String nam);

    ArrayList<String[]> getTOPSPThang(String thang, String nam);

    ArrayList<String[]> getTOPDVThang(String thang, String nam);

    ArrayList<String[]> getTOPDVNgay(Date date);

    ArrayList<String[]> getTOPSPNgay(Date date);

    double getTongTienSPNgay(Date ngay);
}
