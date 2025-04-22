package dao.impl;

import dao.NhanVienDAO;
import entity.NhanVien;
import entity.Phong;
import entity.TaiKhoan;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import util.JPAUtil;

import java.util.List;

public class NhanVienDAOImpl extends GenericDAOImpl<NhanVien, String> implements NhanVienDAO {
    public NhanVienDAOImpl(Class<NhanVien> clazz) {
        super(clazz);
    }

    public NhanVienDAOImpl(EntityManager em, Class<NhanVien> clazz) {
        super(em, clazz);
    }

    @Override
    public List<NhanVien> getListNhanVien() {
        try {
            TypedQuery<NhanVien> query = em.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<NhanVien> getNhanVienTheoMa(String maTK) {
        try {
            TypedQuery<NhanVien> query = em.createQuery(
                "SELECT nv FROM NhanVien nv WHERE nv.taiKhoan.maTK = :maTK", 
                NhanVien.class
            );
            query.setParameter("maTK", maTK);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<NhanVien> getNhanVienTheoMa(String maNV, boolean isAdmin) {
        try {
            TypedQuery<NhanVien> query = em.createQuery(
                "SELECT nv FROM NhanVien nv WHERE nv.maNV = :maNV", 
                NhanVien.class
            );
            query.setParameter("maNV", maNV);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public NhanVien getNhanVienTheoMaNV(String maNV) {
        try {
            return em.find(NhanVien.class, maNV);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getLatestID() {
        try {
            TypedQuery<String> query = em.createQuery(
                "SELECT nv.maNV FROM NhanVien nv ORDER BY nv.maNV DESC", 
                String.class
            );
            query.setMaxResults(1);
            List<String> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public boolean update(NhanVien nv) {
        try {
            em.getTransaction().begin();
            em.merge(nv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String maNV) {
        try {
            em.getTransaction().begin();
            NhanVien nv = em.find(NhanVien.class, maNV);
            if (nv != null) {
                em.remove(nv);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NhanVien findById(String maNV) {
        try {
            return em.find(NhanVien.class, maNV);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public boolean capNhatTaiKhoanNhanVien(String maNV, String maTK) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {
            tx.begin();

            String jpql = "UPDATE NhanVien n SET n.taiKhoan.maTK = :maTK WHERE n.maNV = :maNV";

            int n = em.createQuery(jpql)
                    .setParameter("maTK", maTK)
                    .setParameter("maNV", maNV)
                    .executeUpdate();

            tx.commit();
            updated = n > 0;
        } catch (PersistenceException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return updated;
    }

    @Override
    public String getTaiKhoanCuaNhanVien(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        String maTK = null;

        try {
            String jpql = "SELECT nv.taiKhoan.maTK FROM NhanVien nv WHERE nv.maNV = :maNV ";

            maTK = em.createQuery(jpql, String.class)
                    .setParameter("maNV", maNV)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return maTK;
    }

    @Override
    public boolean insert(NhanVien nv) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;

        try {
            tx.begin();
            // Persist the new HoaDon
            em.persist(nv);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close(); // RẤT QUAN TRỌNG
        }

        return success;
    }
} 