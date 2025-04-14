package gui.ui;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.common.base.Objects;

import Format_UI.TableActionCellRender;
import connectDB.ConnectDB;
import dao.*;
import entity.*;
import gui.dialog.ThongTinPhong_Dialog;
import gui.event.TableActionCellEditor;
import gui.event.TableActionEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DichVuSanPham_GUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tbl_DichVu, tbl_SanPham, tbl_SPDV;
	private DefaultTableModel tableModelDV, tableModelSP, tableModelSPDV;
	private HoaDonDAO hoadondao;
	private PhongDAO phongdao;
	private DonDatPhongDAO dondatphongdao;
	private CTDonDatPhongDAO ctdondatphongdao;
	private SanPhamDAO sanphamdao;
	private DichVuDAO dichvudao;
	private CTHoaDonDAO cthoadondao;
	private int currentSL;
	private double gia;
	private ArrayList<SanPham> dsSP;
	private ArrayList<DichVu> dsDV;
	private ArrayList<DonDatPhong> dsDDP;
	private ArrayList<HoaDon> dsHD;
	private ArrayList<Phong> dsPhong;
	private ArrayList<CTDonDatPhong> dsCTDDP;
	private ArrayList<CTHoaDon> dsCTHD;
	private  DonDatPhong ddp;
	private  DanhSachPhong_GUI danhSachPhong;
	private JButton btnXong;
	private   Phong phong;
	private JLabel tieuDeLabel;
	private ThongTinPhong_Dialog thongtinphong;

