package entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
//@ToString
public class Phong implements Serializable {
    @Id
    @Column(name = "maPhong", nullable = false, columnDefinition = "varchar(50)")
    @EqualsAndHashCode.Include
    private String maPhong;

    @Column(name = "tenPhong", columnDefinition = "nvarchar(255)")
    private String tenPhong;

    @ManyToOne
    @JoinColumn(name = "maLoai", columnDefinition = "varchar(50)")
    private LoaiPhong loaiPhong;

    @Column(name = "soGiuong", columnDefinition = "INT CHECK (soGiuong > 0)")
    private int soGiuong;

    @Column(name = "giaTien", columnDefinition = "DECIMAL(18, 2) CHECK (giaTien >= 0)")
    private double giaTien;

    @Column(name = "soNguoiLon", columnDefinition = "INT CHECK (soNguoiLon >= 1)")
    private int soNguoiLon;

    @Column(name = "soTreEm", columnDefinition = "INT CHECK (soTreEm >= 0)")
    private int soTreEm;

    @Column(name = "trangThai", columnDefinition = "nvarchar(255)")
    private String trangThai;

    @OneToMany(mappedBy = "phong")
    private Set<HoaDon> dsHoaDon;

    @OneToMany(mappedBy = "phong")
    private Set<DonDatPhong> dsDonDatPhong;
}
