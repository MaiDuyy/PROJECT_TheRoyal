package service;

import dao.GenericDAO;
import entity.DichVu;
import service.impl.GenericServiceImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DichVuService extends Remote, GenericService<DichVu, String> {
    //Lấy dịch vụ từ mã hoặc tên
    List<DichVu> getDichVuByMaHoacTen(String maDV) throws RemoteException;

    //Lấy dịch vụ từ mã
    DichVu getDichVuByMaDV(String maDV) throws RemoteException;

    //Lấy dịch vụ từ tên
    List<DichVu> getDichVuTheoTen(String tenDV) throws RemoteException;

    //Kiểm tra dịch vụ tồn tại
    boolean DichVuTonTai(DichVu sp) throws RemoteException;

    //Cập nhật số lượng dịch vụ
    boolean updateSLDV(DichVu mh, int sl) throws RemoteException;

    //Lấy tổnng tiền tháng
    int getTongTienThang(int thang, int nam) throws RemoteException;

    //Lấy dựa trên tháng của năm nào đó
    List<Object[]> getMHThang(int thang, int nam) throws RemoteException;

    //Lấy dựa trên năm
    List<Object[]> getMHNam(int nam) throws RemoteException;

    //Lấy tổng tiền năm
    Double getTongTienNam(int nam) throws RemoteException;
    public String getLatestID() throws RemoteException;

    public DichVu timDichVuTheoMaHoacTheoTen(String maOrTen) throws RemoteException;
}
