package gui.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;


import gui.format_ui.Table;

import controller.TimPhong;

import entity.*;
import gui.component.ButtonCustom;
import gui.dialog.SuaKhuyenMai_Dialog;
import gui.dialog.SuaPhong_Dialog;
import gui.dialog.ThemKhuyenMai_Dialog;
import gui.dialog.ThemPhong_Dialog;
import gui.swing.notification.Notification;
import rmi.RMIClient;
import service.LoaiPhongService;
import service.PhongService;

import java.awt.Color;
import java.awt.Dimension;

public class QLPhong_GUI extends JInternalFrame implements ActionListener {
    private final PhongService phongService;
    private LoaiPhongService loaiPhongService;
    private final JLabel tieuDeLabel;
    private final JSeparator phanCach;
    private Table tblPhong;
    public DefaultTableModel tableModelPhong;
    private JButton btnTim, btnThem, btnXoa, btnCapNhat, btnXemTatCa, btnLocNgayKetThuc;
    private JTextField txtTenP, txtgiaTien, txtTim;
    private final JLabel lbShowMessages;
    private JComboBox<String> cbxLuachon, cbxLoc;
    private DefaultComboBoxModel<String> modelLuaChon, modelLoc;
    private ButtonCustom btnHuyTim;
    private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
    public JFrame popup = new JFrame();
    private ArrayList<Phong> dsPhong;
    private ArrayList<LoaiPhong> dsLoaiPhong;
    private static final long serialVersionUID = 1;
    private final ThemPhong_Dialog themphongdialog;
    private String loaiPhong;

    public QLPhong_GUI() {
        phongService = RMIClient.getInstance().getPhongService();
        getContentPane().setBackground(new Color(255, 255, 255));
        themphongdialog = new ThemPhong_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Nhãn tiêu đề
        tieuDeLabel = new JLabel("Quản lý Phòng");
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
        setBounds(100, 100, 1364, 799);
        // setSize(1270, 710);
        // setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setVisible(true);
        setClosable(true);

        // Cài đặt cho Internal Frame
        setBorder(null); // Xóa viền
        setTitle(""); // Đặt title là chuỗi rỗng

        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        setClosable(false);
        setIconifiable(false);
        setResizable(false);
        setMaximizable(false);
        loadDataPhong();
        loadDataLoaiPhong();
        DocDuLieuPhongVaoTable();

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
        String[] columns = {"Mã phòng", "Tên phòng", "Mã loại", "Số giường", "Giá tiền", "Số người lớn", "Số trẻ em",
                "Trạng thái"};
        Object[][] data = {};
        tableModelPhong = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPanel.setLayout(null);
        tblPhong = new Table();
        tblPhong.setModel(tableModelPhong);
        tblPhong.setFillsViewportHeight(true);
        tblPhong.setBackground(new Color(255, 255, 255));
        tblPhong.setBackground(Color.white);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(tblPhong);
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
                "Tên phòng ",
                "Mã phòng",


        });
        cbxLuachon = new JComboBox<String>(modelLuaChon);
        cbxLuachon.setBounds(10, 34, 175, 29);
        searchPanel.add(cbxLuachon);


        txtTim = new JTextField(15);
        txtTim.setBounds(195, 34, 290, 29);

        btnTim = new JButton("Tìm Kiếm");
        btnTim.setBounds(376, 34, 107, 29);
        btnTim.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/src/ICON/icon/search_16.png")));
        btnHuyTim = new ButtonCustom("Xem tất cả", "rest", 12);
        btnHuyTim.setBounds(493, 34, 112, 29);
        btnHuyTim.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/src/ICON/icon/refresh.png")));
        searchPanel.setLayout(null);
        searchPanel.add(txtTim);
