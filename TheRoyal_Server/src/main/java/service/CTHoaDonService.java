package service;

import entity.CTHoaDon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.List;

public interface CTHoaDonService extends GenericService<CTHoaDon, String> , Remote {
    List<CTHoaDon> getListCTHoaDonByMaHD(String maHD) throws RemoteException;

    String getLatestID() throws RemoteException;

    boolean checkIfCTHoaDonExists(CTHoaDon cthd) throws RemoteException;

    boolean updateSLSP(CTHoaDon ctHoaDon) throws RemoteException;

    boolean updateSLDV(CTHoaDon ctHoaDon) throws RemoteException;

    CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD) throws RemoteException;

    CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD) throws RemoteException;

    double getTongTienNam(int nam) throws RemoteException;

    double getTongTienThang(int thang, int nam) throws RemoteException;

    double getTongDVTienNgay(Date ngay) throws RemoteException;

    public boolean insert(CTHoaDon ctHoaDon) throws RemoteException;

    List<String[]> getTopDichVuTheoNam(int nam) throws RemoteException;


    List<String[]> getTOPSPNam(String nam) throws Exception;

    List<String[]> getTKDVNam(String nam) throws RemoteException;

    List<String[]> getTKSPNam(String nam) throws RemoteException;

    List<String[]> getTOPDVThang(String thang, String nam) throws RemoteException;

    List<String[]> getTOPSPThang(String thang, String nam) throws RemoteException;

    List<String[]> getTOPDVNgay(Date date) throws RemoteException;

    List<String[]> getTOPSPNgay(Date date) throws RemoteException;

    double getTongTienSPNgay(Date ngay) throws RemoteException;

    List<String[]> getTOPDVNam(String nam) throws RemoteException;

}
