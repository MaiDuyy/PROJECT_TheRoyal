package service.impl;

import dao.TaiKhoanDAO;
import entity.TaiKhoan;
import service.TaiKhoanService;

import java.rmi.RemoteException;
import java.util.List;

public class TaiKhoanServiceImpl extends   GenericServiceImpl<TaiKhoan, String>implements TaiKhoanService {
    private  TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanServiceImpl(TaiKhoanDAO taiKhoanDAO) throws RemoteException {
        super(taiKhoanDAO);
        this.taiKhoanDAO = taiKhoanDAO;
    }




    @Override
    public List<TaiKhoan> getTaiKhoanTheoMaTK(String maTK) {
        return taiKhoanDAO.getTaiKhoanTheoMaTK(maTK);
    }

    @Override
    public List<TaiKhoan> getTaiKhoanTheoMaTKNhanVien(String maTK) {
        return taiKhoanDAO.getTaiKhoanTheoMaTKNhanVien(maTK);
    }

    @Override
    public TaiKhoan getTaiKhoanTheoMaTk(String maTK) {
        return taiKhoanDAO.getTaiKhoanTheoMaTk(maTK);
    }

    @Override
    public TaiKhoan getTaiKhoanTheoMaNv(String maNV) {
        return taiKhoanDAO.getTaiKhoanTheoMaNv(maNV);
    }

    @Override
    public boolean updateMatKhau(TaiKhoan tk) {
        return taiKhoanDAO.updateMatKhau(tk);
    }



    @Override
    public boolean validateLogin(String userName, String password) {
        return taiKhoanDAO.validateLogin(userName, password);
    }

    @Override
    public boolean updatePass(String email, String password) {
        return taiKhoanDAO.updatePass(email,password);
    }

    @Override
    public boolean capnhatMK(String cccd, String matkhauMoi) {
        return taiKhoanDAO.capnhatMK(cccd, matkhauMoi);
    }

    @Override
    public boolean checkCCCD(String cccd) {
        return taiKhoanDAO.checkCCCD(cccd);
    }
}