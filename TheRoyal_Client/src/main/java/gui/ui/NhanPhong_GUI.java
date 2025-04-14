package gui.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import dao.*;
import entity.*;
import Format_UI.ButtonCellEditor;
import Format_UI.ButtonCellRenderer;
import Format_UI.MaterialTabbed;
import Format_UI.RoundedBorder;
import Format_UI.SpinnerCellEditor;
import Format_UI.SpinnerCellRenderer;
import Format_UI.Table;
import connectDB.ConnectDB;
import dao.DichVuDAO;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.GridLayout;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import org.jdesktop.swingx.prompt.PromptSupport;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import java.awt.SystemColor;

public class NhanPhong_GUI extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private Table bangSP;
    private Table bangDV;
    private Table bangSPDV;
    private JTextField txtTmKimSP;
    private JTextField txtTmKimDV;
	private DefaultTableModel modelDV;
	private JTextField txtTimKiemKH;
	private JTextField txtHoTen;
	private JTextField txtSDT;
	private JTextField txtTuoi;
	private JTextField txtMa;
	private JTextField txtCCCD;
	private JTextField txtLoai;
	private ButtonGroup groupGioiTinh;
	private JRadioButton rdNam;
	private JRadioButton rdNu;
	private JTextField txtSoTreEm;
	private JTextField txtSoNguoiLon;
	private DefaultTableModel modelSP;
	private JTextField ghiChu;
	private JLabel txtMaPhong;
	private JLabel loaiPhong;
	private JLabel soPhong_2;
	private JDateChooser ngayNhanPhong;
	private JDateChooser ngayTraPhong;
	private JLabel tenPhong;
	private JLabel giuong;
	private JLabel giaPhong;
	private Component traPhong;
	private MaterialTabbed tabbedPane;
	private DecimalFormat df;
	private JLabel luu;
	private JLabel nhanPhong;
	private JLabel xemDon;
	private DanhSachPhong_GUI danhSachPhong;
	private DefaultTableModel modelSPDV;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
