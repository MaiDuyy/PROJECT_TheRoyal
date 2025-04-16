package controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.KhachHang;
import rmi.RMIClient;
import service.KhachHangService;

public class TimKhachHang {
	public static TimKhachHang getInstance() {
		return new TimKhachHang();
	}

	private final KhachHangService khachHangService = RMIClient.getInstance().getKhachHangService();

	public List<KhachHang> searchTatCa(String text) {
		List<KhachHang> result = new ArrayList<>();
		try {
			List<KhachHang> armt = khachHangService.getAll();
			for (KhachHang kh : armt) {
				if (kh.getMaKH().toLowerCase().contains(text.toLowerCase())
						|| kh.getCCCD().toLowerCase().contains(text.toLowerCase())
						|| kh.getSDT().toLowerCase().contains(text.toLowerCase())
						|| kh.getLoaiKH().toLowerCase().contains(text.toLowerCase())
						|| kh.getTenKH().toLowerCase().contains(text.toLowerCase())) {
					result.add(kh);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<KhachHang> searchTen(String text) {
		List<KhachHang> result = new ArrayList<>();
		try {
			List<KhachHang> armt = khachHangService.getAll();
			for (KhachHang kh : armt) {
				if (kh.getTenKH().toLowerCase().contains(text.toLowerCase())) {
					result.add(kh);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<KhachHang> searchSDT(String text) {
		List<KhachHang> result = new ArrayList<>();
		try {
			List<KhachHang> armt = khachHangService.getAll();
			for (KhachHang kh : armt) {
				if (kh.getSDT().toLowerCase().contains(text.toLowerCase())) {
					result.add(kh);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<KhachHang> searchCCCD(String text) {
		List<KhachHang> result = new ArrayList<>();
		try {
			List<KhachHang> armt = khachHangService.getAll();
			for (KhachHang kh : armt) {
				if (kh.getCCCD().toLowerCase().contains(text.toLowerCase())) {
					result.add(kh);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
}
