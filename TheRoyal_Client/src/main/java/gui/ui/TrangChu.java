package gui.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import com.formdev.flatlaf.FlatIntelliJLaf;

import dao.DonDatPhongDAO;
import dao.PhongDAO;

import entity.Phong;
import formatdate.FormatDate;
import gui.component.PanelShadow;
import gui.format_ui.RoundedBorder;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.DonDatPhongService;
import service.PhongService;

public class TrangChu extends JInternalFrame {

    JPanel top, center, bar1, bar2;
    private JPanel pnlPhongTrong , pnlPhongDangO , pnlPhongDat ;

    private PhongService phongService = RMIClient.getInstance().getPhongService();

    private DonDatPhongService donDatPhongService = RMIClient.getInstance().getDonDatPhongService();
	
    public TrangChu() {
        initComponent();
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
    	setBounds(100, 100, 1258, 554);
		setVisible(true);
		setClosable(true);

		setBorder(null); // Xóa viền
		setTitle(""); // Đặt title là chuỗi rỗng

		BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
		ui.setNorthPane(null);

    }

    private void initComponent() {
        this.setBackground(new Color(24, 24, 24));
        this.setBounds(0, 200, 300, 1200);
        getContentPane().setLayout(new BorderLayout(0, 0));
//        this.setOpaque(true);

        top = new JPanel();
        top.setBackground(new Color(255, 255, 255));
        top.setPreferredSize(new Dimension(1100, 200));
        top.setLayout(null);

        JLabel slogan = new JLabel();
        slogan.setBounds(60, 10, 1081, 161);
        slogan.setIcon(new ImageIcon(getClass().getResource("/ICON/icon/sologan.png")));
        top.add(slogan);

        getContentPane().add(top, BorderLayout.NORTH);

        center = new JPanel();
        center.setBackground(new Color(255, 255, 255));
        center.setPreferredSize(new Dimension(1100, 800));

        getContentPane().add(center, BorderLayout.CENTER);
        center.setLayout(null);
        
        pnlPhongTrong = new JPanel();
        pnlPhongTrong.setBounds(122, 99, 312, 218);
        center.add(pnlPhongTrong);
        pnlPhongTrong.setLayout(null);
        
        
        
        pnlPhongDangO = new JPanel();
        pnlPhongDangO.setBounds(514, 99, 312, 218);
        center.add(pnlPhongDangO);
        pnlPhongDangO.setLayout(null);
        
        
        pnlPhongDat = new JPanel();
        pnlPhongDat.setLayout(null);
        pnlPhongDat.setBounds(906, 99, 312, 218);
       center.add(pnlPhongDat);
       
       
        phongTrong ();
        phongDangO ();
        phongDangDat ();
        
     
      
    }
    
   
    @SneakyThrows
    private void phongTrong() {
        pnlPhongTrong.setBackground(new Color(153, 255, 153));
        pnlPhongTrong.setLayout(null);

        // Header Panel
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(153, 255, 153));
        panel_1.setBounds(203, 0, 109, 65);
        panel_1.setLayout(null);
        pnlPhongTrong.add(panel_1);

        JLabel lblNewLabel_2 = new JLabel();
        lblNewLabel_2.setBounds(40, 11, 24, 24);
        lblNewLabel_2.setIcon(new ImageIcon(DanhSachPhong_GUI.class.getResource("/src/icon/phongtrong24.png")));
        panel_1.add(lblNewLabel_2);

        JLabel lblNewLabel_1_1 = new JLabel("Phòng trống");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNewLabel_1_1.setBounds(0, 29, 100, 36);
        panel_1.add(lblNewLabel_1_1);

        // Room Count Panel
        RoundedBorder panel_3 = new RoundedBorder(94);
        panel_3.setBackground(new Color(255, 255, 255));
        panel_3.setForeground(new Color(204, 255, 204));
        panel_3.setBounds(111, 64, 90, 90);
        panel_3.setLayout(new BorderLayout(0, 0));
        pnlPhongTrong.add(panel_3);

        JLabel lblNewLabel_4 = new JLabel();
        int phongTrong = phongService.getTongSoPhong();
        lblNewLabel_4.setText(String.valueOf(phongTrong));
        lblNewLabel_4.setForeground(new Color(153, 255, 153));
        lblNewLabel_4.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
        panel_3.add(lblNewLabel_4, BorderLayout.CENTER);
    }

    
    
    @SneakyThrows
    private void phongDangO () {
    	pnlPhongDangO.setBackground(new Color(255, 153, 51));
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 153, 51));
		panel_1.setBounds(203, 0, 109, 65);
		pnlPhongDangO.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(40, 11, 24, 24);
		lblNewLabel_2.setIcon(new ImageIcon(DanhSachPhong_GUI.class.getResource("/src/icon/khachdango24.png")));
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_1_1 = new JLabel("Đang ở");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(0, 29, 100, 36);
		panel_1.add(lblNewLabel_1_1);

		
		RoundedBorder panel_3 = new RoundedBorder(94);
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setForeground(new Color(204, 255, 204));
		panel_3.setBounds(111, 64, 90, 90);
		pnlPhongDangO.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_4 = new JLabel();
		java.sql.Date ngayhientai = new java.sql.Date(System.currentTimeMillis());
        int phongTrong = donDatPhongService.countSLDonDangO(ngayhientai);
		lblNewLabel_4.setText(String.valueOf(phongTrong));
		lblNewLabel_4.setForeground(new Color(255, 153, 51));
		lblNewLabel_4.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setIcon(null);
		panel_3.add(lblNewLabel_4, BorderLayout.CENTER);

		
		pnlPhongDangO.setLayout(null);
    }
    
    
    @SneakyThrows
    private void phongDangDat () {
    	pnlPhongDat.setBackground(Color.RED);
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.RED);
		panel_1.setBounds(203, 0, 109, 65);
		pnlPhongDat.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(40, 11, 24, 24);
		lblNewLabel_2.setIcon(new ImageIcon(DanhSachPhong_GUI.class.getResource("/src/icon/khachdango24.png")));
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_1_1 = new JLabel("Đã đặt");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(0, 29, 100, 36);
		panel_1.add(lblNewLabel_1_1);

		
		RoundedBorder panel_3 = new RoundedBorder(94);
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setForeground(new Color(204, 255, 204));
		panel_3.setBounds(111, 64, 90, 90);
		pnlPhongDat.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_4 = new JLabel();
		java.sql.Date ngayhientai = new java.sql.Date(System.currentTimeMillis());
        int phongTrong = donDatPhongService.countSLDonDatTruoc(ngayhientai);
		lblNewLabel_4.setText(String.valueOf(phongTrong));
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setFont(new Font("Bookman Old Style", Font.BOLD, 50));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setIcon(null);
		panel_3.add(lblNewLabel_4, BorderLayout.CENTER);

		
		pnlPhongDat.setLayout(null);
    }
}
