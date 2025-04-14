package gui.ui;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;
import formatdate.FormatDate;
import gui.component.ButtonCustom;
import gui.component.HeaderTitle;
import gui.component.InputForm;
import gui.component.NumericDocumentFilter;
import validata.BCrypt;
import validata.Validation;

public class Profile_Dialog_GUI extends JDialog implements ActionListener {

    CardLayout card;
    ButtonCustom save, cancel;
    HeaderTitle title;
    JPanel top, center, top_center, main_center, bottom;
     InputForm current_pwd, phone, email, new_pwd, confirm;
    Dasboard_UI home;
    JRadioButton[] jbr;
    JPanel[] panel;
    NhanVien nv ;
   
    public Profile_Dialog_GUI(JFrame owner, Dasboard_UI home, String title, boolean modal) {
        super(owner, title, modal);
        initComponent(home);
        this.setLocationRelativeTo(null);
    }

    public void initComponent(Dasboard_UI home) {
        this.home = home;
        this.setSize(400, 300);
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setResizable(false);
         nv = LoginFrame.getNhanVienDangNhap();
        top = new JPanel();
        top.setBackground(Color.WHITE);
        top.setLayout(new FlowLayout(0, 0, 0));
        title = new HeaderTitle("CHỈNH SỬA THÔNG TIN");
        top.add(title);
        this.add(top, BorderLayout.NORTH);

        top_center = new JPanel(new FlowLayout(1, 40, 0));
        top_center.setBorder(new EmptyBorder(20, 0, 0, 0));
        top_center.setBackground(Color.WHITE);
        main_center = new JPanel();
        main_center.setBorder(new EmptyBorder(0,20,0,20));
        main_center.setBackground(Color.WHITE);

        ButtonGroup bg = new ButtonGroup();
        String opt[] = {"Số điện thoại", "Email", "Mật khẩu"};
        jbr = new JRadioButton[3];
        for (int i = 0; i < jbr.length; i++) {
            jbr[i] = new JRadioButton();
            jbr[i].addActionListener(this);
            jbr[i].setText(opt[i]);
            top_center.add(jbr[i]);
            bg.add(jbr[i]);
        }
        jbr[0].setSelected(true);

        center = new JPanel();
        center.setLayout(new BorderLayout());
        center.add(top_center, BorderLayout.NORTH);
        center.add(main_center, BorderLayout.CENTER);

        panel = new JPanel[3];
        panel[0] = new JPanel(new GridLayout(1, 1));
        panel[0].setPreferredSize(new Dimension(400, 100));
        phone = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) phone.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        phone.setText(nv.getsDT());
        panel[0].add(phone);

        panel[1] = new JPanel(new GridLayout(1, 1));
        panel[1].setPreferredSize(new Dimension(400, 100));
        email = new InputForm("Email");
        email.setText(nv.getEmail());
        panel[1].add(email);
        main_center.add(panel[0]);

        panel[2] = new JPanel(new GridLayout(3, 1));
        panel[2].setPreferredSize(new Dimension(400, 300));
        current_pwd = new InputForm("Mật khẩu hiện tại", "password");
        new_pwd = new InputForm("Mật khẩu mới", "password");
        confirm = new InputForm("Nhập lại mật khẩu mới", "password");
        panel[2].add(current_pwd);
        panel[2].add(new_pwd);
        panel[2].add(confirm);

        this.add(center, BorderLayout.CENTER);

        bottom = new JPanel(new FlowLayout(1, 20, 10));
        bottom.setBackground(Color.WHITE);

        cancel = new ButtonCustom("Hủy", "danger", 12);
        cancel.addActionListener(this);
        bottom.add(cancel);
        save = new ButtonCustom("Lưu", "success", 12);
        save.addActionListener(this);
        bottom.add(save);
        this.add(bottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
        	 int yes = JOptionPane.showConfirmDialog(null, 
                     "Bạn có chắc chắn muốn thoát?", "Thông báo", 
                     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

             if (yes == JOptionPane.YES_OPTION) {
//             	  System.exit(0);
            	 dispose();
             }
        }
        for (int i = 0; i < 3; i++) {
            if (e.getSource() == jbr[i]) {
                if (i == 2) {
                    this.setSize(new Dimension(400, 500));
                    this.setLocationRelativeTo(null);
                } else {
                    this.setSize(400, 300);
                }
                main_center.removeAll();
                main_center.add(panel[i]);
                main_center.repaint();
                main_center.validate();
            }
        }

        if (jbr[0].isSelected()) {
            if (e.getSource() == save) {
                if (Validation.isEmpty(phone.getText()) || phone.getText().length() != 10) {
                    JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải có 10 ký tự sô", "Chỉnh sửa số điện thoại", JOptionPane.WARNING_MESSAGE);
                } else {
                    String sdt = phone.getText();
                    NhanVien nvdto = new NhanVien(nv.getMaNV()	,nv.getTenNV() 	, nv.isGioiTinh(), nv.getcCCD(), nv.getNgaySinh(), sdt, 
                    		nv.getEmail(), new TaiKhoan(nv.getTaiKhoan().getMaTK()), nv.getNgayVaoLam(), nv.getChucVu(), nv.getTrangThai());
                    NhanVienDAO.getInstance().update(nvdto);
                    NhanVienDAO.getInstance().getListNhanVien();QLNhanVien_GUI.DocDuLieuNVVaoTable();
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                }
            }
        }

        if (jbr[1].isSelected()) {
            if (e.getSource() == save) {
                if (Validation.isEmpty(email.getText()) || !Validation.isEmail(email.getText())) {
                    JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng định dạng", "Chỉnh sửa email", JOptionPane.WARNING_MESSAGE);
                } else {
                    String emailString = email.getText();
                    NhanVien nvdto = new NhanVien(nv.getMaNV()	,nv.getTenNV() 	, nv.isGioiTinh(), nv.getcCCD(), nv.getNgaySinh(), nv.getsDT(), 
                    		emailString, new TaiKhoan(nv.getTaiKhoan().getMaTK()), nv.getNgayVaoLam(), nv.getChucVu(), nv.getTrangThai());
                    NhanVienDAO.getInstance().update(nvdto);
                    NhanVienDAO.getInstance().getListNhanVien();
                    QLNhanVien_GUI.DocDuLieuNVVaoTable();
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");

                }
            }
        }
        if (jbr[2].isSelected()) {
            if (e.getSource() == save) {

                TaiKhoan tkdto = TaiKhoanDAO.getInstance().getTaiKhoanTheoMaNv(nv.getMaNV());
                if (Validation.isEmpty(current_pwd.getPass())) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                } else if (Validation.isEmpty(new_pwd.getPass())||new_pwd.getPass().length()<6) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu mới không được rỗng và có ít nhất 6 ký tự", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                } else if (Validation.isEmpty(confirm.getPass())) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (new_pwd.getPass().equals(confirm.getPass()) == false) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp với mật khẩu mới", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    if (BCrypt.checkpw(current_pwd.getPass(), tkdto.getMatKhau())) {
                        String pass = BCrypt.hashpw(confirm.getPass(), BCrypt.gensalt(12));
                        TaiKhoanDAO.getInstance().updatePass(nv.getEmail(), pass);
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                        current_pwd.setPass("");
                        new_pwd.setPass("");
                        confirm.setPass("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        home.resetChange();
    }
}
