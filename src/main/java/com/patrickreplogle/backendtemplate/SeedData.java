package com.patrickreplogle.backendtemplate;

import com.patrickreplogle.backendtemplate.models.Role;
import com.patrickreplogle.backendtemplate.models.User;
import com.patrickreplogle.backendtemplate.models.UserRoles;
import com.patrickreplogle.backendtemplate.services.RoleService;
import com.patrickreplogle.backendtemplate.services.UserService;
import com.patrickreplogle.backendtemplate.util.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role r1 = new Role(Mappings.ADMIN_ROLE);
        Role r2 = new Role(Mappings.USER_ROLE);

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);

        User user1 = new User("user1", "password", "user1@email.com", "John", "Doe", "Portland", "97219");
        user1.getRoles().add(new UserRoles(user1, r1));
        user1.getRoles().add(new UserRoles(user1, r2));

        User user2 = new User("user2", "password", "user2@email.com", "Jane", "Doe", "Los Angeles", "90210");
        user2.getRoles().add(new UserRoles(user2, r2));
        user2.getRoles().add(new UserRoles(user2, r1));

        userService.save(user1);
        userService.save(user2);

    }
}
