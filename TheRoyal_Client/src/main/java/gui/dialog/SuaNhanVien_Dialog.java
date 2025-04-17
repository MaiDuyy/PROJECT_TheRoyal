package gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import gui.ui.QLNhanVien_GUI;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;
import formatdate.FormatDate;
import gui.component.ButtonCustom;
import gui.component.HeaderTitle;
import gui.validata.Validation;
import lombok.SneakyThrows;
import rmi.RMIClient;

public class SuaNhanVien_Dialog extends JDialog {
	private JTextField txtTenNV;
	private ButtonCustom btnThem, btnDong;
	public JTextField txtMaNV;
	private JTextField txtCCCD;
	private JTextField txtSDT;
	private JTextField txtTrangThai;
	private JTextField txtTim;
	private JTextField txtPassword,txtEmail;
	private JTextField txtUserName;
	private JComboBox<String> cboGioiTinh, cboChucVu, cboLoaiTK;
	private JDateChooser chonNgaySinh, chonNgayVaoLam;
	private JLabel lbShowMessages;
	private DefaultComboBoxModel<String> modelMaPhong;
	private JComboBox<String> cboMaTK;
	private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
	public JFrame popup = new JFrame();
	private ArrayList<NhanVien> dsNV = (ArrayList<NhanVien>) RMIClient.getInstance().getNhanVienService();
	private ArrayList<TaiKhoan> dsTk;
	private QLNhanVien_GUI home ; 
	private JTextField txtMaTK;
	@SneakyThrows
	public SuaNhanVien_Dialog(javax.swing.JInternalFrame parent, JFrame owner, boolean modal) {
        super(owner, modal);
        GuiNhanVien();
        setLocationRelativeTo(null);
        home = (QLNhanVien_GUI) parent;
        dsNV = (ArrayList<NhanVien>) home.nhanVienService.getAll();
        NhanVien nv = home.getNhanVienSelect();
        txtMaNV.setText(nv.getMaNV());
        txtTenNV.setText(nv.getTenNV());
		txtCCCD.setText(nv.getCCCD());
		cboChucVu.setSelectedItem(nv.getCCCD());
		chonNgaySinh.setDate(nv.getNgaySinh());
		chonNgayVaoLam.setDate(nv.getNgayVaoLam());
		cboGioiTinh.setSelectedItem(nv.isGioiTinh());
		txtSDT.setText(nv.getSDT());
		txtTrangThai.setText(nv.getTrangThai());
		txtMaTK.setText(nv.getTaiKhoan().getMaTK());
		txtEmail.setText(nv.getEmail());
      
    }

