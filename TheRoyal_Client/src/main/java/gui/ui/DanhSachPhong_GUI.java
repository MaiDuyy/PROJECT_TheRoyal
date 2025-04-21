package gui.ui;

import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;



import com.toedter.calendar.JDateChooser;
import formatdate.FormatDate;
import gui.format_ui.RadiusButton;
import gui.format_ui.RoundedBorder;
import controller.TimDonDatDonDatPhong;

import entity.DonDatPhong;
import entity.HoaDon;
import entity.KhachHang;
import entity.Phong;
import gui.component.ButtonCustom;
import gui.component.panelPopUpMoreDatPhong;
import gui.dialog.ThongTinPhong_Dialog;
import gui.event.EventPopUpMoreDatPhong;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.*;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;

import java.awt.GridLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.DefaultComboBoxModel;

public class DanhSachPhong_GUI extends JInternalFrame implements ActionListener {

	private JPanel danhSachPhong;
	private PhongService phongService;
	private KhachHangService khachHangService;
	private JPanel pn_p_bottom;
	private LocalTime now;
	DateTimeFormatter formatter;
	private String formattedTime;
	private LocalDate today;
	private JLabel soPhongDangDon;
	private JLabel soPhongDatTruoc;
	private JLabel soPhongDangO;
	private JLabel soPhongTrong;
	public ButtonCustom btn_Tim;
	public JButton btnPhong;
	public JButton btn_ThanhToan;
	public JButton btn_VaoThue;
	public JButton btn_NhanPhong;
	public JButton btn_SuDungDV;
	public JButton btn_XemLichDat;
	public JButton btn_HuyDP;
	public JButton btn_traPhong;
	public JButton btn_donDep;
	private HoaDonService hoaDonService;
	private CTHoaDonService ctHoaDonService;
	private DonDatPhongService donDatPhongService;
	private List<DonDatPhong> dsDDP;
	private ArrayList<HoaDon> dsHD;
	private ArrayList<KhachHang> dsKH;
	private JTextField txtTim;
	private Phong phong;
	private DonDatPhong ddp;
	private JDialog dialog;
	private ThanhToan_GUI thanhtoan;
	private DichVuSanPham_GUI dichvusanpham;
	private RadiusButton btnMore;
	private panelPopUpMoreDatPhong panelPopUpMoreDatPhong1;
	private JLabel lblTatCa;
	private DatPhong datphong;
	private DatPhongTruoc_GUI datphongtruoc;
//	private DatNhieuPhong_GUI datnhieuphong;
	private ThongTinPhong_Dialog thongtinphongdialog;
	JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
	private KhachHang khachHang;
	private Date selectedDate;
	public JDateChooser date_DSPhong;
	private java.sql.Date sqlSelectedDate;
	public Date ngayHienTai;
	private JComboBox<String> cbxLuachon;
	private DefaultComboBoxModel<String> modelLuaChon;

	/**
	 * Create the frame.
	 */
	public DanhSachPhong_GUI() {
		phongService = RMIClient.getInstance().getPhongService();
		donDatPhongService = RMIClient.getInstance().getDonDatPhongService();
		khachHangService = RMIClient.getInstance().getKhachHangService();
		hoaDonService = RMIClient.getInstance().getHoaDonService();
		ctHoaDonService = RMIClient.getInstance().getCtHoaDonService();

		setBackground(new Color(41, 180, 55));
		getContentPane().setBackground(new Color(244, 164, 96));
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel jpanel_DSPhong = new JPanel();
		jpanel_DSPhong.setBackground(new Color(255, 255, 255));
		getContentPane().add(jpanel_DSPhong, BorderLayout.CENTER);

		JPanel jpanel_DSPhong_header = new JPanel();
		jpanel_DSPhong_header.setBackground(new Color(255, 255, 255));

		JPanel jpanel_DSPhong_content = new JPanel();
		GroupLayout gl_jpanel_DSPhong = new GroupLayout(jpanel_DSPhong);
		gl_jpanel_DSPhong.setHorizontalGroup(gl_jpanel_DSPhong.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_jpanel_DSPhong.createSequentialGroup().addGap(0)
						.addGroup(gl_jpanel_DSPhong.createParallelGroup(Alignment.TRAILING)
								.addComponent(jpanel_DSPhong_header, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jpanel_DSPhong_content, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1258,
										Short.MAX_VALUE))
						.addGap(0)));
		gl_jpanel_DSPhong.setVerticalGroup(gl_jpanel_DSPhong.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jpanel_DSPhong.createSequentialGroup()
						.addComponent(jpanel_DSPhong_header, GroupLayout.PREFERRED_SIZE, 105,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(jpanel_DSPhong_content, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)));
		JScrollPane scrollPane_DSPhong_content = new JScrollPane();
		scrollPane_DSPhong_content.getVerticalScrollBar().setUnitIncrement(20);

