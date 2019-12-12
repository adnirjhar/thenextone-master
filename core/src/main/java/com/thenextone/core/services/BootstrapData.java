package com.thenextone.core.services;

import com.thenextone.core.entities.Group;
import com.thenextone.core.entities.Privilege;
import com.thenextone.core.entities.Role;
import com.thenextone.core.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BootstrapData {

    final Logger logger = LoggerFactory.getLogger(BootstrapData.class);

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PrivilegeService privilegeService;

    @EventListener(ApplicationReadyEvent.class)
    private void loadUserOnStart() throws Exception {
        final String masterEmail = environment.getProperty("master.admin.email");
        final String masterPassword = environment.getProperty("master.admin.password");
        User masterUser = userService.findUserByEmail(masterEmail);

        if (masterUser == null) {

            Privilege read = this.privilegeService.save(new Privilege("Read Privilege", "READ_PRIVILEGE"));
            Privilege write = this.privilegeService.save(new Privilege("Write Privilege", "WRITE_PRIVILEGE"));
            Privilege force = this.privilegeService.save(new Privilege("Force Privilege", "FORCE_PRIVILEGE"));

            Role admin = this.roleService.save(new Role("Admin Role", "ADMIN",Arrays.asList(write,force)));
            Role general = this.roleService.save(new Role("Basic User role", "BASIC",Arrays.asList(read)));

            Group newAdminGroup = this.groupService.save(new Group("Admin Group", "ADMIN", Arrays.asList(admin, general)));

//            admin.setGroup(Arrays.asList());
//            general.setGroup(newAdminGroup);
//            this.roleService.save(admin);
//            this.roleService.save(general);

            User user = new User("Super","Admin", masterEmail, masterPassword, Arrays.asList(newAdminGroup));
            userService.addUser(user);

            logger.info("============================ Admin User Added - " + masterEmail + " ==========================");
        }
    }
}
