package dao;

import dao.impl.TaiKhoanDAOImpl;
import entity.TaiKhoan;
import util.JPAUtil;

import java.rmi.Remote;
import java.util.List;

public interface TaiKhoanDAO  extends GenericDAO<TaiKhoan, String> , Remote {
    static TaiKhoanDAO getInstance() {
        return new TaiKhoanDAOImpl(JPAUtil.getEntityManager(), TaiKhoan.class);
    }

    List<TaiKhoan> getAllTaiKhoan();

    List<TaiKhoan> getTaiKhoanTheoMaTK(String maTK);

    List<TaiKhoan> getTaiKhoanTheoMaTKNhanVien(String maTK);

    TaiKhoan getTaiKhoanTheoMaTk(String maTK);

    TaiKhoan getTaiKhoanTheoMaNv(String maNV);

    boolean updateMatKhau(TaiKhoan tk);

    boolean delete(String maTK);

    boolean validateLogin(String userName, String password);

    boolean updatePass(String email, String password);

    boolean capnhatMK(String cccd, String matkhauMoi);

    boolean checkCCCD(String cccd);


}
