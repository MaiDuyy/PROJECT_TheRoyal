package gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import gui.ui.QLKhachHang_GUI;
import gui.ui.QLNhanVien_GUI;
import gui.ui.QLPhong_GUI;
import gui.ui.QLUuDai_GUI;
import dao.KhachHangDAO;
import dao.KhuyenMaiDAO;
import dao.LoaiPhongDAO;
import dao.PhongDAO;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.Phong;
import gui.component.ButtonCustom;
import gui.component.HeaderTitle;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.LoaiPhongService;
import service.PhongService;

public class ThemPhong_Dialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtTenP, txtMaP, txtgiaTien, txtTrangThai, txtTim;
	private SpinnerNumberModel modelSpinNGLon, modelSpinTreE, modelSoGiuong;
	private JSpinner spinSoTrem, spinSoNguoiLon, spinSoGiuong;
	private JComboBox<String> cboMaLoaiP, cboTrangThai;
	private JComboBox < String > cbxLuachon;
    private ArrayList < Phong > dsPhong;
    private ArrayList < LoaiPhong > dsLoaiPhong;
    private DefaultComboBoxModel<String> modelMaPhong;
    private ButtonCustom btnThem, btnDong;
    private JLabel lbShowMessages;
	private String loaiPhong;
    private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
    private QLPhong_GUI home;

	private PhongService phongService = RMIClient.getInstance().getPhongService();

	private LoaiPhongService loaiPhongService = RMIClient.getInstance().getLoaiPhongService();
    @SneakyThrows
	public ThemPhong_Dialog(javax.swing.JInternalFrame parent, javax.swing.JFrame owner, boolean modal) {
        super(owner, modal);
        GUI();
        setLocationRelativeTo(null);
        home = (QLPhong_GUI) parent;
        dsPhong = (ArrayList<Phong>) phongService.getAll();
        dsLoaiPhong = (ArrayList<LoaiPhong>) loaiPhongService.getAll();
        loadCboMaLoaiPhong();

    }

    public void GUI() {
        setSize(614, 570);
        setTitle("Thêm phòng");
        HeaderTitle title = new HeaderTitle("THÊM PHÒNG");
		getContentPane().add(title, BorderLayout.NORTH);
     // Panel thông tin nhân viên
    	JPanel staffInfoPanel = new JPanel();
    	staffInfoPanel.setBackground(Color.white);
    	staffInfoPanel.setBounds(40, 60, 567, 422);
    	staffInfoPanel
    			.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
    					"Thông tin phòng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
    					new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

    	JLabel lblMaNV = new JLabel("Mã phòng:");
    	lblMaNV.setBounds(5, 23, 278, 38);
    	txtMaP = new JTextField(15);
    	txtMaP.setEditable(false);
    	txtMaP.setBounds(283, 23, 278, 38);

    	JLabel lblSDT = new JLabel("Số giường:");
    	lblSDT.setBounds(5, 167, 278, 38);
    	// txtSoGiuong = new JTextField(15);
    	modelSoGiuong = new SpinnerNumberModel(1, 1, null, 1);
    	spinSoGiuong = new JSpinner(modelSoGiuong);
    	spinSoGiuong.setBounds(283, 167, 278, 38);
    	staffInfoPanel.add(spinSoGiuong);

    	staffInfoPanel.setLayout(null);

    	staffInfoPanel.add(lblMaNV);
    	staffInfoPanel.add(txtMaP);

    	JLabel lblTen = new JLabel("Tên:");
    	lblTen.setBounds(5, 71, 278, 38);
    	staffInfoPanel.add(lblTen);
    	txtTenP = new JTextField(15);
    	txtTenP.setBounds(283, 71, 278, 38);
    	staffInfoPanel.add(txtTenP);

    	JLabel lblLoaiPhong = new JLabel("Loại phòng:");
    	lblLoaiPhong.setBounds(5, 119, 278, 38);
    	staffInfoPanel.add(lblLoaiPhong);
    	modelMaPhong = new DefaultComboBoxModel<String>();
    	cboMaLoaiP = new JComboBox<String>(modelMaPhong);
    	cboMaLoaiP.setBounds(283, 119, 278, 38);
    	staffInfoPanel.add(cboMaLoaiP);
    	staffInfoPanel.add(lblSDT);

    	JLabel lblGiaTien = new JLabel("Giá tiền:");
    	lblGiaTien.setBounds(5, 215, 278, 38);
    	staffInfoPanel.add(lblGiaTien);
    	txtgiaTien = new JTextField(15);
    	txtgiaTien.setBounds(283, 215, 278, 38);
    	staffInfoPanel.add(txtgiaTien);

    	JLabel lblSoTreEm = new JLabel("Số trẻ em:");
    	lblSoTreEm.setBounds(5, 311, 278, 38);
    	staffInfoPanel.add(lblSoTreEm);
    	modelSpinTreE = new SpinnerNumberModel(1, 1, null, 1);
    	spinSoTrem = new JSpinner(modelSpinTreE);
    	// txtsoTreEm = new JTextField(15);
    	spinSoTrem.setBounds(283, 311, 278, 38);
    	staffInfoPanel.add(spinSoTrem);

    	JLabel lblNgaySinh = new JLabel("Số người lớn:");
    	lblNgaySinh.setBounds(5, 263, 278, 38);
    	staffInfoPanel.add(lblNgaySinh);

    	modelSpinNGLon = new SpinnerNumberModel(1, 1, null, 1);
    	spinSoNguoiLon = new JSpinner(modelSpinNGLon);
    	// txtsoNguoiLon = new JTextField(15);
    	spinSoNguoiLon.setBounds(283, 263, 278, 38);
    	staffInfoPanel.add(spinSoNguoiLon);

    	JLabel lblTrangThai = new JLabel("Trạng thái:");
    	lblTrangThai.setBounds(5, 359, 278, 38);
//        txtTrangThai = new JTextField(15);
//        txtTrangThai.setBounds(283, 359, 278, 38);
    	cboTrangThai = new JComboBox<>(new String[] { "Phòng trống", "Đã đặt", "Đang ở", "Đang dọn dẹp"

    	});
    	cboTrangThai.setBounds(283, 359, 278, 38);
    	staffInfoPanel.add(lblTrangThai);
    	staffInfoPanel.add(cboTrangThai);

    	getContentPane().add(staffInfoPanel);
        
        lbShowMessages = new JLabel("");
        lbShowMessages.setBounds(143, 385, 295, 22);
        staffInfoPanel.add(lbShowMessages);
        

        btnThem = new ButtonCustom("Thêm","success", 12);
        btnThem.setLocation(164, 425);
        btnThem.setSize(100, 29);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNV();
            }
        });
        btnDong =  new ButtonCustom("Hủy","danger", 12);
        btnDong.setLocation(304, 425);
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
    private void themNV() {
    	home.showMessage("", 2);
		Phong p = null;
		LoaiPhong lp = null;
		p = dataPhong();
		if (validData(ADD)) {
			try {

				boolean result = phongService.save(p);
				String maPhong = phongService.getLatestID();
				// txtMaP.setText(String.valueOf(maPhong));
				if (result == true) {

					Double gia = p.getGiaTien();
					DecimalFormat df = new DecimalFormat("#,###.##");
					String donGia = df.format(gia);
					
					 
				            home.tableModelPhong.addRow(new Object[] { maPhong, p.getTenPhong(), p.getLoaiPhong().getMaLoai(),
									p.getSoGiuong(), donGia, p.getSoNguoiLon(), p.getSoTreEm(), p.getTrangThai() });
				  
					

					home.showMessage("Thêm phòng thành công", SUCCESS);
					dispose();
					home.tableModelPhong.fireTableDataChanged();
					home.huytim();
				
				} else {
					home.showMessage("Lỗi: Thêm phòng thất bại", ERROR);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
    }
	 
	private Phong dataPhong() {
		String maP = txtMaP.getText().trim();
		String tenP = txtTenP.getText().trim();
		String loaiP = cboMaLoaiP.getSelectedItem().toString().trim();
		int sogiuong = Integer.parseInt(spinSoGiuong.getValue().toString());
		String gia = txtgiaTien.getText().trim().replace(",", "");
//        Double donGia = Double.parseDouble(gia);

		double giaTri = 0.00f;

		if (gia != null && !gia.trim().isEmpty()) {
			try {
				giaTri = Double.parseDouble(gia);
			} catch (NumberFormatException e) {
				home.showMessage("Phải nhập số", txtgiaTien);
			}
		}
		int songlon = Integer.parseInt(spinSoNguoiLon.getValue().toString());
		int sotreem = Integer.parseInt(spinSoTrem.getValue().toString());
//        String trangthai = txtTrangThai.getText().trim();
		String trangthai = cboTrangThai.getSelectedItem().toString().trim();
		Phong phong = new Phong(maP, tenP, new LoaiPhong(loaiP), sogiuong, giaTri, songlon, sotreem, trangthai ,null ,null);
		return phong;
	}
	private boolean validData(int type) {
		String tenP = txtTenP.getText().trim();
		String donGia = txtgiaTien.getText().trim().replace(",", "");

		double giaTri = 0.0f;
		if (!(tenP.length() > 0)) {
			home.showMessage("Lỗi: Tên không được để trống", txtTenP);
			return false;
		}

		if (donGia == null || donGia.trim().isEmpty()) {
			home.showMessage("Lỗi: Giá trị không được để trống", txtgiaTien);
			return false;
		} else {
			try {
				giaTri = Float.parseFloat(donGia);
				if (giaTri <= 0) {
					home.showMessage("Lỗi: Giá trị phải lớn hơn 0", txtgiaTien);
					return false;
				}
			} catch (NumberFormatException e) {
				home.showMessage("Lỗi: Giá trị phải là số hợp lệ", txtgiaTien);
				return false;
			}
		}
		return true;
	}
	  @SneakyThrows
	  public void loadCboMaLoaiPhong() {
			dsLoaiPhong = (ArrayList<LoaiPhong>) loaiPhongService.getAll();
			if (dsLoaiPhong == null || dsLoaiPhong.size() <= 0)
				return;
			for (LoaiPhong item : dsLoaiPhong) {
//				setLoaiPhong(item);
				
				cboMaLoaiP.addItem(item.getMaLoai());
			}
		}
	  
	  private void setLoaiPhong(LoaiPhong ph) {
	        if (ph.getMaLoai().equals("LP01")) {
	            loaiPhong = "Phòng Đơn";
	        }else if (ph.getMaLoai().equals("LP02")) {
	            loaiPhong = "Phòng Đôi";
	        }else if (ph.getMaLoai().equals("LP03")) {
	            loaiPhong = "Phòng Penthouse";
	        }
	    }
	  
	
	  
	  
	 

}