package gui.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import lombok.SneakyThrows;
import org.jdesktop.swingx.auth.DefaultUserNameStore;


import dao.NhanVienDAO;
import entity.NhanVien;
import entity.TaiKhoan;
import gui.component.panelPopUpMoreDatPhong;
import gui.dialog.AboutDialog;
import gui.event.EventPopUpMoreDatPhong;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Dasboard_UI extends JFrame {

	private static final long serialVersionUID = 1;
	private JPanel contentPane;
	private JDesktopPane desktopPaneDashBoard;
	private panelPopUpMoreDatPhong panelPopUpMoreDatPhong1;
	private JMenu menuTrangChu, menuThongTinCaNhan, menuTroGiup, menuGioiThieu;
	private JMenuItem itemTrangChu, itemThongTinCaNhan, itemTroGiup, itemGioiThieu;
	private JMenuBar menuBar;
	public NhanVien nhanVienDangNhap = Login.getNhanVienDangNhap();
		JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);


	public Dasboard_UI() {
		setBackground(new Color(255, 192, 203));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 192, 203));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel jpanel_main = new JPanel();
		jpanel_main.setBackground(new Color(255, 192, 203));
		contentPane.add(jpanel_main);

		JPanel jpanel_menu = new JPanel();
		jpanel_menu.setBackground(new Color(255, 192, 203));
		jpanel_menu.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel menu = new JPanel();
		menu.setForeground(new Color(148, 0, 211));
		menu.setBackground(new Color(255, 192, 203));
		jpanel_menu.add(menu);
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

		JPanel Jpanel_MenuLogo = new JPanel();
		Jpanel_MenuLogo.setBackground(new Color(255, 192, 203));

		menu.add(Jpanel_MenuLogo);
		Jpanel_MenuLogo.setLayout(null);

		JLabel label_7 = new JLabel();
		label_7.setBounds(0, 0, 93, 83);
		Jpanel_MenuLogo.add(label_7);

		JLabel lblNewLabel = new JLabel("TENTWO");
		lblNewLabel.setForeground(new Color(255, 51, 51));
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 20));
		lblNewLabel.setBounds(50, 0, 137, 83);
		Jpanel_MenuLogo.add(lblNewLabel);

		JPanel Jpanel_MenuDSPhong = new JPanel();
		Jpanel_MenuDSPhong.setBackground(new Color(255, 192, 203));

		ImageIcon icon = new ImageIcon("src\\icon\\phong_bar.png");
		JLabel label = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/phong_bar.png")));
		label.setBounds(10, 0, 33, 83);

		JLabel textLabel = new JLabel("Danh sách phòng");
		textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel.setBounds(53, 0, 175, 83);
		Jpanel_MenuDSPhong.setLayout(null);
		Jpanel_MenuDSPhong.add(label);
		Jpanel_MenuDSPhong.add(textLabel);
		menu.add(Jpanel_MenuDSPhong);

		JPanel Jpanel_MenuQlyKH = new JPanel();
		Jpanel_MenuQlyKH.setBackground(new Color(255, 192, 203));
		Jpanel_MenuQlyKH.setLayout(null);
		menu.add(Jpanel_MenuQlyKH);

		JLabel label_1 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/khachhang_bar.png")));
		label_1.setBounds(10, 0, 33, 83);
		Jpanel_MenuQlyKH.add(label_1);

		JLabel textLabel_1 = new JLabel("Quản lý khách hàng");
		textLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_1.setBounds(53, 0, 175, 83);
		Jpanel_MenuQlyKH.add(textLabel_1);

		JPanel Jpanel_QLNV = new JPanel();
		Jpanel_QLNV.setBackground(new Color(255, 192, 203));
		Jpanel_QLNV.setLayout(null);
		menu.add(Jpanel_QLNV);

		JLabel label_2 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/nhanvien_bar.png")));
		label_2.setBounds(10, 0, 33, 83);
		Jpanel_QLNV.add(label_2);

		JLabel textLabel_2 = new JLabel("Quản lý nhân viên");
		textLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_2.setBounds(53, 0, 175, 84);
		Jpanel_QLNV.add(textLabel_2);

		JPanel Jpanel_MenuQLSP = new JPanel();
		Jpanel_MenuQLSP.setBackground(new Color(255, 192, 203));
		Jpanel_MenuQLSP.setLayout(null);
		menu.add(Jpanel_MenuQLSP);

		JLabel label_3 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/sanpham_bar.png")));
		label_3.setBounds(10, 0, 33, 83);
		Jpanel_MenuQLSP.add(label_3);

		JLabel textLabel_3 = new JLabel("Quản lý sản phẩm");
		textLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_3.setBounds(53, 0, 175, 83);
		Jpanel_MenuQLSP.add(textLabel_3);

		JPanel Jpanel_MenuQLDV = new JPanel();
		Jpanel_MenuQLDV.setBackground(new Color(255, 192, 203));
		Jpanel_MenuQLDV.setLayout(null);
		menu.add(Jpanel_MenuQLDV);

		JLabel label_4 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/dichvu_bar.png")));
		label_4.setBounds(10, 0, 33, 83);
		Jpanel_MenuQLDV.add(label_4);

		JLabel textLabel_4 = new JLabel("Quản lý dịch vụ");
		textLabel_4.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_4.setBounds(53, 0, 175, 83);
		Jpanel_MenuQLDV.add(textLabel_4);

		JPanel Jpanel_MenuQLHD = new JPanel();
		Jpanel_MenuQLHD.setBackground(new Color(255, 192, 203));
		Jpanel_MenuQLHD.setLayout(null);
		menu.add(Jpanel_MenuQLHD);

		JLabel label_5 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/hoadon_bar.png")));
		label_5.setBounds(10, 0, 33, 83);
		Jpanel_MenuQLHD.add(label_5);

		JLabel textLabel_5 = new JLabel("Quản lý hóa đơn");
		textLabel_5.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_5.setBounds(53, 0, 175, 83);
		Jpanel_MenuQLHD.add(textLabel_5);

		JPanel Jpanel_MenuQLUD = new JPanel();
		Jpanel_MenuQLUD.setBackground(new Color(255, 192, 203));
		Jpanel_MenuQLUD.setLayout(null);
		menu.add(Jpanel_MenuQLUD);

		JLabel label_6 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/voucher_bar.png")));
		label_6.setBounds(10, 0, 33, 83);
		Jpanel_MenuQLUD.add(label_6);

		JLabel textLabel_6 = new JLabel("Quản lý ưu đãi");
		textLabel_6.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_6.setBounds(53, 0, 175, 83);
		Jpanel_MenuQLUD.add(textLabel_6);

		JPanel Jpanel_MenuQLP = new JPanel();
		Jpanel_MenuQLP.setBackground(new Color(255, 192, 203));
		Jpanel_MenuQLP.setLayout(null);
		menu.add(Jpanel_MenuQLP);

		JLabel label_8 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/phong_bar (2).png")));
		label_8.setBounds(10, 0, 33, 83);
		Jpanel_MenuQLP.add(label_8);

		JLabel textLabel_8 = new JLabel("Quản lý phòng");
		textLabel_8.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_8.setBounds(53, 0, 175, 83);
		Jpanel_MenuQLP.add(textLabel_8);

		JPanel Jpanel_MenuTK = new JPanel();
		Jpanel_MenuTK.setBackground(new Color(255, 192, 203));
		Jpanel_MenuTK.setLayout(null);
		menu.add(Jpanel_MenuTK);

		JLabel label_9 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/thongke_bar.png")));
		label_9.setBounds(10, 0, 33, 83);
		Jpanel_MenuTK.add(label_9);

		JLabel textLabel_9 = new JLabel("Thống kê");
		textLabel_9.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_9.setBounds(53, 0, 175, 83);
		Jpanel_MenuTK.add(textLabel_9);
		
		
		
		JPanel Jpanel_MenuTKSP = new JPanel();
		Jpanel_MenuTKSP.setBackground(new Color(255, 192, 203));
		Jpanel_MenuTKSP.setLayout(null);
		menu.add(Jpanel_MenuTKSP);

		JLabel label_10 = new JLabel(new ImageIcon(Dasboard_UI.class.getResource("/src/icon/thongke_bar.png")));
		label_10.setBounds(10, 0, 33, 83);
		Jpanel_MenuTK.add(label_10);

		JLabel textLabel_10 = new JLabel("Thống kê sản phẩm");
		textLabel_10.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textLabel_10.setBounds(53, 0, 175, 83);
		Jpanel_MenuTKSP.add(textLabel_10);

		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		menuTrangChu = new JMenu("Trang chủ");
		menuTrangChu.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(menuTrangChu);
		itemTrangChu = new JMenuItem("Trang chủ");
		menuTrangChu.add(itemTrangChu);
		itemTrangChu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openTrangChu();
				
			}
		}); 


		menuGioiThieu = new JMenu("Giới thiệu");
		menuBar.add(menuGioiThieu);
		itemGioiThieu = new JMenuItem("Giới thiệu");
		menuGioiThieu.add(itemGioiThieu);
		itemGioiThieu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				AboutDialog a = new AboutDialog(owner);
