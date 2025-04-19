package dao.impl;


import dao.DichVuDAO;
import entity.DichVu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.util.List;

public class DichVuDAOImpl extends GenericDAOImpl<DichVu, String> implements DichVuDAO {
    public DichVuDAOImpl(Class<DichVu> clazz) {
        super(clazz);
    }
    public DichVuDAOImpl(EntityManager em, Class<DichVu> clazz) {
        super(em, clazz);
    }
    @Override
    public List<DichVu> getDichVuByMaHoacTen(String maDV){
        try {
            TypedQuery<DichVu> query = em.createQuery("SELECT dv FROM DichVu dv WHERE dv.maDV = :maDV OR dv.tenDV LIKE :tenDV", DichVu.class);
            query.setParameter("maDV", maDV);
            query.setParameter("maDV", "%"+maDV+"%");
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DichVu getDichVuByMaDV(String maDV){
        try {
            TypedQuery<DichVu> query = em.createQuery("SELECT dv FROM DichVu dv WHERE dv.maDV = :maDV", DichVu.class);
            query.setParameter("maDV", maDV);
            return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DichVu> getDichVuTheoTen(String tenDV){
        try {
            TypedQuery<DichVu> query = em.createQuery("SELECT dv FROM DichVu dv WHERE dv.tenDV = :tenDV", DichVu.class);
            query.setParameter("tenDV", tenDV);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean DichVuTonTai(DichVu sp){
        try {
            TypedQuery<DichVu> query = em.createQuery("SELECT dv FROM DichVu dv WHERE dv.id = :id OR dv.tenDV = :tenDV", DichVu.class);
            query.setParameter("id", sp.getMaDV());
            query.setParameter("tenDV", sp.getTenDV());
            return query.getSingleResult() != null;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSLDV(DichVu mh, int sl){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE DichVu d SET d.soLuongDV = :soLuongDV WHERE d.maDV = :maDV";

            int n = em.createQuery(jpql)
                    .setParameter("soLuongDV", sl)
                    .setParameter("maDV", mh.getMaDV())
                    .executeUpdate();

            tx.commit();
            updated = n > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public int getTongTienThang(int thang, int nam) {
        try {
            TypedQuery<Double> query = em.createQuery(
                    "SELECT SUM(od.soLuongDV * dv.giaDV) " +
                            "FROM CTHoaDon od " +
                            "JOIN od.hoaDon o " +
                            "JOIN od.dichVu dv " +
                            "WHERE FUNCTION('MONTH', o.thoiGianLapHD) = :thang " +
                            "AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam",
                    Double.class
            );
            query.setParameter("thang", thang);
            query.setParameter("nam", nam);

            Double result = query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Object[]> getMHThang(int thang, int nam) {
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT dv.maDV, dv.tenDV, SUM(od.soLuongDV), SUM(od.soLuongDV * dv.giaDV) " +
                            "FROM CTHoaDon od " +
                            "JOIN od.hoaDon o " +
                            "JOIN od.dichVu dv " +
                            "WHERE FUNCTION('MONTH', o.thoiGianLapHD) = :thang " +
                            "AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam " +
                            "GROUP BY dv.maDV, dv.tenDV " +
                            "ORDER BY dv.maDV ASC",
                    Object[].class
            );

            query.setParameter("thang", thang);
            query.setParameter("nam", nam);

            return query.getResultList();  // List<Object[]> { maDV, tenDV, soLuong, tongTien }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object[]> getMHNam(int nam) {
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT dv.maDV, dv.tenDV, SUM(od.soLuongDV), SUM(od.soLuongDV * dv.giaDV) " +
                            "FROM CTHoaDon od " +
                            "JOIN od.hoaDon o " +
                            "JOIN od.dichVu dv " +
                            "WHERE FUNCTION('YEAR', o.thoiGianLapHD) = :nam " +
                            "GROUP BY dv.maDV, dv.tenDV " +
                            "ORDER BY dv.maDV ASC",
                    Object[].class
            );

            query.setParameter("nam", nam);
            return query.getResultList();  // List<Object[]>: {maDV, tenDV, tongSoLuong, tongTien}
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double getTongTienNam(int nam) {
        try {
            TypedQuery<Double> query = em.createQuery(
                    "SELECT SUM(tongTien) FROM (SELECT dv.maDV, dv.tenDV, SUM(od.soLuongDV * dv.giaDV) as tongTien FROM CTHoaDon od JOIN HoaDon o ON od.hoaDon = o JOIN DichVu dv ON od.dichVu = dv WHERE FUNCTION('YEAR', o.thoiGianLapHD) = :nam GROUP BY dv.maDV, dv.tenDV as subquery",
                    Double.class
            );
            query.setParameter("nam", nam);
            return query.getSingleResult() != null ? query.getSingleResult() : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public String getLatestID() {

        String id = null;
        try {
            String jpql = "SELECT d.maDV FROM DichVu d ORDER BY d.maDV DESC";

            id = em.createQuery(jpql, String.class)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            id = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    @Override
    public DichVu timDichVuTheoMaHoacTheoTen(String maOrTen) {
        EntityManager em = JPAUtil.getEntityManager();
        DichVu dv = null;
        try {
            String jpql = "SELECT d FROM DichVu d " +
                    "WHERE LOWER(d.maDV) = LOWER(:maOrTen) " +
                    "OR LOWER(d.tenDV) = LOWER(:maOrTen)";

            dv = em.createQuery(jpql, DichVu.class)
                    .setParameter("maOrTen", maOrTen)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dv;
    }


}
