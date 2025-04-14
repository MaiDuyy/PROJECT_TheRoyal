package gui.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Format_UI.Table;
import dao.CTHoaDonDAO;
import dao.DichVuDAO;
import dao.HoaDonDAO;
import dao.SanPhamDAO;
import entity.HoaDon;
import entity.SanPham;
import gui.chart.Chart;
import gui.chart.ModelChart;
import gui.chart.ModelPieChart;
import gui.chart.PieChart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
public class ThongKeSanPham_GUI extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JPanel pnlTKDT, pnlHeader, pnlBottom, pnlChart , pnlChart2;
	private JLabel lblTKMH, lblNgay, lblThang, lblNam, lblTongHD, lblTongTien, lblTong;
	private JComboBox<String> cmbTKTheo, cmbTKChiTiet;
	private JDateChooser txtDate;
	private Table tblTKSP , tblTKDV;
	private JScrollPane scr ,scrDV;
	private DefaultTableModel tableModelSanPham ,tableModelDichVu;
	private SanPhamDAO sanphamdao;
	 private PieChart pieChart1, pieChart2;
	private JPanel panelCenter;
	private JLabel lblBang;
	private JLabel lblBieuDo;
	private JLabel lblTongTienSP;
	private CTHoaDonDAO cthoadondao ;
	  private DefaultPieDataset dataset ,dataset2;
	    private PieDataset pieDataset;
	    private JLabel lblSanPham;
	    private JLabel lblDichVu;
	    private JLabel lblTongTienDV;
	    private JLabel lblTongTien_1;
	

	public ThongKeSanPham_GUI() {
		initUI();
		initComponents();
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

		cthoadondao = new CTHoaDonDAO();
		sanphamdao = new SanPhamDAO();
		pnlHeader = new JPanel();
		 pieChart1 = new PieChart();
		 pieChart2 = new PieChart()	;
		pnlHeader.setLocation(0, 0);
		pnlHeader.setSize(1364, 124);
		pnlHeader.setBackground(Color.WHITE);
		pnlHeader.setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		lblTKMH = new JLabel("THỐNG KÊ SẢN PHẨM", JLabel.CENTER);
		lblTKMH.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTKMH.setForeground(new Color(246, 167, 193));
		lblTKMH.setBounds(500, 10, 300, 27);

		JSeparator phanCach = new JSeparator();
		phanCach.setBounds(0, 125, screenSize.width, 10);
		getContentPane().add(phanCach);

		lblNgay = new JLabel("Ngày:");
		lblNgay.setBounds(315, 65, 50, 18);
		lblThang = new JLabel("Tháng:");
		lblThang.setBounds(480, 65, 50, 18);
		lblNam = new JLabel("Năm:");
		lblNam.setBounds(643, 65, 55, 18);

		cmbTKTheo = new JComboBox<>(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
		cmbTKTheo.setBounds(530, 62, 100, 21);
		cmbTKTheo.setBackground(Color.WHITE);
		cmbTKTheo.addActionListener(evt -> cmbTKTheoActionPerformed());

		cmbTKChiTiet = new JComboBox<>(
				new String[] { "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024" });
		cmbTKChiTiet.setBounds(708, 62, 143, 21);
		cmbTKChiTiet.addActionListener(evt -> cmbTKChiTietActionPerformed());

		txtDate = new JDateChooser();
		txtDate.getCalendarButton().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				txtDatePropertyChange(evt);
			}
		});
		
		txtDate.setBounds(360, 62, 110, 21);
		txtDate.setDate(new Date());
		txtDate.setDateFormatString("dd-MM-yyyy hh:mm");