		// Lắng nghe sự kiện bánh xe chuột để tạo hiệu ứng cuộn mượt
		scrollPane_DSPhong_content.addMouseWheelListener(new MouseWheelListener() {
			private final Timer timer = new Timer(5, null); // Timer với độ trễ thấp để cuộn mượt
			private int targetPosition;

			{
				timer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JScrollBar bar = scrollPane_DSPhong_content.getVerticalScrollBar();
						int currentPosition = bar.getValue();
						int delta = (targetPosition - currentPosition) / 5; // Tốc độ cuộn mượt
						if (Math.abs(delta) < 1) {
							timer.stop();
						} else {
							bar.setValue(currentPosition + delta);
						}
					}
				});
			}

			public void mouseWheelMoved1(MouseWheelEvent e) {
				JScrollBar bar = scrollPane_DSPhong_content.getVerticalScrollBar();
				targetPosition = bar.getValue() + e.getUnitsToScroll() * 20; // Điều chỉnh độ nhạy
				if (!timer.isRunning()) {
					timer.start();
				}
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub

			}
		});
		scrollPane_DSPhong_content.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_DSPhong_content.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_jpanel_DSPhong_content = new GroupLayout(jpanel_DSPhong_content);
		gl_jpanel_DSPhong_content.setHorizontalGroup(gl_jpanel_DSPhong_content.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jpanel_DSPhong_content.createSequentialGroup().addGap(1)
						.addComponent(scrollPane_DSPhong_content, GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)));
		gl_jpanel_DSPhong_content.setVerticalGroup(gl_jpanel_DSPhong_content.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_DSPhong_content, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE));
		danhSachPhong = new JPanel();
		danhSachPhong.setBackground(new Color(255, 255, 204));
		scrollPane_DSPhong_content.setViewportView(danhSachPhong);
		danhSachPhong.setLayout(new GridLayout(0, 4, 20, 20));
		jpanel_DSPhong_content.setLayout(gl_jpanel_DSPhong_content);
		jpanel_DSPhong_header.setLayout(null);
		danhSachPhong.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		danhSachPhong.revalidate(); // Cập nhật layout
		danhSachPhong.repaint(); // Vẽ lại giao diện
		JPanel jpanel_DSPhong_header_left = new JPanel();
		jpanel_DSPhong_header_left.setBackground(new Color(255, 255, 255));
		jpanel_DSPhong_header_left.setBounds(0, 0, 539, 109);
		jpanel_DSPhong_header.add(jpanel_DSPhong_header_left);

		JPanel jpanel_date = new JPanel();
		jpanel_date.setBackground(new Color(255, 255, 255));
		jpanel_date.setLayout(null);

		JLabel lblNgy = new JLabel("Ngày");
		lblNgy.setBounds(59, 11, 70, 34);
		lblNgy.setFont(new Font("Arial", Font.PLAIN, 20));
		jpanel_date.add(lblNgy);

		date_DSPhong = new JDateChooser();
		date_DSPhong.getCalendarButton().setBackground(new Color(204, 255, 204));
		date_DSPhong.setToolTipText("");
		date_DSPhong.getCalendarButton()
				.setIcon(new ImageIcon("icon/date.png"));
		date_DSPhong.setDateFormatString("dd/MM/yyyy");
		date_DSPhong.setBounds(10, 56, 165, 42);
		Calendar calendar = Calendar.getInstance();
		date_DSPhong.setDate(calendar.getTime());
		selectedDate = date_DSPhong.getDate();
		sqlSelectedDate = new java.sql.Date(selectedDate.getTime());
		setSelectedDate(convertToSqlDate(selectedDate));
		
		//Su kien khi thay đoi jdatechooser 
		date_DSPhong.getDateEditor().addPropertyChangeListener("date", evt -> {
		  	Date selectedUtilDate = date_DSPhong.getDate();
		    java.sql.Date sqlDate = convertToSqlDate(selectedUtilDate);
	        setSelectedDate(sqlDate);
	        hienThiSoLuongTungPhong();
		    if (evt.getNewValue() != null) {
		        selectedDate = (Date) evt.getNewValue();
		        danhSachPhong.removeAll();
		        List<Phong> dsPhong = null;
				List<DonDatPhong> dsDDP = null;
				try {
					dsPhong = phongService.getAll();
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				;


				try {
					dsDDP = donDatPhongService.getAll();
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}


				java.sql.Date sqlSelectedDate = new java.sql.Date(selectedDate.getTime());

		        for (Phong phong : dsPhong) {
					String trangThai = null;
					try {
						trangThai = donDatPhongService.getTrangThaiPhongOThoiDiemChon(phong.getMaPhong(), sqlSelectedDate);
					} catch (RemoteException e) {
						throw new RuntimeException(e);
					}
					if(trangThai.equals("Đặt trước")) {
		                	hienThiPhongDatTruoc(phong);
		                }else if(trangThai.equals("Đang ở")) {
		                	hienThiPhongDangO(phong);
		                }else if(trangThai.equals("Phòng trống")||trangThai.equals("Hoàn tất") ){
		                	hienThiPhongTrong(phong);
		                }  else if(trangThai.equals("Đang dọn dẹp") ){
		                	hienThiPhongDangDon(phong);
		                }
		        }
		    }
		});

		// Thay đổi kích thước phông chữ cho JTextField hiển thị ngày
		date_DSPhong.getDateEditor().getUiComponent().setFont(new Font("Arial", Font.PLAIN, 18));

		// Thay đổi kích thước phông chữ cho JCalendar bên trong
		date_DSPhong.getJCalendar().setFont(new Font("Arial", Font.PLAIN, 18));
		jpanel_date.add(date_DSPhong);

		JPanel jpanel_loaiphong = new JPanel();
		jpanel_loaiphong.setBackground(new Color(255, 255, 255));
		jpanel_loaiphong.setLayout(null);

		JLabel lblLoiPhng = new JLabel("Loại phòng");
		lblLoiPhng.setBounds(23, 11, 131, 34);
		lblLoiPhng.setFont(new Font("Arial", Font.PLAIN, 20));
		jpanel_loaiphong.add(lblLoiPhng);
		GroupLayout gl_jpanel_DSPhong_header_left = new GroupLayout(jpanel_DSPhong_header_left);
		gl_jpanel_DSPhong_header_left.setHorizontalGroup(
			gl_jpanel_DSPhong_header_left.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_jpanel_DSPhong_header_left.createSequentialGroup()
					.addComponent(jpanel_date, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jpanel_loaiphong, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_jpanel_DSPhong_header_left.setVerticalGroup(
			gl_jpanel_DSPhong_header_left.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jpanel_DSPhong_header_left.createSequentialGroup()
					.addGroup(gl_jpanel_DSPhong_header_left.createParallelGroup(Alignment.LEADING)
						.addComponent(jpanel_date, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(jpanel_loaiphong, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Tất cả", "Thường", "Vip", "Penthouse" }));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		comboBox.setBounds(10, 56, 144, 42);
		jpanel_loaiphong.add(comboBox);
		
		JLabel lblTrngThi = new JLabel("Trạng thái");
		lblTrngThi.setFont(new Font("Arial", Font.PLAIN, 20));
		lblTrngThi.setBounds(177, 11, 131, 34);
		jpanel_loaiphong.add(lblTrngThi);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Tất cả", "Phòng trống", "Đặt trước", "Đang ở", "Đang dọn"}));
		comboBox_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		comboBox_1.setBounds(177, 56, 144, 42);
		jpanel_loaiphong.add(comboBox_1);

		// sukien combobox
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lấy giá trị đã chọn
				String selectedItem = (String) comboBox.getSelectedItem();
				// Kiểm tra giá trị đã chọn và thực hiện hành động tương ứng
				switch (selectedItem) {
				case "Thường":
					hienThiDanhSachPhongThuong(sqlSelectedDate);
					break;
				case "Vip":
					hienThiDanhSachPhongVip(sqlSelectedDate);
					break;
				case "Penthouse":
					hienThiDanhSachPhongPenthouse(sqlSelectedDate);
					break;
				case "Tất cả":
					hienThiDanhSachPhong();
					break;
				}
			}
		});
		jpanel_DSPhong_header_left.setLayout(gl_jpanel_DSPhong_header_left);

		JPanel jpanel_DSPhong_header_right = new JPanel();
		jpanel_DSPhong_header_right.setBackground(new Color(255, 255, 255));
		jpanel_DSPhong_header_right.setBounds(536, 0, 722, 109);
		jpanel_DSPhong_header.add(jpanel_DSPhong_header_right);
		jpanel_DSPhong_header_right.setLayout(null);

		 modelLuaChon = new DefaultComboBoxModel < String > (new String[] {
		            "--Tìm tất cả phòng--",
		            "Mã đơn đặt phòng",
		            "Số điện thoại khách hàng đang ở",
		            "Số điện thoại khách hàng đặt trước"
		        });
		        cbxLuachon = new JComboBox < String > (modelLuaChon);
		        cbxLuachon.setBounds(10, 34, 175, 29);
		        jpanel_DSPhong_header_right.add(cbxLuachon);
		        
		        
		        txtTim = new JTextField(15);
		        txtTim.setBounds(195, 34, 290, 29);
		        jpanel_DSPhong_header_right.add(txtTim);
		               btn_Tim = new ButtonCustom("Tìm Kiếm" , "success", 12);
		               btn_Tim.setBounds(491, 33, 107, 29);
		               btn_Tim.setIcon(new ImageIcon("icon/search_16.png"));
		               btn_Tim.addActionListener(this);
		        jpanel_DSPhong_header_right.add(btn_Tim);
		
		soPhongDatTruoc = new JLabel("(0) Đặt trước");
		soPhongDatTruoc.setForeground(new Color(240, 248, 255));
		soPhongDatTruoc.setOpaque(true);

		soPhongDatTruoc.setBackground(Color.RED);
		soPhongDatTruoc.setFont(new Font("Segoe UI", Font.BOLD, 16));
		soPhongDatTruoc.setBounds(145, 70, 120, 28);
		soPhongDatTruoc.setHorizontalAlignment(SwingConstants.CENTER);
		soPhongDatTruoc.setVerticalAlignment(SwingConstants.CENTER);
		jpanel_DSPhong_header_right.add(soPhongDatTruoc);
//		soPhongDatTruoc.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				danhSachPhong.removeAll();
//				hienThiDanhSachPhongDatTruoc();
//				danhSachPhong.repaint();
//			};
//		});

		soPhongDangO = new JLabel("(0) Đang ở");
		soPhongDangO.setForeground(Color.WHITE);
		soPhongDangO.setVerticalAlignment(SwingConstants.CENTER);
		soPhongDangO.setOpaque(true);
		soPhongDangO.setHorizontalAlignment(SwingConstants.CENTER);
		soPhongDangO.setFont(new Font("Segoe UI", Font.BOLD, 16));
		soPhongDangO.setBackground(new Color(255, 153, 51));
		soPhongDangO.setBounds(270, 70, 120, 28);
//		soPhongDangO.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				danhSachPhong.removeAll();
//				hienThiDanhSachPhongDangO(sqlSelectedDate);
//				danhSachPhong.repaint();
//			};
//		});
		jpanel_DSPhong_header_right.add(soPhongDangO);

		soPhongDangDon = new JLabel("(0) Đang dọn");
		soPhongDangDon.setForeground(Color.WHITE);
		soPhongDangDon.setVerticalAlignment(SwingConstants.CENTER);
		soPhongDangDon.setOpaque(true);
		soPhongDangDon.setHorizontalAlignment(SwingConstants.CENTER);
		soPhongDangDon.setFont(new Font("Segoe UI", Font.BOLD, 16));
		soPhongDangDon.setBackground(new Color(0, 204, 255));
		soPhongDangDon.setBounds(400, 70, 120, 28);
//		soPhongDangDon.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				danhSachPhong.removeAll();
//				hienThiDanhSachPhongDangDon();
//				danhSachPhong.repaint();
//			};
//		});
		jpanel_DSPhong_header_right.add(soPhongDangDon);

		soPhongTrong = new JLabel("(0) Phòng trống");
		soPhongTrong.setForeground(Color.WHITE);
		soPhongTrong.setVerticalAlignment(SwingConstants.CENTER);
		soPhongTrong.setOpaque(true);
		soPhongTrong.setHorizontalAlignment(SwingConstants.CENTER);
		soPhongTrong.setFont(new Font("Segoe UI", Font.BOLD, 16));
		soPhongTrong.setBackground(new Color(41, 180, 55));
		soPhongTrong.setBounds(10, 70, 130, 28);
//		soPhongTrong.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				danhSachPhong.removeAll();
//				hienThiDanhSachPhongTrong();
//				danhSachPhong.repaint();
//			};
//		});
		jpanel_DSPhong_header_right.add(soPhongTrong);

		JLabel lblNewLabel_1 = new JLabel("Có khách");
		lblNewLabel_1.setIcon(new ImageIcon("icon/khachdango24.png"));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 0, 100, 41);
		jpanel_DSPhong_header_right.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Phòng trống");
		lblNewLabel_1_1.setIcon(new ImageIcon("icon/phongtrong16.png"));
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(120, 0, 111, 41);
		jpanel_DSPhong_header_right.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Đang dọn");
		lblNewLabel_1_1_1.setIcon(new ImageIcon("icon/phongdangdon24.png"));
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel_1_1_1.setBounds(241, 1, 100, 41);
		jpanel_DSPhong_header_right.add(lblNewLabel_1_1_1);

		lblTatCa = new JLabel("(0) Tất cả");
		lblTatCa.setVerticalAlignment(SwingConstants.CENTER);
		lblTatCa.setOpaque(true);
		lblTatCa.setHorizontalAlignment(SwingConstants.CENTER);
		lblTatCa.setForeground(Color.WHITE);
		lblTatCa.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTatCa.setBackground(new Color(128, 0, 128));
		lblTatCa.setBounds(530, 70, 120, 28);
//		lblTatCa.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				danhSachPhong.removeAll();
//				hienThiDanhSachPhongTrong();
//				hienThiDanhSachPhongDatTruoc();
//				hienThiDanhSachPhongDangO(sqlSelectedDate);
//				hienThiDanhSachPhongDangDon();
//				danhSachPhong.repaint();
//			};
//		});
		jpanel_DSPhong_header_right.add(lblTatCa);
		jpanel_DSPhong.setLayout(gl_jpanel_DSPhong);

		btnMore = new RadiusButton();
		btnMore.setBounds(1227, 11, 68, 48);
		jpanel_DSPhong_header.add(btnMore);

		btnMore.setIcon(new ImageIcon("icon/square-plus.png"));

		btnMore.setBorder(null);

		btnMore.setFont(new Font("Segoe UI", 1, 12));
		btnMore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnMoreActionPerformed();

			}
		});

		setBounds(100, 100, 1258, 554);
		setVisible(true);
		setClosable(true);

		// Cài đặt cho Internal Frame
		setBorder(null); // Xóa viền
		setTitle(""); // Đặt title là chuỗi rỗng

		// Loại bỏ thanh tiêu đề
		BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
		ui.setNorthPane(null);
		// Khởi tạo dao để truy xuất dữ liệu


		// Hiển thị danh sách phòng
		hienThiDanhSachPhong();
		hienThiSoLuongTungPhong();
		setClosable(false);
		setIconifiable(false);
		setResizable(false);
		setMaximizable(false);

	}

	@SneakyThrows
	public void hienThiDanhSachPhong() {
		danhSachPhong.removeAll();
		List<Phong> dsPhong = null;
		dsPhong = phongService.getAll();

		// Duyệt qua danh sách phòng và tạo các panel cho mỗi phòng
		for (Phong phong : dsPhong) {
			hienThiPhong(phong, "");
		}
		danhSachPhong.revalidate();
		danhSachPhong.repaint();

	}

	@SneakyThrows
	private void hienThiDanhSachPhongThuong(java.sql.Date selectedDate) {
		danhSachPhong.removeAll();
		List<Phong> dsPhong = null;
		dsPhong = phongService.getAll();

		// Duyệt qua danh sách phòng và tạo các panel cho mỗi phòng
		for (Phong phong : dsPhong) {
			String maLoai = phong.getLoaiPhong().getMaLoai();
			if (maLoai.equals("LP01")) {
				hienThiPhong(phong, "");
			}

		}
	}

	@SneakyThrows
	private void hienThiDanhSachPhongVip(java.sql.Date selectedDate) {
		danhSachPhong.removeAll();
		List<Phong> dsPhong = null;
		dsPhong = phongService.getAll();

		// Duyệt qua danh sách phòng và tạo các panel cho mỗi phòng
		for (Phong phong : dsPhong) {
			String maLoai = phong.getLoaiPhong().getMaLoai();
			if (maLoai.equals("LP02")) {
				hienThiPhong(phong, "");
			}
			danhSachPhong.revalidate();
			danhSachPhong.repaint();
		}
	}

	@SneakyThrows
	private void hienThiDanhSachPhongPenthouse(java.sql.Date selectedDate) {
		danhSachPhong.removeAll();
		List<Phong> dsPhong = null;
		dsPhong = phongService.getAll();

		// Duyệt qua danh sách phòng và tạo các panel cho mỗi phòng
		for (Phong phong : dsPhong) {
			String maLoai = phong.getLoaiPhong().getMaLoai();
			if (maLoai.equals("LP03")) {
				hienThiPhong(phong, "");
			}
			danhSachPhong.revalidate();
			danhSachPhong.repaint();
		}
	}

	@SneakyThrows
	private void hienThiPhong(Phong phong, String th) {

		String maPhong = phong.getMaPhong(); // Lấy mã phòng
		String tenPhong = phong.getTenPhong(); // Lấy tên phòng
		String maLoai = phong.getLoaiPhong().getMaLoai(); // Lấy mã loại phòng
		int soGiuong = phong.getSoGiuong(); // Lấy số giường
		double giaTien = phong.getGiaTien(); // Lấy giá tiền
		int soNguoiLon = phong.getSoNguoiLon(); // Lấy số người lớn
		int soTreEm = phong.getSoTreEm(); // Lấy số trẻ em
		String trangThai = phong.getTrangThai();
		RoundedBorder panelPhong = new RoundedBorder(20);
		panelPhong.setPreferredSize(new Dimension(300, 250));
		
		Date utilDate = date_DSPhong.getDate();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	
        String trangThaiDDP = donDatPhongService.getTrangThaiPhongOThoiDiemChon(phong.getMaPhong(),sqlDate);
	
        if((!th.isEmpty() && th.equals("Đặt trước")) || (trangThaiDDP != null && trangThaiDDP.equals("Đặt trước"))) {
        	hienThiPhongDatTruoc2(phong);
//        	hienThiPhongDatTruoc(phong);

        }else if((!th.isEmpty() && th.equals("Đang ở")) || (trangThaiDDP != null && trangThaiDDP.equals("Đang ở"))) {
        	hienThiPhongDangO(phong);

        }else if((!th.isEmpty() && th.equals("Phòng trống")) || (trangThaiDDP != null && trangThaiDDP.equals("Phòng trống"))){
        	hienThiPhongTrong(phong);

        }else if((!th.isEmpty() && th.equals("Đang dọn dẹp")) || (trangThaiDDP != null && trangThaiDDP.equals("Đang dọn dẹp"))){
        	hienThiPhongDangDon(phong);
        }
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}

	private void hienThiPhongTrong(Phong phong) {
		String maPhong = phong.getMaPhong(); // Lấy mã phòng
		String tenPhong = phong.getTenPhong(); // Lấy tên phòng
		String maLoai = phong.getLoaiPhong().getMaLoai(); // Lấy mã loại phòng
		int soGiuong = phong.getSoGiuong(); // Lấy số giường
		double giaTien = phong.getGiaTien(); // Lấy giá tiền
		int soNguoiLon = phong.getSoNguoiLon(); // Lấy số người lớn
		int soTreEm = phong.getSoTreEm(); // Lấy số trẻ em
		String trangThai = phong.getTrangThai();
		RoundedBorder panelPhong = new RoundedBorder(20);
		panelPhong.setPreferredSize(new Dimension(300, 250));
		panelPhong.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				 new ThongTinPhong_Dialog(phong, ddp, DanhSachPhong_GUI.this, owner, true).setVisible(true);;
				

			}
		});

