package controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.DichVu;
import rmi.RMIClient;
import service.DichVuService;

public class TimDichVu {
	public static TimDichVu getInstance() {
		return new TimDichVu();
	}

	private final DichVuService dichVuService = RMIClient.getInstance().getDichVuService();

	public List<DichVu> searchTatCa(String text) {
		List<DichVu> result = new ArrayList<>();
		try {
			List<DichVu> allDichVu = dichVuService.getAll();
			for (DichVu dv : allDichVu) {
				if (dv.getTenDV().toLowerCase().contains(text.toLowerCase()) ||
						dv.getMaDV().toLowerCase().contains(text.toLowerCase())) {
					result.add(dv);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<DichVu> searchTen(String text) {
		List<DichVu> result = new ArrayList<>();
		try {
			List<DichVu> allDichVu = dichVuService.getAll();
			for (DichVu dv : allDichVu) {
				if (dv.getTenDV().toLowerCase().contains(text.toLowerCase())) {
					result.add(dv);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<DichVu> searhMa(String text) {
		List<DichVu> result = new ArrayList<>();
		try {
			List<DichVu> allDichVu = dichVuService.getAll();
			for (DichVu dv : allDichVu) {
				if (dv.getMaDV().toLowerCase().contains(text.toLowerCase())) {
					result.add(dv);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
}
