package controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dao.KhuyenMaiDAO;
import dao.TaiKhoanDAO;
import entity.KhuyenMai;
import entity.TaiKhoan;

public class TimKhuyenMai {
	public static TimKhuyenMai getInstance() {
        return new TimKhuyenMai();
    }

	
	 public ArrayList<KhuyenMai> searchTatCa(String text) {
	        ArrayList<KhuyenMai> result = new ArrayList<>();
	        ArrayList<KhuyenMai> armt = KhuyenMaiDAO.getInstance().getListUuDai();
	        for (var ncc : armt) {
	            if (ncc.getTenKM().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getMaKM().toLowerCase().contains(text.toLowerCase())
	                    
	                    
	            		) {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 
	 public ArrayList<KhuyenMai> searchTen(String text) {
	        ArrayList<KhuyenMai> result = new ArrayList<>();
	        ArrayList<KhuyenMai> armt = KhuyenMaiDAO.getInstance().getListUuDai();
	        for (var ncc : armt) {
	            if (ncc.getTenKM().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 public ArrayList<KhuyenMai> searhMa(String text) {
	        ArrayList<KhuyenMai> result = new ArrayList<>();
	        ArrayList<KhuyenMai> armt = KhuyenMaiDAO.getInstance().getListUuDai();
	        for (var ncc : armt ) {
	            if (ncc.getMaKM().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 public ArrayList<KhuyenMai> searhNgayBatDau(String text) {
		    ArrayList<KhuyenMai> result = new ArrayList<>();
		    ArrayList<KhuyenMai> armt = KhuyenMaiDAO.getInstance().getListUuDai();

		    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		    
		    for (var km : armt) {
		        try {
		            String formattedDate = sdf.format(km.getNgayBatDau()); 
		            if (formattedDate.contains(text)) {
		                result.add(km);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		    return result;
	    }
	 public ArrayList<KhuyenMai> searchNgayKetThuc(String text) {
		    ArrayList<KhuyenMai> result = new ArrayList<>();
		    ArrayList<KhuyenMai> armt = KhuyenMaiDAO.getInstance().getListUuDai();

		    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		    
		    for (var km : armt) {
		        try {
		            String formattedDate = sdf.format(km.getNgayKetThuc()); 
		            if (formattedDate.contains(text)) {
		                result.add(km);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		    return result;
		}
	 public ArrayList<KhuyenMai> searchLocNgayKetThuc(Date time) {
		    ArrayList<KhuyenMai> result = new ArrayList<>();
		    ArrayList<KhuyenMai> armt = KhuyenMaiDAO.getInstance().getListUuDai();

		    for (KhuyenMai km : armt) {
		        if (km.getNgayKetThuc() != null && km.getNgayKetThuc().before(time)) {
		            result.add(km);
		        }
		    }

		    return result;
		}

	
}
