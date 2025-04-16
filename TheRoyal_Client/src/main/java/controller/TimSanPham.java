package controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.SanPham;
import rmi.RMIClient;
import service.SanPhamService;

public class TimSanPham {
	public static TimSanPham getInstance() {
		return new TimSanPham();
	}

	private final SanPhamService sanPhamService = RMIClient.getInstance().getSanPhamService();

	public List<SanPham> searchTatCa(String text) {
		List<SanPham> result = new ArrayList<>();
		try {
			List<SanPham> armt = sanPhamService.getAll();
			for (SanPham sp : armt) {
				if (sp.getTenSP().toLowerCase().contains(text.toLowerCase())
						|| sp.getMaSP().toLowerCase().contains(text.toLowerCase())) {
					result.add(sp);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SanPham> searchTen(String text) {
		List<SanPham> result = new ArrayList<>();
		try {
			List<SanPham> armt = sanPhamService.getAll();
			for (SanPham sp : armt) {
				if (sp.getTenSP().toLowerCase().contains(text.toLowerCase())) {
					result.add(sp);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SanPham> searhMa(String text) {
		List<SanPham> result = new ArrayList<>();
		try {
			List<SanPham> armt = sanPhamService.getAll();
			for (SanPham sp : armt) {
				if (sp.getMaSP().toLowerCase().contains(text.toLowerCase())) {
					result.add(sp);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
}
