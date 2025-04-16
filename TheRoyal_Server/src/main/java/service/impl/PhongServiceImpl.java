package service.impl;

import dao.PhongDAO;
import entity.Phong;
import service.PhongService;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public class PhongServiceImpl extends GenericServiceImpl<Phong, String>  implements PhongService {
    private PhongDAO phongDAO ;

    public PhongServiceImpl(PhongDAO phongDAO) throws RemoteException {
        super(phongDAO);
        this.phongDAO = phongDAO;
    }



    @Override
    public List<Phong> getPhongTheoMaPhong(String maPhong) {
        return phongDAO.getPhongTheoMaPhong(maPhong);
    }

    @Override
    public Phong getPhongByMaPhong(String maPhong) {
        return phongDAO.getPhongByMaPhong(maPhong);
    }

    @Override
    public boolean updateTinhTrang(String maPhong, String trangThaiMoi) {
        return phongDAO.updateTinhTrang(maPhong, trangThaiMoi);
    }

    @Override
    public List<Phong> getPhongByTinhTrang(String tinhTrang) {
        return phongDAO.getPhongByTinhTrang(tinhTrang);
    }

    @Override
    public List<Phong> getPhongConLaiTheoNgayNhanPhong(LocalDate ngayNhan) {
        return phongDAO.getPhongConLaiTheoNgayNhanPhong(ngayNhan);
    }

    @Override
    public List<Phong> getPhongDaDat(LocalDate ngayNhan) {
        return phongDAO.getPhongDaDat(ngayNhan);
    }

    @Override
    public Phong getPhongByMaHoaDon(String maPhong) {
        return phongDAO.getPhongByMaHoaDon(maPhong);
    }

    @Override
    public int getSoPhongTheoTrangThai(String trangThai) {
        return phongDAO.getSoPhongTheoTrangThai(trangThai);
    }

    @Override
    public int getTongSoPhong() {
        return phongDAO.getTongSoPhong();
    }

    @Override
    public String getLatestID() throws RemoteException {
        return phongDAO.getLatestID();
    }

    @Override
    public boolean updateTinhTrangPhong(Phong phong, String trangThai) throws RemoteException {
        return phongDAO.updateTinhTrangPhong(phong, trangThai);
    }


}
