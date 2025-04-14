package gui.format_ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Table extends JTable {

    public Table() {
        // Thiết lập mặc định cho JTable
        setRowHeight(40); // Chiều cao của từng hàng
        setGridColor(Color.LIGHT_GRAY); // Màu đường kẻ bảng
        setShowGrid(true); // Hiển thị lưới kẻ

        // Thiết lập bảng
        setFont(new Font("Arial", Font.PLAIN, 12)); // Font chữ của nội dung
        setSelectionBackground(Color.LIGHT_GRAY); // Màu nền khi chọn hàng
        setSelectionForeground(Color.BLACK); // Màu chữ khi chọn hàng

        // Thiết lập tiêu đề bảng
        JTableHeader header = getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13)); 
        header.setForeground(Color.WHITE); // Màu chữ tiêu đề
//        header.setBackground(new Color(255, 192, 203)); // Màu nền tiêu đề
        header.setBackground(new Color(117, 117, 117)); // Màu nền tiêu đề
        header.setReorderingAllowed(false); // Không cho phép sắp xếp lại cột
        header.setPreferredSize(new Dimension(0, 40));
        // Tạo và áp dụng renderer cho các ô
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        component.setBackground(Color.white); // Màu nền hàng chẵn
                    } else {
                        component.setBackground(Color.WHITE); // Màu nền hàng lẻ
                    }
                }
                
                // Căn giữa nội dung
                setHorizontalAlignment(SwingConstants.CENTER);

                return component;
            }
        };

        setDefaultRenderer(Object.class, cellRenderer);
    }
}
