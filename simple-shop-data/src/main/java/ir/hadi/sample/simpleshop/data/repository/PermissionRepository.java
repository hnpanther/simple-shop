package ir.hadi.sample.simpleshop.data.repository;

import ir.hadi.sample.simpleshop.data.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    public Optional<Permission> findPermissionByTitle(String title);
}
