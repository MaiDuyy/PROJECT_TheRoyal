package gui.format_ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JButton button;
    private final DefaultTableModel model;
    private final DefaultTableModel modelSP; // Tham chiếu đến bảng SP
    private final DefaultTableModel modelDV; // Tham chiếu đến bảng DV
    private int editingRow; // Biến để lưu hàng đang chỉnh sửa

    public ButtonCellEditor(DefaultTableModel model, DefaultTableModel modelSP, DefaultTableModel modelDV) {
        this.model = model;
        this.modelSP = modelSP;
        this.modelDV = modelDV;

        button = new JButton();
        button.setBackground(Color.RED); // Màu nền cho nút
        button.setForeground(Color.WHITE); // Màu chữ cho nút

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingRow != -1) {
                    // Dừng chỉnh sửa nếu bảng đang chỉnh sửa
                    fireEditingStopped();

                    // Lấy số lượng sản phẩm/dịch vụ sẽ bị xóa
                    int quantityToRemove = (int) model.getValueAt(editingRow, 2);

                    // Lấy tên sản phẩm/dịch vụ
                    String productName = (String) model.getValueAt(editingRow, 1);

                    // Xóa hàng
                    model.removeRow(editingRow);

                    // Cập nhật bảng, gọi callback để thông báo thay đổi
                    model.fireTableRowsDeleted(editingRow, editingRow);

                    // Cập nhật số lượng trong bảng SP hoặc DV
                    updateProductQuantityInTable(productName, quantityToRemove);

                    // Cập nhật lại STT nếu còn hàng
                    if (model.getRowCount() > 0) {
                        for (int i = 0; i < model.getRowCount(); i++) {
                            model.setValueAt(i + 1, i, 0);
                        }
                    }
                }
            }
        });

    }

    private void updateProductQuantityInTable(String productName, int quantityToRemove) {
        // Cập nhật số lượng trong bảng SP
        for (int i = 0; i < modelSP.getRowCount(); i++) {
            String spName = (String) modelSP.getValueAt(i, 1); // Cột "Tên sản phẩm" là cột thứ 1
            if (spName.equals(productName)) {
                int currentQuantitySP = (int) modelSP.getValueAt(i, 3); // Cột "Số lượng" là cột thứ 3
                modelSP.setValueAt(currentQuantitySP + quantityToRemove, i, 3); // Cập nhật số lượng
                break;
            }
        }

        // Cập nhật số lượng trong bảng DV nếu không tìm thấy trong bảng SP
        for (int i = 0; i < modelDV.getRowCount(); i++) {
            String dvName = (String) modelDV.getValueAt(i, 1); // Cột "Tên dịch vụ" là cột thứ 1
            if (dvName.equals(productName)) {
                int currentQuantityDV = (int) modelDV.getValueAt(i, 3); // Cột "Số lượng" là cột thứ 3
                modelDV.setValueAt(currentQuantityDV + quantityToRemove, i, 3); // Cập nhật số lượng
                break;
            }
        }
    }

    @Override
    public Object getCellEditorValue() {
        return null; // Không cần giá trị
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editingRow = row; // Lưu hàng đang chỉnh sửa
        return button; // Trả về nút
    }
}
