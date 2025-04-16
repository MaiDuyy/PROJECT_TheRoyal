package controller;

import java.rmi.RemoteException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entity.KhuyenMai;
import rmi.RMIClient;
import service.KhuyenMaiService;

public class TimKhuyenMai {
	public static TimKhuyenMai getInstance() {
		return new TimKhuyenMai();
	}

	private final KhuyenMaiService khuyenMaiService = RMIClient.getInstance().getKhuyenMaiService();

	public List<KhuyenMai> searchTatCa(String text) {
		List<KhuyenMai> result = new ArrayList<>();
		try {
			List<KhuyenMai> armt = khuyenMaiService.getAll();
			for (KhuyenMai km : armt) {
				if (km.getTenKM().toLowerCase().contains(text.toLowerCase())
						|| km.getMaKM().toLowerCase().contains(text.toLowerCase())) {
					result.add(km);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<KhuyenMai> searchTen(String text) {
		List<KhuyenMai> result = new ArrayList<>();
		try {
			List<KhuyenMai> armt = khuyenMaiService.getAll();
			for (KhuyenMai km : armt) {
				if (km.getTenKM().toLowerCase().contains(text.toLowerCase())) {
					result.add(km);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<KhuyenMai> searhMa(String text) {
		List<KhuyenMai> result = new ArrayList<>();
		try {
			List<KhuyenMai> armt = khuyenMaiService.getAll();
			for (KhuyenMai km : armt) {
				if (km.getMaKM().toLowerCase().contains(text.toLowerCase())) {
					result.add(km);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<KhuyenMai> searhNgayBatDau(String text) {
		List<KhuyenMai> result = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {
			List<KhuyenMai> armt = khuyenMaiService.getAll();
			for (KhuyenMai km : armt) {
				if (km.getThoiGianBatDau() != null) {
					String formatted = sdf.format(km.getThoiGianBatDau());
					if (formatted.contains(text)) {
						result.add(km);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<KhuyenMai> searchNgayKetThuc(String text) {
		List<KhuyenMai> result = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {
			List<KhuyenMai> armt = khuyenMaiService.getAll();
			for (KhuyenMai km : armt) {
				if (km.getThoiGianKetThuc() != null) {
					String formatted = sdf.format(km.getThoiGianKetThuc());
					if (formatted.contains(text)) {
						result.add(km);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<KhuyenMai> searchLocNgayKetThuc(Date time) {
		List<KhuyenMai> result = new ArrayList<>();
		try {
			List<KhuyenMai> armt = khuyenMaiService.getAll();
			for (KhuyenMai km : armt) {
				if (km.getThoiGianKetThuc() != null &&
						km.getThoiGianKetThuc().before(time)) {
					result.add(km);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

}
