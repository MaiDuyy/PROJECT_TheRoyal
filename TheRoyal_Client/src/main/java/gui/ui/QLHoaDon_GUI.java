package gui.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import gui.format_ui.Table;

import dao.*;
import entity.*;
import gui.component.ButtonCustom;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.CTHoaDonService;
import service.DichVuService;
import service.HoaDonService;
import service.SanPhamService;

import java.awt.Component;
import java.util.Date;
import java.util.List;


public class QLHoaDon_GUI extends JInternalFrame  implements ActionListener,MouseListener{
    private JLabel tieuDeLabel;
    private JSeparator phanCach;
    private Table tblHoaDon   , tblSP ,tblDV;
    private DefaultTableModel tableModelHoaDon, tableModelCTHD  , tableModelSP , tableModelDV;
    private JButton btnTim, btnThem, btnXoa, btnCapNhat, btnLamLai, btnThemTaiKhoanNV;
    private JTextField txtTim ,txtGiaDV, txtGiaSP,txtTongGiaHD;
    private JLabel lbShowMessages;
    private ButtonCustom btnXemTatCa;
  
    private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
    public JFrame popup = new JFrame();
    private static final long serialVersionUID = 1;
    private HoaDonService hoadondao;
    private CTHoaDonService cthddao;
    private SanPhamService sanphamdao;
    private DichVuService dichvudao;
     
    private ArrayList < CTHoaDon > dsCTHD;
    private List<HoaDon> dsHD;
    private ArrayList < CTHoaDon > dsCT;

  

	public QLHoaDon_GUI() {

		getContentPane().setBackground(new Color(255, 255, 255));
        hoadondao = RMIClient.getInstance().getHoaDonService();
        cthddao = RMIClient.getInstance().getCtHoaDonService();
        sanphamdao = RMIClient.getInstance().getSanPhamService();
        dichvudao = RMIClient.getInstance().getDichVuService();
        dsHD = (ArrayList<HoaDon>) RMIClient.getInstance().getHoaDonService();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      
        tieuDeLabel = new JLabel("Quản lý Hóa Đơn");
        tieuDeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tieuDeLabel.setForeground(new Color(246, 167, 193));
        tieuDeLabel.setBounds(29, 10, 300, 30);
        getContentPane().add(tieuDeLabel);

        phanCach = new JSeparator();
        phanCach.setBounds(0, 50, screenSize.width, 10);
        getContentPane().add(phanCach);

        setBackground(Color.white);
        setBounds(100, 100, 1364, 799);
//        	        setSize(1270, 710);
        //	        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JLabel lblGiaDV_1 = new JLabel("Tổng giá hóa đơn");
        lblGiaDV_1.setBounds(630, 578, 125, 31);
        getContentPane().add(lblGiaDV_1);
        
        txtTongGiaHD = new JTextField(15);
        txtTongGiaHD.setFont(new Font("Tahoma", Font.BOLD, 17));
        txtTongGiaHD.setBackground(new Color(255, 255, 255));
        txtTongGiaHD.setEnabled(false);
        txtTongGiaHD.setBounds(801, 578, 288, 31);
        txtTongGiaHD.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(txtTongGiaHD);
        setVisible(true);
        setClosable(true);

  
        setBorder(null); 
        setTitle(""); 

        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
     	ui.setNorthPane(null);

     	setClosable(false);
     	setIconifiable(false);
        setResizable(false);
        setMaximizable(false);
        
        
        GUIQLHoaDon();
        loadDataHoaDon();
        loadDataCTHD();
        DocDuLieuHoaDonVaoTable();
//        DocDuLieuSPVaoTable();
//        DocDuLieuDVVaoTable();
//        DocDuLieuCTHDVaoTable();
    }

    private void GUIQLHoaDon() {
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(29, 60, 1221, 59);
        searchPanel.setBackground(Color.white);
        searchPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
            "Tìm kiếm",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(246, 167, 193)
        ));

        JLabel searchLabel = new JLabel("Mã hóa đơn");
        searchLabel.setBounds(386, 24, 102, 25);
        txtTim = new JTextField(15);
        txtTim.setBounds(493, 24, 126, 25);
        txtTim.addKeyListener(new KeyAdapter() {
        		@SneakyThrows
                @Override
        		public void keyReleased(KeyEvent e) {
        		      showMessage("", 2);
      	            if (validDataTim()) {
      	                String maHD = txtTim.getText().trim();
      	                tableModelHoaDon.getDataVector().removeAllElements();
      	                tableModelHoaDon.fireTableDataChanged();
      	                dsHD = hoadondao.getHoaDonTheoMaList(maHD);
      	                if (dsHD == null || dsHD.size() <= 0) {
      	                    showMessage("Không tìm thấy hóa đơn", ERROR);
      	                } else
      	                    DocDuLieuHoaDonVaoTable();
      	            }
        		}
        });
        btnTim = new JButton("Tìm Kiếm");
        btnTim.setBounds(624, 23, 110, 21);
        btnTim.setIcon(new ImageIcon(QLHoaDon_GUI.class.getResource("/src/icon/search_16.png")));
        btnXemTatCa = new ButtonCustom("Xem tất cả","rest", 12);
        btnXemTatCa.setBounds(661, 25, 110, 24);
        btnXemTatCa.setIcon(new ImageIcon(QLHoaDon_GUI.class.getResource("/src/icon/refresh.png")));
        searchPanel.setLayout(null);

        searchPanel.add(searchLabel);
        searchPanel.add(txtTim);
