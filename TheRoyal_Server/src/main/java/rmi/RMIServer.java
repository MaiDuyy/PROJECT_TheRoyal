package rmi;

import dao.LoaiPhongDAO;
import dao.NhanVienDAO;
import dao.PhongDAO;
import dao.TaiKhoanDAO;
import dao.impl.LoaiPhongDAOImpl;
import dao.impl.NhanVienDAOImpl;
import dao.impl.PhongDAOImpl;
import dao.impl.TaiKhoanDAOImpl;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;
import entity.TaiKhoan;
import service.LoaiPhongService;
import service.NhanVienService;
import service.PhongService;
import service.TaiKhoanService;
import service.impl.LoaiPhongServiceImpl;
import service.impl.NhanVienServiceImpl;
import service.impl.PhongServiceImpl;
import service.impl.TaiKhoanServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) throws Exception{

        Context context = new InitialContext();

        LocateRegistry.createRegistry(9090);



        PhongDAO phongDAO = new PhongDAOImpl(Phong.class);
        PhongService phongService =  new PhongServiceImpl(phongDAO) ;
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/phongService", phongService);


        TaiKhoanDAO taiKhoanDAO =  new TaiKhoanDAOImpl(TaiKhoan.class);
        TaiKhoanService taiKhoanService = new TaiKhoanServiceImpl(taiKhoanDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/taiKhoanService", taiKhoanService);


        NhanVienDAO nhanVienDAO =  new NhanVienDAOImpl(NhanVien.class) ;
        NhanVienService nhanVienService = new NhanVienServiceImpl(nhanVienDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/nhanVienService", nhanVienService);

        LoaiPhongDAO loaiPhongDAO = new LoaiPhongDAOImpl(LoaiPhong.class);
        LoaiPhongService loaiPhongService = new LoaiPhongServiceImpl(loaiPhongDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/loaiPhongService", loaiPhongService);

        System.out.println("RMI server is running...");

    }

}
