package rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import service.*;

public class RMIClient {
    private static RMIClient instance;
    private Context context;
    private String rmiHost = "DESKTOP-Q4NO7E1";
    private int rmiPort = 9091;

    // Các dịch vụ RMI
    private PhongService phongService;
    private TaiKhoanService taiKhoanService;
    private NhanVienService nhanVienService;
    private LoaiPhongService loaiPhongService;
    private KhuyenMaiService khuyenMaiService;
    private KhachHangService khachHangService;
    private HoaDonService hoaDonService;
    private DonDatPhongService donDatPhongService;
    private DichVuService dichVuService;
    private CTHoaDonService ctHoaDonService;

    private SanPhamService sanPhamService;

    private RMIClient() {
        try {
            context = new InitialContext();
            connectServices();
        } catch (NamingException e) {
            System.err.println("Lỗi khởi tạo RMIClient: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized RMIClient getInstance() {
        if (instance == null) {
            instance = new RMIClient();
        }
        return instance;
    }

    private void connectServices() {
        try {
            phongService = (PhongService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/phongService");
            taiKhoanService = (TaiKhoanService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/taiKhoanService");
            nhanVienService = (NhanVienService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/nhanVienService");
            loaiPhongService = (LoaiPhongService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/loaiPhongService");
            khuyenMaiService = (KhuyenMaiService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/khuyenMaiService");
            khachHangService = (KhachHangService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/khachHangService");
            hoaDonService = (HoaDonService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/hoaDonService");
            donDatPhongService = (DonDatPhongService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/donDatPhongService");
            dichVuService = (DichVuService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/dichVuService");
            ctHoaDonService = (CTHoaDonService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/ctHoaDonService");
            sanPhamService = (SanPhamService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/sanPhamService");

            System.out.println("Đã kết nối thành công đến tất cả các dịch vụ RMI");
        } catch (NamingException e) {
            System.err.println("Lỗi kết nối đến dịch vụ RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setRmiHostPort(String host, int port) {
        this.rmiHost = host;
        this.rmiPort = port;
        connectServices();
    }

    public PhongService getPhongService() { return phongService; }
    public TaiKhoanService getTaiKhoanService() { return taiKhoanService; }
    public NhanVienService getNhanVienService() { return nhanVienService; }
    public LoaiPhongService getLoaiPhongService() { return loaiPhongService; }
    public KhuyenMaiService getKhuyenMaiService() { return khuyenMaiService; }
    public KhachHangService getKhachHangService() { return khachHangService; }
    public HoaDonService getHoaDonService() { return hoaDonService; }
    public DonDatPhongService getDonDatPhongService() { return donDatPhongService; }
    public DichVuService getDichVuService() { return dichVuService; }
    public CTHoaDonService getCtHoaDonService() { return ctHoaDonService; }

    public SanPhamService getSanPhamService() { return sanPhamService; }

    public boolean isConnected() {
        return phongService != null && taiKhoanService != null && nhanVienService != null &&
                loaiPhongService != null && khuyenMaiService != null && khachHangService != null &&
                hoaDonService != null && donDatPhongService != null &&
                dichVuService != null && ctHoaDonService != null && sanPhamService != null ;
    }

    public void reconnect() {
        connectServices();
    }

    public static void main(String[] args) {
        boolean b = getInstance().isConnected();
        System.out.println(b);
    }
}
