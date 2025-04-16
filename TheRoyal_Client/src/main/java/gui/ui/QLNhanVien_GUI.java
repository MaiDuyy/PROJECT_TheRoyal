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
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import gui.format_ui.RadiusButton;
import gui.format_ui.Table;

import controller.TimNhanVien;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.TaiKhoan;
import formatdate.FormatDate;
import gui.component.ButtonCustom;
import gui.dialog.SuaNhanVien_Dialog;
import gui.dialog.ThemNhanVien_Dialog;
import gui.dialog.ThemTaiKhoan_Dialog;
import gui.swing.notification.Notification;
import gui.validata.BCrypt;

public class QLNhanVien_GUI extends JInternalFrame implements ActionListener, MouseListener, KeyListener {

	private JLabel tieuDeLabel;
	private JSeparator phanCach;
	private Table tblNhanVien, tblTaiKhoan;
	public static DefaultTableModel tableModelNV;
	public DefaultTableModel tableModelTK;
	public NhanVienDAO nhanviendao;
	public TaiKhoanDAO taikhoandao;
	private JButton btnThem, btnTim, btnXoa, btnCapNhat, btnLamLai, btnThemTaiKhoanNV;
	private JTextField  txtTim;
	private ButtonCustom btnHuyTim;
	private JComboBox<String> cbxLuachon;
	private DefaultComboBoxModel<String> modelLuaChon;
	private JLabel lbShowMessages;
	private final int SUCCESS = 1, ERROR = 0, ADD = 1, UPDATE = 2;
	public ThemNhanVien_Dialog nhanviendialog;

	public JFrame popup = new JFrame();
	private static ArrayList<NhanVien> dsNV;
	private ArrayList<TaiKhoan> dsTk;

	private static final long serialVersionUID = 1;
	private JTextField txtMaTK;
	private JTextField txtMatKhau;

