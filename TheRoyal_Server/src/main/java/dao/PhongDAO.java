package dao;

import dao.impl.PhongDAOImpl;
import dao.impl.SanPhamDAOImpl;
import entity.Phong;
import entity.SanPham;
import util.JPAUtil;

import java.rmi.Remote;
import java.time.LocalDate;
import java.util.List;

public interface PhongDAO extends GenericDAO<Phong, String>, Remote {
    static PhongDAO getInstance() {
        return new PhongDAOImpl(JPAUtil.getEntityManager(), Phong.class);
    }

    List<Phong> getPhongTheoMaPhong(String maPhong);

    Phong getPhongByMaPhong(String maPhong);

    boolean updateTinhTrang(String maPhong, String trangThaiMoi);

    List<Phong> getPhongByTinhTrang(String tinhTrang);

    List<Phong> getPhongConLaiTheoNgayNhanPhong(LocalDate ngayNhan);

    List<Phong> getPhongDaDat(LocalDate ngayNhan);

    Phong getPhongByMaHoaDon(String maPhong);

    int getSoPhongTheoTrangThai(String trangThai);

    int getTongSoPhong();
}
