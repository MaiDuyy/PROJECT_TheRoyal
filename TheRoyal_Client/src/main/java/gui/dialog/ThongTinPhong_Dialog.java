package gui.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import gui.ui.DanhSachPhong_GUI;
import gui.ui.DatPhong;
import gui.ui.DatPhongTruoc_GUI;
import gui.ui.DichVuSanPham_GUI;
import gui.ui.NhanPhong_GUI;
import gui.ui.QLKhachHang_GUI;
import gui.ui.ThanhToan_GUI;
import gui.ui.ThongTinDatPhongTruoc_DL;
import dao.DonDatPhongDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.PhongDAO;
import entity.DonDatPhong;
import entity.HoaDon;
import entity.KhachHang;
import entity.Phong;
import formatdate.FormatDate;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.DonDatPhongService;
import service.HoaDonService;
import service.PhongService;

public class ThongTinPhong_Dialog extends JDialog implements ActionListener {


	public JButton btnPhong;
	public JButton btn_ThanhToan ,btn_VaoThue ,btn_DatTruoc;
	public JButton btn_DatPhong;
	public JButton btn_NhanPhong;
	public JButton btn_SuDungDV;
	public JButton btn_XemLichDat;
	public JButton btn_HuyDP;
	public JButton btn_traPhong;
	public JButton btn_donDep;
	private Phong phong;
	private DonDatPhong ddp;
	private JPanel pn_p_bottom;
	private ThanhToan_GUI	thanhtoandialog ;
	private DanhSachPhong_GUI home;

	private PhongService phongService = RMIClient.getInstance().getPhongService();

	private DonDatPhongService donDatPhongService = RMIClient.getInstance().getDonDatPhongService();

	public ThongTinPhong_Dialog( Phong phong, DonDatPhong ddp, DanhSachPhong_GUI parent, Frame owner  , boolean modal) {
		super(owner, modal);
		home = (DanhSachPhong_GUI) parent;
		this.phong = phong;
		this.ddp = ddp;
		showRoomInfoDialog(phong);
		setLocationRelativeTo(null);
	}


