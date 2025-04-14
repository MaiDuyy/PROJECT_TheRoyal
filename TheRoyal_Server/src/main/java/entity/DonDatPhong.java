package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    @Column(name = "maDDP", columnDefinition = "VARCHAR(50)")
    @EqualsAndHashCode.Include
    private String maDDP;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime thoiGianDatPhong;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime thoiGianNhanPhong;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime thoiGianTraPhong;

    // Mối quan hệ n - 1 với KhachHang
    @ManyToOne
    @JoinColumn(name = "maKH")
    private KhachHang khachHang;

    // Mối quan hệ n - 1 với Phong
    @ManyToOne
    @JoinColumn(name = "maPhong")
    private Phong phong;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String trangThai;

    @Column(name = "soNguoiLon", columnDefinition = "INT")
    private int soNguoiLon;

    @Column(name = "soTreEm", columnDefinition = "INT")
    private int soTreEm;

    // Mối quan hệ 1 - n với HoaDon
    @OneToMany(mappedBy = "donDatPhong")
    private Set<HoaDon> dsHoaDon;
}
