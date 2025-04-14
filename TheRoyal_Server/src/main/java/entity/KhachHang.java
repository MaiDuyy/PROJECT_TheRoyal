package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "KhachHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class KhachHang  implements Serializable {

    @Id
    @Column(name = "maKH", nullable = false, columnDefinition = "varchar(50)")
    @EqualsAndHashCode.Include
    private String maKH;

    @Column(name = "tenKH", columnDefinition = "nvarchar(255)")
    private String tenKH;

    @Column(name = "sDT", columnDefinition = "varchar(50)")
    private String sDT;

    @Column(name = "loaiKH", columnDefinition = "nvarchar(50)")
    private String loaiKH;

    @Column(name = "CCCD", columnDefinition = "varchar(50)")
    private String CCCD;

    @Column(name = "gioiTinh", columnDefinition = "BIT CHECK (gioiTinh IN (0, 1))")
    private boolean gioiTinh;

    // Mối quan hệ 1-n với DonDatPhong
    @OneToMany(mappedBy = "khachHang")
    private Set<DonDatPhong> dsDonDatPhong;

    // Mối quan hệ 1-n với HoaDon
    @OneToMany(mappedBy = "khachHang")
    private Set<HoaDon> dsHoaDon;
}
