package controller;

import java.util.ArrayList;

import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;

public class TimNhanVien {
	public static TimNhanVien getInstance() {
        return new TimNhanVien();
    }

	
	 public ArrayList<NhanVien> searchTatCaNhanVien(String text) {
	        ArrayList<NhanVien> result = new ArrayList<>();
	        ArrayList<NhanVien> armt = NhanVienDAO.getInstance().getListNhanVien();
	        for (var ncc : armt) {
	            if (ncc.getMaNV().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getTenNV().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getsDT().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getChucVu().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getcCCD().toLowerCase().contains(text.toLowerCase())
	                    
	            		) {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 
	 public ArrayList<NhanVien> searchChucVuNhanVien(String text) {
	        ArrayList<NhanVien> result = new ArrayList<>();
	        ArrayList<NhanVien> armt = NhanVienDAO.getInstance().getListNhanVien();
	        for (var ncc : armt) {
	            if (ncc.getChucVu().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 public ArrayList<NhanVien> searchTenDangNhapNhanVien(String text) {
		    ArrayList<NhanVien> result = new ArrayList<>();
		    ArrayList<NhanVien> armt = NhanVienDAO.getInstance().getListNhanVien();
		    
		    for (var ncc : armt) {
		        // Kiểm tra xem ncc.getTaiKhoan() và ncc.getTaiKhoan().getMaTK() có phải là null không
		        if (ncc.getTaiKhoan() != null && ncc.getTaiKhoan().getMaTK() != null) {
		            // Kiểm tra nếu tài khoản chứa văn bản tìm kiếm
		            if (ncc.getTaiKhoan().getMaTK().toLowerCase().contains(text.toLowerCase())) {
		                result.add(ncc);
		            }
		        }
		    }
		    
		    return result;
		}

	 public ArrayList<NhanVien> searchCCCD(String text) {
	        ArrayList<NhanVien> result = new ArrayList<>();
	        ArrayList<NhanVien> armt = NhanVienDAO.getInstance().getListNhanVien();
	        for (var ncc : armt) {
	            if (ncc.getcCCD().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	
}
