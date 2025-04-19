package service.impl;

import dao.DichVuDAO;
import entity.DichVu;
import service.DichVuService;

import java.rmi.RemoteException;
import java.util.List;

public class DichVuServiceImpl extends GenericServiceImpl<DichVu, String> implements DichVuService {
    private DichVuDAO dichVuDAO;

    public DichVuServiceImpl(DichVuDAO dichVuDAO) throws RemoteException {
        super(dichVuDAO);
        this.dichVuDAO = dichVuDAO;
    }

    @Override
    public List<DichVu> getDichVuByMaHoacTen(String maDV) throws RemoteException{
        return dichVuDAO.getDichVuByMaHoacTen(maDV);
    }

    @Override
    public DichVu getDichVuByMaDV(String maDV)throws RemoteException{
        return dichVuDAO.getDichVuByMaDV(maDV);
    }

    @Override
    public List<DichVu> getDichVuTheoTen(String tenDV) throws RemoteException{
        return dichVuDAO.getDichVuTheoTen(tenDV);
    }

    @Override
    public boolean DichVuTonTai(DichVu sp) throws RemoteException{
        return dichVuDAO.DichVuTonTai(sp);
    }

    @Override
    public boolean updateSLDV(DichVu mh, int sl)throws RemoteException{
        return dichVuDAO.updateSLDV(mh, sl);
    }

    @Override
    public int getTongTienThang(int thang, int nam)throws RemoteException{
        return dichVuDAO.getTongTienThang(thang, nam);
    }

    @Override
    public List<Object[]> getMHThang(int thang, int nam)throws RemoteException{
        return dichVuDAO.getMHThang(thang, nam);
    }

    @Override
    public List<Object[]> getMHNam(int nam)throws RemoteException{
        return dichVuDAO.getMHNam(nam);
    }

    @Override
    public Double getTongTienNam(int nam)throws RemoteException{
        return dichVuDAO.getTongTienNam(nam);
    }

    @Override
    public String getLatestID() throws RemoteException {
        return dichVuDAO.getLatestID();
    }

    @Override
    public DichVu timDichVuTheoMaHoacTheoTen(String maOrTen) throws RemoteException {
        return dichVuDAO.timDichVuTheoMaHoacTheoTen(maOrTen);
    }
}
