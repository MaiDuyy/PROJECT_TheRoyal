package gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class AboutDialog extends JDialog {

    public AboutDialog(Frame parent) {
        super(parent, "About", true);
        
        getContentPane().setLayout(null);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        getContentPane().setBackground(Color.WHITE);
      

        JLabel label2 = new JLabel("Giới Thiệu");
        label2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label2.setForeground(new Color(231, 84, 128)); // SeaGreen
        label2.setBounds(20, 10, 170, 50);
        getContentPane().add(label2);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(10, 60, 580, 2);
        getContentPane().add(separator1);

        JButton btnClose = new JButton("X");
        btnClose.setBounds(570, 10, 20, 20);
        btnClose.setBorder(null);
        btnClose.setBackground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(e -> dispose());
        getContentPane().add(btnClose);

        JLabel label5 = new JLabel("PHẦN MỀM QUẢN LÝ ĐẶT PHÒNG KHÁCH SẠN");
        label5.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label5.setForeground(new Color(231, 84, 128)); // SeaGreen
        label5.setBounds(136, 153, 327, 50);
        getContentPane().add(label5);

        JLabel label3 = new JLabel("GVHD: Trần Thị Anh Thi");
        label3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label3.setForeground(Color.DARK_GRAY);
        label3.setBounds(228, 287, 144, 25);
        getContentPane().add(label3);

        JSeparator separator2 = new JSeparator();
        separator2.setBounds(150, 322, 300, 2);
        getContentPane().add(separator2);

        // Tạo nhãn nhóm
        JLabel label1 = new JLabel("Nhóm 12: TENTWO");
        label1.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label1.setForeground(Color.DARK_GRAY);
        label1.setBounds(244, 230, 111, 25);
        getContentPane().add(label1);

        // Tạo nhãn phiên bản
        JLabel label9 = new JLabel("Phiên bản 1.1.0");
        label9.setFont(new Font("Tahoma", Font.PLAIN, 9));
        label9.setForeground(new Color(231, 84, 128)); // SeaGreen
        label9.setBounds(10, 470, 100, 25);
        getContentPane().add(label9);

        // Tạo nút Đóng (nút này ở dưới cùng của màn hình)
        JButton btnCloseBottom = new JButton("Đóng");
        btnCloseBottom.setBounds(400, 440, 180, 40);
        btnCloseBottom.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnCloseBottom.setBackground(Color.WHITE);
        btnCloseBottom.setForeground(new Color(231, 84, 128)); // SeaGreen
        btnCloseBottom.setBorder(new LineBorder(new Color(231, 84, 128), 2));
        btnCloseBottom.setFocusPainted(false);
        btnCloseBottom.addActionListener(e -> dispose());
        getContentPane().add(btnCloseBottom);
    }

  
}