//   
    }

    /**
     * Create the dialog.
     */
  
    
    public NhanPhong_GUI(Phong phong, DanhSachPhong_GUI danhSachPhong) {
    	this.danhSachPhong = danhSachPhong;

        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Xóa thanh tiêu đề
        setUndecorated(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // Đặt kích thước và vị trí giữa màn hình
        setSize(1000, 800);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        getContentPane().add(contentPanel, BorderLayout.SOUTH);
             
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 192, 203));
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        
        JPanel panel_6_1 = new JPanel();
        panel_6_1.setLayout(null);
        panel_6_1.setBackground(Color.WHITE);
        
        RoundedBorder panel_7_2 = new RoundedBorder(10);
        panel_7_2.setBackground(Color.RED);
        panel_7_2.setBounds(850, 11, 105, 35);
        
        panel_7_2.setLayout(new BorderLayout(0, 0));
        
        JLabel lblNewLabel_2 = new JLabel("Hủy");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setForeground(Color.WHITE);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel_7_2.add(lblNewLabel_2, BorderLayout.CENTER);
        
        RoundedBorder panel_7_1_1 = new RoundedBorder(10);
        panel_7_1_1.setBackground(SystemColor.textHighlight);
        panel_7_1_1.setBounds(735, 11, 105, 35);
        panel_6_1.add(panel_7_1_1);
        panel_7_1_1.setLayout(new BorderLayout(0, 0));
        
        luu = new JLabel("Lưu");
        luu.setForeground(Color.WHITE);
        luu.setFont(new Font("Tahoma", Font.BOLD, 20));
        luu.setHorizontalAlignment(SwingConstants.CENTER);
        
        nhanPhong = new JLabel("Nhận");
        nhanPhong.setForeground(Color.WHITE);
        nhanPhong.setFont(new Font("Tahoma", Font.BOLD, 20));
        nhanPhong.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        if(phong.getTrangThai().equals("Đã đặt")) {
        	panel_7_1_1.add(nhanPhong, BorderLayout.CENTER);
        	panel_6_1.add(panel_7_2);
        	nhanPhong.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
            		
					PhongDAO phongDao = new PhongDAO();
					Phong thayDoiTrangThai = phongDao.getPhongByMaPhong(phong.getMaPhong());
					thayDoiTrangThai.setTrangThai("Đang ở");
					phongDao.updateTinhTrang(thayDoiTrangThai, "Đang ở");
	                danhSachPhong.capNhatLaiDanhSachPhong();
					dispose();
            	}
            });
        	
        	panel_6_1.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
            		dispose();
            	}
            });
        	
        }else {
        	panel_7_1_1.add(luu, BorderLayout.CENTER);
            luu.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
//            		loadCTHoaDonToTable(phong);
            		luuCTHoaDonTuBangSPDV(phong);
            		dispose();
            	}
            });
        }
        
        
        
        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(
        	gl_contentPanel.createParallelGroup(Alignment.TRAILING)
        		.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        		.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        		.addComponent(panel_6_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
        );
        gl_contentPanel.setVerticalGroup(
        	gl_contentPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPanel.createSequentialGroup()
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 661, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_6_1, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
        );
        panel_1.setLayout(new BorderLayout(0, 0));
        
        tabbedPane = new MaterialTabbed();
        panel_1.add(tabbedPane, BorderLayout.CENTER);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(255, 255, 255));
        tabbedPane.addTab("Thông tin", null, panel_2, null);
        panel_2.setLayout(null);
        
        JPanel panel_10 = new JPanel();
        panel_10.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(255, 192, 203)));
        panel_10.setBackground(Color.WHITE);
        panel_10.setBounds(0, 0, 995, 195);
        panel_2.add(panel_10);
        panel_10.setLayout(null);
        
        JLabel lblNewLabel_3 = new JLabel("Tìm kiếm :");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3.setBounds(30, 11, 173, 32);
        panel_10.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		KhachHang kh = new KhachHang();
        		KhachHangDAO khTimDuoc = new KhachHangDAO();
        		kh = khTimDuoc.getKhachHangTheoSDTHoacCCCD(txtTimKiemKH.getText());
        		txtHoTen.setText(kh.getTenKH());
        		txtCCCD.setText(kh.getcCCD());
        		txtLoai.setText(kh.getLoaiKH().toString());
        		txtMa.setText(kh.getMaKH());
        		txtSDT.setText(kh.getsDT());
        		if(kh.isGioiTinh() == true) {
        			rdNam.setSelected(true);
        		}else {
        			rdNu.setSelected(true);
        		}
        	}
        });
        lblNewLabel_4.setIcon(new ImageIcon(NhanPhong_GUI.class.getResource("/ICON/icon/search.png")));
        lblNewLabel_4.setBounds(771, 11, 24, 32);
        panel_10.add(lblNewLabel_4);
        
        JLabel lblNewLabel_3_2 = new JLabel("Họ và tên :");
        lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2.setBounds(30, 60, 112, 32);
        panel_10.add(lblNewLabel_3_2);
        
        JLabel lblNewLabel_3_3 = new JLabel("Tuổi :");
        lblNewLabel_3_3.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_3.setBounds(30, 140, 107, 32);
        panel_10.add(lblNewLabel_3_3);
        
        JLabel lblNewLabel_3_2_1 = new JLabel("Mã khách hàng :");
        lblNewLabel_3_2_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_1.setBounds(462, 60, 127, 32);
        panel_10.add(lblNewLabel_3_2_1);
        
        JLabel lblNewLabel_3_3_1 = new JLabel("CCCD :");
        lblNewLabel_3_3_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_3_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_3_1.setBounds(462, 100, 135, 32);
        panel_10.add(lblNewLabel_3_3_1);
        
        JLabel lblNewLabel_3_3_1_1 = new JLabel("Giới tính :");
        lblNewLabel_3_3_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_3_1_1.setBounds(791, 145, 85, 32);
        panel_10.add(lblNewLabel_3_3_1_1);
        
        JLabel lblNewLabel_3_1 = new JLabel("SDT :");
        lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_1.setBounds(30, 100, 117, 32);
        panel_10.add(lblNewLabel_3_1);
        
        JLabel lblNewLabel_3_2_3 = new JLabel("Loại khách hàng :");
        lblNewLabel_3_2_3.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_3.setBounds(462, 140, 135, 32);
        panel_10.add(lblNewLabel_3_2_3);
        
        RoundedBorder panel_7_1_1_1 = new RoundedBorder(10);
        panel_7_1_1_1.setBackground(new Color(255, 192, 203));
        panel_7_1_1_1.setBounds(825, 8, 127, 35);
        panel_10.add(panel_7_1_1_1);
        panel_7_1_1_1.setLayout(new BorderLayout(0, 0));
        
        JLabel lblNewLabel_5 = new JLabel("Thêm mới");
        lblNewLabel_5.setForeground(new Color(255, 255, 255));
        lblNewLabel_5.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5.setIcon(new ImageIcon(NhanPhong_GUI.class.getResource("/ICON/icon/khachhang_bar.png")));
        panel_7_1_1_1.add(lblNewLabel_5, BorderLayout.CENTER);
        
        txtHoTen = new JTextField();
        txtHoTen.setBounds(147, 60, 273, 32);
        txtHoTen.setBorder(new MatteBorder(0,0,1,0, Color.LIGHT_GRAY));
        panel_10.add(txtHoTen);
        txtHoTen.setColumns(10);
        
        txtSDT = new JTextField();
        txtSDT.setColumns(10);
        txtSDT.setBorder(new MatteBorder(0,0,1,0, Color.LIGHT_GRAY));
        txtSDT.setBounds(147, 100, 273, 32);
        panel_10.add(txtSDT);
        
        txtTuoi = new JTextField();
        txtTuoi.setColumns(10);
        txtTuoi.setBorder(new MatteBorder(0,0,1,0, Color.LIGHT_GRAY));
        txtTuoi.setBounds(147, 140, 273, 32);
        panel_10.add(txtTuoi);
        
        txtMa = new JTextField();
        txtMa.setColumns(10);
        txtMa.setBounds(599, 60, 273, 32);
        txtMa.setBorder(new MatteBorder(0,0,1,0, Color.LIGHT_GRAY));
        panel_10.add(txtMa);
        
        txtCCCD = new JTextField();
        txtCCCD.setColumns(10);
        txtCCCD.setBorder(new MatteBorder(0,0,1,0, Color.LIGHT_GRAY));
        txtCCCD.setBounds(599, 100, 273, 32);
        panel_10.add(txtCCCD);
        
        txtLoai = new JTextField();
        txtLoai.setColumns(10);
        txtLoai.setBounds(599, 140, 182, 32);
        txtLoai.setBorder(new MatteBorder(0,0,1,0, Color.LIGHT_GRAY));
        panel_10.add(txtLoai);
        
         rdNam = new JRadioButton("Nam");
        rdNam.setBackground(Color.WHITE);
        rdNam.setBounds(868, 152, 54, 23);
        rdNam.setSelected(true); // Đặt "Nam" là mặc định
        panel_10.add(rdNam);

         rdNu = new JRadioButton("Nữ");
        rdNu.setBackground(Color.WHITE);
        rdNu.setBounds(924, 152, 47, 23);
        panel_10.add(rdNu);

        // Nhóm hai JRadioButton vào ButtonGroup
        groupGioiTinh = new ButtonGroup();
        groupGioiTinh.add(rdNam);
        groupGioiTinh.add(rdNu);

        RoundedBorder panel_12 = new RoundedBorder(10);
        
        panel_12.setBackground(Color.WHITE);
        panel_12.setBorderColor(Color.LIGHT_GRAY);
        panel_12.setBorderWidth(1);
        panel_12.setBounds(147, 14, 612, 30);
        panel_10.add(panel_12);
        panel_12.setLayout(null);
        
        txtTimKiemKH = new JTextField();
        txtTimKiemKH.setBackground(Color.WHITE);
        txtTimKiemKH.setBounds(2, 2, 608, 26);
        txtTimKiemKH.setBorder(new MatteBorder(0,0,0,0, Color.black));
        panel_12.add(txtTimKiemKH);
        PromptSupport.setPrompt("Tìm kiếm khách hàng(SDT hoặc CCCD)", txtTimKiemKH);
        txtTimKiemKH.setColumns(10);
        
        JPanel panel_11 = new JPanel();
        panel_11.setBackground(Color.WHITE);
        panel_11.setBounds(0, 199, 995, 419);
        panel_2.add(panel_11);
        panel_11.setBorder(new MatteBorder(1,1,1,1, new Color(255,192,203)));
        panel_11.setLayout(null);
        
        JLabel lblNewLabel_3_2_2 = new JLabel("Mã phòng :");
        lblNewLabel_3_2_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2.setBounds(34, 25, 105, 32);
        panel_11.add(lblNewLabel_3_2_2);
        
        JLabel lblNewLabel_3_2_2_1 = new JLabel("Loại phòng :");
        lblNewLabel_3_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_1.setBounds(32, 93, 107, 32);
        panel_11.add(lblNewLabel_3_2_2_1);
        
        JLabel lblNewLabel_3_2_2_1_1 = new JLabel("Giường :");
        lblNewLabel_3_2_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_1_1.setBounds(438, 93, 82, 32);
        panel_11.add(lblNewLabel_3_2_2_1_1);
        
        txtMaPhong = new JLabel("");
        txtMaPhong.setHorizontalAlignment(SwingConstants.LEFT);
        txtMaPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
        txtMaPhong.setBounds(149, 25, 258, 32);
        panel_11.add(txtMaPhong);
        
        loaiPhong = new JLabel("");
        loaiPhong.setHorizontalAlignment(SwingConstants.LEFT);
        loaiPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
        loaiPhong.setBounds(149, 93, 258, 32);
        panel_11.add(loaiPhong);
        
        soPhong_2 = new JLabel("");
        soPhong_2.setHorizontalAlignment(SwingConstants.LEFT);
        soPhong_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        soPhong_2.setBounds(625, 25, 155, 32);
        panel_11.add(soPhong_2);
        
        ngayNhanPhong = new JDateChooser();
     
        ngayNhanPhong.getCalendarButton().setIcon(new ImageIcon(NhanPhong_GUI.class.getResource("/ICON/icon/date.png")));
        ngayNhanPhong.getCalendarButton().setBackground(new Color(204, 255, 204));
        ngayNhanPhong.setToolTipText("");
        ngayNhanPhong.setDateFormatString("dd/MM/yyyy");
        ngayNhanPhong.setBounds(149, 151, 258, 32);
        panel_11.add(ngayNhanPhong);
        
        ngayTraPhong = new JDateChooser();
        ngayTraPhong.getCalendarButton().setIcon(new ImageIcon(NhanPhong_GUI.class.getResource("/ICON/icon/date.png")));
        ngayTraPhong.getCalendarButton().setBackground(new Color(204, 255, 204));
        ngayTraPhong.setToolTipText("");
        ngayTraPhong.setDateFormatString("dd/MM/yyyy");
        ngayTraPhong.setBounds(545, 151, 227, 32);
        panel_11.add(ngayTraPhong);
        
        JLabel lblNewLabel_3_2_2_2 = new JLabel("Nhận phòng :");
        lblNewLabel_3_2_2_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_2.setBounds(34, 151, 105, 32);
        panel_11.add(lblNewLabel_3_2_2_2);
        
        JLabel lblNewLabel_3_2_2_2_1 = new JLabel("Trả phòng :");
        lblNewLabel_3_2_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_2_1.setBounds(438, 151, 105, 32);
        panel_11.add(lblNewLabel_3_2_2_2_1);
        
        JLabel lblNewLabel_3_2_2_2_2 = new JLabel("Số người lớn :");
        lblNewLabel_3_2_2_2_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_2_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_2_2.setBounds(34, 208, 105, 32);
        panel_11.add(lblNewLabel_3_2_2_2_2);
        
        JLabel lblNewLabel_3_2_2_2_1_1 = new JLabel("Số trẻ em :");
        lblNewLabel_3_2_2_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_2_1_1.setBounds(438, 208, 105, 32);
        panel_11.add(lblNewLabel_3_2_2_2_1_1);
        
        JLabel lblNewLabel_3_2_2_1_1_1 = new JLabel("Giá :");
        lblNewLabel_3_2_2_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_1_1_1.setBounds(790, 25, 50, 32);
        panel_11.add(lblNewLabel_3_2_2_1_1_1);
        
        giaPhong = new JLabel("");
        giaPhong.setHorizontalAlignment(SwingConstants.LEFT);
        giaPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
        giaPhong.setBounds(830, 25, 155, 32);
        panel_11.add(giaPhong);
        
        txtSoTreEm = new JTextField();
        txtSoTreEm.setColumns(10);
        txtSoTreEm.setBorder(new MatteBorder(0,0,1,0, Color.LIGHT_GRAY));
        txtSoTreEm.setBounds(545, 211, 243, 32);
        panel_11.add(txtSoTreEm);
        
        txtSoNguoiLon = new JTextField();
        txtSoNguoiLon.setColumns(10);
        txtSoNguoiLon.setBorder(new MatteBorder(0,0,1,0, Color.LIGHT_GRAY));
        txtSoNguoiLon.setBounds(149, 211, 279, 32);
        panel_11.add(txtSoNguoiLon);
        
        JLabel lblNewLabel_3_2_2_3 = new JLabel("Số phòng :");
        lblNewLabel_3_2_2_3.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_3.setBounds(438, 25, 105, 32);
        panel_11.add(lblNewLabel_3_2_2_3);
        
        tenPhong = new JLabel("");
        tenPhong.setHorizontalAlignment(SwingConstants.LEFT);
        tenPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
        tenPhong.setBounds(545, 25, 215, 32);
        panel_11.add(tenPhong);
        
        JLabel lblNewLabel_3_2_2_2_2_1 = new JLabel("Ghi chú :");
        lblNewLabel_3_2_2_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3_2_2_2_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3_2_2_2_2_1.setBounds(34, 272, 105, 32);
        panel_11.add(lblNewLabel_3_2_2_2_2_1);
        
        RoundedBorder panel_12_1 = new RoundedBorder(10);
        panel_12_1.setLayout(null);
        panel_12_1.setBorderWidth(2);
        panel_12_1.setBorderColor(Color.LIGHT_GRAY);
        panel_12_1.setBackground(Color.WHITE);
        panel_12_1.setBounds(148, 272, 824, 104);
        panel_11.add(panel_12_1);
        
        ghiChu = new JTextField();
        ghiChu.setColumns(10);
        ghiChu.setBorder(new MatteBorder(0,0,0,0, Color.black));
        ghiChu.setBackground(Color.WHITE);
        ghiChu.setBounds(4, 13, 810, 78);
        panel_12_1.add(ghiChu);
        
        giuong = new JLabel("");
        giuong.setHorizontalAlignment(SwingConstants.LEFT);
        giuong.setFont(new Font("Tahoma", Font.BOLD, 15));
        giuong.setBounds(545, 93, 215, 32);
        panel_11.add(giuong);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(Color.WHITE);
        tabbedPane.addTab("Sản phẩm & dịch vụ", null, panel_3, null);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(Color.WHITE);
        
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.WHITE);
        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
        gl_panel_3.setHorizontalGroup(
        	gl_panel_3.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE))
        );
        gl_panel_3.setVerticalGroup(
        	gl_panel_3.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
        				.addComponent(panel_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(panel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE))
        			.addContainerGap(67, Short.MAX_VALUE))
        );
        panel_5.setLayout(new BorderLayout(0, 0));
        
        String[] columnNamesSPDV = {"STT", "Tên sản phẩm & dịch vụ", "Số lượng", "Đơn giá", "Thành tiền", ""};
        modelSPDV = new DefaultTableModel(columnNamesSPDV, 0) {
        	 @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 2 || column == 5; // Cho phép chỉnh sửa cột số lượng và cột xóa
             }
        };
        bangSPDV = new Table();
        bangSPDV.setBackground(Color.WHITE);
        bangSPDV.setModel(modelSPDV);
      
        // Thiết lập kích thước cột
        bangSPDV.getColumnModel().getColumn(0).setPreferredWidth(50);  // STT
        bangSPDV.getColumnModel().getColumn(1).setPreferredWidth(250); // Tên sản phẩm & dịch vụ
        bangSPDV.getColumnModel().getColumn(2).setPreferredWidth(80);  // Số lượng
        bangSPDV.getColumnModel().getColumn(3).setPreferredWidth(100);  // Đơn giá
        bangSPDV.getColumnModel().getColumn(4).setPreferredWidth(100);  // Thành tiền
        bangSPDV.getColumnModel().getColumn(5).setPreferredWidth(70);   // Cột trống

   
        
        JScrollPane scrollPaneSPDV = new JScrollPane(bangSPDV);
        scrollPaneSPDV.setBorder(new MatteBorder(1, 1, 1, 1, new Color(255, 192, 203)));
        panel_5.add(scrollPaneSPDV);
        String[] columnNames = {"STT", "Tên sản phẩm", "Giá tiền", "Số lượng"};
        modelSP = new DefaultTableModel(columnNames, 0) {
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        bangSP = new Table();
        bangSP.setModel(modelSP);
        bangSP.getColumnModel().getColumn(0).setPreferredWidth(50); // STT
        bangSP.getColumnModel().getColumn(1).setPreferredWidth(200); // Tên dịch vụ
        bangSP.getColumnModel().getColumn(2).setPreferredWidth(100); // Giá tiền
        bangSP.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        
     // Thêm sự kiện cho bảng Sản phẩm
        bangSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = bangSP.getSelectedRow();
                    if (row != -1) {
                        String tenSP = bangSP.getValueAt(row, 1).toString(); // Tên sản phẩm
                        double giaTien = Double.parseDouble(bangSP.getValueAt(row, 2).toString()); // Giá tiền
                        int soLuongSP = Integer.parseInt(bangSP.getValueAt(row, 3).toString()); // Lấy số lượng sản phẩm hiện tại

                        // Kiểm tra nếu sản phẩm đã tồn tại trong bảng SPDV
                        boolean found = false;
                        for (int i = 0; i < modelSPDV.getRowCount(); i++) {
                            if (modelSPDV.getValueAt(i, 1).equals(tenSP)) {
                                int soLuong = Integer.parseInt(modelSPDV.getValueAt(i, 2).toString()) + 1;
                                modelSPDV.setValueAt(soLuong, i, 2); // Cập nhật số lượng trong SPDV
                                modelSPDV.setValueAt(soLuong * giaTien, i, 4); // Cập nhật thành tiền trong SPDV
                                found = true;
                                break;
                            }
                        }

                        // Nếu không tìm thấy sản phẩm, thêm mới vào bảng SPDV
                        if (!found) {
                            int stt = modelSPDV.getRowCount() + 1;
                            modelSPDV.addRow(new Object[]{stt, tenSP, 1, giaTien, giaTien});
                        }

          
                        if (soLuongSP > 0) { 
                            soLuongSP--;
                            bangSP.setValueAt(soLuongSP, row, 3); 
                        }
                    }
                }
            }
        });

        
        JPanel panel_8 = new JPanel();
        panel_8.setBounds(0, 0, 290, 29);
        
        // Đặt bảng vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(bangSP);
        scrollPane.setBorder(new MatteBorder(1, 1, 1, 1,  new Color(255, 192, 203)));
        
        scrollPane.setFocusable(false);
        scrollPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        scrollPane.setBounds(0, 35, 332, 270);
        bangSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
     
        
        String[] columnNamesDV = {"STT", "Tên dịch vụ", "Giá tiền", "Số lượng"};
        modelDV = new DefaultTableModel(columnNamesDV, 0) {
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bangDV = new Table();
        bangDV.setModel(modelDV);
        // Thiết lập kích thước cột
        bangDV.getColumnModel().getColumn(0).setPreferredWidth(50); // STT
        bangDV.getColumnModel().getColumn(1).setPreferredWidth(200); // Tên dịch vụ
        bangDV.getColumnModel().getColumn(2).setPreferredWidth(100); // Giá tiền
        bangDV.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        
      
        JPanel panel_9 = new JPanel();
        panel_9.setBounds(0, 312, 290, 29);

        // Thêm bảng vào scroll pane (nếu cần)
        JScrollPane scrollPaneDV = new JScrollPane(bangDV);
        scrollPaneDV.setBorder(new MatteBorder(1, 1, 1, 1,  new Color(255, 192, 203)));
        scrollPaneDV.setBounds(0, 345, 332, 265);
        bangDV.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        bangDV.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = bangDV.getSelectedRow();
                    if (row != -1) {
                        String tenDV = bangDV.getValueAt(row, 1).toString(); // Tên dịch vụ
                        double giaDV = Double.parseDouble(bangDV.getValueAt(row, 2).toString()); // Giá dịch vụ
                        int soLuongDichVu = Integer.parseInt(bangDV.getValueAt(row, 3).toString()); // Lấy số lượng dịch vụ hiện tại

                        // Kiểm tra nếu dịch vụ đã tồn tại trong bảng SPDV
                        boolean found = false;
                        for (int i = 0; i < modelSPDV.getRowCount(); i++) {
                            if (modelSPDV.getValueAt(i, 1).equals(tenDV)) {
                                int soLuong = Integer.parseInt(modelSPDV.getValueAt(i, 2).toString()) + 1;
                                modelSPDV.setValueAt(soLuong, i, 2); 
                                modelSPDV.setValueAt(soLuong * giaDV, i, 4); 
                                found = true;
                                break;
                            }
                        }

                        // Nếu không tìm thấy dịch vụ, thêm mới vào bảng SPDV
                        if (!found) {
                            int stt = modelSPDV.getRowCount() + 1;
                            modelSPDV.addRow(new Object[]{stt, tenDV, 1, giaDV, giaDV});
                        }

                        if (soLuongDichVu > 0) { 
                            soLuongDichVu--; 
                            bangDV.setValueAt(soLuongDichVu, row, 3);
                        }
                    }
                }
            }
        });
        // Thiết lập cột số lượng với JSpinner
        bangSPDV.getColumnModel().getColumn(2).setCellRenderer(new SpinnerCellRenderer());
        bangSPDV.getColumnModel().getColumn(2).setCellEditor(new SpinnerCellEditor(bangSPDV, modelSPDV, bangSP,modelSP, bangDV,  modelDV));
        
        // Thiết lập cột xóa với nút xóa
        bangSPDV.getColumnModel().getColumn(5).setCellRenderer(new ButtonCellRenderer());
        bangSPDV.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor(modelSPDV, modelSP, modelDV));

        
        panel_4.setLayout(null);
        panel_4.add(panel_9);
        panel_9.setLayout(new BorderLayout(0, 0));
        
        txtTmKimDV = new JTextField();
        txtTmKimDV.setBorder(new MatteBorder(1, 1, 1, 1, new Color(255, 192, 203)));
        PromptSupport.setPrompt("Tìm kiếm dịch vụ", txtTmKimDV);
        txtTmKimDV.setForeground(Color.BLACK);
        panel_9.add(txtTmKimDV, BorderLayout.CENTER);
        txtTmKimDV.setColumns(10);
        panel_4.add(scrollPane);
        panel_4.add(scrollPaneDV);
        panel_4.add(panel_8);
        panel_8.setLayout(new BorderLayout(0, 0));
        
        txtTmKimSP = new JTextField();
        txtTmKimSP.setBorder(new MatteBorder(1, 1, 1, 1,  new Color(255, 192, 203)));
        PromptSupport.setPrompt("Tìm kiếm sản phẩm", txtTmKimSP);
        txtTmKimSP.setForeground(Color.BLACK);
        panel_8.add(txtTmKimSP, BorderLayout.CENTER);
        txtTmKimSP.setColumns(10);
        
        JLabel timKiemSP = new JLabel("");
        timKiemSP.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		SanPhamDAO spDao = new SanPhamDAO();
        		SanPham sp = new SanPham();
        		sp = spDao.getSanPhamTheoMaHoacTen(txtTmKimSP.getText());
        		  if (sp != null) {
        	            modelSP.getDataVector().removeAllElements();
        	            modelSP.addRow(new Object[] {1, sp.getTenSP(), sp.getGiaSP(), sp.getSoLuongSP()});
        	        } else {
        	            JOptionPane.showMessageDialog(null, "Không tìm thấy dịch vụ với mã hoặc tên đã nhập.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        	            modelSP.getDataVector().removeAllElements(); // Xóa các dòng trong bảng nếu cần
        	            modelSP.fireTableDataChanged(); // Cập nhật lại bảng
        	        }
        	}
        });
        
        //Thuc hien hanh dong thi thay doi text
        txtTmKimSP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkEmpty();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkEmpty();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkEmpty();
            }

            // Kiểm tra nếu JTextField rỗng, thực hiện hành động
            private void checkEmpty() {
                if (txtTmKimSP.getText().isEmpty()) {
                   loadSanPhamToTable();
                }
            }
        });
        
        
        timKiemSP.setIcon(new ImageIcon(NhanPhong_GUI.class.getResource("/ICON/icon/search.png")));
        timKiemSP.setBounds(298, 0, 24, 32);
        panel_4.add(timKiemSP);
        
        JLabel timKiemDV = new JLabel("");
        timKiemDV.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		DichVuDAO dvDao = new DichVuDAO();
        		DichVu dv = dvDao.timDichVuTheoMaHoacTheoTen(txtTmKimDV.getText());
        		  if (dv != null) {
        	            modelDV.getDataVector().removeAllElements();
        	            modelDV.addRow(new Object[] {1, dv.getTenDV(), dv.getGiaDV(), dv.getSoLuongDV()});
        	        } else {
        	            JOptionPane.showMessageDialog(null, "Không tìm thấy dịch vụ với mã hoặc tên đã nhập.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        	            modelDV.getDataVector().removeAllElements(); // Xóa các dòng trong bảng nếu cần
        	            modelDV.fireTableDataChanged(); // Cập nhật lại bảng
        	        }
        	}
        });
        
        
        //Thuc hien hanh dong thi thay doi text
        txtTmKimDV.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkEmpty();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkEmpty();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkEmpty();
            }

            // Kiểm tra nếu JTextField rỗng, thực hiện hành động
            private void checkEmpty() {
                if (txtTmKimDV.getText().isEmpty()) {
                    loadDichVuToTable();
                }
            }
        });
        
        timKiemDV.setIcon(new ImageIcon(NhanPhong_GUI.class.getResource("/ICON/icon/search.png")));
        timKiemDV.setBounds(300, 309, 24, 32);
        panel_4.add(timKiemDV);
        panel_3.setLayout(gl_panel_3);
        
        
        //Thanh toan
