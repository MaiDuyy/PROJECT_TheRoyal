package gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import gui.ui.QLKhachHang_GUI;
import gui.ui.QLNhanVien_GUI;
import dao.KhachHangDAO;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;
import gui.component.ButtonCustom;
import gui.component.HeaderTitle;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.KhachHangService;

public class SuaKhachHang_Dialog extends JDialog {
	private JTextField txtMaKH;
	private JTextField txtTenKH;
	private JTextField txtSDT;
	private JComboBox<String> cboLoaiKH;
	private JTextField txtCCCD;
	private JComboBox<String> cboGioiTinh;
	private ButtonCustom btnThem, btnDong;
	ArrayList<KhachHang> dsKH;
	private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
	private QLKhachHang_GUI home;

	private KhachHangService khachHangService = RMIClient.getInstance().getKhachHangService();
	@SneakyThrows
	public SuaKhachHang_Dialog(javax.swing.JInternalFrame parent, javax.swing.JFrame owner, boolean modal) {
		super(owner, modal);
		GuiKhachHang();
		setLocationRelativeTo(null);
		home = (QLKhachHang_GUI) parent;
		dsKH = (ArrayList<KhachHang>) khachHangService.getAll();
		KhachHang kh = home.getKhachHangSelect();
		txtMaKH.setText(kh.getMaKH());
		txtCCCD.setText(kh.getCCCD());
		txtSDT.setText(kh.getSDT());
		cboGioiTinh.setSelectedItem(kh.isGioiTinh());
		cboLoaiKH.setSelectedItem(kh.getLoaiKH());
		txtTenKH.setText(kh.getTenKH());
		
	}

	public void GuiKhachHang() {
		setSize(614,528);
		HeaderTitle title = new HeaderTitle("SỬA KHÁCH HÀNG");
		getContentPane().add(title, BorderLayout.NORTH);
		JPanel staffInfoPanel = new JPanel();
		staffInfoPanel.setBackground(Color.white);
		staffInfoPanel.setBounds(55, 50, 552, 491);
		staffInfoPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Thông tin khách hàng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

		JLabel lblMaKH = new JLabel("Mã khách hàng:");
		lblMaKH.setBounds(10, 34, 108, 30);
		txtMaKH = new JTextField(15);
		txtMaKH.setBounds(244, 34, 331, 29);
		txtMaKH.setEditable(false);
		JLabel lblTenKH = new JLabel("Tên khách hàng:");
		lblTenKH.setBounds(10, 88, 108, 30);
		txtTenKH = new JTextField(15);
		txtTenKH.setBounds(244, 87, 331, 30);
		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setBounds(10, 142, 108, 30);
		txtSDT = new JTextField(15);
		txtSDT.setBounds(244, 142, 331, 30);
		JLabel lblCCCD = new JLabel("CCCD:");
		lblCCCD.setBounds(10, 247, 108, 30);
		txtCCCD = new JTextField(15);
		txtCCCD.setBounds(244, 247, 331, 30);
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setBounds(10, 300, 101, 30);
		cboGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ" });
		cboGioiTinh.setBounds(244, 299, 331, 30);
		staffInfoPanel.setLayout(null);

		staffInfoPanel.add(lblMaKH);
		staffInfoPanel.add(txtMaKH);
		staffInfoPanel.add(lblTenKH);
		staffInfoPanel.add(txtTenKH);
		staffInfoPanel.add(lblSDT);
		staffInfoPanel.add(txtSDT);
		JLabel lblLoaiKH = new JLabel("Loại khách hàng:");
		lblLoaiKH.setBounds(10, 195, 108, 30);
		staffInfoPanel.add(lblLoaiKH);
		cboLoaiKH = new JComboBox<>(new String[] { "Thường", "VIP", "Khác" });
		cboLoaiKH.setBounds(244, 194, 331, 30);
		staffInfoPanel.add(cboLoaiKH);
		staffInfoPanel.add(lblCCCD);
		staffInfoPanel.add(txtCCCD);
		staffInfoPanel.add(lblGioiTinh);
		staffInfoPanel.add(cboGioiTinh);

		getContentPane().add(staffInfoPanel);

		btnThem = new ButtonCustom("Cập nhật","success", 12);
		btnThem.setLocation(165, 381);
		btnThem.setSize(100, 29);
		btnThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				themNV();
			}
		});
		btnDong =  new ButtonCustom("Hủy","danger", 12);
		btnDong.setLocation(305, 381);
		btnDong.setSize(100, 29);
		btnDong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			 	 int yes = JOptionPane.showConfirmDialog(null, 
	                     "Bạn có chắc chắn muốn thoát?", "Thông báo", 
	                     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

	             if (yes == JOptionPane.YES_OPTION) {
//	             	  System.exit(0);
	            	 dispose();
	             }
			}
		});
		staffInfoPanel.add(btnDong);
		staffInfoPanel.add(btnThem);

	}

	
	@SneakyThrows
	private void themNV() {
		 if(validData(UPDATE)) {
		KhachHang nv = dataKhachHang();
        khachHangService.update(nv);
        this.dispose();
        home.huytim();
        home.thongBao(0, "Cập nhật thành công");
		 }
	}
	
	
	private KhachHang dataKhachHang() {
		String maKH = txtMaKH.getText().trim();
		String tenKH = txtTenKH.getText().trim();
		String sDT = txtSDT.getText().trim();
		String loaiKH = cboLoaiKH.getSelectedItem().toString().trim();
		String cccd = txtCCCD.getText().trim();
		boolean gioiTinh = cboGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam");

		KhachHang kh = new KhachHang(maKH, tenKH, sDT, loaiKH, cccd, gioiTinh, null , null);
		return kh;
	}

	private boolean validData(int type) {
		// type = 1 -> thêm mới
		// type != 1 -> cập nhật
		String tenKH = txtTenKH.getText().trim();
		String sDT = txtSDT.getText().trim();
		String cccd = txtCCCD.getText().trim();

		if (!(tenKH.length() > 0)) {
			home.showMessage("Lỗi: Tên không được để trống", txtTenKH);
			return false;
		}
		if (!tenKH.matches("^[^0-9]+$")) {
			home.showMessage("Lỗi: Tên không được có số", txtTenKH);
			return false;
		}
		if (!sDT.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
			home.showMessage("Lỗi: Phải đủ 10 số", txtSDT);
			return false;
		}
		if (!(cccd.length() > 0 && cccd.matches("^(\\d{12})$"))) {
			home.showMessage("Lỗi:  CCCD phải có 12 số", txtCCCD);
			return false;
		} else {
			
			for (KhachHang item : dsKH) {
				if (item.getSDT().equalsIgnoreCase(sDT)) {
					home.showMessage("Lỗi: Số điện thoại đã tồn tại ", txtSDT);
					return false;
				}
			}
		
			
			if (type == ADD)
				for (KhachHang item : dsKH) {
					if (item.getCCCD().equalsIgnoreCase(cccd)) {
						home.showMessage("Lỗi: CCCD đã tồn tại", txtCCCD);
						return false;
					}
				}
		}

		return true;
	}

}
