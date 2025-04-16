package dao;

import entity.DichVu;

import java.util.List;

public interface DichVuDAO extends GenericDAO<DichVu, String> {
    DichVu getDichVuByMaDV(String maDV);

    List<DichVu> getDichVuByMaHoacTen(String maDV);

    List<DichVu> getDichVuTheoTen(String tenDV);

    boolean DichVuTonTai(DichVu sp);

    boolean updateSLDV(DichVu mh, int sl);

    int getTongTienThang(int thang, int nam);

    List<Object[]> getMHThang(int thang, int nam);

    List<Object[]> getMHNam(int nam);

    Double getTongTienNam(int nam);

    public String getLatestID();


}
