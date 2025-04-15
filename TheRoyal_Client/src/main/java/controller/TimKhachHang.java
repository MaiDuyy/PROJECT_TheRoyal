package controller;

import java.util.ArrayList;

import dao.KhachHangDAO;
import dao.TaiKhoanDAO;
import entity.KhachHang;
import entity.TaiKhoan;

public class TimKhachHang {
	public static TimKhachHang getInstance() {
        return new TimKhachHang();
    }

	
	 public ArrayList<KhachHang> searchTatCa(String text) {
	        ArrayList<KhachHang> result = new ArrayList<>();
	        ArrayList<KhachHang> armt = KhachHangDAO.getInstance().getListKhachHang();
	        for (var ncc : armt) {
	            if (ncc.getMaKH().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getcCCD().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getsDT().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getLoaiKH().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getcCCD().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getTenKH().toLowerCase().contains(text.toLowerCase())
	                    
	            		) {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 
	 public ArrayList<KhachHang> searchTen(String text) {
	        ArrayList<KhachHang> result = new ArrayList<>();
	        ArrayList<KhachHang> armt = KhachHangDAO.getInstance().getListKhachHang();
	        for (var ncc : armt) {
	            if (ncc.getTenKH().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 public ArrayList<KhachHang> searhSDT(String text) {
	        ArrayList<KhachHang> result = new ArrayList<>();
	        ArrayList<KhachHang> armt = KhachHangDAO.getInstance().getListKhachHang();
	        for (var ncc : armt ) {
	            if (ncc.getsDT().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        
	        
	        return result;
	    }
	 public ArrayList<KhachHang> searchCCCD(String text) {
	        ArrayList<KhachHang> result = new ArrayList<>();
	        ArrayList<KhachHang> armt = KhachHangDAO.getInstance().getListKhachHang();
	        for (var ncc : armt) {
	            if (ncc.getcCCD().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	
}
