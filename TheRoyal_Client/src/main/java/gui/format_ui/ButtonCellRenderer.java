package gui.format_ui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonCellRenderer extends JButton implements TableCellRenderer {

    public ButtonCellRenderer() {
        setText("Xóa"); // Thiết lập văn bản cho nút
        setBackground(Color.RED); // Màu nền cho nút
        setForeground(Color.WHITE); // Màu chữ cho nút
        setFocusPainted(false); // Không có viền khi nút được nhấn
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this; // Trả về nút
    }
}
