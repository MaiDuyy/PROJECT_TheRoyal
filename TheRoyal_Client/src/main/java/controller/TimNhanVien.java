package controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.NhanVien;
import rmi.RMIClient;
import service.NhanVienService;

public class TimNhanVien {
	public static TimNhanVien getInstance() {
		return new TimNhanVien();
	}

	private final NhanVienService nhanVienService = RMIClient.getInstance().getNhanVienService();

	public List<NhanVien> searchTatCaNhanVien(String text) {
		List<NhanVien> result = new ArrayList<>();
		try {
			List<NhanVien> armt = nhanVienService.getListNhanVien();
			for (NhanVien nv : armt) {
				if (nv.getMaNV().toLowerCase().contains(text.toLowerCase())
						|| nv.getTenNV().toLowerCase().contains(text.toLowerCase())
						|| nv.getSDT().toLowerCase().contains(text.toLowerCase())
						|| nv.getChucVu().toLowerCase().contains(text.toLowerCase())
						|| nv.getCCCD().toLowerCase().contains(text.toLowerCase())) {
					result.add(nv);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<NhanVien> searchChucVuNhanVien(String text) {
		List<NhanVien> result = new ArrayList<>();
		try {
			List<NhanVien> armt = nhanVienService.getListNhanVien();
			for (NhanVien nv : armt) {
				if (nv.getChucVu().toLowerCase().contains(text.toLowerCase())) {
					result.add(nv);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<NhanVien> searchTenDangNhapNhanVien(String text) {
		List<NhanVien> result = new ArrayList<>();
		try {
			List<NhanVien> armt = nhanVienService.getListNhanVien();
			for (NhanVien nv : armt) {
				if (nv.getTaiKhoan() != null &&
						nv.getTaiKhoan().getMaTK() != null &&
						nv.getTaiKhoan().getMaTK().toLowerCase().contains(text.toLowerCase())) {
					result.add(nv);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<NhanVien> searchCCCD(String text) {
		List<NhanVien> result = new ArrayList<>();
		try {
			List<NhanVien> armt = nhanVienService.getListNhanVien();
			for (NhanVien nv : armt) {
				if (nv.getCCCD().toLowerCase().contains(text.toLowerCase())) {
					result.add(nv);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
}
