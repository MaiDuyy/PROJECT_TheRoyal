package dao.impl;

import dao.LoaiPhongDAO;
import entity.LoaiPhong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

public class LoaiPhongDAOImpl extends GenericDAOImpl<LoaiPhong, String> implements LoaiPhongDAO {


    public LoaiPhongDAOImpl(Class<LoaiPhong> clazz) {
        super(clazz);
    }

    public LoaiPhongDAOImpl(EntityManager em, Class<LoaiPhong> clazz) {
        super(em, clazz);
    }

    // Lấy Loại Phòng theo mã loại
    @Override
    public LoaiPhong getLoaiPhongTheoMa(String maLoai) {
        TypedQuery<LoaiPhong> query = JPAUtil.getEntityManager().createQuery("SELECT l FROM LoaiPhong l WHERE l.maLoai = :maLoai", LoaiPhong.class);
        query.setParameter("maLoai", maLoai);
        return query.getResultStream().findFirst().orElse(null);
    }
}
