package service;

import entity.TaiKhoan;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TaiKhoanService extends Remote , GenericService<TaiKhoan, String> {


    List<TaiKhoan> getTaiKhoanTheoMaTK(String maTK) throws RemoteException ;

    List<TaiKhoan> getTaiKhoanTheoMaTKNhanVien(String maTK) throws RemoteException;

    TaiKhoan getTaiKhoanTheoMaTk(String maTK) throws RemoteException;

    TaiKhoan getTaiKhoanTheoMaNv(String maNV)throws RemoteException;

    boolean updateMatKhau(TaiKhoan tk) throws RemoteException;

    boolean validateLogin(String userName, String password) throws RemoteException;

    boolean updatePass(String email, String password) throws RemoteException;

    boolean capnhatMK(String cccd, String matkhauMoi) throws RemoteException;

    boolean checkCCCD(String cccd)throws RemoteException;

    public boolean insert(TaiKhoan tk) throws RemoteException;
} 