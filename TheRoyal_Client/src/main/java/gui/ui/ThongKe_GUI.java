package gui.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import gui.format_ui.Table;
import dao.HoaDonDAO;
import entity.HoaDon;
import gui.chart.Chart;
import gui.chart.ModelChart;
import gui.chart.blankchart.BlankPlotChart;
import rmi.RMIClient;
import service.HoaDonService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class ThongKe_GUI extends JInternalFrame  implements ActionListener{
	private static final long serialVersionUID = 1L;

	private JPanel pnlTKDT, pnlHeader, pnlBottom, pnlChart;
	private JLabel lblTKMH, lblNgay, lblThang, lblNam, lblTongSoHD, lblTongHD, lblTongTien, lblTong;
	private JComboBox<String> cmbTKTheo, cmbTKChiTiet;
	 private DefaultComboBoxModel < String > modelTKTheo, modelTKChiTiet;
	private JDateChooser txtDate;
	private Table tblTKHD;
	private JScrollPane scr;
	private DefaultTableModel tableModelHoaDon;
	private HoaDonService hoaDonService = RMIClient.getInstance().getHoaDonService();
	private Chart chart = new Chart();
	private JPanel panelCenter;
	private JLabel lblBang;
	private JLabel lblBieuDo;
	private JLabel lblTngTin;
	private Dasboard_UI db;

	public ThongKe_GUI(Dasboard_UI db ){
		this.db = db;
		initUI();
		initComponents();
		DocDuLieu();
	}

	private void initUI() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setBackground(Color.white);
		setBounds(100, 100, 1364, 799);
		getContentPane().setLayout(null);
		setVisible(true);
		setClosable(true);
		setBorder(null);
		setTitle("");

		BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
		ui.setNorthPane(null);
		setClosable(false);
		setIconifiable(false);
		setResizable(false);
		setMaximizable(false);
	}

	private void initComponents() {
		pnlHeader = new JPanel();
		pnlHeader.setLocation(0, 0);
		pnlHeader.setSize(1364, 124);
		pnlHeader.setBackground(Color.WHITE);
		pnlHeader.setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		lblTKMH = new JLabel("THỐNG KÊ DOANH THU", JLabel.CENTER);
		lblTKMH.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTKMH.setForeground(new Color(246, 167, 193));
		lblTKMH.setBounds(500, 10, 300, 27);

		JSeparator phanCach = new JSeparator();
		phanCach.setBounds(0, 125, screenSize.width, 10);
		getContentPane().add(phanCach);

		lblNgay = new JLabel("Ngày:");
		lblNgay.setBounds(315, 65, 50, 21);
		lblThang = new JLabel("Tháng:");
		lblThang.setBounds(480, 65, 50, 21);
		lblNam = new JLabel("Năm:");
		lblNam.setBounds(643, 65, 55, 21);

		modelTKTheo = new DefaultComboBoxModel<String>();
		cmbTKTheo = new JComboBox<String>(modelTKTheo);
		  for (int i = 0; i < 12; i++) {
			  cmbTKTheo.addItem(String.valueOf(i +1 ));
          }
		cmbTKTheo.setBounds(530, 62, 100, 24);
		cmbTKTheo.setBackground(Color.WHITE);
		cmbTKTheo.addActionListener(evt -> updateMonthData());


		modelTKChiTiet = new DefaultComboBoxModel<String>();
		cmbTKChiTiet = new JComboBox<String>(modelTKChiTiet);
//		 hoaDonService.getDSNamTheoNgayLap().forEach(doc -> cmbTKChiTiet.addItem(String.valueOf(doc)));

		cmbTKChiTiet.setBounds(708, 62, 143, 24);
		cmbTKChiTiet.addActionListener(evt -> updateYearData());

		txtDate = new JDateChooser();
		txtDate.getCalendarButton().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				txtDatePropertyChange(evt);
			}
		});

		txtDate.setBounds(360, 62, 110, 24);
		txtDate.setDate(new Date());

		txtDate.setDateFormatString("dd-MM-yyyy hh:mm");
		txtDate.addPropertyChangeListener(evt -> updateSelectedDate());

		pnlHeader.add(lblTKMH);
		pnlHeader.add(lblNgay);
		pnlHeader.add(txtDate);
		pnlHeader.add(lblThang);
		pnlHeader.add(cmbTKTheo);
		pnlHeader.add(lblNam);
		pnlHeader.add(cmbTKChiTiet);

		pnlChart = new JPanel();
		pnlChart.setBackground(new Color(255, 255, 255));
		pnlChart.setBounds(0, 0, 1384, 628);
		panelCenter = new JPanel();
		panelCenter.setBackground(new Color(255, 255, 255));
		panelCenter.setBounds(0, 134, 1364, 628);
		getContentPane().add(panelCenter);

		String[] columns = { "STT", "Mã hóa đơn", "Ngày lập hóa đơn", "Khách hàng", "Nhân viên", "Khuyến mãi",
				"Tổng hóa đơn" };
		Object[][] data = {};
		tableModelHoaDon = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		panelCenter.setLayout(null);


		tblTKHD = new Table();
		tblTKHD.setModel(tableModelHoaDon);
		tblTKHD.setBackground(Color.white);
		tblTKHD.setFillsViewportHeight(true);
		tblTKHD.setBackground(new Color(255, 255, 255));

		scr = new JScrollPane(tblTKHD);
		scr.setBounds(10, 5, 1281, 522);
		panelCenter.add(scr);
		getContentPane().add(panelCenter);

		lblTongTien = new JLabel("0");



		getContentPane().add(pnlHeader);

		lblBang = new JLabel("Bảng", SwingConstants.CENTER);
		lblBang.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBang.setBounds(22, 81, 93, 35);
		lblBang.setOpaque(true);
		lblBang.setBackground(new Color(200, 200, 255)); // Active tab color
		lblBang.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		lblBang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchToTableTab();
			}
		});

		lblBieuDo = new JLabel("Biểu đồ", SwingConstants.CENTER);
		lblBieuDo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBieuDo.setBounds(125, 81, 93, 35);
		lblBieuDo.setOpaque(true);
		lblBieuDo.setBackground(Color.WHITE);
		lblBieuDo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		lblBieuDo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchToChartTab();
			}
		});

		pnlHeader.add(lblBang);
		pnlHeader.add(lblBieuDo);

		lblTongTien = new JLabel("0");
		lblTongTien.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTongTien.setBounds(1034, 95, 120, 22);
		pnlHeader.add(lblTongTien);

		lblTongHD = new JLabel("");
		lblTongHD.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTongHD.setBounds(899, 94, 37, 22);
		pnlHeader.add(lblTongHD);


		lblTongSoHD = new JLabel("Tổng số hóa đơn:");
		lblTongSoHD.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTongSoHD.setBounds(769, 93, 120, 23);
		pnlHeader.add(lblTongSoHD);

		lblTngTin = new JLabel("Tổng tiền: ");
		lblTngTin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTngTin.setBounds(949, 94, 120, 23);
		pnlHeader.add(lblTngTin);

		  cmbTKTheo.addActionListener(this);
	        cmbTKChiTiet.addActionListener(this);

		switchToTableTab();

	}

	private void switchToTableTab() {
		panelCenter.removeAll();
		panelCenter.add(scr);
		panelCenter.revalidate();
		panelCenter.repaint();

		lblBang.setBackground(new Color(200, 200, 255));
		lblBieuDo.setBackground(Color.WHITE);
	}

	private void switchToChartTab() {
		panelCenter.removeAll();
		panelCenter.add(pnlChart, BorderLayout.CENTER);
		panelCenter.revalidate();
		panelCenter.repaint();

		lblBieuDo.setBackground(new Color(200, 200, 255));
		lblBang.setBackground(Color.WHITE);
	}

	private void updateMonthData() {
		String thang = (String) cmbTKTheo.getSelectedItem();
		String nam = (String) cmbTKChiTiet.getSelectedItem();
		if (thang == null || nam == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng và năm hợp lệ.");
			return;
		}

        List<HoaDon> dsHoaDon = null;
        try {
            dsHoaDon = hoaDonService.getDoanhThuThang(thang, nam);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if (dsHoaDon == null || dsHoaDon.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn trong tháng của năm "+nam +".");
			lblTongTien.setText("");
			lblTongHD.setText("");
//			chart.clear();?
//			pnlChart.removeAll();
			clearTable();
			return;
		}
		populateTable(dsHoaDon);
//        try {
//            updateTotalCountAndAmount(hoaDonService.getSoLuongHoaDonThang(thang, nam), hoaDonService.getDoanhThuThang(thang, nam));
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        }
//		updateChart(new ArrayList<>(),nam);

	}

	private void updateYearData() {
		String nam = (String) cmbTKChiTiet.getSelectedItem();
		if (nam == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn năm hợp lệ.");
			return;
		}

        List<HoaDon> dsHoaDon = null;
        try {
            dsHoaDon = hoaDonService.getDoanhThuNam(nam);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if (dsHoaDon == null || dsHoaDon.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn trong năm.");
			chart.clear();
			removeData();
			clearTable();
		}
		populateTable(dsHoaDon);
//		updateTotalCountAndAmount(hoaDonService.getSoLuongHoaDonNam(nam), hoaDonService.getTongTienNam(nam));
		updateChart(new ArrayList<>(),nam);
	}

	private void updateSelectedDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date selectedDate = txtDate.getDate();
		if (selectedDate != null) {
			txtDate.setDateFormatString("yyyy-MM-dd");
			System.out.println("Selected date: " + dateFormat.format(selectedDate));
		}
	}

	private void removeData() {
		DefaultTableModel df = (DefaultTableModel) tblTKHD.getModel();
		df.setRowCount(0);
	}

	public void DocDuLieu() {
//        removeData();
        List<HoaDon> dsHoaDon = null;
        try {
            dsHoaDon = hoaDonService.getAll();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        int stt = 1;
		for (HoaDon hd : dsHoaDon) {
			  DecimalFormat df = new DecimalFormat("#,###.##");
  	        String tongTien = df.format(hd.getTongTien() );
			tableModelHoaDon
					.addRow(new Object[] { stt++ + "", hd.getMaHD(), hd.getThoiGianLapHD(), hd.getKhachHang().getMaKH(),
							hd.getNhanVien().getMaNV(), hd.getKhuyenMai().getMaKM(), tongTien });
		}
	}

	private void populateTable(List<HoaDon> dsHoaDon) {
		clearTable();
		int stt = 1;
		for (HoaDon hd : dsHoaDon) {
			  DecimalFormat df = new DecimalFormat("#,###.##");
	  	        String tongTien = df.format(hd.getTongTien() );
			tableModelHoaDon
					.addRow(new Object[] { stt++, hd.getMaHD(), hd.getThoiGianLapHD(), hd.getKhachHang().getMaKH(),
							hd.getNhanVien().getMaNV(), hd.getKhuyenMai().getMaKM(), tongTien });
		}
	}

	private void txtDatePropertyChange(PropertyChangeEvent evt) {
	    // Định dạng ngày
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    Date selectedDate = txtDate.getDate();
	    if (selectedDate == null) {
	        JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày hợp lệ.");
	        return;
	    }

	    String formattedDate = dateFormat.format(selectedDate);
	    txtDate.setDateFormatString("dd-MM-yyyy");

	    java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        List<HoaDon> dsHoaDon = null;
        try {
            dsHoaDon = hoaDonService.getDoanhThuNgay(sqlDate.toLocalDate());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        removeData();

	    if (dsHoaDon == null || dsHoaDon.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Không có hóa đơn trong ngày");
	    } else {
	        int stt = 1;
	        for (HoaDon hd : dsHoaDon) {

	        	  DecimalFormat df = new DecimalFormat("#,###.##");
	    	        String tongTien = df.format(hd.getTongTien() );
	            tableModelHoaDon.addRow(new Object[]{
	                String.valueOf(stt++),
	                hd.getMaHD(),
	                hd.getThoiGianLapHD(),
	                hd.getKhachHang().getMaKH(),
	                hd.getNhanVien().getMaNV(),
	                hd.getKhuyenMai().getMaKM(),
	                tongTien
	            });
	        }
	    }

        int tongHD = 0;
        try {
            tongHD = hoaDonService.getSoLuongHoaDonNgay(sqlDate.toLocalDate());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        lblTongHD.setText(String.valueOf(tongHD));
//	    double tongTien = hoaDonService.getTongTienNgay(sqlDate);
		 String COUNTRY = "VN";
		 String LANGUAGE = "vi";
//		 String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE,COUNTRY)).format(tongTien);
//	    lblTongTien.setText(String.valueOf(str));
	}



	private void clearTable() {
		tableModelHoaDon.setRowCount(0);
	}

	private void updateTotalCountAndAmount(int count, double amount) {

		 String COUNTRY = "VN";
		 String LANGUAGE = "vi";
		 String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE,COUNTRY)).format(amount);
		lblTongHD.setText(String.valueOf(count));
		lblTongTien.setText(str);
	}


	  private void updateChart(ArrayList<String[]> list, String nam) {



		  	pnlChart.removeAll();
	        chart = new Chart();;

	        pnlChart.removeAll();

//	        list = hoaDonService.getDoanhThuTungThangNam(nam);
	        chart.addLegend("Tổng tiền", new Color(12, 84, 175), new Color(0, 108, 247));
	        for (String[] arr : list) {
	            String month = arr[0];
	            double total = Double.parseDouble(arr[1]);
	            chart.addData(new ModelChart(month, new double[]{total}));
	        }
	        chart.start();

	        pnlChart.add(chart);
	    }






	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


}
