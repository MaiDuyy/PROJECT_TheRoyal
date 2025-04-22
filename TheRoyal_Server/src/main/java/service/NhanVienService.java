package service;

import entity.NhanVien;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhanVienService extends Remote, GenericService<NhanVien, String> {
    List<NhanVien> getListNhanVien() throws RemoteException;
    
    List<NhanVien> getNhanVienTheoMa(String maTK) throws RemoteException;
    
    List<NhanVien> getNhanVienTheoMa(String maNV, boolean isAdmin) throws RemoteException;
    
    NhanVien getNhanVienTheoMaNV(String maNV) throws RemoteException;
    
    String getLatestID() throws RemoteException;
    
    boolean capNhatTaiKhoanNhanVien(String maNV, String maTK) throws RemoteException;
    
    String getTaiKhoanCuaNhanVien(String maNV) throws RemoteException;

    public boolean insert(NhanVien nv)  throws RemoteException;
} 