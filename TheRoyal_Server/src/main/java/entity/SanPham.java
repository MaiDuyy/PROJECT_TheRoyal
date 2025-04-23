package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "SanPham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class SanPham  implements Serializable {

    @Id
    @GenericGenerator(name = "sequence_sp_id", strategy = "geneid.SanPhamIdGenerator")
    @GeneratedValue(generator = "sequence_sp_id")
    @Column(name = "maSP", nullable = false, columnDefinition = "varchar(50)")
//    @EqualsAndHashCode.Include
    private String maSP;

    @Column(name = "tenSP", columnDefinition = "nvarchar(255)")
    private String tenSP;

    @Column(name = "moTa", columnDefinition = "nvarchar(255)")
    private String moTa;

    @Column(name = "giaSP", columnDefinition = "DECIMAL(18, 2) CHECK (giaSP >= 0)")
    private double giaSP;

    @Column(name = "soLuongSP", columnDefinition = "INT CHECK (soLuongSP >= 0)")
    private int soLuongSP;

    @Column(name = "trangThai", columnDefinition = "nvarchar(50)")
    private String trangThai;

    // Mối quan hệ 1-n với CTHoaDon
    @OneToMany(mappedBy = "sanPham")
    private Set<CTHoaDon> ctHoaDon;


    public SanPham(String tenSP, String moTa, double giaSP, int soLuongSP, String trangThai) {
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.giaSP = giaSP;
        this.soLuongSP = soLuongSP;
        this.trangThai = trangThai;
    }
}
