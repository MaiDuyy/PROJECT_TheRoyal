package gui.ui;

import java.awt.Color;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;

import com.toedter.calendar.JDateChooser;

import controller.TimKhuyenMai;
import gui.format_ui.Table;
import entity.DichVu;
import entity.KhuyenMai;
import entity.NhanVien;
import entity.TaiKhoan;
import gui.component.ButtonCustom;
import gui.dialog.SuaKhuyenMai_Dialog;
import gui.dialog.SuaSanPham_Dialog;
import gui.dialog.ThemDichVu_Dialog;
import gui.dialog.ThemKhuyenMai_Dialog;
import gui.dialog.ThemSanPham_Dialog;
import gui.format_ui.Table;
import gui.swing.notification.Notification;
import rmi.RMIClient;
import service.KhuyenMaiService;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;


public class QLUuDai_GUI extends JInternalFrame implements ActionListener {

    private KhuyenMaiService khuyenMaiService;
    private final JLabel tieuDeLabel;
    private final JSeparator phanCach;
    private Table tblUuDai;
    public DefaultTableModel tableModelUD;
    private static final long serialVersionUID = 1L;
    private ArrayList<KhuyenMai> dsKM;
    private JComboBox<String> cbxLuachon;
    private DefaultComboBoxModel<String> modelLuaChon;
    private JTextField txtTen, txtMa, txtGiaTri, txtMoTa, txtSoLuong, txtTim;
    private JDateChooser ngaybatdau, ngayketthuc;
    private JButton btnThem, btnTim, btnXoa, btnCapNhat;
    private final JLabel lbShowMessages;
    private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
    private final ThemKhuyenMai_Dialog themkhuyenmaidialog;
    private JButton btnLocNgayKetThuc;
    private ButtonCustom btnHuyTim;


    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public QLUuDai_GUI() {
        khuyenMaiService = RMIClient.getInstance().getKhuyenMaiService();
        getContentPane().setBackground(new Color(255, 255, 255));
        themkhuyenmaidialog = new ThemKhuyenMai_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Nhãn tiêu đề
        tieuDeLabel = new JLabel("Quản lý Khách Hàng");
        tieuDeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tieuDeLabel.setForeground(new Color(246, 167, 193));
        tieuDeLabel.setBounds(29, 10, 300, 30);
        getContentPane().add(tieuDeLabel);

        // Phân cách
        phanCach = new JSeparator();
        phanCach.setBounds(0, 50, screenSize.width, 10);
        getContentPane().add(phanCach);

        lbShowMessages = new JLabel("");
        lbShowMessages.setBounds(1051, 10, 295, 37);
        getContentPane().add(lbShowMessages);
        GUI();
        ChucNang();
        setBackground(Color.white);
        setBounds(100, 100, 1343, 850);
        //			   setSize(1273,760);
        getContentPane().setLayout(null);
        setVisible(true);
        setClosable(true);

        // Cài đặt cho Internal Frame
        setBorder(null); // Xóa viền
        setTitle(""); // Đặt title là chuỗi rỗng

        // Loại bỏ thanh tiêu đề
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        setClosable(false);
        setIconifiable(false);
        setResizable(false);
        setMaximizable(false);
        loadListUuDai();
        DocDuLieuNVVaoTable();


    }

