package service.impl;

import dao.CTHoaDonDAO;
import entity.CTHoaDon;
import service.CTHoaDonService;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

public class CTHoaDonServiceImpl extends  GenericServiceImpl<CTHoaDon, String>  implements CTHoaDonService {

    private CTHoaDonDAO cthoaDonDAO;

    public CTHoaDonServiceImpl(CTHoaDonDAO cthoaDonDAO) throws RemoteException {
        super(cthoaDonDAO);
        this.cthoaDonDAO = cthoaDonDAO;
    }


    @Override public List<CTHoaDon> getListCTHoaDonByMaHD(String maHD) throws RemoteException {
        return cthoaDonDAO.getListCTHoaDonByMaHD(maHD);
    }


    @Override public String getLatestID() throws RemoteException {
        return cthoaDonDAO.getLatestID();
    }


    @Override public boolean checkIfCTHoaDonExists(CTHoaDon cthd) throws RemoteException {
        return cthoaDonDAO.checkIfCTHoaDonExists(cthd);
    }


    @Override public boolean updateSLSP(CTHoaDon ctHoaDon) throws RemoteException {
        return cthoaDonDAO.updateSLSP(ctHoaDon);
    }

    @Override public boolean updateSLDV(CTHoaDon ctHoaDon) throws RemoteException {
        return cthoaDonDAO.updateSLDV(ctHoaDon);
    }


    @Override public CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD) throws RemoteException {
        return cthoaDonDAO.getCTHoaDonByMaDV(maDV, maHD);
    }


    @Override public CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD) throws RemoteException {
        return cthoaDonDAO.getCTHoaDonByMaSP(maSP, maHD);
    }


    @Override public double getTongTienNam(int nam) throws RemoteException {
        return cthoaDonDAO.getTongTienNam(nam);
    }


    @Override public double getTongTienThang(int thang, int nam) throws RemoteException {
        return cthoaDonDAO.getTongTienThang(thang, nam);
    }


    @Override public double getTongDVTienNgay(Date ngay) throws RemoteException {
        return cthoaDonDAO.getTongDVTienNgay(ngay);
    }

    @Override
    public boolean insert(CTHoaDon ctHoaDon) throws RemoteException {
        return cthoaDonDAO.insert(ctHoaDon);
    }

    @Override
    public List<String[]> getTopDichVuTheoNam(int nam) throws RemoteException{
        return cthoaDonDAO.getTopDichVuTheoNam(nam);
    }

    @Override
    public List<String[]> getTOPSPNam(String nam) throws RemoteException{
        return cthoaDonDAO.getTOPSPNam(nam);
    }

    @Override
    public List<String[]> getTKDVNam(String nam) throws RemoteException{
        return cthoaDonDAO.getTKDVNam(nam);
    }

    @Override
    public List<String[]> getTKSPNam(String nam) throws RemoteException{
        return cthoaDonDAO.getTKSPNam(nam);
    }

    @Override
    public List<String[]> getTOPDVThang(String thang, String nam) throws RemoteException {

        return cthoaDonDAO.getTOPDVThang(thang, nam);
    }

    @Override
    public List<String[]> getTOPSPThang(String thang, String nam) throws RemoteException {
        return cthoaDonDAO.getTOPSPThang(thang, nam);
    }

    @Override
    public List<String[]> getTOPDVNgay(Date date) throws RemoteException{

        return cthoaDonDAO.getTOPDVNgay(date);
    }

    @Override
    public List<String[]> getTOPSPNgay(Date date) throws RemoteException{

        return cthoaDonDAO.getTOPSPNgay(date);
    }

    @Override
    public double getTongTienSPNgay(Date ngay) throws RemoteException{
        return cthoaDonDAO.getTongTienSPNgay(ngay);
    }

    @Override
    public List<String[]> getTOPDVNam(String nam) throws RemoteException{
        return cthoaDonDAO.getTOPDVNam(nam);
    }
}
