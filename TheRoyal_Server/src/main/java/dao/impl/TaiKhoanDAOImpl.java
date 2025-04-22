package dao.impl;

import dao.GenericDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.Phong;
import entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAOImpl extends GenericDAOImpl<TaiKhoan ,String> implements TaiKhoanDAO {
    public TaiKhoanDAOImpl(Class<TaiKhoan> clazz) {
        super(clazz);
    }

    public TaiKhoanDAOImpl(EntityManager em, Class<TaiKhoan> clazz) {
        super(em, clazz);
    }





    
    @Override public List<TaiKhoan> getTaiKhoanTheoMaTK(String maTK) {
        List<TaiKhoan> dsTaiKhoan = new ArrayList<>();
        try {
            TypedQuery<TaiKhoan> query = em.createQuery("SELECT tk FROM TaiKhoan tk WHERE tk.maTK = :maTK", TaiKhoan.class);
            query.setParameter("maTK", maTK);
            dsTaiKhoan = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsTaiKhoan;
    }
    
    @Override public List<TaiKhoan> getTaiKhoanTheoMaTKNhanVien(String maTK) {
        List<TaiKhoan> dsTaiKhoan = new ArrayList<>();
        try {
            TypedQuery<TaiKhoan> query = em.createQuery(
                "SELECT tk FROM TaiKhoan tk WHERE tk.maTK = (SELECT nv.taiKhoan.maTK FROM NhanVien nv WHERE nv.taiKhoan.maTK = :maTK)",
                TaiKhoan.class);
            query.setParameter("maTK", maTK);
            dsTaiKhoan = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsTaiKhoan;
    }
    
    @Override public TaiKhoan getTaiKhoanTheoMaTk(String maTK) {
        TaiKhoan taiKhoan = null;
        try {
            TypedQuery<TaiKhoan> query = em.createQuery("SELECT tk FROM TaiKhoan tk WHERE tk.maTK = :maTK", TaiKhoan.class);
            query.setParameter("maTK", maTK);
            List<TaiKhoan> results = query.getResultList();
            if (!results.isEmpty()) {
                taiKhoan = results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taiKhoan;
    }
    
    @Override public TaiKhoan getTaiKhoanTheoMaNv(String maNV) {
        TaiKhoan taiKhoan = null;
        try {
            TypedQuery<TaiKhoan> query = em.createQuery(
                "SELECT tk FROM TaiKhoan tk JOIN NhanVien nv ON tk.maTK = nv.taiKhoan.maTK WHERE nv.maNV = :maNV",
                TaiKhoan.class);
            query.setParameter("maNV", maNV);
            List<TaiKhoan> results = query.getResultList();
            if (!results.isEmpty()) {
                taiKhoan = results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taiKhoan;
    }
    
    @Override public boolean updateMatKhau(TaiKhoan tk) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            TaiKhoan existingTK = em.find(TaiKhoan.class, tk.getMaTK());
            if (existingTK != null) {
                existingTK.setMatKhau(tk.getMatKhau());
                existingTK.setLoaiTaiKhoan(tk.getLoaiTaiKhoan());
                em.merge(existingTK);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    



    @Override public boolean validateLogin(String userName, String password) {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(tk) FROM TaiKhoan tk WHERE tk.maTK = :userName AND tk.matKhau = :password", 
                Long.class);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            Long count = query.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override public boolean updatePass(String email, String password) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query query = em.createQuery(
                "UPDATE TaiKhoan tk SET tk.matKhau = :password " +
                "WHERE tk.maTK = (SELECT nv.taiKhoan.maTK FROM NhanVien nv WHERE nv.email = :email)");
            query.setParameter("password", password);
            query.setParameter("email", email);
            int updatedCount = query.executeUpdate();
            transaction.commit();
            return updatedCount > 0;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override public boolean capnhatMK(String cccd, String matkhauMoi) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query query = em.createQuery(
                "UPDATE TaiKhoan tk SET tk.matKhau = :matkhauMoi " +
                "WHERE tk.maTK = (SELECT nv.taiKhoan.maTK FROM NhanVien nv WHERE nv.email = :cccd)");
            query.setParameter("matkhauMoi", matkhauMoi);
            query.setParameter("cccd", cccd);
            int updatedCount = query.executeUpdate();
            transaction.commit();
            return updatedCount > 0;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override public boolean checkCCCD(String cccd) {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(nv) FROM NhanVien nv WHERE nv.taiKhoan.nhanVien.CCCD = :cccd",
                Long.class);
            query.setParameter("cccd", cccd);
            Long count = query.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(TaiKhoan tk) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;

        try {
            tx.begin();
            // Persist the new HoaDon
            em.persist(tk);
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