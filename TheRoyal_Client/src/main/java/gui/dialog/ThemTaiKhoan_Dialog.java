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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import UI.QLNhanVien_GUI;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;
import gui.component.ButtonCustom;
import gui.component.HeaderTitle;
import validata.BCrypt;

public class ThemTaiKhoan_Dialog extends JDialog {

	private ButtonCustom btnThem, btnDong;
	private JTextField txtTenNV;
	public JTextField txtMaNV;
	private JTextField txtChucVu;
	private JTextField txtMaTK, txtMatKhau;
	private JLabel lbShowMessages;
	private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
	public JFrame popup = new JFrame();
	private ArrayList<NhanVien> dsNV;
	private ArrayList<TaiKhoan> dsTk;
	private QLNhanVien_GUI home;

	public ThemTaiKhoan_Dialog(javax.swing.JInternalFrame parent, JFrame owner, boolean modal) {
		super(owner, modal);
		GuiNhanVien();
		setLocationRelativeTo(null);
		home = (QLNhanVien_GUI) parent;
		NhanVien nv = home.getNhanVienSelect();
		txtTenNV.setText(nv.getTenNV());
		txtChucVu.setText(nv.getChucVu());
		dsTk = TaiKhoanDAO.getInstance().getAllTaiKhoan();
	}

	private void GuiNhanVien() {
		setSize(614, 393);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Thêm tài khoản");
		HeaderTitle title = new HeaderTitle("THÊM TÀI KHOẢN");
		getContentPane().add(title, BorderLayout.NORTH);

		// Panel thông tin nhân viên
		JPanel staffInfoPanel = new JPanel();
		staffInfoPanel.setBackground(Color.white);
		staffInfoPanel.setBounds(40, 111, 567, 451);
		staffInfoPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Thông tin nhân viên", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
		staffInfoPanel.setLayout(null);

		JLabel lblTen = new JLabel("Tên:");
		lblTen.setBounds(20, 32, 278, 38);
		staffInfoPanel.add(lblTen);
		txtTenNV = new JTextField(15);
		txtTenNV.setEnabled(false);
		txtTenNV.setBounds(298, 33, 278, 38);
		staffInfoPanel.add(txtTenNV);

		JLabel lblCCCD = new JLabel("Chức vụ:");
		lblCCCD.setBounds(20, 80, 278, 38);
		staffInfoPanel.add(lblCCCD);
		txtChucVu = new JTextField(15);
		txtChucVu.setEnabled(false);
		txtChucVu.setBounds(298, 80, 278, 38);
		staffInfoPanel.add(txtChucVu);

		JLabel lblMatKhu = new JLabel("Mật khẩu");
		lblMatKhu.setBounds(20, 175, 278, 38);
		staffInfoPanel.add(lblMatKhu);

		txtMatKhau = new JTextField(15);
		txtMatKhau.setBounds(298, 176, 278, 38);
		staffInfoPanel.add(txtMatKhau);

		JLabel lblMaTk = new JLabel("Tên đăng nhập");
		lblMaTk.setBounds(20, 128, 278, 38);
		staffInfoPanel.add(lblMaTk);
		txtMaTK = new JTextField(15);
		txtMaTK.setBounds(298, 128, 278, 38);
		txtMaTK.requestFocus();
		staffInfoPanel.add(txtMaTK);

		getContentPane().add(staffInfoPanel);
		btnThem = new ButtonCustom("Thêm","success", 12);
		btnThem.setLocation(174, 243);
		btnThem.setSize(100, 29);
		btnThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				themNV();
			}
		});
		btnDong =  new ButtonCustom("Hủy","danger", 12);
		btnDong.setLocation(314, 243);
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

	public void themNV() {
		try {
			if (validataTK(ADD)) {
				TaiKhoan tk = dataTKhoan();

				if (tk == null)
					return;

				String maNV = home.getNhanVienSelect().getMaNV();
				String existingMaTK = NhanVienDAO.getInstance().getTaiKhoanCuaNhanVien(maNV);
				if (existingMaTK != null && !existingMaTK.isEmpty()) {
					home.thongBao(3, "Nhân viên này đã có tài khoản! Không thể thêm tài khoản mới.");
					return;
				}

				boolean result = TaiKhoanDAO.getInstance().insert(tk);

				if (result) {
					boolean ktra = NhanVienDAO.getInstance().capNhatTaiKhoanNhanVien(maNV, tk.getMaTK());

					if (ktra) {
						home.tableModelTK.addRow(new Object[] { tk.getMaTK(), tk.getMatKhau(), tk.getLoaiTaiKhoan() });
						home.huytim();
						home.thongBao(0, "Thêm tài khoản thành công");
						dispose();
					} else {
						home.thongBao(3, "Cập nhật tải khoản cho nhân viên thất bại");
					}
				} else {
					home.thongBao(3, "Thêm tài khoản thất bại");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi trong quá trình xử lý!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean validataTK(int type) {
		String tenDangNhap = txtMaTK.getText().trim();
		String matkhau = txtMatKhau.getText().trim();

		if (!(tenDangNhap.length() > 0)) {
			home.thongBao(3, "Tên không được để trống");
			home.showMessage("Lỗi: Tên không được để trống", txtMaTK);
			return false;
		}

		if (!(matkhau.length() > 0)) {
			home.thongBao(3, "Mật khẩu không được để trống");
			home.showMessage("Lỗi: Tên không được để trống", txtMatKhau);
			return false;
		}
		if (type == ADD) {
			for (TaiKhoan item : dsTk) {
				if (item.getMaTK().equalsIgnoreCase(tenDangNhap)) {
					home.thongBao(3, "Tên tài khoản đã tồn tại");
					home.showMessage("Lỗi: Tên tài khoản đã tồn tại", txtMaTK);
					return false;
				}
			}
		}
		return true;
	}

	public TaiKhoan dataTKhoan() {
		// Retrieve values from input fields
		String maTk = txtMaTK.getText().trim();
		String matkhau = txtMatKhau.getText().trim();
		String loaiTk = txtChucVu.getText().trim();
		
	        String hash = BCrypt.hashpw(matkhau, BCrypt.gensalt(12));

		TaiKhoan tk = new TaiKhoan(maTk, hash, loaiTk);
		return tk;
	}
}
