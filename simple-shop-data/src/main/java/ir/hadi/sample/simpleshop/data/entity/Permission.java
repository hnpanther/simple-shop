package ir.hadi.sample.simpleshop.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "permission")
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public boolean addRole(Role role) {
        return this.roles.add(role);
    }

    public boolean removeRole(Role role) {
        return this.roles.add(role);
    }

    public boolean addUser(User user) {
        return this.users.add(user);
    }

    public boolean removeUser(User user) {
        return this.users.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission permission = (Permission) o;
        if(id != null) {
            return id.equals(permission.id);
        }
        return title.equals(permission.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
