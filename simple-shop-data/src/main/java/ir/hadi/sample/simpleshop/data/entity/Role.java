package ir.hadi.sample.simpleshop.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<Permission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();

    public boolean addUser(User user) {
        return this.users.add(user);
    }

    public boolean removeUser(User user) {
        return this.users.remove(user);
    }

    public boolean addPermission(Permission permission) {
        return this.permissions.add(permission);
    }

    public boolean removePermission(Permission permission) {
        return this.permissions.remove(permission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        if(id != null) {
            return id.equals(role.getId());
        }
        return roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
