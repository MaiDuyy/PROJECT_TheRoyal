package dao;

import entity.DonDatPhong;


import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;

public interface DonDatPhongDAO extends GenericDAO<DonDatPhong, String> {

    List<DonDatPhong> findPhongByTrangThaiAndSDTDangO(String maDDP);

    List<DonDatPhong> findPhongByTrangThaiAndSDTDaDat(String sdt, String trangThai);

    String getLatestId();

    DonDatPhong getDonDatPhongById(String id);

    DonDatPhong getDonDatPhongByMaTraPhong(String maDDP);

    DonDatPhong getDonDatPhongByRoomId(String roomId);

    DonDatPhong getDonDatPhongByMaKH(String maKH);

    boolean updateTinhTrang(String maDDP, String tinhTrang);

    boolean updateTraPhong(String maDDP, Date traPhong);

    boolean updateNhanPhong(String maDDP, Date nhanPhong);

    boolean isRoomAvailable(String maPhong, Date traPhong, Date nhanPhong);

    List<DonDatPhong> getDonDatPhongByNgayNhanPhong(Date ngayNhanPhong);

    List<DonDatPhong> timDonDatPhong(String maDDP, String maKH, String maPhong);

    String getTrangThaiPhongOThoiDiemChon(String maPhong, Date selectedDate);

    DonDatPhong getDonDatTruocTheoPhongVaTrangThai(String maPhong, String trangThai);

    int countSLDonDatTruoc(Date ngayDuocChon);

    int countSLDonDangO(Date ngayDuocChon);

    int countSLPhongTrong(Date thoiGianChon);

    DonDatPhong getDonDatPhongTheoMaHD(String maHD);

    int countSLDonDangDon(Date ngayDuocChon);

    public  DonDatPhong getDonDatTruocTheoPhongVaNgay(String maPhong, java.util.Date thoiGianChon) ;

    public boolean insert(DonDatPhong ddp);
}
