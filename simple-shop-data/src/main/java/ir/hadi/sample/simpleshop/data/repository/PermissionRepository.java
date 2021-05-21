package ir.hadi.sample.simpleshop.data.repository;

import ir.hadi.sample.simpleshop.data.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
