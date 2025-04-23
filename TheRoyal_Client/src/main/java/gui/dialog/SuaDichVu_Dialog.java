package gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
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

import gui.ui.QLKhachHang_GUI;
import gui.ui.QLNhanVien_GUI;
import gui.ui.QLDichVu_GUI;
import dao.KhachHangDAO;
import dao.DichVuDAO;
import entity.KhachHang;
import gui.component.ButtonCustom;
import gui.component.HeaderTitle;
import entity.DichVu;
import lombok.SneakyThrows;
import rmi.RMIClient;

public class SuaDichVu_Dialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtDonGia;
    private JTextField txtMa, txtTen;
    private JTextField txtMoTa;
    private JTextField txtSoLuong;
    private ArrayList < DichVu > dsDV;
    private ButtonCustom btnThem, btnDong;
    private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
    private QLDichVu_GUI home;
    HeaderTitle title;



    @SneakyThrows
    public SuaDichVu_Dialog(javax.swing.JInternalFrame parent, javax.swing.JFrame owner, boolean modal) {
        super(owner, modal);
        GUI();
        setLocationRelativeTo(null);
        home = (QLDichVu_GUI) parent;
        dsDV = (ArrayList<DichVu>) RMIClient.getInstance().getDichVuService().getAll();
        DichVu sp = home.getSanPhanSelect();
        txtMa.setText(sp.getMaDV());
        txtTen.setText(sp.getTenDV());
        txtMoTa.setText(sp.getMoTa());
        double dongia = sp.getGiaDV();
        txtDonGia.setText(Double.toString(dongia).replace(".0", ""));
        int soluong = sp.getSoLuongDV();
        txtSoLuong.setText(Integer.toString(soluong));

    }

    public void GUI() {
        setSize(614, 423);
       title = new HeaderTitle("SỬA DỊCH VỤ"); 
       getContentPane().add(title,BorderLayout.NORTH);
        JPanel staffInfoPanel = new JPanel();
        staffInfoPanel.setBackground(Color.white);
        staffInfoPanel.setBounds(55, 50, 552, 491);
        staffInfoPanel
            .setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
                "Thông tin sản phẩm", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
        staffInfoPanel.setLayout(null);

        JLabel lblMaSP = new JLabel("Mã dịch vụ:");
        lblMaSP.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMaSP.setBounds(10, 45, 108, 30);
        staffInfoPanel.add(lblMaSP);

        txtMa = new JTextField();
        txtMa.setBounds(244, 47, 331, 29);
        txtMa.setEditable(false);
        staffInfoPanel.add(txtMa);
        txtMa.setColumns(10);

        JLabel lblTenSP = new JLabel("Tên dịch vụ:");
        lblTenSP.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTenSP.setBounds(10, 84, 108, 30);
        staffInfoPanel.add(lblTenSP);

        txtTen = new JTextField();
        txtTen.setBounds(244, 86, 331, 30);
        staffInfoPanel.add(txtTen);
        txtTen.setColumns(10);

        JLabel lblDonGia = new JLabel("Đơn giá:");
        lblDonGia.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblDonGia.setBounds(10, 124, 108, 30);
        staffInfoPanel.add(lblDonGia);

        txtDonGia = new JTextField();
        txtDonGia.setBounds(244, 126, 331, 30);
        staffInfoPanel.add(txtDonGia);
        txtDonGia.setColumns(10);

        JLabel lblMota = new JLabel("Mô tả:");
        lblMota.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMota.setBounds(10, 201, 101, 30);
        staffInfoPanel.add(lblMota);

        txtMoTa = new JTextField();
        txtMoTa.setColumns(10);
        txtMoTa.setBounds(244, 203, 331, 30);
        staffInfoPanel.add(txtMoTa);

        JLabel lblSoluong = new JLabel("Số lượng:");
        lblSoluong.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblSoluong.setBounds(10, 164, 101, 30);
        staffInfoPanel.add(lblSoluong);

        txtSoLuong = new JTextField();
        txtSoLuong.setColumns(10);
        txtSoLuong.setBounds(244, 163, 331, 30);
        staffInfoPanel.add(txtSoLuong);

        getContentPane().add(staffInfoPanel);

        btnThem = new ButtonCustom("Cập nhật","success", 12);
        btnThem.setLocation(165, 263);
        btnThem.setSize(100, 29);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNV();
            }
        });
        btnDong =  new ButtonCustom("Hủy","danger", 12);
        btnDong.setLocation(305, 263);
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
        DichVu nv = dataDichVu();
        RMIClient.getInstance().getDichVuService().update(nv);
        this.dispose();
        home.huytim();
        home.thongBao(0, "Cập nhật thành công");
    }
    }
    private boolean validData(int type) {
        // type = 1 -> thêm mới
        // type != 1 -> cập nhật

        String donGia = txtDonGia.getText().trim().replace(",", "");
        String soLuongStr = txtSoLuong.getText();
        //		     giaTri = 0.0f;
        int soLuong = 0;
        double giaTri = 0.0f;

        String tenNV = txtTen.getText().trim();
        if (tenNV.isEmpty()) {
            home.showMessage("Lỗi: Tên không được để trống", txtTen);
            return false;
        }
        if (!tenNV.matches("^[^0-9]+$")) {
            home.showMessage("Lỗi: Tên không được có số", txtTen);
            return false;
        }

        if (donGia == null || donGia.trim().isEmpty()) {
            home.showMessage("Lỗi: Giá trị không được để trống", txtDonGia);
            return false;
        } else {
            try {
                giaTri = Double.parseDouble(donGia);
                if (giaTri <= 0) {
                    home.showMessage("Lỗi: Giá trị phải lớn hơn 0", txtDonGia);
                    return false;
                }
            } catch (NumberFormatException e) {
                home.showMessage("Lỗi: Giá trị phải là số hợp lệ", txtDonGia);
                return false;
            }
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

    private DichVu dataDichVu() {
        String maDV = txtMa.getText().trim();
        String tenDV = txtTen.getText().trim();
        String moTaDV = txtMoTa.getText().trim();
        String DonGiaStr = txtDonGia.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();

        double giaTri = 0.00f;

        if (DonGiaStr != null && !DonGiaStr.trim().isEmpty()) {
            try {
                giaTri = Double.parseDouble(DonGiaStr);
            } catch (NumberFormatException e) {
                home.showMessage("Phải nhập số", txtDonGia);
            }
        }
        int soLuong = 0;

        if (soLuongStr != null && !soLuongStr.trim().isEmpty()) {
            try {
                soLuong = Integer.parseInt(soLuongStr);
            } catch (NumberFormatException e) {
                home.showMessage("Phải nhập số", txtSoLuong);
            }
        }

        DichVu dv = new DichVu(maDV, tenDV, moTaDV, giaTri, soLuong , null ,null);
        return dv;
    }

}