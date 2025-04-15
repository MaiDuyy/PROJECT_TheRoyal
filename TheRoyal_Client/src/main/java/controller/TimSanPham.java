package controller;

import java.util.ArrayList;

import dao.SanPhamDAO;
import dao.TaiKhoanDAO;
import entity.SanPham;
import entity.TaiKhoan;

public class TimSanPham {
	public static TimSanPham getInstance() {
        return new TimSanPham();
    }

	
	 public ArrayList<SanPham> searchTatCa(String text) {
	        ArrayList<SanPham> result = new ArrayList<>();
	        ArrayList<SanPham> armt = SanPhamDAO.getInstance().getDanhSachSanPham();
	        for (var ncc : armt) {
	            if (ncc.getTenSP().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getMaSP().toLowerCase().contains(text.toLowerCase())
	                    
	                    
	            		) {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 
	 public ArrayList<SanPham> searchTen(String text) {
	        ArrayList<SanPham> result = new ArrayList<>();
	        ArrayList<SanPham> armt = SanPhamDAO.getInstance().getDanhSachSanPham();
	        for (var ncc : armt) {
	            if (ncc.getTenSP().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 public ArrayList<SanPham> searhMa(String text) {
	        ArrayList<SanPham> result = new ArrayList<>();
	        ArrayList<SanPham> armt = SanPhamDAO.getInstance().getDanhSachSanPham();
	        for (var ncc : armt ) {
	            if (ncc.getMaSP().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        
	        
	        return result;
	    }
	
	
}
