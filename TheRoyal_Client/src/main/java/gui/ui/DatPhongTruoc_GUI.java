package gui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import gui.format_ui.RadiusButton;
import lombok.SneakyThrows;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.toedter.calendar.JDateChooser;

import gui.format_ui.RadiusButton;
import gui.format_ui.RoundedBorder;
import gui.format_ui.Table;

import dao.*;
import entity.*;
import gui.dialog.ThongTinPhong_Dialog;
import rmi.RMIClient;
import service.*;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;

public class DatPhongTruoc_GUI extends JDialog implements ActionListener, MouseListener {

	private PhongService phongdao;
	private KhachHangService khachhangdao;
	private DonDatPhongService dondatphongdao;
	private HoaDonService hoadondao;
	private RadiusButton btnMore;
	private JButton btnThem, btnLamLai ;
	private JTextField txtSDT, txtTenKH, txtTrangThai, txtTim;
	private JDateChooser dateNhanPhong;
	private DefaultComboBoxModel<String> modelMaPhong, modelMaKH;
	private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
	private ArrayList<Phong> dsPhong;
	private ArrayList<KhachHang> dsKH;
	private ArrayList<DonDatPhong> dsDDP;
	private ArrayList<HoaDon> dsHD;
	private static final long serialVersionUID = 1;
	private JComboBox<String> cboLoaiKH;
	private JComboBox<String> cboGioiTinh;
	private JTextField txtCCCD;
	private JLabel lbShowMessages;
	private DanhSachPhong_GUI danhSachPhong;
	private DonDatPhong ddpp;
	private JComboBox<String> cboMaKH;
	private JCheckBox chkKH;
	private JTextField txtNglon;
	private JTextField txtTreEm;
	private JLabel lblSTrEm;
	private Table tblPhong;
	private DefaultTableModel tableModelPhong;
	private JTextField txtMPhong;
	private JDateChooser dateTraPhong;
	private Phong phong;
	private DonDatPhongDAO ddpDao;
	private ThongTinPhong_Dialog thongtindattruoc;
	private JLabel lblLoaiPhong_3;
	private JTextField txtLoaiPhong;
	private JLabel txtGia;
	private JTextField txtGiaPhong;
	private JLabel lblLoaiPhong_5;
	private JTextField txtSoGiuong;
	private LoaiPhongService lpDao;
	private JLabel hinhAnh;
	private JTextField txtTimKiemKH;
	private JLabel lblTmKim;
	/**
	 * Create the dialog.
	 */
	public DatPhongTruoc_GUI(DanhSachPhong_GUI danhSachPhong, Phong phong ,ThongTinPhong_Dialog thongtindattruoc, boolean modal) {
		super(thongtindattruoc,modal);
		getContentPane().setBackground(new Color(255, 255, 255));
		this.danhSachPhong = danhSachPhong;
		this.phong = phong;
		this.thongtindattruoc= thongtindattruoc;
	
		phongdao = RMIClient.getInstance().getPhongService();
		khachhangdao = RMIClient.getInstance().getKhachHangService();
		dondatphongdao = RMIClient.getInstance().getDonDatPhongService();
		hoadondao = RMIClient.getInstance().getHoaDonService();

		setBounds(100, 100, 1276, 721);
		setLocationRelativeTo(this);
		GUIQLYPhong();
		loadDataPhong();
		loadCboMaKhachHang();
		loadListKhachHang();
		loadDonDatPhongToTable(phong.getMaPhong());
	}

