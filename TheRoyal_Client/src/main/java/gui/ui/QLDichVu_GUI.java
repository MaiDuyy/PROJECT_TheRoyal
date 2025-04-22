package gui.ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import gui.format_ui.Table;
import dao.*;

import entity.*;
import gui.dialog.SuaKhachHang_Dialog;
import gui.component.ButtonCustom;
import gui.dialog.SuaDichVu_Dialog;
import gui.dialog.ThemDichVu_Dialog;
import gui.dialog.ThemKhachHang_Dialog;
import gui.dialog.ThemDichVu_Dialog;
import gui.swing.notification.Notification;
import controller.TimDichVu;
import lombok.SneakyThrows;
import rmi.RMIClient;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class QLDichVu_GUI extends JInternalFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JSeparator phanCach;
    private Table tblDichVu;
    private JTextField txtTim;
    public DefaultTableModel tableModelDichVu;
    private ArrayList < DichVu > dsDV;
    private JComboBox < String > cbxLuachon;
    private DefaultComboBoxModel < String > modelLuaChon;
    private JButton btnThem, btnTim, btnXoa,btnCapNhat;
    private ButtonCustom btnHuyTim;
    private JLabel lbShowMessages;
    private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
    private JLabel tieuDeLabel;
    private ThemDichVu_Dialog themdichvudialog;

    public QLDichVu_GUI() {
    	getContentPane().setBackground(new Color(255, 255, 255));
        themdichvudialog = new ThemDichVu_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
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
        loadListDichVu();
        DocDuLieuDVVaoTable();

    }
    private void GUI() {

        JPanel tabelPanel = new JPanel();
        tabelPanel.setBounds(41, 160, 1257, 509);
        tabelPanel.setBackground(Color.white);
        tabelPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
            "Danh sách sản phẩm",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(246, 167, 193)
        ));
        String[] columns = {
            "Mã dịch vụ",
            "Tên dịch vụ",
            "Mô Tả",
            "Đơn Giá",
            "Số Lượng"
        };
        Object[][] data = {};
        tableModelDichVu = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPanel.setLayout(null);

        tblDichVu = new Table();
        tblDichVu.setModel(tableModelDichVu);
        tblDichVu.setFillsViewportHeight(true);
        tblDichVu.setBackground(new Color(255, 255, 255));
        tblDichVu.setBackground(Color.white);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(tblDichVu);
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

        modelLuaChon = new DefaultComboBoxModel < String > (new String[] {
            "Tất cả",
            "Tên",
            "Mã sản phẩm"
        });
        cbxLuachon = new JComboBox < String > (modelLuaChon);
        cbxLuachon.setBounds(10, 34, 175, 29);
        searchPanel.add(cbxLuachon);
        txtTim = new JTextField(15);
        txtTim.setBounds(195, 34, 290, 29);

        btnTim = new JButton("Tìm Kiếm");
        btnTim.setBounds(376, 34, 107, 29);
        btnTim.setIcon(new ImageIcon("icon/search_16.png"));
        btnHuyTim = new ButtonCustom("Xem tất cả","rest", 12);
        btnHuyTim.setBounds(493, 34, 112, 29);
        btnHuyTim.setIcon(new ImageIcon("icon/refresh.png"));
        searchPanel.setLayout(null);
        searchPanel.add(txtTim);