    private void GUI() {

        JPanel tabelPanel = new JPanel();
        tabelPanel.setBounds(41, 160, 1257, 509);
        tabelPanel.setBackground(Color.white);
        tabelPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
                "Thông tin khuyến mãi",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.PLAIN, 12),
                new Color(246, 167, 193)
        ));
        String[] columns = {"Mã KM", "Tên KM", "Giá Trị KM", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Mô Tả KM", "Số Lượng"};
        Object[][] data = {};
        tableModelUD = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPanel.setLayout(null);

        tblUuDai = new Table();
        tblUuDai.setModel(tableModelUD);
        tblUuDai.setFillsViewportHeight(true);
        tblUuDai.setBackground(new Color(255, 255, 255));
        tblUuDai.setBackground(Color.white);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(tblUuDai);
        scrollPane.setBounds(10, 23, 1237, 476);
        tabelPanel.add(scrollPane);
        getContentPane().add(tabelPanel);

    }

    private void ChucNang() {

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(616, 51, 682, 96);
        searchPanel.setBackground(Color.white);
        searchPanel
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
                        "Tìm kiếm", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

        modelLuaChon = new DefaultComboBoxModel<String>(new String[]{
                "Tất cả",
                "Tên khuyến mãi",
                "Mã khuyến mãi",
                "Ngày bắt đầu",
                "Ngày kết thúc"
        });
        cbxLuachon = new JComboBox<String>(modelLuaChon);
        cbxLuachon.setBounds(10, 34, 175, 29);
        searchPanel.add(cbxLuachon);
        txtTim = new JTextField(15);
        txtTim.setBounds(195, 34, 290, 29);

        btnTim = new JButton("Tìm Kiếm");
        btnTim.setBounds(376, 34, 107, 29);
        btnTim.setIcon(new ImageIcon("icon/search_16.png"));
        btnHuyTim = new ButtonCustom("Xem tất cả", "rest", 12);
        btnHuyTim.setBounds(493, 34, 112, 29);
        btnHuyTim.setIcon(new ImageIcon("icon/refresh.png"));
        searchPanel.setLayout(null);
        searchPanel.add(txtTim);
//	        searchPanel.add(btnTim);
        searchPanel.add(btnHuyTim);
        getContentPane().add(searchPanel);

        // Panel điều khiển
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(39, 51, 527, 96);
        controlPanel.setBackground(Color.white);
        controlPanel
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
                        "Chức năng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

        btnThem = new JButton("Thêm",
                new ImageIcon("icon/blueAdd_16.png"));
        btnThem.setBackground(new Color(255, 255, 255));
        btnThem.setBounds(37, 25, 67, 63);
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.setFocusable(false);
        btnThem.setHorizontalTextPosition(SwingConstants.CENTER);
        btnThem.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnCapNhat = new JButton("Sửa");
        btnCapNhat.setBackground(new Color(255, 255, 255));
        btnCapNhat.setBounds(191, 25, 67, 63);
        btnCapNhat.setIcon(new ImageIcon("icon/updated.png"));
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat.setFocusable(false);
        btnCapNhat.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCapNhat.setVerticalTextPosition(SwingConstants.BOTTOM);

        controlPanel.setLayout(null);

        controlPanel.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(255, 255, 255));
        btnXoa.setBounds(114, 25, 67, 63);
        btnXoa.setIcon(new ImageIcon("icon/trash2_16.png"));
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.setFocusable(false);
        btnXoa.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXoa.setVerticalTextPosition(SwingConstants.BOTTOM);
        controlPanel.add(btnXoa);
        controlPanel.add(btnCapNhat);
        getContentPane().add(controlPanel);

        btnLocNgayKetThuc = new JButton("Lọc ngày kết thúc");
        btnLocNgayKetThuc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date ngayHT = Date.valueOf(LocalDate.now());
                tableModelUD.getDataVector().removeAllElements();
                tableModelUD.fireTableDataChanged();
                dsKM = (ArrayList<KhuyenMai>) TimKhuyenMai.getInstance().searchLocNgayKetThuc(ngayHT);
                DocDuLieuNVVaoTable();

            }
        });
        btnLocNgayKetThuc.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnLocNgayKetThuc.setIcon(new ImageIcon("icon/cancel-event.png"));
        btnLocNgayKetThuc.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLocNgayKetThuc.setFocusable(false);
        btnLocNgayKetThuc.setBackground(Color.WHITE);
        btnLocNgayKetThuc.setBounds(268, 25, 146, 63);
        controlPanel.add(btnLocNgayKetThuc);

        btnTim.addActionListener(this);
        btnHuyTim.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        txtTim.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String luachon = (String) cbxLuachon.getSelectedItem();
                String searchContent = txtTim.getText();

                tableModelUD.getDataVector().removeAllElements();
                tableModelUD.fireTableDataChanged();

                switch (luachon) {
                    case "Tất cả":
                        dsKM = (ArrayList<KhuyenMai>) TimKhuyenMai.getInstance().searchTatCa(searchContent);
                        break;
                    case "Tên":
                        dsKM = (ArrayList<KhuyenMai>) TimKhuyenMai.getInstance().searchTen(searchContent);
                        break;
                    case "Mã sản phẩm":
                        dsKM = (ArrayList<KhuyenMai>) TimKhuyenMai.getInstance().searhMa(searchContent);
                        break;
                    case "Ngày bắt đầu":
                        dsKM = (ArrayList<KhuyenMai>) TimKhuyenMai.getInstance().searhNgayBatDau(searchContent);
                        break;
                    case "Ngày kết thúc":
                        dsKM = (ArrayList<KhuyenMai>) TimKhuyenMai.getInstance().searchNgayKetThuc(searchContent);
                        break;
                }

                if (dsKM == null || dsKM.isEmpty()) {
                    thongBao(2, "Không tìm thấy kết quả");
                } else {
                    DocDuLieuNVVaoTable();
                }
            }
        });

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(btnThem)) {
            ThemKhuyenMai_Dialog a;
            a = new ThemKhuyenMai_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);

            a.setVisible(true);
        } else if (e.getSource().equals(btnXoa)) {


            showMessage("", 2);
            int row = tblUuDai.getSelectedRow();

            if (row == -1) {
                thongBao(2, "Bạn cần chọn dòng cần xóa");
                showMessage("Lỗi: Bạn cần chọn dòng cần xóa", ERROR);
                return;
            }
            try {
                KhuyenMai km = getKhuyenMaiSelect();
                String tenKH = km.getTenKM();
                int confirm = JOptionPane.showConfirmDialog(this,
                        "<html><p style='text-align: center; font-size: 18px; color:red'>Cảnh báo</p>" +
                                "<p style='text-align: center;'>Xóa khách hàng <span style='color: blue'>" + tenKH + "</span>?</p>" +
                                "<p style='text-align: center;'>Hãy suy nghĩ thật kỹ trước khi quyết định.</p></html>",
                        "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    khuyenMaiService.delete(km.getMaKM());
                    ((DefaultTableModel) tblUuDai.getModel()).removeRow(row);
                    loadListUuDai();
                    thongBao(0, "Xóa Thành công");
                    showMessage("Xóa thành công", SUCCESS);
                }
            } catch (IllegalArgumentException ex) {
                thongBao(2, "Không thể xóa! Không có khách hàng nào được chọn");
            } catch (Exception ex) {
                ex.printStackTrace();
                thongBao(2, "Xóa thất bại");
            }


        }


        if (e.getSource().equals(btnHuyTim)) {
            tableModelUD.getDataVector().removeAllElements();
            loadListUuDai();
            DocDuLieuNVVaoTable();

        }
        if (e.getSource().equals(btnCapNhat)) {
            showMessage("", 2);

            if (tblUuDai.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần chỉnh sửa !");
            } else {

                SuaKhuyenMai_Dialog u = new SuaKhuyenMai_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
                u.setVisible(true);
            }
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
            showMessage("Lỗi: Tên không được để trống", txtTen);
            return false;
        }
        if (!tenNV.matches("^[^0-9]+$")) {
            showMessage("Lỗi: Tên không được có số", txtTen);
            return false;
        }

        if (giaTriStr == null || giaTriStr.trim().isEmpty()) {
            showMessage("Lỗi: Giá trị không được để trống", txtGiaTri);
            return false;
        } else {
            try {
                giaTri = Float.parseFloat(giaTriStr);
                if (giaTri <= 0) {
                    showMessage("Lỗi: Giá trị phải lớn hơn 0", txtGiaTri);
                    return false;
                }
            } catch (NumberFormatException e) {
                showMessage("Lỗi: Giá trị phải là số hợp lệ", txtGiaTri);
                return false;
            }
        }


        if (soLuongStr == null || soLuongStr.trim().isEmpty()) {
            showMessage("Lỗi: Số lượng không được để trống", txtSoLuong);
            return false;
        } else {
            try {
                soLuong = Integer.parseInt(soLuongStr);
                if (soLuong <= 0) {
                    showMessage("Lỗi: Số lượng phải lớn hơn 0", txtSoLuong);
                    return false;
                }
            } catch (NumberFormatException e) {
                showMessage("Lỗi: Số lượng phải là số nguyên hợp lệ", txtSoLuong);
                return false;
            }
        }

        return true;
    }


    public String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public void DocDuLieuNVVaoTable() {
        if (dsKM == null || dsKM.size() <= 0)
            return;
        for (KhuyenMai item : dsKM) {
            String ngayBatDau = formatDate(item.getThoiGianBatDau());
            String ngayKetThuc = formatDate(item.getThoiGianKetThuc());
            float giaTri = item.getGiaTriKhuyenMai();
            String giaTriPhanTram = String.format("%.0f%%", giaTri);
            tableModelUD.addRow(new Object[]{
                    item.getMaKM(), item.getTenKM(), giaTriPhanTram, ngayBatDau, ngayKetThuc, item.getMoTaKM(), item.getSoLuong()
            });

        }
    }

    public void loadListUuDai() {
        try {
            dsKM = (ArrayList<KhuyenMai>) khuyenMaiService.getAll();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private KhuyenMai dataUuDai() {
        // Retrieve values from input fields
        String maKM = txtMa.getText();
        String tenKM = txtTen.getText();
        String giaTriStr = txtGiaTri.getText();
        String moTa = txtMoTa.getText();
        String soLuongStr = txtSoLuong.getText();

        Date ngayBD = new Date(ngaybatdau.getDate().getTime());
//        Date ngayKT = new Date(ngayketthuc.getDate().getTime());
        Date ngayKT = null;


        float giaTri = 0.0f;
        if (giaTriStr != null && !giaTriStr.trim().isEmpty()) {
            try {
                giaTri = Float.parseFloat(giaTriStr);

                if (giaTri < 1 || giaTri > 100) {
                    showMessage("Giá trị phải nằm trong khoảng từ 1% đến 100%", txtGiaTri);
                    return null;
                }

            } catch (NumberFormatException e) {
                showMessage("Phải nhập số", txtGiaTri);
                return null;
            }
        }
        if (ngayketthuc.getDate() == null) {
            showMessage("Vui lòng nhập ngày kết thúc", ERROR);
            return null; // Dừng lại nếu không hợp lệ
        } else {
            ngayKT = new Date(ngayketthuc.getDate().getTime());
        }
        int soluong = 0;
        if (soLuongStr != null && !soLuongStr.trim().isEmpty()) {
            try {
                soluong = Integer.parseInt(soLuongStr);
            } catch (NumberFormatException e) {
                showMessage("Phải nhập số", txtSoLuong);
            }
        }


        KhuyenMai ud = new KhuyenMai(maKM, tenKM, giaTri, ngayBD, ngayKT, moTa, soluong, null,null);
        return ud;
    }

    public static Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }

    private boolean validDataTim() {
        String maNV = txtTim.getText().trim();
        if (!(maNV.length() > 0)) {
            showMessage("Lỗi: Mã không được để trống", txtTim);
            return false;
        }
        return true;
    }

    public void showMessage(String message, JTextField txt) {
        showMessage(message, ERROR);
        txt.requestFocus();
        txt.selectAll();

    }

    public void showMessage(String message, int type) {
        if (type == SUCCESS) {
            lbShowMessages.setForeground(Color.GREEN);
            //	            lbShowMessages.setIcon(checkIcon);
        } else if (type == ERROR) {
            lbShowMessages.setForeground(Color.RED);
            //	            lbShowMessages.setIcon(errorIcon);
        } else {
            lbShowMessages.setForeground(Color.BLACK);
            lbShowMessages.setIcon(null);
        }
        lbShowMessages.setText(message);
    }

    public void thongBao(int type, String txt) {
        Notification noti = new Notification(
                (java.awt.Frame) SwingUtilities.getWindowAncestor(this),
                type,
                Notification.Location.TOP_RIGHT,
                txt
        );

        noti.showNotification();
    }

    public void thongBao(int type, String text, JTextField txt) {
        Notification noti = new Notification(
                (java.awt.Frame) SwingUtilities.getWindowAncestor(this),
                type,
                Notification.Location.TOP_RIGHT,
                text
        );
        txt.requestFocus();
        txt.selectAll();
        noti.showNotification();
    }

    public void huytim() {
        tableModelUD.getDataVector().removeAllElements();
        loadListUuDai();
        DocDuLieuNVVaoTable();

    }

    public KhuyenMai getKhuyenMaiSelect() {
        int i_row = tblUuDai.getSelectedRow();
        if (i_row == -1) {
            throw new IllegalArgumentException("Không chọn được dòng nào");
        }
        String maKM = tblUuDai.getValueAt(i_row, 0).toString();
        try {
            return khuyenMaiService.getKhuyenMaiTheoMa(maKM);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
