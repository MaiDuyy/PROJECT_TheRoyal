package gui.format_ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.text.DecimalFormat;

public class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JSpinner spinner;
    private final JTable tableSPDV;
    private final JTable tableSP;
    private final JTable tableDV;
    private final DefaultTableModel modelSPDV;
    private final DefaultTableModel modelSP;
    private final DefaultTableModel modelDV;
    private int previousValue; // Lưu trữ giá trị trước đó của spinner

    public SpinnerCellEditor(JTable tableSPDV, DefaultTableModel modelSPDV, JTable tableSP, DefaultTableModel modelSP, JTable tableDV, DefaultTableModel modelDV) {
        this.tableSPDV = tableSPDV;
        this.modelSPDV = modelSPDV;
        this.tableSP = tableSP;
        this.modelSP = modelSP;
        this.tableDV = tableDV;
        this.modelDV = modelDV;

        // Tạo JSpinner với giới hạn
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1)); // Giới hạn từ 1 trở lên
        spinner.setFont(new Font("Arial", Font.PLAIN, 15)); // Kiểu chữ giống Table

        // Cấu hình JSpinner
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setFont(new Font("Arial", Font.PLAIN, 15)); // Kiểu chữ cho TextField
        editor.getTextField().setBackground(Color.WHITE); // Thiết lập màu nền mặc định

        // Lắng nghe sự thay đổi của spinner
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int row = tableSPDV.getEditingRow(); // Lấy hàng đang chỉnh sửa trong bảng SPDV
                if (row != -1) {
                    int currentValue = (int) spinner.getValue();
                    int delta = currentValue - previousValue; // Tính toán sự chênh lệch

                    String productName = (String) modelSPDV.getValueAt(row, 1); // Cột "Tên sản phẩm & dịch vụ" là cột thứ 1
                    double unitPrice = (double) modelSPDV.getValueAt(row, 3); // Cột "Đơn giá" là cột thứ 3
                    double totalPrice = currentValue * unitPrice; // Tính lại thành tiền
                    modelSPDV.setValueAt(formatCurrency(totalPrice), row, 4); // Cập nhật giá trị cột "Thành tiền"

                    // Cập nhật số lượng trong bảng SP hoặc DV nếu không trở thành số âm
                    if (updateProductQuantityInTable(productName, -delta)) {
                        // Cập nhật giá trị previousValue
                        previousValue = currentValue;
                    } else {
                        // Nếu không cập nhật được, giữ nguyên giá trị trong bảng SPDV
                        modelSPDV.setValueAt(formatCurrency(previousValue * unitPrice), row, 4); // Đặt lại thành tiền
                        spinner.setValue(previousValue); // Đặt lại giá trị spinner
                    }
                }
            }
        });
    }

    private boolean updateProductQuantityInTable(String productName, int delta) {
        // Cập nhật số lượng trong bảng SP
        for (int i = 0; i < modelSP.getRowCount(); i++) {
            String spName = (String) modelSP.getValueAt(i, 1); // Cột "Tên sản phẩm" là cột thứ 1
            if (spName.equals(productName)) {
                int currentQuantitySP = (int) modelSP.getValueAt(i, 3); // Cột "Số lượng" là cột thứ 3
                if (currentQuantitySP + delta >= 0) { // Kiểm tra số lượng không âm
                    modelSP.setValueAt(currentQuantitySP + delta, i, 3); // Cập nhật số lượng
                    return true; // Cập nhật thành công
                }
                return false; // Không cập nhật được do số lượng âm
            }
        }

        // Cập nhật số lượng trong bảng DV nếu không tìm thấy trong bảng SP
        for (int i = 0; i < modelDV.getRowCount(); i++) {
            String dvName = (String) modelDV.getValueAt(i, 1); // Cột "Tên dịch vụ" là cột thứ 1
            if (dvName.equals(productName)) {
                int currentQuantityDV = (int) modelDV.getValueAt(i, 3); // Cột "Số lượng" là cột thứ 3
                if (currentQuantityDV + delta >= 0) { // Kiểm tra số lượng không âm
                    modelDV.setValueAt(currentQuantityDV + delta, i, 3); // Cập nhật số lượng
                    return true; // Cập nhật thành công
                }
                return false; // Không cập nhật được do số lượng âm
            }
        }
        return false; // Không tìm thấy sản phẩm trong cả hai bảng
    }

    // Phương thức định dạng giá trị tiền tệ
    private String formatCurrency(double value) {
    	DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(value)+"đ"; // Định dạng giá trị với dấu phẩy và đơn vị tiền tệ
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue(); // Trả về giá trị spinner
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        spinner.setValue(value); // Thiết lập giá trị cho spinner
        previousValue = (int) value; // Gán giá trị ban đầu cho previousValue

        // Thiết lập màu nền cho JTextField bên trong spinner tùy theo hàng chẵn hay lẻ
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        JTextField textField = editor.getTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 15)); // Kiểu chữ giống Table
        textField.setBackground(row % 2 == 0 ? new Color(255, 240, 245) : Color.WHITE); // Màu nền hàng chẵn và lẻ

        return spinner; // Trả về spinner
    }
}