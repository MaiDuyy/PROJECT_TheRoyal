package geneid;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import util.JPAUtil;

public class HoaDonIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT h.maHD FROM HoaDon h ORDER BY h.maHD DESC";
            String latestMaHD = em.createQuery(jpql, String.class)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            int nextNumber = 1;
            if (latestMaHD != null && latestMaHD.length() >= 4) {
                String numberPart = latestMaHD.substring(2); // Bỏ "HD"
                try {
                    nextNumber = Integer.parseInt(numberPart) + 1;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return String.format("HD%02d", nextNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
