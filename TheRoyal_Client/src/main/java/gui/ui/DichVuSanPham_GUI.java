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

import gui.format_ui.TableActionCellRender;

import dao.*;
import entity.*;
import gui.dialog.ThongTinPhong_Dialog;
import gui.event.TableActionCellEditor;
import gui.event.TableActionEvent;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DichVuSanPham_GUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tbl_DichVu, tbl_SanPham, tbl_SPDV;
	private DefaultTableModel tableModelDV, tableModelSP, tableModelSPDV;

	private HoaDonService hoaDonService;
	private PhongService phongService;
	private DonDatPhongService donDatPhongService;
	private CTHoaDonService ctHoaDonService;
	private SanPhamService sanPhamService;
	private DichVuService dichVuService;



	private int currentSL;
	private ArrayList<SanPham> dsSP;
	private ArrayList<DichVu> dsDV;
	private ArrayList<DonDatPhong> dsDDP;
	private ArrayList<HoaDon> dsHD;
	private ArrayList<Phong> dsPhong;
	private Phong phong;
	private DonDatPhong ddp;
	private DanhSachPhong_GUI danhSachPhong;
	private JButton btnXong;
	private JLabel tieuDeLabel;
	private ThongTinPhong_Dialog thongtinphong;

	public DichVuSanPham_GUI(Phong phong, DonDatPhong ddp, DanhSachPhong_GUI danhSachPhong, JDialog owner, boolean modal) {
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

		// Initialize services from RMI client
		hoaDonService = RMIClient.getInstance().getHoaDonService();
		phongService = RMIClient.getInstance().getPhongService();
		donDatPhongService = RMIClient.getInstance().getDonDatPhongService();
		dichVuService = RMIClient.getInstance().getDichVuService();
		sanPhamService = RMIClient.getInstance().getSanPhamService();
		ctHoaDonService = RMIClient.getInstance().getCtHoaDonService();

		// Initialize DAOs


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
			@SneakyThrows
			@Override
			public void tangSoLuong(int row) {
				currentSL = (int) tableModelSPDV.getValueAt(row, 2);
				String itemIdOrName = (String) tableModelSPDV.getValueAt(row, 0);
				double gia;
				HoaDon maHD = hoaDonService.getHoaDonTheoPhong(phong.getMaPhong());
				double[] tongTien = hoaDonService.getTongTienSanPhamDichVu(maHD.getMaHD());
				double tongTienSP = tongTien[0];
				double tongTienDV = tongTien[1];
				if (isSanPham(itemIdOrName)) {
					SanPham mh = sanPhamService.getSanPhamTheoMaHoacTen(itemIdOrName);
					gia = mh.getGiaSP();
					if (mh.getSoLuongSP() <= 0) {
						JOptionPane.showMessageDialog(null, "Sản phẩm " + mh.getTenSP() + " đã hết hàng!", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					CTHoaDon ctHoaDon = ctHoaDonService.getCTHoaDonByMaSP(mh.getMaSP(), maHD.getMaHD());
					if (mh.getSoLuongSP() > 0  && ctHoaDon != null) {
						mh.setSoLuongSP(mh.getSoLuongSP() - 1);
						sanPhamService.updateSLSP(mh, mh.getSoLuongSP());

						for (int i = 0; i < tableModelSP.getRowCount(); i++) {
							if (tableModelSP.getValueAt(i, 0).equals(mh.getMaSP())) {
								tableModelSP.setValueAt(mh.getSoLuongSP(), i, 3);
								break;
							}
						}

						ctHoaDon.setSoLuongSP(currentSL + 1);
						ctHoaDon.setTongTienSP((currentSL + 1) * gia);
						ctHoaDonService.updateSLSP(ctHoaDon);

						tongTienSP += gia;
					}
				} else {
					DichVu dv = (DichVu) dichVuService.getDichVuByMaHoacTen(itemIdOrName);

					if (dv.getSoLuongDV() <= 0) {
						JOptionPane.showMessageDialog(null, "Dịch vụ " + dv.getTenDV() + " đã hết hàng!", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					gia = dv.getGiaDV();
					CTHoaDon ctHoaDon = ctHoaDonService.getCTHoaDonByMaDV(dv.getMaDV(), maHD.getMaHD());
					if (dv.getSoLuongDV() > 0 && ctHoaDon != null) {
						dv.setSoLuongDV(dv.getSoLuongDV() - 1);
						dichVuService.updateSLDV(dv, dv.getSoLuongDV());

						for (int i = 0; i < tableModelDV.getRowCount(); i++) {
							if (tableModelDV.getValueAt(i, 0).equals(dv.getMaDV())) {
								tableModelDV.setValueAt(dv.getSoLuongDV(), i, 3);
								break;
							}
						}


						ctHoaDon.setSoLuongDV(currentSL + 1);
						ctHoaDon.setTongTienDV((currentSL + 1) * gia);
						ctHoaDonService.updateSLDV(ctHoaDon);

						tongTienDV += gia;
					}
				}

				hoaDonService.updateTongTienHoaDon(maHD, tongTienSP, tongTienDV);
				tableModelSPDV.setValueAt(currentSL + 1, row, 2);
				double newTotalPrice = (currentSL + 1) * gia;
				DecimalFormat df = new DecimalFormat("#,###.##");
				String donGia = df.format(newTotalPrice);
				// tableModelSPDV.setValueAt(donGia, row, 3);
			}

			@SneakyThrows
			@Override
			public void giamSoLuong(int row) {
				currentSL = (int) tableModelSPDV.getValueAt(row, 2);
				String itemIdOrName = (String) tableModelSPDV.getValueAt(row, 0);
				HoaDon maHD = hoaDonService.getHoaDonTheoPhong(phong.getMaPhong());
				double gia;

				double[] tongTien = hoaDonService.getTongTienSanPhamDichVu(maHD.getMaHD());
				double tongTienSP = tongTien[0];
				double tongTienDV = tongTien[1];

				if (isSanPham(itemIdOrName)) {
					SanPham mh = sanPhamService.getSanPhamTheoMaHoacTen(itemIdOrName);
					gia = mh.getGiaSP();

					mh.setSoLuongSP(mh.getSoLuongSP() + 1);
					sanPhamService.updateSLSP(mh, mh.getSoLuongSP());

					for (int i = 0; i < tableModelSP.getRowCount(); i++) {
						if (tableModelSP.getValueAt(i, 0).equals(mh.getMaSP())) {
							tableModelSP.setValueAt(mh.getSoLuongSP(), i, 3);
							break;
						}
					}

					CTHoaDon ctHoaDon = ctHoaDonService.getCTHoaDonByMaSP(mh.getMaSP(), maHD.getMaHD());
					if (ctHoaDon != null && currentSL > 1) {
						ctHoaDon.setSoLuongSP(currentSL - 1);
						ctHoaDon.setTongTienSP((currentSL - 1) * gia);
						ctHoaDonService.updateSLSP(ctHoaDon);

						tongTienSP -= gia;
					}

				} else {
					DichVu dv = (DichVu) dichVuService.getDichVuByMaHoacTen(itemIdOrName);
					gia = dv.getGiaDV();

					dv.setSoLuongDV(dv.getSoLuongDV() + 1);
					dichVuService.updateSLDV(dv, dv.getSoLuongDV());

					for (int i = 0; i < tableModelDV.getRowCount(); i++) {
						if (tableModelDV.getValueAt(i, 0).equals(dv.getMaDV())) {
							tableModelDV.setValueAt(dv.getSoLuongDV(), i, 3);
							break;
						}
					}

					CTHoaDon ctHoaDon = ctHoaDonService.getCTHoaDonByMaDV(dv.getMaDV(), maHD.getMaHD());
					if (ctHoaDon != null && currentSL > 1) {
						ctHoaDon.setSoLuongDV(currentSL - 1);
						ctHoaDon.setTongTienDV((currentSL - 1) * gia);
						ctHoaDonService.updateSLDV(ctHoaDon);

						tongTienDV -= gia;
					}
				}

				hoaDonService.updateTongTienHoaDon(maHD, tongTienSP, tongTienDV);

				if (currentSL > 1) {
					tableModelSPDV.setValueAt(currentSL - 1, row, 2);
					double newTotalPrice = (currentSL - 1) * gia;
					DecimalFormat df = new DecimalFormat("#,###.##");
					String donGia = df.format(newTotalPrice);
					// tableModelSPDV.setValueAt(donGia, row, 3);
				} else {
					tableModelSPDV.removeRow(row);
				}
			}

			@SneakyThrows
			private boolean isSanPham(String itemIdOrName) {
				return sanPhamService.getSanPhamTheoMaHoacTen(itemIdOrName) != null;
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

	@SneakyThrows
	private void loadData() {
		dsHD = new ArrayList<>(hoaDonService.getAll());
		dsPhong = new ArrayList<>(phongService.getAll());
		dsDDP = new ArrayList<>(donDatPhongService.getAll());
		dsDV = new ArrayList<>(dichVuService.getAll());
		dsSP = new ArrayList<>(sanPhamService.getAll());

		loadDichVuTable();
		loadSanPhamTable();
		loadCTHoaDonToTable(phong);
	}

	private void loadDichVuTable() {
		tableModelDV.setRowCount(0); // Clear existing rows
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
		tableModelSP.setRowCount(0); // Clear existing rows
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

	@SneakyThrows
	private DichVu getSelectedDichVu(int row) {
		String maDV = (String) tbl_DichVu.getValueAt(row, 0);
		return (DichVu) dichVuService.getDichVuByMaHoacTen(maDV);
	}

	@SneakyThrows
	private SanPham getSelectedSanPham(int row) {
		String maSP = (String) tableModelSP.getValueAt(row, 0);
		return sanPhamService.getSanPhamTheoMaHoacTen(maSP);
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

	@SneakyThrows
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
			tableModelSPDV.setValueAt(df.format(newTotalPrice), rowIndex, 3);

		} else {
			tableModelSPDV.addRow(new Object[] { dv.getMaDV(), dv.getTenDV(), 1, donGia });
		}

		dv.setSoLuongDV(dv.getSoLuongDV() - 1);
		dichVuService.updateSLDV(dv, dv.getSoLuongDV());

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

		// Update HoaDon if necessary
		HoaDon hoaDon = hoaDonService.getHoaDonTheoPhong(phong.getMaPhong());
		if (hoaDon != null) {
			CTHoaDon ctHoaDon = ctHoaDonService.getCTHoaDonByMaDV(dv.getMaDV(), hoaDon.getMaHD());
			if (ctHoaDon == null) {
				// Create new CTHoaDon for this DichVu
				String maCTD = ctHoaDonService.getLatestID();
				ctHoaDon = new CTHoaDon(maCTD, hoaDon, null, dv, 0, 1, 0, gia);
				ctHoaDonService.save(ctHoaDon);
			} else {
				// Update existing CTHoaDon
				ctHoaDon.setSoLuongDV(ctHoaDon.getSoLuongDV() + 1);
				ctHoaDon.setTongTienDV(ctHoaDon.getSoLuongDV() * gia);
				ctHoaDonService.updateSLDV(ctHoaDon);
			}

			double[] tongTien = hoaDonService.getTongTienSanPhamDichVu(hoaDon.getMaHD());
			hoaDonService.updateTongTienHoaDon(hoaDon, tongTien[0], tongTien[1] + gia);
		}
	}

	@SneakyThrows
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
			tableModelSPDV.setValueAt(df.format(newTotalPrice), rowIndex, 3);
		} else {
			tableModelSPDV.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), 1, donGia });
		}

		sp.setSoLuongSP(sp.getSoLuongSP() - 1);
		sanPhamService.updateSLSP(sp, sp.getSoLuongSP());

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

		// Update HoaDon if necessary
		HoaDon hoaDon = hoaDonService.getHoaDonTheoPhong(phong.getMaPhong());
		if (hoaDon != null) {
			CTHoaDon ctHoaDon = ctHoaDonService.getCTHoaDonByMaSP(sp.getMaSP(), hoaDon.getMaHD());
			if (ctHoaDon == null) {
				// Create new CTHoaDon for this SanPham
				String maCTD = ctHoaDonService.getLatestID();
				ctHoaDon = new CTHoaDon(maCTD, hoaDon, sp, null, 1, 0, gia, 0);
				ctHoaDonService.save(ctHoaDon);
			} else {
				// Update existing CTHoaDon
				ctHoaDon.setSoLuongSP(ctHoaDon.getSoLuongSP() + 1);
				ctHoaDon.setTongTienSP(ctHoaDon.getSoLuongSP() * gia);
				ctHoaDonService.updateSLSP(ctHoaDon);
			}

			double[] tongTien = hoaDonService.getTongTienSanPhamDichVu(hoaDon.getMaHD());
			hoaDonService.updateTongTienHoaDon(hoaDon, tongTien[0] + gia, tongTien[1]);
		}
	}

	@SneakyThrows
	public ArrayList<CTHoaDon> createCTHoaDonFromTable(HoaDon maHD) {
		ArrayList<CTHoaDon> listCTHoaDon = new ArrayList<>();

		for (int i = 0; i < tableModelSPDV.getRowCount(); i++) {
			String maSP = (String) tableModelSPDV.getValueAt(i, 0);
			String tenSP = (String) tableModelSPDV.getValueAt(i, 1);
			int soLuong = (int) tableModelSPDV.getValueAt(i, 2);

			// Handle potential format issues with price values
			String valueAtStr = tableModelSPDV.getValueAt(i, 3).toString();
			// Remove commas and other non-numeric characters except decimal point
			String cleanValue = valueAtStr.replaceAll("[^0-9.]", "");
			double tongTien = Double.parseDouble(cleanValue);

			SanPham sp = sanPhamService.getSanPhamTheoMaHoacTen(maSP);
			DichVu dv = dichVuService.getDichVuByMaHoacTen(maSP).stream().findFirst().orElse(null);
			String maCTD = ctHoaDonService.getLatestID();

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
		try {
			HoaDon hoaDon = hoaDonService.getHoaDonTheoPhong(phong.getMaPhong());
			tableModelSPDV.setRowCount(0);
			List<CTHoaDon> listCTHD = ctHoaDonService.getListCTHoaDonByMaHD(hoaDon.getMaHD());
			DecimalFormat df = new DecimalFormat("#,###.##");

			for (CTHoaDon ctHoaDon : listCTHD) {
				Object[] row = new Object[6];
				row[0] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getSanPham().getMaSP()
						: (ctHoaDon.getDichVu() != null ? ctHoaDon.getDichVu().getMaDV() : "");
				row[1] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getSanPham().getTenSP()
						: (ctHoaDon.getDichVu() != null ? ctHoaDon.getDichVu().getTenDV() : "");
				row[2] = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getSoLuongSP() : ctHoaDon.getSoLuongDV();

				double unitPrice = (ctHoaDon.getSanPham() != null)
						? ctHoaDon.getTongTienSP() / ctHoaDon.getSoLuongSP()
						: (ctHoaDon.getDichVu() != null ? ctHoaDon.getTongTienDV() / ctHoaDon.getSoLuongDV() : 0);
				row[3] = df.format(unitPrice);

				double totalPrice = (ctHoaDon.getSanPham() != null) ? ctHoaDon.getTongTienSP()
						: ctHoaDon.getTongTienDV();
				row[4] = df.format(totalPrice);
				row[5] = "";
				tableModelSPDV.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void xong(Phong phong) {
		try {
			HoaDon hoaDon = hoaDonService.getHoaDonTheoPhong(phong.getMaPhong());
			if (hoaDon == null) {
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy hóa đơn cho phòng: " + phong.getMaPhong(), "Thông báo",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			ArrayList<CTHoaDon> listCTHoaDon = createCTHoaDonFromTable(hoaDon);
			for (CTHoaDon cthd : listCTHoaDon) {
				boolean exists = ctHoaDonService.checkIfCTHoaDonExists(cthd);
				if (!exists) {
					boolean kq = ctHoaDonService.save(cthd);
					if (!kq) {
						JOptionPane.showMessageDialog(null,
								"Không thể thêm chi tiết hóa đơn cho sản phẩm/dịch vụ: "
										+ (cthd.getSanPham() != null ? cthd.getSanPham().getTenSP()
										: "Dịch vụ không xác định"),
								"Thông báo", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			double[] tongTien = hoaDonService.getTongTienSanPhamDichVu(hoaDon.getMaHD());
			boolean updateSuccess = hoaDonService.updateTongTienHoaDon(hoaDon, tongTien[0], tongTien[1]);
			if (!updateSuccess) {
				JOptionPane.showMessageDialog(null, "Không thể cập nhật tổng tiền vào hóa đơn!", "Thông báo",
						JOptionPane.ERROR_MESSAGE);
			}

			JOptionPane.showMessageDialog(null, "Đã thêm vào chi tiết hóa đơn thành công!", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
			thongtinphong.dis();
			dispose();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi xử lý hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}
}