package ir.hadi.sample.simpleshop.data.repository;

import ir.hadi.sample.simpleshop.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