//		if ("Phòng trống".equals(trangThai)) {
			panelPhong.setBackground(new Color(41, 180, 55));
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(41, 180, 55));
			panel_1.setBounds(203, 0, 97, 65);
			panelPhong.add(panel_1);
			panel_1.setLayout(null);

			JLabel lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setBounds(40, 11, 24, 24);
			lblNewLabel_2.setIcon(new ImageIcon("icon/phongtrong24.png"));
			panel_1.add(lblNewLabel_2);

			JLabel lblNewLabel_1_1 = new JLabel("Phòng trống");
			lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel_1_1.setBounds(0, 29, 100, 36);
			panel_1.add(lblNewLabel_1_1);

			// Panel chứa thời gian
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(41, 180, 55));
			panel_2.setBounds(10, 192, 280, 58);
			panelPhong.add(panel_2);
			panel_2.setLayout(null);

			JLabel lblNewLabel_3 = new JLabel();
			now = LocalTime.now();

			// Định dạng giờ thành HH:mm (ví dụ: 20:15)
			formatter = DateTimeFormatter.ofPattern("HH:mm");
			formattedTime = now.format(formatter);
			lblNewLabel_3.setText(formattedTime);

			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setForeground(Color.WHITE);
			lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 28));
			lblNewLabel_3.setBackground(new Color(204, 255, 153));
			lblNewLabel_3.setBounds(153, 6, 100, 40);
			panel_2.add(lblNewLabel_3);

			JLabel lblNewLabel_1_2 = new JLabel();
			// Lấy ngày hiện tại
			today = LocalDate.now();

			// Định dạng ngày thành dd/MM/yy (ví dụ: 12/10/25)
			formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
			String formattedDate = today.format(formatter);
			lblNewLabel_1_2.setText(formattedDate);
			// Đặt ngày vào JLabel

			lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2.setForeground(Color.WHITE);
			lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.BOLD, 20));
			lblNewLabel_1_2.setBounds(42, 11, 100, 36);
			panel_2.add(lblNewLabel_1_2);

			// Panel chứa icon và viền tròn
			RoundedBorder panel_3 = new RoundedBorder(94);
			panel_3.setBackground(new Color(255, 255, 255));
			panel_3.setForeground(new Color(204, 255, 204));
			panel_3.setBounds(110, 70, 90, 90);
			panelPhong.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));

			JLabel lblNewLabel_4 = new JLabel();
			if (maLoai.equals("LP01")) {
				lblNewLabel_4.setText("T");
			} else if (maLoai.equals("LP02")) {
				lblNewLabel_4.setText("V");
			} else if (maLoai.equals("LP03")) {
				lblNewLabel_4.setText("P");
			}
			lblNewLabel_4.setForeground(new Color(41, 180, 55));
			lblNewLabel_4.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_4.setIcon(null);
			panel_3.add(lblNewLabel_4, BorderLayout.CENTER);

			// Panel chứa thông tin phòng
			JPanel panel = new JPanel();
			panel.setBackground(new Color(41, 180, 55));
			panel.setBounds(10, 0, 100, 65);
			panelPhong.add(panel);

			JLabel lblNewLabel = new JLabel();
			lblNewLabel.setText(tenPhong);
			lblNewLabel.setBackground(new Color(204, 255, 153));
			lblNewLabel.setBounds(0, 0, 100, 40);
			lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

			JLabel lblNewLabel_1 = new JLabel("Phòng đơn");
			lblNewLabel_1.setBounds(0, 29, 100, 36);
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel.setLayout(null);
			panel.add(lblNewLabel);
			panel.add(lblNewLabel_1);
			panelPhong.setLayout(null);

			danhSachPhong.add(panelPhong);
	//	}

		// Refresh lại giao diện
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}

	@SneakyThrows
	private void hienThiPhongDatTruoc2(Phong phong) {
		String maPhong = phong.getMaPhong(); // Lấy mã phòng
		String tenPhong = phong.getTenPhong(); // Lấy tên phòng
		String maLoai = phong.getLoaiPhong().getMaLoai(); // Lấy mã loại phòng
		int soGiuong = phong.getSoGiuong(); // Lấy số giường
		double giaTien = phong.getGiaTien(); // Lấy giá tiền
		int soNguoiLon = phong.getSoNguoiLon(); // Lấy số người lớn
		int soTreEm = phong.getSoTreEm(); // Lấy số trẻ em
		String trangThai = phong.getTrangThai();
		
		java.sql.Date ngayDaChon = getSelectedDate();
		
		DonDatPhong ddp = donDatPhongService.getDonDatTruocTheoPhongVaTrangThai(maPhong, "Đặt trước");
		if(ddp == null ) {
			return ;
		}
		RoundedBorder panelPhong = new RoundedBorder(20);
		panelPhong.setPreferredSize(new Dimension(300, 250));
		panelPhong.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				 ThongTinDatPhongTruoc_DL dl = new ThongTinDatPhongTruoc_DL(phong, DanhSachPhong_GUI.this ,owner, true);
				 dl.setVisible(true);

			}
		});
