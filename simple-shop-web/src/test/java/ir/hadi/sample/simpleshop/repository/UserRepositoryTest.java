package ir.hadi.sample.simpleshop.repository;

import ir.hadi.sample.simpleshop.data.entity.Permission;
import ir.hadi.sample.simpleshop.data.entity.Role;
import ir.hadi.sample.simpleshop.data.repository.PermissionRepository;
import ir.hadi.sample.simpleshop.data.repository.RoleRepository;
import ir.hadi.sample.simpleshop.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private TestEntityManager entityManager;

//    @Value("${example.firstProperty}") private String firstProperty;


    @BeforeEach
    void setUp() {

        Role userRole = new Role();
        userRole.setRoleName("USER");
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");

        Permission writeUser = new Permission();
        writeUser.setTitle("WRITE_USER");

        Permission readUser = new Permission();
        readUser.setTitle("READ_USER");

        this.permissionRepository.save(writeUser);
        this.permissionRepository.save(readUser);
        this.entityManager.flush();

        userRole.addPermission(readUser);
        readUser.addRole(userRole);

        adminRole.addPermission(readUser);
        adminRole.addPermission(writeUser);
        readUser.addRole(adminRole);
        writeUser.addRole(adminRole);

        this.roleRepository.save(adminRole);
        this.roleRepository.save(userRole);

        this.entityManager.flush();
        this.entityManager.clear();
    }

    @Test
    public void setUpTest() {
        Optional<Role> findAdminRole = this.roleRepository.findRoleByRoleName("ADMIN");
        if(findAdminRole.isEmpty()) {
            fail();
        }
        assertEquals(2, findAdminRole.get().getPermissions().size());
        System.out.println("=======================");
        for(Permission p: findAdminRole.get().getPermissions()) {
            System.out.println(p);
        }
        System.out.println("=======================");
    }


    private void initData() {

    }
}
