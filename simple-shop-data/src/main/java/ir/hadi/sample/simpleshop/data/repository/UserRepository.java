package ir.hadi.sample.simpleshop.data.repository;

import ir.hadi.sample.simpleshop.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String username);
}
