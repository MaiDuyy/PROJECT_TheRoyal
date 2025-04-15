package dao;

import dao.impl.SanPhamDAOImpl;
import entity.SanPham;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

import java.rmi.Remote;
import java.util.Date;
import java.util.List;

public interface SanPhamDAO extends GenericDAO<SanPham, String> , Remote {


    static SanPhamDAO getInstance() {
        return new SanPhamDAOImpl(JPAUtil.getEntityManager(), SanPham.class);
    }



    List<SanPham> getSanPhamTheoMa(String maSP);

    List<SanPham> getSanPhamTheoLoai(String tenSP);



    default String generateNewId() {
        try {
            TypedQuery<String> query = JPAUtil.getEntityManager().createQuery(
                    "SELECT sp.maSP FROM SanPham sp ORDER BY sp.maSP DESC",
                    String.class);
            query.setMaxResults(1);
            List<String> results = query.getResultList();

            if (results.isEmpty()) {
                return "SP01";
            }

            String lastId = results.get(0);
            int number = Integer.parseInt(lastId.substring(2)) + 1;
            return "SP" + String.format("%02d", number);
        } catch (Exception e) {
            e.printStackTrace();
            return "SP01"; // Default fallback
        }
    }

    String getLatestID();

    boolean xoaSanPham(SanPham spXoa);

    boolean suaSanPham(SanPham sp);

    boolean updateSLSP(SanPham sp, int sl);

    boolean SanPhamTonTai(SanPham sp);

    SanPham getSanPhamTheoMaHoacTen(String maOrTen);

    int getTongTienThang(String thang, String nam);

    List<String[]> getSPNgay(Date ngay);

    List<String[]> getMHThang(String thang, String nam);

    List<String[]> getMHNam(String nam);

    int getTongTienSPNam(String nam);
}