//        searchPanel.add(btnTim);
        searchPanel.add(btnXemTatCa);
        getContentPane().add(searchPanel);



        // Bảng nhân viên
        JPanel tabelPanelPhong = new JPanel();
        tabelPanelPhong.setBounds(29, 124, 1221, 166);
        tabelPanelPhong.setBackground(Color.white);
        tabelPanelPhong.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
            "Danh sách hóa đơn",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(246, 167, 193)
        ));
        String[] columns = {
        		"Mã hóa đơn",
        		"Mã khách hàng",
            "Mã phòng",
            "Mã nhân viên",
            "Mã đơn đặt phòng",
            "Mã khuyến mãi",
            "Thời gian lập",
            "Tiền phòng",
            "Tiền phạt",
            "Tiền khuyến mãi",
            "Tiền dịch vụ",
            "Tiền sản phẩm",
            "Tổng tiền"
        };
        Object[][] data = {};
        tableModelHoaDon = new DefaultTableModel(columns, 0){
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPanelPhong.setLayout(null);

        tblHoaDon = new Table();
        tblHoaDon.setModel(tableModelHoaDon);
        tblHoaDon.setFillsViewportHeight(true);
        tblHoaDon.setBackground(new Color(255, 255, 255));
        tblHoaDon.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(tblHoaDon);
        scrollPane.setBounds(10, 20, 1201, 126);
        tabelPanelPhong.add(scrollPane);
        getContentPane().add(tabelPanelPhong);



      JPanel tabelPanelTK = new JPanel();
        tabelPanelTK.setBounds(29, 300, 583, 252);
        tabelPanelTK.setBackground(Color.white);
        tabelPanelTK.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
            "Danh sách dịch vụ",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(246, 167, 193)
        ));
        String[] columns1 = {
            "Mã dịch vụ",
            "Tên dịch vụ",
            "Số lượng",
            "Giá"
        };
        tableModelDV= new DefaultTableModel(columns1, 0){
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPanelTK.setLayout(null);

        tblDV = new Table();
        tblDV.setModel(tableModelDV);
        tblDV.setBackground(Color.white);
        tblDV.setFillsViewportHeight(true);
        tblDV.setBackground(new Color(255, 255, 255));

        JScrollPane scrollPaneTK = new JScrollPane(tblDV);
        scrollPaneTK.setBounds(10, 23, 563, 183);

        JLabel lblGiaDV = new JLabel("Tổng giá dịch vụ");
        lblGiaDV.setLocation(151, 211);
        lblGiaDV.setSize(99, 31);
        txtGiaDV = new JTextField(15);
        txtGiaDV.setEnabled(false);
        txtGiaDV.setFont(new Font("Tahoma", Font.BOLD, 17));
        txtGiaDV.setForeground(new Color(255, 0, 128));
        txtGiaDV.setBackground(new Color(255, 255, 255));
        txtGiaDV.setLocation(298, 211);
        txtGiaDV.setSize(275, 31);
        txtGiaDV.setBorder(BorderFactory.createEmptyBorder());
        tabelPanelTK.add(lblGiaDV);
        tabelPanelTK.add(txtGiaDV);
        tabelPanelTK.add(scrollPaneTK);




        getContentPane().add(tabelPanelTK);


        JPanel tabelPanelSP = new JPanel();
        tabelPanelSP.setBounds(622, 300, 628, 252);
        tabelPanelSP.setBackground(Color.white);
        tabelPanelSP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
            "Danh sách sản phẩm",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(246, 167, 193)
        ));
        String[] columns2 = {
            "Mã sản phẩm",
            "Tên sản phẩm",
            "Số lượng",
            "Giá"
        };
       tableModelSP= new DefaultTableModel(columns2, 0){
       	@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
       tabelPanelSP.setLayout(null);

        tblSP = new Table();
        tblSP.setModel(tableModelSP);
        tblSP.setBackground(Color.white);
        tblSP.setFillsViewportHeight(true);
        tblSP.setBackground(new Color(255, 255, 255));

        tabelPanelSP.setLayout(null);

        JScrollPane scrollPaneSP = new JScrollPane(tblSP);
        scrollPaneSP.setBounds(10, 22, 608, 184);
        tabelPanelSP.add(scrollPaneSP);
        getContentPane().add(tabelPanelSP);

        JLabel lblGiaDV_1 = new JLabel("Tổng giá sản phẩm");
        lblGiaDV_1.setBounds(171, 211, 124, 31);
        tabelPanelSP.add(lblGiaDV_1);

        txtGiaSP = new JTextField(15);
        txtGiaSP.setFont(new Font("Tahoma", Font.BOLD, 17));
        txtGiaSP.setBackground(new Color(255, 255, 255));
        txtGiaSP.setEnabled(false);
        txtGiaSP.setBounds(336, 211, 282, 31);
        txtGiaSP.setBorder(BorderFactory.createEmptyBorder());
        tabelPanelSP.add(txtGiaSP);




        // Panel điều khiển
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(40, 535, 300, 98);
        controlPanel.setBackground(Color.white);
        controlPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
            "Chức năng",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(246, 167, 193)
        ));

        btnThem = new JButton("Thêm ", new ImageIcon("src/icon/blueAdd_16.png"));
        btnThem.setBounds(45, 23, 132, 21);
        btnCapNhat = new JButton("Cập Nhật ");
        btnCapNhat.setBounds(222, 23, 145, 21);
        btnCapNhat.setIcon(new ImageIcon("src/icon/check2_16.png"));

        btnLamLai = new JButton("Làm Lại");
        btnLamLai.setBounds(222, 47, 145, 21);
        btnLamLai.setIcon(new ImageIcon("src/icon/refresh_16.png"));

        btnThemTaiKhoanNV = new JButton("Đổi mật khẩu");
        btnThemTaiKhoanNV.setBounds(397, 23, 145, 21);
        btnThemTaiKhoanNV.setIcon(new ImageIcon("src/icon/check2_16.png"));
        controlPanel.setLayout(null);

        controlPanel.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(45, 47, 132, 21);
        btnXoa.setIcon(new ImageIcon("src/icon/trash2_16.png"));

        lbShowMessages = new JLabel("");
        lbShowMessages.setBounds(170, 61, 295, 37);
        controlPanel.add(lbShowMessages);
