package com.restapiwithjwt.storage.entity;

import lombok.Data;



import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Maps to a role table which which is mapped by user
 * @author Nihad
 */
@Entity
@Table(name = "role")
@Data
public class RoleEntity {
    @Override
    public String toString() {
        return "RoleEntity{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;
}
