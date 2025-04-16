package controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.DonDatPhong;
import rmi.RMIClient;
import service.DonDatPhongService;

public class TimDonDatDonDatPhong {
	public static TimDonDatDonDatPhong getInstance() {
		return new TimDonDatDonDatPhong();
	}

	private final DonDatPhongService donDatPhongService = RMIClient.getInstance().getDonDatPhongService();

	public List<DonDatPhong> searchTen(String text) {
		List<DonDatPhong> result = new ArrayList<>();
		try {
			List<DonDatPhong> list = donDatPhongService.findPhongByTrangThaiAndSDTDangO(text);
			for (DonDatPhong ddp : list) {
				String sdt = ddp.getKhachHang().getSDT();
				if (sdt != null && sdt.toLowerCase().contains(text.toLowerCase())) {
					result.add(ddp);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<DonDatPhong> searhMa(String text) {
		List<DonDatPhong> result = new ArrayList<>();
		try {
			List<DonDatPhong> list = donDatPhongService.getAll();
			for (DonDatPhong ddp : list) {
				if (ddp.getMaDDP().toLowerCase().contains(text.toLowerCase())) {
					result.add(ddp);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
}