//		if ("Đã đặt".equals(trangThai)) {

			panelPhong.setBackground(Color.RED);
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.RED);
			panel_1.setBounds(203, 0, 97, 65);
			panelPhong.add(panel_1);
			panel_1.setLayout(null);

			JLabel lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setBounds(40, 11, 24, 24);
			lblNewLabel_2.setIcon(new ImageIcon("icon/phongtrong24.png"));
			panel_1.add(lblNewLabel_2);

			JLabel lblNewLabel_1_1 = new JLabel("Đặt trước");
			lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel_1_1.setBounds(0, 29, 100, 36);
			panel_1.add(lblNewLabel_1_1);

			// Panel chứa icon và viền tròn
			RoundedBorder panel_3 = new RoundedBorder(94);
			panel_3.setBackground(new Color(255, 255, 255));
			panel_3.setForeground(new Color(204, 255, 204));
			panel_3.setBounds(110, 70, 90, 90);
			panelPhong.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));

			JLabel lblNewLabel_4 = new JLabel();
			if (maLoai.equals("LP01")) {
				lblNewLabel_4.setText("T");
			} else if (maLoai.equals("LP02")) {
				lblNewLabel_4.setText("V");
			} else if (maLoai.equals("LP03")) {
				lblNewLabel_4.setText("P");
			}
			lblNewLabel_4.setForeground(Color.RED);
			lblNewLabel_4.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_4.setIcon(null);
			panel_3.add(lblNewLabel_4, BorderLayout.CENTER);

			// Panel chứa thông tin phòng
			JPanel panel = new JPanel();
			panel.setBackground(Color.RED);
			panel.setBounds(10, 0, 100, 65);
			panelPhong.add(panel);

			JLabel lblNewLabel = new JLabel();
			lblNewLabel.setText(tenPhong);
			lblNewLabel.setBackground(new Color(204, 255, 153));
			lblNewLabel.setBounds(0, 0, 100, 40);
			lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

			JLabel lblNewLabel_1 = new JLabel("Phòng đơn");
			lblNewLabel_1.setBounds(0, 29, 100, 36);
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel.setLayout(null);
			panel.add(lblNewLabel);
			panel.add(lblNewLabel_1);

			panelPhong.setLayout(null);

			JPanel panel_4 = new JPanel();
			panel_4.setBackground(Color.RED);
			panel_4.setBounds(10, 81, 90, 100);
			panelPhong.add(panel_4);
			panel_4.setLayout(null);

			JLabel lblNewLabel_5 = new JLabel("");
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_5.setIcon(new ImageIcon("icon/oclock.png"));
			lblNewLabel_5.setBounds(0, 0, 90, 41);
			panel_4.add(lblNewLabel_5);

			JLabel thoiGianDatPhong = new JLabel("12:00");
			thoiGianDatPhong.setForeground(Color.WHITE);
			thoiGianDatPhong.setFont(new Font("Segoe UI", Font.BOLD, 20));
			thoiGianDatPhong.setHorizontalAlignment(SwingConstants.CENTER);
			thoiGianDatPhong.setBounds(0, 31, 90, 41);
			panel_4.add(thoiGianDatPhong);

			JLabel ngayDatPhong = new JLabel("");
			ngayDatPhong.setHorizontalAlignment(SwingConstants.CENTER);
			ngayDatPhong.setForeground(Color.WHITE);
			ngayDatPhong.setFont(new Font("Segoe UI", Font.BOLD, 16));
			ngayDatPhong.setBounds(0, 60, 90, 36);
			panel_4.add(ngayDatPhong);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = dateFormat.format(ddp.getThoiGianNhanPhong());
			ngayDatPhong.setText(formattedDate); 
			
			JPanel panel_4_1 = new JPanel();
			panel_4_1.setLayout(null);
			panel_4_1.setBackground(Color.RED);
			panel_4_1.setBounds(210, 81, 90, 100);
			panelPhong.add(panel_4_1);

			JLabel lblNewLabel_5_1 = new JLabel("");
			lblNewLabel_5_1.setIcon(new ImageIcon("icon/oclock.png"));
			lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_5_1.setBounds(0, 0, 90, 41);
			panel_4_1.add(lblNewLabel_5_1);

			JLabel thoiGianNhanPhong = new JLabel("12:00");
			thoiGianNhanPhong.setHorizontalAlignment(SwingConstants.CENTER);
			thoiGianNhanPhong.setForeground(Color.WHITE);
			thoiGianNhanPhong.setFont(new Font("Segoe UI", Font.BOLD, 20));
			thoiGianNhanPhong.setBounds(0, 31, 90, 41);
			panel_4_1.add(thoiGianNhanPhong);

			JLabel ngayNhanPhong = new JLabel("");
			ngayNhanPhong.setHorizontalAlignment(SwingConstants.CENTER);
			ngayNhanPhong.setForeground(Color.WHITE);
			ngayNhanPhong.setFont(new Font("Segoe UI", Font.BOLD, 16));
			ngayNhanPhong.setBounds(0, 60, 90, 36);
			panel_4_1.add(ngayNhanPhong);

			String formattedDate1 = dateFormat.format(ddp.getThoiGianTraPhong());
			ngayNhanPhong.setText(formattedDate1); 
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(Color.RED);
			panel_2.setBounds(0, 175, 300, 65);
			panelPhong.add(panel_2);
			panel_2.setLayout(null);

			JPanel panel_5 = new JPanel();
			panel_5.setBackground(Color.RED);
			panel_5.setBounds(10, 0, 118, 65);
			panel_2.add(panel_5);
			panel_5.setLayout(null);

			JLabel lblNewLabel_1_2_1_2 = new JLabel("SDT");
			lblNewLabel_1_2_1_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2_1_2.setForeground(Color.WHITE);
			lblNewLabel_1_2_1_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNewLabel_1_2_1_2.setBounds(0, 5, 90, 36);
			panel_5.add(lblNewLabel_1_2_1_2);

			JLabel lblNewLabel_1_2_1_2_1 = new JLabel("");
			lblNewLabel_1_2_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2_1_2_1.setForeground(Color.WHITE);
			lblNewLabel_1_2_1_2_1.setFont(new Font("Segoe UI", Font.BOLD, 17));
			lblNewLabel_1_2_1_2_1.setBounds(0, 29, 118, 36);
			panel_5.add(lblNewLabel_1_2_1_2_1);
			KhachHang kh = khachHangService.getKhachHangTheoMa(ddp.getKhachHang().getMaKH());
			lblNewLabel_1_2_1_2_1.setText(kh.getSDT());
			
			JPanel panel_5_1 = new JPanel();
			panel_5_1.setLayout(null);
			panel_5_1.setBackground(Color.RED);
			panel_5_1.setBounds(170, 0, 130, 65);
			panel_2.add(panel_5_1);

			JLabel lblNewLabel_1_2_1_2_2 = new JLabel("Thời gian");
			lblNewLabel_1_2_1_2_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2_1_2_2.setForeground(Color.WHITE);
			lblNewLabel_1_2_1_2_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNewLabel_1_2_1_2_2.setBounds(25, 5, 105, 36);
			panel_5_1.add(lblNewLabel_1_2_1_2_2);

			JLabel lblNewLabel_1_2_1_2_1_1 = new JLabel("");
			lblNewLabel_1_2_1_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2_1_2_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_2_1_2_1_1.setFont(new Font("Segoe UI", Font.BOLD, 17));
			lblNewLabel_1_2_1_2_1_1.setBounds(20, 29, 110, 36);
			panel_5_1.add(lblNewLabel_1_2_1_2_1_1);

		LocalDateTime nhanPhongLocal = LocalDate.parse(FormatDate.formatDate(ddp.getThoiGianNhanPhong()),
						DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				.atStartOfDay();
			nhanPhongLocal = nhanPhongLocal.plusHours(12); 

			LocalDateTime now = LocalDateTime.now();

			Duration duration = Duration.between(now, nhanPhongLocal);

			String labelText = "";

			// Kiểm tra xem đã qua thời gian nhận phòng chưa
			if (duration.isNegative()) {
			    // Nếu đã qua thời gian nhận phòng, tính thời gian trễ
			    duration = duration.negated(); // Lấy giá trị dương của duration
			    
			    long days = duration.toDays();
			    long hours = duration.toHours() % 24; // Lấy số giờ dư
			    long minutes = duration.toMinutes() % 60; // Lấy số phút dư
			    labelText = "Trễ: " + days + "N:" + hours + "H:" + minutes + "p";
			} else {
			    // Nếu chưa qua thời gian nhận phòng, hiển thị thời gian còn lại
			    long days = duration.toDays();
			    long hours = duration.toHours() % 24;
			    long minutes = duration.toMinutes() % 60;
			    labelText = days + "N:" + hours + "H:" + minutes + "p";
			}

			// Cập nhật label với thông tin
			lblNewLabel_1_2_1_2_1_1.setText(labelText);

			panelPhong.setLayout(null);

			danhSachPhong.add(panelPhong);
//		}
		// Refresh lại giao diện
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}
	@SneakyThrows
	private void hienThiPhongDatTruoc(Phong phong) {
		String maPhong = phong.getMaPhong(); // Lấy mã phòng
		String tenPhong = phong.getTenPhong(); // Lấy tên phòng
		String maLoai = phong.getLoaiPhong().getMaLoai(); // Lấy mã loại phòng
		int soGiuong = phong.getSoGiuong(); // Lấy số giường
		double giaTien = phong.getGiaTien(); // Lấy giá tiền
		int soNguoiLon = phong.getSoNguoiLon(); // Lấy số người lớn
		int soTreEm = phong.getSoTreEm(); // Lấy số trẻ em
		String trangThai = phong.getTrangThai();
		
		Date ngayDaChon = getSelectedDate();
		
		DonDatPhong ddp = donDatPhongService.getDonDatTruocTheoPhongVaNgay(maPhong, ngayDaChon);
		if(ddp == null ) {
			return ;
		}
		RoundedBorder panelPhong = new RoundedBorder(20);
		panelPhong.setPreferredSize(new Dimension(300, 250));
		panelPhong.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				 ThongTinDatPhongTruoc_DL dl = new ThongTinDatPhongTruoc_DL(phong, DanhSachPhong_GUI.this ,owner, true);
				 dl.setVisible(true);

			}
		});
