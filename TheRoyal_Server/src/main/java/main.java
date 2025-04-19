import dao.HoaDonDAO;
import dao.PhongDAO;
import dao.TaiKhoanDAO;
import dao.impl.HoaDonDAOImpl;
import dao.impl.PhongDAOImpl;
import entity.HoaDon;
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

        HoaDonDAO hoaDonDAO = new HoaDonDAOImpl(HoaDon.class);

          HoaDon hoaDon = hoaDonDAO.getHoaDonTheoPhong("P204");

        System.out.println(hoaDon.getMaHD());



    }
}