//        searchPanel.add(btnTim);
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
        btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btnCapNhat = new JButton("Sửa");
        btnCapNhat.setBackground(new Color(255, 255, 255));
        btnCapNhat.setBounds(191, 25, 67, 63);
        btnCapNhat.setIcon(new ImageIcon("icon/updated.png"));
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat.setFocusable(false);
        btnCapNhat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCapNhat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        controlPanel.setLayout(null);

        controlPanel.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(255, 255, 255));
        btnXoa.setBounds(114, 25, 67, 63);
        btnXoa.setIcon(new ImageIcon("icon/trash2_16.png"));
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.setFocusable(false);
        btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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
                
                tableModelDichVu.getDataVector().removeAllElements();
                tableModelDichVu.fireTableDataChanged();
                switch (luachon) {
                case "Tất cả":
                    dsDV = (ArrayList<DichVu>) TimDichVu.getInstance().searchTatCa(searchContent);
                    break;
                case "Tên":
                    dsDV = (ArrayList<DichVu>) TimDichVu.getInstance().searchTen(searchContent);
                    break;
                case "Mã sản phẩm":
                    dsDV = (ArrayList<DichVu>) TimDichVu.getInstance().searhMa(searchContent);
                    break;
                }
                if (dsDV == null || dsDV.isEmpty()) {
	                   thongBao(2, "Không tìm thấy kết quả");
	                } else {
	                   DocDuLieuDVVaoTable();
	                }

            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnThem)) {

            ThemDichVu_Dialog a;
            a = new ThemDichVu_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);

            a.setVisible(true);

        }
        if (e.getSource().equals(btnXoa)) {
            showMessage("", 2);
            int row = tblDichVu.getSelectedRow();

            if (row == -1) {
                thongBao(2, "Bạn cần chọn dòng cần xóa");
                showMessage("Lỗi: Bạn cần chọn dòng cần xóa", ERROR);
                return;
            }
            try {
                DichVu sp = getSanPhanSelect();
                String tenKH = sp.getTenDV();
                int confirm = JOptionPane.showConfirmDialog(this,
                    "<html><p style='text-align: center; font-size: 18px; color:red'>Cảnh báo</p>" +
                    "<p style='text-align: center;'>Xóa khách hàng <span style='color: blue'>" + tenKH + "</span>?</p>" +
                    "<p style='text-align: center;'>Hãy suy nghĩ thật kỹ trước khi quyết định.</p></html>",
                    "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {

                    RMIClient.getInstance().getDichVuService().delete(sp.getMaDV());
                    ((DefaultTableModel) tblDichVu.getModel()).removeRow(row);
                    loadListDichVu();
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

            if (tblDichVu.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần chỉnh sửa !");
            } else {

                SuaDichVu_Dialog u = new SuaDichVu_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
                u.setVisible(true);
            }
        }

        if (e.getSource().equals(btnHuyTim)) {
            tableModelDichVu.getDataVector().removeAllElements();
            loadListDichVu();
            DocDuLieuDVVaoTable();

        }

    }

    private void DocDuLieuDVVaoTable() {
        if (dsDV == null || dsDV.size() <= 0)
            return;
        for (DichVu item: dsDV) {
            double number = item.getGiaDV();
            DecimalFormat df = new DecimalFormat("#,###.##");
            String donGia = df.format(number);
            tableModelDichVu.addRow(new Object[] {
                item.getMaDV(), item.getTenDV(), item.getMoTa(), donGia, item.getSoLuongDV()
            });

        }
    }

    @SneakyThrows
    public void loadListDichVu() {
        dsDV = (ArrayList<DichVu>) RMIClient.getInstance().getDichVuService().getAll();
    }

    private boolean validDataTim() {
        String maKM = txtTim.getText().trim();

        // Kiểm tra mã khuyến mãi không được để trống
        if (maKM.isEmpty()) {
            showMessage("Lỗi: Mã sản phẩm không được để trống", txtTim);
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
        tableModelDichVu.getDataVector().removeAllElements();
        loadListDichVu();
        DocDuLieuDVVaoTable();

    }
    @SneakyThrows
    public DichVu getSanPhanSelect() {
        int i_row = tblDichVu.getSelectedRow();
        if (i_row == -1) {
            throw new IllegalArgumentException("Không chọn được dòng nào");
        }
        String maNV = tblDichVu.getValueAt(i_row, 0).toString();
        return RMIClient.getInstance().getDichVuService().getDichVuByMaDV(maNV);
    }

}