//	public static void main(String[] args) {
//		new DichVuSanPham_GUI(phong, danhSachPhong).setVisible(true);
//
//	}

	public DichVuSanPham_GUI(Phong phong, DonDatPhong ddp,DanhSachPhong_GUI danhSachPhong , JDialog owner , boolean modal) {
		super(owner, modal);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		 tieuDeLabel = new JLabel("Sản phẩm / Dịch vụ");
		 tieuDeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        tieuDeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
	        tieuDeLabel.setForeground(new Color(246, 167, 193));
	        tieuDeLabel.setBounds(-10, 10, 1149, 30);
	        getContentPane().add(tieuDeLabel);
		this.phong = phong;
		this.danhSachPhong = danhSachPhong;
		this.ddp = ddp;
		thongtinphong = (ThongTinPhong_Dialog) owner;
		setTitle("Thêm sản phẩm, dịch vụ");
		hoadondao = new HoaDonDAO();
		phongdao = new PhongDAO();
		dondatphongdao = new DonDatPhongDAO();
		dichvudao = new DichVuDAO();
		sanphamdao = new SanPhamDAO();
		ctdondatphongdao = new CTDonDatPhongDAO();
		cthoadondao = new CTHoaDonDAO();

		setBounds(100, 100, 1163, 661);
		initComponents();
		loadData();
	}

	private void initComponents() {
		JPanel tabelPanel = new JPanel();
		tabelPanel.setBounds(10, 57, 483, 487);
		tabelPanel.setBackground(Color.white);
		tabelPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Cập nhật sản phẩm dịch vụ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

		// Table for DichVu
		String[] columnsDV = { "Mã dịch vụ", "Tên dịch vụ", "Đơn giá", "Số lượng" };
		tableModelDV = new DefaultTableModel(columnsDV, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbl_DichVu = new JTable(tableModelDV);
		tbl_DichVu.setBackground(Color.white);
		tbl_DichVu.setFillsViewportHeight(true);
		tbl_DichVu.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tbl_DichVu.getSelectedRow();
				if (selectedRow != -1) {
					DichVu chondichvu = getSelectedDichVu(selectedRow);
					cthoadondao = new CTHoaDonDAO();
					addToDVThemTable(chondichvu);

				}
			}
		});

		JScrollPane scrollPaneDV = new JScrollPane(tbl_DichVu);
		scrollPaneDV.setBounds(26, 280, 432, 197);

		// Table for SanPham
		String[] columnsSP = { "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng" };
		tableModelSP = new DefaultTableModel(columnsSP, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbl_SanPham = new JTable(tableModelSP);
		tbl_SanPham.setBackground(Color.white);
		tbl_SanPham.setFillsViewportHeight(true);
		tbl_SanPham.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tbl_SanPham.getSelectedRow();
				if (selectedRow != -1) {
					SanPham chonsanpham = getSelectedSanPham(selectedRow);
					cthoadondao = new CTHoaDonDAO();
					addToSPThemTable(chonsanpham);

				}
			}
		});

		JScrollPane scrollPaneSP = new JScrollPane(tbl_SanPham);
		scrollPaneSP.setBounds(26, 46, 432, 197);

		getContentPane().setLayout(null);
		tabelPanel.setLayout(null);
		tabelPanel.add(scrollPaneDV);
		tabelPanel.add(scrollPaneSP);
		getContentPane().add(tabelPanel);

		JPanel tabelPanelInfo = new JPanel();
		tabelPanelInfo.setLayout(null);
		tabelPanelInfo
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Danh sách sản phẩm, dịch vụ đã thêm", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));

		String[] columnsSPDV = { "Mã ", "Tên ", "Số lượng", "Đơn giá", "Hành động" };
		tableModelSPDV = new DefaultTableModel(columnsSPDV, 0);
		tbl_SPDV = new JTable(tableModelSPDV);
		tbl_SPDV.setBackground(Color.white);
		tbl_SPDV.setFillsViewportHeight(true);

		JScrollPane scrollPaneSPDV = new JScrollPane(tbl_SPDV);
		scrollPaneSPDV.setBounds(16, 40, 585, 437);

		tabelPanelInfo.add(scrollPaneSPDV);
		TableActionEvent event = new TableActionEvent() {
			@Override
			public void tangSoLuong(int row) {
				currentSL = (int) tableModelSPDV.getValueAt(row, 2);
				String itemIdOrName = (String) tableModelSPDV.getValueAt(row, 0);
				double gia;
				 HoaDon maHD = hoadondao.getHoaDonTheoPhong(phong.getMaPhong());
				double[] tongTien = hoadondao.getTongTienSanPhamDichVu(maHD.getMaHD());
                double tongTienSP = tongTien[0];
                double tongTienDV = tongTien[1];
				if (isSanPham(itemIdOrName)) {
					sanphamdao = new SanPhamDAO();
					SanPham mh = sanphamdao.getSanPhamTheoMaHoacTen(itemIdOrName);
					gia = mh.getGiaSP();
					if (mh.getSoLuongSP() <= 0) {
						JOptionPane.showMessageDialog(null, "Sản phẩm " + mh.getTenSP() + " đã hết hàng!", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					CTHoaDon ctHoaDon = cthoadondao.getCTHoaDonByMaSP(mh.getMaSP(),maHD.getMaHD());
					if (mh.getSoLuongSP() > 0  && ctHoaDon !=null) {
						mh.setSoLuongSP(mh.getSoLuongSP() - 1);
						sanphamdao.updateSLSP(mh, mh.getSoLuongSP());

						for (int i = 0; i < tableModelSP.getRowCount(); i++) {
							if (tableModelSP.getValueAt(i, 0).equals(mh.getMaSP())) {
								tableModelSP.setValueAt(mh.getSoLuongSP(), i, 3);
								break;
							}
						}
						
						
						
	                    ctHoaDon.setSoLuongSP(currentSL + 1);
	                    ctHoaDon.setTongTienSP((currentSL + 1) * gia);
	                    cthoadondao.updateSLSP(ctHoaDon);

	                    
	                    tongTienSP += gia;
	                    
	                    
					}
					
						
				} else {
					dichvudao = new DichVuDAO();
					DichVu dv = dichvudao.timDichVuTheoMaHoacTheoTen(itemIdOrName);

					if (dv.getSoLuongDV() <= 0) {
						JOptionPane.showMessageDialog(null, "Dịch vụ " + dv.getTenDV() + " đã hết hàng!", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					gia = dv.getGiaDV();
					CTHoaDon ctHoaDon = cthoadondao.getCTHoaDonByMaDV(dv.getMaDV(), maHD.getMaHD());
					if (dv.getSoLuongDV() > 0 && ctHoaDon != null) {
						dv.setSoLuongDV(dv.getSoLuongDV() - 1);
						dichvudao.updateSLDV(dv, dv.getSoLuongDV());

						for (int i = 0; i < tableModelDV.getRowCount(); i++) {
							if (tableModelDV.getValueAt(i, 0).equals(dv.getMaDV())) {
								tableModelDV.setValueAt(dv.getSoLuongDV(), i, 3);
								break;
							}
						}
						
					
		                ctHoaDon.setSoLuongDV(currentSL + 1);
		                ctHoaDon.setTongTienDV((currentSL + 1) * gia);
		                cthoadondao.updateSLDV(ctHoaDon);

		                tongTienDV += gia;
					}
					
					
			
	                
				}
		
                hoadondao.updateTongTienHoaDon(maHD, tongTienSP, tongTienDV);
				tableModelSPDV.setValueAt(currentSL + 1, row, 2);
				double newTotalPrice = (currentSL + 1) * gia;
				DecimalFormat df = new DecimalFormat("#,###.##");
				String donGia = df.format(newTotalPrice);
//				tableModelSPDV.setValueAt(donGia, row, 3);
			}

			@Override
			public void giamSoLuong(int row) {
			    currentSL = (int) tableModelSPDV.getValueAt(row, 2);
			    String itemIdOrName = (String) tableModelSPDV.getValueAt(row, 0);
			    HoaDon maHD = hoadondao.getHoaDonTheoPhong(phong.getMaPhong());
			    double gia;

			    double[] tongTien = hoadondao.getTongTienSanPhamDichVu(maHD.getMaHD());
			    double tongTienSP = tongTien[0];
			    double tongTienDV = tongTien[1];
			    
			    if (isSanPham(itemIdOrName)) {
			        sanphamdao = new SanPhamDAO();
			        SanPham mh = sanphamdao.getSanPhamTheoMaHoacTen(itemIdOrName);
			        gia = mh.getGiaSP();

			        mh.setSoLuongSP(mh.getSoLuongSP() + 1);
			        sanphamdao.updateSLSP(mh, mh.getSoLuongSP());

			        for (int i = 0; i < tableModelSP.getRowCount(); i++) {
			            if (tableModelSP.getValueAt(i, 0).equals(mh.getMaSP())) {
			                tableModelSP.setValueAt(mh.getSoLuongSP(), i, 3);
			                break;
			            }
			        }

			        CTHoaDon ctHoaDon = cthoadondao.getCTHoaDonByMaSP(mh.getMaSP(),maHD.getMaHD());
			        if (ctHoaDon != null && currentSL > 1) { 
			            ctHoaDon.setSoLuongSP(currentSL - 1);
			            ctHoaDon.setTongTienSP((currentSL - 1) * gia);
			            cthoadondao.updateSLSP(ctHoaDon);

			            tongTienSP -= gia;
			        }

			    } else {
			        dichvudao = new DichVuDAO();
			        DichVu dv = dichvudao.timDichVuTheoMaHoacTheoTen(itemIdOrName);
			        gia = dv.getGiaDV();

			        dv.setSoLuongDV(dv.getSoLuongDV() + 1);
			        dichvudao.updateSLDV(dv, dv.getSoLuongDV());

			        for (int i = 0; i < tableModelDV.getRowCount(); i++) {
			            if (tableModelDV.getValueAt(i, 0).equals(dv.getMaDV())) {
			                tableModelDV.setValueAt(dv.getSoLuongDV(), i, 3);
			                break;
			            }
			        }

			        CTHoaDon ctHoaDon = cthoadondao.getCTHoaDonByMaDV(dv.getMaDV(),maHD.getMaHD());
			        if (ctHoaDon != null && currentSL > 1) { 
			            ctHoaDon.setSoLuongDV(currentSL - 1);
			            ctHoaDon.setTongTienDV((currentSL - 1) * gia);
			            cthoadondao.updateSLDV(ctHoaDon);

			            tongTienDV -= gia;
			        }
			    }

			    hoadondao.updateTongTienHoaDon(maHD, tongTienSP, tongTienDV);

			    if (currentSL > 1) {
			        tableModelSPDV.setValueAt(currentSL - 1, row, 2);
			        double newTotalPrice = (currentSL - 1) * gia;
			        DecimalFormat df = new DecimalFormat("#,###.##");
			        String donGia = df.format(newTotalPrice);
//			        tableModelSPDV.setValueAt(donGia, row, 3);
			    } else {
			        tableModelSPDV.removeRow(row);
			    }
			}


			private boolean isSanPham(String itemIdOrName) {
				sanphamdao = new SanPhamDAO();
				return sanphamdao.getSanPhamTheoMaHoacTen(itemIdOrName) != null;
			}
		};

		// Cài đặt một sự kiện duy nhất cho cell editor
		tbl_SPDV.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
		tbl_SPDV.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));

		tabelPanelInfo.setBackground(Color.WHITE);
		tabelPanelInfo.setBounds(522, 57, 617, 487);
		getContentPane().add(tabelPanelInfo);

		btnXong = new JButton("Hoàn thành");
		btnXong.setIcon(new ImageIcon(DichVuSanPham_GUI.class.getResource("/src/ICON/icon/check2_16.png")));
		btnXong.setBounds(939, 573, 130, 21);
		btnXong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				xong(phong);

			}
		});
		getContentPane().add(btnXong);
	}

	private void loadData() {
		dsHD = hoadondao.getListHoaDon();
		dsPhong = phongdao.getListPhong();
		dsDDP = dondatphongdao.getListDonDatPhong();
		dsDV = dichvudao.getDanhSachDichVu();
		dsSP = sanphamdao.getDanhSachSanPham();

		loadDichVuTable();
		loadSanPhamTable();
	 
		loadCTHoaDonToTable(phong);

	}

	private void loadDichVuTable() {
		if (dsDV == null || dsDV.isEmpty())
			return;
		for (DichVu dv : dsDV) {
			double number = dv.getGiaDV();
			DecimalFormat df = new DecimalFormat("#,###.##");
			String donGia = df.format(number);
			tableModelDV.addRow(new Object[] { dv.getMaDV(), dv.getTenDV(), donGia, dv.getSoLuongDV() });
		}
	}

	private void loadSanPhamTable() {
		if (dsSP == null || dsSP.isEmpty())
			return;
		for (SanPham sp : dsSP) {
			double number = sp.getGiaSP();
			DecimalFormat df = new DecimalFormat("#,###.##");
			String donGia = df.format(number);
			tableModelSP.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), donGia, sp.getSoLuongSP() });
		}
	}

	private String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}

	private DichVu getSelectedDichVu(int row) {
		String maDV = (String) tbl_DichVu.getValueAt(row, 0);
		return dichvudao.timDichVuTheoMaHoacTheoTen(maDV);
	}

	private SanPham getSelectedSanPham(int row) {
		String maSP = (String) tableModelSP.getValueAt(row, 0);
		return sanphamdao.getSanPhamTheoMaHoacTen(maSP);
	}

	private int findRowIndexByCode(String code) {
		for (int i = 0; i < tbl_SPDV.getRowCount(); i++) {
			String codeInDVThem = String.valueOf(tableModelSPDV.getValueAt(i, 0));
			if (Objects.equals(codeInDVThem, code)) {
				return i;
			}
		}
		return -1;
	}

	private void addToDVThemTable(DichVu dv) {
		String selectedCode = dv.getMaDV();
		int rowIndex = findRowIndexByCode(selectedCode);
		double gia = dv.getGiaDV();
		DecimalFormat df = new DecimalFormat("#,###.##");
		String donGia = df.format(gia);
		if (dv.getSoLuongDV() <= 0) {
			JOptionPane.showMessageDialog(null, "Dịch vụ " + dv.getTenDV() + " đã hết !", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (rowIndex != -1) {
			currentSL = (int) tableModelSPDV.getValueAt(rowIndex, 2);

			tableModelSPDV.setValueAt(currentSL + 1, rowIndex, 2);
			double newTotalPrice = (currentSL + 1) * gia;
			tableModelSPDV.setValueAt(newTotalPrice, rowIndex, 3);

		} else {
			tableModelSPDV.addRow(new Object[] { dv.getMaDV(), dv.getTenDV(), 1, donGia });
		}

		dv.setSoLuongDV(dv.getSoLuongDV() - 1);
		dichvudao.updateSLDV(dv, dv.getSoLuongDV());

		for (int i = 0; i < tableModelDV.getRowCount(); i++) {
			if (tableModelDV.getValueAt(i, 0).equals(dv.getMaDV())) {
				if (dv.getSoLuongDV() > 0) {
					tableModelDV.setValueAt(dv.getSoLuongDV(), i, 3);
				} else {
					tableModelDV.setValueAt("Hết hàng", i, 3);
				}
				break;
			}
		}
	}

	private void addToSPThemTable(SanPham sp) {
		String selectedCode = sp.getMaSP();
		int rowIndex = findRowIndexByCode(selectedCode);
		double gia = sp.getGiaSP();
		DecimalFormat df = new DecimalFormat("#,###.##");
		String donGia = df.format(gia);
		if (sp.getSoLuongSP() <= 0) {
			JOptionPane.showMessageDialog(null, "Sản phẩm " + sp.getTenSP() + " đã hết!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (rowIndex != -1) {
			currentSL = (int) tableModelSPDV.getValueAt(rowIndex, 2);

			tableModelSPDV.setValueAt(currentSL + 1, rowIndex, 2);
			double newTotalPrice = (currentSL + 1) * gia;
			tableModelSPDV.setValueAt(newTotalPrice, rowIndex, 3);

		} else {
			tableModelSPDV.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), 1, donGia });
		}

		sp.setSoLuongSP(sp.getSoLuongSP() - 1);
		sanphamdao.updateSLSP(sp, sp.getSoLuongSP());

		for (int i = 0; i < tableModelSP.getRowCount(); i++) {
			if (tableModelSP.getValueAt(i, 0).equals(sp.getMaSP())) {
				if (sp.getSoLuongSP() > 0) {
					tableModelSP.setValueAt(sp.getSoLuongSP(), i, 3);
				} else {
					tableModelSP.setValueAt("Hết hàng", i, 3);
				}
				break;
			}
		}
	}

	public ArrayList<CTHoaDon> createCTHoaDonFromTable(HoaDon maHD) {
	    ArrayList<CTHoaDon> listCTHoaDon = new ArrayList<>();

	    for (int i = 0; i < tableModelSPDV.getRowCount(); i++) {
	        String maSP = (String) tableModelSPDV.getValueAt(i, 0);
	        String tenSP = (String) tableModelSPDV.getValueAt(i, 1);
	        int soLuong = (int) tableModelSPDV.getValueAt(i, 2);
	        String valueAt = tableModelSPDV.getValueAt(i, 3).toString().replace(",", "");
	        double tongTien = Double.parseDouble(valueAt);

	        SanPham sp = sanphamdao.getSanPhamTheoMaHoacTen(maSP);
	        DichVu dv = dichvudao.timDichVuTheoMaHoacTheoTen(maSP);
	        String maCTD = cthoadondao.getLatestID();

	        if (sp != null || dv != null) {
	            CTHoaDon cthd = new CTHoaDon(
	                maCTD,                
	                maHD,                 
	                sp,                   
	                dv,                 
	                sp != null ? soLuong : 0,  
	                dv != null ? soLuong : 0,  
	                sp != null ? tongTien : 0, 
	                dv != null ? tongTien : 0  
	            );
	            listCTHoaDon.add(cthd);
	        }
	    }
	    return listCTHoaDon;
	}
	 public void loadCTHoaDonToTable(Phong phong) {
	        HoaDonDAO hdDAO = new HoaDonDAO();
	        HoaDon hoaDon = hdDAO.getHoaDonTheoPhong(phong.getMaPhong());

	        tableModelSPDV.setRowCount(0);

	        CTHoaDonDAO ctHoaDonDao = new CTHoaDonDAO();
	        List<CTHoaDon> listCTHD = ctHoaDonDao.getCTHoaDonByMaHoaDon(hoaDon.getMaHD());

	        DecimalFormat df = new DecimalFormat("#,###.##");

	        for (CTHoaDon ctHoaDon : listCTHD) {
	            Object[] row = new Object[6];

	            row[0] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getSanPham().getMaSP() :
	                    (ctHoaDon.getDichVu() != null ? ctHoaDon.getDichVu().getMaDV() : "");
	            row[1] = (ctHoaDon.getSanPham() != null) ? 
	                     ctHoaDon.getSanPham().getTenSP() : 
	                     (ctHoaDon.getDichVu() != null ? ctHoaDon.getDichVu().getTenDV() : "");
	            row[2] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getSoLuongSP() : ctHoaDon.getSoLuongDV();

	            double unitPrice = (ctHoaDon.getSanPham() != null) ? 
	                    ctHoaDon.getTongTienSP() / ctHoaDon.getSoLuongSP() : 
	                    (ctHoaDon.getDichVu() != null ? ctHoaDon.getTongTienDV() / ctHoaDon.getSoLuongDV() : 0);
	            row[3] = df.format(unitPrice);

	            double totalPrice = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getTongTienSP() : ctHoaDon.getTongTienDV();
	            row[4] = df.format(totalPrice);

	            row[5] = ""; 

	            tableModelSPDV.addRow(row);
	        }
	    }


	public void xong(Phong phong) {
	    HoaDon hoaDon = hoadondao.getHoaDonTheoPhong(phong.getMaPhong());

	    if (hoaDon == null) {
	        JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn cho phòng: " + phong.getMaPhong(), "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    ArrayList<CTHoaDon> listCTHoaDon = createCTHoaDonFromTable(hoaDon);
	    for (CTHoaDon cthd : listCTHoaDon) {
	        boolean exists = cthoadondao.checkIfCTHoaDonExists(cthd);
	        if (!exists) {
	            boolean kq = cthoadondao.insertCTHoaDon(cthd);
	            if (!kq) {
	                JOptionPane.showMessageDialog(null, "Không thể thêm chi tiết hóa đơn cho sản phẩm/dịch vụ: " +
	                        (cthd.getSanPham() != null ? cthd.getSanPham().getTenSP() : "Dịch vụ không xác định"),
	                        "Thông báo", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }

	    double[] tongTien = hoadondao.getTongTienSanPhamDichVu(hoaDon.getMaHD());
	    double tongTienSP = tongTien[0];
	    double tongTienDV = tongTien[1];

	    boolean updateSuccess = hoadondao.updateTongTienHoaDon(hoaDon, tongTienSP, tongTienDV);
	    if (updateSuccess) {
//	        JOptionPane.showMessageDialog(null, "Tổng tiền hóa đơn đã được cập nhật!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(null, "Không thể cập nhật tổng tiền vào hóa đơn!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	    }

	    JOptionPane.showMessageDialog(null, "Đã thêm vào chi tiết hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    thongtinphong.dis();
	    dispose();
	    
	}



	

}
