package service;

import dao.GenericDAO;
import entity.DichVu;
import service.impl.GenericServiceImpl;

import java.rmi.Remote;
import java.util.List;

public interface DichVuService extends Remote, GenericService<DichVu, String> {
    //Lấy dịch vụ từ mã hoặc tên
    List<DichVu> getDichVuByMaHoacTen(String maDV);

    //Lấy dịch vụ từ mã
    DichVu getDichVuByMaDV(String maDV);

    //Lấy dịch vụ từ tên
    List<DichVu> getDichVuTheoTen(String tenDV);

    //Kiểm tra dịch vụ tồn tại
    boolean DichVuTonTai(DichVu sp);

    //Cập nhật số lượng dịch vụ
    boolean updateSLDV(DichVu mh, int sl);

    //Lấy tổnng tiền tháng
    int getTongTienThang(int thang, int nam);

    //Lấy dựa trên tháng của năm nào đó
    List<Object[]> getMHThang(int thang, int nam);

    //Lấy dựa trên năm
    List<Object[]> getMHNam(int nam);

    //Lấy tổng tiền năm
    Double getTongTienNam(int nam);
}
