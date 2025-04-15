package service.impl;

import dao.GenericDAO;
import dao.SanPhamDAO;
import entity.SanPham;
import service.SanPhamService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public class SanPhamServiceImpl extends  GenericServiceImpl<SanPham, String>  implements SanPhamService {
    private SanPhamDAO sanPhamDAO ;

    public SanPhamServiceImpl(SanPhamDAO sanPhamDAO) throws RemoteException {
        super(sanPhamDAO);
        this.sanPhamDAO = sanPhamDAO;
    }


    @Override public List<SanPham> getSanPhamTheoMa(String maSP) throws RemoteException {
        return sanPhamDAO.getSanPhamTheoMa(maSP);
    }

    @Override public List<SanPham> getSanPhamTheoLoai(String tenSP) throws RemoteException {
        return sanPhamDAO.getSanPhamTheoLoai(tenSP);
    }

    @Override public String generateNewId() throws RemoteException  {
        return  SanPhamDAO.getInstance().generateNewId();
    }

    @Override public String getLatestID() throws RemoteException {
        return sanPhamDAO.getLatestID();
    }

    @Override public boolean xoaSanPham(SanPham spXoa) throws RemoteException {
        return sanPhamDAO.xoaSanPham(spXoa);
    }

    @Override public boolean suaSanPham(SanPham sp) throws RemoteException {
        return sanPhamDAO.suaSanPham(sp);
    }

    @Override public boolean updateSLSP(SanPham sp, int sl) throws RemoteException {
        return sanPhamDAO.updateSLSP(sp, sl);
    }

    @Override public boolean SanPhamTonTai(SanPham sp) throws RemoteException {
        return sanPhamDAO.SanPhamTonTai(sp);
    }

    @Override public SanPham getSanPhamTheoMaHoacTen(String maOrTen) throws RemoteException {
        return sanPhamDAO.getSanPhamTheoMaHoacTen(maOrTen);
    }

    @Override public int getTongTienThang(String thang, String nam) throws RemoteException {
        return sanPhamDAO.getTongTienThang(thang, nam);
    }

    @Override public List<String[]> getSPNgay(Date ngay) throws RemoteException {
        return sanPhamDAO.getSPNgay(ngay);
    }

    @Override public List<String[]> getMHThang(String thang, String nam) throws RemoteException {
        return sanPhamDAO.getMHThang(thang, nam);
    }

    @Override public List<String[]> getMHNam(String nam) throws RemoteException {
        return sanPhamDAO.getMHNam(nam);
    }

    @Override public int getTongTienSPNam(String nam) throws RemoteException {
        return sanPhamDAO.getTongTienSPNam(nam);
    }
}
