package gui.format_ui;

import javax.swing.*;
import java.awt.*;

public class RoundedBorder extends JPanel {
    private int radius;
    private Color borderColor = null; // Không có màu mặc định
    private int borderWidth = 1; // Độ dày border mặc định

    public RoundedBorder(int radius) {
        this.radius = radius;
        setOpaque(false);  // Cho phép hiển thị bo tròn bằng cách tắt nền mặc định
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint(); // Vẽ lại để áp dụng màu mới
    }

    public void setBorderWidth(int width) {
        this.borderWidth = width;
        repaint(); // Vẽ lại để áp dụng độ dày mới
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Bật khử răng cưa cho các đường cong mượt mà hơn
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Lấy kích thước của panel
        int width = getWidth();
        int height = getHeight();

        // Vẽ nền bo tròn
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, width, height, radius, radius);

        // Vẽ border nếu borderColor không phải là null
        if (borderColor != null) {
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(borderWidth / 2, borderWidth / 2, width - borderWidth, height - borderWidth, radius, radius);
        }
    }
}
