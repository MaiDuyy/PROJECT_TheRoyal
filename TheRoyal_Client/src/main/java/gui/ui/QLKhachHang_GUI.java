package gui.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;


import gui.format_ui.Table;

import controller.TimKhachHang;
import controller.TimNhanVien;
import entity.KhachHang;
import gui.component.ButtonCustom;
import gui.dialog.SuaKhachHang_Dialog;
import gui.dialog.SuaNhanVien_Dialog;
import gui.dialog.ThemKhachHang_Dialog;
import gui.dialog.ThemNhanVien_Dialog;
import gui.swing.notification.Notification;
import rmi.RMIClient;
import service.KhachHangService;
import service.impl.KhachHangServiceImpl;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;


public class QLKhachHang_GUI extends JInternalFrame implements ActionListener, MouseListener {

    private final JLabel tieuDeLabel;
    private final JSeparator phanCach;
    public DefaultTableModel tableModel;

    private KhachHangService khachHangService;
    private static final long serialVersionUID = 1L;
    private JTextField txtTim;
    private JButton btnTim;
    private JButton btnThem;
    private JButton btnCapNhat;
    private ButtonCustom btnHuyTim;
    private JButton btnXoa;
    private Table tblKhachHang;
    private final JLabel lbShowMessages;
    private JComboBox<String> cbxLuachon;
    private DefaultComboBoxModel<String> modelLuaChon;
    ArrayList<KhachHang> dsKH;
    private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
    private final ThemKhachHang_Dialog themkhachhangdialog;