//				a.setVisible(true );
			}
		}); 

		menuTroGiup = new JMenu("Trợ giúp");
		menuBar.add(menuTroGiup);
		itemTroGiup = new JMenuItem("Trợ giúp");
		menuTroGiup.add(itemTroGiup);
		itemTroGiup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				  try {
					Desktop.getDesktop().browse(new URL("https://maiduyy.github.io/HDSD/").toURI());
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		
	

		Jpanel_MenuDSPhong.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_MenuDSPhong.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_MenuDSPhong.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        openDSPhong_GUI();
		    }
		});

		Jpanel_MenuQLDV.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_MenuQLDV.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_MenuQLDV.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        openQLDichVu_GUI();
		    }
		});

		Jpanel_MenuQLHD.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_MenuQLHD.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_MenuQLHD.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        openQLHoaDon_GUI();
		    }
		});

		Jpanel_MenuQLP.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_MenuQLP.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_MenuQLP.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        openQLPhong_GUI();
		    }
		});

		Jpanel_MenuQLSP.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_MenuQLSP.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_MenuQLSP.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        openQLSanPham_GUI();
		    }
		});

		Jpanel_MenuQLUD.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_MenuQLUD.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_MenuQLUD.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        openQLUuDai_GUI();
		    }
		});

		Jpanel_MenuQlyKH.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_MenuQlyKH.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_MenuQlyKH.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        openQLKhachHang_GUI();
		    }
		});

		Jpanel_QLNV.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_QLNV.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_QLNV.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (nhanVienDangNhap.getChucVu().equals("Quản lý")) {
		            openQLNhanVien_GUI();
		        } else {
		            JOptionPane.showMessageDialog(null, "Nhân viên không có quyền truy cập vào chức năng này.");
		        }
		    }
		});

		Jpanel_MenuTK.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        Jpanel_MenuTK.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        Jpanel_MenuTK.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        openThongKe_GUI();
		    }
		});
		
		Jpanel_MenuTKSP.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	Jpanel_MenuTKSP.setBackground(new Color(255, 182, 193));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	Jpanel_MenuTKSP.setBackground(new Color(255, 192, 203));
		    }

		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	openThongKeSanPham_GUI();
		    }
		});


		desktopPaneDashBoard = new JDesktopPane();
		desktopPaneDashBoard.setBackground(new Color(255, 192, 203));
		desktopPaneDashBoard.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 192, 203));
		panel.setLayout(null);

		JPanel jpanel_profile = new JPanel();
		jpanel_profile.setBackground(new Color(255, 192, 203));
		jpanel_profile.setBounds(1044, 0, 311, 83);
		panel.add(jpanel_profile);
		jpanel_profile.setLayout(null);

		JLabel Label_Profile = new JLabel("");
		// Sự kiện mở profile
		Label_Profile.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        JPopupMenu popupMenu = new JPopupMenu();

		        JMenuItem menuItemProfile = new JMenuItem("Thông tin cá nhân");
		        menuItemProfile.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                Profile_Dialog_GUI profileDialog = new Profile_Dialog_GUI(
		                        owner, Dasboard_UI.this, "Thông tin cá nhân", true);
		                profileDialog.setVisible(true);
		            }

				
		        });
		        JMenuItem menuItemLogout = new JMenuItem("Đăng xuất");
		        menuItemLogout.addActionListener(new ActionListener() {
		            @SneakyThrows
					@Override
		            public void actionPerformed(ActionEvent e) {
		                int confirm = JOptionPane.showConfirmDialog(null,
		                        "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất",
		                        JOptionPane.YES_NO_OPTION);
		                if (confirm == JOptionPane.YES_OPTION) {
		                    JFrame giaodienhientai = (JFrame) SwingUtilities.getWindowAncestor(Label_Profile);
		                    if (giaodienhientai != null) {
		                    	giaodienhientai.dispose(); 
		                    }
		                    Login loginFrame = new Login();
		                    loginFrame.setVisible(true);
		                }
		            }
		        });


		        popupMenu.add(menuItemProfile);
		        popupMenu.add(menuItemLogout);

		        popupMenu.show(Label_Profile, e.getX(), e.getY());
		    }
		});



		Label_Profile.setIcon(new ImageIcon(Dasboard_UI.class.getResource("/ICON/icon/businessman.png")));
		Label_Profile.setBounds(10, 0, 97, 83);
		jpanel_profile.add(Label_Profile);
		
		

		JLabel lblNewLabel_3 = new JLabel();
		if (nhanVienDangNhap != null) {
		    lblNewLabel_3.setText(nhanVienDangNhap.getTenNV());
		} else {
		    lblNewLabel_3.setText("Tên nhân viên không xác định");
		}
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(109, 36, 202, 47);
		jpanel_profile.add(lblNewLabel_3);
	
		
		JLabel lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setText(nhanVienDangNhap.getChucVu());
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(109, 0, 202, 40);
		jpanel_profile.add(lblNewLabel_2);

	
		GroupLayout gl_jpanel_main = new GroupLayout(jpanel_main);
		gl_jpanel_main.setHorizontalGroup(gl_jpanel_main.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jpanel_main.createSequentialGroup().addGap(227).addComponent(panel,
						GroupLayout.PREFERRED_SIZE, 1379, GroupLayout.PREFERRED_SIZE))
				.addComponent(jpanel_menu, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_jpanel_main.createSequentialGroup().addGap(227).addComponent(desktopPaneDashBoard,
						GroupLayout.DEFAULT_SIZE, 1379, Short.MAX_VALUE)));
		gl_jpanel_main.setVerticalGroup(gl_jpanel_main.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
				.addComponent(jpanel_menu, GroupLayout.PREFERRED_SIZE, 846, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_jpanel_main.createSequentialGroup().addGap(83).addComponent(desktopPaneDashBoard,
						GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)));
		jpanel_main.setLayout(gl_jpanel_main);
		openTrangChu();
	}

	// OPEN Jinternal
	private void openDSPhong_GUI() {
		desktopPaneDashBoard.removeAll();
		DanhSachPhong_GUI DSPhong_GUI = new DanhSachPhong_GUI();
		desktopPaneDashBoard.add(DSPhong_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		DSPhong_GUI.setSize(desktopSize.width, desktopSize.height);
		DSPhong_GUI.setLocation(0, 0);
		DSPhong_GUI.setVisible(true);
	}
	
	private void openTrangChu() {
		desktopPaneDashBoard.removeAll();
		TrangChu DSPhong_GUI = new TrangChu();
		desktopPaneDashBoard.add(DSPhong_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		DSPhong_GUI.setSize(desktopSize.width, desktopSize.height);
		DSPhong_GUI.setLocation(0, 0);
		DSPhong_GUI.setVisible(true);
	}

	private void openQLDichVu_GUI() {
		desktopPaneDashBoard.removeAll();
		QLDichVu_GUI QLDichVu_GUI = new QLDichVu_GUI();
		desktopPaneDashBoard.add(QLDichVu_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		QLDichVu_GUI.setSize(desktopSize.width, desktopSize.height);
		QLDichVu_GUI.setLocation(0, 0);
		QLDichVu_GUI.setVisible(true);
	}

	private void openQLHoaDon_GUI() {
		desktopPaneDashBoard.removeAll();
		QLHoaDon_GUI QLHoaDon_GUI = new QLHoaDon_GUI();
		desktopPaneDashBoard.add(QLHoaDon_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		QLHoaDon_GUI.setSize(desktopSize.width, desktopSize.height);
		QLHoaDon_GUI.setLocation(0, 0);
		QLHoaDon_GUI.setVisible(true);
	}

	private void openQLKhachHang_GUI() {
		desktopPaneDashBoard.removeAll();
		QLKhachHang_GUI QLKhachHang_GUI = new QLKhachHang_GUI();
		desktopPaneDashBoard.add(QLKhachHang_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		QLKhachHang_GUI.setSize(desktopSize.width, desktopSize.height);
		QLKhachHang_GUI.setLocation(0, 0);
		QLKhachHang_GUI.setVisible(true);
	}

	private void openQLNhanVien_GUI() {
		desktopPaneDashBoard.removeAll();
		QLNhanVien_GUI QLNhanVien_GUI = new QLNhanVien_GUI();
		desktopPaneDashBoard.add(QLNhanVien_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		QLNhanVien_GUI.setSize(desktopSize.width, desktopSize.height);
		QLNhanVien_GUI.setLocation(0, 0);
		QLNhanVien_GUI.setVisible(true);
	}

	private void openQLPhong_GUI() {
		desktopPaneDashBoard.removeAll();
		QLPhong_GUI QLPhong_GUI = new QLPhong_GUI();
		desktopPaneDashBoard.add(QLPhong_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		QLPhong_GUI.setSize(desktopSize.width, desktopSize.height);
		QLPhong_GUI.setLocation(0, 0);
		QLPhong_GUI.setVisible(true);
	}

	private void openQLSanPham_GUI() {
		desktopPaneDashBoard.removeAll();
		QLSanPham_GUI QLSanPham_GUI = new QLSanPham_GUI();
		desktopPaneDashBoard.add(QLSanPham_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		QLSanPham_GUI.setSize(desktopSize.width, desktopSize.height);
		QLSanPham_GUI.setLocation(0, 0);
		QLSanPham_GUI.setVisible(true);
	}

	private void openQLUuDai_GUI() {
		desktopPaneDashBoard.removeAll();
		QLUuDai_GUI QLUuDai_GUI = new QLUuDai_GUI();
		desktopPaneDashBoard.add(QLUuDai_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		QLUuDai_GUI.setSize(desktopSize.width, desktopSize.height);
		QLUuDai_GUI.setLocation(0, 0);
		QLUuDai_GUI.setVisible(true);
	}

	private void openThongKe_GUI() {
		desktopPaneDashBoard.removeAll();
		ThongKe_GUI ThongKe_GUI = new ThongKe_GUI(this);
		desktopPaneDashBoard.add(ThongKe_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		ThongKe_GUI.setSize(desktopSize.width, desktopSize.height);
		ThongKe_GUI.setLocation(0, 0);
		ThongKe_GUI.setVisible(true);
	}
	
	
	private void openThongKeSanPham_GUI() {
		desktopPaneDashBoard.removeAll();
		ThongKeSanPham_GUI ThongKe_GUI = new ThongKeSanPham_GUI();
		desktopPaneDashBoard.add(ThongKe_GUI);
		Dimension desktopSize = desktopPaneDashBoard.getSize();
		ThongKe_GUI.setSize(desktopSize.width, desktopSize.height);
		ThongKe_GUI.setLocation(0, 0);
		ThongKe_GUI.setVisible(true);
	}
	  public void resetChange(){
//	       this.nhanVienDangNhap =  NhanVienDAO.getInstance().getNhanVienTheoMaNV(String.valueOf(nhanVienDangNhap.getMaNV()));
	    }
}