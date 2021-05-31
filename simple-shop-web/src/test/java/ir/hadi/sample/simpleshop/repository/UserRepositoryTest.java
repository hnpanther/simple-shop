package ir.hadi.sample.simpleshop.repository;

import ir.hadi.sample.simpleshop.data.entity.Permission;
import ir.hadi.sample.simpleshop.data.entity.Role;
import ir.hadi.sample.simpleshop.data.entity.User;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

        Permission anotherPermission = new Permission();
        anotherPermission.setTitle("ANOTHER_PERMISSION");

        this.permissionRepository.save(writeUser);
        this.permissionRepository.save(readUser);
        this.permissionRepository.save(anotherPermission);
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

    @Test
    public void createNewUserTest() {
        initData();

        Optional<User> findUser = this.userRepository.findByUsername("new_user");
        if(findUser.isEmpty()) {
            fail();
        }
        User user = findUser.get();
        assertEquals(1, user.getRoles().size());
        for(Role role: user.getRoles()) {
            for(Permission permission: role.getPermissions()) {
                System.out.println(permission);
            }
        }


    }

    @Test
    public void addRoleToUserTest() {
        initData();
        Role userRole = findRole("USER");

        User user = findUser("new_user");

        assertEquals(1, user.getRoles().size());

        user.addRole(userRole);
        userRole.addUser(user);
        this.userRepository.save(user);

        this.entityManager.flush();
        this.entityManager.clear();

        User updatedUser = findUser("new_user");

        assertEquals(2, user.getRoles().size());

        Set<Permission> permissionSet = new HashSet<>();

        for(Role role: updatedUser.getRoles()) {
            for(Permission p: role.getPermissions()) {
                permissionSet.add(p);
                System.out.println(p);
            }
        }

        assertEquals(2, permissionSet.size());
        System.out.println("=========================================");
        for(Permission p: permissionSet) {
            System.out.println(p);
        }


    }

    @Test
    public void deleteRoleFromUserTest() {
        initData();
        User user = findUser("new_user");
        assertEquals(1, user.getRoles().size());
        Role userRole = findRole("USER");
        Role adminRole = findRole("ADMIN");

        boolean deleteNonExistRole = user.removeRole(userRole);
        boolean deleteExistsRole = user.removeRole(adminRole);

        assertFalse(deleteNonExistRole);
        assertTrue(deleteExistsRole);

        this.entityManager.flush();
        this.entityManager.clear();

        User findUser = findUser("new_user");
        assertEquals(0, findUser.getRoles().size());

    }


    private void initData() {
        User newUser = new User();
        newUser.setUsername("new_user");
        newUser.setPassword("password");;


        Role adminRole = findRole("ADMIN");

        newUser.addRole(adminRole);
        adminRole.addUser(newUser);

        this.userRepository.save(newUser);

        this.entityManager.flush();
        this.entityManager.clear();
    }

    private User findUser(String username) {
        Optional<User> oUser = this.userRepository.findByUsername(username);
        if(oUser.isEmpty()) {
            fail();
        }
        return oUser.get();
    }

    private Role findRole(String roleName) {
        Optional<Role> oRole = this.roleRepository.findRoleByRoleName(roleName);
        if(oRole.isEmpty()) {
            fail();
        }
        return oRole.get();
    }
}
