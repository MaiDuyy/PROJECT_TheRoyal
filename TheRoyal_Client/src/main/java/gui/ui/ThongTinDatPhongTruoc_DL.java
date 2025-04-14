package gui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import dao.DonDatPhongDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.PhongDAO;
import entity.DonDatPhong;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;
import entity.Phong;
import formatdate.FormatDate;

public class ThongTinDatPhongTruoc_DL extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JButton btn_NhanPhong;
	public JButton btn_HuyDP;
	private JPanel pn_p_bottom;
	private DonDatPhongDAO dondatphongdao;
	private PhongDAO phongdao;
	private DonDatPhong ddp;
	private Phong phong;
	private ArrayList<DonDatPhong> dsDDP;
	private KhachHang khachhang;
	private KhachHangDAO khachhangdao;
	private DanhSachPhong_GUI danhSachPhong;


	public ThongTinDatPhongTruoc_DL( Phong phong , DanhSachPhong_GUI danhSachPhong  , Frame owner , boolean modal ) {
		super(owner , modal);
		dondatphongdao = new DonDatPhongDAO();
		phongdao = new PhongDAO();
		khachhangdao = new KhachHangDAO();
		this.phong = phong;
		this.danhSachPhong = danhSachPhong;
		// Setting up the dialog
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Thông tin phòng đặt trước");
		getContentPane().setLayout(null);
		setBackground(Color.white);
		setSize(412, 362);
		setLocationRelativeTo(this);
		showPhongDatTruoc( phong);

	}

	private void showPhongDatTruoc(Phong phong) {
	    String maPhongFromList = phong.getMaPhong();
	    	ddp = dondatphongdao.getDonDatPhongTheoMaTraPhong(maPhongFromList);

	    if (ddp == null) {
	        JOptionPane.showMessageDialog(null, "Không tìm thấy đơn đặt phòng cho phòng này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    String maDDP = ddp.getMaDDP();
	    String maKH = ddp.getKhachHang() != null ? ddp.getKhachHang().getMaKH() : null;

	    if (maKH == null) {
	        JOptionPane.showMessageDialog(null, "Khách hàng không hợp lệ hoặc không có thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    dsDDP = dondatphongdao.timDonDatPhong(maDDP, maKH, maPhongFromList);

	    ddp.setPhong(phong);

	    JPanel panel = new JPanel();
	    panel.setBorder(BorderFactory.createTitledBorder(
	        BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
	        "Thông tin phòng đăt trước",
	        TitledBorder.DEFAULT_JUSTIFICATION,
	        TitledBorder.DEFAULT_POSITION,
	        new Font("Segoe UI", Font.PLAIN, 12),
	        new Color(246, 167, 193)
	    ));
	    panel.setBounds(21, 10, 359, 232);
	    getContentPane().add(panel);
	    panel.setBackground(Color.white);
	    panel.setLayout(null);
	    
	    if (!dsDDP.isEmpty()) {
	    JLabel lblRoomInfo_1 = new JLabel("Phòng: " + phong.getMaPhong());
	    lblRoomInfo_1.setFont(new Font("Arial", Font.BOLD, 13));
	    lblRoomInfo_1.setBounds(84, 7, 161, 38);
	    panel.add(lblRoomInfo_1);

	    JLabel lblRoomInfo_1_1 = new JLabel("Tên khách hàng: " + khachhangdao.getKhachHangTheoMa(maKH).getTenKH());
	    lblRoomInfo_1_1.setFont(new Font("Arial", Font.BOLD, 13));
	    lblRoomInfo_1_1.setBounds(84, 52, 190, 38);
	    panel.add(lblRoomInfo_1_1);

	    JLabel lblRoomInfo_1_2 = new JLabel("Số điện thoại: " + khachhangdao.getKhachHangTheoMa(maKH).getsDT());
	    lblRoomInfo_1_2.setFont(new Font("Arial", Font.BOLD, 13));
	    lblRoomInfo_1_2.setBounds(84, 97, 190, 38);
	    panel.add(lblRoomInfo_1_2);

	    JLabel lblRoomInfo_1_2_1 = new JLabel("Ngày đặt: " + ddp.getThoiGianDatPhong());
	    lblRoomInfo_1_2_1.setFont(new Font("Arial", Font.BOLD, 13));
	    lblRoomInfo_1_2_1.setBounds(84, 142, 149, 38);
	    panel.add(lblRoomInfo_1_2_1);

	    JLabel lblRoomInfo_1_2_3 = new JLabel("Ngày nhận: " + ddp.getThoiGianNhanPhong());
	    lblRoomInfo_1_2_3.setFont(new Font("Arial", Font.BOLD, 13));
	    lblRoomInfo_1_2_3.setBounds(84, 187, 149, 38);
	    panel.add(lblRoomInfo_1_2_3);
	    }
	    btn_NhanPhong = new JButton("Nhận phòng");
	    btn_NhanPhong.setFont(new Font("Tahoma", Font.BOLD, 10));
	    btn_NhanPhong.setBounds(72, 21, 69, 23);
	    btn_NhanPhong.setBackground(Color.ORANGE);
	    btn_NhanPhong.setForeground(Color.WHITE);
	    btn_NhanPhong.setOpaque(true);
	    btn_NhanPhong.setBorder(null);

	    btn_HuyDP = new JButton("Hủy");
	    btn_HuyDP.setFont(new Font("Tahoma", Font.BOLD, 10));
	    btn_HuyDP.setBounds(168, 21, 69, 23);
	    btn_HuyDP.setBackground(Color.RED);
	    btn_HuyDP.setForeground(Color.WHITE);
	    btn_HuyDP.setOpaque(true);
	    btn_HuyDP.setBorder(null);

	    JPanel pn_p_c = new JPanel();
	    pn_p_c.setLocation(21, 233);
	    pn_p_c.setSize(359, 69);
	    pn_p_c.setBackground(Color.white);
	    getContentPane().add(pn_p_c);
	    pn_p_c.setLayout(null);

	    pn_p_bottom = new JPanel();
	    pn_p_bottom.setBounds(10, 5, 339, 54);
	    pn_p_bottom.setBackground(Color.white);
	    pn_p_c.add(pn_p_bottom);
	    pn_p_bottom.setLayout(null);
	    DonDatPhong ddp = dondatphongdao.getDonDatPhongTheoPhongVaTrangThai(phong.getMaPhong(),"Đặt trước");

	    if (ddp != null) {
	        pn_p_bottom.add(btn_HuyDP);
	        pn_p_bottom.add(btn_NhanPhong);
	    }

	    btn_NhanPhong.addActionListener(this);
	    btn_HuyDP.addActionListener(this);
	}

	public void nhanPhong (Phong phong) {
		String maPhong = phong.getMaPhong();
		DonDatPhong ddp = dondatphongdao.getDonDatPhongTheoMaPhong(maPhong);

		Date ngayhientai = new Date(System.currentTimeMillis());
		String dateNhan = FormatDate.formatDate(ddp.getThoiGianNhanPhong());
		String dateht = FormatDate.formatDate(ngayhientai);

		Calendar calendar = Calendar.getInstance();
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currentMinute = calendar.get(Calendar.MINUTE);

		Calendar ngaymai = Calendar.getInstance();
		ngaymai.add(Calendar.DAY_OF_MONTH, 1);
		Date ngayMai = new Date(ngaymai.getTimeInMillis());
		String dateNgayMai = FormatDate.formatDate(ngayMai);

		if (!dateht.equals(dateNhan) && !dateNhan.equals(dateNgayMai)) {
		    JOptionPane.showMessageDialog(this, "Chỉ được nhận phòng trong ngày hôm nay hoặc ngày mai");
		    dispose();
		} else if (dateht.equals(dateNhan) && currentHour < 12) {
		    JOptionPane.showMessageDialog(this, "Chỉ được nhận phòng từ 12 giờ trưa trở đi");
		    dispose();
		} else {
		      NhanVien nhanVienDangNhap = LoginFrame.getNhanVienDangNhap();
		      
		        HoaDon hd = null;
				try {
					hd = new HoaDon(HoaDonDAO.getInstance().taoMaHoaDonTheoNgay(),new KhachHang(ddp.getKhachHang().getMaKH()), new Phong(ddp.getPhong().getMaPhong()),
					                   new NhanVien(  nhanVienDangNhap.getMaNV()), ddp, new KhuyenMai(), new Date(System.currentTimeMillis()),
					                    phong.getGiaTien(), 0, 0, 0, 0, 0, "Chưa thanh toán");
				       boolean hdInserted = HoaDonDAO.getInstance().insert(hd);

				        if (hdInserted) {
				            dondatphongdao.updateTinhTrang(ddp.getMaDDP(), "Đang ở");
//				            phong.setTrangThai("Đang ở");
				            phongdao.updateTinhTrang(phong, phong.getTrangThai());

				            ArrayList<Phong>   dsPhong = phongdao.getListPhong();
				            danhSachPhong.capNhatLaiDanhSachPhong();
				            dispose();
				          
				            DichVuSanPham_GUI dvsp = new DichVuSanPham_GUI(phong, ddp, danhSachPhong, this, true);
				            dvsp.setVisible(true);
				            JOptionPane.showMessageDialog(this, "Nhận phòng thành công");
				        } 
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    
		    }
		   
		}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btn_NhanPhong)) {
			nhanPhong(phong);
		}

		if (e.getSource().equals(btn_HuyDP)) {
			String maPhong = phong.getMaPhong();

			DonDatPhong ddp = dondatphongdao.getDonDatPhongTheoMaPhong(maPhong);

			if (ddp != null) {
				String maKH = ddp.getKhachHang().getMaKH();
				String maDDP = ddp.getMaDDP();

				KhachHang kh = khachhangdao.getKhachHangTheoMa(maKH);
				String tenKH = kh.getTenKH();
				int select = JOptionPane.showConfirmDialog(this,
						"<html>" + "<p style='text-align: center; font-size: 18px; color:red'>Cảnh báo</p>"
								+ "<p style='text-align: center;'>Hủy đơn đặt phòng của khách hàng "
								+ "<span style='color: blue'> " + tenKH + "</span>"
								+ " sẽ dẫn đến xóa đơn đặt phòng, hóa đơn có liên quan.</p>"
								+ "<p style='text-align: left;'>Hãy suy nghĩ thật kỹ trước khi quyết định.</p>"
								+ "</html>",
						"Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

				if (select == JOptionPane.YES_OPTION) {
					dondatphongdao.deleteDonDatPhong(maDDP, maPhong);
//					dsKH = khachhangdao.getListKhachHang();

					JOptionPane.showMessageDialog(this, "Đơn đặt phòng của khách hàng " + tenKH + " đã được xóa.",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
					phongdao.updateTinhTrang(phong, "Phòng trống");
					dispose();
					danhSachPhong.capNhatLaiDanhSachPhong();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy đơn đặt phòng cho phòng này.", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}
