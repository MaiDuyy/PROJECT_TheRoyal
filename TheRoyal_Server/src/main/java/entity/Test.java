package entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Test {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("theroyal")
                .createEntityManager();

        EntityTransaction tr = em.getTransaction();
    }
}