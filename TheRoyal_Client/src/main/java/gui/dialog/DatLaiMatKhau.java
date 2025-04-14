package gui.dialog;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import gui.component.ButtonCustom;
import gui.component.HeaderTitle;
import gui.component.InputForm;
import gui.ui.Login;
import gui.validata.BCrypt;
import gui.validata.SMTPAuthEmail;
import gui.validata.Validation;
import lombok.SneakyThrows;
import rmi.RMIClient;
import service.NhanVienService;

import javax.swing.SwingConstants;

public class DatLaiMatKhau extends JDialog {
    private InputForm txtEmail ,txtNewPass, txtRePass, txtOTP ;
    private HeaderTitle txtTitle;
    private ButtonCustom btnThem , btnXacNhan , btnDong;
    private String otpNumber;
    private JPanel mainPanel, pnlEmail, pnlOTP, pnlNewPass;
    private JLabel lbShowMessages;
    private final int SUCCESS = 1, ERROR = 0;
    private Login home  ;

	public DatLaiMatKhau(javax.swing.JFrame parent, javax.swing.JFrame owner, boolean modal) {
        super(owner, modal);
        setSize(577, 319);
        GUI();
        setLocationRelativeTo(null);
        home = (Login) parent;
        rmiClient = RMIClient.getInstance();
        nhanVienService = rmiClient.getNhanVienService();
    }

    private RMIClient rmiClient;
    private NhanVienService nhanVienService;

    public void GUI() {
        setTitle("Đặt Lại Mật Khẩu");

        mainPanel = new JPanel(new CardLayout());
        getContentPane().add(mainPanel);

        pnlEmail =  new JPanel(null);
        pnlEmail.setBackground(new Color(255, 255, 255));
        pnlEmail.setLayout(null);
        txtTitle = new HeaderTitle("ĐẶT LẠI MẬT KHẨU");
        txtTitle.setBounds(0, 0, 563, 50);
        pnlEmail.add(txtTitle);

        txtEmail = new InputForm("Nhập email của tài khoản", 20, 20);
        txtEmail.setBounds(10, 70, 540, 60);
        pnlEmail.add(txtEmail);
        txtEmail.getTxtForm().addKeyListener(new KeyAdapter() {
        	@SneakyThrows
            @Override
        	public void keyPressed(KeyEvent e) {
        		int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                	checklogin();
                }
        	}
		});

        btnThem = new ButtonCustom("Xác nhận", "success", 15);
        btnThem.setBounds(246, 140, 150, 40);
        pnlEmail.add(btnThem);
        
        
        btnDong = new ButtonCustom("Đóng", "danger", 15);
        btnDong.setBounds(400, 140, 150, 40);
        pnlEmail.add(btnDong);
        
        

        lbShowMessages = new JLabel("", JLabel.CENTER);
        lbShowMessages.setBounds(10, 150, 500, 30);
        pnlEmail.add(lbShowMessages);

        mainPanel.add(pnlEmail, "EmailPanel");

        pnlOTP = new JPanel(null);
        txtTitle = new HeaderTitle("ĐẶT LẠI MẬT KHẨU");
        txtTitle.setBounds(0, 0, 563, 50);
        pnlOTP.add(txtTitle);
        pnlOTP.setBackground(new Color(255, 255, 255));


