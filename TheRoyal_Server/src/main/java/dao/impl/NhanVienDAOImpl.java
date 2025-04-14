package dao.impl;

import dao.NhanVienDAO;
import entity.NhanVien;
import entity.Phong;
import entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
        try {
            em.getTransaction().begin();
            NhanVien nv = em.find(NhanVien.class, maNV);
            if (nv != null) {
                TaiKhoan tk = em.find(TaiKhoan.class, maTK);
                if (tk != null) {
                    nv.setTaiKhoan(tk);
                    em.merge(nv);
                    em.getTransaction().commit();
                    return true;
                }
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
    public String getTaiKhoanCuaNhanVien(String maNV) {
        try {
            NhanVien nv = em.find(NhanVien.class, maNV);
            return nv != null && nv.getTaiKhoan() != null ? nv.getTaiKhoan().getMaTK() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 