	@SneakyThrows
	private void GUIQLYPhong() {
		getContentPane().setLayout(null);
		JPanel staffInfoPanel = new JPanel();
		staffInfoPanel.setBackground(Color.white);
		staffInfoPanel.setBounds(10, 280, 597, 394);
		staffInfoPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Thông tin đặt phòng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

		JLabel lblMaNV = new JLabel("Tên khách hàng : ");
		lblMaNV.setBounds(5, 120, 141, 38);
		txtTenKH = new JTextField(15);
		txtTenKH.setEnabled(false);
		txtTenKH.setBounds(98, 120, 152, 38);
		// txtSoGiuong = new JTextField(15);

		staffInfoPanel.setLayout(null);

		staffInfoPanel.add(lblMaNV);
		staffInfoPanel.add(txtTenKH);

		JLabel lblTen = new JLabel("Số điện thoại:");
		lblTen.setBounds(5, 170, 141, 38);
		staffInfoPanel.add(lblTen);
		txtSDT = new JTextField(15);
		txtSDT.setEnabled(false);
		txtSDT.setBounds(98, 170, 152, 38);
		staffInfoPanel.add(txtSDT);
		modelMaPhong = new DefaultComboBoxModel<String>();

		dateNhanPhong = new JDateChooser();
		dateNhanPhong.setBounds(391, 70, 197, 38);
		dateNhanPhong.setDate(danhSachPhong.getSelectedDate());
		dateNhanPhong.setDateFormatString("dd-MM-yyyy");
		staffInfoPanel.add(dateNhanPhong);

		getContentPane().add(staffInfoPanel);

		JLabel lblSoTreEm_1 = new JLabel("Ngày nhận phòng:");
		lblSoTreEm_1.setBounds(260, 70, 121, 38);
		
		staffInfoPanel.add(lblSoTreEm_1);

		btnThem = new JButton("Đặt phòng", new ImageIcon(DatPhongTruoc_GUI.class.getResource("/src/icon/blueAdd_16.png")));
		btnThem.setBounds(303, 360, 132, 21);
		staffInfoPanel.add(btnThem);

		btnLamLai = new JButton("Làm Lại");
		btnLamLai.setBounds(445, 360, 145, 21);
		staffInfoPanel.add(btnLamLai);
		btnLamLai.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/src/icon/refresh_16.png")));

		txtCCCD = new JTextField(15);
		txtCCCD.setEnabled(false);
		txtCCCD.setBounds(98, 220, 152, 38);
		staffInfoPanel.add(txtCCCD);

		JLabel lblCnCcCng = new JLabel("Căn cước công dân:");
		lblCnCcCng.setBounds(5, 220, 141, 38);
		staffInfoPanel.add(lblCnCcCng);

		cboLoaiKH = new JComboBox<>(new String[] { "Thường", "VIP", "Khác" });
		cboLoaiKH.setEnabled(false);
		cboLoaiKH.setBounds(391, 170, 197, 38);
		staffInfoPanel.add(cboLoaiKH);

		JLabel lblLoaiPhong_1 = new JLabel("Loại khách hàng:");
		lblLoaiPhong_1.setBounds(260, 170, 121, 38);
		staffInfoPanel.add(lblLoaiPhong_1);

		JLabel lblLoaiPhong_1_1 = new JLabel("Giới tính:");
		lblLoaiPhong_1_1.setBounds(260, 220, 121, 38);
		staffInfoPanel.add(lblLoaiPhong_1_1);

		cboGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ" });
		cboGioiTinh.setEnabled(false);
		cboGioiTinh.setBounds(391, 220, 197, 38);
		staffInfoPanel.add(cboGioiTinh);

		lbShowMessages = new JLabel("");
		lbShowMessages.setBounds(5, 344, 295, 37);
		staffInfoPanel.add(lbShowMessages);

		chkKH = new JCheckBox("Thêm khách hàng mới");
		chkKH.setBounds(303, 332, 132, 21);
		staffInfoPanel.add(chkKH);

		JLabel lblMKhchHng = new JLabel("Mã khách hàng:");
		lblMKhchHng.setBounds(5, 70, 141, 38);
		staffInfoPanel.add(lblMKhchHng);
		modelMaKH = new DefaultComboBoxModel<String>();
		cboMaKH = new JComboBox<String>(modelMaKH);
		cboMaKH.setBounds(98, 70, 152, 38);
		staffInfoPanel.add(cboMaKH);

		JLabel lblSonguoiLon = new JLabel("Số người lớn:");
		lblSonguoiLon.setBounds(5, 270, 86, 38);
		staffInfoPanel.add(lblSonguoiLon);

		txtNglon = new JTextField(15);
		txtNglon.setEnabled(false);
		txtNglon.setBounds(98, 270, 152, 38);
		staffInfoPanel.add(txtNglon);

		lblSTrEm = new JLabel("Số trẻ em:");
		lblSTrEm.setBounds(270, 270, 86, 38);
		staffInfoPanel.add(lblSTrEm);

		txtTreEm = new JTextField(15);
		txtTreEm.setEnabled(false);
		txtTreEm.setBounds(391, 270, 152, 38);
		staffInfoPanel.add(txtTreEm);
		
		JLabel lblNgayTra = new JLabel("Ngày trả phòng:");
		lblNgayTra.setBounds(260, 120, 121, 38);
		staffInfoPanel.add(lblNgayTra);
		
		dateTraPhong = new JDateChooser();
		dateTraPhong.setDateFormatString("dd-MM-yyyy");
		dateTraPhong.setBounds(391, 120, 197, 38);
		staffInfoPanel.add(dateTraPhong);
		
		RoundedBorder panelTimKiemKH = new RoundedBorder(10);
		panelTimKiemKH.setBounds(98, 22, 459, 37);
		panelTimKiemKH.setBorderWidth(2);
		panelTimKiemKH.setBorderColor(Color.LIGHT_GRAY);
		panelTimKiemKH.setBackground(Color.WHITE);
		staffInfoPanel.add(panelTimKiemKH);
		panelTimKiemKH.setLayout(null);
		
		txtTimKiemKH = new JTextField();
		txtTimKiemKH.setBounds(10, 5, 439, 25);
		panelTimKiemKH.add(txtTimKiemKH);
		
		JLabel btnTimKiemKH = new JLabel("");
		btnTimKiemKH.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/src/icon/search.png")));
		btnTimKiemKH.setBounds(564, 22, 24, 32);
		staffInfoPanel.add(btnTimKiemKH);
		txtTimKiemKH.setBackground(Color.WHITE);
        txtTimKiemKH.setBorder(new MatteBorder(0,0,0,0, Color.black));
       
        PromptSupport.setPrompt("Tìm kiếm khách hàng(SDT hoặc CCCD)", txtTimKiemKH);
		txtTimKiemKH.setColumns(10);
		
		lblTmKim = new JLabel("Tìm kiếm khách:");
		lblTmKim.setBounds(5, 22, 141, 38);
		staffInfoPanel.add(lblTmKim);
		
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
		
		
//		RoundedBorder panelTimKiem = new RoundedBorder(10);
//		panelTimKiem.setLayout(null);
//		panelTimKiem.setBorderWidth(2);
//		panelTimKiem.setBorderColor(Color.LIGHT_GRAY);
//		panelTimKiem.setBackground(Color.WHITE);

//        staffInfoPanel.add(panelTimKiem);
//        panelTimKiem.add(txtTimKiemKH);
        
//		txtTimKiemKH = new JTextField();
//		txtTimKiemKH.setBounds(98, 24, 434, 35);
//		txtTimKiemKH.setBackground(Color.WHITE);
//        txtTimKiemKH.setBorder(new MatteBorder(0,0,0,0, Color.black));
//       
//        PromptSupport.setPrompt("Tìm kiếm khách hàng(SDT hoặc CCCD)", txtTimKiemKH);
//        txtTimKiemKH.setColumns(10);

		JPanel tabelPanelPhong = new JPanel();
		tabelPanelPhong.setBounds(617, 11, 633, 663);
		tabelPanelPhong.setBackground(Color.white);
		tabelPanelPhong
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Danh sách phòng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
		String[] columns = { "Mã Phòng", "Họ tên", "SDT", "Check in", "Check out", "Người lớn", "Trẻ em", "Trạng thái"};
		Object[][] data = {};
		tableModelPhong = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelPanelPhong.setLayout(null);
		
		tblPhong = new Table();
		tblPhong.setModel(tableModelPhong);
		tblPhong.setBackground(Color.white);

		JScrollPane scrollPane = new JScrollPane(tblPhong);
		scrollPane.setBounds(10, 20, 615, 632);
		tabelPanelPhong.add(scrollPane);
		getContentPane().add(tabelPanelPhong);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Th\u00F4ng tin ph\u00F2ng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 192, 203)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 597, 258);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblLoaiPhong_2 = new JLabel("Mã phòng:");
		lblLoaiPhong_2.setBounds(10, 29, 141, 38);
		panel.add(lblLoaiPhong_2);
		
				txtMPhong = new JTextField(15);
				txtMPhong.setBounds(98, 29, 152, 38);
				panel.add(txtMPhong);
				txtMPhong.setEnabled(false);
				txtMPhong.setText(phong.getMaPhong());
				
				lblLoaiPhong_3 = new JLabel("Loại phòng:");
				lblLoaiPhong_3.setBounds(10, 78, 141, 38);
				panel.add(lblLoaiPhong_3);
				
				txtLoaiPhong = new JTextField(15);
				txtLoaiPhong.setText((String) null);
				txtLoaiPhong.setEnabled(false);
				txtLoaiPhong.setBounds(98, 78, 152, 38);
				panel.add(txtLoaiPhong);
				
				txtGia = new JLabel("Giá phòng:");
				txtGia.setBounds(10, 127, 141, 38);
				panel.add(txtGia);
				
				txtGiaPhong = new JTextField(15);
				txtGiaPhong.setText((String) null);
				txtGiaPhong.setEnabled(false);
				txtGiaPhong.setBounds(98, 127, 152, 38);
				panel.add(txtGiaPhong);
				
				lblLoaiPhong_5 = new JLabel("Số giường:");
				lblLoaiPhong_5.setBounds(10, 175, 141, 38);
				panel.add(lblLoaiPhong_5);
				
				txtSoGiuong = new JTextField(15);
				txtSoGiuong.setText((String) null);
				txtSoGiuong.setEnabled(false);
				txtSoGiuong.setBounds(98, 176, 152, 38);
				panel.add(txtSoGiuong);
				
				lpDao = RMIClient.getInstance().getLoaiPhongService();
		
				LoaiPhong lp = lpDao.getLoaiPhongTheoMa(phong.getLoaiPhong().getMaLoai());
				txtLoaiPhong.setText(lp.getTenLoai());
				txtGiaPhong.setText(String.valueOf(phong.getGiaTien()));
				txtSoGiuong.setText(String.valueOf(phong.getSoGiuong()));
				
				hinhAnh = new JLabel("");
				
	
				
				if(lp.getMaLoai().equals("LP01")) {
					if(phong.getSoGiuong()==1) {
						hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/src/icon/phongThuongDon.png")));
					}else {
						hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/src/icon/phongThuongDoi.png")));
					}
				}else if(lp.getMaLoai().equals("LP02")) {
					if(phong.getSoGiuong()==1) {
						hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/src/icon/hinhPhongVipDon.png")));
					}else {
						hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/src/icon/hinhAnhPhongVipDoi.png")));
					}
				}else {
					hinhAnh.setIcon(new ImageIcon(DatPhongTruoc_GUI.class.getResource("/src/icon/hinhAnhPenthouse.png")));
				}
				
				hinhAnh.setBounds(276, 29, 300, 200);
				panel.add(hinhAnh);


		btnLamLai.addActionListener(this);
		btnThem.addActionListener(this);
		cboMaKH.addActionListener(this);
		chkKH.addActionListener(this);
		tblPhong.addMouseListener(this);

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
					 if (!dondatphongdao.isRoomAvailable(ddp.getPhong().getMaPhong(), (Date) ddp.getThoiGianDatPhong(), (Date) ddp.getThoiGianTraPhong())) {
					        showMessage("Phòng " + ddp.getPhong().getMaPhong() + " đã được đặt trong khoảng thời gian này, vui lòng chọn phòng khác!", ERROR);
					        return;
					    }

				} catch (Exception e1) {
					e1.printStackTrace();
					return;
				}

				boolean result1 = dondatphongdao.save(ddp);
				String maDDP = dondatphongdao.getLatestId();

				if (result1) {
					Date ngaynhan = convertFromJAVADateToSQLDate(ddp.getThoiGianNhanPhong());
					Date ngaytra = convertFromJAVADateToSQLDate(ddp.getThoiGianTraPhong());

					ddp = new DonDatPhong(maDDP, Date.valueOf(java.time.LocalDate.now()), ngaynhan, ngaytra, kh, new Phong(ddp.getPhong().getMaPhong()),
							"Đặt trước", ddp.getSoNguoiLon(), ddp.getSoTreEm(), null);
//		        } else {
//		            showMessage("Lỗi: Thêm đơn đặt phòng thất bại", ERROR);
//		            return;
				}