//	        searchPanel.add(btnTim);
        searchPanel.add(btnHuyTim);
        getContentPane().add(searchPanel);

        // Panel điều khiển
        JPanel controlPanel = new JPanel();

        modelLoc = new DefaultComboBoxModel<String>(new String[]{
                "--Lọc ở đây--",
                "Phòng đơn",
                "Phòng đôi",
                "Phòng Penthouse",
                "Phòng trống",
                "Đang ở",


        });
        cbxLoc = new JComboBox<String>(modelLoc);
        cbxLoc.setBounds(342, 25, 175, 29);
        controlPanel.add(cbxLoc);
        cbxLoc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tableModelPhong.getDataVector().removeAllElements();
                tableModelPhong.fireTableDataChanged();
                String selectedItem = (String) cbxLoc.getSelectedItem();
                switch (selectedItem) {
                    case "Phòng đơn":
                        dsPhong = (ArrayList<Phong>) TimPhong.getInstance().searchPhongVip(selectedItem);
                        break;
                    case "Phòng đôi":
                        dsPhong = (ArrayList<Phong>) TimPhong.getInstance().searchPhongVip(selectedItem);
                        break;
                    case "Phòng Penthouse":
                        dsPhong = (ArrayList<Phong>) TimPhong.getInstance().searchPhongVip(selectedItem);
                        break;
                    case "Phòng trống":
                        dsPhong = (ArrayList<Phong>) TimPhong.getInstance().searchTrangThai(selectedItem);
                        break;
                    case "Đang ở":
                        dsPhong = (ArrayList<Phong>) TimPhong.getInstance().searchTrangThai(selectedItem);
                        break;
                }
                if (dsPhong == null || dsPhong.isEmpty()) {
                    thongBao(2, "Không tìm thấy kết quả");
                } else {
                    DocDuLieuPhongVaoTable();
                }


            }
        });
        controlPanel.setBounds(39, 51, 527, 96);
        controlPanel.setBackground(Color.white);
        controlPanel
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
                        "Chức năng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

        btnThem = new JButton("Thêm",
                new ImageIcon(QLNhanVien_GUI.class.getResource("/src/ICON/icon/blueAdd_16.png")));
        btnThem.setBackground(new Color(255, 255, 255));
        btnThem.setBounds(37, 25, 67, 63);
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.setFocusable(false);
        btnThem.setHorizontalTextPosition(SwingConstants.CENTER);
        btnThem.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnCapNhat = new JButton("Sửa");
        btnCapNhat.setBackground(new Color(255, 255, 255));
        btnCapNhat.setBounds(191, 25, 67, 63);
        btnCapNhat.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/src/ICON/icon/updated.png")));
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat.setFocusable(false);
        btnCapNhat.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCapNhat.setVerticalTextPosition(SwingConstants.BOTTOM);

        controlPanel.setLayout(null);

        controlPanel.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(255, 255, 255));
        btnXoa.setBounds(114, 25, 67, 63);
        btnXoa.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/src/ICON/icon/trash2_16.png")));
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
                tableModelPhong.getDataVector().removeAllElements();
                tableModelPhong.fireTableDataChanged();
//	        		 dsPhong = TimPhong.getInstance().searchLocNgayKetThuc(ngayHT);
                DocDuLieuPhongVaoTable();

            }
        });
        btnLocNgayKetThuc.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnLocNgayKetThuc.setIcon(new ImageIcon(QLUuDai_GUI.class.getResource("/ICON/icon/cancel-event.png")));
        btnLocNgayKetThuc.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLocNgayKetThuc.setFocusable(false);
        btnLocNgayKetThuc.setBackground(Color.WHITE);
        btnLocNgayKetThuc.setBounds(268, 25, 146, 63);
