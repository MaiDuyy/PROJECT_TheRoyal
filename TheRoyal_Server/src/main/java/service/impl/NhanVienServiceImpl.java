package service.impl;

import dao.NhanVienDAO;
import entity.NhanVien;
import service.NhanVienService;

import java.rmi.RemoteException;
import java.util.List;

public class NhanVienServiceImpl extends GenericServiceImpl<NhanVien, String> implements NhanVienService {
    private NhanVienDAO nhanVienDAO;

    public NhanVienServiceImpl(NhanVienDAO nhanVienDAO) throws RemoteException {
        super(nhanVienDAO);
        this.nhanVienDAO = nhanVienDAO;
    }

    @Override
    public List<NhanVien> getListNhanVien() throws RemoteException {
        return nhanVienDAO.getListNhanVien();
    }

    @Override
    public List<NhanVien> getNhanVienTheoMa(String maTK) throws RemoteException {
        return nhanVienDAO.getNhanVienTheoMa(maTK);
    }

    @Override
    public List<NhanVien> getNhanVienTheoMa(String maNV, boolean isAdmin) throws RemoteException {
        return nhanVienDAO.getNhanVienTheoMa(maNV, isAdmin);
    }

    @Override
    public NhanVien getNhanVienTheoMaNV(String maNV) throws RemoteException {
        return nhanVienDAO.getNhanVienTheoMaNV(maNV);
    }

    @Override
    public String getLatestID() throws RemoteException {
        return nhanVienDAO.getLatestID();
    }

    @Override
    public boolean capNhatTaiKhoanNhanVien(String maNV, String maTK) throws RemoteException {
        return nhanVienDAO.capNhatTaiKhoanNhanVien(maNV, maTK);
    }

    @Override
    public String getTaiKhoanCuaNhanVien(String maNV) throws RemoteException {
        return nhanVienDAO.getTaiKhoanCuaNhanVien(maNV);
    }

    @Override
    public boolean insert(NhanVien nv) throws RemoteException {
        return nhanVienDAO.insert(nv);
    }
} 