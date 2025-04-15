package service.impl;

import dao.DonDatPhongDAO;
import entity.DonDatPhong;
import service.DonDatPhongService;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

public class DonDatPhongServiceImpl extends GenericServiceImpl<DonDatPhong, String> implements DonDatPhongService {
    private DonDatPhongDAO donDatPhongDAO;

    public DonDatPhongServiceImpl(DonDatPhongDAO donDatPhongDAO) throws RemoteException {
        super(donDatPhongDAO);
        this.donDatPhongDAO = donDatPhongDAO;
    }

    @Override
    public List<DonDatPhong> findPhongByTrangThaiAndSDTDangO(String maDDP) throws RemoteException{
        return donDatPhongDAO.findPhongByTrangThaiAndSDTDangO(maDDP);
    }

    @Override
    public List<DonDatPhong> findPhongByTrangThaiAndSDTDaDat(String sdt, String trangThai) throws RemoteException{
        return donDatPhongDAO.findPhongByTrangThaiAndSDTDaDat(sdt,trangThai);
    }

    @Override
    public String getLatestId() throws RemoteException{
        return donDatPhongDAO.getLatestId();
    }

    @Override
    public DonDatPhong getDonDatPhongById(String id) throws RemoteException{
        return donDatPhongDAO.getDonDatPhongById(id);
    }

    @Override
    public DonDatPhong getDonDatPhongByMaTraPhong(String maDDP) throws RemoteException{
        return donDatPhongDAO.getDonDatPhongByMaTraPhong(maDDP);
    }

    @Override
    public DonDatPhong getDonDatPhongByRoomId(String roomId) throws RemoteException{
        return donDatPhongDAO.getDonDatPhongByRoomId(roomId);
    }

    @Override
    public DonDatPhong getDonDatPhongByMaKH(String maKH) throws RemoteException{
        return donDatPhongDAO.getDonDatPhongByMaKH(maKH);
    }

    @Override
    public boolean updateTinhTrang(String maDDP, String tinhTrang) throws RemoteException{
        return donDatPhongDAO.updateTinhTrang(maDDP,tinhTrang);
    }

    @Override
    public boolean updateTraPhong(String maDDP, Date traPhong)throws RemoteException{
        return donDatPhongDAO.updateTraPhong(maDDP,traPhong);
    }

    @Override
    public boolean updateNhanPhong(String maDDP, Date nhanPhong) throws RemoteException{
        return donDatPhongDAO.updateNhanPhong(maDDP,nhanPhong);
    }

    @Override
    public boolean isRoomAvailable(String maPhong, Date traPhong, Date nhanPhong) throws RemoteException{
        return donDatPhongDAO.isRoomAvailable(maPhong,traPhong,nhanPhong);
    }

    @Override
    public List<DonDatPhong> getDonDatPhongByNgayNhanPhong(Date ngayNhanPhong) throws RemoteException{
        return donDatPhongDAO.getDonDatPhongByNgayNhanPhong(ngayNhanPhong);
    }

    @Override
    public List<DonDatPhong> timDonDatPhong(String maDDP, String maKH, String maPhong) throws RemoteException{
        return donDatPhongDAO.timDonDatPhong(maDDP,maKH,maPhong);
    }

    @Override
    public String getTrangThaiPhongOThoiDiemChon(String maPhong, Date selectedDate) throws RemoteException{
        return donDatPhongDAO.getTrangThaiPhongOThoiDiemChon(maPhong,selectedDate);
    }

    @Override
    public DonDatPhong getDonDatTruocTheoPhongVaTrangThai(String maPhong, String trangThai) throws RemoteException{
        return donDatPhongDAO.getDonDatTruocTheoPhongVaTrangThai(maPhong,trangThai);
    }

    @Override
    public int countSLDonDatTruoc(Date ngayDuocChon) throws RemoteException{
        return donDatPhongDAO.countSLDonDatTruoc(ngayDuocChon);
    }

    @Override
    public int countSLDonDangO(Date ngayDuocChon) throws RemoteException{
        return donDatPhongDAO.countSLDonDangO(ngayDuocChon);
    }

    @Override
    public int countSLPhongTrong(Date thoiGianChon) throws RemoteException{
        return donDatPhongDAO.countSLPhongTrong(thoiGianChon);
    }

    @Override
    public DonDatPhong getDonDatPhongTheoMaHD(String maHD) throws RemoteException{
        return donDatPhongDAO.getDonDatPhongTheoMaHD(maHD);
    }

    @Override
    public int countSLDonDangDon(Date ngayDuocChon) throws RemoteException{
        return donDatPhongDAO.countSLDonDangDon(ngayDuocChon);
    }
}
