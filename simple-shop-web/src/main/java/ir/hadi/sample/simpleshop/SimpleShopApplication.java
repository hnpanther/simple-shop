package ir.hadi.sample.simpleshop;

import ir.hadi.sample.simpleshop.data.entity.Permission;
import ir.hadi.sample.simpleshop.data.entity.User;

import ir.hadi.sample.simpleshop.data.repository.PermissionRepository;
import ir.hadi.sample.simpleshop.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleShopApplication implements ApplicationRunner {

    @Autowired
    private UserRepository userRepositry;

    @Autowired
    private PermissionRepository permissionRepository;



    public static void main(String[] args) {
        SpringApplication.run(SimpleShopApplication.class, args);



    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User();
//        user.setId(12L);
//        user.setId(2);
        user.setPassword("hadi123");
        user.setUsername("Hadi");

//        this.userRepositry.save(user);

        Permission permission1 = new Permission();
        permission1.setTitle("test132");

        this.permissionRepository.save(permission1);
    }
}
