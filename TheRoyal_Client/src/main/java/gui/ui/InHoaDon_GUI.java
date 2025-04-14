package gui.ui;

import entity.*;
import dao.CTHoaDonDAO;
import dao.DonDatPhongDAO;
import dao.HoaDonDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;

import static java.awt.print.Printable.NO_SUCH_PAGE;

public class InHoaDon_GUI implements Printable {

    private HoaDon hoaDon;
    private final SimpleDateFormat formatNgay = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss");
    
    private final DecimalFormat df = new DecimalFormat("#,##0");
    private ArrayList<CTHoaDonDetail> dsCT;
    private HoaDonDAO hoadondao;
    private CTHoaDonDAO cthoadondao;

    public InHoaDon_GUI(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
        this.hoadondao = new HoaDonDAO();
        this.cthoadondao = new CTHoaDonDAO();
        
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        LocalTime currentTime = LocalTime.now();
        Graphics2D g2 = (Graphics2D) g;
        g2.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        int y = 20;
        int yShift = 15; 
        NhanVien nhanVienDangNhap = LoginFrame.getNhanVienDangNhap();
        DonDatPhong ddp = DonDatPhongDAO.getInstance().getDonDatPhongTheoMaHD(hoaDon.getMaHD());
        g2.setFont(new Font("Monospaced", Font.PLAIN, 9));
        y += yShift + 45;

        g2.drawString("----------------------------------------------------", 12, y);
        y += yShift;
        g2.drawString("HOTEL TENTWO                      Ngày:" + formatNgay.format(hoaDon.getThoiGianLapHD()), 12, y);
        y += yShift;
        g2.drawString("TENTWO GÒ VẤP                     Gio:" +currentTime.getHour() +":"+ currentTime.getMinute() , 12, y);
        y += yShift;
        g2.drawString("DT:0365338606                     So HD:" + hoaDon.getMaHD(), 12, y);
        y += yShift ; 
        g2.drawString("Ngày đặt : "+formatNgay.format(ddp.getThoiGianDatPhong())+"             Ngày trả:" + formatNgay.format(ddp.getThoiGianTraPhong()), 12, y);
        y += yShift + 5; 
        g2.drawString("                  NV:" + nhanVienDangNhap.getTenNV(), 12, y);
        y += yShift + 10;
        g2.setFont(new Font("Monospaced", Font.BOLD, 9));
        g2.drawString("                     HOA DON                  ", 12, y);
        y += yShift + 10;
        g2.setFont(new Font("Monospaced", Font.PLAIN, 9));
        g2.drawString(String.format("%-20s %-6s %s %18s", "Mặt hàng", "SL", "Giá", "Thành tiền"), 12, y);
        y += yShift;
        g2.drawString("----------------------------------------------------", 12, y);
        y += yShift;

        dsCT = cthoadondao.getListSPDVByMaCTHD(hoaDon.getMaHD());
        for (CTHoaDonDetail chiTiet : dsCT) {
            String tenMatHangDV = chiTiet.getTenDV();
            double giaDV = chiTiet.getGiaDV();
            int soLuongDV = chiTiet.getSoLuongDV();
            double thanhTienDV = giaDV * soLuongDV;
            
            String tenMatHangSP = chiTiet.getTenSP();
            double giaSP = chiTiet.getGiaSP();
            int soLuongSP = chiTiet.getSoLuongSP();
            double thanhTienSP = giaSP * soLuongSP;

            if (tenMatHangDV != null && !tenMatHangDV.isEmpty()) {
                g2.drawString(String.format("%-20s %-6d %-10s %12s", tenMatHangDV, soLuongDV, df.format(giaDV), df.format(thanhTienDV)), 12, y);
                y += yShift;
            }
            
            if (tenMatHangSP != null && !tenMatHangSP.isEmpty()) {
                g2.drawString(String.format("%-20s %-6d %-10s %12s", tenMatHangSP, soLuongSP, df.format(giaSP), df.format(thanhTienSP)), 12, y);
                y += yShift;
            }
        }
        g2.drawString("----------------------------------------------------", 12, y);
        y += yShift;
        g2.drawString(String.format("TIỀN PHẠT: %35s", df.format(hoaDon.getTienPhat())), 12, y);
        y += yShift;
        g2.drawString(String.format("TIỀN KHUYẾN MÃI: %35s", df.format(hoaDon.getTienKhuyenMai())), 12, y);
        y += yShift;
        g2.drawString(String.format("TIỀN PHÒNG: %40s", df.format(hoaDon.getTienPhong())), 12, y);
        y += yShift;
        g2.drawString("----------------------------------------------------", 12, y);
        y += yShift;
        g2.drawString(String.format("TỔNG CỘNG: %40s", df.format(hoaDon.getTongTien())), 12, y);
        y += yShift;

        g2.drawString("----------------------------------------------------", 12, y);
        y += yShift;
        g2.drawString("            Cảm ơn quý khách hẹn gặp lại          ", 12, y);
        y += yShift;
        g2.drawString("----------------------------------------------------", 12, y);

        return PAGE_EXISTS;
    }

}
