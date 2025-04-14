package dao;

import entity.KhuyenMai;

import java.util.List;

public interface KhuyenMaiDAO extends GenericDAO<KhuyenMai, String> {
    // Lấy Khuyến Mãi theo mã
    KhuyenMai getKhuyenMaiTheoMa(String maUuDai);

    // Lấy danh sách Khuyến Mãi sau ngày cụ thể
    List<KhuyenMai> getKhuyenMaiSauNgay(java.sql.Date time);

    // Cập nhật số lượng Khuyến Mãi
    boolean updateSoLuong(String maKM);

    // Lấy mã Khuyến Mãi mới nhất
    String getLatestID();
}
