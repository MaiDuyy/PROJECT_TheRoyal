package dao;

import entity.NhanVien;
import entity.TaiKhoan;

import java.util.List;

public interface NhanVienDAO extends GenericDAO<NhanVien, String> {
    List<NhanVien> getListNhanVien();
    
    List<NhanVien> getNhanVienTheoMa(String maTK);
    
    List<NhanVien> getNhanVienTheoMa(String maNV, boolean isAdmin);
    
    NhanVien getNhanVienTheoMaNV(String maNV);
    
    String getLatestID();
    
    boolean capNhatTaiKhoanNhanVien(String maNV, String maTK);
    
    String getTaiKhoanCuaNhanVien(String maNV);
} 