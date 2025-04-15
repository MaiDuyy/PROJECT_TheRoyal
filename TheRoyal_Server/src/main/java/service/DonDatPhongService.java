package service;

import entity.DonDatPhong;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface DonDatPhongService extends Remote, GenericService<DonDatPhong, String> {
    List<DonDatPhong> findPhongByTrangThaiAndSDTDangO(String maDDP) throws RemoteException;

    List<DonDatPhong> findPhongByTrangThaiAndSDTDaDat(String sdt, String trangThai) throws RemoteException;

    String getLatestId() throws RemoteException;

    DonDatPhong getDonDatPhongById(String id) throws RemoteException;

    DonDatPhong getDonDatPhongByMaTraPhong(String maDDP) throws RemoteException;

    DonDatPhong getDonDatPhongByRoomId(String roomId) throws RemoteException;

    DonDatPhong getDonDatPhongByMaKH(String maKH) throws RemoteException;

    boolean updateTinhTrang(String maDDP, String tinhTrang) throws RemoteException;

    boolean updateTraPhong(String maDDP, Date traPhong) throws RemoteException;

    boolean updateNhanPhong(String maDDP, Date nhanPhong) throws RemoteException;

    boolean isRoomAvailable(String maPhong, Date traPhong, Date nhanPhong) throws RemoteException;

    List<DonDatPhong> getDonDatPhongByNgayNhanPhong(Date ngayNhanPhong) throws RemoteException;

    List<DonDatPhong> timDonDatPhong(String maDDP, String maKH, String maPhong) throws RemoteException;

    String getTrangThaiPhongOThoiDiemChon(String maPhong, Date selectedDate) throws RemoteException;

    DonDatPhong getDonDatTruocTheoPhongVaTrangThai(String maPhong, String trangThai) throws RemoteException;

    int countSLDonDatTruoc(Date ngayDuocChon) throws RemoteException;

    int countSLDonDangO(Date ngayDuocChon) throws RemoteException;

    int countSLPhongTrong(Date thoiGianChon) throws RemoteException;

    DonDatPhong getDonDatPhongTheoMaHD(String maHD) throws RemoteException;

    int countSLDonDangDon(Date ngayDuocChon) throws RemoteException;

    public  DonDatPhong getDonDatTruocTheoPhongVaNgay(String maPhong, java.util.Date thoiGianChon)   throws RemoteException;
}
