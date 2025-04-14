package service.impl;

import dao.KhachHangDAO;
import entity.KhachHang;
import service.KhachHangService;

import java.rmi.RemoteException;
import java.util.List;

public class KhachHangServiceImpl extends GenericServiceImpl<KhachHang, String> implements KhachHangService {
    private KhachHangDAO khachHangDAO;

    public KhachHangServiceImpl(KhachHangDAO khachHangDAO) throws RemoteException {
        super(khachHangDAO);
        this.khachHangDAO = khachHangDAO;
    }

    // Lấy Khách Hàng theo tên
    @Override 
    public List<KhachHang> getKhachHangTheoTen(String ten) throws RemoteException {
        return khachHangDAO.getKhachHangTheoTen(ten);
    }

    // Lấy Khách Hàng theo loại
    @Override 
    public List<KhachHang> getKhachHangTheoLoai(String loaiKH) throws RemoteException {
        return khachHangDAO.getKhachHangTheoLoai(loaiKH);
    }

    // Lấy Khách Hàng theo số điện thoại hoặc CCCD
    @Override 
    public KhachHang getKhachHangTheoSDTHoacCCCD(String sdtOrCCCD) throws RemoteException {
        return khachHangDAO.getKhachHangTheoSDTHoacCCCD(sdtOrCCCD);
    }

    // Lấy Khách Hàng theo mã
    @Override 
    public KhachHang getKhachHangTheoMa(String maKH) throws RemoteException {
        return khachHangDAO.getKhachHangTheoMa(maKH);
    }

    // Lấy mã Khách Hàng mới nhất (thực hiện theo cách tự sinh mã)
    @Override 
    public String getLatestID() throws RemoteException {
        return khachHangDAO.getLatestID();
    }
}
