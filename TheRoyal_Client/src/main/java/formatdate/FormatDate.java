package formatdate;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class FormatDate {

	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}
	
	public static Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
		Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}
	
}
