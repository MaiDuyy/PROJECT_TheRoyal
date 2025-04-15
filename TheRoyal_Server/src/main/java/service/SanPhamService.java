package service;

import entity.SanPham;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface SanPhamService extends GenericService<SanPham, String> {
    List<SanPham> getSanPhamTheoMa(String maSP) throws RemoteException;

    List<SanPham> getSanPhamTheoLoai(String tenSP) throws RemoteException;

    String generateNewId() throws RemoteException;

    String getLatestID() throws RemoteException;

    boolean xoaSanPham(SanPham spXoa) throws RemoteException;

    boolean suaSanPham(SanPham sp) throws RemoteException;

    boolean updateSLSP(SanPham sp, int sl) throws RemoteException;

    boolean SanPhamTonTai(SanPham sp) throws RemoteException;

    SanPham getSanPhamTheoMaHoacTen(String maOrTen) throws RemoteException;

    int getTongTienThang(String thang, String nam) throws RemoteException;

    List<String[]> getSPNgay(Date ngay) throws RemoteException;

    List<String[]> getMHThang(String thang, String nam) throws RemoteException;

    List<String[]> getMHNam(String nam) throws RemoteException;

    int getTongTienSPNam(String nam) throws RemoteException;
}
