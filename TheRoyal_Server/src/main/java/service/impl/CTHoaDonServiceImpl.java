package service.impl;

import dao.CTHoaDonDAO;
import entity.CTHoaDon;
import service.CTHoaDonService;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

public class CTHoaDonServiceImpl extends GenericServiceImpl<CTHoaDon, String> implements CTHoaDonService {
    private CTHoaDonDAO cthoaDonDAO ;

    public CTHoaDonServiceImpl(CTHoaDonDAO cthoaDonDAO) throws RemoteException {
        super(cthoaDonDAO);
        this.cthoaDonDAO = cthoaDonDAO;
    }

    @Override
    public List<CTHoaDon> getListCTHoaDonByMaHD(String maHD){
        return cthoaDonDAO.getListCTHoaDonByMaHD(maHD);
    }

    @Override
    public String getLatestID(){
        return cthoaDonDAO.getLatestID();
    }

    @Override
    public boolean checkIfCTHoaDonExists(CTHoaDon cthd){
        return cthoaDonDAO.checkIfCTHoaDonExists(cthd);
    }

    @Override
    public boolean updateSLSP(CTHoaDon ctHoaDon){
        return cthoaDonDAO.updateSLSP(ctHoaDon);
    }

    @Override
    public boolean updateSLDV(CTHoaDon ctHoaDon){
        return cthoaDonDAO.updateSLDV(ctHoaDon);
    }

    @Override
    public CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD){
        return cthoaDonDAO.getCTHoaDonByMaDV(maDV, maHD);
    }

    @Override
    public CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD){
        return cthoaDonDAO.getCTHoaDonByMaSP(maSP, maHD);
    }

    @Override
    public double getTongTienNam(int nam){
        return cthoaDonDAO.getTongTienNam(nam);
    }

    @Override
    public double getTongTienThang(int thang, int nam){
        return cthoaDonDAO.getTongTienThang(thang, nam);
    }

    @Override
    public double getTongDVTienNgay(Date ngay){
        return cthoaDonDAO.getTongDVTienNgay(ngay);
    }
}
