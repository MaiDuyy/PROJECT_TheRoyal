package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TaiKhoan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "TaiKhoan.validataLogin", query = "SELECT t FROM TaiKhoan t WHERE t.maTK = :maTK AND t.matKhau = :matKhau"),
        @NamedQuery(name = "getListTaiKhoan", query = "SELECT t FROM TaiKhoan t")
})
public class TaiKhoan implements Serializable {
    @Id
    @Column(name = "maTK", nullable = false, columnDefinition = "nvarchar(50)")
    @EqualsAndHashCode.Include
    private String maTK;

    @Column(name = "matKhau", nullable = false, columnDefinition = "nvarchar(255)")
    private String matKhau;

    @Column(name = "loaiTaiKhoan", nullable = false, columnDefinition = "nvarchar(50)")
    private String loaiTaiKhoan;

    @OneToOne(mappedBy = "taiKhoan", cascade = CascadeType.ALL)
    private NhanVien nhanVien;

}

