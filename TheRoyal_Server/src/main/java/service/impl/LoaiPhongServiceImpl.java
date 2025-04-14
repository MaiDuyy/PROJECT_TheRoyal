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
        TypedQuery<LoaiPhong> query = JPAUtil.getEntityManager().createQuery("SELECT l FROM LoaiPhong l WHERE l.maLoai = :maLoai", LoaiPhong.class);
        query.setParameter("maLoai", maLoai);
        return query.getResultStream().findFirst().orElse(null);
    }
}
