package ir.hadi.sample.simpleshop.repository;

import ir.hadi.sample.simpleshop.data.entity.Permission;
import ir.hadi.sample.simpleshop.data.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {

    }

    @Test
    public void createPermissionTest() {
        Permission p1 = new Permission();
        p1.setTitle("READ_USER");
        this.permissionRepository.save(p1);

        this.entityManager.flush();
        this.entityManager.clear();

        Permission p2 = readPermission("READ_USER");

        assertEquals(p2.getTitle(), p1.getTitle());

    }

    @Test
    public void createNotUniquePermissionTest() {
        initPermission();
        Permission p1 = new Permission();
        p1.setTitle("READ_USER");
        try {
            this.permissionRepository.save(p1);
            this.entityManager.flush();
            fail();
        } catch (PersistenceException e) {
            // pass test
        }

    }

    @Test
    public void updateExistingPermissionTest() {
        initPermission();
        Permission p1 = readPermission("READ_USER");
        int id = p1.getId();
        p1.setTitle("READ_ROLE");
        this.permissionRepository.save(p1);

        this.entityManager.flush();
        this.entityManager.clear();

        Permission findP1 = readPermission("READ_ROLE");
        assertEquals(id, findP1.getId());

    }

    @Test
    public void readNotExistingPermissionTest() {
        Optional<Permission> op1 = this.permissionRepository.findPermissionByTitle("WRITE_PERMISSION");
        if(op1.isPresent()) {
            fail();
        }
    }

    @Test
    public void deletePermissionTest() {
        initPermission();
        Permission p1 = readPermission("READ_USER");
        this.permissionRepository.delete(p1);

        this.entityManager.flush();
        this.entityManager.clear();

        Optional<Permission> op1 = this.permissionRepository.findPermissionByTitle("READ_USER");
        if(op1.isPresent()) {
            fail();
        }

    }


    @Test
    public void equlasTest() {
        Permission p1 = new Permission();
        p1.setTitle("READ_USER");
        p1.setId(1);

        Permission p2 = new Permission();
        p2.setTitle("WRITE_USER");
        p2.setId(2);

        Permission p3 = new Permission();
        p3.setTitle("READ_USER");

        Permission p4 = new Permission();
        p4.setId(1);

        Permission p5 = new Permission();
        p5.setId(1);
        p5.setTitle("TEST");

        Permission p6 = new Permission();
        p6.setId(1);
        p6.setTitle("READ_USER");

        assertEquals(p3, p1);
        assertNotEquals(p1, p3);

        assertEquals(p1, p4);
        assertEquals(p1, p5);
        assertEquals(p1, p6);

        Set<Permission> permissions = new HashSet<>();
        boolean b1 = permissions.add(p1);
        permissions.add(p2);
        permissions.add(p3);
        permissions.add(p5);
        boolean b6 = permissions.add(p6);
        assertTrue(b1);
        assertFalse(b6);
        assertEquals(3, permissions.size());
    }


    private Permission readPermission(String title) {
        Optional<Permission> op1 = this.permissionRepository.findPermissionByTitle(title);
        if(op1.isEmpty()) {
            fail();
        }
        return op1.get();
    }


    private void initPermission() {
        Permission p1 = new Permission();
        p1.setTitle("READ_USER");
        Permission p2 = new Permission();
        p2.setTitle("WRITE_USER");

        this.permissionRepository.saveAll(Arrays.asList(p1,p2));

        this.entityManager.flush();
        this.entityManager.clear();

    }
}
