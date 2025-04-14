package service;

import entity.LoaiPhong;

public interface LoaiPhongService extends GenericService<LoaiPhong, String> {
    LoaiPhong getLoaiPhongTheoMa(String maLoai);
}
