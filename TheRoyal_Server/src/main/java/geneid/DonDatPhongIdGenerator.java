package geneid;

import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import util.JPAUtil;

public class DonDatPhongIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Bước 1: Tìm maDDP lớn nhất hiện tại
            String jpql = "SELECT d.maDDP FROM DonDatPhong d ORDER BY d.maDDP DESC";
            String latestMaDDP = em.createQuery(jpql, String.class)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            // Bước 2: Sinh maDDP mới
            int nextNumber = 1;
            if (latestMaDDP != null && latestMaDDP.length() >= 5) {
                String numberPart = latestMaDDP.substring(3); // Bỏ "DDP"
                try {
                    nextNumber = Integer.parseInt(numberPart) + 1;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return String.format("DDP%02d", nextNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
