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

    @ManyToMany(mappedBy = "permissions")
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.add(role);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        if(id == null || that.id == null) {
            return title.equals(that.title);
        }
        return id.equals(that.id) && title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
