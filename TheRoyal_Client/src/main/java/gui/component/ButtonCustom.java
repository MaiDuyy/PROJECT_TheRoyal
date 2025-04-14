package gui.component;

//import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonCustom extends JButton {

	public void initComponent(String type, String text, int fontsize, int width, int height) {
	    this.setText(text);
	    Color color = null;

	    switch (type) {
	        case "success":
	            color = new Color(	231, 84, 128);
	            break;
	        case "danger":
	            color =new Color(117, 117, 117);
	            break;
	        case "warning":
	            color = new Color(255, 204, 0);
	            break;
	        case "excel":
	            color = new Color(0, 128, 0); 
	            break;
	        case "return":
	            color = new Color(255, 165, 0); 
	            break;
	        case "ok":
	            color = Color.BLACK;
	            break;
	        case "rest":
	        	color = new Color(246, 167, 193);
	        	break;
	        default:
	            color = Color.WHITE;
	    }

	    // Cấu hình JButton
	    this.putClientProperty("JButton.buttonType", "roundRect"); // Kiểu bo góc
	    this.setBackground(color);
	    this.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, fontsize));
	    this.setForeground(new Color(255, 255, 255)); // Màu chữ trắng
	    this.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Thay đổi con trỏ chuột
	    this.setPreferredSize(new Dimension(width, height)); // Kích thước nút
	    this.setBorder(null);
	}


    public ButtonCustom(String text, String type, int fontsize) {
        initComponent(type, text, fontsize, 150, 40);
    }

    public ButtonCustom(String text, String type, int fontsize, int w, int h) {
        initComponent(type, text, fontsize, w, h);
    }

    public ButtonCustom(String text, String type, int fontsize, String linkIcon) {
        initComponent(type, text, fontsize, 150, 40);
        this.setIcon(new ImageIcon(getClass().getResource(linkIcon)));
    }

    public ButtonCustom(String text, String type, int fontsize, String linkIcon, int width, int height) {
        initComponent(type, text, fontsize, width, height);
        this.setIcon(new ImageIcon(getClass().getResource(linkIcon)));
    }

    public void setVisible(Boolean value) {
        this.setVisible(value);
    }
}
