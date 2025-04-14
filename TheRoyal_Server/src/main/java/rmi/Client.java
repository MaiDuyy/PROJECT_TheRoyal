package rmi;

import service.LoaiPhongService;
import service.NhanVienService;
import service.PhongService;
import service.TaiKhoanService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws NamingException {
        Context context = new InitialContext();

        PhongService phongService = (PhongService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/phongService");
        TaiKhoanService taiKhoanService = (TaiKhoanService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/taiKhoanService") ;
//        DepartmentService departmentService = (DepartmentService) context.lookup("rmi://H51M15:9090/departmentService");
        NhanVienService nhanVienService = (NhanVienService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/nhanVienService");

        LoaiPhongService loaiPhongService = (LoaiPhongService) context.lookup("rmi://DESKTOP-Q4NO7E1:9090/loaiPhongService");

        try {
            loaiPhongService.getAll().forEach(st -> System.out.println(st.getMaLoai()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}
