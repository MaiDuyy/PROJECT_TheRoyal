package dao;

import entity.LoaiPhong;

import java.rmi.Remote;

public interface LoaiPhongDAO extends GenericDAO<LoaiPhong, String> , Remote {
    // Lấy Loại Phòng theo mã loại
    LoaiPhong getLoaiPhongTheoMa(String maLoai);
}
