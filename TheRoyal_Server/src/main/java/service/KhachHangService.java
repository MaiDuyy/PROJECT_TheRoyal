package service;

import entity.KhachHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KhachHangService extends GenericService<KhachHang, String>  , Remote {
    // Lấy Khách Hàng theo tên
    List<KhachHang> getKhachHangTheoTen(String ten) throws RemoteException;

    // Lấy Khách Hàng theo loại
    List<KhachHang> getKhachHangTheoLoai(String loaiKH) throws RemoteException;

    // Lấy Khách Hàng theo số điện thoại hoặc CCCD
    KhachHang getKhachHangTheoSDTHoacCCCD(String sdtOrCCCD) throws RemoteException;

    // Lấy Khách Hàng theo mã
    KhachHang getKhachHangTheoMa(String maKH) throws RemoteException;

    // Lấy mã Khách Hàng mới nhất (thực hiện theo cách tự sinh mã)
    String getLatestID() throws RemoteException;
}
