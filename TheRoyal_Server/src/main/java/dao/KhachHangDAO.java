package dao;

import entity.KhachHang;

import java.util.List;

public interface KhachHangDAO extends GenericDAO<KhachHang, String> {


    // Lấy Khách Hàng theo tên
    List<KhachHang> getKhachHangTheoTen(String ten);

    // Lấy Khách Hàng theo loại
    List<KhachHang> getKhachHangTheoLoai(String loaiKH);

    // Lấy Khách Hàng theo số điện thoại hoặc CCCD
    KhachHang getKhachHangTheoSDTHoacCCCD(String sdtOrCCCD);

    // Lấy Khách Hàng theo mã
    KhachHang getKhachHangTheoMa(String maKH);

    // Lấy mã Khách Hàng mới nhất (thực hiện theo cách tự sinh mã)
    String getLatestID();
}