	public QLNhanVien_GUI() {
		getContentPane().setBackground(new Color(255, 255, 255));
		nhanviendao = new NhanVienDAO();
		taikhoandao = new TaiKhoanDAO();
		nhanviendialog = new ThemNhanVien_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Nhãn tiêu đề
		tieuDeLabel = new JLabel("Quản lý Nhân Viên");
		tieuDeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		tieuDeLabel.setForeground(new Color(246, 167, 193));
		tieuDeLabel.setBounds(29, 10, 300, 30);
		getContentPane().add(tieuDeLabel);

		// Phân cách
		phanCach = new JSeparator();
		phanCach.setBounds(0, 50, screenSize.width, 10);
		getContentPane().add(phanCach);
		GuiNhanVien();
		ChucNang();
		setBackground(Color.white);
		setBounds(100, 100, 1346, 718);
//        setSize(1270, 710);
//        setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		lbShowMessages = new JLabel("");
		lbShowMessages.setBounds(1051, 10, 295, 37);
		getContentPane().add(lbShowMessages);
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

		loadListNhanVien();
		loadListTaiKhoan();
		DocDuLieuNVVaoTable();
		DocDuLieuTKVaoTable();

	}
	private void ChucNang()	{

		// Panel tìm kiếm
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(616, 51, 682, 96);
		searchPanel.setBackground(Color.white);
		searchPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Tìm kiếm", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
		

	        
	        modelLuaChon = new DefaultComboBoxModel<String>(new String [] {"Tất cả", "Căn cước công dân", "Tên đăng nhập", "Chức vụ"});
	        cbxLuachon = new JComboBox<String>(modelLuaChon);
	        cbxLuachon.setBounds(10, 34, 175, 29);
	        searchPanel.add(cbxLuachon);
		txtTim = new JTextField(15);
		txtTim.setBounds(195, 34, 290, 29);

		btnTim = new JButton("Tìm Kiếm");
		btnTim.setBounds(376, 34, 107, 29);
		btnTim.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/src/ICON/icon/search_16.png")));
		btnHuyTim = new ButtonCustom("Xem tất cả","rest", 12);
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
				btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
				btnThem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
				
				
				btnCapNhat = new JButton("Sửa");
				btnCapNhat.setBackground(new Color(255, 255, 255));
				btnCapNhat.setBounds(191, 25, 67, 63);
				btnCapNhat.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/src/ICON/icon/updated.png")));
				btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
				btnCapNhat.setFocusable(false);
				btnCapNhat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
				btnCapNhat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

				btnLamLai = new JButton("Đặt lại");
				btnLamLai.setBackground(new Color(255, 255, 255));
				btnLamLai.setBounds(268, 25, 87, 63);
				btnLamLai.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/ICON/icon/reset.png")));
				btnLamLai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
				btnLamLai.setFocusable(false);
				btnLamLai.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
				btnLamLai.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

				btnThemTaiKhoanNV = new JButton("Thêm tài khoản");
				btnThemTaiKhoanNV.setBackground(new Color(255, 255, 255));
				btnThemTaiKhoanNV.setBounds(365, 25, 129, 63);
				btnThemTaiKhoanNV.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/ICON/icon/add-friend.png")));
				
				btnThemTaiKhoanNV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
				btnThemTaiKhoanNV.setFocusable(false);
				btnThemTaiKhoanNV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
				btnThemTaiKhoanNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
				controlPanel.setLayout(null);

				controlPanel.add(btnThem);

				btnXoa = new JButton("Xóa");
				btnXoa.setBackground(new Color(255, 255, 255));
				btnXoa.setBounds(114, 25, 67, 63);
				btnXoa.setIcon(new ImageIcon(QLNhanVien_GUI.class.getResource("/src/ICON/icon/trash2_16.png")));
				btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
				btnXoa.setFocusable(false);
				btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
				btnXoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
				controlPanel.add(btnXoa);
				controlPanel.add(btnCapNhat);
				controlPanel.add(btnLamLai);
				controlPanel.add(btnThemTaiKhoanNV);
				getContentPane().add(controlPanel);

				btnTim.addActionListener(this);
				btnHuyTim.addActionListener(this);
				btnThem.addActionListener(this);
				btnXoa.addActionListener(this);
				btnLamLai.addActionListener(this);
				btnCapNhat.addActionListener(this);
				btnThemTaiKhoanNV.addActionListener(this);
				tblNhanVien.addMouseListener(this);
				tblTaiKhoan.addMouseListener(this);
				txtTim.addKeyListener(this);
		
	}
	private void GuiNhanVien() {
		// Bảng nhân viên
		JPanel tabelPanelNV = new JPanel();
		tabelPanelNV.setBounds(38, 157, 1260, 311);
		tabelPanelNV.setBackground(Color.white);
		tabelPanelNV
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Danh sách nhân viên", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
		String[] columns = { "Mã nhân viên", "Tên", "Giới Tính", "CCCD", "Ngày sinh", "SĐT","Email", "Ngày vào làm",
				"Tên đăng nhập", "Chức vụ", "Trạng thái" };
		Object[][] data = {};
	
		tableModelNV = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelPanelNV.setLayout(null);

		tblNhanVien = new Table();
		tblNhanVien.setModel(tableModelNV);
		tblNhanVien.setFillsViewportHeight(true);
		tblNhanVien.setBackground(Color.white);
		tblNhanVien.setBackground(new Color(255, 255, 255));
		tblNhanVien.setRowHeight(40); 

		JScrollPane scrollPane = new JScrollPane(tblNhanVien);
		scrollPane.setBounds(10, 23, 1240, 278);
		tabelPanelNV.add(scrollPane);
		getContentPane().add(tabelPanelNV);

		// Bảng tài khoản
		JPanel tabelPanelTK = new JPanel();
		tabelPanelTK.setBounds(38, 478, 1260, 192);
		tabelPanelTK.setBackground(Color.white);
		tabelPanelTK
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1),
						"Danh sách tài khoản", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("Segoe UI", Font.PLAIN, 12), new Color(246, 167, 193)));
		String[] columns1 = { "Tên đăng nhập", "Mật khẩu", "Loại tài khoản" };
		tableModelTK = new DefaultTableModel(columns1, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelPanelTK.setLayout(null);

		tblTaiKhoan = new Table();
		tblTaiKhoan.setModel(tableModelTK);
		tblTaiKhoan.setBackground(Color.white);
		tblTaiKhoan.setFillsViewportHeight(true);
		tblTaiKhoan.setBackground(new Color(255, 255, 255));

		JScrollPane scrollPaneTK = new JScrollPane(tblTaiKhoan);
		scrollPaneTK.setBounds(10, 23, 1240, 159);
		tabelPanelTK.add(scrollPaneTK);
		getContentPane().add(tabelPanelTK);

		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
//		tim();
		
		 String luachon = (String) cbxLuachon.getSelectedItem();
	        String searchContent = txtTim.getText();
	        tableModelNV.getDataVector().removeAllElements();
			tableModelNV.fireTableDataChanged();
	        switch (luachon) {
	            case "Tất cả":
	                dsNV = TimNhanVien.getInstance().searchTatCaNhanVien(searchContent);
	                break;
	            case "Căn cước công dân":
		                dsNV = TimNhanVien.getInstance().searchCCCD(searchContent);
	                break;
	            case "Tên đăng nhập":
	                dsNV = TimNhanVien.getInstance().searchTenDangNhapNhanVien(searchContent);
	                break;
	            case "Chức vụ":
	                dsNV = TimNhanVien.getInstance().searchChucVuNhanVien(searchContent);
	                break;
	        }
	        if (dsNV == null || dsNV.isEmpty()) {
                thongBao(2, "Không tìm thấy kết quả");
             } else {
            	   DocDuLieuNVVaoTable();
             }
	     

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(tblNhanVien)) {
			int row = tblNhanVien.getSelectedRow();
		
			String maHD  = tableModelNV.getValueAt(row, 8).toString();
			


			dsTk = TaiKhoanDAO.getInstance().getTaiKhoanTheoMaTKNhanVien(maHD);
			
			DocDuLieuTKVaoTable();
			
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
	
	public void huytim() {
		tableModelNV.getDataVector().removeAllElements();
		loadListNhanVien();
		DocDuLieuNVVaoTable();

		tableModelTK.getDataVector().removeAllElements();
		loadListTaiKhoan();
		DocDuLieuTKVaoTable();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		

	

		if (e.getSource().equals(btnHuyTim)) {
			 huytim();
		}

		if (e.getSource().equals(btnThem)) {
			 ThemNhanVien_Dialog a;
		        a = new ThemNhanVien_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
		       
		        a.setVisible(true);

		}
		if(e.getSource().equals(btnThemTaiKhoanNV)) {
			  if (tblNhanVien.getSelectedRow() == -1) {
		            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần chỉnh sửa !");
		        } else {
		            if (getNhanVienSelect().getChucVu().equals("Admin")) {
		                JOptionPane.showMessageDialog(this, "Không thể sửa tài khoản admin tại đây !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
		            } else {
		            	ThemTaiKhoan_Dialog u = new ThemTaiKhoan_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
		                u.setVisible(true);
		            }
		        
		}
			
		}
		if (e.getSource().equals(btnCapNhat)) {
			showMessage("", 2);

			
				  if (tblNhanVien.getSelectedRow() == -1) {
			            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần chỉnh sửa !");
			        } else {
			            if (getNhanVienSelect().getChucVu().equals("Admin")) {
			                JOptionPane.showMessageDialog(this, "Không thể sửa tài khoản admin tại đây !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			            } else {
			                SuaNhanVien_Dialog u = new SuaNhanVien_Dialog(this, (JFrame) SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
			                u.setVisible(true);
			            }
			        
			}
		}

		if (e.getSource().equals(btnXoa)) {
		    showMessage("", 2);
		    int row = tblNhanVien.getSelectedRow();

		    if (row == -1) {
		    	thongBao(2, "Bạn cần chọn dòng cần xóa");
		        showMessage("Lỗi: Bạn cần chọn dòng cần xóa", ERROR);
		        return;
		    }
		    try {
		        NhanVien nv = getNhanVienSelect();
		        String tenNV = nv.getTenNV();
		        int confirm = JOptionPane.showConfirmDialog(this,
		                "<html><p style='text-align: center; font-size: 18px; color:red'>Cảnh báo</p>"
		                + "<p style='text-align: center;'>Xóa nhân viên <span style='color: blue'>" + tenNV + "</span>?</p>"
		                + "<p style='text-align: center;'>Hãy suy nghĩ thật kỹ trước khi quyết định.</p></html>",
		                "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

		        if (confirm == JOptionPane.YES_OPTION) {
		            NhanVienDAO.getInstance().delete(nv);
		            taikhoandao.delete(nv.getTaiKhoan().getMaTK());
		            ((DefaultTableModel) tblNhanVien.getModel()).removeRow(row);
		            ((DefaultTableModel) tblTaiKhoan.getModel()).removeRow(row);
		            loadListNhanVien();
		            loadListTaiKhoan();
		            thongBao(0, "Xóa Thành công");
		            showMessage("Xóa thành công", SUCCESS);
		        }
		    } catch (IllegalArgumentException ex) {
		    	thongBao(2, "Không thể xóa! Không có nhân viên nào được chọn");
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        thongBao(2, "Xóa thất bại");
		    }
		}


		if (e.getSource().equals(btnLamLai)) {
			   if (tblTaiKhoan.getSelectedRow() == -1) {
		            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần đặt lại mật khẩu !");
		        } else {
		            int check = JOptionPane.showConfirmDialog(this, "Bạn có muổn đổi mật khẩu tài khoản này ?", "Reset", JOptionPane.YES_NO_OPTION);
		            if (check == JOptionPane.YES_OPTION) {
		                String textct = JOptionPane.showInputDialog(this, "Nhập mật khẩu bạn muốn thay đổi: ");
		                if (textct.equals("")) {
		                	thongBao(2, "Không được để trống !");
		                    JOptionPane.showMessageDialog(this, "Không được để trống !");
		                } else {
		                    int row = tblTaiKhoan.getSelectedRow();
		                    String userName = tblTaiKhoan.getValueAt(row, 0).toString();
		                    TaiKhoan accReset = TaiKhoanDAO.getInstance().getTaiKhoanTheoMaTk(userName);
		                    accReset.setMatKhau(BCrypt.hashpw(textct, BCrypt.gensalt(12)));
//		                    accReset.setMatKhau(textct);
		                    try {
		                        TaiKhoanDAO.getInstance().updateMatKhau(accReset);
		                        tableModelTK.fireTableDataChanged();
		                        loadListTaiKhoan();
		                      	thongBao(0, "Thay đổi thành công !");
//		                        JOptionPane.showMessageDialog(this, "Thay đổi thành công !");
		                    } catch (Exception e1) {
		                    	thongBao(2, "Thay đổi không thành công !");
		                        JOptionPane.showMessageDialog(this, "Thay đổi không thành công !");
		                    }
//		                   
		                }
		            }
		        }
		}

	}

	public String formatDate(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}

	public static void DocDuLieuNVVaoTable() {
		if (dsNV == null || dsNV.size() <= 0)
			return;
		for (NhanVien item : dsNV) {
			String ngaysinh = FormatDate.formatDate(item.getNgaySinh());
			String ngayvaolam = FormatDate.formatDate(item.getNgayVaoLam());
			String gioiTinh = item.isGioiTinh() ? "Nữ" : "Nam";
			tableModelNV.addRow(new Object[] { item.getMaNV(), item.getTenNV(), gioiTinh, item.getcCCD(), ngaysinh,
					item.getsDT(),item.getEmail(), ngayvaolam, item.getTaiKhoan().getMaTK(), item.getChucVu(), item.getTrangThai() });

		}
	}

	public void loadListNhanVien() {
		dsNV = nhanviendao.getListNhanVien();
	}

	private void DocDuLieuTKVaoTable() {
		tableModelTK.setRowCount(0);
		if (dsTk == null || dsTk.size() <= 0)
			return;
		for (TaiKhoan item : dsTk) {
			tableModelTK.addRow(new Object[] { item.getMaTK(), item.getMatKhau(), item.getLoaiTaiKhoan() });
		}
	}

	public void loadListTaiKhoan() {
		dsTk = taikhoandao.getAllTaiKhoan();
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

	private boolean validDataTim() {
		String maNV = txtTim.getText().trim();
		if (!(maNV.length() > 0)) {
			
//			thongBao(2,"Mã tìm không được để trống");
			showMessage("Lỗi: Mã không được để trống", txtTim);
			tableModelNV.getDataVector().removeAllElements();
			loadListNhanVien();
			DocDuLieuNVVaoTable();
			return false;
		}
		return true;
	}


	public static Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
		Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}
	
	public void thongBao(int type ,String txt  ) {
		Notification noti = new Notification(
                (java.awt.Frame) SwingUtilities.getWindowAncestor(this),
                type,
                Notification.Location.TOP_RIGHT,
               txt
        );
	
        noti.showNotification();
	}
	public void thongBao(int type , String text , JTextField txt ) {
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

	
	
	public NhanVien getNhanVienSelect() {
	    int i_row = tblNhanVien.getSelectedRow();
	    if (i_row == -1) {
	        throw new IllegalArgumentException("Không chọn được dòng nào");
	    }
	    String maNV = tblNhanVien.getValueAt(i_row, 0).toString();
	    return NhanVienDAO.getInstance().getNhanVienTheoMaNV(maNV);
	}
}