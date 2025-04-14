package rmi;

import dao.KhuyenMaiDAO;
import service.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws NamingException, RemoteException {
        Context context = new InitialContext();

        PhongService phongService = (PhongService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/phongService");
        TaiKhoanService taiKhoanService = (TaiKhoanService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/taiKhoanService") ;
//        DepartmentService departmentService = (DepartmentService) context.lookup("rmi://H51M15:9090/departmentService");
        NhanVienService nhanVienService = (NhanVienService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/nhanVienService");

        LoaiPhongService loaiPhongService = (LoaiPhongService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/loaiPhongService");

        KhuyenMaiService  khuyenMaiService = (KhuyenMaiService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/khuyenMaiService");

        KhachHangService khachHangService = (KhachHangService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/khachHangService");

        HoaDonService hoaDonService = (HoaDonService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/hoaDonService");


        hoaDonService.getAll().forEach(st -> System.out.println(st.getMaHD()));

    }
}
