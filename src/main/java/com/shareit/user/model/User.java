package com.shareit.user.model;

import com.shareit.common.model.AuditingModel;
import com.shareit.user.utility.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
//@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Audited
@Table(name = "users")
public class User extends AuditingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

//    @OneToMany(mappedBy = "id")
//    private List<Item> items;

    @PrePersist
    public void setDefaultValues() {
        if (userRole == null) {
            userRole = UserRole.valueOf("USER");
        }
    }

}

