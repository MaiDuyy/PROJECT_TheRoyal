package service;

import entity.LoaiPhong;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoaiPhongService extends GenericService<LoaiPhong, String>, Remote {
    LoaiPhong getLoaiPhongTheoMa(String maLoai) throws RemoteException;
}
