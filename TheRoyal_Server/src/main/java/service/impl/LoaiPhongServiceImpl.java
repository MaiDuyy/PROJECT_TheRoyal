package service.impl;


import dao.LoaiPhongDAO;
import entity.LoaiPhong;
import jakarta.persistence.TypedQuery;
import service.LoaiPhongService;
import util.JPAUtil;

import java.rmi.RemoteException;

public class LoaiPhongServiceImpl extends GenericServiceImpl<LoaiPhong, String> implements LoaiPhongService {

    private LoaiPhongDAO loaiPhongDAO;

    public LoaiPhongServiceImpl(LoaiPhongDAO loaiPhongDAO) throws RemoteException {
        super(loaiPhongDAO);
        this.loaiPhongDAO = loaiPhongDAO;
    }

    @Override
    public LoaiPhong getLoaiPhongTheoMa(String maLoai) {
       return loaiPhongDAO.getLoaiPhongTheoMa(maLoai);
    }
}