//		txtDate.addPropertyChangeListener(evt -> updateSelectedDate());

		pnlHeader.add(lblTKMH);
		pnlHeader.add(lblNgay);
		pnlHeader.add(txtDate);
		pnlHeader.add(lblThang);
		pnlHeader.add(cmbTKTheo);
		pnlHeader.add(lblNam);
		pnlHeader.add(cmbTKChiTiet);

		pnlChart = new JPanel();
		pnlChart.setBackground(new Color(255, 255, 255));
		pnlChart.setBounds(0, 0, 682, 600);
	
		
		pnlChart2 = new JPanel();
		pnlChart2.setBackground(new Color(255, 255, 255));
		pnlChart2.setBounds(682, 0, 682, 600);
		
		pnlChart.setLayout(null);
		
		lblSanPham = new JLabel("BIỂU ĐỒ SẢN PHẨM" );
		lblSanPham.setBounds(260, 10, 150, 21);
		lblSanPham.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSanPham.setForeground(new Color(246, 167, 193));
		pieChart1.add(lblSanPham);
		
		pnlChart2.setLayout(null);
		
		lblDichVu = new JLabel("BIỂU ĐỒ DỊCH VỤ");
		lblDichVu.setBounds(279, 10, 150, 21);
		lblDichVu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDichVu.setForeground(new Color(246, 167, 193));
		pieChart2.add(lblDichVu);
	

		panelCenter = new JPanel();
		panelCenter.setBackground(new Color(255, 255, 255));
		panelCenter.setBounds(0, 134, 1364, 628);
		getContentPane().add(panelCenter);

		String[] columns = { "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng tiêu thụ", "Tỷ lệ","Tổng giá trị" };
		tableModelSanPham = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		panelCenter.setLayout(null);
		
		tblTKSP = new Table();
		tblTKSP.setModel(tableModelSanPham);
		tblTKSP.setBackground(Color.white);
		tblTKSP.setFillsViewportHeight(true); 
		tblTKSP.setBackground(new Color(255, 255, 255));

		scr = new JScrollPane(tblTKSP);
		scr.setBounds(10, 5, 1281, 245);
		panelCenter.add(scr);
		
		
		
		String[] columndv = { "STT", "Mã dịch vụ", "Tên dịch vụ", "Số lượng tiêu thụ", "Tỷ lệ","Tổng giá trị"};
		tableModelDichVu = new DefaultTableModel(columndv, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		panelCenter.setLayout(null);
		

		tblTKDV = new Table();
		tblTKDV.setModel(tableModelDichVu);
		tblTKDV.setBackground(Color.white);
		tblTKDV.setFillsViewportHeight(true); 
		tblTKDV.setBackground(new Color(255, 255, 255));

		scrDV = new JScrollPane(tblTKDV);
		scrDV.setBounds(13, 301, 1281, 245);
		panelCenter.add(scrDV);
		
		
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
		lblTongTien.setBounds(838, 93, 120, 22);
		pnlHeader.add(lblTongTien);

		lblTongHD = new JLabel("");
		lblTongHD.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTongHD.setBounds(899, 94, 37, 22);
		pnlHeader.add(lblTongHD);
		
		lblTongTienSP = new JLabel("Tổng tiền sản phẩm: ");
		lblTongTienSP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTongTienSP.setBounds(680, 93, 148, 23);
		pnlHeader.add(lblTongTienSP);
		
		lblTongTienDV = new JLabel("Tổng tiền dịch vụ: ");
		lblTongTienDV.setBounds(964, 93, 142, 23);
		pnlHeader.add(lblTongTienDV);
		lblTongTienDV.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblTongTien_1 = new JLabel("0");
		lblTongTien_1.setBounds(1116, 94, 120, 22);
		pnlHeader.add(lblTongTien_1);
		lblTongTien_1.setFont(new Font("Tahoma", Font.PLAIN, 13));

		switchToTableTab();
//		switchToChartTab();
	}

	private void switchToTableTab() {
		panelCenter.removeAll();
		panelCenter.add(scr);
		panelCenter.add(scrDV);
		panelCenter.revalidate();
		panelCenter.repaint();

		lblBang.setBackground(new Color(200, 200, 255)); 
		lblBieuDo.setBackground(Color.WHITE);
	}

	private void switchToChartTab() {
		panelCenter.removeAll();
		panelCenter.add(pnlChart, BorderLayout.WEST);
		panelCenter.add(pnlChart2, BorderLayout.EAST);
		panelCenter.revalidate();
		panelCenter.repaint();

		lblBieuDo.setBackground(new Color(200, 200, 255)); 
		lblBang.setBackground(Color.WHITE); 
	}
	  private void removeData() {
	        DefaultTableModel dv = (DefaultTableModel) tblTKDV.getModel();
	        DefaultTableModel mh = (DefaultTableModel) tblTKSP.getModel();
	        dv.setRowCount(0);
	        mh.setRowCount(0);
	    }


	  private void cmbTKChiTietActionPerformed() {
		    try {
		        String nam = (String) cmbTKChiTiet.getSelectedItem();
		        if (nam == null) {
		            JOptionPane.showMessageDialog(this, "Vui lòng chọn năm hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        ArrayList<String[]> dsSPNam = cthoadondao.getTOPSPNam(nam);
		        ArrayList<String[]> dsDVNam = cthoadondao.getTOPDVNam(nam);
		        clearTable();
		        pieChart1.clearData();
		        pieChart2.clearData();

		        if (dsSPNam == null || dsSPNam.isEmpty() && dsDVNam == null || dsSPNam.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "Không có dữ liệu để hiển thị cho năm " + nam + ".", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		            return;
		        }

		     
		        int stt = 1;
		        for (int i = 0; i < Math.min(5, dsSPNam.size()); i++) {
		            String[] arr = dsSPNam.get(i);
		            DecimalFormat df = new DecimalFormat();
		            String tongTien = df.format( Double.parseDouble(arr[4]));
		            tableModelSanPham.addRow(new Object[]{
		            		
		            	
		                stt++, arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),tongTien
		            });
		        }
		        
		        int stt2= 1;
		        for (int i = 0; i < Math.min(5, dsDVNam.size()); i++) {
		            String[] arr = dsDVNam.get(i);
		            DecimalFormat df = new DecimalFormat();
		            String tongTien = df.format( Double.parseDouble(arr[4]));
		            tableModelDichVu.addRow(new Object[]{
		                stt2++, arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),tongTien
		            });
		        }

		        dataset = new DefaultPieDataset();
		        dataset2 = new DefaultPieDataset();
		        ArrayList<String[]> data = cthoadondao.getTKSPNam(nam);
		        ArrayList<String[]> data2 = cthoadondao.getTKDVNam(nam);

		        pnlChart.removeAll();
		        pnlChart.setLayout(new BorderLayout());
		        pnlChart2.removeAll();
		        pnlChart2.setLayout(new BorderLayout());
		        
		        int[][] rgbValues = {
		            {41, 173, 86},
		            {205, 13, 13},
		            {255, 153, 153},
		            {166, 208, 238},
		            {189, 135, 245}
		        };

		        int numDataPoints = Math.min(5, data.size());
		        for (int i = 0; i < numDataPoints; i++) {
		            String[] item = data.get(i);
		            String tenMH = item[1];
		            double tyle = Double.parseDouble(item[3]);
		            int[] rgb = rgbValues[i % rgbValues.length];
		            Color color = new Color(rgb[0], rgb[1], rgb[2]);

		            dataset.setValue(tenMH, tyle);
		            pieChart1.addData(new ModelPieChart(tenMH, tyle, color));
		        }
		        
		        int numDataPoints1 = Math.min(5, data2.size());
		        for (int i = 0; i < numDataPoints1; i++) {
		            String[] item = data2.get(i);
		            String tenMH = item[1];
		            double tyle = Double.parseDouble(item[3]);
		            int[] rgb = rgbValues[i % rgbValues.length];
		            Color color = new Color(rgb[0], rgb[1], rgb[2]);

		            dataset2.setValue(tenMH, tyle);
		            pieChart2.addData(new ModelPieChart(tenMH, tyle, color));
		        }
		        
		        SanPhamDAO ctsp = new SanPhamDAO();
		        double tongALLTienSP = ctsp.getTongTienSPNam(nam);
		        String formattedTienSP = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(tongALLTienSP);
		        lblTongTien.setText(formattedTienSP);
		        
		       
		        DichVuDAO ctdv = new DichVuDAO();
		        double tongALLTienDV = ctdv.getTongTienNam(nam);
		        String formattedTienDV = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(tongALLTienDV);
		        lblTongTien_1.setText(formattedTienDV);
		        

		        pnlChart.add(pieChart1, BorderLayout.CENTER);
		        pnlChart2.add(pieChart2, BorderLayout.CENTER);
		        pnlChart.revalidate();
		        pnlChart2.revalidate();
		        pnlChart.repaint();
		        pnlChart2.repaint();
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
		        e.printStackTrace();
		    }
		}
      
	  
	  
	  private void cmbTKTheoActionPerformed() {
		    try {
		        String thang = (String) cmbTKTheo.getSelectedItem();
		        String nam = (String) cmbTKChiTiet.getSelectedItem();
		        if (thang == null || nam == null) {
		            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng và năm hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        ArrayList<String[]> dsSPThang = cthoadondao.getTOPSPThang(thang, nam);
		        ArrayList<String[]> dsDVThang = cthoadondao.getTOPDVThang(thang, nam);
		        clearTable();
		        pieChart1.clearData();
		        pieChart2.clearData();
		        

		        if (dsSPThang == null || dsSPThang.isEmpty() && dsDVThang == null || dsDVThang.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "Không có dữ liệu để hiển thị cho tháng " + thang + " năm " + nam + ".", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		            return;
		        }
		        
		        int stt1 =  1;
		        for (int i = 0; i < Math.min(5, dsSPThang.size()); i++) {
		            String[] arr = dsSPThang.get(i);
		            DecimalFormat df = new DecimalFormat();
		            String tongTien = df.format( Double.parseDouble(arr[4]));
		            tableModelSanPham.addRow(new Object[]{
		            		
		            	
		                stt1++, arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),tongTien
		            });
		        }

		        int stt2= 1;
		        for (int i = 0; i < Math.min(5, dsDVThang.size()); i++) {
		            String[] arr = dsDVThang.get(i);
		            DecimalFormat df = new DecimalFormat();
		            String tongTien = df.format( Double.parseDouble(arr[4]));
		            tableModelDichVu.addRow(new Object[]{
		            		
		            	
		                stt2++, arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),tongTien
		            });
		        }
		        
		        dataset = new DefaultPieDataset();
		        dataset2 = new DefaultPieDataset();
		       
		        int[][] rgbValues = {
		            {41, 173, 86},
		            {205, 13, 13},
		            {255, 153, 153},
		            {166, 208, 238},
		            {189, 135, 245}
		        };

		        for (int i = 0; i < Math.min(5, dsSPThang.size()); i++) {
		            String[] item = dsSPThang.get(i);
		            String tenMH = item[1];
		            double tyle = Double.parseDouble(item[3]);
		            int[] rgb = rgbValues[i % rgbValues.length];
		            Color color = new Color(rgb[0], rgb[1], rgb[2]);

		            dataset.setValue(tenMH, tyle);
		            pieChart1.addData(new ModelPieChart(tenMH, tyle, color));
		        }
		        
		        for (int i = 0; i < Math.min(5, dsDVThang.size()); i++) {
		            String[] item = dsDVThang.get(i);
		            String tenMH = item[1];
		            double tyle = Double.parseDouble(item[3]);
		            int[] rgb = rgbValues[i % rgbValues.length];
		            Color color = new Color(rgb[0], rgb[1], rgb[2]);

		            dataset2.setValue(tenMH, tyle);
		            pieChart2.addData(new ModelPieChart(tenMH, tyle, color));
		        }
		        
		        SanPhamDAO ctsp = new SanPhamDAO();
		        double tongALLTienSP = ctsp.getTongTienThang(thang,nam);
		        String formattedTienSP = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(tongALLTienSP);
		        lblTongTien.setText(formattedTienSP);
		        
		       
		        DichVuDAO ctdv = new DichVuDAO();
		        double tongALLTienDV = ctdv.getTongTienThang(thang,nam);
		        String formattedTienDV = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(tongALLTienDV);
		        lblTongTien_1.setText(formattedTienDV);

		        pnlChart.add(pieChart1, BorderLayout.CENTER);
		        pnlChart2.add(pieChart2, BorderLayout.CENTER);
		        pnlChart.revalidate();
		        pnlChart2.revalidate();
		        pnlChart.repaint();
		        pnlChart2.repaint();
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
		        e.printStackTrace();
		    }
		}
	  
	  private void txtDatePropertyChange(PropertyChangeEvent evt) {                                       
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    try {
		        Date selectedDate = txtDate.getDate();
		        if (selectedDate == null) return;
		        String formattedDate = dateFormat.format(selectedDate);
		        txtDate.setDateFormatString("yyyy-MM-dd");

		        java.sql.Date sqlDate = new java.sql.Date(dateFormat.parse(formattedDate).getTime());

		        ArrayList<String[]> dsSPNgay = cthoadondao.getTOPSPNgay(sqlDate);
		        ArrayList<String[]> dsMHNgay = sanphamdao.getSPNgay(sqlDate);

		        ArrayList<String[]> dsDVNgay = cthoadondao.getTOPDVNgay(sqlDate);
		        
		        removeData();

		        int stt = 1;
		        for (int i = 0; i < Math.min(5, dsSPNgay.size()); i++) {
		            String[] arr = dsSPNgay.get(i);
		            String maDV = arr[0];
		            String tenDV = arr[1];
		            int soLuong = Integer.parseInt(arr[2]);
		            double tyLe = Double.parseDouble(arr[3]);
		            double tongTien = Double.parseDouble(arr[4]);
		            DecimalFormat df = new DecimalFormat();
		            String tongTienSP = df.format(tongTien);
		  
		            tableModelSanPham.addRow(new Object[]{stt++, maDV, tenDV, soLuong, tyLe, tongTienSP});
		        }
		        
		     
		        
		        
		        int stt2 = 1;
		        for (int i = 0; i < Math.min(5, dsDVNgay.size()); i++) {
		            String[] arr = dsDVNgay.get(i);
		            String maDV = arr[0];
		            String tenDV = arr[1];
		            int soLuong = Integer.parseInt(arr[2]);
		            double tyLe = Double.parseDouble(arr[3]);
		            double tongTien = Double.parseDouble(arr[4]);
		            DecimalFormat df = new DecimalFormat();
		            String tongTienDV = df.format(tongTien);
		  		  
		            tableModelDichVu.addRow(new Object[]{stt2++, maDV, tenDV, soLuong, tyLe, tongTienDV});
		        }

		        CTHoaDonDAO cthd = new CTHoaDonDAO();
		        double tongALLTienSP = cthd.getTongTienSPNgay(sqlDate);
		        String formattedTienSP = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(tongALLTienSP);
		        lblTongTien.setText(formattedTienSP);
		        
		        double tongALLTienDV = cthd.getTongTienDVNgay(sqlDate);
		        String formattedTienDV = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(tongALLTienDV);
		        lblTongTien_1.setText(formattedTienDV);
		        

		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		}
  
	  
	  private void clearTable() {
			tableModelSanPham.setRowCount(0);
			tableModelDichVu.setRowCount(0);
		}
}
