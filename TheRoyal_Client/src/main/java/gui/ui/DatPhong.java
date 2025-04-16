package gui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;


import dao.*;
import entity.*;
import gui.dialog.ThongTinPhong_Dialog;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.border.EtchedBorder;
import gui.format_ui.RoundedBorder;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.*;
import service.impl.LoaiPhongServiceImpl;

import javax.swing.border.MatteBorder;

public class DatPhong extends JDialog implements ActionListener, MouseListener {

	private PhongService phongdao;
	private KhachHangService khachhangdao;
	private DonDatPhongService dondatphongdao;
	private HoaDonService hoadondao;
	private JButton btnThem, btnLamLai;
	private JTextField txtSDT, txtTenKH, txtTrangThai;
	private JDateChooser dateDatPhong;
	private DefaultComboBoxModel<String> modelMaPhong, modelMaKH;
	private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
	private ArrayList<Phong> dsPhong;
	private ArrayList<KhachHang> dsKH;
	private ArrayList<DonDatPhong> dsDDP;
	private ArrayList<HoaDon> dsHD;
	private static final long serialVersionUID = 1;
	private JDateChooser dateTraPhong;
	private JComboBox<String> cboLoaiKH;
	private JComboBox<String> cboGioiTinh;
	private JTextField txtCCCD;
	private JLabel lbShowMessages ,tieuDeLabel;
	private DanhSachPhong_GUI danhSachPhong;
	private DonDatPhong ddpp;
	private JComboBox<String> cboMaKH;
	private JCheckBox chkKH;
	private JTextField txtNglon;
	private JTextField txtTreEm;
	private JLabel lblSTrEm;
	private Phong phong1;
	private NhanVien nv = null;
	private ThongTinPhong_Dialog thongtinphong;
	private JPanel panel;
	private JLabel lblLoaiPhong_2;
	private JTextField txtMPhong;
	private JLabel lblLoaiPhong_3;
	private JTextField txtLoaiPhong;
	private JLabel txtGia;
	private JTextField txtGiaPhong;
	private JLabel lblLoaiPhong_4;
	private JTextField txtSoGiuong;
	private JLabel hinhAnh;
	private LoaiPhongDAO lpDao;
	private JLabel lblTmKim;
	private JTextField txtTimKiemKH;

	/**
	 * Create the dialog.
	 */
	public DatPhong(Phong phong1, DanhSachPhong_GUI danhSachPhong , ThongTinPhong_Dialog thongtinphong, boolean modal) {
		super(thongtinphong , modal);
		getContentPane().setBackground(new Color(255, 255, 255));
		this.danhSachPhong = danhSachPhong;
		this.phong1 = phong1;
		this.thongtinphong = thongtinphong;
		
		phongdao = RMIClient.getInstance().getPhongService();
		khachhangdao =RMIClient.getInstance().getKhachHangService();
		dondatphongdao = RMIClient.getInstance().getDonDatPhongService();
		hoadondao = RMIClient.getInstance().getHoaDonService();

		setBounds(100, 100, 810, 720);
		setLocationRelativeTo(this);
		GUIQLYPhong();
		loadDataPhong();
		loadDataPhongMoi(phong1);
		loadCboMaKhachHang();
		loadListKhachHang();
	}

