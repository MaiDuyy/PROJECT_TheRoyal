package dao.impl;

import dao.CTHoaDonDAO;
import entity.CTHoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;
//import util.JPAUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class CTHoaDonDAOImpl extends GenericDAOImpl<CTHoaDon, String> implements CTHoaDonDAO {

    public CTHoaDonDAOImpl(Class<CTHoaDon> clazz) {
        super(clazz);
    }

    public CTHoaDonDAOImpl(EntityManager em, Class<CTHoaDon> clazz) {
        super(em, clazz);
    }

    @Override
    public List<CTHoaDon> getListCTHoaDonByMaHD(String maHDon) {
        EntityManager em = JPAUtil.getEntityManager();
        List<CTHoaDon> dataList = new ArrayList<>();
        try {
            String jpql = "SELECT c FROM CTHoaDon c WHERE c.hoaDon.maHD = :maHDon";

            dataList = em.createQuery(jpql, CTHoaDon.class)
                    .setParameter("maHDon", maHDon)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    public String getLatestID() {
        try {
            TypedQuery<String> query = em.createQuery(
                    "SELECT cthd.id FROM CTHoaDon cthd ORDER BY cthd.id DESC",
                    String.class
            );
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean checkIfCTHoaDonExists(CTHoaDon cthd) {
        EntityManager em = JPAUtil.getEntityManager();
        boolean exists = false;

        try {
            String jpql = "SELECT COUNT(c) FROM CTHoaDon c " +
                    "WHERE c.hoaDon.maHD = :maHD " +
                    "AND ( (c.sanPham IS NOT NULL AND c.sanPham.maSP = :maSP) " +
                    "   OR (c.dichVu IS NOT NULL AND c.dichVu.maDV = :maDV) )";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("maHD", cthd.getHoaDon().getMaHD())
                    .setParameter("maSP", cthd.getSanPham() != null ? cthd.getSanPham().getMaSP() : null)
                    .setParameter("maDV", cthd.getDichVu() != null ? cthd.getDichVu().getMaDV() : null)
                    .getSingleResult();

            exists = count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }


    @Override
    public boolean updateSLSP(CTHoaDon ctHoaDon) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {

            tx.begin();

            String jpql = "UPDATE CTHoaDon c " +
                    "SET c.soLuongSP = :soLuongSP, " +
                    "    c.tongTienSP = :tongTienSP " +
                    "WHERE c.maCTHD = :maCTHD " +
                    "AND c.sanPham.maSP = :maSP";

            int n = em.createQuery(jpql)
                    .setParameter("soLuongSP", ctHoaDon.getSoLuongSP())
                    .setParameter("tongTienSP", ctHoaDon.getTongTienSP())
                    .setParameter("maCTHD", ctHoaDon.getMaCTHD())
                    .setParameter("maSP", ctHoaDon.getSanPham().getMaSP())
                    .executeUpdate();

            tx.commit();
            updated = n > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean updateSLDV(CTHoaDon ctHoaDon) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean updated = false;

        try {

            tx.begin();

            String jpql = "UPDATE CTHoaDon c " +
                    "SET c.soLuongDV = :soLuongDV, " +
                    "    c.tongTienDV = :tongTienDV " +
                    "WHERE c.maCTHD = :maCTHD " +
                    "AND c.dichVu.maDV = :maDV";

            int n = em.createQuery(jpql)
                    .setParameter("soLuongDV", ctHoaDon.getSoLuongDV())
                    .setParameter("tongTienDV", ctHoaDon.getTongTienDV())
                    .setParameter("maCTHD", ctHoaDon.getMaCTHD())
                    .setParameter("maDV", ctHoaDon.getDichVu().getMaDV())
                    .executeUpdate();

            tx.commit();
            updated = n > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public CTHoaDon getCTHoaDonByMaDV(String maDV, String maHD) {
        CTHoaDon cthd = null;
        try {
            String jpql = "SELECT c FROM CTHoaDon c " +
                    "WHERE c.dichVu.maDV = :maDV " +
                    "AND c.hoaDon.maHD = :maHD";

            cthd = em.createQuery(jpql, CTHoaDon.class)
                    .setParameter("maDV", maDV)
                    .setParameter("maHD", maHD)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cthd;
    }

    @Override
    public CTHoaDon getCTHoaDonByMaSP(String maSP, String maHD) {
        CTHoaDon cthd = null;
        try {

            String jpql = "SELECT c FROM CTHoaDon c " +
                    "WHERE c.sanPham.maSP = :maSP " +
                    "AND c.hoaDon.maHD = :maHD";

            cthd = em.createQuery(jpql, CTHoaDon.class)
                    .setParameter("maSP", maSP)
                    .setParameter("maHD", maHD)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return cthd;
    }

    @Override
    public double getTongTienNam(int nam) {
        try {
            List<Double> top5TongTien = em.createQuery("""
                                SELECT SUM(od.soLuongDV * dv.giaDV) 
                                FROM CTHoaDon od
                                JOIN od.hoaDon o
                                JOIN od.dichVu dv
                                WHERE FUNCTION('YEAR', o.thoiGianLapHD) = :nam
                                GROUP BY dv.id, dv.tenDV
                                ORDER BY SUM(od.soLuongDV) DESC
                            """, Double.class)
                    .setParameter("nam", nam)
                    .setMaxResults(5)
                    .getResultList();

            double tongTatCaTienDVNam = top5TongTien.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            return tongTatCaTienDVNam;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double getTongTienThang(int thang, int nam) {
        try {
            List<Double> top5TongTien = em.createQuery("""
                                SELECT SUM(od.soLuongDV * dv.giaDV)
                                FROM CTHoaDon od
                                JOIN od.hoaDon o
                                JOIN od.dichVu dv
                                WHERE FUNCTION('MONTH', o.thoiGianLapHD) = :thang
                                  AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam
                                GROUP BY dv.id, dv.tenDV
                                ORDER BY SUM(od.soLuongDV) DESC
                            """, Double.class)
                    .setParameter("thang", thang)
                    .setParameter("nam", nam)
                    .setMaxResults(5)
                    .getResultList();

            double tongTatCaTienDVThang = top5TongTien.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            return tongTatCaTienDVThang;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double getTongDVTienNgay(Date date) {
        try {
            // Tách ngày, tháng, năm từ Date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int ngay = cal.get(Calendar.DAY_OF_MONTH);
            int thang = cal.get(Calendar.MONTH) + 1;
            int nam = cal.get(Calendar.YEAR);

            List<Double> top5TongTien = em.createQuery("""
                            SELECT SUM(od.soLuongDV * dv.giaDV)
                            FROM CTHoaDon od
                            JOIN od.hoaDon o
                            JOIN od.dichVu dv
                            WHERE FUNCTION('DAY', o.thoiGianLapHD) = :ngay
                              AND FUNCTION('MONTH', o.thoiGianLapHD) = :thang
                              AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam
                            GROUP BY dv.maDV, dv.tenDV
                            ORDER BY SUM(od.soLuongDV) DESC
                        """, Double.class)
                    .setParameter("ngay", ngay)
                    .setParameter("thang", thang)
                    .setParameter("nam", nam)
                    .setMaxResults(5)
                    .getResultList();

            return top5TongTien.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public boolean insert(CTHoaDon ctHoaDon) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean success = false;

        try {
            tx.begin();
            // Persist the new HoaDon
            em.persist(ctHoaDon);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public List<String[]> getTOPSPNam(String nam) {
        String sql = """
                SELECT TOP 5 
                    sp.maSP, 
                    sp.tenSP, 
                    SUM(od.soLuongSP) AS SoLuong, 
                    ROUND((SUM(od.soLuongSP) * 100.0 / 
                       (SELECT SUM(SoLuong) 
                        FROM 
                            (SELECT TOP 5 SUM(od2.soLuongSP) AS SoLuong 
                             FROM CTHoaDon od2 
                             JOIN HoaDon o2 ON od2.maHD = o2.maHD 
                             WHERE YEAR(o2.thoiGianLapHD) = :nam 
                             GROUP BY od2.maSP 
                             ORDER BY SUM(od2.soLuongSP) DESC) AS top5)), 2) AS TyLe, 
                    ROUND(SUM(od.soLuongSP * sp.giaSP), 2) AS TongTien
                FROM 
                    CTHoaDon od
                    JOIN HoaDon o ON od.maHD = o.maHD
                    JOIN SanPham sp ON od.maSP = sp.maSP
                WHERE 
                  YEAR(o.thoiGianLapHD) = :nam 
                GROUP BY 
                    sp.maSP, sp.tenSP 
                ORDER BY 
                    SUM(od.soLuongSP) DESC
                """;

        List<String[]> list = new ArrayList<>();

        try {
            List<Object[]> resultList = em.createNativeQuery(sql)
                    .setParameter("nam", nam)
                    .getResultList();

            for (Object[] row : resultList) {
                String[] stringRow = new String[row.length];
                for (int i = 0; i < row.length; i++) {
                    stringRow[i] = String.valueOf(row[i]); // chuyển từng phần tử sang String
                }
                list.add(stringRow);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log
        }
        return list;
    }


    @Override
    public List<String[]> getTopDichVuTheoNam(int nam) {
        String sql = """
                SELECT TOP 5 
                    dv.maDV, 
                    dv.tenDV, 
                    SUM(od.soLuongDV) AS SoLuong, 
                    ROUND((SUM(od.soLuongDV) * 100.0 / 
                       (SELECT SUM(SoLuong) 
                        FROM 
                            (SELECT TOP 5 SUM(od2.soLuongDV) AS SoLuong 
                             FROM CTHoaDon od2 
                             JOIN HoaDon o2 ON od2.maHD = o2.maHD 
                             WHERE YEAR(o2.thoiGianLapHD) = :nam 
                             GROUP BY od2.maDV 
                             ORDER BY SUM(od2.soLuongDV) DESC) AS top5)), 2) AS TyLe, 
                    ROUND(SUM(od.soLuongDV * dv.giaDV), 2) AS TongTien 
                FROM 
                    CTHoaDon od 
                    JOIN HoaDon o ON od.maHD = o.maHD 
                    JOIN DichVu dv ON od.maDV = dv.maDV 
                WHERE 
                    YEAR(o.thoiGianLapHD) = :nam 
                GROUP BY 
                    dv.maDV, dv.tenDV 
                ORDER BY 
                    SUM(od.soLuongDV) DESC
                """;

        List<String[]> list = new ArrayList<>();

        try {
            List<Object[]> resultList = em.createNativeQuery(sql)
                    .setParameter("nam", nam)
                    .getResultList();

            for (Object[] row : resultList) {
                String[] stringRow = new String[row.length];
                for (int i = 0; i < row.length; i++) {
                    stringRow[i] = String.valueOf(row[i]); // null-safe chuyển về String
                }
                list.add(stringRow);
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log
        }
        return list;
    }

    @Override
    public List<String[]> getTKSPNam(String nam) {
        List<String[]> list = new ArrayList<>();
        try {
            String jpql = """
                        SELECT sp.maSP, sp.tenSP, SUM(od.soLuongSP), ROUND(SUM(od.soLuongSP * sp.giaSP), 2)
                        FROM CTHoaDon od
                        JOIN od.hoaDon o
                        JOIN od.sanPham sp
                        WHERE FUNCTION('YEAR', o.thoiGianLapHD) = :nam
                        GROUP BY sp.maSP, sp.tenSP
                        ORDER BY SUM(od.soLuongSP) DESC
                    """;

            List<Object[]> resultList = em.createQuery(jpql)
                    .setParameter("nam", Integer.parseInt(nam))
                    .setMaxResults(5)
                    .getResultList();

            long tongSoLuongTop5 = resultList.stream()
                    .mapToLong(row -> ((Number) row[2]).longValue())
                    .sum();

            for (Object[] row : resultList) {
                String[] arr = new String[5];
                arr[0] = String.valueOf(row[0]);
                arr[1] = String.valueOf(row[1]);
                arr[2] = String.valueOf(row[2]);

                double tyLe = (tongSoLuongTop5 != 0)
                        ? ((Number) row[2]).doubleValue() * 100.0 / tongSoLuongTop5
                        : 0.0;
                arr[3] = String.format("%.2f", tyLe);

                arr[4] = String.valueOf(row[3]);
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public List<String[]> getTKDVNam(String nam) {
        List<String[]> list = new ArrayList<>();
        try {
            String jpql = """
                        SELECT dv.maDV, dv.tenDV, SUM(od.soLuongDV), ROUND(SUM(od.soLuongDV * dv.giaDV), 2)
                        FROM CTHoaDon od
                        JOIN od.hoaDon o
                        JOIN od.dichVu dv
                        WHERE FUNCTION('YEAR', o.thoiGianLapHD) = :nam
                        GROUP BY dv.maDV, dv.tenDV
                        ORDER BY SUM(od.soLuongDV) DESC
                    """;

            List<Object[]> resultList = em.createQuery(jpql)
                    .setParameter("nam", Integer.parseInt(nam))
                    .setMaxResults(5)
                    .getResultList();

            long tongSoLuongTop5 = resultList.stream()
                    .mapToLong(row -> ((Number) row[2]).longValue())
                    .sum();

            for (Object[] row : resultList) {
                String[] arr = new String[5];
                arr[0] = String.valueOf(row[0]);
                arr[1] = String.valueOf(row[1]);
                arr[2] = String.valueOf(row[2]);

                double tyLe = (tongSoLuongTop5 != 0)
                        ? ((Number) row[2]).doubleValue() * 100.0 / tongSoLuongTop5
                        : 0.0;
                arr[3] = String.format("%.2f", tyLe);

                arr[4] = String.valueOf(row[3]);
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<String[]> getTOPSPThang(String thang, String nam) {
        List<String[]> list = new ArrayList<>();
        try {
            String jpql = """
                        SELECT sp.maSP, sp.tenSP, SUM(od.soLuongSP), ROUND(SUM(od.soLuongSP * sp.giaSP), 2)
                        FROM CTHoaDon od
                        JOIN od.hoaDon o
                        JOIN od.sanPham sp
                        WHERE FUNCTION('MONTH', o.thoiGianLapHD) = :thang AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam
                        GROUP BY sp.maSP, sp.tenSP
                        ORDER BY SUM(od.soLuongSP) DESC
                    """;

            List<Object[]> resultList = em.createQuery(jpql)
                    .setParameter("thang", Integer.parseInt(thang))
                    .setParameter("nam", Integer.parseInt(nam))
                    .setMaxResults(5)  // tương đương SQL TOP 5
                    .getResultList();

            long tongSoLuongTop5 = resultList.stream()
                    .mapToLong(row -> ((Number) row[2]).longValue())
                    .sum();

            for (Object[] row : resultList) {
                String[] arr = new String[5];
                arr[0] = String.valueOf(row[0]);  // maSP
                arr[1] = String.valueOf(row[1]);  // tenSP
                arr[2] = String.valueOf(row[2]);  // SoLuong

                double tyLe = (tongSoLuongTop5 != 0) ? ((Number) row[2]).doubleValue() * 100.0 / tongSoLuongTop5 : 0.0;
                arr[3] = String.format("%.2f", tyLe);  // TyLe

                arr[4] = String.valueOf(row[3]);  // TongTien
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<String[]> getTOPDVThang(String thang, String nam) {
        List<String[]> list = new ArrayList<>();
        try {
            String jpql = """
                        SELECT dv.maDV, dv.tenDV, SUM(od.soLuongDV), ROUND(SUM(od.soLuongDV * dv.giaDV), 2)
                        FROM CTHoaDon od
                        JOIN od.hoaDon o
                        JOIN od.dichVu dv
                        WHERE FUNCTION('MONTH', o.thoiGianLapHD) = :thang 
                          AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam
                        GROUP BY dv.maDV, dv.tenDV
                        ORDER BY SUM(od.soLuongDV) DESC
                    """;

            List<Object[]> resultList = em.createQuery(jpql)
                    .setParameter("thang", Integer.parseInt(thang))
                    .setParameter("nam", Integer.parseInt(nam))
                    .setMaxResults(5) // tương đương TOP 5
                    .getResultList();

            long tongSoLuongTop5 = resultList.stream()
                    .mapToLong(row -> ((Number) row[2]).longValue())
                    .sum();

            for (Object[] row : resultList) {
                String[] arr = new String[5];
                arr[0] = String.valueOf(row[0]);  // maDV
                arr[1] = String.valueOf(row[1]);  // tenDV
                arr[2] = String.valueOf(row[2]);  // SoLuong

                double tyLe = (tongSoLuongTop5 != 0) ? ((Number) row[2]).doubleValue() * 100.0 / tongSoLuongTop5 : 0.0;
                arr[3] = String.format("%.2f", tyLe);  // TyLe

                arr[4] = String.valueOf(row[3]);  // TongTien
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<String[]> getTOPDVNgay(Date date) {
        List<String[]> list = new ArrayList<>();
        try {
            Calendar cal = Calendar.getInstance();
            int ngay = cal.get(Calendar.DAY_OF_MONTH);
            int thang = cal.get(Calendar.MONTH) + 1;  // Tháng tính từ 0 -> cần +1
            int nam = cal.get(Calendar.YEAR);

            String jpql = """
            SELECT dv.maDV, dv.tenDV, SUM(od.soLuongDV), ROUND(SUM(od.soLuongDV * dv.giaDV), 2)
            FROM CTHoaDon od
            JOIN od.hoaDon o
            JOIN od.dichVu dv
            WHERE FUNCTION('DAY', o.thoiGianLapHD) = :ngay
              AND FUNCTION('MONTH', o.thoiGianLapHD) = :thang
              AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam
            GROUP BY dv.maDV, dv.tenDV
            ORDER BY SUM(od.soLuongDV) DESC
        """;

            List<Object[]> resultList = em.createQuery(jpql)
                    .setParameter("ngay", ngay)
                    .setParameter("thang", thang)
                    .setParameter("nam", nam)
                    .setMaxResults(5)
                    .getResultList();

            long tongSoLuongTop5 = resultList.stream()
                    .mapToLong(row -> ((Number) row[2]).longValue())
                    .sum();

            for (Object[] row : resultList) {
                String[] arr = new String[5];
                arr[0] = String.valueOf(row[0]);
                arr[1] = String.valueOf(row[1]);
                arr[2] = String.valueOf(row[2]);

                double tyLe = (tongSoLuongTop5 != 0) ? ((Number) row[2]).doubleValue() * 100.0 / tongSoLuongTop5 : 0.0;
                arr[3] = String.format("%.2f", tyLe);

                arr[4] = String.valueOf(row[3]);
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<String[]> getTOPSPNgay(Date date) {
        List<String[]> list = new ArrayList<>();
        try {
            // Tách ngày, tháng, năm từ Date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int ngay = cal.get(Calendar.DAY_OF_MONTH);
            int thang = cal.get(Calendar.MONTH) + 1;  // Tháng tính từ 0 -> cần +1
            int nam = cal.get(Calendar.YEAR);

            String jpql = """
            SELECT sp.maSP, sp.tenSP, SUM(od.soLuongSP), ROUND(SUM(od.soLuongSP * sp.giaSP), 2)
            FROM CTHoaDon od
            JOIN od.hoaDon o
            JOIN od.sanPham sp
            WHERE FUNCTION('DAY', o.thoiGianLapHD) = :ngay
              AND FUNCTION('MONTH', o.thoiGianLapHD) = :thang
              AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam
            GROUP BY sp.maSP, sp.tenSP
            ORDER BY SUM(od.soLuongSP) DESC
        """;

            List<Object[]> resultList = em.createQuery(jpql)
                    .setParameter("ngay", ngay)
                    .setParameter("thang", thang)
                    .setParameter("nam", nam)
                    .setMaxResults(5)  // Lấy TOP 5 sản phẩm
                    .getResultList();

            long tongSoLuongTop5 = resultList.stream()
                    .mapToLong(row -> ((Number) row[2]).longValue())
                    .sum();

            for (Object[] row : resultList) {
                String[] arr = new String[5];
                arr[0] = String.valueOf(row[0]);  // maSP
                arr[1] = String.valueOf(row[1]);  // tenSP
                arr[2] = String.valueOf(row[2]);  // SoLuong

                double tyLe = (tongSoLuongTop5 != 0) ? ((Number) row[2]).doubleValue() * 100.0 / tongSoLuongTop5 : 0.0;
                arr[3] = String.format("%.2f", tyLe);  // Tỷ lệ %

                arr[4] = String.valueOf(row[3]);  // Tổng tiền
                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public double getTongTienSPNgay(Date date) {
        try {
            // Tách ngày, tháng, năm từ Date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int ngay = cal.get(Calendar.DAY_OF_MONTH);
            int thang = cal.get(Calendar.MONTH) + 1;
            int nam = cal.get(Calendar.YEAR);

            String jpql = """
            SELECT ROUND(SUM(od.soLuongSP * sp.giaSP), 2)
            FROM CTHoaDon od
            JOIN od.hoaDon o
            JOIN od.sanPham sp
            WHERE FUNCTION('DAY', o.thoiGianLapHD) = :ngay
              AND FUNCTION('MONTH', o.thoiGianLapHD) = :thang
              AND FUNCTION('YEAR', o.thoiGianLapHD) = :nam
            GROUP BY sp.maSP, sp.tenSP
            ORDER BY SUM(od.soLuongSP) DESC
        """;

            TypedQuery<Double> query = em.createQuery(jpql, Double.class);
            query.setParameter("ngay", ngay);
            query.setParameter("thang", thang);
            query.setParameter("nam", nam);
            query.setMaxResults(5);

            List<Double> top5TongTien = query.getResultList();

            return top5TongTien.stream().mapToDouble(Double::doubleValue).sum();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<String[]> getTOPDVNam(String nam) {
        List<String[]> list = new ArrayList<>();
        try {
            String subQuery = """
                SELECT SUM(od.soLuongDV) 
                FROM CTHoaDon od 
                JOIN od.hoaDon o 
                JOIN od.dichVu dv 
                WHERE FUNCTION('YEAR', o.thoiGianLapHD) = :nam 
                GROUP BY dv.maDV, dv.tenDV 
                ORDER BY SUM(od.soLuongDV) DESC
                """;

            List<Object> top5SoLuong = em.createQuery(subQuery)
                    .setParameter("nam", Integer.parseInt(nam))
                    .setMaxResults(5)
                    .getResultList();

            long tongSoLuongTop5 = top5SoLuong.stream()
                    .mapToLong(o -> ((Number) o).longValue())
                    .sum();

            String jpql = """
                SELECT dv.maDV, dv.tenDV, SUM(od.soLuongDV), ROUND(SUM(od.soLuongDV * dv.giaDV), 2)
                FROM CTHoaDon od 
                JOIN od.hoaDon o 
                JOIN od.dichVu dv 
                WHERE FUNCTION('YEAR', o.thoiGianLapHD) = :nam 
                GROUP BY dv.maDV, dv.tenDV 
                ORDER BY SUM(od.soLuongDV) DESC
                """;

            List<Object[]> resultList = em.createQuery(jpql)
                    .setParameter("nam", Integer.parseInt(nam))
                    .setMaxResults(5) // lấy top 5
                    .getResultList();

            for (Object[] row : resultList) {
                String[] arr = new String[5];
                arr[0] = String.valueOf(row[0]);  // maDV
                arr[1] = String.valueOf(row[1]);  // tenDV
                arr[2] = String.valueOf(row[2]);  // SoLuong

                double tyLe = (tongSoLuongTop5 != 0)
                        ? ((Number) row[2]).doubleValue() * 100.0 / tongSoLuongTop5
                        : 0.0;
                arr[3] = String.format("%.2f", tyLe);  // TyLe %

                arr[4] = String.valueOf(row[3]);  // TongTien

                list.add(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
