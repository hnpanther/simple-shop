package ir.hadi.sample.simpleshop.repository;

import ir.hadi.sample.simpleshop.data.entity.Permission;
import ir.hadi.sample.simpleshop.data.repository.PermissionRepository;
import ir.hadi.sample.simpleshop.data.repository.RoleRepository;
import ir.hadi.sample.simpleshop.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserPartTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Value("${example.firstProperty}") private String firstProperty;

    @Test
    public void createPermission() {
        Permission p1 = new Permission();
        p1.setTitle("READ_USERS");
        this.permissionRepository.save(p1);

        this.testEntityManager.flush();
        this.testEntityManager.clear();

        Permission findP1 = this.permissionRepository.findPermissionByTitle("READ_USERS").get();
        System.out.println(firstProperty);
        assertEquals(p1.getTitle(), findP1.getTitle());
    }



    private void initData() {

    }
}
