package ma.eniad.pfaback.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "rolse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private ma.eniad.pfaback.model.enums.Role name;

}