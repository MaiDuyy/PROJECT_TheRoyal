package dao;

import entity.CTHoaDon;

import java.sql.Date;
import java.util.List;

public interface CTHoaDonDAO extends GenericDAO<CTHoaDon, String> {
    List<CTHoaDon> getListCTHoaDonByMaHD(String maHD);

    String getLatestID();

    boolean checkIfCTHoaDonExists(CTHoaDon cthd);

    boolean updateSLSP(CTHoaDon ctHoaDon);

    boolean updateSLDV(CTHoaDon ctHoaDon);

    CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD);

    CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD);

    int getTongTienNam(int nam);

    int getTongTienThang(int thang, int nam);
}