	private void GUIQLYPhong() {
		getContentPane().setLayout(null);
		 tieuDeLabel = new JLabel("Đặt phòng");
	        tieuDeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
	        tieuDeLabel.setForeground(new Color(246, 167, 193));
	        tieuDeLabel.setBounds(321, 0, 112, 30);
	        getContentPane().add(tieuDeLabel);
		// Panel thông tin nhân viên
		JPanel staffInfoPanel = new JPanel();
		staffInfoPanel.setBackground(Color.white);
		staffInfoPanel.setBounds(31, 298, 724, 364);
		staffInfoPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Thông tin đặt phòng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

		JLabel lblMaNV = new JLabel("Tên khách hàng : ");
		lblMaNV.setBounds(5, 110, 141, 38);
		txtTenKH = new JTextField(15);
		txtTenKH.setEnabled(false);
		txtTenKH.setBounds(156, 110, 197, 38);
		// txtSoGiuong = new JTextField(15);

		staffInfoPanel.setLayout(null);

		staffInfoPanel.add(lblMaNV);
		staffInfoPanel.add(txtTenKH);

		JLabel lblTen = new JLabel("Số điện thoại:");
		lblTen.setBounds(5, 160, 141, 38);
		staffInfoPanel.add(lblTen);
		txtSDT = new JTextField(15);
		txtSDT.setEnabled(false);
		txtSDT.setBounds(156, 160, 197, 38);
		staffInfoPanel.add(txtSDT);
		modelMaPhong = new DefaultComboBoxModel<String>();

		JLabel lblSoTreEm = new JLabel("Ngày trả:");
		lblSoTreEm.setBounds(363, 110, 121, 38);
		staffInfoPanel.add(lblSoTreEm);

		dateDatPhong = new JDateChooser();
		dateDatPhong.setBounds(494, 60, 197, 38);
		dateDatPhong.setDateFormatString("dd-MM-yyyy");
		dateDatPhong.setDate(Date.valueOf(java.time.LocalDate.now()));
		staffInfoPanel.add(dateDatPhong);

		getContentPane().add(staffInfoPanel);

		dateTraPhong = new JDateChooser();
		dateTraPhong.setDateFormatString("dd-MM-yyyy");
		dateTraPhong.setBounds(494, 110, 197, 38);
		staffInfoPanel.add(dateTraPhong);

		JLabel lblSoTreEm_1 = new JLabel("Ngày đặt:");
		lblSoTreEm_1.setBounds(363, 60, 121, 38);
		staffInfoPanel.add(lblSoTreEm_1);

		btnThem = new JButton("Đặt phòng", new ImageIcon(DatPhong.class.getResource("/src/ICON/icon/blueAdd_16.png")));
		btnThem.setBounds(406, 332, 132, 21);
		staffInfoPanel.add(btnThem);

		btnLamLai = new JButton("Làm Lại");
		btnLamLai.setBounds(569, 332, 145, 21);
		staffInfoPanel.add(btnLamLai);
		btnLamLai.setIcon(new ImageIcon(DatPhong.class.getResource("/src/ICON/icon/refresh_16.png")));

		txtCCCD = new JTextField(15);
		txtCCCD.setEnabled(false);
		txtCCCD.setBounds(156, 210, 197, 38);
		staffInfoPanel.add(txtCCCD);

		JLabel lblCnCcCng = new JLabel("Căn cước công dân:");
		lblCnCcCng.setBounds(5, 210, 141, 38);
		staffInfoPanel.add(lblCnCcCng);

		cboLoaiKH = new JComboBox<>(new String[] { "Thường", "VIP", "Khác" });
		cboLoaiKH.setEnabled(false);
		cboLoaiKH.setBounds(494, 160, 197, 38);
		staffInfoPanel.add(cboLoaiKH);

		JLabel lblLoaiPhong_1 = new JLabel("Loại khách hàng:");
		lblLoaiPhong_1.setBounds(363, 160, 121, 38);
		staffInfoPanel.add(lblLoaiPhong_1);

		JLabel lblLoaiPhong_1_1 = new JLabel("Giới tính:");
		lblLoaiPhong_1_1.setBounds(363, 210, 121, 38);
		staffInfoPanel.add(lblLoaiPhong_1_1);

		cboGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ" });
		cboGioiTinh.setEnabled(false);
		cboGioiTinh.setBounds(494, 210, 197, 38);
		staffInfoPanel.add(cboGioiTinh);

		lbShowMessages = new JLabel("");
		lbShowMessages.setBounds(20, 293, 346, 37);
		staffInfoPanel.add(lbShowMessages);

		chkKH = new JCheckBox("Thêm khách hàng mới");
		chkKH.setBounds(406, 304, 132, 21);
		staffInfoPanel.add(chkKH);

		JLabel lblMKhchHng = new JLabel("Mã khách hàng:");
		lblMKhchHng.setBounds(5, 60, 141, 38);
		staffInfoPanel.add(lblMKhchHng);
		modelMaKH = new DefaultComboBoxModel<String>();
		cboMaKH = new JComboBox<String>(modelMaKH);
		cboMaKH.setBounds(156, 60, 197, 38);
		staffInfoPanel.add(cboMaKH);

		JLabel lblSonguoiLon = new JLabel("Số người lớn:");
		lblSonguoiLon.setBounds(5, 260, 105, 38);
		staffInfoPanel.add(lblSonguoiLon);

		txtNglon = new JTextField(15);
		txtNglon.setBounds(156, 260, 197, 38);
		int songuoilon = phong1.getSoNguoiLon();
		txtNglon.setText(Integer.toString(songuoilon));

		staffInfoPanel.add(txtNglon);

		lblSTrEm = new JLabel("Số trẻ em:");
		lblSTrEm.setBounds(363, 260, 86, 38);
		staffInfoPanel.add(lblSTrEm);

		txtTreEm = new JTextField(15);
		txtTreEm.setBounds(494, 260, 197, 38);
		int sotreem = phong1.getSoTreEm();
		txtTreEm.setText(Integer.toString(sotreem));

		staffInfoPanel.add(txtTreEm);
		
		lblTmKim = new JLabel("Tìm kiếm khách:");
		lblTmKim.setBounds(5, 28, 141, 21);
		staffInfoPanel.add(lblTmKim);
		
		RoundedBorder panelTimKiemKH = new RoundedBorder(10);
		panelTimKiemKH.setLayout(null);
		panelTimKiemKH.setBorderWidth(2);
		panelTimKiemKH.setBorderColor(Color.LIGHT_GRAY);
		panelTimKiemKH.setBackground(Color.WHITE);
		panelTimKiemKH.setBounds(155, 15, 459, 37);
		staffInfoPanel.add(panelTimKiemKH);
		
		txtTimKiemKH = new JTextField();
		txtTimKiemKH.setColumns(10);
		txtTimKiemKH.setBorder(new MatteBorder(0,0,0,0, Color.black));
		txtTimKiemKH.setBackground(Color.WHITE);
		txtTimKiemKH.setBounds(10, 5, 439, 25);
		panelTimKiemKH.add(txtTimKiemKH);
		
		JLabel btnTimKiemKH = new JLabel("");
		btnTimKiemKH.setIcon(new ImageIcon(DatPhong.class.getResource("/ICON/icon/search.png")));
		btnTimKiemKH.setBounds(621, 12, 24, 32);
		staffInfoPanel.add(btnTimKiemKH);
		
		btnTimKiemKH.addMouseListener(new MouseAdapter() {
        	@SneakyThrows
			@Override
        	public void mouseClicked(MouseEvent e) {

        		KhachHang kh = khachhangdao.getKhachHangTheoSDTHoacCCCD(txtTimKiemKH.getText());
        		if (kh == null) {
        		    JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        		} else {
        			cboMaKH.setSelectedItem(kh.getMaKH());
	        		txtTenKH.setText(kh.getTenKH());
					txtCCCD.setText(kh.getCCCD());
					txtSDT.setText(kh.getSDT());
					cboLoaiKH.setSelectedItem(kh.getLoaiKH());
					cboGioiTinh.setSelectedItem(kh.isGioiTinh());
        		}
        	}
        });
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Th\u00F4ng tin ph\u00F2ng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 192, 203)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(31, 39, 724, 258);
		getContentPane().add(panel);
		
