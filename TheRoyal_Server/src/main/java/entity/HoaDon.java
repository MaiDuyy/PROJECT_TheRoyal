package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "HoaDon")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)

public class HoaDon implements Serializable  {
    @Id
    @GenericGenerator(name = "sequence_hoadon_id", strategy = "geneid.HoaDonIdGenerator")
    @GeneratedValue(generator = "sequence_hoadon_id")
    @Column(name = "maHD", columnDefinition = "VARCHAR(50)")
    @EqualsAndHashCode.Include
    private String maHD;

    // Mối quan hệ n - 1 với KhachHang
    @ManyToOne
    @JoinColumn(name = "maKH")
    private KhachHang khachHang;

    // Mối quan hệ n - 1 với Phong
    @ManyToOne
    @JoinColumn(name = "maPhong")
    private Phong phong;

    // Mối quan hệ n - 1 với NhanVien
    @ManyToOne
    @JoinColumn(name = "maNV")
    private NhanVien nhanVien;

    // Mối quan hệ n - 1 với DonDatPhong
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maDDP")
    private DonDatPhong donDatPhong;

    // Mối quan hệ n - 1 với KhuyenMai
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maKM")
    private KhuyenMai khuyenMai;

    @Column(name = "thoiGianLapHD", columnDefinition = "DATETIME")
    private Date thoiGianLapHD;

    @Column(name = "tienPhong", columnDefinition = "DECIMAL(18, 2) CHECK (tienPhong >= 0)")
    private double tienPhong;

    @Column(name = "tienPhat", columnDefinition = "DECIMAL(18, 2) CHECK (tienPhat >= 0)")
    private double tienPhat;

    @Column(name = "tienKhuyenMai", columnDefinition = "DECIMAL(18, 2) CHECK (tienKhuyenMai >= 0)")
    private double tienKhuyenMai;

    @Column(name = "tienDichVu", columnDefinition = "DECIMAL(18, 2) CHECK (tienDichVu >= 0)")
    private double tienDichVu;

    @Column(name = "tienSanPham", columnDefinition = "DECIMAL(18, 2) CHECK (tienSanPham >= 0)")
    private double tienSanPham;

    @Column(name = "tongTien", columnDefinition = "DECIMAL(18, 2) CHECK (tongTien >= 0)")
    private double tongTien;

    @Column(name = "trangThai", columnDefinition = "NVARCHAR(50)")
    private String trangThai;

    @OneToMany(mappedBy = "hoaDon")
    private Set<CTHoaDon> ctHoaDon;

    public HoaDon(KhachHang khachHang, Phong phong, NhanVien nhanVien, DonDatPhong donDatPhong, KhuyenMai khuyenMai, Date thoiGianLapHD, double tienPhong, double tienPhat, double tienKhuyenMai, double tienDichVu, double tienSanPham, double tongTien, String trangThai) {
        this.khachHang = khachHang;
        this.phong = phong;
        this.nhanVien = nhanVien;
        this.donDatPhong = donDatPhong;
        this.khuyenMai = khuyenMai;
        this.thoiGianLapHD = thoiGianLapHD;
        this.tienPhong = tienPhong;
        this.tienPhat = tienPhat;
        this.tienKhuyenMai = tienKhuyenMai;
        this.tienDichVu = tienDichVu;
        this.tienSanPham = tienSanPham;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }


}
