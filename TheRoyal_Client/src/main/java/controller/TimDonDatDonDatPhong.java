package controller;

import java.util.ArrayList;

import dao.DonDatPhongDAO;
import dao.TaiKhoanDAO;
import entity.DonDatPhong;
import entity.TaiKhoan;

public class TimDonDatDonDatPhong {
	public static TimDonDatDonDatPhong getInstance() {
        return new TimDonDatDonDatPhong();
    }
	
	

	 
	 public ArrayList<DonDatPhong> searchTen( String text) {
		    ArrayList<DonDatPhong> result = new ArrayList<>();
		    // Get the filtered list by trangThai
		    ArrayList<DonDatPhong> armt = DonDatPhongDAO.getInstance().findPhongByTrangThaiAndSDTDangO( text);
		    // Further filter by checking if sDT contains the search text
		    for (DonDatPhong ddp : armt) {
		        String sdt = ddp.getKhachHang().getsDT();
		        if (sdt != null && sdt.toLowerCase().contains(text.toLowerCase())) {
		            result.add(ddp);
		        }
		    }
		    return result;
		}

	 public ArrayList<DonDatPhong> searhMa(String text) {
	        ArrayList<DonDatPhong> result = new ArrayList<>();
	        ArrayList<DonDatPhong> armt = DonDatPhongDAO.getInstance().getListDonDatPhong();
	        for (var ncc : armt ) {
	            if (ncc.getMaDDP().toLowerCase().contains(text.toLowerCase()))
	                   {
	                result.add(ncc);
	            }
	        }
	        
	        
	        return result;
	    }
	
	
	
}