//		if ("Đã đặt".equals(trangThai)) {

			panelPhong.setBackground(Color.RED);
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.RED);
			panel_1.setBounds(203, 0, 97, 65);
			panelPhong.add(panel_1);
			panel_1.setLayout(null);

			JLabel lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setBounds(40, 11, 24, 24);
			lblNewLabel_2.setIcon(new ImageIcon("icon/phongtrong24.png"));
			panel_1.add(lblNewLabel_2);

			JLabel lblNewLabel_1_1 = new JLabel("Đặt trước");
			lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel_1_1.setBounds(0, 29, 100, 36);
			panel_1.add(lblNewLabel_1_1);

			// Panel chứa icon và viền tròn
			RoundedBorder panel_3 = new RoundedBorder(94);
			panel_3.setBackground(new Color(255, 255, 255));
			panel_3.setForeground(new Color(204, 255, 204));
			panel_3.setBounds(110, 70, 90, 90);
			panelPhong.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));

			JLabel lblNewLabel_4 = new JLabel();
			if (maLoai.equals("LP01")) {
				lblNewLabel_4.setText("T");
			} else if (maLoai.equals("LP02")) {
				lblNewLabel_4.setText("V");
			} else if (maLoai.equals("LP03")) {
				lblNewLabel_4.setText("P");
			}
			lblNewLabel_4.setForeground(Color.RED);
			lblNewLabel_4.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_4.setIcon(null);
			panel_3.add(lblNewLabel_4, BorderLayout.CENTER);

			// Panel chứa thông tin phòng
			JPanel panel = new JPanel();
			panel.setBackground(Color.RED);
			panel.setBounds(10, 0, 100, 65);
			panelPhong.add(panel);

			JLabel lblNewLabel = new JLabel();
			lblNewLabel.setText(tenPhong);
			lblNewLabel.setBackground(new Color(204, 255, 153));
			lblNewLabel.setBounds(0, 0, 100, 40);
			lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

			JLabel lblNewLabel_1 = new JLabel("Phòng đơn");
			lblNewLabel_1.setBounds(0, 29, 100, 36);
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel.setLayout(null);
			panel.add(lblNewLabel);
			panel.add(lblNewLabel_1);

			panelPhong.setLayout(null);

			JPanel panel_4 = new JPanel();
			panel_4.setBackground(Color.RED);
			panel_4.setBounds(10, 81, 90, 100);
			panelPhong.add(panel_4);
			panel_4.setLayout(null);

			JLabel lblNewLabel_5 = new JLabel("");
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_5.setIcon(new ImageIcon("icon/oclock.png"));
			lblNewLabel_5.setBounds(0, 0, 90, 41);
			panel_4.add(lblNewLabel_5);

			JLabel thoiGianDatPhong = new JLabel("12:00");
			thoiGianDatPhong.setForeground(Color.WHITE);
			thoiGianDatPhong.setFont(new Font("Segoe UI", Font.BOLD, 20));
			thoiGianDatPhong.setHorizontalAlignment(SwingConstants.CENTER);
			thoiGianDatPhong.setBounds(0, 31, 90, 41);
			panel_4.add(thoiGianDatPhong);

			JLabel ngayDatPhong = new JLabel("");
			ngayDatPhong.setHorizontalAlignment(SwingConstants.CENTER);
			ngayDatPhong.setForeground(Color.WHITE);
			ngayDatPhong.setFont(new Font("Segoe UI", Font.BOLD, 16));
			ngayDatPhong.setBounds(0, 60, 90, 36);
			panel_4.add(ngayDatPhong);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = dateFormat.format(ddp.getThoiGianNhanPhong());
			ngayDatPhong.setText(formattedDate); 
			
			JPanel panel_4_1 = new JPanel();
			panel_4_1.setLayout(null);
			panel_4_1.setBackground(Color.RED);
			panel_4_1.setBounds(210, 81, 90, 100);
			panelPhong.add(panel_4_1);

			JLabel lblNewLabel_5_1 = new JLabel("");
			lblNewLabel_5_1.setIcon(new ImageIcon("icon/oclock.png"));
			lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_5_1.setBounds(0, 0, 90, 41);
			panel_4_1.add(lblNewLabel_5_1);

			JLabel thoiGianNhanPhong = new JLabel("12:00");
			thoiGianNhanPhong.setHorizontalAlignment(SwingConstants.CENTER);
			thoiGianNhanPhong.setForeground(Color.WHITE);
			thoiGianNhanPhong.setFont(new Font("Segoe UI", Font.BOLD, 20));
			thoiGianNhanPhong.setBounds(0, 31, 90, 41);
			panel_4_1.add(thoiGianNhanPhong);

			JLabel ngayNhanPhong = new JLabel("");
			ngayNhanPhong.setHorizontalAlignment(SwingConstants.CENTER);
			ngayNhanPhong.setForeground(Color.WHITE);
			ngayNhanPhong.setFont(new Font("Segoe UI", Font.BOLD, 16));
			ngayNhanPhong.setBounds(0, 60, 90, 36);
			panel_4_1.add(ngayNhanPhong);

			String formattedDate1 = dateFormat.format(ddp.getThoiGianTraPhong());
			ngayNhanPhong.setText(formattedDate1); 
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(Color.RED);
			panel_2.setBounds(0, 175, 300, 65);
			panelPhong.add(panel_2);
			panel_2.setLayout(null);

			JPanel panel_5 = new JPanel();
			panel_5.setBackground(Color.RED);
			panel_5.setBounds(10, 0, 118, 65);
			panel_2.add(panel_5);
			panel_5.setLayout(null);

			JLabel lblNewLabel_1_2_1_2 = new JLabel("SDT");
			lblNewLabel_1_2_1_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2_1_2.setForeground(Color.WHITE);
			lblNewLabel_1_2_1_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNewLabel_1_2_1_2.setBounds(0, 5, 90, 36);
			panel_5.add(lblNewLabel_1_2_1_2);

			JLabel lblNewLabel_1_2_1_2_1 = new JLabel("");
			lblNewLabel_1_2_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2_1_2_1.setForeground(Color.WHITE);
			lblNewLabel_1_2_1_2_1.setFont(new Font("Segoe UI", Font.BOLD, 17));
			lblNewLabel_1_2_1_2_1.setBounds(0, 29, 118, 36);
			panel_5.add(lblNewLabel_1_2_1_2_1);

			KhachHang kh = khachHangService.getKhachHangTheoMa(ddp.getKhachHang().getMaKH());
			lblNewLabel_1_2_1_2_1.setText(kh.getSDT());
			
			JPanel panel_5_1 = new JPanel();
			panel_5_1.setLayout(null);
			panel_5_1.setBackground(Color.RED);
			panel_5_1.setBounds(170, 0, 130, 65);
			panel_2.add(panel_5_1);

			JLabel lblNewLabel_1_2_1_2_2 = new JLabel("Thời gian");
			lblNewLabel_1_2_1_2_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2_1_2_2.setForeground(Color.WHITE);
			lblNewLabel_1_2_1_2_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNewLabel_1_2_1_2_2.setBounds(25, 5, 105, 36);
			panel_5_1.add(lblNewLabel_1_2_1_2_2);

			JLabel lblNewLabel_1_2_1_2_1_1 = new JLabel("");
			lblNewLabel_1_2_1_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_2_1_2_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_2_1_2_1_1.setFont(new Font("Segoe UI", Font.BOLD, 17));
			lblNewLabel_1_2_1_2_1_1.setBounds(20, 29, 110, 36);
			panel_5_1.add(lblNewLabel_1_2_1_2_1_1);
			LocalDateTime nhanPhongLocal = LocalDate.parse(FormatDate.formatDate(ddp.getThoiGianNhanPhong()),
						DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				.atStartOfDay();

		nhanPhongLocal = nhanPhongLocal.plusHours(12);

			LocalDateTime now = LocalDateTime.now();

			Duration duration = Duration.between(now, nhanPhongLocal);

			String labelText = "";

			// Kiểm tra xem đã qua thời gian nhận phòng chưa
			if (duration.isNegative()) {
			    // Nếu đã qua thời gian nhận phòng, tính thời gian trễ
			    duration = duration.negated(); // Lấy giá trị dương của duration
			    
			    long days = duration.toDays();
			    long hours = duration.toHours() % 24; // Lấy số giờ dư
			    long minutes = duration.toMinutes() % 60; // Lấy số phút dư
			    labelText = "Trễ: " + days + "N:" + hours + "H:" + minutes + "p";
			} else {
			    // Nếu chưa qua thời gian nhận phòng, hiển thị thời gian còn lại
			    long days = duration.toDays();
			    long hours = duration.toHours() % 24;
			    long minutes = duration.toMinutes() % 60;
			    labelText = days + "N:" + hours + "H:" + minutes + "p";
			}

			// Cập nhật label với thông tin
			lblNewLabel_1_2_1_2_1_1.setText(labelText);

			panelPhong.setLayout(null);

			danhSachPhong.add(panelPhong);
//		}
		// Refresh lại giao diện
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}
	@SneakyThrows
	private void hienThiPhongDangO(Phong phong) {
		String maPhong = phong.getMaPhong();
		String tenPhong = phong.getTenPhong();
		String maLoai = phong.getLoaiPhong().getMaLoai();
		String trangThai = phong.getTrangThai();

		// Lấy đơn đặt phòng "Đang ở"
		DonDatPhong ddp = donDatPhongService.getDonDatTruocTheoPhongVaTrangThai(maPhong, "Đang ở");
		if (ddp == null) {
			System.out.println("Không tìm thấy đơn đặt phòng đang ở cho phòng " + maPhong);
			return;
		}
//		if (!"Đang ở".equals(trangThai)) {
//			System.out.println("Phòng " + maPhong + " không có trạng thái 'Đang ở'");
//			return;
//		}

		// Panel phòng
		RoundedBorder panelPhong = new RoundedBorder(20);
		panelPhong.setPreferredSize(new Dimension(300, 250));
		panelPhong.setBackground(new Color(255, 153, 51));
		panelPhong.setLayout(null);
		panelPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ThongTinPhong_Dialog(phong, ddp, DanhSachPhong_GUI.this, owner, true).setVisible(true);
			}
		});

		// Góc phải "Có khách"
		JPanel panelTrangThai = new JPanel(null);
		panelTrangThai.setBackground(new Color(255, 153, 51));
		panelTrangThai.setBounds(203, 0, 97, 65);
		panelPhong.add(panelTrangThai);

		JLabel iconTrangThai = new JLabel(new ImageIcon("icon/khachdango24.png"));
		iconTrangThai.setBounds(40, 11, 24, 24);
		panelTrangThai.add(iconTrangThai);

		JLabel lblTrangThai = new JLabel("Có khách", SwingConstants.CENTER);
		lblTrangThai.setForeground(Color.WHITE);
		lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTrangThai.setBounds(0, 29, 100, 36);
		panelTrangThai.add(lblTrangThai);

		// Tên phòng
		JPanel panelTenPhong = new JPanel(null);
		panelTenPhong.setBackground(new Color(255, 153, 51));
		panelTenPhong.setBounds(10, 0, 100, 65);
		panelPhong.add(panelTenPhong);

		JLabel lblTenPhong = new JLabel(tenPhong, SwingConstants.CENTER);
		lblTenPhong.setForeground(Color.WHITE);
		lblTenPhong.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblTenPhong.setBounds(0, 0, 100, 40);
		panelTenPhong.add(lblTenPhong);

		JLabel lblLoaiPhong = new JLabel("Phòng đơn", SwingConstants.CENTER);
		lblLoaiPhong.setForeground(Color.WHITE);
		lblLoaiPhong.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblLoaiPhong.setBounds(0, 29, 100, 36);
		panelTenPhong.add(lblLoaiPhong);

		// Icon loại phòng
		RoundedBorder panelIconLoai = new RoundedBorder(94);
		panelIconLoai.setBackground(Color.WHITE);
		panelIconLoai.setForeground(new Color(204, 255, 204));
		panelIconLoai.setBounds(110, 70, 90, 90);
		panelPhong.add(panelIconLoai);
		panelIconLoai.setLayout(new BorderLayout(0, 0));

		JLabel lblIconLoai = new JLabel("", SwingConstants.CENTER);
		lblIconLoai.setForeground(new Color(255, 153, 51));
		lblIconLoai.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
		if ("LP01".equals(maLoai)) lblIconLoai.setText("T");
		else if ("LP02".equals(maLoai)) lblIconLoai.setText("V");
		else if ("LP03".equals(maLoai)) lblIconLoai.setText("P");
		panelIconLoai.add(lblIconLoai);

		// Panel thời gian hiện tại
		JPanel panelThoiGianHienTai = new JPanel(null);
		panelThoiGianHienTai.setBackground(new Color(255, 153, 51));
		panelThoiGianHienTai.setBounds(10, 81, 90, 100);
		panelPhong.add(panelThoiGianHienTai);

		JLabel lblClock1 = new JLabel(new ImageIcon("icon/oclock.png"), SwingConstants.CENTER);
		lblClock1.setBounds(0, 0, 90, 41);
		panelThoiGianHienTai.add(lblClock1);

		JLabel lblGioHienTai = new JLabel(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), SwingConstants.CENTER);
		lblGioHienTai.setForeground(Color.WHITE);
		lblGioHienTai.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblGioHienTai.setBounds(0, 31, 90, 41);
		panelThoiGianHienTai.add(lblGioHienTai);

		JLabel lblNgayHienTai = new JLabel(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), SwingConstants.CENTER);
		lblNgayHienTai.setForeground(Color.WHITE);
		lblNgayHienTai.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNgayHienTai.setBounds(0, 64, 90, 36);
		panelThoiGianHienTai.add(lblNgayHienTai);

		// Panel thời gian nhận phòng
		JPanel panelNhanPhong = new JPanel(null);
		panelNhanPhong.setBackground(new Color(255, 153, 51));
		panelNhanPhong.setBounds(210, 81, 90, 100);
		panelPhong.add(panelNhanPhong);

		JLabel lblClock2 = new JLabel(new ImageIcon("icon/oclock.png"), SwingConstants.CENTER);
		lblClock2.setBounds(0, 0, 90, 41);
		panelNhanPhong.add(lblClock2);

		JLabel lblGioNhanPhong = new JLabel("12:00", SwingConstants.CENTER);
		lblGioNhanPhong.setForeground(Color.WHITE);
		lblGioNhanPhong.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblGioNhanPhong.setBounds(0, 31, 90, 41);
		panelNhanPhong.add(lblGioNhanPhong);

		JLabel lblNgayNhanPhong = new JLabel("", SwingConstants.CENTER);
		lblNgayNhanPhong.setForeground(Color.WHITE);
		lblNgayNhanPhong.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNgayNhanPhong.setBounds(0, 64, 90, 36);
		panelNhanPhong.add(lblNgayNhanPhong);

		if (ddp.getThoiGianNhanPhong() != null) {
			lblNgayNhanPhong.setText(new SimpleDateFormat("dd/MM/yyyy").format(ddp.getThoiGianNhanPhong()));
		} else {
			lblNgayNhanPhong.setText("N/A");
		}

		// Panel thông tin "Còn" và "Phụ thu"
		JPanel panelThongTin = new JPanel(null);
		panelThongTin.setBackground(new Color(255, 153, 51));
		panelThongTin.setBounds(20, 175, 253, 65);
		panelPhong.add(panelThongTin);

		JPanel panelCon = new JPanel(null);
		panelCon.setBackground(new Color(255, 153, 51));
		panelCon.setBounds(23, 11, 113, 54);
		panelThongTin.add(panelCon);

		JLabel lblCon = new JLabel("Còn", SwingConstants.CENTER);
		lblCon.setForeground(Color.WHITE);
		lblCon.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblCon.setBounds(10, 0, 90, 28);
		panelCon.add(lblCon);

		JLabel lblThoiGianCon = new JLabel("", SwingConstants.CENTER);
		lblThoiGianCon.setForeground(Color.WHITE);
		lblThoiGianCon.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblThoiGianCon.setBounds(10, 18, 90, 36);
		panelCon.add(lblThoiGianCon);

		JPanel panelPhuThu = new JPanel(null);
		panelPhuThu.setBackground(new Color(255, 153, 51));
		panelPhuThu.setBounds(130, 11, 113, 54);
		panelThongTin.add(panelPhuThu);

		JLabel lblPhuThu = new JLabel("Phụ thu", SwingConstants.CENTER);
		lblPhuThu.setForeground(Color.WHITE);
		lblPhuThu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblPhuThu.setBounds(10, 0, 90, 28);
		panelPhuThu.add(lblPhuThu);

		JLabel lblSoPhuThu = new JLabel("", SwingConstants.CENTER);
		lblSoPhuThu.setForeground(Color.WHITE);
		lblSoPhuThu.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblSoPhuThu.setBounds(10, 18, 90, 36);
		panelPhuThu.add(lblSoPhuThu);

		// Xử lý thời gian trả phòng
		LocalDateTime traPhongLocal = null;
		if (ddp.getThoiGianTraPhong() instanceof java.sql.Timestamp ts) {
			traPhongLocal = ts.toLocalDateTime().withHour(12);
		} else if (ddp.getThoiGianTraPhong() instanceof java.sql.Date d) {
			traPhongLocal = d.toLocalDate().atStartOfDay().plusHours(12);
		}

		if (traPhongLocal != null) {
			LocalDateTime hienTai = LocalDateTime.now();
			Duration duration = Duration.between(hienTai, traPhongLocal);

			long hoursLate = 0;
			if (duration.isNegative()) {
				duration = duration.negated();
				lblCon.setText("Trễ:");
				hoursLate = duration.toHours();
			}

			long days = duration.toDays();
			long hours = duration.toHours() % 24;
			long minutes = duration.toMinutes() % 60;
			lblThoiGianCon.setText(days + "N:" + hours + "H:" + minutes + "p");

			// Tính tiền phụ thu: mỗi giờ trễ 50.000 VND
			long tienPhuThu = hoursLate * 50000;
			lblSoPhuThu.setText(String.format("%,d", tienPhuThu) + "đ");
		}

		danhSachPhong.add(panelPhong);
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}


	private void hienThiPhongDangDon(Phong phong) {
		String maPhong = phong.getMaPhong(); // Lấy mã phòng
		String tenPhong = phong.getTenPhong(); // Lấy tên phòng
		String maLoai = phong.getLoaiPhong().getMaLoai(); // Lấy mã loại phòng
		int soGiuong = phong.getSoGiuong(); // Lấy số giường
		double giaTien = phong.getGiaTien(); // Lấy giá tiền
		int soNguoiLon = phong.getSoNguoiLon(); // Lấy số người lớn
		int soTreEm = phong.getSoTreEm(); // Lấy số trẻ em
		String trangThai = phong.getTrangThai();
		RoundedBorder panelPhong = new RoundedBorder(20);
		panelPhong.setPreferredSize(new Dimension(300, 250));
		panelPhong.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				new ThongTinPhong_Dialog(phong, ddp, DanhSachPhong_GUI.this, owner, true).setVisible(true);;

			}
		});
