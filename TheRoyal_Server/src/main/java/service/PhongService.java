package service;


import entity.Phong;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public interface PhongService  extends Remote  ,GenericService<Phong, String>  {


    List<Phong> getPhongTheoMaPhong(String maPhong) throws RemoteException;

    Phong getPhongByMaPhong(String maPhong) throws RemoteException;

    boolean updateTinhTrang(String maPhong, String trangThaiMoi) throws RemoteException;

    List<Phong> getPhongByTinhTrang(String tinhTrang) throws RemoteException;

    List<Phong> getPhongConLaiTheoNgayNhanPhong(LocalDate ngayNhan) throws RemoteException;

    List<Phong> getPhongDaDat(LocalDate ngayNhan) throws RemoteException;

    Phong getPhongByMaHoaDon(String maPhong) throws RemoteException;

    int getSoPhongTheoTrangThai(String trangThai)throws RemoteException;

    int getTongSoPhong() throws RemoteException;

    public String getLatestID() throws RemoteException;

    public boolean updateTinhTrangPhong(Phong phong, String trangThai) throws  RemoteException;
}
