package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "DonDatPhong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class DonDatPhong implements Serializable {
    @Id
    @GenericGenerator(name = "sequence_dpp_id", strategy = "geneid.DonDatPhongIdGenerator")
    @GeneratedValue(generator = "sequence_dpp_id")
    @Column(name = "maDDP", columnDefinition = "VARCHAR(50)")
    @EqualsAndHashCode.Include
    private String maDDP;

    @Column(columnDefinition = "DATETIME")
    private Date thoiGianDatPhong;

    @Column(columnDefinition = "DATETIME")
    private Date thoiGianNhanPhong;

    @Column(columnDefinition = "DATETIME")
    private Date thoiGianTraPhong;

    // Mối quan hệ n - 1 với KhachHang
    @ManyToOne
    @JoinColumn(name = "maKH")
    private KhachHang khachHang;

    // Mối quan hệ n - 1 với Phong
    @ManyToOne
    @JoinColumn(name = "maPhong")
    private Phong phong;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String trangThai;

    @Column(name = "soLuongNgLon", columnDefinition = "INT")
    private int soNguoiLon;

    @Column(name = "soLuongTreEm", columnDefinition = "INT")
    private int soTreEm;

    // Mối quan hệ 1 - n với HoaDon
    @OneToMany(mappedBy = "donDatPhong", cascade = CascadeType.ALL)
    private Set<HoaDon> dsHoaDon;

    public DonDatPhong(Date thoiGianDatPhong, Date thoiGianNhanPhong, Date thoiGianTraPhong, KhachHang khachHang, Phong phong, String trangThai, int soNguoiLon, int soTreEm) {
        this.thoiGianDatPhong = thoiGianDatPhong;
        this.thoiGianNhanPhong = thoiGianNhanPhong;
        this.thoiGianTraPhong = thoiGianTraPhong;
        this.khachHang = khachHang;
        this.phong = phong;
        this.trangThai = trangThai;
        this.soNguoiLon = soNguoiLon;
        this.soTreEm = soTreEm;
    }
}
