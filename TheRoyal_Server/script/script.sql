SELECT h.maHD FROM HoaDon h ORDER BY h.maHD DESC;
DELETE FROM CTHoaDon WHERE maHD IN (SELECT maHD FROM HoaDon);
DELETE FROM HoaDon;
DELETE FROM HoaDon WHERE maDDP IN (SELECT maDDP FROM DonDatPhong);
DELETE FROM DonDatPhong;

Select * from DonDatPhong where trangThai =  'Đang ở';

ALTER TABLE KhuyenMai ALTER COLUMN trangThai NVARCHAR(50);

UPDATE Phong
SET trangThai = N'Phòng trống'
    WHERE maPhong= 'P204';