//        controlPanel.add(btnXoa);
        controlPanel.add(btnCapNhat);
        controlPanel.add(btnLamLai);
//        controlPanel.add(btnThemTaiKhoanNV);
//        getContentPane().add(controlPanel);

        btnTim.addActionListener(this);
        btnXemTatCa.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamLai.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnThemTaiKhoanNV.addActionListener(this);
        tblHoaDon.addMouseListener(this);
        tblDV.addMouseListener(this);

    }

    @SneakyThrows
    private void loadDataHoaDon() {
    	dsHD =  hoadondao.getAll();
    }
    @SneakyThrows
    private void loadDataCTHD() {
    	dsCTHD = (ArrayList<CTHoaDon>) cthddao.getAll();
    }
    private void DocDuLieuSPVaoTable() {
//    	dsCT = cthddao.getCTHoaDonDetails();
    	tableModelSP.setRowCount(0);
    	 for (CTHoaDon detail : dsCT) {
    		  DecimalFormat df = new DecimalFormat("#,###.##");
  	        String giaSP = df.format(detail.getSanPham().getGiaSP());
    		 tableModelSP.addRow(new Object[]{
                     detail.getSanPham().getMaSP(), 
                     detail.getSanPham().getTenSP(),
                     detail.getSoLuongSP(), 
                     giaSP
             });
         }
    }
    
    private void DocDuLieuDVVaoTable() {
//    	dsCT = cthddao.getCTHoaDonDetails();
    	tableModelDV.setRowCount(0);
      
    	 for (CTHoaDon detail : dsCT) {
    		  DecimalFormat df = new DecimalFormat("#,###.##");
    	        String giaDV = df.format(detail.getDichVu().getGiaDV());
    		 tableModelDV.addRow(new Object[]{
    				 
    				 
                     detail.getDichVu().getMaDV(), 
                     detail.getDichVu().getTenDV(),
                     detail.getSoLuongDV(), 
                     giaDV
             });
         }
    }
    
    
    
    private void DocDuLieuHoaDonVaoTable() {
    	   if (dsHD == null || dsHD.size() <= 0)
               return;
           for (HoaDon item: dsHD) {
               double tienDV = item.getTienDichVu();
               double tienKM = item.getTienKhuyenMai();
               double tienPhat = item.getTienPhat();
               double tienPhong = item.getTienPhong();
               double tienSanPham = item.getTienSanPham();
               double tongTien = item.getTongTien();
               String ngaylaphoadon = formatDate(item.getThoiGianLapHD());
               
   
               DecimalFormat df = new DecimalFormat("#,###.##");
               String tiendv = df.format(tienDV);
               String tienkm = df.format(tienKM);
               String tienphat = df.format(tienPhat);
               String tienphong = df.format(tienPhong);
               String tiensp = df.format(tienSanPham);
               String tongtien = df.format(tongTien);
               tableModelHoaDon.addRow(new Object[] {
                   item.getMaHD(), item.getKhachHang().getMaKH(), item.getPhong().getMaPhong(), item.getNhanVien().getMaNV(), item.getDonDatPhong().getMaDDP(), item.getKhuyenMai().getMaKM(), ngaylaphoadon, 
                   tienphong,tienphat,tienkm, tiendv,tiensp,tongtien
               });
           }
    }
    
    private void DocDuLieuCTHDVaoTable() {
    	
    	tableModelCTHD.setRowCount(0);
 	   if (dsCTHD == null || dsCTHD.size() <= 0)
            return;

        for (CTHoaDon item: dsCTHD) {
            double tienDV = item.getTongTienDV();
            double tienSanPham = item.getTongTienSP();

            

            DecimalFormat df = new DecimalFormat("#,###.##");
            String tiendv = df.format(tienDV);
            String tiensp = df.format(tienSanPham);
            tableModelCTHD.addRow(new Object[] {
                item.getMaCTHD(), item.getHoaDon().getMaHD(), item.getSanPham().getMaSP(), item.getDichVu().getMaDV(), item.getSoLuongSP(), item.getSoLuongSP(), 
                tiendv,tiensp
            });
        }
 }
    
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        return sdf.format(date);
    }

    private void showMessage(String message, JTextField txt) {
	 	showMessage(message, ERROR);
        txt.requestFocus();
        txt.selectAll();

    }
    private void showMessage(String message, int type) {
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
    private boolean validDataTim() {
        String maHD = txtTim.getText().trim();
        if (!(maHD.length() > 0)) {
            showMessage("Lỗi: Mã hóa đơn không được để trống", txtTim);
            return false;
        }
        return true;
    }
    
    @SneakyThrows
    public void inHoaDon(String maHD) {
        HoaDon hoaDon = hoadondao.getHoaDonTheoMa(maHD);
//        HoaDon.getInstance().setMaHD(maHD);
//        HoaDon.getInstance().setNhanVien(hoaDon.getNhanVien());
        if (hoaDon != null) {
            InHoaDon_GUI inHoaDonGUI = new InHoaDon_GUI(hoaDon);
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable(inHoaDonGUI);

            try {
                if (printerJob.printDialog()) {
                    printerJob.print();
                }
            } catch (PrinterException e) {
                System.err.println("In lỗi: " + e.getMessage());
            }
        } else {
            System.out.println("Hóa đơn không tồn tại với mã: " + maHD);
        }
    }
	@SneakyThrows
    @Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource().equals(tblHoaDon)) {
			int row = tblHoaDon.getSelectedRow();
		
			String maHD  = tableModelHoaDon.getValueAt(row, 0).toString();
			
			txtGiaSP.setText(tableModelHoaDon.getValueAt(row, 11).toString());
			txtGiaDV.setText(tableModelHoaDon.getValueAt(row, 10).toString());
			txtTongGiaHD.setText(tableModelHoaDon.getValueAt(row, 12).toString());
			
		

			dsCT = (ArrayList<CTHoaDon>) cthddao.getListCTHoaDonByMaHD(maHD);
			
			DocDuLieuDVVaoTable();
			DocDuLieuSPVaoTable();
//			nHoaDon(maHD);
		}


	
		
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

	@SneakyThrows
    @Override
	public void actionPerformed(ActionEvent e) {
		  if (e.getSource().equals(btnTim)) {
	            showMessage("", 2);
	            if (validDataTim()) {
	                String maHD = txtTim.getText().trim();
	                tableModelHoaDon.getDataVector().removeAllElements();
	                tableModelHoaDon.fireTableDataChanged();
	                dsHD = (List<HoaDon>) hoadondao.getHoaDonTheoMa(maHD);
	                if (dsHD == null || dsHD.size() <= 0) {
	                    showMessage("Không tìm thấy hóa đơn", ERROR);
	                } else
	                    DocDuLieuHoaDonVaoTable();
	            }
	        }
		  
		  if (e.getSource().equals(btnXemTatCa)) {
			  tableModelHoaDon.getDataVector().removeAllElements();
	            loadDataHoaDon();
	            loadDataCTHD();
	            DocDuLieuHoaDonVaoTable();
	        }

		
	}
}
