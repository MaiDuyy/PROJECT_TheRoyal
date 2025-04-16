package gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import gui.ui.QLKhachHang_GUI;
import gui.ui.QLNhanVien_GUI;
import gui.ui.QLUuDai_GUI;
import dao.DichVuDAO;
import dao.KhachHangDAO;
import dao.KhuyenMaiDAO;
import entity.DichVu;
import entity.KhachHang;
import entity.KhuyenMai;
import formatdate.FormatDate;
import gui.component.ButtonCustom;
import gui.component.HeaderTitle;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.KhuyenMaiService;

public class SuaKhuyenMai_Dialog extends JDialog {

    private static final long serialVersionUID = 1L;
	private JTextField txtTen, txtMa, txtGiaTri, txtMoTa,txtSoLuong, txtTim ;
	private JDateChooser ngaybatdau, ngayketthuc;
    private ArrayList < KhuyenMai > dsKM;
    private ButtonCustom btnThem, btnDong;
    private JLabel lbShowMessages;
    private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
    private QLUuDai_GUI home;

    private KhuyenMaiService khuyenMaiService = RMIClient.getInstance().getKhuyenMaiService();

    @SneakyThrows
    public SuaKhuyenMai_Dialog(javax.swing.JInternalFrame parent, javax.swing.JFrame owner, boolean modal) {
        super(owner, modal);
        GUI();
        setLocationRelativeTo(null);
        home = (QLUuDai_GUI) parent;
        dsKM = (ArrayList<KhuyenMai>) khuyenMaiService.getAll();
        KhuyenMai km = home.getSanPhanSelect();
        txtMa.setText(km.getMaKM());
        txtTen.setText(km.getTenKM()); 
        
        double gia = km.getGiaTriKhuyenMai();
        txtGiaTri.setText(Double.toString(gia).replace(".0", ""));
        txtMoTa.setText(km.getMoTaKM());

        
        ngaybatdau.setDate(km.getThoiGianBatDau());
        ngayketthuc.setDate(km.getThoiGianKetThuc());
        
    
        int soluong = km.getSoLuong();
        txtSoLuong.setText(Integer.toString(soluong)); 

    }

