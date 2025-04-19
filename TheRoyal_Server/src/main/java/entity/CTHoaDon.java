package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
@Table(name = "CTHoaDon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class CTHoaDon implements Serializable {
    @Id
    @GenericGenerator(name = "sequence_cthd_id", strategy = "geneid.CTHoaDonIdGenerator")
    @GeneratedValue(generator = "sequence_cthd_id")
    @Column(name = "maCTHD", columnDefinition = "VARCHAR(50)")
    @EqualsAndHashCode.Include
    private String maCTHD;

    @ManyToOne
    @JoinColumn(name = "maHD")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "maSP")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "maDV")
    private DichVu dichVu;

    @Column(name = "soLuongSP", columnDefinition = "INT CHECK (soLuongSP >= 0)")
    private int soLuongSP;

    @Column(name = "soLuongDV", columnDefinition = "INT CHECK (soLuongDV >= 0)")
    private int soLuongDV;

    @Column(name = "tongTienSP", columnDefinition = "DECIMAL(18, 2) CHECK (tongTienSP >= 0)")
    private double tongTienSP;

    @Column(name = "tongTienDV", columnDefinition = "DECIMAL(18, 2) CHECK (tongTienDV >= 0)")
    private double tongTienDV;

    public CTHoaDon(HoaDon hoaDon, SanPham sanPham, DichVu dichVu, int soLuongSP, int soLuongDV, double tongTienSP, double tongTienDV) {
        this.hoaDon = hoaDon;
        this.sanPham = sanPham;
        this.dichVu = dichVu;
        this.soLuongSP = soLuongSP;
        this.soLuongDV = soLuongDV;
        this.tongTienSP = tongTienSP;
        this.tongTienDV = tongTienDV;
    }
}
