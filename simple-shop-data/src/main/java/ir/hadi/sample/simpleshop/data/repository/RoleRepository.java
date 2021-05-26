package ir.hadi.sample.simpleshop.data.repository;

import ir.hadi.sample.simpleshop.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Optional<Role> findRoleByRoleName(String roleName);

    public Optional<Role> findRoleByRoleNameOrId(String roleName, int id);
}
