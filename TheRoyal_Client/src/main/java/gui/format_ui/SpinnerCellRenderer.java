package gui.format_ui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class SpinnerCellRenderer extends JSpinner implements TableCellRenderer {
    public SpinnerCellRenderer() {
        // Thiết lập kiểu chữ cho spinner
        setFont(new Font("Arial", Font.PLAIN, 15)); // Kiểu chữ giống Table

        // Đảm bảo rằng spinner không thay đổi kích thước
        setPreferredSize(new Dimension(80, 30)); // Kích thước phù hợp
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setValue(value); // Thiết lập giá trị cho spinner

        // Thiết lập màu nền và màu chữ cho JTextField bên trong spinner
        DefaultEditor editor = (DefaultEditor) this.getEditor();
        JTextField textField = editor.getTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 15)); // Kiểu chữ giống Table

        // Thiết lập màu nền tùy theo trạng thái chọn và hàng chẵn hay lẻ
        if (isSelected) {
            textField.setBackground(table.getSelectionBackground()); // Màu nền khi được chọn
            textField.setForeground(table.getSelectionForeground()); // Màu chữ khi được chọn
        } else {
            // Màu nền hàng chẵn và lẻ
            textField.setBackground(row % 2 == 0 ? new Color(255, 240, 245) : Color.WHITE);
            textField.setForeground(Color.BLACK); // Màu chữ bình thường
        }

        return this; // Trả về spinner
    }
}