//		if ("Đang dọn dẹp".equals(trangThai)) {

			panelPhong.setBackground(new Color(0, 204, 255));
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(0, 204, 255));
			panel_1.setBounds(203, 0, 97, 65);
			panelPhong.add(panel_1);
			panel_1.setLayout(null);

			JLabel lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setBounds(40, 11, 24, 24);
			lblNewLabel_2.setIcon(new ImageIcon("icon/phongdangdon24.png"));
			panel_1.add(lblNewLabel_2);

			JLabel lblNewLabel_1_1 = new JLabel("Đang dọn");
			lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel_1_1.setBounds(0, 29, 100, 36);
			panel_1.add(lblNewLabel_1_1);

			// Panel chứa icon và viền tròn
			RoundedBorder panel_3 = new RoundedBorder(94);
			panel_3.setBackground(new Color(255, 255, 255));
			panel_3.setForeground(new Color(0, 204, 255));
			panel_3.setBounds(110, 70, 90, 90);
			panelPhong.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));

			JLabel lblNewLabel_4 = new JLabel();
			if (maLoai.equals("LP01")) {
				lblNewLabel_4.setText("T");
			} else if (maLoai.equals("LP02")) {
				lblNewLabel_4.setText("V");
			} else if (maLoai.equals("LP03")) {
				lblNewLabel_4.setText("P");
			}
			lblNewLabel_4.setForeground(new Color(0, 204, 255));
			lblNewLabel_4.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_4.setIcon(null);
			panel_3.add(lblNewLabel_4, BorderLayout.CENTER);

			// Panel chứa thông tin phòng
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 204, 255));
			panel.setBounds(10, 0, 100, 65);
			panelPhong.add(panel);

			JLabel lblNewLabel = new JLabel();
			lblNewLabel.setText(tenPhong);
			lblNewLabel.setBackground(new Color(204, 255, 153));
			lblNewLabel.setBounds(0, 0, 100, 40);
			lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

			JLabel lblNewLabel_1 = new JLabel("Phòng đơn");
			lblNewLabel_1.setBounds(0, 29, 100, 36);
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel.setLayout(null);
			panel.add(lblNewLabel);
			panel.add(lblNewLabel_1);

			panelPhong.setLayout(null);

			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(0, 204, 255));
			panel_2.setBounds(10, 165, 280, 74);
			panelPhong.add(panel_2);

			JLabel lblNewLabel_5 = new JLabel("");
			lblNewLabel_5.setIcon(new ImageIcon("icon/broom.png"));
			panel_2.add(lblNewLabel_5);

			JLabel lblNewLabel_5_1 = new JLabel("");
			lblNewLabel_5_1.setIcon(new ImageIcon("icon/broom.png"));
			panel_2.add(lblNewLabel_5_1);

			JLabel lblNewLabel_5_1_1 = new JLabel("");
			lblNewLabel_5_1_1.setIcon(new ImageIcon("icon/broom.png"));
			panel_2.add(lblNewLabel_5_1_1);
			danhSachPhong.add(panelPhong);