		lblLoaiPhong_2 = new JLabel("Mã phòng:");
		lblLoaiPhong_2.setBounds(10, 29, 141, 38);
		panel.add(lblLoaiPhong_2);
		
		txtMPhong = new JTextField(15);
		txtMPhong.setText((String) null);
		txtMPhong.setEnabled(false);
		txtMPhong.setBounds(98, 29, 188, 38);
		panel.add(txtMPhong);
		
		lblLoaiPhong_3 = new JLabel("Loại phòng:");
		lblLoaiPhong_3.setBounds(10, 78, 141, 38);
		panel.add(lblLoaiPhong_3);
		
		txtLoaiPhong = new JTextField(15);
		txtLoaiPhong.setText((String) null);
		txtLoaiPhong.setEnabled(false);
		txtLoaiPhong.setBounds(98, 78, 188, 38);
		panel.add(txtLoaiPhong);
		
		txtGia = new JLabel("Giá phòng:");
		txtGia.setBounds(10, 127, 141, 38);
		panel.add(txtGia);
		
		txtGiaPhong = new JTextField(15);
		txtGiaPhong.setText((String) null);
		txtGiaPhong.setEnabled(false);
		txtGiaPhong.setBounds(98, 127, 188, 38);
		panel.add(txtGiaPhong);
		
		lblLoaiPhong_4 = new JLabel("Số giường:");
		lblLoaiPhong_4.setBounds(10, 175, 141, 38);
		panel.add(lblLoaiPhong_4);
		
		txtSoGiuong = new JTextField(15);
		txtSoGiuong.setText((String) null);
		txtSoGiuong.setEnabled(false);
		txtSoGiuong.setBounds(98, 176, 188, 38);
		panel.add(txtSoGiuong);
		