	@SneakyThrows
	private void showRoomInfoDialog(Phong phong) {
		List<Phong> dsPhong = null;
		dsPhong =phongService.getAll();
		this.phong = phong;
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Thông tin phòng");
		getContentPane().setLayout(null);
		setBackground(Color.white);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
				"Chức năng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
				new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
		panel.setBounds(21, 10, 359, 197);
		getContentPane().add(panel);
		panel.setBackground(Color.white);
		panel.setLayout(null);

		String gia = new DanhSachPhong_GUI().currencyFormat(phong.getGiaTien());
		JLabel lblRoomInfo_1 = new JLabel("Phòng:" + phong.getMaPhong());
		lblRoomInfo_1.setFont(new Font("Arial", Font.BOLD, 13));
		lblRoomInfo_1.setBounds(29, 31, 161, 55);
		panel.add(lblRoomInfo_1);

		JLabel lblRoomInfo_1_1 = new JLabel("Loại phòng:" + phong.getLoaiPhong().getMaLoai());
		lblRoomInfo_1_1.setFont(new Font("Arial", Font.BOLD, 13));
		lblRoomInfo_1_1.setBounds(200, 31, 149, 55);
		panel.add(lblRoomInfo_1_1);

		JLabel lblRoomInfo_1_2 = new JLabel("Giá tiền:" + gia);
		lblRoomInfo_1_2.setFont(new Font("Arial", Font.BOLD, 13));
		lblRoomInfo_1_2.setBounds(29, 96, 161, 55);
		panel.add(lblRoomInfo_1_2);

		JLabel lblRoomInfo_1_2_1 = new JLabel("Trạng thái:" + phong.getTrangThai());
		lblRoomInfo_1_2_1.setFont(new Font("Arial", Font.BOLD, 13));
		lblRoomInfo_1_2_1.setBounds(200, 96, 149, 55);
		panel.add(lblRoomInfo_1_2_1);

		// Panel bottom để chứa các button
		JPanel pn_p_c = new JPanel();
		pn_p_c.setLocation(21, 182);
		pn_p_c.setSize(359, 69);
		pn_p_c.setBackground(Color.white);
		getContentPane().add(pn_p_c);
		pn_p_c.setLayout(null);

		pn_p_bottom = new JPanel();
		pn_p_bottom.setBounds(10, 5, 339, 54);
		pn_p_bottom.setBackground(Color.white);
		pn_p_c.add(pn_p_bottom);
		pn_p_bottom.setLayout(null);

		btn_ThanhToan = new JButton("Thanh toán");
		btn_ThanhToan.setFont(new Font("Tahoma", Font.BOLD, 10));
//               btn_ThanhToan.setBounds(72, 21, 69, 23);
		btn_ThanhToan.setBounds(72, 21, 69, 23);
		btn_ThanhToan.setBackground(Color.RED);
		btn_ThanhToan.setForeground(Color.WHITE);
		btn_ThanhToan.setOpaque(true);
		btn_ThanhToan.setBorder(null);

		btn_VaoThue = new JButton("Vào thuê");
		btn_VaoThue.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_VaoThue.setBounds(70, 21, 69, 23);
		btn_VaoThue.setBackground(Color.GREEN);
		btn_VaoThue.setForeground(Color.WHITE);
		btn_VaoThue.setOpaque(true);
		btn_VaoThue.setBorder(null);

		btn_DatTruoc = new JButton("Đặt trước");
		btn_DatTruoc.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_DatTruoc.setBounds(200, 21, 69, 23);
		btn_DatTruoc.setBackground(Color.GREEN);
		btn_DatTruoc.setForeground(Color.WHITE);
		btn_DatTruoc.setOpaque(true);
		btn_DatTruoc.setBorder(null);

		btn_NhanPhong = new JButton("Nhận phòng");
		btn_NhanPhong.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_NhanPhong.setBounds(72, 21, 69, 23);
		btn_NhanPhong.setBackground(Color.ORANGE);
		btn_NhanPhong.setForeground(Color.WHITE);
		btn_NhanPhong.setOpaque(true);
		btn_NhanPhong.setBorder(null);

		btn_SuDungDV = new JButton("Xem đơn");
		btn_SuDungDV.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_SuDungDV.setBounds(158, 21, 120, 23);
		btn_SuDungDV.setBackground(Color.BLUE);
		btn_SuDungDV.setForeground(Color.WHITE);
		btn_SuDungDV.setOpaque(true);
		btn_SuDungDV.setBorder(null);

		btn_HuyDP = new JButton("Hủy");
		btn_HuyDP.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_HuyDP.setBounds(168, 21, 69, 23);
		btn_HuyDP.setBackground(Color.red);
		btn_HuyDP.setForeground(Color.WHITE);
		btn_HuyDP.setOpaque(true);
		btn_HuyDP.setBorder(null);

		btn_traPhong = new JButton("Trả phòng");
		btn_traPhong.setFont(new Font("Tahoma", Font.BOLD, 10));
//               btn_traPhong.setBounds(158, 21, 120, 23);
		btn_traPhong.setBounds(72, 21, 69, 23);
		btn_traPhong.setBackground(Color.BLUE);
		btn_traPhong.setForeground(Color.WHITE);
		btn_traPhong.setOpaque(true);
		btn_traPhong.setBorder(null);

		btn_donDep = new JButton("Dọn dẹp");
		btn_donDep.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_donDep.setBounds(135, 21, 69, 23);
		btn_donDep.setBackground(Color.GREEN);
		btn_donDep.setForeground(Color.WHITE);
		btn_donDep.setOpaque(true);
		btn_donDep.setBorder(null);

		java.util.Date utilDate = home.date_DSPhong.getDate();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		String trangThai = donDatPhongService.getTrangThaiPhongOThoiDiemChon(phong.getMaPhong(), sqlDate);
		if ("Đang ở".equals(trangThai)) {
			String maPhong = phong.getMaPhong();
			DonDatPhong ddp =  donDatPhongService.getDonDatTruocTheoPhongVaTrangThai(maPhong, trangThai);

			if (ddp.getThoiGianTraPhong() != null) {
				pn_p_bottom.add(btn_ThanhToan);
				pn_p_bottom.add(btn_SuDungDV);
				btn_ThanhToan.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (ddp != null) {
							getddp(phong,ddp);
							dispose();

						}
					}
				});

				btn_SuDungDV.addActionListener(new ActionListener() {
					@SneakyThrows
					@Override
					public void actionPerformed(ActionEvent e) {
						HoaDonService hoaDonService = RMIClient.getInstance().getHoaDonService();
						HoaDon hd = hoaDonService.getHoaDonTheoDonDatPhong(ddp.getMaDDP());
						if (hd != null) {
							getdichvusanpham(phong,ddp);
//							dichvusanpham.dispose();

						}
					}
				});

			}
