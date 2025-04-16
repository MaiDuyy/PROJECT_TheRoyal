package controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.Phong;
import rmi.RMIClient;
import service.PhongService;

public class TimPhong {
	public static TimPhong getInstance() {
		return new TimPhong();
	}

	private final PhongService phongService = RMIClient.getInstance().getPhongService();

	private String loaiPhong;

	public List<Phong> searchTatCa(String text) {
		List<Phong> result = new ArrayList<>();
		try {
			List<Phong> armt = phongService.getAll();
			for (Phong p : armt) {
				setLoaiPhong(p);
				if (p.getTenPhong().toLowerCase().contains(text.toLowerCase())
						|| p.getMaPhong().toLowerCase().contains(text.toLowerCase())
						|| loaiPhong.toLowerCase().contains(text.toLowerCase())) {
					result.add(p);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Phong> searchTen(String text) {
		List<Phong> result = new ArrayList<>();
		try {
			List<Phong> armt = phongService.getAll();
			for (Phong p : armt) {
				if (p.getTenPhong().toLowerCase().contains(text.toLowerCase())) {
					result.add(p);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Phong> searhMa(String text) {
		List<Phong> result = new ArrayList<>();
		try {
			List<Phong> armt = phongService.getAll();
			for (Phong p : armt) {
				if (p.getMaPhong().toLowerCase().contains(text.toLowerCase())) {
					result.add(p);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Phong> searchTrangThai(String text) {
		List<Phong> result = new ArrayList<>();
		try {
			List<Phong> armt = phongService.getAll();
			for (Phong p : armt) {
				if (p.getTrangThai().toLowerCase().contains(text.toLowerCase())) {
					result.add(p);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Phong> searchPhongVip(String text) {
		List<Phong> result = new ArrayList<>();
		try {
			List<Phong> armt = phongService.getAll();
			for (Phong p : armt) {
				setLoaiPhong(p);
				if (loaiPhong.toLowerCase().contains(text.toLowerCase())) {
					result.add(p);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void setLoaiPhong(Phong ph) {
		if (ph.getLoaiPhong() == null || ph.getLoaiPhong().getMaLoai() == null) {
			loaiPhong = "";
			return;
		}
		switch (ph.getLoaiPhong().getMaLoai()) {
			case "LP01":
				loaiPhong = "Phòng Đơn";
				break;
			case "LP02":
				loaiPhong = "Phòng Đôi";
				break;
			case "LP03":
				loaiPhong = "Phòng Penthouse";
				break;
			default:
				loaiPhong = "";
		}
	}
}