//		}
		// Refresh lại giao diện
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}

	@SneakyThrows
	private void hienThiDanhSachPhongTrong() {
		List<Phong> dsPhong = null;
		dsPhong = phongService.getAll();

		// Duyệt qua danh sách phòng và tạo các panel cho mỗi phòng
		for (Phong phong : dsPhong) {
			String maPhong = phong.getMaPhong(); // Lấy mã phòng
			String tenPhong = phong.getTenPhong(); // Lấy tên phòng
			String maLoai = phong.getLoaiPhong().getMaLoai(); // Lấy mã loại phòng
			int soGiuong = phong.getSoGiuong(); // Lấy số giường
			double giaTien = phong.getGiaTien(); // Lấy giá tiền
			int soNguoiLon = phong.getSoNguoiLon(); // Lấy số người lớn
			int soTreEm = phong.getSoTreEm(); // Lấy số trẻ em
			String trangThai = phong.getTrangThai();
			RoundedBorder panelPhong = new RoundedBorder(20);
			panelPhong.setPreferredSize(new Dimension(300, 250));

			if ("Phòng trống".equals(trangThai)) {
				hienThiPhongTrong(phong);
			}
		}
		// Refresh lại giao diện
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}

	@SneakyThrows
	private void hienThiDanhSachPhongDangO(java.sql.Date selectedDate) {
		List<Phong> dsPhong = null;
		dsPhong = phongService.getAll();

		// Duyệt qua danh sách phòng và tạo các panel cho mỗi phòng
		for (Phong phong : dsPhong) {
			String maPhong = phong.getMaPhong(); // Lấy mã phòng
			String tenPhong = phong.getTenPhong(); // Lấy tên phòng
			String maLoai = phong.getLoaiPhong().getMaLoai(); // Lấy mã loại phòng
			int soGiuong = phong.getSoGiuong(); // Lấy số giường
			double giaTien = phong.getGiaTien(); // Lấy giá tiền
			int soNguoiLon = phong.getSoNguoiLon(); // Lấy số người lớn
			int soTreEm = phong.getSoTreEm(); // Lấy số trẻ em
			String trangThai = phong.getTrangThai();
			RoundedBorder panelPhong = new RoundedBorder(20);
			panelPhong.setPreferredSize(new Dimension(300, 250));
			String trangThaiDDP = donDatPhongService.getTrangThaiPhongOThoiDiemChon(phong.getMaPhong(), sqlSelectedDate);
			if (trangThaiDDP != null && "Đang ở".equals(trangThaiDDP)) {
				hienThiPhongDangO(phong);
			}
		}
		// Refresh lại giao diện
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}

	@SneakyThrows
	private void hienThiDanhSachPhongDangDon() {
		List<Phong> dsPhong = null;
		dsPhong = phongService.getAll();

		// Duyệt qua danh sách phòng và tạo các panel cho mỗi phòng
		for (Phong phong : dsPhong) {
			String maPhong = phong.getMaPhong(); // Lấy mã phòng
			String tenPhong = phong.getTenPhong(); // Lấy tên phòng
			String maLoai = phong.getLoaiPhong().getMaLoai(); // Lấy mã loại phòng
			int soGiuong = phong.getSoGiuong(); // Lấy số giường
			double giaTien = phong.getGiaTien(); // Lấy giá tiền
			int soNguoiLon = phong.getSoNguoiLon(); // Lấy số người lớn
			int soTreEm = phong.getSoTreEm(); // Lấy số trẻ em
			String trangThai = phong.getTrangThai();
			RoundedBorder panelPhong = new RoundedBorder(20);
			panelPhong.setPreferredSize(new Dimension(300, 250));
			if ("Đang dọn dẹp".equals(trangThai)) {
				hienThiPhongDangDon(phong);
			}
		}
		// Refresh lại giao diện
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
	}

