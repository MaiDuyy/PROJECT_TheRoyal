package geneid;

import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import util.JPAUtil;

public class SanPhamIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Bước 1: Tìm maDDP lớn nhất hiện tại
            String jpql = "SELECT sp.maSP FROM SanPham sp ORDER BY sp.maSP DESC";
            String latestMaCTHD = em.createQuery(jpql, String.class)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);


            // Bước 2: Sinh maDDP mới
            int nextNumber = 1;
            if (latestMaCTHD != null && latestMaCTHD.length() >= 4) { // "CTHD01" -> lấy từ ký tự thứ 5
                String numberPart = latestMaCTHD.substring(2);
                try {
                    nextNumber = Integer.parseInt(numberPart) + 1;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return String.format("SP%02d", nextNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