	private void GuiNhanVien() {
		setSize(614,713);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Sửa nhân viên");
		HeaderTitle title = new HeaderTitle("SỬA NHÂN VIÊN");
		getContentPane().add(title, BorderLayout.NORTH);
		// Panel thông tin nhân viên
		JPanel staffInfoPanel = new JPanel();
		staffInfoPanel.setBackground(Color.white);
		staffInfoPanel.setBounds(40, 111, 567, 451);
		staffInfoPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Thông tin nhân viên", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

		JLabel lblMaNV = new JLabel("Mã nhân viên:");
		lblMaNV.setBounds(15, 28, 278, 38);
		txtMaNV = new JTextField(15);
		txtMaNV.setEditable(false);
		txtMaNV.setBounds(293, 29, 278, 38);
		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setBounds(15, 172, 278, 38);
		txtSDT = new JTextField(15);
		txtSDT.setBounds(293, 173, 278, 38);
		JLabel lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setBounds(15, 510, 278, 38);
		txtTrangThai = new JTextField(15);
		txtTrangThai.setBounds(293, 511, 278, 38);
		staffInfoPanel.setLayout(null);

		staffInfoPanel.add(lblMaNV);
		staffInfoPanel.add(txtMaNV);
		JLabel lblTen = new JLabel("Tên:");
		lblTen.setBounds(15, 76, 278, 38);
		staffInfoPanel.add(lblTen);
		txtTenNV = new JTextField(15);
		txtTenNV.setBounds(293, 77, 278, 38);
		staffInfoPanel.add(txtTenNV);
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setBounds(15, 124, 278, 38);
		staffInfoPanel.add(lblGioiTinh);
		cboGioiTinh = new JComboBox<>(new String[] { "Nữ", "Nam" });
		cboGioiTinh.setBounds(293, 124, 278, 38);
		staffInfoPanel.add(cboGioiTinh);
		staffInfoPanel.add(lblSDT);
		staffInfoPanel.add(txtSDT);
		JLabel lblCCCD = new JLabel("Số CCCD:");
		lblCCCD.setBounds(15, 220, 278, 38);
		staffInfoPanel.add(lblCCCD);
		txtCCCD = new JTextField(15);
		txtCCCD.setBounds(293, 221, 278, 38);
		staffInfoPanel.add(txtCCCD);
		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setBounds(15, 315, 278, 38);
		staffInfoPanel.add(lblNgaySinh);
		chonNgaySinh = new JDateChooser();
		chonNgaySinh.setBounds(293, 315, 278, 38);
		chonNgaySinh.setDate(Date.valueOf(java.time.LocalDate.now()));
		chonNgaySinh.setDateFormatString("dd-MM-yyyy");

		staffInfoPanel.add(chonNgaySinh);
		JLabel lblNgayVaoLam = new JLabel("Ngày vào làm:");
		lblNgayVaoLam.setBounds(15, 363, 278, 38);
		staffInfoPanel.add(lblNgayVaoLam);
		chonNgayVaoLam = new JDateChooser();
		chonNgayVaoLam.setBounds(293, 363, 278, 38);
		chonNgayVaoLam.setDate(Date.valueOf(java.time.LocalDate.now()));
		chonNgayVaoLam.setDateFormatString("dd-MM-yyyy");
		staffInfoPanel.add(chonNgayVaoLam);


		JLabel lblChucVu = new JLabel("Chức vụ/Loại tài khoản:");
		lblChucVu.setBounds(15, 462, 273, 38);
		staffInfoPanel.add(lblChucVu);
		cboChucVu = new JComboBox<>(new String[] { "Quản lý", "Nhân viên" });
		cboChucVu.setBounds(293, 462, 278, 38);
		staffInfoPanel.add(cboChucVu);
		staffInfoPanel.add(lblTrangThai);
		staffInfoPanel.add(txtTrangThai);

		getContentPane().add(staffInfoPanel);
		btnThem = new ButtonCustom("Cập nhật","success", 12);
		btnThem.setLocation(174, 564);
		btnThem.setSize(100, 29);
	
		btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	themNV();
            }
        });
		btnDong =  new ButtonCustom("Hủy","danger", 12);
		btnDong.setLocation(312, 564);
		btnDong.setSize(100, 29);
		   btnDong.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	 int yes = JOptionPane.showConfirmDialog(null, 
	                         "Bạn có chắc chắn muốn thoát?", "Thông báo", 
	                         JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

	                 if (yes == JOptionPane.YES_OPTION) {
//	                 	  System.exit(0);
	                	 dispose();
	                 }
	            }
	        });
		   staffInfoPanel.add(btnDong);
		staffInfoPanel.add(btnThem);
		
		JLabel lblCCCD_1 = new JLabel("Tên đăng nhập:");
		lblCCCD_1.setBounds(15, 413, 278, 38);
		staffInfoPanel.add(lblCCCD_1);
		
		txtMaTK = new JTextField(15);
		txtMaTK.setEditable(false);
		txtMaTK.setText((String) null);
		txtMaTK.setBounds(293, 414, 278, 38);
		staffInfoPanel.add(txtMaTK);
		
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(15, 266, 278, 38);
		staffInfoPanel.add(lblEmail);
		
		txtEmail = new JTextField(15);
		txtEmail.setBounds(293, 267, 278, 38);
		staffInfoPanel.add(txtEmail);
		
	
	}
	
	
	
	
	@SneakyThrows
	private void themNV() {
		if (validData(UPDATE)) {
			NhanVien nv = dataNVien();
			RMIClient.getInstance().getNhanVienService().update(nv);
	        this.dispose();
	        home.huytim();
	        home.thongBao(0, "Cập nhật thành công");
			
		}
	}
	
	public boolean validData(int type) {
		// type = 1 -> thêm mới
		// type != 1 -> cập nhật

		String tenNV = txtTenNV.getText().trim();
		String cccd = txtCCCD.getText().trim();
		String sDT = txtSDT.getText().trim();
		Date ngaySinh = FormatDate.convertFromJAVADateToSQLDate(chonNgaySinh.getDate());
		Date ngayVaoLam = FormatDate.convertFromJAVADateToSQLDate(chonNgayVaoLam.getDate());
		Date ngayHienTai = Date.valueOf(java.time.LocalDate.now());

		String nS = FormatDate.formatDate(ngaySinh);
//		String tenDangNhap = txtMaTK.getText().trim();
		String email = txtEmail.getText().trim();

		if (!(tenNV.length() > 0)) {
			home.thongBao(2, "Tên không được để trống", txtTenNV);
			home.showMessage("Lỗi: Tên không được để trống", txtTenNV);
			return false;
		}
		if (!tenNV.matches("^[^0-9]+$")) {
			home.showMessage("Lỗi: Tên không được có số", txtTenNV);
			return false;
		}

		if (!sDT.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
			home.showMessage("Lỗi: Phải đủ 10 số", txtSDT);
			return false;
		} else {
			if (type == ADD) {
				for (NhanVien item : dsNV) {
					if (item.getSDT().equalsIgnoreCase(sDT)) {
						home.showMessage("Lỗi: Số điện thoại đã tồn tại ", txtSDT);
						return false;
					}
				}
			}

		}

		if (!(cccd.length() > 0 && cccd.matches("^(\\d{12})$"))) {
			home.showMessage("Lỗi:  CCCD phải có 12 số", txtCCCD);
			return false;
		} else {
			if (type == ADD) {
				for (NhanVien item : dsNV) {
					if (item.getCCCD().equalsIgnoreCase(cccd)) {
						home.showMessage("Lỗi: CMND hoặc CCCD đã tồn tại", txtCCCD);
						return false;
					}
				}
			}
		}

		if (!(email.length() > 0 && Validation.isEmail(email))) {
			home.showMessage("Lỗi:  Email sai định dạng", txtEmail);
			return false;
		} else {
			if (type == ADD) {
				for (NhanVien item : dsNV) {
					if (item.getEmail().equalsIgnoreCase(email)) {
						home.showMessage("Lỗi: email đã tồn tại ", txtEmail);
						return false;
					}
				}
			}
		}
		if(!Validation.validateNgaySinh(ngaySinh)) {
			return false;
		}
		if (ngayVaoLam == null) {
		    JOptionPane.showMessageDialog(null, "Ngày vào làm không được để trống!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		    return false;
		} else if (ngayVaoLam.before(ngayHienTai)) {
		    JOptionPane.showMessageDialog(null, "Ngày vào làm không được trước ngày hiện tại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		    return false;
		}


		return true;
	}
	public  NhanVien dataNVien() {
		// Retrieve values from input fields
		String maNV = txtMaNV.getText().trim();
		String tenNV = txtTenNV.getText().trim();
		String sdt = txtSDT.getText().trim();

		boolean gioiTinh = cboGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nữ");

		Date ngaysinh = FormatDate.convertFromJAVADateToSQLDate(chonNgaySinh.getDate());
		Date ngayvaolam = FormatDate.convertFromJAVADateToSQLDate(chonNgayVaoLam.getDate());

		String matk = txtMaTK.getText().trim();
		String email = txtEmail.getText().trim();
		String cccd = txtCCCD.getText().trim();
		String trangthai = txtTrangThai.getText().trim();
		String chucvu = cboChucVu.getSelectedItem().toString().trim();

		NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, cccd, ngaysinh, sdt, email, new TaiKhoan(matk), ngayvaolam,
				chucvu, trangthai, null );
		return nv;
	}
}
