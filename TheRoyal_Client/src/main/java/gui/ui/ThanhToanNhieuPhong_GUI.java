//package gui.ui;
//
//import java.sql.Date;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.*;
//import javax.swing.border.TitledBorder;
//import javax.swing.table.DefaultTableModel;
//
//import gui.format_ui.TableActionCellRender;
//
//import dao.*;
//import entity.*;
//import gui.event.TableActionCellEditor;
//import gui.event.TableActionEvent;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;
//
//public class ThanhToanNhieuPhong_GUI extends JDialog {
//
//    private static final long serialVersionUID = 1L;
//    private final JPanel contentPanel = new JPanel();
//
//    private JTextField txtMaPhong;
//    private JTable tbl_DichVu, tbl_SanPham , tbl_SPDV;
//    private DefaultTableModel tableModelDV, tableModelSP, tableModelSPDV;
//    private HoaDonDAO hoadondao;
//    private PhongDAO phongdao;
//    private DonDatPhongDAO dondatphongdao;
//    private CTDonDatPhongDAO ctdondatphongdao;
//    private SanPhamDAO sanphamdao;
//    private DichVuDAO dichvudao;
//    private CTHoaDonDAO cthoadondao;
//    private KhuyenMaiDAO khuyenmaidao;
//	private int currentSL;
//	private double gia;
//    private ArrayList<SanPham> dsSP;
//    private ArrayList<DichVu> dsDV;
//    private ArrayList<DonDatPhong> dsDDP;
//    private ArrayList<HoaDon> dsHD;
//    private ArrayList<Phong> dsPhong;
//    private ArrayList<CTDonDatPhong> dsCTDDP;
//    private ArrayList<CTHoaDon> dsCTHD;
//    private ArrayList<KhuyenMai> dsKhuyenMai;
//    private JTextField txtTGNP;
//    private JTextField txtTGTP;
//    private JTextField txtTienPhong;
//	private DonDatPhong ddp;
//	private HoaDon hd ;
//	private JTextField txtMaHD;
//	private JTextField txtTienSP;
//	private JTextField txtTienDV;
//	private JTextField txtTienKM;
//	private JTextField txtPhuThu;
//	private JTextField txtTongTien;
//	 private DanhSachPhong_GUI danhSachPhong;
//	private Phong phong  ;
//	private JCheckBox chkInHoaDon;
//	private JButton btnKiemTra;
//	  private DefaultComboBoxModel < String > modelKhuyenMai;
//	    private JComboBox < String > cboKhuyenMai ;
//	    private JLabel tieuDeLabel;
//
//
//
//    public ThanhToanNhieuPhong_GUI(Phong phong,DonDatPhong ddp ,DanhSachPhong_GUI danhSachPhong ) {
//    	getContentPane().setBackground(new Color(255, 255, 255));
//
//        this.ddp = ddp;
//        this.danhSachPhong = danhSachPhong;
//        this.phong = phong;
//
//        setTitle("Thanh toán");
//        hoadondao = new HoaDonDAO();
//        phongdao = new PhongDAO();
//        dondatphongdao = new DonDatPhongDAO();
//        dichvudao = new DichVuDAO();
//        sanphamdao = new SanPhamDAO();
//        ctdondatphongdao = new CTDonDatPhongDAO();
//        cthoadondao = new CTHoaDonDAO();
//        khuyenmaidao = new KhuyenMaiDAO();
//
//        setBounds(100, 100, 1228, 645);
//        initComponents();
//        loadData();
//        loadCboMaKhuyenMai();
//    }
//
//    private void initComponents() {
//
//
//		 tieuDeLabel = new JLabel("Thanh Toán Nhiều Phòng");
//		tieuDeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
//		tieuDeLabel.setForeground(new Color(246, 167, 193));
//		tieuDeLabel.setBounds(472, 10, 259, 30);
//		getContentPane().add(tieuDeLabel);
//        JPanel tabelPanel = new JPanel();
//        tabelPanel.setBounds(10, 215, 502, 296);
//        tabelPanel.setBackground(Color.white);
//        tabelPanel.setBorder(BorderFactory.createTitledBorder(
//                BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
//                "Cập nhật sản phẩm dịch vụ",
//                TitledBorder.DEFAULT_JUSTIFICATION,
//                TitledBorder.DEFAULT_POSITION,
//                new Font("Segoe UI", Font.PLAIN, 12),
//                new Color(246, 167, 193)
//        ));
//
//        String[] columnsSPDV = { "Mã ", "Tên ", "Số lượng", "Đơn giá", "Hành động" };
//		tableModelSPDV = new DefaultTableModel(columnsSPDV, 0);
//		tbl_SPDV = new JTable(tableModelSPDV);
//		tbl_SPDV.setBackground(Color.white);
//		tbl_SPDV.setFillsViewportHeight(true);
//
//		JScrollPane scrollPaneSPDV = new JScrollPane(tbl_SPDV);
//		scrollPaneSPDV.setBounds(10, 25, 482, 256);
//
//        getContentPane().setLayout(null);
//        tabelPanel.setLayout(null);
//        tabelPanel.add(scrollPaneSPDV);
//        getContentPane().add(tabelPanel);
//
//        JPanel tabelPanelInfo = new JPanel();
//        tabelPanelInfo.setLayout(null);
//        tabelPanelInfo.setBorder(BorderFactory.createTitledBorder(
//                BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
//                "Thông tin hóa đơn",
//                TitledBorder.DEFAULT_JUSTIFICATION,
//                TitledBorder.DEFAULT_POSITION,
//                new Font("Segoe UI", Font.PLAIN, 12),
//                new Color(246, 167, 193)
//        ));
//        tabelPanelInfo.setBackground(Color.WHITE);
//        tabelPanelInfo.setBounds(522, 71, 670, 363);
//        getContentPane().add(tabelPanelInfo);
//
//        TableActionEvent event = new TableActionEvent() {
//            @Override
//            public void tangSoLuong(int row) {
//                int currentSL = (int) tableModelSPDV.getValueAt(row, 2);
//                String itemIdOrName = (String) tableModelSPDV.getValueAt(row, 0);
//                HoaDon maHD = hoadondao.getHoaDonTheoPhong(phong.getMaPhong());
//                double gia;
//
//                double[] tongTien = hoadondao.getTongTienSanPhamDichVu(maHD.getMaHD());
//                double tongTienSP = tongTien[0];
//                double tongTienDV = tongTien[1];
//
//                if (isSanPham(itemIdOrName)) {
//                    SanPham mh = sanphamdao.getSanPhamTheoMaHoacTen(itemIdOrName);
//                    gia = mh.getGiaSP();
//                    if (mh.getSoLuongSP() <= 0) {
//                        JOptionPane.showMessageDialog(null, "Sản phẩm " + mh.getTenSP() + " đã hết hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//                        return;
//                    }
//                    mh.setSoLuongSP(mh.getSoLuongSP() - 1);
//                    sanphamdao.updateSLSP(mh, mh.getSoLuongSP());
//
//                    CTHoaDon ctHoaDon = cthoadondao.getCTHoaDonByMaSP(mh.getMaSP(),maHD.getMaHD());
//                    ctHoaDon.setSoLuongSP(currentSL + 1);
//                    ctHoaDon.setTongTienSP((currentSL + 1) * gia);
//                    cthoadondao.updateSLSP(ctHoaDon);
//
//                    tongTienSP += gia;
//
//                } else {
//                    DichVu dv = dichvudao.timDichVuTheoMaHoacTheoTen(itemIdOrName);
//                    gia = dv.getGiaDV();
//                    if (dv.getSoLuongDV() <= 0) {
//                        JOptionPane.showMessageDialog(null, "Dịch vụ " + dv.getTenDV() + " đã hết hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//                        return;
//                    }
//                    dv.setSoLuongDV(dv.getSoLuongDV() - 1);
//                    dichvudao.updateSLDV(dv, dv.getSoLuongDV());
//
//                    CTHoaDon ctHoaDon = cthoadondao.getCTHoaDonByMaDV(dv.getMaDV(),maHD.getMaHD());
//                    ctHoaDon.setSoLuongDV(currentSL + 1);
//                    ctHoaDon.setTongTienDV((currentSL + 1) * gia);
//                    cthoadondao.updateSLDV(ctHoaDon);
//
//                    tongTienDV += gia;
//                }
//
//                hoadondao.updateTongTienHoaDon(maHD, tongTienSP, tongTienDV);
//
//
//                tableModelSPDV.setValueAt(currentSL + 1, row, 2);
//                double newTotalPrice = (currentSL + 1) * gia;
//                DecimalFormat df = new DecimalFormat("#,###.##");
////                tableModelSPDV.setValueAt(df.format(newTotalPrice), row, 3);
//                double tongtien1 = tongTienDV + tongTienSP + maHD.getTienPhong();
//
//                txtTienSP.setText(df.format(tongTienSP));
//                txtTienDV.setText(df.format(tongTienDV));
//                txtTongTien.setText(df.format(tongtien1));
//            }
//
//            @Override
//            public void giamSoLuong(int row) {
//                int currentSL = (int) tableModelSPDV.getValueAt(row, 2);
//                if (currentSL <= 0) {
//                    JOptionPane.showMessageDialog(null, "Số lượng không thể giảm thêm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//
//                String itemIdOrName = (String) tableModelSPDV.getValueAt(row, 0);
//                HoaDon maHD = hoadondao.getHoaDonTheoPhong(phong.getMaPhong());
//                double gia;
//
//                double[] tongTien = hoadondao.getTongTienSanPhamDichVu(maHD.getMaHD());
//                double tongTienSP = tongTien[0];
//                double tongTienDV = tongTien[1];
//
//                if (isSanPham(itemIdOrName)) {
//                    SanPham mh = sanphamdao.getSanPhamTheoMaHoacTen(itemIdOrName);
//                    gia = mh.getGiaSP();
//                    mh.setSoLuongSP(mh.getSoLuongSP() + 1);
//                    sanphamdao.updateSLSP(mh, mh.getSoLuongSP());
//
//                    CTHoaDon ctHoaDon = cthoadondao.getCTHoaDonByMaSP(mh.getMaSP(),maHD.getMaHD());
//                    ctHoaDon.setSoLuongSP(currentSL - 1);
//                    ctHoaDon.setTongTienSP((currentSL - 1) * gia);
//                    cthoadondao.updateSLSP(ctHoaDon);
//
//                    tongTienSP -= gia;
//
//                } else {
//                    DichVu dv = dichvudao.timDichVuTheoMaHoacTheoTen(itemIdOrName);
//                    gia = dv.getGiaDV();
//                    dv.setSoLuongDV(dv.getSoLuongDV() + 1);
//                    dichvudao.updateSLDV(dv, dv.getSoLuongDV());
//
//                    CTHoaDon ctHoaDon = cthoadondao.getCTHoaDonByMaDV(dv.getMaDV(),maHD.getMaHD());
//                    ctHoaDon.setSoLuongDV(currentSL - 1);
//                    ctHoaDon.setTongTienDV((currentSL - 1) * gia);
//                    cthoadondao.updateSLDV(ctHoaDon);
//
//                    tongTienDV -= gia;
//                }
//
//                hoadondao.updateTongTienHoaDon(maHD, tongTienSP, tongTienDV);
//
//                tableModelSPDV.setValueAt(currentSL - 1, row, 2);
//                double newTotalPrice = (currentSL - 1) * gia;
//                DecimalFormat df = new DecimalFormat("#,###.##");
////                tableModelSPDV.setValueAt(df.format(newTotalPrice), row, 3);
//                double tongtien1 = tongTienDV + tongTienSP + maHD.getTienPhong();
//
//                txtTienSP.setText(df.format(tongTienSP));
//                txtTienDV.setText(df.format(tongTienDV));
//                txtTongTien.setText(df.format(tongtien1));
//            }
//
//            private boolean isSanPham(String itemIdOrName) {
//                return sanphamdao.getSanPhamTheoMaHoacTen(itemIdOrName) != null;
//            }
//        };
//
//
//
//        tbl_SPDV.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
//        tbl_SPDV.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
//
//
//
//        JLabel lblMaNV = new JLabel("Mã phòng:");
//        lblMaNV.setBounds(26, 78, 112, 38);
//        txtMaPhong = new JTextField(15);
//        txtMaPhong.setLocation(102, 79);
//        txtMaPhong.setSize(126, 38);
//        txtMaPhong.setEditable(false);
//
//        JLabel lblTGNP = new JLabel("Thời gian nhận phòng:");
//        lblTGNP.setBounds(26, 126, 187, 38);
//        txtTGNP = new JTextField(15);
//        txtTGNP.setEditable(false);
//        txtTGNP.setBounds(165, 127, 126, 38);
//
//        JLabel lblTGTP = new JLabel("Thời gian trả phòng:");
//        lblTGTP.setBounds(26, 175, 202, 38);
//        txtTGTP = new JTextField(15);
//        txtTGTP.setEditable(false);
//        txtTGTP.setBounds(165, 176, 126, 38);
//
//        JLabel lblTienPhong = new JLabel("Tiền phòng:");
//        lblTienPhong.setBounds(299, 224, 88, 38);
//        txtTienPhong = new JTextField(15);
//        txtTienPhong.setEditable(false);
//        txtTienPhong.setBounds(409, 225, 126, 38);
//
//        tabelPanelInfo.add(lblMaNV);
//        tabelPanelInfo.add(txtMaPhong);
//        tabelPanelInfo.add(lblTGNP);
//        tabelPanelInfo.add(txtTGNP);
//        tabelPanelInfo.add(lblTGTP);
//        tabelPanelInfo.add(txtTGTP);
//        tabelPanelInfo.add(lblTienPhong);
//        tabelPanelInfo.add(txtTienPhong);
//
//        JLabel lblMHan = new JLabel("Mã hóa đơn:");
//        lblMHan.setBounds(26, 30, 112, 38);
//        tabelPanelInfo.add(lblMHan);
//
//        txtMaHD = new JTextField(15);
//        txtMaHD.setEditable(false);
//        txtMaHD.setBounds(102, 31, 126, 38);
//        tabelPanelInfo.add(txtMaHD);
//
//        JLabel lblTienSP = new JLabel("Tổng tiền sản phẩm:");
//        lblTienSP.setBounds(299, 30, 110, 38);
//        tabelPanelInfo.add(lblTienSP);
//
//        txtTienSP = new JTextField(15);
//        txtTienSP.setEditable(false);
//        txtTienSP.setBounds(409, 30, 126, 38);
//        tabelPanelInfo.add(txtTienSP);
//
//        JLabel lblTienDV = new JLabel("Tổng tiền dịch vụ");
//        lblTienDV.setBounds(299, 78, 110, 38);
//        tabelPanelInfo.add(lblTienDV);
//
//        txtTienDV = new JTextField(15);
//        txtTienDV.setEditable(false);
//        txtTienDV.setBounds(409, 78, 126, 38);
//        tabelPanelInfo.add(txtTienDV);
//
//        JLabel lblTienKM = new JLabel("Tiền khuyến mãi:");
//        lblTienKM.setBounds(299, 126, 110, 38);
//        tabelPanelInfo.add(lblTienKM);
//
//
//        modelKhuyenMai = new DefaultComboBoxModel < String > ();
//        cboKhuyenMai = new JComboBox < String > (modelKhuyenMai);
//        cboKhuyenMai.setBounds(409, 126, 126, 38);
//        tabelPanelInfo.add(cboKhuyenMai);
//
//        txtPhuThu = new JTextField(15);
//        txtPhuThu.setEditable(false);
//        txtPhuThu.setBounds(409, 175, 126, 38);
//        tabelPanelInfo.add(txtPhuThu);
//
//        JLabel lblPhuThu = new JLabel("Phụ thu:");
//        lblPhuThu.setBounds(299, 175, 110, 38);
//        tabelPanelInfo.add(lblPhuThu);
//
//        JLabel lblTongTien = new JLabel("Tổng tiền:");
//        lblTongTien.setBounds(198, 289, 88, 38);
//        tabelPanelInfo.add(lblTongTien);
//
//        txtTongTien = new JTextField(15);
//        txtTongTien.setEditable(false);
//        txtTongTien.setBounds(296, 290, 126, 38);
//        tabelPanelInfo.add(txtTongTien);
//
//        btnKiemTra = new JButton("Kiểm tra");
//        btnKiemTra.setIcon(new ImageIcon(ThanhToanNhieuPhong_GUI.class.getResource("/src/ICON/icon/check2_16.png")));
//        btnKiemTra.setBounds(545, 135, 115, 21);
//        tabelPanelInfo.add(btnKiemTra);
//
//        JButton btnThanhToan = new JButton("Thanh Toán");
//        btnThanhToan.setBounds(1062, 547, 142, 40);
//        getContentPane().add(btnThanhToan);
//        btnThanhToan.setIcon(new ImageIcon(ThanhToanNhieuPhong_GUI.class.getResource("/src/ICON/icon/cash-on-delivery.png")));
//
//        chkInHoaDon = new JCheckBox("Xuất hóa đơn");
//        chkInHoaDon.setBounds(939, 557, 110, 21);
//        getContentPane().add(chkInHoaDon);
//        chkInHoaDon.setBackground(new Color(255, 255, 255));
//        btnThanhToan.addActionListener(e -> thanhToanHoaDon(phong));
//        btnKiemTra.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				btnkiemtra();
//
//			}
//		});
//    }
//
//    private void loadData() {
//        dsHD = hoadondao.getListHoaDon();
//        dsPhong = phongdao.getListPhong();
//        dsDDP = dondatphongdao.getListDonDatPhong();
//        dsDV = dichvudao.getDanhSachDichVu();
//        dsSP = sanphamdao.getDanhSachSanPham();
//
//        loadReservationInfo(ddp);
//        loadThongTinHoaDon(ddp);
//        loadCTHoaDonToTable(phong);
//    }
//
//
//
//    private void thanhToanHoaDon(Phong phong) {
//        try {
//            if (hoadondao == null) {
//                JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            if (ddp == null || ddp.getPhong() == null) {
//                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin phòng hoặc đơn đặt phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            if (chkInHoaDon.isSelected()) {
//                dondatphongdao.updateTinhTrang(ddp.getMaDDP(), "Đã thanh toán");
//
//                HoaDon maHD = hoadondao.getHoaDonTheoDonDatPhong(ddp.getMaDDP());
//                if (maHD != null) {
//                    hoadondao.updateTinhTrang(maHD.getMaHD(), "Đã thanh toán");
//                    inHoaDon(maHD.getMaHD());
//                } else {
//                    JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn cho đơn đặt phòng này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                Phong phong1 = phongdao.getPhongByMaPhong(ddp.getPhong().getMaPhong());
//                if (phong1 != null) {
//                    phongdao.updateTinhTrang(phong1, "Đang dọn dẹp");
//                }
//                JOptionPane.showMessageDialog(this, "Thanh toán thành công và hóa đơn đã được in!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                HoaDon maHD = hoadondao.getHoaDonTheoDonDatPhong(ddp.getMaDDP());
//                if (maHD != null) {
//                    hoadondao.updateTinhTrang(maHD.getMaHD(), "Đã thanh toán");
//                }
//
//                dondatphongdao.updateTinhTrang(ddp.getMaDDP(), "Đã thanh toán");
//
//
//                Phong phong1 = phongdao.getPhongByMaPhong(ddp.getPhong().getMaPhong());
//                if (phong1 != null) {
//                    phongdao.updateTinhTrang(phong1, "Đang dọn dẹp");
//                }
//
//                JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            }
//
//            dispose();
//            danhSachPhong.capNhatLaiDanhSachPhong();
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            ex.printStackTrace();
//        }
//    }
//
//
//
//
//    private void loadRoomInfo(DonDatPhong ddp) {
//        if (ddp.getPhong() != null) {
//            Phong phong = ddp.getPhong();
//            txtMaPhong.setText(phong.getMaPhong());
//            double number = phong.getGiaTien();
//            DecimalFormat df = new DecimalFormat("#,###.##");
//            String dongia = df.format(number);
//            txtTienPhong.setText(dongia);
//        } else {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void loadReservationInfo(DonDatPhong ddp) {
//        txtTGNP.setText(formatDate(ddp.getThoiGianDatPhong()));
//        txtTGTP.setText(formatDate(ddp.getThoiGianTraPhong()));
//    }
//
//
//    private String formatDate(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        return sdf.format(date);
//    }
//    private void loadThongTinHoaDon(DonDatPhong ddp) {
// 	   if (dsHD == null || dsHD.size() <= 0)
//            return;
//
//
//        for (HoaDon item: dsHD) {
//        	if (item.getDonDatPhong().getMaDDP().equals(ddp.getMaDDP())) {
//            double tienDV = item.getTienDichVu();
//            double tienKM = item.getTienKhuyenMai();
//            double tienPhat = item.getTienPhat();
//            double tienPhong = item.getTienPhong();
//            double tienSanPham = item.getTienSanPham();
//            double tongTien = item.getTongTien();
//            String ngaylaphoadon = formatDate(item.getThoiGianLapHD());
//
//
//            DecimalFormat df = new DecimalFormat("#,###.##");
//            String tiendv = df.format(tienDV);
//            String tienkm = df.format(tienKM);
//            String tienphat = df.format(tienPhat);
//            String tienphong = df.format(tienPhong);
//            String tiensp = df.format(tienSanPham);
//            String tongtien = df.format(tongTien);
//
//            txtMaPhong.setText(item.getPhong().getMaPhong());
//            txtMaHD.setText(item.getMaHD());
//            txtTienPhong.setText(tienphong);
//            txtTienSP.setText(tiensp);
//            txtTienDV.setText(tiendv);
////            txtTienKM.setText(tienkm);
//            txtPhuThu.setText(tienphat);
//            txtTongTien.setText(tongtien);
//            break;
//        	}
//
//
//        }
// }
//
//    private void loadCboMaKhuyenMai() {
//        dsKhuyenMai = khuyenmaidao.getListUuDai();
//        if (dsKhuyenMai == null || dsKhuyenMai.size() <= 0)
//            return;
//        for (KhuyenMai item: dsKhuyenMai) {
//            cboKhuyenMai.addItem(item.getMaKM());
//        }
//    }
//    public void loadCTHoaDonToTable(Phong phong) {
//        HoaDon hoaDon = hoadondao.getHoaDonTheoPhong(phong.getMaPhong());
//
//        tableModelSPDV.setRowCount(0);
//
//         dsCTHD = (ArrayList<CTHoaDon>) cthoadondao.getCTHoaDonByMaHoaDon(hoaDon.getMaHD());
//
//        DecimalFormat df = new DecimalFormat("#,###.##");
//
//        for (CTHoaDon ctHoaDon : dsCTHD) {
//            Object[] row = new Object[6];
//
//            row[0] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getSanPham().getMaSP() :
//                    (ctHoaDon.getDichVu() != null ? ctHoaDon.getDichVu().getMaDV() : "");
//            row[1] = (ctHoaDon.getSanPham() != null) ?
//                     ctHoaDon.getSanPham().getTenSP() :
//                     (ctHoaDon.getDichVu() != null ? ctHoaDon.getDichVu().getTenDV() : "");
//            row[2] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getSoLuongSP() : ctHoaDon.getSoLuongDV();
//
//            double unitPrice = (ctHoaDon.getSanPham() != null) ?
//                    ctHoaDon.getTongTienSP() / ctHoaDon.getSoLuongSP() :
//                    (ctHoaDon.getDichVu() != null ? ctHoaDon.getTongTienDV() / ctHoaDon.getSoLuongDV() : 0);
//            row[3] = df.format(unitPrice);
//
//            double totalPrice = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getTongTienSP() : ctHoaDon.getTongTienDV();
//            row[4] = df.format(totalPrice);
//
//            row[5] = "";
//
//            tableModelSPDV.addRow(row);
//        }
//    }
//	 public void inHoaDon(String maHD) {
//	        HoaDon hoaDon = hoadondao.getHoaDonTheoPhong(phong.getMaPhong());
//
////	        HoaDon.getInstance().setMaHD(maHD);
////	        HoaDon.getInstance().setNhanVien(hoaDon.getNhanVien());
//	        if (hoaDon != null) {
//	            InHoaDon_GUI inHoaDonGUI = new InHoaDon_GUI(hoaDon);
//	            PrinterJob printerJob = PrinterJob.getPrinterJob();
//	            printerJob.setPrintable(inHoaDonGUI);
//
//	            try {
//	                if (printerJob.printDialog()) {
//	                    printerJob.print();
//	                }
//	            } catch (PrinterException e) {
//	                System.err.println("In lỗi: " + e.getMessage());
//	            }
//	        } else {
//	            System.out.println("Hóa đơn không tồn tại với mã: " + maHD);
//	        }
//	    }
//
//	 private void btnkiemtra() {
//		    String maKM = (String) cboKhuyenMai.getSelectedItem();
//		    HoaDon hoaDon = hoadondao.getHoaDonTheoPhong(phong.getMaPhong());
//
//		    if (maKM == null || maKM.isEmpty()) {
//		        JOptionPane.showMessageDialog(this, "Vui lòng chọn mã", "Trống", JOptionPane.WARNING_MESSAGE);
//		        return;
//		    }
//
//		    KhuyenMai khuyenMai = khuyenmaidao.getKhuyenMaiTheoMa1(maKM);
//		    if (khuyenMai == null) {
//		        JOptionPane.showMessageDialog(this, "Mã khuyến mãi không hợp lệ", "Error", JOptionPane.ERROR_MESSAGE);
//		        return;
//		    }
//
//		    Date ngayhientai = new Date(System.currentTimeMillis());
//		    Date ngayketthuc = khuyenMai.getNgayKetThuc();
//
//		    if (ngayketthuc.before(ngayhientai)) {
//		        JOptionPane.showMessageDialog(this, "Mã khuyến mãi đã hết hạn", "Error", JOptionPane.ERROR_MESSAGE);
//		        return;
//		    }
//
//		    double discountPercentage = khuyenMai.getGiaTriKM();
//		    double currentTotal = hoaDon.getTongTien();
//		    double discountAmount = currentTotal * (discountPercentage / 100);
//		    double newTotal = currentTotal - discountAmount;
//
//		    hoaDon.setTongTien(newTotal);
//		    hoaDon.setPhong(phong);
//		    hoaDon.setKhuyenMai(khuyenMai);
//		    hoaDon.setTienKhuyenMai(discountAmount);
//
//		    boolean isTongTienUpdated = hoadondao.update(hoaDon);
//		    boolean isSoLuongUpdated = khuyenmaidao.updateSoLuong(maKM);
//
//		    if (isTongTienUpdated && isSoLuongUpdated) {
//		        JOptionPane.showMessageDialog(this, "Áp dụng mã thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
//		        DecimalFormat df = new DecimalFormat("#,###.##");
//		        txtTongTien.setText(df.format(newTotal));
//		    } else {
//		        JOptionPane.showMessageDialog(this, "Áp dụng mã thất bại", "Error", JOptionPane.ERROR_MESSAGE);
//		    }
//		}
//
//
//
//}
