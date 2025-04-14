package gui.validata;


import java.sql.Date;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;


public class Validation {

    public static Boolean isEmpty(String input) {
        if (input == null) {
            return true;
        }
        return input.equals("");
    }

    public static Boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public static boolean isNumber(String num) {
        boolean result = true;
        if (num == null) return false;
        try {
            long k = Long.parseLong(num);
            if(k < 0) {
                result = false;
            }
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
    
	public static boolean validateNgaySinh(Date ngaySinh) {
	    if (ngaySinh == null) {
	        JOptionPane.showMessageDialog(null, "Ngày sinh không được để trống!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    Calendar currentDate = Calendar.getInstance();

	    Calendar birthDate = Calendar.getInstance();
	    birthDate.setTime(ngaySinh);

	    int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

	    if (currentDate.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) ||
	        (currentDate.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) &&
	         currentDate.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
	        age--;
	    }

	    if (age < 18) {
	        JOptionPane.showMessageDialog(null, "Nhân viên phải đủ 18 tuổi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	        return false;
	    }
	    
	    return true;
	}
}