    public void GUI() {
        setSize(614, 491);
        HeaderTitle title = new HeaderTitle("SỬA KHUYẾN MÃI");
        // Panel thông tin khuyến mãiHeaderTitle title = new HeaderTitle("SỬA KHÁCH HÀNG");
		getContentPane().add(title, BorderLayout.NORTH);
        JPanel staffInfoPanel = new JPanel();
        staffInfoPanel.setBackground(new Color(255, 255, 255));
        staffInfoPanel.setBounds(61, 79, 552, 417);
        staffInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
                "Thông tin khuyến mãi",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.PLAIN, 12),
                new Color(246, 167, 193)
            ));

        JLabel lblMa = new JLabel("Mã khuyến mãi:");
        lblMa.setBounds(32, 35, 108, 30);
        txtMa = new JTextField(15);
        txtMa.setEditable(false);
        txtMa.setForeground(new Color(0, 0, 0));
        txtMa.setBackground(new Color(255, 255, 255));
        txtMa.setBounds(260, 36, 314, 29);
        JLabel lblTen = new JLabel("Tên khuyến mãi:");
        lblTen.setBounds(32, 75, 108, 30);
        txtTen = new JTextField(15);
        txtTen.setBounds(260, 75, 314, 30);
        JLabel lblGiaTri = new JLabel("Giá trị khuyến mãi:");
        lblGiaTri.setBounds(32, 115, 108, 30);
        txtGiaTri = new JTextField(15);
        txtGiaTri.setBounds(260, 116, 314, 30);
        JLabel lblStartDay = new JLabel("Ngày bắt đầu:");
        lblStartDay.setBounds(32, 155, 101, 30);
        ngaybatdau = new JDateChooser();
        ngaybatdau.setDateFormatString("dd-MM-yyyy");
        ngaybatdau.setDate(Date.valueOf(java.time.LocalDate.now()));
        ngaybatdau.setBounds(260, 155, 314, 30);
        staffInfoPanel.setLayout(null);

        staffInfoPanel.add(lblMa);
        staffInfoPanel.add(txtMa);
        staffInfoPanel.add(lblTen);
        staffInfoPanel.add(txtTen);
        staffInfoPanel.add(lblGiaTri);
        staffInfoPanel.add(txtGiaTri);
        staffInfoPanel.add(lblStartDay);
        staffInfoPanel.add(ngaybatdau);

        getContentPane().add(staffInfoPanel);
        
        JLabel lblMoTa = new JLabel("Mô tả khuyến mãi:");
        lblMoTa.setBounds(32, 238, 142, 22);
        staffInfoPanel.add(lblMoTa);
        
        txtMoTa = new JTextField();
        txtMoTa.setBounds(260, 235, 314, 30);
        staffInfoPanel.add(txtMoTa);
        txtMoTa.setColumns(10);
        
        ngayketthuc = new JDateChooser();
        ngayketthuc.setBounds(260, 195, 314, 30);
        ngayketthuc.setDateFormatString("dd-MM-yyyy");
        ngayketthuc.setDate(Date.valueOf(java.time.LocalDate.now()));
        staffInfoPanel.add(ngayketthuc);
        
        JLabel lblNgayKetThuc = new JLabel("Ngày kết thúc:");
        lblNgayKetThuc.setBounds(32, 195, 101, 30);
        staffInfoPanel.add(lblNgayKetThuc);
        
        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(32, 281, 88, 27);
        staffInfoPanel.add(lblSoLuong);
        
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(260, 278, 314, 30);
        staffInfoPanel.add(txtSoLuong);
        txtSoLuong.setColumns(10);
        
        lbShowMessages = new JLabel("");
        lbShowMessages.setBounds(143, 385, 295, 22);
        staffInfoPanel.add(lbShowMessages);
        

        btnThem = new ButtonCustom("Cập nhật","success", 12);
        btnThem.setLocation(161, 346);
        btnThem.setSize(100, 29);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNV();
            }
        });
    	btnDong =  new ButtonCustom("Hủy","danger", 12);
        btnDong.setLocation(301, 346);
        btnDong.setSize(100, 29);
        btnDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 int yes = JOptionPane.showConfirmDialog(null, 
                         "Bạn có chắc chắn muốn thoát?", "Thông báo", 
                         JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                 if (yes == JOptionPane.YES_OPTION) {
//                 	  System.exit(0);
                	 dispose();
                 }
            }
        });
        staffInfoPanel.add(btnDong);
        staffInfoPanel.add(btnThem);

    }
    @SneakyThrows
    private void themNV() {
    	 if(validData(UPDATE)) {
        KhuyenMai nv = dataUuDai();
        khuyenMaiService.update(nv);
        this.dispose();
        home.huytim();
        home.thongBao(0, "Cập nhật thành công");
    	 }
    }
    private boolean validData(int type) {
	    // type = 1 -> thêm mới
	    // type != 1 -> cập nhật
	  
	    String giaTriStr = txtGiaTri.getText();
	    String soLuongStr = txtSoLuong.getText();
	    float giaTri = 0.0f;
	    int soLuong = 0;

	    
	    String tenNV = txtTen.getText().trim();
	    if (tenNV.isEmpty()) {
	        home.showMessage("Lỗi: Tên không được để trống", txtTen);
	        return false;
	    }
	    if (!tenNV.matches("^[^0-9]+$")) {
	        home.showMessage("Lỗi: Tên không được có số", txtTen);
	        return false;
	    }

	    if (giaTriStr == null || giaTriStr.trim().isEmpty()) {
	        home.showMessage("Lỗi: Giá trị không được để trống", txtGiaTri);
	        return false;
	    } else {
	        try {
	            giaTri = Float.parseFloat(giaTriStr);
	            if (giaTri <= 0) {
	                home.showMessage("Lỗi: Giá trị phải lớn hơn 0", txtGiaTri);
	                return false;
	            }
	        } catch (NumberFormatException e) {
	            home.showMessage("Lỗi: Giá trị phải là số hợp lệ", txtGiaTri);
	            return false;
	        }
	    }
	    
	    
	    Date ngayBatDau = FormatDate.convertFromJAVADateToSQLDate(ngaybatdau.getDate());
	    Date ngayKetThuc = FormatDate.convertFromJAVADateToSQLDate(ngayketthuc.getDate());
		Date ngayHienTai = Date.valueOf(java.time.LocalDate.now());
		
		if (ngaybatdau == null) {
		    JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được để trống!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		    return false;
		} else if (ngayBatDau.before(ngayHienTai)) {
		    JOptionPane.showMessageDialog(null, "Ngày bắt đầukhông được trước ngày hiện tại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		    return false;
		}

		
		if (ngayketthuc == null) {
		    JOptionPane.showMessageDialog(null, "Ngày kết thúc không được để trống!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		    return false;
		} else if (ngayKetThuc.before(ngayHienTai)) {
		    JOptionPane.showMessageDialog(null, "Ngày kết thúc không được trước ngày hiện tại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		    
		    return false;
		}

	    if (soLuongStr == null || soLuongStr.trim().isEmpty()) {
	        home.showMessage("Lỗi: Số lượng không được để trống", txtSoLuong);
	        return false;
	    } else {
	        try {
	            soLuong = Integer.parseInt(soLuongStr);
	            if (soLuong <= 0) {
	                home.showMessage("Lỗi: Số lượng phải lớn hơn 0", txtSoLuong);
	                return false;
	            }
	        } catch (NumberFormatException e) {
	            home.showMessage("Lỗi: Số lượng phải là số nguyên hợp lệ", txtSoLuong);
	            return false;
	        }
	    }

	    return true;
	}

    private KhuyenMai dataUuDai() {
        // Retrieve values from input fields
    	String maKM = txtMa.getText();
        String tenKM = txtTen.getText();
        String giaTriStr = txtGiaTri.getText();
        String moTa = txtMoTa.getText();
        String soLuongStr = txtSoLuong.getText();
        
        Date ngayBD = home.convertFromJAVADateToSQLDate(ngaybatdau.getDate());
//        Date ngayKT = new Date(ngayketthuc.getDate().getTime());
        Date ngayKT =home.convertFromJAVADateToSQLDate(ngayketthuc.getDate());
        
        
        
        float giaTri = 0.0f;
        if (giaTriStr != null && !giaTriStr.trim().isEmpty()) {
            try {
                giaTri = Float.parseFloat(giaTriStr);

                if (giaTri < 1 || giaTri > 100) {
                    home.showMessage("Giá trị phải nằm trong khoảng từ 1% đến 100%", txtGiaTri);
                    return null; 
                }
                
            } catch (NumberFormatException e) {
                home.showMessage("Phải nhập số", txtGiaTri);
                return null; 
            }
        }
        if (ngayketthuc.getDate() == null) {
            home.showMessage("Vui lòng nhập ngày kết thúc", ERROR);
            return null; // Dừng lại nếu không hợp lệ
        } else {
            ngayKT = new Date(ngayketthuc.getDate().getTime());
        }
         int soluong = 0; 
         if (soLuongStr != null && !soLuongStr.trim().isEmpty()) {
             try {
            	 soluong = Integer.parseInt(soLuongStr);
             } catch (NumberFormatException e) {
              home.showMessage("Phải nhập số", txtSoLuong);
             }
         } 
     

        KhuyenMai ud = new KhuyenMai(maKM, tenKM, giaTri , ngayBD, ngayKT, moTa, 0 , null ,null );
        return ud;
    }
    

}