		hinhAnh = new JLabel("");
		hinhAnh.setBounds(338, 29, 300, 200);
		panel.add(hinhAnh);

		
		btnLamLai.addActionListener(this);
		btnThem.addActionListener(this);
		cboMaKH.addActionListener(this);
		chkKH.addActionListener(this);

	}

	@SneakyThrows
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnThem)) {
		    KhachHang kh = null;
		    DonDatPhong ddp = null;
		    HoaDon hd = null;
		    
		    if (chkKH.isSelected()) {
		        kh = dataKhachHang();
		        if (validData(ADD)) {
		            boolean result = khachhangdao.save(kh);
		            if (result) {
		                String maKH = khachhangdao.getLatestID();
		                kh = new KhachHang(maKH, kh.getTenKH(), kh.getSDT(), kh.getLoaiKH(), kh.getCCCD(), kh.isGioiTinh() , null , null);
		                showMessage("Khách hàng mới được thêm thành công!", SUCCESS);
		                loadListKhachHang();
		            } else {
		                showMessage("Lỗi: Thêm khách hàng mới thất bại", ERROR);
		                return;
		            }
		        }
		    } else {
		        int indx = cboMaKH.getSelectedIndex() - 1;
		        if (indx < 0) {
		            showMessage("Vui lòng chọn khách hàng từ danh sách!", ERROR);
		            return;
		        }
		        kh = dsKH.get(indx);
		    }

		    ddp = dataDDP(kh.getMaKH());
		    Phong phong = phongdao.getPhongByMaPhong(ddp.getPhong().getMaPhong());

		    if (validData2(ADD)) {
		        try {
		            if ("Đã đặt".equals(phong.getTrangThai())) {
		                showMessage("Phòng " + txtMPhong.getText().toString() + " đã được đặt, vui lòng chọn phòng khác!", ERROR);
		                return;
		            }
		        } catch (Exception e1) {
		            e1.printStackTrace();
		            return;
		        }

		        boolean result1 = dondatphongdao.save(ddp);
		        String maDDP = dondatphongdao.getLatestId();

		        if (result1) {
		            Date ngaydat = convertFromJAVADateToSQLDate(ddp.getThoiGianDatPhong());
		            Date ngaytra = convertFromJAVADateToSQLDate(ddp.getThoiGianTraPhong());

		            ddp = new DonDatPhong(maDDP, ngaydat, Date.valueOf(java.time.LocalDate.now()), ngaytra, kh,
		                                  new Phong(ddp.getPhong().getMaPhong()), "Đang ở", ddp.getSoTreEm(),
		                                  ddp.getSoNguoiLon(), null );
		        } else {
		            showMessage("Lỗi: Thêm đơn đặt phòng thất bại", ERROR);
		            return;
		        }
		        NhanVien nhanVienDangNhap = Login.getNhanVienDangNhap();



		            hd = new HoaDon(hoadondao.taoMaHoaDonTheoNgay(), kh, new Phong(ddp.getPhong().getMaPhong()),
		                           new NhanVien(  nhanVienDangNhap.getMaNV()), ddp, new KhuyenMai(), new Date(System.currentTimeMillis()),
		                            phong.getGiaTien(), 0, 0, 0, 0, 0, "Chưa thanh toán" , null);
//					hd = new HoaDon(hoadondao.taoMaHoaDonTheoNgay(), );


		        boolean hdInserted = hoadondao.save(hd);

		        if (hdInserted) {
		            dondatphongdao.updateTinhTrang(maDDP, ddp.getTrangThai());
//		            phong.setTrangThai("Đang ở");
		            phongdao.updateTinhTrang(String.valueOf(phong), phong.getTrangThai());

		            loadDataPhong();
		            danhSachPhong.capNhatLaiDanhSachPhong();
		            dispose();
		            thongtinphong.dis();
		            
		            DichVuSanPham_GUI dvsp = new DichVuSanPham_GUI(phong1, ddp, danhSachPhong, this.thongtinphong, true);
		            dvsp.setVisible(true);
		          
		        } else {
		            showMessage("Lỗi: Thêm hóa đơn thất bại", ERROR);
		        }
		    }
		}

		

		if (e.getSource().equals(cboMaKH)) {
			int indx = cboMaKH.getSelectedIndex();
			if (indx == 0) {
				txtTenKH.setText("");
				txtCCCD.setText("");
				txtSDT.setText("");
				cboLoaiKH.setSelectedIndex(0);
				cboGioiTinh.setSelectedIndex(0);
			} else {
				indx -= 1;
				txtTenKH.setText(dsKH.get(indx).getTenKH());
				txtCCCD.setText(dsKH.get(indx).getCCCD());
				txtSDT.setText(dsKH.get(indx).getSDT());
				cboLoaiKH.setSelectedItem(dsKH.get(indx).getLoaiKH());
				cboGioiTinh.setSelectedItem(dsKH.get(indx).isGioiTinh());
			}
		}

		if (e.getSource().equals(chkKH)) {
			if (chkKH.isSelected()) {
				txtTenKH.setEnabled(true);
				txtCCCD.setEnabled(true);
				cboLoaiKH.setEnabled(true);
				txtSDT.setEnabled(true);
				txtTenKH.requestFocus();
				cboGioiTinh.setEnabled(true);
				cboMaKH.setEnabled(false);
				txtNglon.setEnabled(true);
				txtTreEm.setEnabled(true);
			} else {
				txtTenKH.setEnabled(false);
				txtCCCD.setEnabled(false);
				txtSDT.setEnabled(false);
				cboLoaiKH.setEnabled(false);
				cboGioiTinh.setEnabled(false);
				cboMaKH.setEnabled(true);
				cboMaKH.requestFocus();
				txtNglon.setEnabled(true);
				txtTreEm.setEnabled(true);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@SneakyThrows
	private void loadCboMaKhachHang() {
		dsKH = (ArrayList<KhachHang>) khachhangdao.getAll();
		if (dsKH == null || dsKH.size() <= 0)
			return;
		cboMaKH.addItem("");
		for (KhachHang item : dsKH) {
			cboMaKH.addItem(item.getMaKH());
		}
	}

	@SneakyThrows
	private void loadDataPhong() {
		dsPhong = (ArrayList<Phong>) phongdao.getAll();
	}

	@SneakyThrows
	private void loadListKhachHang() {
		dsKH = (ArrayList<KhachHang>) khachhangdao.getAll();
	}

	private DonDatPhong dataDDP(String ma) {
		String maphong = txtMPhong.getText().trim();
		Date ngaydat = convertFromJAVADateToSQLDate(dateDatPhong.getDate());
		Date ngaytra = convertFromJAVADateToSQLDate(dateTraPhong.getDate());
//		String maKH = cboMaKH.getSelectedItem().toString().trim();
//		int selectedIndex = cboMaKH.getSelectedIndex();
//		   int maKHInt = Integer.parseInt(dsKH.get(selectedIndex).getMaKH());
//		   String maKH = String.valueOf(maKHInt);
		int nglon = txtNglon.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtNglon.getText().trim());
		int treem = txtTreEm.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtTreEm.getText().trim());

		DonDatPhong ddp = new DonDatPhong("", ngaydat, Date.valueOf(java.time.LocalDate.now()), ngaytra,
				new KhachHang(ma), new Phong(maphong), "", nglon, treem , null );
		return ddp;
	}

	private KhachHang dataKhachHang() {
		String tenKH = txtTenKH.getText().trim();
		String sDT = txtSDT.getText().trim();
		String loaiKH = cboLoaiKH.getSelectedItem().toString().trim();
		String cccd = txtCCCD.getText().trim();
		boolean gioiTinh = cboGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam");

		KhachHang kh = new KhachHang("", tenKH, sDT, loaiKH, cccd, gioiTinh , null , null);
		return kh;
	}

	private String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}

	public static Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
		Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}

	private void showMessage(String message, JTextField txt) {
		showMessage(message, ERROR);
		txt.requestFocus();
		txt.selectAll();

	}

	private void showMessage(String message, int type) {
		if (type == SUCCESS) {
			lbShowMessages.setForeground(Color.GREEN);
			// lbShowMessages.setIcon(checkIcon);
		} else if (type == ERROR) {
			lbShowMessages.setForeground(Color.RED);
			// lbShowMessages.setIcon(errorIcon);
		} else {
			lbShowMessages.setForeground(Color.BLACK);
			lbShowMessages.setIcon(null);
		}
		lbShowMessages.setText(message);
	}

	private boolean validData2(int type) {
		// type = 1 -> thêm mới
		// type != 1 -> cập nhật
		String tenKH = txtTenKH.getText().trim();
		String sDT = txtSDT.getText().trim();
		String cccd = txtCCCD.getText().trim();
		Date ngayBD = convertFromJAVADateToSQLDate(dateDatPhong.getDate());
		Date ngayDI = convertFromJAVADateToSQLDate(dateTraPhong.getDate());
		Date ngayhientai = new Date(System.currentTimeMillis());

		if (!(tenKH.length() > 0)) {
			showMessage("Lỗi: Tên không được để trống", txtTenKH);
			return false;
		}
		if (!tenKH.matches("^[^0-9]+$")) {
			showMessage("Lỗi: Tên không được có số", txtTenKH);
			return false;
		}
		if (!sDT.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
			showMessage("Lỗi: Phải đủ 10 số", txtSDT);
			return false;
		}

//		if (ngayBD == null) {
//			showMessage("Lỗi: Ngày đến không được để trống", ERROR);
//			return false;
//		}
		if (ngayDI == null) {
			showMessage("Lỗi: Ngày đi không được để trống", ERROR);
			return false;
		}
//		if (ngayBD.before(ngayhientai)) {
//			showMessage("Lỗi: Ngày đến phải không được trước ngày hiện tại", ERROR);
//			return false;
//		}
		if (ngayDI.before(ngayBD)) {
			showMessage("Lỗi: Ngày đi không được trước ngày đến", ERROR);
			return false;
		}
		if (!(cccd.length() > 0 && cccd.matches("^(\\d{12})$"))) {
			showMessage("Lỗi:  CCCD phải có 12 số", txtCCCD);
			return false;
		}

		return true;
	}

	private boolean validData(int type) {
		// type = 1 -> thêm mới
		// type != 1 -> cập nhật
		String tenKH = txtTenKH.getText().trim();
		String sDT = txtSDT.getText().trim();
		String cccd = txtCCCD.getText().trim();
		Date ngayBD = convertFromJAVADateToSQLDate(dateDatPhong.getDate());
		Date ngayDI = convertFromJAVADateToSQLDate(dateTraPhong.getDate());
		Date ngayhientai = new Date(System.currentTimeMillis());

		if (!(tenKH.length() > 0)) {
			showMessage("Lỗi: Tên không được để trống", txtTenKH);
			return false;
		}
		if (!tenKH.matches("^[^0-9]+$")) {
			showMessage("Lỗi: Tên không được có số", txtTenKH);
			return false;
		}
		if (!sDT.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
			showMessage("Lỗi: Phải đủ 10 số", txtSDT);
			return false;
		}
//		if (ngayBD == null) {
//			showMessage("Lỗi: Ngày đến không được để trống", ERROR);
//			return false;
//		}
		if (ngayDI == null) {
			showMessage("Lỗi: Ngày đi không được để trống", ERROR);
			return false;
		}
		if (ngayBD.before(ngayhientai)) {
			showMessage("Lỗi: Ngày đến phải không được trước ngày hiện tại", ERROR);
			return false;
		}
		if (ngayDI.before(ngayBD)) {
			showMessage("Lỗi: Ngày đi không được trước ngày đến", ERROR);
			return false;
		}

		if (!(cccd.length() > 0 && cccd.matches("^(\\d{12})$"))) {
			showMessage("Lỗi:  CCCD phải có 12 số", txtCCCD);
			return false;
		} else {
			if (type == ADD)
				for (KhachHang item : dsKH) {
					if (item.getCCCD().equalsIgnoreCase(cccd)) {
						showMessage("Lỗi: CCCD đã tồn tại", txtCCCD);
						return false;
					}
				}
		}

		return true;
	}
	
	@SneakyThrows
	private void loadDataPhongMoi(Phong phong) {
		LoaiPhongService loaiPhongService = RMIClient.getInstance().getLoaiPhongService();
		LoaiPhong lp = loaiPhongService.getLoaiPhongTheoMa(phong.getLoaiPhong().getMaLoai());
		txtMPhong.setText(phong.getMaPhong());
		txtLoaiPhong.setText(lp.getTenLoai());
		txtGiaPhong.setText(String.valueOf(phong.getGiaTien()));
		txtSoGiuong.setText(String.valueOf(phong.getSoGiuong()));

		if(lp.getMaLoai().equals("LP01")) {
			if(phong.getSoGiuong()==1) {
				hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/ICON/icon/phongThuongDon.png")));
			}else {
				hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/ICON/icon/phongThuongDoi.png")));
			}
		}else if(lp.getMaLoai().equals("LP02")) {
			if(phong.getSoGiuong()==1) {
				hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/ICON/icon/hinhPhongVipDon.png")));
			}else {
				hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/ICON/icon/hinhAnhPhongVipDoi.png")));
			}
		}else {
			hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/ICON/icon/hinhAnhPenthouse.png")));
		}
	}
}
