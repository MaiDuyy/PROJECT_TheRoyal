package entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "NhanVien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class NhanVien implements Serializable {
    @Id
    @Column(name = "maNV", nullable = false, columnDefinition = "varchar(50)")
    @EqualsAndHashCode.Include
    private String maNV;

    @Column(name = "tenNV", columnDefinition = "nvarchar(255)")
    private String tenNV;

    @Column(name = "gioiTinh", columnDefinition = "BIT CHECK (gioiTinh IN (0, 1))")
    private boolean gioiTinh;

    @Column(name = "CCCD", columnDefinition = "varchar(50)")
    private String CCCD;

    @Column(name = "ngaySinh", columnDefinition = "DATE")
    private Date ngaySinh;

    @Column(name = "sDT", columnDefinition = "varchar(50)")
    private String sDT;

    @Column(name = "email", columnDefinition = "varchar(255)")
    private String email;

    @OneToOne
    @JoinColumn(name = "maTK", columnDefinition = "nvarchar(50)")
    private TaiKhoan taiKhoan;

    @Column(name = "ngayVaoLam", columnDefinition = "DATE")
    private Date ngayVaoLam;

    @Column(name = "chucVu", columnDefinition = "nvarchar(50)")
    private String chucVu;

    @Column(name = "trangThai", columnDefinition = "nvarchar(50)")
    private String trangThai;

    @OneToMany(mappedBy = "nhanVien")
    private Set<HoaDon> dsHoaDon;

    public NhanVien(String maNV) {
        this.maNV = maNV;
    }
}