//				else {
//				pn_p_bottom.add(btn_traPhong);
//
//				btn_traPhong.addActionListener(new ActionListener() {
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						boolean traPhongThanhCong = dondatphongdao.updateTraPhong(ddp.getMaDDP(),
//								new java.sql.Date(System.currentTimeMillis()));
//
//						if (traPhongThanhCong) {
//							pn_p_bottom.remove(btn_traPhong);
//
//							pn_p_bottom.add(btn_ThanhToan);
//							pn_p_bottom.add(btn_SuDungDV);
//							btn_SuDungDV.addActionListener(new ActionListener() {
//								@Override
//								public void actionPerformed(ActionEvent e) {
//									NhanPhong_GUI xemDon = new NhanPhong_GUI(phong, DanhSachPhong_GUI.this);
//									xemDon.setVisible(true);
//									PhongDAO phongdao = new PhongDAO();
//
//									capNhatLaiDanhSachPhong();
//								}
//							});
//
//							pn_p_bottom.revalidate();
//							pn_p_bottom.repaint();
//						} else {
//						}
//					}
//				});
//			}

//               if ("Bận".equals(phong.getTrangThai())) {
//                   pn_p_bottom.add(btn_traPhong);
//                   pn_p_bottom.add(btn_SuDungDV);
//                   btn_SuDungDV.addActionListener(new ActionListener() {
//                       @Override
//                       public void actionPerformed(ActionEvent e) {
//                    	   NhanPhong_GUI xemDon = new NhanPhong_GUI(phong, DanhSachPhong_GUI.this);
//                    	   xemDon.setVisible(true);
//                    	   PhongDAO phongdao = new PhongDAO();
//
//                	       capNhatLaiDanhSachPhong();
//                       }
//                   });

		} else if ("Phòng trống".equals(trangThai)) {
			//Láy ngày hiện tại và so sánh với ngày của jdatechooser
			java.util.Date selectedDate = home.date_DSPhong.getDate();
			home.ngayHienTai = new java.util.Date();

			// Xóa phần thời gian (giờ, phút, giây) của selectedDate và ngayHienTai
			Calendar calendarSelectedDate = Calendar.getInstance();
			calendarSelectedDate.setTime(selectedDate);
			calendarSelectedDate.set(Calendar.HOUR_OF_DAY, 0);
			calendarSelectedDate.set(Calendar.MINUTE, 0);
			calendarSelectedDate.set(Calendar.SECOND, 0);
			calendarSelectedDate.set(Calendar.MILLISECOND, 0);

			Calendar calendarCurrentDate = Calendar.getInstance();
			calendarCurrentDate.setTime(home.ngayHienTai);
			calendarCurrentDate.set(Calendar.HOUR_OF_DAY, 0);
			calendarCurrentDate.set(Calendar.MINUTE, 0);
			calendarCurrentDate.set(Calendar.SECOND, 0);
			calendarCurrentDate.set(Calendar.MILLISECOND, 0);



			// So sánh chỉ ngày tháng năm
			if (calendarSelectedDate.getTime().equals(calendarCurrentDate.getTime())) {
			    pn_p_bottom.add(btn_VaoThue);
			    pn_p_bottom.add(btn_DatTruoc);
			}else {
				btn_DatTruoc.setBounds(135, 21, 69, 23);
				pn_p_bottom.add(btn_DatTruoc);

			}

		} else if ("Đặt trước".equals(trangThai)) {
			pn_p_bottom.add(btn_HuyDP);
			pn_p_bottom.add(btn_NhanPhong);
		} else  {
			pn_p_bottom.add(btn_donDep);
			btn_donDep.addActionListener(new ActionListener() {
				@SneakyThrows
				@Override
				public void actionPerformed(ActionEvent e) {


					String maPhong = phong.getMaPhong();
					DonDatPhong ddp = donDatPhongService.getDonDatTruocTheoPhongVaTrangThai(maPhong, trangThai);
					phongService.updateTinhTrangPhong(phong, "Phòng trống");
					donDatPhongService.updateTinhTrang(ddp.getMaDDP(), "Hoàn tất");
					phong.setTrangThai("Phòng trống");
					dispose();

					home.capNhatLaiDanhSachPhong();

//					boolean dondepthanhcong = phongdao.updateTinhTrang(phong, "Phòng trống");
//
//					if (dondepthanhcong) {
//						phong.setTrangThai("Phòng trống");
//
//						pn_p_bottom.remove(btn_donDep);
//						pn_p_bottom.add(btn_VaoThue);
//
//						pn_p_bottom.revalidate();
//						pn_p_bottom.repaint();
//						dialog.dispose();
//
//						capNhatLaiDanhSachPhong();
//					} else {
//
//					}
				}
			});

		}

		setSize(412, 300);
		setLocationRelativeTo(this);
		btn_DatTruoc.addActionListener(this);
		btn_VaoThue.addActionListener(this);
		btn_ThanhToan.addActionListener(this);
		btn_NhanPhong.addActionListener(this);
		btn_HuyDP.addActionListener(this);
		btn_SuDungDV.addActionListener(this);
	}

	public void getddp(Phong maPhong ,DonDatPhong ddp) {

//		String maPhongFromList = phong.getMaPhong();
//		DonDatPhong ddp = DonDatPhongDAO.getInstance().getDonDatPhongTheoMaTraPhong(maPhongFromList);
//		ddp.setPhong(phong);

		if (ddp != null) {
			ThanhToan_GUI thanhtoan = new ThanhToan_GUI(phong, ddp, this.home,this, rootPaneCheckingEnabled);
			thanhtoan.setVisible(true);

			return;
		}
	}

	public void getdichvusanpham(Phong maPhong, DonDatPhong ddp) {

		if (phong != null) {
			DichVuSanPham_GUI dichvusanpham = new DichVuSanPham_GUI(phong, ddp, this.home,this ,rootPaneCheckingEnabled);
			dichvusanpham.setVisible(true);
		} else {
			System.out.println("Phòng không hợp lệ!");
		}
	}

	public void datphongnhan(String maPhong) {

		String maPhongFromList = phong.getMaPhong();
		Phong phong = PhongDAO.getInstance().getPhongByMaPhong(maPhongFromList);

		if (maPhongFromList != null) {
			DatPhong	datphong = new DatPhong(phong, this.home, this ,rootPaneCheckingEnabled);
			datphong.setVisible(true);

			return;
		}
	}
	public void datphongtruoc(String maPhong) {
		String maPhongFromList = phong.getMaPhong();
		Phong phong = PhongDAO.getInstance().getPhongByMaPhong(maPhongFromList);

		if (maPhongFromList != null) {
			open_DLDatPhongTruoc(phong);
			return;
		}
	}

	public void open_DLDatPhongTruoc(Phong p) {

		DatPhongTruoc_GUI	datphongtruoc = new DatPhongTruoc_GUI(this.home,  p ,this, true);
		 datphongtruoc.setVisible(true);

	}
	
	
	
	@SneakyThrows
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btn_NhanPhong)) {

		}

