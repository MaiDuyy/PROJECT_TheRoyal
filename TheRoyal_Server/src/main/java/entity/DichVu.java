package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "DichVu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class DichVu  implements Serializable {

    @Id
    @Column(name = "maDV", nullable = false, columnDefinition = "varchar(50)")
    @EqualsAndHashCode.Include
    private String maDV;

    @Column(name = "tenDV", columnDefinition = "nvarchar(255)")
    private String tenDV;

    @Column(name = "moTa", columnDefinition = "nvarchar(255)")
    private String moTa;

    @Column(name = "giaDV", columnDefinition = "DECIMAL(18, 2) CHECK (giaDV >= 0)")
    private double giaDV;

    @Column(name = "soLuongDV", columnDefinition = "INT CHECK (soLuongDV >= 0)")
    private int soLuongDV;

    @Column(name = "trangThai", columnDefinition = "nvarchar(50)")
    private String trangThai;

    // Mối quan hệ 1-n với CTHoaDon
    @OneToMany(mappedBy = "dichVu")
    private Set<CTHoaDon> ctHoaDon;
}
