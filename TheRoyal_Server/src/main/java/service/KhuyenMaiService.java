package service;

import entity.KhuyenMai;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KhuyenMaiService extends GenericService<KhuyenMai, String>, Remote {
    KhuyenMai getKhuyenMaiTheoMa(String maUuDai) throws  RemoteException;

    // Lấy danh sách Khuyến Mãi sau ngày cụ thể
    List<KhuyenMai> getKhuyenMaiSauNgay(java.sql.Date time) throws  RemoteException;

    // Cập nhật số lượng Khuyến Mãi
    boolean updateSoLuong(String maKM) throws  RemoteException;

    // Lấy mã Khuyến Mãi mới nhất
    String getLatestID() throws  RemoteException;
}
