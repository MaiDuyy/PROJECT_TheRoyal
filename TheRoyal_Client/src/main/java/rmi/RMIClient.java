package rmi;

import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import service.NhanVienService;
import service.PhongService;
import service.TaiKhoanService;

/**
 * Lớp RMIClient quản lý kết nối đến các dịch vụ RMI
 * Sử dụng mẫu thiết kế Singleton để đảm bảo chỉ có một instance của RMIClient
 */
public class RMIClient {
    private static RMIClient instance;
    private Context context;
    private String rmiHost = "DESKTOP-Q4NO7E1";
    private int rmiPort = 9090;

    // Các dịch vụ RMI
    private PhongService phongService;
    private TaiKhoanService taiKhoanService;
    private NhanVienService nhanVienService;

    /**
     * Constructor riêng tư để thực hiện mẫu Singleton
     */
    private RMIClient() {
        try {
            // Khởi tạo context
            context = new InitialContext();

            // Kết nối đến các dịch vụ
            connectServices();
        } catch (NamingException e) {
            System.err.println("Lỗi khởi tạo RMIClient: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Phương thức để lấy instance của RMIClient (Singleton)
     * @return instance của RMIClient
     */
    public static synchronized RMIClient getInstance() {
        if (instance == null) {
            instance = new RMIClient();
        }
        return instance;
    }

    /**
     * Kết nối đến các dịch vụ RMI
     */
    private void connectServices() {
        try {
            // Kết nối đến PhongService
            phongService = (PhongService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/phongService");

            // Kết nối đến TaiKhoanService
            taiKhoanService = (TaiKhoanService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/taiKhoanService");

            // Kết nối đến NhanVienService
            nhanVienService = (NhanVienService) context.lookup("rmi://" + rmiHost + ":" + rmiPort + "/nhanVienService");

            System.out.println("Đã kết nối thành công đến các dịch vụ RMI");
        } catch (NamingException e) {
            System.err.println("Lỗi kết nối đến dịch vụ RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Thiết lập host và port cho RMI
     * @param host Tên host
     * @param port Số port
     */
    public void setRmiHostPort(String host, int port) {
        this.rmiHost = host;
        this.rmiPort = port;
        // Kết nối lại các dịch vụ với host và port mới
        connectServices();
    }

    /**
     * Lấy dịch vụ PhongService
     * @return PhongService
     */
    public PhongService getPhongService() {
        return phongService;
    }

    /**
     * Lấy dịch vụ TaiKhoanService
     * @return TaiKhoanService
     */
    public TaiKhoanService getTaiKhoanService() {
        return taiKhoanService;
    }

    /**
     * Lấy dịch vụ NhanVienService
     * @return NhanVienService
     */
    public NhanVienService getNhanVienService() {
        return nhanVienService;
    }

    /**
     * Kiểm tra kết nối đến các dịch vụ
     * @return true nếu tất cả các dịch vụ đều đã kết nối, false nếu không
     */
    public boolean isConnected() {
        return phongService != null && taiKhoanService != null && nhanVienService != null;
    }

    /**
     * Kết nối lại đến các dịch vụ
     */
    public void reconnect() {
        connectServices();
    }
}
