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
@Table(name = "KhuyenMai")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class KhuyenMai implements Serializable {

    @Id

    @GenericGenerator(name = "sequence_km_id", strategy = "geneid.KhuyenMaiIdGenerator")
    @GeneratedValue(generator = "sequence_km_id")
    @Column(name = "maKM", columnDefinition = "VARCHAR(50)")
    @EqualsAndHashCode.Include
    private String maKM;

    @Column(name = "tenKM", columnDefinition = "NVARCHAR(50)")
    private String tenKM;

    @Column(name = "giaTriKhuyenMai", columnDefinition = "FLOAT CHECK (giaTriKhuyenMai > 0)")
    private float giaTriKhuyenMai;

    @Column(name = "thoiGianBatDau", columnDefinition = "DATETIME")
    private Date thoiGianBatDau;

    @Column(name = "thoiGianKetThuc", columnDefinition = "DATETIME")
    private Date thoiGianKetThuc;

    @Column(name = "moTaKM", columnDefinition = "NVARCHAR(50)")
    private String moTaKM;

    @Column(name = "soLuong", columnDefinition = "INT CHECK (soLuong >= 0)")
    private int soLuong;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String trangThai;

    @OneToMany(mappedBy = "khuyenMai" ,cascade = CascadeType.ALL)
    private Set<HoaDon> dsHoaDon;

    public KhuyenMai(String tenKM, float giaTriKhuyenMai, Date thoiGianBatDau, Date thoiGianKetThuc, String moTaKM, int soLuong, String trangThai) {
        this.tenKM = tenKM;
        this.giaTriKhuyenMai = giaTriKhuyenMai;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.moTaKM = moTaKM;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }
}
