package controller;

import java.util.ArrayList;

import dao.PhongDAO;
import dao.TaiKhoanDAO;
import entity.LoaiPhong;
import entity.Phong;
import entity.TaiKhoan;

public class TimPhong {
	public static TimPhong getInstance() {
        return new TimPhong();
    }
private String loaiPhong;
	
	 public ArrayList<Phong> searchTatCa(String text) {
	        ArrayList<Phong> result = new ArrayList<>();
	        ArrayList<Phong> armt = PhongDAO.getInstance().getListPhong();
	        for (var ncc : armt) {
	        	setLoaiPhong(ncc);
	            if (ncc.getTenPhong().toLowerCase().contains(text.toLowerCase())
	                    || ncc.getMaPhong().toLowerCase().contains(text.toLowerCase())
	                    ||  loaiPhong.toLowerCase().contains(text.toLowerCase())
	                    
	                    
	            		) {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 private void setLoaiPhong(Phong ph) {
	        if (ph.getLoaiPhong().getMaLoai().equals("LP01")) {
	            loaiPhong = "Phòng Đơn";
	        }else if (ph.getLoaiPhong().getMaLoai().equals("LP02")) {
	            loaiPhong = "Phòng Đôi";
	        }else if (ph.getLoaiPhong().getMaLoai().equals("LP03")) {
	            loaiPhong = "Phòng Penthouse";
	        }
	    }
	 
	 public ArrayList<Phong> searchTen(String text) {
	        ArrayList<Phong> result = new ArrayList<>();
	        ArrayList<Phong> armt = PhongDAO.getInstance().getListPhong();
	        for (var ncc : armt) {
	            if (ncc.getTenPhong().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        return result;
	    }
	 
	 public ArrayList<Phong> searhMa(String text) {
	        ArrayList<Phong> result = new ArrayList<>();
	        ArrayList<Phong> armt = PhongDAO.getInstance().getListPhong();
	        for (var ncc : armt ) {
	            if (ncc.getMaPhong().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        
	        
	        return result;
	    }
	 public ArrayList<Phong> searchTrangThai(String text) {
	        ArrayList<Phong> result = new ArrayList<>();
	        ArrayList<Phong> armt = PhongDAO.getInstance().getListPhong();
	        for (var ncc : armt ) {
	            if (ncc.getTrangThai().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        
	        
	        return result;
	    }
	 
	 
	 public ArrayList<Phong> searchPhongVip(String text) {
	        ArrayList<Phong> result = new ArrayList<>();
	        ArrayList<Phong> armt = PhongDAO.getInstance().getListPhong();
	        for (var ncc : armt ) {
	        	setLoaiPhong(ncc);
	            if (loaiPhong.toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        
	        
	        return result;
	    }
	
	
}
