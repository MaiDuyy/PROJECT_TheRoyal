package gui.dialog;

import javax.swing.*;
import gui.ui.ThanhToan_GUI;
import entity.HoaDon;
import gui.component.ButtonCustom;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URLEncoder;

public class QuickLinkQrPanel extends JDialog {

    private BufferedImage qrImage;
    private JPanel pnlQR;
    public static ThanhToan_GUI home;
    private ButtonCustom btnDong;

    private HoaDon hd;

    public QuickLinkQrPanel(ThanhToan_GUI parent, boolean modal, String qrUrl) {
        super(parent, modal);
        this.home = parent;
        hd = home.getNhanVienSelect();
        GUI(qrUrl);
        setLocationRelativeTo(parent);
    }

    public void GUI(String qrUrl) {
        pnlQR = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (qrImage != null) {
                    g.drawImage(qrImage, 0, 0, this);
                }
            }
        };
        pnlQR.setBackground(new Color(255, 255, 255));

        try {
//            URL url = new URL(qrUrl);
//            URLConnection conn = url.openConnection();
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//            conn.connect();
//            this.qrImage = ImageIO.read(conn.getInputStream());
            this.qrImage = ImageIO.read(new URL(qrUrl));
            if (this.qrImage == null) {
                System.err.println("Ảnh QR bị null, không đọc được!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlQR, BorderLayout.CENTER);
        pnlQR.setLayout(null);

        btnDong = new ButtonCustom("Hoàn tất", "success", 12);
        btnDong.setIcon(new ImageIcon("icon/check2_16.png"));
        btnDong.setLocation(373, 569);
        pnlQR.add(btnDong);
        btnDong.setSize(150, 33);
        btnDong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.thanhToanHoaDon(home.phong);
            }
        });

        setSize(547, 649);
        setTitle("QR Thanh Toán");
    }

    public static String createQRUrl() {
        HoaDon hd = home.getNhanVienSelect();
        String bankId = "VPB";
        String accountNo = "5058686879";
        String template = "compact";
        double amount = hd.getTongTien();
        if (amount <= 0) amount = 1;

        String description = "Thanh toan hoa don";
        String accountName = "Phạm Mai Duy";

        try {
            String descriptionEncoded = URLEncoder.encode(description, "UTF-8");
            String accountNameEncoded = URLEncoder.encode(accountName, "UTF-8");

            return String.format(
                    "https://img.vietqr.io/image/%s-%s-%s.png?amount=%.0f&addInfo=%s&accountName=%s",
                    bankId, accountNo, template, amount, descriptionEncoded, accountNameEncoded
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
