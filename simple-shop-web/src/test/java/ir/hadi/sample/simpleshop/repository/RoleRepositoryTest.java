package ir.hadi.sample.simpleshop.repository;

import ir.hadi.sample.simpleshop.data.entity.Permission;
import ir.hadi.sample.simpleshop.data.entity.Role;
import ir.hadi.sample.simpleshop.data.repository.PermissionRepository;
import ir.hadi.sample.simpleshop.data.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {
        Permission p1 = new Permission();
        p1.setTitle("READ_USER");
        Permission p2 = new Permission();
        p2.setTitle("WRITE_USER");
        Permission p3 = new Permission();
        p3.setTitle("CHANGE_PASSWORD");

        this.permissionRepository.saveAll(Arrays.asList(p1, p2, p3));
        this.entityManager.flush();
        this.entityManager.clear();
    }

    @Test
    public void createNewRoleTest() {
        Role r1 = new Role();
        r1.setRoleName("USER");

        this.roleRepository.save(r1);
        this.entityManager.flush();
        int id = r1.getId();
        this.entityManager.clear();

        Role findRold = findRole(null, id);

        assertEquals(findRold.getRoleName(), r1.getRoleName());

    }

    @Test
    public void createNotUniqueRoleTest() {
        Role r1 = new Role();
        r1.setRoleName("USER");

        try {

            this.roleRepository.save(r1);
            this.entityManager.flush();
            this.entityManager.clear();

        } catch (PersistenceException e) {
            // pass test
        }

    }

    @Test
    public void readNotExistingRoleTest() {
        Optional<Role> findRole = this.roleRepository.findRoleByRoleNameOrId("USER", 2);
        if(findRole.isPresent()) {
            fail();
        }
    }

    @Test
    public void updateRoleTest() {
        initRole();
        Role r1 = findRole("USER", 0);
        r1.setRoleName("MANAGER");
        this.roleRepository.save(r1);
        this.entityManager.flush();
        this.entityManager.clear();

        Role r2 = findRole("MANAGER",0);
    }

    @Test
    public void createRoleByPermission() {

        Optional<Permission> fp1 = this.permissionRepository.findPermissionByTitle("READ_USER");
        Optional<Permission> fp2 = this.permissionRepository.findPermissionByTitle("WRITE_USER");

        if(fp1.isEmpty() || fp2.isEmpty()) {
            fail();
        }

        Permission p1 = fp1.get();
        Permission p2 = fp2.get();


        Role role = new Role();
        role.setRoleName("MANAGER");

    }




    private Role findRole(String roleName, int id) {
        Optional<Role> findRole = this.roleRepository.findRoleByRoleNameOrId(roleName, id);
        if(findRole.isEmpty()) {
            fail();
        }
        return findRole.get();
    }

    private void initRole() {
        Role r1 = new Role();
        r1.setRoleName("USER");
        Role r2 = new Role();
        r2.setRoleName("ADMIN");

        this.roleRepository.saveAll(Arrays.asList(r1, r2));
        this.entityManager.flush();
        this.entityManager.clear();
    }

}
