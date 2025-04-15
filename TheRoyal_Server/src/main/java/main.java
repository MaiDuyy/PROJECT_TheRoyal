import dao.PhongDAO;
import dao.TaiKhoanDAO;
import dao.impl.PhongDAOImpl;
import entity.Phong;
import entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class main {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("theroyal")
                .createEntityManager();

//        PhongDAO phongDAO = new PhongDAOImpl(em, Phong.class);
//        List<Phong> list = phongDAO.getPhongTheoMaPhong("P101");
//        System.out.println(list);
        List<TaiKhoan> list = TaiKhoanDAO.getInstance().getAll();


    }
}