//        JPanel panel_13 = new JPanel();
//        panel_13.setBackground(Color.WHITE);
//        //tabbedPane.addTab("Trả phòng", null, panel_13, null);
//        panel_13.setLayout(null);
        
//        JLabel lblNewLabel_3_2_4 = new JLabel("Họ và tên :");
//        lblNewLabel_3_2_4.setBounds(28, 39, 82, 19);
//        lblNewLabel_3_2_4.setHorizontalAlignment(SwingConstants.LEFT);
//        lblNewLabel_3_2_4.setFont(new Font("Tahoma", Font.BOLD, 15));
//        panel_13.add(lblNewLabel_3_2_4);
//        
   
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("NHẬN PHÒNG KHÁCH SẠN");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(0, 0, 300, 72);
        panel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        	}
        });
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setIcon(new ImageIcon(NhanPhong_GUI.class.getResource("/ICON/icon/exit.png")));
        lblNewLabel_1.setBounds(953, 24, 37, 37);
        
     
        panel.add(lblNewLabel_1);
        contentPanel.setLayout(gl_contentPanel);
        
        //tabbedPane.add();
        loadSanPhamToTable();
        loadDichVuToTable();
        loadDonDatPhong(phong.getMaPhong());
        loadCTHoaDonToTable(phong);
    }
    
    public void loadSanPhamToTable() {
        SanPhamDAO sanPhamDao = new SanPhamDAO();
        ArrayList<SanPham> listSanPham = sanPhamDao.getDanhSachSanPham();

        // Lấy model từ bảng bangSP và xóa dữ liệu cũ
        DefaultTableModel model = (DefaultTableModel) bangSP.getModel();
        model.setRowCount(0); // Xóa các dòng cũ (nếu có)

        int stt = 1;
        for (SanPham sp : listSanPham) {
            String tenSP = sp.getTenSP();
            Double giaTien = sp.getGiaSP();
            int soLuongSP = sp.getSoLuongSP();
			// Thêm dữ liệu vào model
            model.addRow(new Object[]{stt++, tenSP, giaTien, soLuongSP });
        }
        
    }
    
    public void loadDichVuToTable() {
        DichVuDAO dichVuDao = new DichVuDAO();
        ArrayList<DichVu> listDichVu = dichVuDao.getDanhSachDichVu();

        // Lấy model từ bảng bangDV và xóa dữ liệu cũ
        DefaultTableModel model = (DefaultTableModel) bangDV.getModel();
        model.setRowCount(0); // Xóa các dòng cũ (nếu có)

        int stt = 1;
        for (DichVu dv : listDichVu) {
            String tenDV = dv.getTenDV();
            Double giaDV = dv.getGiaDV();
            int soLuong = dv.getSoLuongDV();

            // Thêm dữ liệu vào model
            model.addRow(new Object[]{stt++, tenDV, giaDV, soLuong});
        }
    }
    
    
    public void loadDonDatPhong(String maPhong) {
    	DonDatPhong ddp = new DonDatPhong();
    	DonDatPhongDAO ddpDao = new DonDatPhongDAO();
    	ddp = ddpDao.getDonDatPhongTheoPhong(maPhong);
    	
    	KhachHang kh = new KhachHang();
    	KhachHangDAO khDao= new KhachHangDAO();
    	
    	kh = khDao.getKhachHangTheoMa(ddp.getKhachHang().getMaKH());
    	
    	txtHoTen.setText(kh.getTenKH());
		txtCCCD.setText(kh.getcCCD());
		txtLoai.setText(kh.getLoaiKH().toString());
		txtMa.setText(kh.getMaKH());
		txtSDT.setText(kh.getsDT());
		if(kh.isGioiTinh() == true) {
			rdNam.setSelected(true);
		}else {
			rdNu.setSelected(true);
		}
		
		Phong phong = new Phong();
		PhongDAO phongDao = new PhongDAO();
		
		phong = phongDao.getPhongByMaPhong(maPhong);
		 	txtMaPhong.setText(phong.getMaPhong());
	     	tenPhong.setText(phong.getTenPhong());
	     	double giaTien = phong.getGiaTien();
	     	df = new DecimalFormat("#,### VND");
	     	giaPhong.setText(df.format(giaTien)); 
	     	String maLoai = phong.getLoaiPhong().getMaLoai();
	     	 
	     	if(maLoai.equals("LP01")) {
	     		loaiPhong.setText("Phòng thường");
	     	}else if(maLoai.equals("LP02")){
	     		loaiPhong.setText("Phòng Vip");
	     	}else {
	     		loaiPhong.setText("Penthouse");
	     	}
	     	giuong.setText(String.valueOf(phong.getSoGiuong()));
	     	
//	     	if(phong.getTrangThai().equals("Đang ở")){
////	     		panel_13.add();
//	     	};
	     	
	     	ngayNhanPhong.setDate(ddp.getThoiGianNhanPhong());
	     	ngayTraPhong.setDate(ddp.getThoiGianTraPhong());
    };
    
    private String formatCurrency(double value) {
    	DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(value)+"đ"; // Định dạng giá trị với dấu phẩy và đơn vị tiền tệ
    }

    public void luuCTHoaDonTuBangSPDV(Phong phong) {
        HoaDonDAO hdDAO = new HoaDonDAO();
        HoaDon hoaDon = hdDAO.getHoaDonTheoPhong(phong.getMaPhong());
        
        for (int i = 0; i < modelSPDV.getRowCount(); i++) {
            String tenSPDV = (String) modelSPDV.getValueAt(i, 1);
            int soLuong = (int) modelSPDV.getValueAt(i, 2);
            double donGia = (double) modelSPDV.getValueAt(i, 3);
            String valueAt = modelSPDV.getValueAt(i, 4).toString();
            double thanhTien = Double.parseDouble(valueAt);

            SanPhamDAO sanPhamDao = new SanPhamDAO();
            SanPham sanPham = sanPhamDao.getSanPhamTheoMaHoacTen(tenSPDV);
            
            DichVuDAO dichVuDao = new DichVuDAO();
            DichVu dichVu = dichVuDao.timDichVuTheoMaHoacTheoTen(tenSPDV);

            CTHoaDon ctHoaDon = new CTHoaDon();
            ctHoaDon.setMaCTHD("CTHD" + (i + 1)); 
            ctHoaDon.setHoaDon(hoaDon); 
            
            if (sanPham != null) {
                ctHoaDon.setSanPham(sanPham);
                ctHoaDon.setSoLuongSP(soLuong);
                ctHoaDon.setTongTienSP(thanhTien); 
                ctHoaDon.setDichVu(null); 
                ctHoaDon.setSoLuongDV(0); 
                ctHoaDon.setTongTienDV(0); 
            } else if (dichVu != null) {
                ctHoaDon.setDichVu(dichVu);
                ctHoaDon.setSoLuongDV(soLuong);
                ctHoaDon.setTongTienDV(thanhTien); 
                ctHoaDon.setSanPham(null); 
                ctHoaDon.setSoLuongSP(0); 
                ctHoaDon.setTongTienSP(0);
            }

            // Lưu vào CTHoaDon
            CTHoaDonDAO ctHoaDonDao = new CTHoaDonDAO();
            ctHoaDonDao.saveCTHoaDon(ctHoaDon);
        }
    }

    public void loadCTHoaDonToTable(Phong phong) {
        // Lấy hóa đơn dựa trên phòng
        HoaDonDAO hdDAO = new HoaDonDAO();
        HoaDon hoaDon = hdDAO.getHoaDonTheoPhong(phong.getMaPhong());

        modelSPDV.setRowCount(0);

        CTHoaDonDAO ctHoaDonDao = new CTHoaDonDAO();
        List<CTHoaDon> listCTHD = ctHoaDonDao.getCTHoaDonByMaHoaDon(hoaDon.getMaHD());

        int stt = 1; // Khởi tạo số thứ tự
        for (CTHoaDon ctHoaDon : listCTHD) {
            Object[] row = new Object[6]; 

            row[0] = stt++; // Cột STT
            row[1] = (ctHoaDon.getSanPham() != null) ? 
                     ctHoaDon.getSanPham().getTenSP() : 
                     (ctHoaDon.getDichVu() != null ? ctHoaDon.getDichVu().getTenDV() : ""); // Cột Tên sản phẩm & dịch vụ
            row[2] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getSoLuongSP() : ctHoaDon.getSoLuongDV(); // Cột Số lượng
            row[3] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getTongTienSP() / ctHoaDon.getSoLuongSP() : 
                     (ctHoaDon.getDichVu() != null ? ctHoaDon.getTongTienDV() / ctHoaDon.getSoLuongDV() : 0); // Cột Đơn giá
            row[4] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getTongTienSP() : ctHoaDon.getTongTienDV(); // Cột Thành tiền
            row[5] = ""; // Cột trống cho các nút hoặc tùy chọn xóa (nếu cần)

            modelSPDV.addRow(row);
        }
    }
    
}