    public QLKhachHang_GUI() {
        khachHangService = RMIClient.getInstance().getKhachHangService();
        getContentPane().setBackground(new Color(255, 255, 255));
        themkhachhangdialog = new ThemKhachHang_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
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
//		   setSize(1273,760);
        getContentPane().setLayout(null);
        setVisible(true);
        setClosable(true);

        // Cài đặt cho Internal Frame
        setBorder(null);  // Xóa viền
        setTitle("");  // Đặt title là chuỗi rỗng

        // Loại bỏ thanh tiêu đề
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        setClosable(false);
        setIconifiable(false);
        setResizable(false);
        setMaximizable(false);
        try {
            loadListKhachHang();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        DocDuLieuKHVaoTable();

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


        modelLuaChon = new DefaultComboBoxModel<String>(new String[]{"Tất cả", "Căn cước công dân", "Tên", "Số điện thoại"});
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
//		searchPanel.add(btnTim);
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
                tableModel.getDataVector().removeAllElements();
                tableModel.fireTableDataChanged();
                switch (luachon) {
                    case "Tất cả":

                        dsKH = (ArrayList<KhachHang>) TimKhachHang.getInstance().searchTatCa(searchContent);
                        break;
                    case "Căn cước công dân":
                        dsKH = (ArrayList<KhachHang>) TimKhachHang.getInstance().searchCCCD(searchContent);
                        break;
                    case "Tên":
                        dsKH = (ArrayList<KhachHang>) TimKhachHang.getInstance().searchTen(searchContent);
                        break;
                    case "Số điện thoại":
                        dsKH = (ArrayList<KhachHang>) TimKhachHang.getInstance().searchSDT(searchContent);
                        break;
                }

                if (dsKH == null || dsKH.isEmpty()) {
                    thongBao(2, "Không tìm thấy kết quả");
                } else {
                    DocDuLieuKHVaoTable();
                }


            }
        });

        tblKhachHang.addMouseListener(this);

    }

    private void GUI() {


        JPanel tabelPanel = new JPanel();
        tabelPanel.setBounds(41, 160, 1257, 509);
        tabelPanel.setBackground(Color.white);
        tabelPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
                "Danh sách khách hàng",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.PLAIN, 12),
                new Color(246, 167, 193)
        ));
        String[] columns = {"Mã KH", "Tên KH", "SĐT", "Loại KH", "CCCD", "Giới tính"};
        Object[][] data = {};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPanel.setLayout(null);

        tblKhachHang = new Table();
        tblKhachHang.setModel(tableModel);
        tblKhachHang.setFillsViewportHeight(true);
        tblKhachHang.setBackground(new Color(255, 255, 255));
        tblKhachHang.setBackground(Color.white);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(tblKhachHang);
        scrollPane.setBounds(10, 23, 1237, 476);
        tabelPanel.add(scrollPane);
        getContentPane().add(tabelPanel);


    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource().equals(btnHuyTim)) {
            tableModel.getDataVector().removeAllElements();
            try {
                loadListKhachHang();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            DocDuLieuKHVaoTable();
        }


        if (e.getSource().equals(btnThem)) {

            ThemKhachHang_Dialog a;
            a = new ThemKhachHang_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);

            a.setVisible(true);

        }

        if (e.getSource().equals(btnXoa)) {
            showMessage("", 2);
            int row = tblKhachHang.getSelectedRow();

            if (row == -1) {
                thongBao(2, "Bạn cần chọn dòng cần xóa");
                showMessage("Lỗi: Bạn cần chọn dòng cần xóa", ERROR);
                return;
            }
            try {
                KhachHang kh = getKhachHangSelect();
                String maKH  = kh.getMaKH();
                String tenKH = kh.getTenKH();
                int confirm = JOptionPane.showConfirmDialog(this,
                        "<html><p style='text-align: center; font-size: 18px; color:red'>Cảnh báo</p>"
                                + "<p style='text-align: center;'>Xóa khách hàng <span style='color: blue'>" + tenKH + "</span>?</p>"
                                + "<p style='text-align: center;'>Hãy suy nghĩ thật kỹ trước khi quyết định.</p></html>",
                        "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    khachHangService.delete(maKH);
                    ((DefaultTableModel) tblKhachHang.getModel()).removeRow(row);
                    loadListKhachHang();
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

        if (e.getSource().equals(btnCapNhat)) {
            showMessage("", 2);


            if (tblKhachHang.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần chỉnh sửa !");
            } else {

                SuaKhachHang_Dialog u = new SuaKhachHang_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
                u.setVisible(true);
            }

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
//		            	            lbShowMessages.setIcon(checkIcon);
        } else if (type == ERROR) {
            lbShowMessages.setForeground(Color.RED);
//		            	            lbShowMessages.setIcon(errorIcon);
        } else {
            lbShowMessages.setForeground(Color.BLACK);
//		            lbShowMessages.setIcon(null);
        }
        lbShowMessages.setText(message);
    }

    private boolean validDataTim() {
        String cccd = txtTim.getText().trim();
        if (!(cccd.length() > 0)) {
            showMessage("Lỗi: Tên không được để trống", txtTim);
            return false;
        }
        return true;
    }


    public void loadListKhachHang() throws RemoteException {
        dsKH = (ArrayList<KhachHang>) khachHangService.getAll();
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        return sdf.format(date);
    }

    private void DocDuLieuKHVaoTable() {
        if (dsKH == null || dsKH.size() <= 0)
            return;
        for (KhachHang item : dsKH) {
//		              String ngaysinh = formatDate(item.get());
//		              String ngayvaolam = formatDate(item.getNgayVaoLam());
            String gioiTinh = item.isGioiTinh() ? "Nam" : "Nữ";
            tableModel.addRow(new Object[]{item.getMaKH(), item.getTenKH(), item.getSDT(), item.getLoaiKH(), item.getCCCD(), gioiTinh, "", ""});

        }
    }

    public static Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
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
        tableModel.getDataVector().removeAllElements();
        try {
            loadListKhachHang();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        DocDuLieuKHVaoTable();


    }

    public KhachHang getKhachHangSelect() {
        int i_row = tblKhachHang.getSelectedRow();
        if (i_row == -1) {
            throw new IllegalArgumentException("Không chọn được dòng nào");
        }
        String maNV = tblKhachHang.getValueAt(i_row, 0).toString();
        try {
            return khachHangService.getKhachHangTheoMa(maNV);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


}