//		if (e.getSource().equals(btn_ThanhToan)) {
//			List<Phong> dsPhong = PhongDAO.getInstance().getListPhong();
//			DonDatPhongDAO donDatPhongDAO = new DonDatPhongDAO();
//			for (Phong phong : dsPhong) {
//				String maPhong = phong.getMaPhong();
//				DonDatPhong ddp = donDatPhongDAO.getDonDatPhongTheoMaTraPhong(maPhong);
//
//				if (ddp != null) {
//					getddp(maPhong);
//					dispose();
//					break;
//
//				}
//			}
//
//		}
		
		//
//		if (e.getSource().equals(btn_SuDungDV)) {
//			List<Phong> dsPhong = PhongDAO.getInstance().getListPhong();
//			HoaDonDAO hoadondao = new HoaDonDAO();
//			for (Phong phong : dsPhong) {
//				String maPhong = phong.getMaPhong();
//				HoaDon hd = hoadondao.getHoaDonTheoPhong(maPhong);
//
//				if (hd != null) {
//					getdichvusanpham(maPhong);
//					this.dispose();
//					break;
//
//				}
//			}
//		}

		if (e.getSource().equals(btn_VaoThue)) {
			List<Phong> dsPhong = phongService.getAll();
			for (Phong phong : dsPhong) {
				String maPhong = phong.getMaPhong();
				Phong p = PhongDAO.getInstance().getPhongByMaPhong(maPhong);
				if (p != null) {
					datphongnhan(maPhong);
					dispose();
					break;

				}
			}
		}

		if (e.getSource().equals(btn_DatTruoc)) {
			List<Phong> dsPhong = phongService.getAll();
			for (Phong phong : dsPhong) {
				String maPhong = phong.getMaPhong();
				Phong p = PhongDAO.getInstance().getPhongByMaPhong(maPhong);
				if (p != null) {
					datphongtruoc(maPhong);
					dispose();
					break;

				}
			}
		}




	}
	
	
	public void dis() {
	dispose();
	}
}