        txtOTP = new InputForm("Nhập OTP", 20, 20);
        txtOTP.setBounds(10, 70, 540, 60);
        pnlOTP.add(txtOTP);
        txtOTP.setText(otpNumber);
        txtOTP.getTxtForm().addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                	checkotp();
                }
        	}
		});

        ButtonCustom btnVerify = new ButtonCustom("Xác nhận OTP", "success", 15);
        btnVerify.setBounds(411, 143, 139, 40);
        pnlOTP.add(btnVerify);
        mainPanel.add(pnlOTP, "OTPPanel");
        
        JLabel lbShowMessages_1 = new JLabel("", SwingConstants.CENTER);
        lbShowMessages_1.setBounds(10, 153, 500, 30);
        pnlOTP.add(lbShowMessages_1);
        
        pnlNewPass = new JPanel(null);
        pnlNewPass.setBackground(new Color(255, 255, 255));
        pnlNewPass.setLayout(null);
        txtTitle = new HeaderTitle("ĐẶT LẠI MẬT KHẨU");
        txtTitle.setBounds(0, 0, 563, 50);
        pnlNewPass.add(txtTitle);

        txtNewPass = new InputForm("Nhập mật khẩu mới", 20, 20);
        txtNewPass.setBounds(10, 70, 540, 60);
        txtNewPass.getTxtForm().addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                	checkpass();
                }
        	}
		});
        pnlNewPass.add(txtNewPass);
        
        txtRePass = new InputForm("Nhập lại mật khẩu mới", 20, 20);
        txtRePass.setBounds(10, 140, 540, 60);
        txtRePass.getTxtForm().addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                	checkpass();
                }
        	}
		});
        pnlNewPass.add(txtRePass);
        

        btnXacNhan = new ButtonCustom("Xác nhận", "success", 15);
        btnXacNhan.setBounds(403, 216, 150, 40);
        pnlNewPass.add(btnXacNhan);



        mainPanel.add(pnlNewPass, "RePass");
        
        JLabel lbShowMessages_1_1 = new JLabel("", SwingConstants.CENTER);
        lbShowMessages_1_1.setBounds(10, 226, 500, 30);
        pnlNewPass.add(lbShowMessages_1_1);
        
        
    
        btnThem.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
            	checklogin();
            }
        });

        btnVerify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkotp();
            }
        });
        
        btnXacNhan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				checkpass();
		        
			}
		});
        
        btnDong.addActionListener(new ActionListener() {
			
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
    }
    public void checklogin() throws RemoteException {
        String email = txtEmail.getText();
        if (checkEmail(email)) {
            otpNumber = generateOTP();
            	 SMTPAuthEmail emailSender = SMTPAuthEmail.getInstance();
            	 emailSender.initializeSession("smtp.gmail.com", "587", "phammainhat123@gmail.com", "qrhjzmckdvpfrvjz");
                   boolean success =    SMTPAuthEmail.getInstance().sendOTP(email, email,otpNumber);

                 if (success) {
                    
                     JOptionPane.showMessageDialog(DatLaiMatKhau.this, "OTP đã gửi tới email của bạn", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                     switchPanel("OTPPanel");
                     System.out.println("Generated OTP: " + otpNumber); 
                     System.out.println("Gửi email thành công!");
                 } else {
                     System.out.println("Gửi email thất bại.");
                 }
               

        } else {
        	  JOptionPane.showMessageDialog(DatLaiMatKhau.this, "Email không tồn tại", "Cảnh báo", JOptionPane.INFORMATION_MESSAGE);
        	  txtEmail.selectAll();
        	  txtEmail.requestFocus();

        }
    	
    }
    public void checkotp() {
    	String enteredOTP = txtOTP.getText();
        if (enteredOTP.equals(otpNumber)) {
            JOptionPane.showMessageDialog(DatLaiMatKhau.this, "OTP hợp lệ! Bạn có thể đặt lại mật khẩu.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            switchPanel("RePass");
          
//            dispose(); 
        } else {
        	  JOptionPane.showMessageDialog(DatLaiMatKhau.this, "OTP hợp lệ! Bạn có thể đặt lại mật khẩu.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        	  txtOTP.selectAll();
        	  txtOTP.requestFocus();
        }
    	
    }
    public void checkpass() {
		String password = txtNewPass.getText();
		String repassword = txtRePass.getText();
       
        if ( Validation.isEmpty(password)||password.length()<6 ) {
        	JOptionPane.showMessageDialog(DatLaiMatKhau.this, "Mật khẩu mới không được rỗng và có ít nhất 6 ký tự", "Cảnh báo!", JOptionPane.INFORMATION_MESSAGE);
        	txtNewPass.selectAll();
        	txtNewPass.requestFocus();
        	return;
        } else if (repassword.equals(password) == false) {
			 JOptionPane.showMessageDialog(DatLaiMatKhau.this, "Mật khẩu nhập lại không khớp với mật khẩu mới", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
			 txtRePass.selectAll();
			 txtRePass.requestFocus();
                return;
		}else {
			 TaiKhoanDAO.getInstance().capnhatMK(txtEmail.getText(),BCrypt.hashpw(password,BCrypt.gensalt(12)));
		        JOptionPane.showMessageDialog(DatLaiMatKhau.this, "Đổi mật khẩu thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
		        switchPanel("RePass");
		        dispose(); 
		}
    	
    }

    private static String generateOTP() {
        int min = 100000;
        int max = 999999;
        return Integer.toString((int) ((Math.random() * (max - min)) + min));
    }

    private boolean checkEmail(String email) throws RemoteException {
        ArrayList<NhanVien> nv = (ArrayList<NhanVien>) nhanVienService.getNhanVienTheoMa(email);
        for (NhanVien i : nv) {
            if (i.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, panelName);
    }
}
