package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "LoaiPhong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)

public class LoaiPhong implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "varchar(50)")
    private String maLoai;

    @Column(columnDefinition = "nvarchar(255)")
    private String tenLoai;

    @OneToMany(mappedBy = "loaiPhong", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Phong> phong;


    public LoaiPhong(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }
}