//		        String maHD = null;
//				try {
//					maHD = hoadondao.taoMaHoaDonTheoNgay();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}

//				try {
//					hd = new HoaDon(hoadondao.taoMaHoaDonTheoNgay(), kh, new Phong(ddp.getPhong().getMaPhong()),
//							new NhanVien(), ddp, new KhuyenMai(), new java.sql.Date(System.currentTimeMillis()),
//							phong.getGiaTien(), 0, 0, 0, 0, 0);
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}

//				boolean hdInserted = hoadondao.insert(hd);

				if (true) {
					dondatphongdao.updateTinhTrang(maDDP, ddp.getTrangThai());
//					phongdao.updateTinhTrang(phong, phong.getTrangThai());

					loadDataPhong();
					danhSachPhong.capNhatLaiDanhSachPhong();
					dispose();
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

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblPhong)) {
			int row = tblPhong.getSelectedRow();
			txtMPhong.setText(tableModelPhong.getValueAt(row, 0).toString());
			txtNglon.setText(tableModelPhong.getValueAt(row, 4).toString());
			txtTreEm.setText(tableModelPhong.getValueAt(row, 5).toString());

		}

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

	private DonDatPhong dataDDP(String maKH) {
		String maphong = txtMPhong.getText().trim();

		Date ngayDat = convertFromJAVADateToSQLDate(dateNhanPhong.getDate());
		Date ngayNhan = convertFromJAVADateToSQLDate(dateNhanPhong.getDate());
		Date ngayTra = convertFromJAVADateToSQLDate(dateTraPhong.getDate());
		int nglon = txtNglon.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtNglon.getText().trim());
		int treem = txtTreEm.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtTreEm.getText().trim());

		DonDatPhong ddp = new DonDatPhong("", ngayDat, ngayNhan, ngayTra, new KhachHang(maKH), new Phong(maphong), "",
				nglon, treem, null );
		return ddp;
	}

	private KhachHang dataKhachHang() {
		String tenKH = txtTenKH.getText().trim();
		String sDT = txtSDT.getText().trim();
		String loaiKH = cboLoaiKH.getSelectedItem().toString().trim();
		String cccd = txtCCCD.getText().trim();
		boolean gioiTinh = cboGioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam");

		KhachHang kh = new KhachHang("", tenKH, sDT, loaiKH, cccd, gioiTinh ,null ,null);
		return kh;
	}

	private String formatDate(java.util.Date ngayNhanDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(ngayNhanDate);
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
		Date ngaynhan = convertFromJAVADateToSQLDate(dateNhanPhong.getDate());
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
//
//		if (ngaydat == null) {
//			showMessage("Lỗi: Ngày đến không được để trống", ERROR);
//			return false;
//		}
//		if (ngaynhan == null) {
//			showMessage("Lỗi: Ngày đi không được để trống", ERROR);
//			return false;
//		}
//		if (ngayBD.before(ngayhientai)) {
//			showMessage("Lỗi: Ngày đến phải không được trước ngày hiện tại", ERROR);
//			return false;
//		}
		if (ngaynhan.before(ngayhientai)) {
			showMessage("Lỗi: Ngày nhận không được trước ngày đặt", ERROR);
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
		Date ngaydat = convertFromJAVADateToSQLDate(dateNhanPhong.getDate());
		Date ngaynhan = convertFromJAVADateToSQLDate(dateNhanPhong.getDate());
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
		if (ngaydat == null) {
			showMessage("Lỗi: Ngày đến không được để trống", ERROR);
			return false;
		}
		if (ngaynhan == null) {
			showMessage("Lỗi: Ngày đi không được để trống", ERROR);
			return false;
		}
//		if (ngayBD.before(ngayhientai)) {
//			showMessage("Lỗi: Ngày đến phải không được trước ngày hiện tại", ERROR);
//			return false;
//		}
		if (ngaynhan.before(ngaydat)) {
			showMessage("Lỗi: Ngày nhận không được trước ngày đặt", ERROR);
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

	private void DocDuLieuPhongVaoTable(ArrayList<Phong> dsPhong) {
		DefaultTableModel model = (DefaultTableModel) tblPhong.getModel();
		model.setRowCount(0);

		for (Phong phong : dsPhong) {
			
			  double number = phong.getGiaTien();
	                        String COUNTRY = "VN";
	                        String LANGUAGE = "vi";
	                        String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(number);
	            DecimalFormat df = new DecimalFormat("#,###.##");
	            String donGia = df.format(number);
			Object[] rowData = { phong.getMaPhong(), phong.getTenPhong(), phong.getLoaiPhong().getMaLoai(),phong.getSoGiuong(),phong.getSoNguoiLon(),phong.getSoTreEm(), str };
			model.addRow(rowData);
		}
	}
	
	@SneakyThrows
	public void loadDonDatPhongToTable(String maPhong) {
	    tableModelPhong.setRowCount(0);

	    List<DonDatPhong> listDonDatPhong = (List<DonDatPhong>) dondatphongdao.getDonDatPhongByRoomId(maPhong);
	    

	    if (!listDonDatPhong.isEmpty()) {
	        for (DonDatPhong ddp : listDonDatPhong) {
            	KhachHang kh = khachhangdao.getKhachHangTheoMa(ddp.getKhachHang().getMaKH());
            	
            	if(ddp.getTrangThai().equals("Đặt trước") || ddp.getTrangThai().equals("Đang ở")) {
	            	Object[] row = {
		                ddp.getPhong().getMaPhong(),
		                kh.getTenKH(),
		                kh.getSDT(),
		                ddp.getThoiGianNhanPhong(),
		                ddp.getThoiGianTraPhong(),
		                ddp.getSoNguoiLon(),
		                ddp.getSoTreEm(),
		                ddp.getTrangThai()
		            };
		            tableModelPhong.addRow(row);
            	}
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin đặt phòng cho mã phòng: " + maPhong);
	    }
	}
}
