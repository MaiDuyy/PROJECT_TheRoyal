package gui.ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;
import gui.component.ButtonCustom;
import gui.dialog.DatLaiMatKhau;
import gui.validata.BCrypt;
import gui.validata.Validation;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.NhanVienService;
import service.TaiKhoanService;

import javax.swing.SwingConstants;

public class Login extends JFrame {

    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private ButtonCustom btnLogin,btnExit;
    private JButton btnQuenMK;
    private ArrayList<TaiKhoan> dsTK;
    private ArrayList<NhanVien> dsNV;
    private static NhanVien nhanVienDangNhap;

    // Sử dụng RMIClient thay vì kết nối trực tiếp
    private RMIClient rmiClient;
    private NhanVienService nhanVienService;

    private TaiKhoanService taiKhoanService;

    public static NhanVien getNhanVienDangNhap() {
        return nhanVienDangNhap;
    }

    public static void setNhanVienDangNhap(NhanVien nhanVien) {
        nhanVienDangNhap = nhanVien;
    }

    public Login() throws NamingException {

        rmiClient = RMIClient.getInstance();
        nhanVienService = rmiClient.getNhanVienService();
        taiKhoanService = rmiClient.getTaiKhoanService();

        initComponents();
    }

    private void initComponents() throws NamingException {
        setTitle("Đăng Nhập");
        setUndecorated(true);
        setSize(755, 354);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(231, 144, 169));
        leftPanel.setBounds(0, 0, 281, 354);
        leftPanel.setLayout(null);
        getContentPane().add(leftPanel);

        ImageIcon originalIcon = new ImageIcon("icon/logo.png");

        Image scaledImage = originalIcon.getImage().getScaledInstance(350, 354, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imgBottom = new JLabel(scaledIcon);
        imgBottom.setBounds(0, 0, 281, 354);
        leftPanel.add(imgBottom);

        JLabel lblTitle = new JLabel("Đăng Nhập");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTitle.setForeground(new Color(246, 167, 193));
        lblTitle.setBounds(325, 20, 153, 37);
        getContentPane().add(lblTitle);

        JLabel lblUserName = new JLabel("Tên đăng nhập:");
        lblUserName.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblUserName.setForeground(new Color(246, 167, 193));
        lblUserName.setBounds(328, 72, 110, 20);
        getContentPane().add(lblUserName);

        txtUserName = new JTextField();
        txtUserName.setText("admin");
        txtUserName.setBounds(332, 97, 370, 29);
        txtUserName.requestFocus();
        txtUserName.setBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1));
        txtUserName.addKeyListener(new KeyAdapter() {
            @SneakyThrows
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    loginAction();
                }
            }
        });
        getContentPane().add(txtUserName);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblPassword.setForeground(new Color(246, 167, 193));
        lblPassword.setBounds(328, 147, 73, 20);
        getContentPane().add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setText("admin1");
        txtPassword.setBounds(332, 172, 370, 29);
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1));
        txtPassword.addKeyListener(new KeyAdapter() {
            @SneakyThrows
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    loginAction();
                }
            }
        });
        getContentPane().add(txtPassword);

        btnLogin = new ButtonCustom("Đăng Nhập" , "success" , 12 );
        btnLogin.setBounds(332, 240, 370, 40);
        btnLogin.setBackground(new Color(185,162, 145));
//        btnLogin.setForeground(new Color(246, 167, 193));
//        btnLogin.setFocusPainted(false);
//        btnLogin.setBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1));
        getContentPane().add(btnLogin);

        btnExit = new ButtonCustom("Thoát", "danger" ,12);
        btnExit.setBounds(332, 294, 370, 40);
//        btnExit.setBackground(Color.WHITE);
//        btnExit.setForeground(new Color(246, 167, 193));
//        btnExit.setFocusPainted(false);
//        btnExit.setBorder(BorderFactory.createLineBorder(new Color(246, 167, 193), 1));
        getContentPane().add(btnExit);

        btnLogin.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int yes = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn thoát chương trình?", "Thông báo",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (yes == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        btnQuenMK = new JButton("Quên mật khẩu?");
        btnQuenMK.setBounds(332, 210, 370, 20);
        btnQuenMK.setBackground(Color.WHITE);
        btnQuenMK.setForeground(new Color(246, 167, 193));
        btnQuenMK.setFocusPainted(false);
        btnQuenMK.setBorder(BorderFactory.createEmptyBorder());
        btnQuenMK.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        getContentPane().add(btnQuenMK);

        btnQuenMK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnquenMK();
            }
        });
    }

    private void loginAction() throws RemoteException {
        String userName = txtUserName.getText();
        @SuppressWarnings("deprecation")
        String pwd = txtPassword.getText();

        // Sử dụng nhanVienService từ RMIClient
        dsNV = (ArrayList<NhanVien>) nhanVienService.getNhanVienTheoMa(userName);

        // Sử dụng TaiKhoanDAO trực tiếp - có thể cần sửa đổi sau này để sử dụng TaiKhoanService từ RMIClient
        TaiKhoan tk = taiKhoanService.getTaiKhoanTheoMaTk(userName);

        if(Validation.isEmpty(userName)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập trống. Phải nhập đủ thông tin");
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return;
        } else if(Validation.isEmpty(pwd)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu trống. Phải nhập đủ thông tin");
            txtPassword.selectAll();
            txtPassword.requestFocus();
            return;
        } else if(tk == null) {
            JOptionPane.showMessageDialog(this, "Tài khoản không khớp");
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return;
        } else {
            if (BCrypt.checkpw(pwd, tk.getMatKhau())) {
                if (!dsNV.isEmpty()) {
                    nhanVienDangNhap = dsNV.get(0);
                    setNhanVienDangNhap(nhanVienDangNhap);
                    Dasboard_UI giaodienchinh = new Dasboard_UI();
                    giaodienchinh.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu không đúng!");
            }
        }
    }

    private void btnquenMK() {

        DatLaiMatKhau a = new DatLaiMatKhau(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
        a.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}