//	private void hienThiDanhSachPhongDatTruoc() {
//	    java.util.Date ngayNhanDate = dateDatTruoc.getDate() != null ? dateDatTruoc.getDate() : new java.util.Date();
//	    String ngayNhanStr = formatDate(ngayNhanDate); 
//
//	    ArrayList<Phong> dsPhong = phongdao.getPhongDaDat(ngayNhanStr);
//
//	    danhSachPhong.removeAll();
//
//	    for (Phong phong : dsPhong) {
//	        String trangThai = phong.getTrangThai();
//
//	        if ("Bận".equals(trangThai)) {
//	            hienThiPhongDatTruoc(phong);
//	        }
//	    }
//
//	    danhSachPhong.revalidate();
//	    danhSachPhong.repaint();
//	}


	@SneakyThrows
	public void hienThiSoLuongTungPhong() {
		
		java.sql.Date ngayDaChon1 = getSelectedDate();
		int trong = phongService.getTongSoPhong() - donDatPhongService.countSLDonDangO(ngayDaChon1) - donDatPhongService.countSLDonDatTruoc(ngayDaChon1) - donDatPhongService.countSLDonDangDon(ngayDaChon1);
		soPhongDangO.setText("("+donDatPhongService.countSLDonDangO(ngayDaChon1)+") Đang ở");
		soPhongDatTruoc.setText("("+donDatPhongService.countSLDonDatTruoc(ngayDaChon1)+") Đặt trước");
		soPhongDangDon.setText("("+donDatPhongService.countSLDonDangDon(ngayDaChon1)+") Đang dọn");
		soPhongTrong.setText("("+trong+") Phòng trống");
		lblTatCa.setText("(" + phongService.getTongSoPhong()+ ") Tất cả");
	}

	@SneakyThrows
	private void timDDP(Phong phong) {

		  String luachon = (String) cbxLuachon.getSelectedItem();
        String searchContent = txtTim.getText();
        if (searchContent.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin tìm kiếm!");
            return;
        }
        switch (luachon) {
       
        case "Mã đơn đặt phòng":
            danhSachPhong.removeAll(); 
            ArrayList<Phong> dsPhong = (ArrayList<Phong>) phongService.getAll();
            dsDDP = TimDonDatDonDatPhong.getInstance().searhMa(searchContent); 
            if (!dsDDP.isEmpty()) {
                DonDatPhong ddp = dsDDP.get(0);
                if (ddp.getMaDDP().equals(searchContent)) {
                    for (Phong p : dsPhong) {
                        if (p.getMaPhong().equals(ddp.getPhong().getMaPhong())) { 
                            hienThiPhong(p, ""); 
                            break;
                        }
                    }
                }
            }else {
            	  JOptionPane.showMessageDialog(null, "Không tìm thấy mã đơn đặt phòng đang ở.");
            }
            danhSachPhong.repaint();
            break;

        case "Số điện thoại khách hàng đang ở":
            danhSachPhong.removeAll(); 

            List<Phong> dsPhong2 = phongService.getAll();

            dsDDP = (ArrayList<DonDatPhong>) donDatPhongService.findPhongByTrangThaiAndSDTDangO(searchContent);


            if (!dsDDP.isEmpty()) {
                for (DonDatPhong ddp : dsDDP) {
                    ArrayList<KhachHang> khachHangs = (ArrayList<KhachHang>) khachHangService.getAll();
                    KhachHang khachHang = null;
                    for (KhachHang k : khachHangs) {
                        if (k.getSDT().equals(searchContent)) {
                            khachHang = k;
                            break;
                        }
                    }

                    if (khachHang != null) {
                        ddp.setKhachHang(khachHang);
                        
                        for (Phong p : dsPhong2) {
                            if (p.getMaPhong().equals(ddp.getPhong().getMaPhong())) { 
                                hienThiPhong(p, ""); 
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy đơn đặt phòng cho số điện thoại này.");
            }

            danhSachPhong.repaint();
            break;
            
        case "Số điện thoại khách hàng đặt trước":
        	  danhSachPhong.removeAll(); 

              ArrayList<Phong> dsPhong3 = (ArrayList<Phong>) phongService.getAll();

			dsDDP = (ArrayList<DonDatPhong>) donDatPhongService.findPhongByTrangThaiAndSDTDangO(searchContent);

              if (!dsDDP.isEmpty()) {
                  for (DonDatPhong ddp : dsDDP) {
                	  if (ddp.getTrangThai().equals("Đặt trước")) {
                      ArrayList<KhachHang> khachHangs = (ArrayList<KhachHang>) khachHangService.getAll();
                      KhachHang khachHang = null;
                      for (KhachHang k : khachHangs) {
                          if (k.getSDT().equals(searchContent)) {
                              khachHang = k;
                              break;
                          }
                      }

                      if (khachHang != null) {
                          ddp.setKhachHang(khachHang);
                          
                          for (Phong p : dsPhong3) {
                              if (p.getMaPhong().equals(ddp.getPhong().getMaPhong()) ) { 
                                  hienThiPhong(p, "Đặt trước"); 
                              }
                          }
                      }
                  }
                  }
              } else {
                  JOptionPane.showMessageDialog(null, "Không tìm thấy đơn đặt phòng cho số điện thoại này.");
              }
              

              danhSachPhong.repaint();
        	
        	break;
            
        default:
            JOptionPane.showMessageDialog(null, "Lựa chọn không hợp lệ!");
            return;
        }  
        
      
    
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btn_NhanPhong)) {
//			NhanPhong_GUI np = new NhanPhong_GUI(phong, DanhSachPhong_GUI.this);
//			np.setVisible(true);

		}
		
		if(e.getSource().equals(btn_Tim)) {
			timDDP(phong);
			
		}


	}

	public void capNhatLaiDanhSachPhong() {
		danhSachPhong.removeAll();
		danhSachPhong.revalidate();
		danhSachPhong.repaint();
		hienThiDanhSachPhong();
		hienThiSoLuongTungPhong();
	}

	public String currencyFormat(Double vnd) {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		return currencyVN.format(vnd);
	}


	public void open_DLDatNhieuPhong() {

//		datnhieuphong = new DatNhieuPhong_GUI(this);
//		datnhieuphong.setVisible(true);

	}

	public void btnMoreActionPerformed() {
		panelPopUpMoreDatPhong1 = new panelPopUpMoreDatPhong();

		panelPopUpMoreDatPhong1.showPopupMenu(this,
				btnMore.getX() - panelPopUpMoreDatPhong1.getPreferredSize().width + 300,
				btnMore.getY() + btnMore.getHeight());

		panelPopUpMoreDatPhong1.addEvent(new EventPopUpMoreDatPhong() {

			@Override
			public void thueNhieuPhong() {
				open_DLDatNhieuPhong();	

			}

			@Override
			public void thanhToanNhieuPhong() {
				// TODO Auto-generated method stub

			}

			@Override
			public void datPhongTruoc() {
//				open_DLDatPhongTruoc();
			}
		});
	}

	private String formatDate(Date ngayNhanDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(ngayNhanDate);
	}
	
	private java.sql.Date ngayChon;

	public void setSelectedDate(java.sql.Date date) {
	    this.ngayChon = date;
	}
	
	public java.sql.Date convertToSqlDate(Date date) {
	    return new java.sql.Date(date.getTime());
	}
	
	public java.sql.Date getSelectedDate() {
	    return ngayChon;
	}

}