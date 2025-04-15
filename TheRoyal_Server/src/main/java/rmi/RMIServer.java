package rmi;

import dao.*;
import dao.impl.*;
import entity.*;
import service.*;
import service.impl.*;

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

        KhuyenMaiDAO  khuyenMaiDAO = new KhuyenMaiDAOImpl(KhuyenMai.class);
        KhuyenMaiService khuyenMaiService = new KhuyenMaiServiceImpl(khuyenMaiDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/khuyenMaiService", khuyenMaiService);

        KhachHangDAO khachHangDAO = new KhachHangDAOImpl(KhachHang.class);
        KhachHangService khachHangService = new KhachHangServiceImpl(khachHangDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/khachHangService", khachHangService);

        HoaDonDAO hoaDonDAO =  new HoaDonDAOImpl(HoaDon.class);
        HoaDonService hoaDonService = new HoaDonServiceImpl(hoaDonDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/hoaDonService", hoaDonService);

        DonDatPhongDAO donDatPhongDAO = new DonDatPhongDAOImpl(DonDatPhong.class);
        DonDatPhongService  donDatPhongService = new DonDatPhongServiceImpl(donDatPhongDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/donDatPhongService", donDatPhongService);

        SanPhamDAO sanPhamDAO = new SanPhamDAOImpl(SanPham.class);
        SanPhamService sanPhamService = new SanPhamServiceImpl(sanPhamDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/sanPhamService", sanPhamService);


        DichVuDAO dichVuDAO = new DichVuDAOImpl(DichVu.class);
        DichVuService dichVuService = new DichVuServiceImpl(dichVuDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/dichVuService", dichVuService);


        CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAOImpl(CTHoaDon.class);
        CTHoaDonService ctHoaDonService =  new CTHoaDonServiceImpl(ctHoaDonDAO);
        context.bind("rmi://DESKTOP-Q4NO7E1:9090/ctHoaDonService", ctHoaDonService);



        System.out.println("RMI server is running...");

    }

}
