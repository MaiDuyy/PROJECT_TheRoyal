package service.impl;

import dao.KhuyenMaiDAO;
import entity.KhuyenMai;
import service.KhuyenMaiService;

import java.rmi.RemoteException;
import java.util.List;

public class KhuyenMaiServiceImpl extends GenericServiceImpl<KhuyenMai , String> implements KhuyenMaiService {

    private KhuyenMaiDAO khuyenMaiDAO ;

    public KhuyenMaiServiceImpl(KhuyenMaiDAO khuyenMaiDAO) throws RemoteException {
        super(khuyenMaiDAO);
        this.khuyenMaiDAO = khuyenMaiDAO ;

    }

   @Override
   public KhuyenMai getKhuyenMaiTheoMa(String maUuDai) throws  RemoteException {
        return khuyenMaiDAO.getKhuyenMaiTheoMa(maUuDai);
    }

    // Lấy danh sách Khuyến Mãi sau ngày cụ thể
  @Override
  public List<KhuyenMai> getKhuyenMaiSauNgay(java.sql.Date time) throws  RemoteException {
        return khuyenMaiDAO.getKhuyenMaiSauNgay(time);
    }

    // Cập nhật số lượng Khuyến Mãi
    @Override
    public  boolean updateSoLuong(String maKM) throws  RemoteException {
        return khuyenMaiDAO.updateSoLuong(maKM);
    }

    // Lấy mã Khuyến Mãi mới nhất
    @Override
    public  String getLatestID()  throws  RemoteException{
        return khuyenMaiDAO.getLatestID();
    }
}
