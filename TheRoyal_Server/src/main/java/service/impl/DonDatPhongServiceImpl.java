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
    public List<DonDatPhong> findPhongByTrangThaiAndSDTDangO(String maDDP){
        return donDatPhongDAO.findPhongByTrangThaiAndSDTDangO(maDDP);
    }

    @Override
    public List<DonDatPhong> findPhongByTrangThaiAndSDTDaDat(String sdt, String trangThai){
        return donDatPhongDAO.findPhongByTrangThaiAndSDTDaDat(sdt,trangThai);
    }

    @Override
    public String getLatestId(){
        return donDatPhongDAO.getLatestId();
    }

    @Override
    public DonDatPhong getDonDatPhongById(String id){
        return donDatPhongDAO.getDonDatPhongById(id);
    }

    @Override
    public DonDatPhong getDonDatPhongByMaTraPhong(String maDDP){
        return donDatPhongDAO.getDonDatPhongByMaTraPhong(maDDP);
    }

    @Override
    public DonDatPhong getDonDatPhongByRoomId(String roomId){
        return donDatPhongDAO.getDonDatPhongByRoomId(roomId);
    }

    @Override
    public DonDatPhong getDonDatPhongByMaKH(String maKH){
        return donDatPhongDAO.getDonDatPhongByMaKH(maKH);
    }

    @Override
    public boolean updateTinhTrang(String maDDP, String tinhTrang){
        return donDatPhongDAO.updateTinhTrang(maDDP,tinhTrang);
    }

    @Override
    public boolean updateTraPhong(String maDDP, Date traPhong){
        return donDatPhongDAO.updateTraPhong(maDDP,traPhong);
    }

    @Override
    public boolean updateNhanPhong(String maDDP, Date nhanPhong){
        return donDatPhongDAO.updateNhanPhong(maDDP,nhanPhong);
    }

    @Override
    public boolean isRoomAvailable(String maPhong, Date traPhong, Date nhanPhong){
        return donDatPhongDAO.isRoomAvailable(maPhong,traPhong,nhanPhong);
    }

    @Override
    public List<DonDatPhong> getDonDatPhongByNgayNhanPhong(Date ngayNhanPhong){
        return donDatPhongDAO.getDonDatPhongByNgayNhanPhong(ngayNhanPhong);
    }

    @Override
    public List<DonDatPhong> timDonDatPhong(String maDDP, String maKH, String maPhong){
        return donDatPhongDAO.timDonDatPhong(maDDP,maKH,maPhong);
    }

    @Override
    public String getTrangThaiPhongOThoiDiemChon(String maPhong, Date selectedDate){
        return donDatPhongDAO.getTrangThaiPhongOThoiDiemChon(maPhong,selectedDate);
    }

    @Override
    public DonDatPhong getDonDatTruocTheoPhongVaTrangThai(String maPhong, String trangThai){
        return donDatPhongDAO.getDonDatTruocTheoPhongVaTrangThai(maPhong,trangThai);
    }

    @Override
    public int countSLDonDatTruoc(Date ngayDuocChon){
        return donDatPhongDAO.countSLDonDatTruoc(ngayDuocChon);
    }

    @Override
    public int countSLDonDangO(Date ngayDuocChon){
        return donDatPhongDAO.countSLDonDangO(ngayDuocChon);
    }

    @Override
    public int countSLPhongTrong(Date thoiGianChon){
        return donDatPhongDAO.countSLPhongTrong(thoiGianChon);
    }

    @Override
    public DonDatPhong getDonDatPhongTheoMaHD(String maHD){
        return donDatPhongDAO.getDonDatPhongTheoMaHD(maHD);
    }

    @Override
    public int countSLDonDangDon(Date ngayDuocChon){
        return donDatPhongDAO.countSLDonDangDon(ngayDuocChon);
    }
}
