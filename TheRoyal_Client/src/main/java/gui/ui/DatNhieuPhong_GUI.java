//package gui.ui;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.sql.Date;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import javax.swing.BorderFactory;
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JDialog;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JSeparator;
//import javax.swing.JSpinner;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import javax.swing.SpinnerNumberModel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.TitledBorder;
//import javax.swing.table.DefaultTableModel;
//
//import com.toedter.calendar.JDateChooser;
//
//import gui.format_ui.RadiusButton;
//import gui.format_ui.Table;
//
//import dao.*;
//import entity.*;
//import javax.swing.ComboBoxModel;
//import javax.swing.JCheckBox;
//
//public class DatNhieuPhong_GUI extends JDialog implements ActionListener, MouseListener {
//
//	private PhongDAO phongdao;
//	private KhachHangDAO khachhangdao;
//	private DonDatPhongDAO dondatphongdao;
//	private HoaDonDAO hoadondao;
//	private RadiusButton btnMore;
//	private JButton btnThem, btnLamLai, btnTim ;
//	private JTextField txtSDT, txtTenKH, txtTrangThai, txtTim;
//	private JDateChooser dateDatPhong, dateNhanPhong;
//	private DefaultComboBoxModel<String> modelMaPhong, modelMaKH;
//	private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
//	private ArrayList<Phong> dsPhong;
//	private ArrayList<KhachHang> dsKH;
//	private ArrayList<DonDatPhong> dsDDP;
//	private ArrayList<HoaDon> dsHD;
//	private static final long serialVersionUID = 1;
//	private JComboBox<String> cboLoaiKH;
//	private JComboBox<String> cboGioiTinh;
//	private JTextField txtCCCD;
//	private JLabel lbShowMessages;
//	private DanhSachPhong_GUI danhSachPhong;
//	private DonDatPhong ddpp;
//	private JComboBox<String> cboMaKH;
//	private JCheckBox chkKH;
//	private JTextField txtNglon;
//	private JTextField txtTreEm;
//	private JLabel lblSTrEm;
//	private Table tblPhong;
//	private DefaultTableModel tableModelPhong;
//	private JTextField txtMPhong;
//	private JDateChooser dateTraPhong;
//	private String loaiPhong;
//
//	/**
//	 * Create the dialog.
//	 */
//	public DatNhieuPhong_GUI(DanhSachPhong_GUI danhSachPhong) {
//
//		getContentPane().setBackground(new Color(255, 255, 255));
//		this.danhSachPhong = danhSachPhong;
//
//		try {
//			ConnectDB.getInstance().connect();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		phongdao = new PhongDAO();
//		khachhangdao = new KhachHangDAO();
//		dondatphongdao = new DonDatPhongDAO();
//		hoadondao = new HoaDonDAO();
//
//		setBounds(100, 100, 1276, 721);
//		GUIQLYPhong();
////		loadDataPhong();
//		loadData();
//		loadCboMaKhachHang();
//		loadListKhachHang();
//	}
//
//	private void GUIQLYPhong() {
//		getContentPane().setLayout(null);
//
//		// Panel thông tin nhân viên
//
//		JPanel searchPanel = new JPanel();
//		searchPanel.setBounds(617, 60, 633, 59);
//		searchPanel.setBackground(Color.white);
//		searchPanel
//				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
//						"Tìm kiếm", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
//						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
//
//		JLabel searchLabel = new JLabel("Ngày nhận:");
//		searchLabel.setBounds(149, 27, 84, 13);
//		dateNhanPhong = new JDateChooser();
//		dateNhanPhong.setBounds(238, 24, 126, 19);
//		dateNhanPhong.setDate(Date.valueOf(java.time.LocalDate.now()));
//		dateNhanPhong.setDateFormatString("dd-MM-yyyy");
//		btnTim = new JButton("Tìm Kiếm");
//		btnTim.setBounds(369, 23, 104, 21);
//		btnTim.setIcon(new ImageIcon(DatNhieuPhong_GUI.class.getResource("/src/ICON/icon/search_16.png")));
//		searchPanel.setLayout(null);
//
//		searchPanel.add(searchLabel);
//		searchPanel.add(dateNhanPhong);
//		searchPanel.add(btnTim);
//		getContentPane().add(searchPanel);
//		JPanel staffInfoPanel = new JPanel();
//		staffInfoPanel.setBackground(Color.white);
//		staffInfoPanel.setBounds(10, 63, 597, 353);
//		staffInfoPanel
//				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
//						"Thông tin đặt phòng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
//						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
//
//		JLabel lblMaNV = new JLabel("Tên khách hàng : ");
//		lblMaNV.setBounds(5, 71, 141, 38);
//		txtTenKH = new JTextField(15);
//		txtTenKH.setEnabled(false);
//		txtTenKH.setBounds(98, 74, 152, 38);
//		// txtSoGiuong = new JTextField(15);
//
//		staffInfoPanel.setLayout(null);
//
//		staffInfoPanel.add(lblMaNV);
//		staffInfoPanel.add(txtTenKH);
//
//		JLabel lblTen = new JLabel("Số điện thoại:");
//		lblTen.setBounds(5, 119, 141, 38);
//		staffInfoPanel.add(lblTen);
//		txtSDT = new JTextField(15);
//		txtSDT.setEnabled(false);
//		txtSDT.setBounds(98, 122, 152, 38);
//		staffInfoPanel.add(txtSDT);
//
//		JLabel lblLoaiPhong = new JLabel("Mã phòng:");
//		lblLoaiPhong.setBounds(5, 222, 141, 38);
//		staffInfoPanel.add(lblLoaiPhong);
//		modelMaPhong = new DefaultComboBoxModel<String>();
//
//		dateDatPhong = new JDateChooser();
//		dateDatPhong.setBounds(391, 23, 197, 38);
//		dateDatPhong.setDate(Date.valueOf(java.time.LocalDate.now()));
//		dateDatPhong.setDateFormatString("dd-MM-yyyy");
//		staffInfoPanel.add(dateDatPhong);
//
//		getContentPane().add(staffInfoPanel);
//
//		JLabel lblSoTreEm_1 = new JLabel("Ngày đặt phòng:");
//		lblSoTreEm_1.setBounds(260, 23, 121, 38);
//		staffInfoPanel.add(lblSoTreEm_1);
//
//		btnThem = new JButton("Đặt phòng", new ImageIcon(DatNhieuPhong_GUI.class.getResource("/src/ICON/icon/blueAdd_16.png")));
//		btnThem.setBounds(303, 309, 132, 21);
//		staffInfoPanel.add(btnThem);
//
//		btnLamLai = new JButton("Làm Lại");
//		btnLamLai.setBounds(445, 309, 145, 21);
//		staffInfoPanel.add(btnLamLai);
//		btnLamLai.setIcon(new ImageIcon(DatNhieuPhong_GUI.class.getResource("/src/ICON/icon/refresh_16.png")));
//
//		txtCCCD = new JTextField(15);
//		txtCCCD.setEnabled(false);
//		txtCCCD.setBounds(98, 170, 152, 38);
//		staffInfoPanel.add(txtCCCD);
//
//		JLabel lblCnCcCng = new JLabel("Căn cước công dân:");
//		lblCnCcCng.setBounds(5, 174, 141, 38);
//		staffInfoPanel.add(lblCnCcCng);
//
//		cboLoaiKH = new JComboBox<>(new String[] { "Thường", "VIP", "Khác" });
//		cboLoaiKH.setEnabled(false);
//		cboLoaiKH.setBounds(391, 119, 197, 38);
//		staffInfoPanel.add(cboLoaiKH);
//
//		JLabel lblLoaiPhong_1 = new JLabel("Loại khách hàng:");
//		lblLoaiPhong_1.setBounds(260, 119, 121, 38);
//		staffInfoPanel.add(lblLoaiPhong_1);
//
//		JLabel lblLoaiPhong_1_1 = new JLabel("Giới tính:");
//		lblLoaiPhong_1_1.setBounds(260, 174, 121, 38);
//		staffInfoPanel.add(lblLoaiPhong_1_1);
//
//		cboGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ" });
//		cboGioiTinh.setEnabled(false);
//		cboGioiTinh.setBounds(391, 174, 197, 38);
//		staffInfoPanel.add(cboGioiTinh);
//
//		lbShowMessages = new JLabel("");
//		lbShowMessages.setBounds(88, 258, 295, 37);
//		staffInfoPanel.add(lbShowMessages);
//
//		chkKH = new JCheckBox("Thêm khách hàng mới");
//		chkKH.setBounds(303, 282, 132, 21);
//		staffInfoPanel.add(chkKH);
//
//		JLabel lblMKhchHng = new JLabel("Mã khách hàng:");
//		lblMKhchHng.setBounds(5, 23, 141, 38);
//		staffInfoPanel.add(lblMKhchHng);
//		modelMaKH = new DefaultComboBoxModel<String>();
//		cboMaKH = new JComboBox<String>(modelMaKH);
//		cboMaKH.setBounds(98, 25, 152, 38);
//		staffInfoPanel.add(cboMaKH);
//
//		JLabel lblSonguoiLon = new JLabel("Số người lớn:");
//		lblSonguoiLon.setBounds(260, 222, 86, 38);
//		staffInfoPanel.add(lblSonguoiLon);
//
//		txtNglon = new JTextField(15);
//		txtNglon.setEnabled(false);
//		txtNglon.setBounds(333, 222, 86, 38);
//		staffInfoPanel.add(txtNglon);
//
//		lblSTrEm = new JLabel("Số trẻ em:");
//		lblSTrEm.setBounds(429, 220, 86, 38);
//		staffInfoPanel.add(lblSTrEm);
//
//		txtTreEm = new JTextField(15);
//		txtTreEm.setEnabled(false);
//		txtTreEm.setBounds(494, 220, 86, 38);
//		staffInfoPanel.add(txtTreEm);
//
//		txtMPhong = new JTextField(15);
//		txtMPhong.setEnabled(false);
//		txtMPhong.setBounds(98, 222, 152, 38);
//		staffInfoPanel.add(txtMPhong);
//
//		JLabel lblNgayTra = new JLabel("Ngày trả phòng:");
//		lblNgayTra.setBounds(260, 71, 121, 38);
//		staffInfoPanel.add(lblNgayTra);
//
//		dateTraPhong = new JDateChooser();
//		dateTraPhong.setDateFormatString("dd-MM-yyyy");
//		dateTraPhong.setBounds(391, 71, 197, 38);
//		staffInfoPanel.add(dateTraPhong);
//
//		JPanel tabelPanelPhong = new JPanel();
//		tabelPanelPhong.setBounds(617, 129, 633, 545);
//		tabelPanelPhong.setBackground(Color.white);
//		tabelPanelPhong
//				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
//						"Danh sách phòng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
//						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
//		String[] columns = { "Mã phòng", "Tên phòng", "Mã loại", "Số giường", "Số người lớn", "Số trẻ em", "Giá tiền" };
//		Object[][] data = {};
//		tableModelPhong = new DefaultTableModel(columns, 0) {
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			}
//		};
//		tabelPanelPhong.setLayout(null);
//
//		tblPhong = new Table();
//		tblPhong.setModel(tableModelPhong);
//		tblPhong.setBackground(Color.white);
//
//		JScrollPane scrollPane = new JScrollPane(tblPhong);
//		scrollPane.setBounds(10, 20, 615, 494);
//		tabelPanelPhong.add(scrollPane);
//		getContentPane().add(tabelPanelPhong);
//
//
//
//
//		btnLamLai.addActionListener(this);
//		btnThem.addActionListener(this);
//		cboMaKH.addActionListener(this);
//		chkKH.addActionListener(this);
//		tblPhong.addMouseListener(this);
//		btnTim.addActionListener(this);
//
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource().equals(btnThem)) {
//			KhachHang kh = null;
//			DonDatPhong ddp = null;
//			HoaDon hd = null;
//
//			   if (chkKH.isSelected()) {
//			        kh = dataKhachHang();
//			        if (validData(ADD)) {
//			            boolean result = khachhangdao.insert(kh);
//			            if (result) {
//			                String maKH = khachhangdao.getLatestID();
//			                kh = new KhachHang(maKH, kh.getTenKH(), kh.getsDT(), kh.getLoaiKH(), kh.getcCCD(), kh.isGioiTinh());
//			                showMessage("Khách hàng mới được thêm thành công!", SUCCESS);
//			                loadListKhachHang();
//			            } else {
//			                showMessage("Lỗi: Thêm khách hàng mới thất bại", ERROR);
//			                return;
//			            }
//			        }
//			    } else {
//			        int indx = cboMaKH.getSelectedIndex() - 1;
//			        if (indx < 0) {
//			            showMessage("Vui lòng chọn khách hàng từ danh sách!", ERROR);
//			            return;
//			        }
//			        kh = dsKH.get(indx);
//			    }
//
//
//			ddp = dataDDP(kh.getMaKH());
//			Phong phong = phongdao.getPhongByMaPhong(ddp.getPhong().getMaPhong());
//
//			if (validData2(ADD)) {
//				try {
//					 if (!dondatphongdao.isRoomAvailable(ddp.getPhong().getMaPhong(), ddp.getThoiGianDatPhong(), ddp.getThoiGianTraPhong())) {
//					        showMessage("Phòng " + ddp.getPhong().getMaPhong() + " đã được đặt trong khoảng thời gian này, vui lòng chọn phòng khác!", ERROR);
//					        return;
//					    }
//
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					return;
//				}
//
//				boolean result1 = dondatphongdao.insert(ddp);
//				String maDDP = dondatphongdao.getLatestID();
//
//				if (result1) {
//					Date ngaydat = convertFromJAVADateToSQLDate(ddp.getThoiGianDatPhong());
//					Date ngaynhan = convertFromJAVADateToSQLDate(ddp.getThoiGianTraPhong());
//					Date ngaytra = convertFromJAVADateToSQLDate(ddp.getThoiGianTraPhong());
//
//					ddp = new DonDatPhong(maDDP, ngaydat, ngaynhan, ngaytra, kh, new Phong(ddp.getPhong().getMaPhong()),
//							"Đã đặt", ddp.getSoLuongNGLon(), ddp.getSoLuongTreEm());
////		        } else {
////		            showMessage("Lỗi: Thêm đơn đặt phòng thất bại", ERROR);
////		            return;
//				}
//
////		        String maHD = null;
////				try {
////					maHD = hoadondao.taoMaHoaDonTheoNgay();
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
//
////				try {
////					hd = new HoaDon(hoadondao.taoMaHoaDonTheoNgay(), kh, new Phong(ddp.getPhong().getMaPhong()),
////							new NhanVien(), ddp, new KhuyenMai(), new java.sql.Date(System.currentTimeMillis()),
////							phong.getGiaTien(), 0, 0, 0, 0, 0);
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
//
////				boolean hdInserted = hoadondao.insert(hd);
//
//				if (true) {
//					dondatphongdao.updateTinhTrang(maDDP, ddp.getTrangThai());
//					phong.setTrangThai("Đã đặt");
//					phongdao.updateTinhTrang(phong, phong.getTrangThai());
//
//					loadDataPhong();
//					danhSachPhong.capNhatLaiDanhSachPhong();
//					dispose();
//				} else {
//					showMessage("Lỗi: Thêm hóa đơn thất bại", ERROR);
//				}
//			}
//		}
//
//		if (e.getSource().equals(cboMaKH)) {
//			int indx = cboMaKH.getSelectedIndex();
//			if (indx == 0) {
//				txtTenKH.setText("");
//				txtCCCD.setText("");
//				txtSDT.setText("");
//				cboLoaiKH.setSelectedIndex(0);
//				cboGioiTinh.setSelectedIndex(0);
//			} else {
//				indx -= 1;
//				txtTenKH.setText(dsKH.get(indx).getTenKH());
//				txtCCCD.setText(dsKH.get(indx).getcCCD());
//				txtSDT.setText(dsKH.get(indx).getsDT());
//				cboLoaiKH.setSelectedItem(dsKH.get(indx).getLoaiKH());
//				cboGioiTinh.setSelectedItem(dsKH.get(indx).isGioiTinh());
//			}
//		}
//
//		if (e.getSource().equals(chkKH)) {
//			if (chkKH.isSelected()) {
//				txtTenKH.setEnabled(true);
//				txtCCCD.setEnabled(true);
//				cboLoaiKH.setEnabled(true);
//				txtSDT.setEnabled(true);
//				txtTenKH.requestFocus();
//				cboGioiTinh.setEnabled(true);
//				cboMaKH.setEnabled(false);
//				txtNglon.setEnabled(true);
//				txtTreEm.setEnabled(true);
//			} else {
//				txtTenKH.setEnabled(false);
//				txtCCCD.setEnabled(false);
//				txtSDT.setEnabled(false);
//				cboLoaiKH.setEnabled(false);
//				cboGioiTinh.setEnabled(false);
//				cboMaKH.setEnabled(true);
//				cboMaKH.requestFocus();
//				txtNglon.setEnabled(true);
//				txtTreEm.setEnabled(true);
//			}
//		}
//
//		if (e.getSource().equals(btnTim)) {
//			if (e.getSource().equals(btnTim)) {
//				java.util.Date ngayNhanDate = dateNhanPhong.getDate();
//
//				if (ngayNhanDate != null) { //
//					String ngayNhanStr = formatDate(ngayNhanDate);
//
//					ArrayList<Phong> dsPhong = phongdao.getPhongConLaiTheoNgayNhanPhong(ngayNhanStr);
//
//					if (!dsPhong.isEmpty()) {
//
//						DocDuLieuPhongVaoTable(dsPhong);
//					} else {
//						JOptionPane.showMessageDialog(null, "Không có phòng trống vào ngày đã chọn.", "Thông báo",
//								JOptionPane.INFORMATION_MESSAGE);
//					}
//				} else {
//					JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày để tìm kiếm.", "Yêu cầu ngày",
//							JOptionPane.WARNING_MESSAGE);
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		if (e.getSource().equals(tblPhong)) {
//			int row = tblPhong.getSelectedRow();
//			txtMPhong.setText(tableModelPhong.getValueAt(row, 0).toString());
//			txtNglon.setText(tableModelPhong.getValueAt(row, 4).toString());
//			txtTreEm.setText(tableModelPhong.getValueAt(row, 5).toString());
//		}
//
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	private void loadCboMaKhachHang() {
//		dsKH = khachhangdao.getListKhachHang();
//		if (dsKH == null || dsKH.size() <= 0)
//			return;
//		cboMaKH.addItem("");
//		for (KhachHang item : dsKH) {
//			cboMaKH.addItem(item.getMaKH());
//		}
//	}
//
//	private void loadDataPhong() {
//		dsPhong = phongdao.getListPhong();
//	}
//
//	private void loadListKhachHang() {
//		dsKH = khachhangdao.getListKhachHang();
//	}
//
//	private DonDatPhong dataDDP(String maKH) {
//		String maphong = txtMPhong.getText().trim();
//
//		Date ngayDat = convertFromJAVADateToSQLDate(dateDatPhong.getDate());
//		Date ngayNhan = convertFromJAVADateToSQLDate(dateNhanPhong.getDate());
//		Date ngayTra = convertFromJAVADateToSQLDate(dateTraPhong.getDate());
//		int nglon = txtNglon.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtNglon.getText().trim());
//		int treem = txtTreEm.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtTreEm.getText().trim());
//
//		DonDatPhong ddp = new DonDatPhong("", ngayDat, ngayNhan, ngayTra, new KhachHang(maKH), new Phong(maphong), "",
//				nglon, treem);
//		return ddp;
//	}
//
//	private KhachHang dataKhachHang() {
//		String tenKH = txtTenKH.getText().trim();
//		String sDT = txtSDT.getText().trim();
//		String loaiKH = cboLoaiKH.getSelectedItem().toString().trim();
//		String cccd = txtCCCD.getText().trim();
//		boolean gioiTinh = cboGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam");
//
//		KhachHang kh = new KhachHang("", tenKH, sDT, loaiKH, cccd, gioiTinh);
//		return kh;
//	}
//
//	private String formatDate(java.util.Date ngayNhanDate) {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//		return sdf.format(ngayNhanDate);
//	}
//
//	public static Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
//		Date sqlDate = null;
//		if (javaDate != null) {
//			sqlDate = new Date(javaDate.getTime());
//		}
//		return sqlDate;
//	}
//
//	private void showMessage(String message, JTextField txt) {
//		showMessage(message, ERROR);
//		txt.requestFocus();
//		txt.selectAll();
//
//	}
//
//	private void showMessage(String message, int type) {
//		if (type == SUCCESS) {
//			lbShowMessages.setForeground(Color.GREEN);
//			// lbShowMessages.setIcon(checkIcon);
//		} else if (type == ERROR) {
//			lbShowMessages.setForeground(Color.RED);
//			// lbShowMessages.setIcon(errorIcon);
//		} else {
//			lbShowMessages.setForeground(Color.BLACK);
//			lbShowMessages.setIcon(null);
//		}
//		lbShowMessages.setText(message);
//	}
//
//	private boolean validData2(int type) {
//		// type = 1 -> thêm mới
//		// type != 1 -> cập nhật
//		String tenKH = txtTenKH.getText().trim();
//		String sDT = txtSDT.getText().trim();
//		String cccd = txtCCCD.getText().trim();
//		Date ngaydat = convertFromJAVADateToSQLDate(dateDatPhong.getDate());
//		Date ngaynhan = convertFromJAVADateToSQLDate(dateNhanPhong.getDate());
//		Date ngayhientai = new Date(System.currentTimeMillis());
//
//		if (!(tenKH.length() > 0)) {
//			showMessage("Lỗi: Tên không được để trống", txtTenKH);
//			return false;
//		}
//		if (!tenKH.matches("^[^0-9]+$")) {
//			showMessage("Lỗi: Tên không được có số", txtTenKH);
//			return false;
//		}
//		if (!sDT.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
//			showMessage("Lỗi: Phải đủ 10 số", txtSDT);
//			return false;
//		}
////
////		if (ngaydat == null) {
////			showMessage("Lỗi: Ngày đến không được để trống", ERROR);
////			return false;
////		}
////		if (ngaynhan == null) {
////			showMessage("Lỗi: Ngày đi không được để trống", ERROR);
////			return false;
////		}
////		if (ngayBD.before(ngayhientai)) {
////			showMessage("Lỗi: Ngày đến phải không được trước ngày hiện tại", ERROR);
////			return false;
////		}
//		if (ngaynhan.before(ngaydat)) {
//			showMessage("Lỗi: Ngày nhận không được trước ngày đến", ERROR);
//			return false;
//		}
//		if (!(cccd.length() > 0 && cccd.matches("^(\\d{12})$"))) {
//			showMessage("Lỗi:  CCCD phải có 12 số", txtCCCD);
//			return false;
//		}
//
//		return true;
//	}
//
//	private boolean validData(int type) {
//		// type = 1 -> thêm mới
//		// type != 1 -> cập nhật
//		String tenKH = txtTenKH.getText().trim();
//		String sDT = txtSDT.getText().trim();
//		String cccd = txtCCCD.getText().trim();
//		Date ngaydat = convertFromJAVADateToSQLDate(dateDatPhong.getDate());
//		Date ngaynhan = convertFromJAVADateToSQLDate(dateNhanPhong.getDate());
//		Date ngayhientai = new Date(System.currentTimeMillis());
//
//		if (!(tenKH.length() > 0)) {
//			showMessage("Lỗi: Tên không được để trống", txtTenKH);
//			return false;
//		}
//		if (!tenKH.matches("^[^0-9]+$")) {
//			showMessage("Lỗi: Tên không được có số", txtTenKH);
//			return false;
//		}
//		if (!sDT.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
//			showMessage("Lỗi: Phải đủ 10 số", txtSDT);
//			return false;
//		}
//		if (ngaydat == null) {
//			showMessage("Lỗi: Ngày đến không được để trống", ERROR);
//			return false;
//		}
//		if (ngaynhan == null) {
//			showMessage("Lỗi: Ngày đi không được để trống", ERROR);
//			return false;
//		}
////		if (ngayBD.before(ngayhientai)) {
////			showMessage("Lỗi: Ngày đến phải không được trước ngày hiện tại", ERROR);
////			return false;
////		}
//		if (ngaynhan.before(ngaydat)) {
//			showMessage("Lỗi: Ngày nhận không được trước ngày đặt", ERROR);
//			return false;
//		}
//
//		if (!(cccd.length() > 0 && cccd.matches("^(\\d{12})$"))) {
//			showMessage("Lỗi:  CCCD phải có 12 số", txtCCCD);
//			return false;
//		} else {
//			if (type == ADD)
//				for (KhachHang item : dsKH) {
//					if (item.getcCCD().equalsIgnoreCase(cccd)) {
//						showMessage("Lỗi: CCCD đã tồn tại", txtCCCD);
//						return false;
//					}
//				}
//		}
//
//		return true;
//	}
//
//	private void DocDuLieuPhongVaoTable(ArrayList<Phong> dsPhong) {
//		DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
//		model.setRowCount(0);
//
//		for (Phong phong : dsPhong) {
//
//			  double number = phong.getGiaTien();
//	                        String COUNTRY = "VN";
//	                        String LANGUAGE = "vi";
//	                        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(number);
//	            DecimalFormat df = new DecimalFormat("#,###.##");
//	            String donGia = df.format(number);
//			Object[] rowData = { phong.getMaPhong(), phong.getTenPhong(), phong.getLoaiPhong().getMaLoai(),phong.getSoGiuong(),phong.getSoNguoiLon(),phong.getSoTreEm(), str };
//			model.addRow(rowData);
//		}
//	}
//
//
//
//
//
//    public void loadData() {
//        // Lấy danh sách phòng
//        phongdao = new PhongDAO();
//        ArrayList<Phong> phongTrongList = phongdao.getPhongByTinhTrang("Phòng trống");
//        // set loai phong vs gia
//
//
//        DefaultTableModel dtm = (DefaultTableModel) tblPhong.getModel();
//        dtm.setRowCount(0);
//        dondatphongdao = new DonDatPhongDAO();
//        ArrayList<DonDatPhong> dsDDP = dondatphongdao.getListDonDatPhong();
//        String maPhongDuocDatTruoc = "";
//        List<Phong> phongCanLoaiBo = new ArrayList<>();
//        for (DonDatPhong ddp : dsDDP) {
//            if (ddp.getThoiGianNhanPhong().toLocalDate().equals(LocalDateTime.now().toLocalDate())) {
//                int gioNhan = ddp.getThoiGianNhanPhong().getDate();
//                int gioDat = LocalDateTime.now().getHour();
//                if (!(gioDat - gioNhan >= 6 || gioDat - gioNhan <= -6)) {
//                    maPhongDuocDatTruoc = ddp.getPhong().getMaPhong();
//
//                    for (Phong phong : phongTrongList) {
//                        if (phong.getMaPhong().equals(maPhongDuocDatTruoc)) {
//                            phongCanLoaiBo.add(phong);
//                        }
//                    }
//                }
//
//            }
//        }
//        phongTrongList.removeAll(phongCanLoaiBo);
//        for (Phong ph : phongTrongList) {
//            setLoaiPhong(ph);
//            dtm.addRow(new Object[]{
//                    ph.getMaPhong(),
//                    ph.getTenPhong(),
//                    loaiPhong,
//                    ph.getGiaTien()
//            });
//        }
//
//    }
//    private void setLoaiPhong(Phong ph) {
//        if (ph.getLoaiPhong().getMaLoai().equals("LP01")) {
//            loaiPhong = "Phòng Đơn";
//        }else if (ph.getLoaiPhong().getMaLoai().equals("LP02")) {
//            loaiPhong = "Phòng Đôi";
//        }else if (ph.getLoaiPhong().getMaLoai().equals("LP03")) {
//            loaiPhong = "Phòng Penthouse";
//        }
//    }
//
//}
