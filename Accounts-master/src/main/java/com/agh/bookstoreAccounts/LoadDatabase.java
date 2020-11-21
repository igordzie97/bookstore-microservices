package com.agh.bookstoreAccounts;



import com.agh.bookstoreAccounts.Admin.AdminFunc;
import com.agh.bookstoreAccounts.Role.RoleService;
import com.agh.bookstoreAccounts.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminFunc adminFunc;
    @Bean
    CommandLineRunner initDatabase(UserRepository repo){
        if(roleService.checkRoles()) {
            roleService.makeRoles();
        }
        adminFunc.addAdminAccIfNotExist();
        return args -> {
            log.info("Database loaded successfully");
        };

    }
}
