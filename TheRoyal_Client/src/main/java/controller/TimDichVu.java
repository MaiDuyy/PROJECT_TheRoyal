package controller;

import java.util.ArrayList;

import dao.DichVuDAO;
import dao.TaiKhoanDAO;
import entity.DichVu;
import entity.TaiKhoan;

public class TimDichVu {
	public static TimDichVu getInstance() {
        return new TimDichVu();
    }

	
	 public ArrayList<DichVu> searchTatCa(String text) {
	        ArrayList<DichVu> result = new ArrayList<>();
	        ArrayList<DichVu> armt = DichVuDAO.getInstance().getDanhSachDichVu();
	        for (var ncc : armt) {
	            if (ncc.getTenDV().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getMaDV().toLowerCase().contains(text.toLowerCase())
	                    
	                    
	            		) {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 
	 public ArrayList<DichVu> searchTen(String text) {
	        ArrayList<DichVu> result = new ArrayList<>();
	        ArrayList<DichVu> armt = DichVuDAO.getInstance().getDanhSachDichVu();
	        for (var ncc : armt) {
	            if (ncc.getTenDV().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 public ArrayList<DichVu> searhMa(String text) {
	        ArrayList<DichVu> result = new ArrayList<>();
	        ArrayList<DichVu> armt = DichVuDAO.getInstance().getDanhSachDichVu();
	        for (var ncc : armt ) {
	            if (ncc.getMaDV().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        
	        
	        return result;
	    }
	
	
}