//	        controlPanel.add(btnLocNgayKetThuc);

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
                tableModelPhong.getDataVector().removeAllElements();
                tableModelPhong.fireTableDataChanged();
                switch (luachon) {
                    case "Tất cả":
                        dsPhong = (ArrayList<Phong>) TimPhong.getInstance().searchTatCa(searchContent);
                        break;
                    case "Tên phòng":
                        dsPhong = (ArrayList<Phong>) TimPhong.getInstance().searchTen(searchContent);
                        break;
                    case "Mã phòng":
                        dsPhong = (ArrayList<Phong>) TimPhong.getInstance().searhMa(searchContent);
                        break;

                }

                if (dsPhong == null || dsPhong.isEmpty()) {
                    thongBao(2, "Không tìm thấy kết quả");
                } else {
                    DocDuLieuPhongVaoTable();
                }


            }
        });

    }


    public void loadDataPhong() {
        try {
            dsPhong = (ArrayList<Phong>) phongService.getAll();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDataLoaiPhong() {
        try {
            dsLoaiPhong = (ArrayList<LoaiPhong>) loaiPhongService.getAll();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void DocDuLieuPhongVaoTable() {
        if (dsPhong == null || dsPhong.size() <= 0)
            return;

        for (Phong item : dsPhong) {
            double number = item.getGiaTien();
            setLoaiPhong(item);
            // String COUNTRY = "VN";
            // String LANGUAGE = "vi";
            // String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE,
            // COUNTRY)).format(number);
            DecimalFormat df = new DecimalFormat("#,###.##");
            String donGia = df.format(number);
            tableModelPhong
                    .addRow(new Object[]{item.getMaPhong(), item.getTenPhong(), loaiPhong,
                            item.getSoGiuong(), donGia, item.getSoNguoiLon(), item.getSoTreEm(), item.getTrangThai()});
        }
    }

    private void setLoaiPhong(Phong ph) {
        if (ph.getLoaiPhong().getMaLoai().equals("LP01")) {
            loaiPhong = "Phòng Đơn";
        } else if (ph.getLoaiPhong().getMaLoai().equals("LP02")) {
            loaiPhong = "Phòng Đôi";
        } else if (ph.getLoaiPhong().getMaLoai().equals("LP03")) {
            loaiPhong = "Phòng Penthouse";
        }
    }

    public void showMessage(String message, JTextField txt) {
        showMessage(message, ERROR);
        txt.requestFocus();
        txt.selectAll();

    }

    public void showMessage(String message, int type) {
        if (type == SUCCESS) {
            lbShowMessages.setForeground(Color.GREEN);
            // lbShowMessages.setIcon(checkIcon);
        } else if (type == ERROR) {
            lbShowMessages.setForeground(Color.RED);
            // lbShowMessages.setIcon(errorIcon);
        } else {
            lbShowMessages.setForeground(Color.BLACK);
            lbShowMessages.setIcon(null);
        }
        lbShowMessages.setText(message);
    }


    // regex
    private boolean validDataTim() {
        String maNV = txtTim.getText().trim();
        if (!(maNV.length() > 0)) {
            showMessage("Lỗi: Mã không được để trống", txtTim);
            return false;
        }
        return true;
    }

    private boolean validData(int type) {
        String tenP = txtTenP.getText().trim();
        String donGia = txtgiaTien.getText().trim().replace(",", "");

        double giaTri = 0.0f;
        if (!(tenP.length() > 0)) {
            showMessage("Lỗi: Tên không được để trống", txtTenP);
            return false;
        }

        if (donGia == null || donGia.trim().isEmpty()) {
            showMessage("Lỗi: Giá trị không được để trống", txtgiaTien);
            return false;
        } else {
            try {
                giaTri = Float.parseFloat(donGia);
                if (giaTri <= 0) {
                    showMessage("Lỗi: Giá trị phải lớn hơn 0", txtgiaTien);
                    return false;
                }
            } catch (NumberFormatException e) {
                showMessage("Lỗi: Giá trị phải là số hợp lệ", txtgiaTien);
                return false;
            }
        }
        return true;
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
        tableModelPhong.getDataVector().removeAllElements();
        loadDataPhong();
        loadDataLoaiPhong();
        DocDuLieuPhongVaoTable();

    }

    public Phong getSanPhanSelect() {
        int i_row = tblPhong.getSelectedRow();
        if (i_row == -1) {
            throw new IllegalArgumentException("Không chọn được dòng nào");
        }
        String maP = tblPhong.getValueAt(i_row, 0).toString();
        try {
            return phongService.getPhongByMaPhong(maP);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(btnXemTatCa)) {
            tableModelPhong.getDataVector().removeAllElements();
            loadDataPhong();
            loadDataLoaiPhong();
            DocDuLieuPhongVaoTable();
        }

        if (e.getSource().equals(btnThem)) {
            ThemPhong_Dialog a;
            a = new ThemPhong_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);

            a.setVisible(true);

        }
        ///
        if (e.getSource().equals(btnCapNhat)) {
            showMessage("", 2);

            if (tblPhong.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần chỉnh sửa !");
            } else {

                SuaPhong_Dialog u = new SuaPhong_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
                u.setVisible(true);
            }
        }

        if (e.getSource().equals(btnXoa)) {
            showMessage("", 2);
            int row = tblPhong.getSelectedRow();
            try {
                if (row == -1) {
                    showMessage("Lỗi: Bạn cần chọn dòng cần xóa", ERROR);
                } else {
                    int select = JOptionPane.NO_OPTION;
                    Phong p = null;
                    p = getSanPhanSelect();
                    String maPhong = p.getMaPhong();
                    select = JOptionPane.showConfirmDialog(this,
                            "<html>" + "<p style='text-align: center; font-size: 18px; color:red'>Cảnh báo</p>"
                                    + "<p style='text-align: center;'>Xóa phòng " + "<span style='color: blue'> "
                                    + maPhong + "</span>" + " Bạn có chắc chắn muốn xóa phòng " + maPhong
                                    + " này không</p>"
                                    + "<p style='text-align: center;'>Hãy suy nghĩ thật kỹ trước khi quyết định.</p>"
                                    + "</html>",
                            "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    if (select == JOptionPane.YES_OPTION) {
                        phongService.delete(maPhong);
                        tableModelPhong.removeRow(row);
                        loadDataPhong();
                        thongBao(0, "Xóa thành công");
                        showMessage("Xóa thành công", SUCCESS);
                    }
                }
            } catch (Exception e3) {
                thongBao(2, "Xóa thất bại");
                showMessage("Xóa thất bại", ERROR);
            }

        }

    }
}