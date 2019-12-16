package com.thenextone.core.services;

import com.thenextone.core.entities.Group;
import com.thenextone.core.entities.Privilege;
import com.thenextone.core.entities.Role;
import com.thenextone.core.entities.User;
import com.thenextone.core.models.UserDTO;
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
        final String masterEmail = "superadmin@app.com";
        final String masterPassword = "12345";
        User masterUser = userService.findUserByEmail(masterEmail);

        if (masterUser == null) {
            Privilege fetchPrivileges = this.privilegeService.save(new Privilege("Fetch Privileges - Privilege", "PRIVILEGE_FETCH_PRIVILEGE"));
            Privilege addPrivilege = this.privilegeService.save(new Privilege("Add Privilege - Privilege", "PRIVILEGE_ADD_PRIVILEGE"));
            Privilege updatePrivilege = this.privilegeService.save(new Privilege("Update Privilege - Privilege", "PRIVILEGE_UPDATE_PRIVILEGE"));
            Privilege deletePrivilege = this.privilegeService.save(new Privilege("Delete Privilege - Privilege", "PRIVILEGE_DELETE_PRIVILEGE"));

            Privilege fetchRoles = this.privilegeService.save(new Privilege("Fetch Roles - Privilege", "PRIVILEGE_FETCH_ROLE"));
            Privilege addRolePrivilege = this.privilegeService.save(new Privilege("Add Role - Privilege", "PRIVILEGE_ADD_ROLE"));
            Privilege updateRolePrivilege = this.privilegeService.save(new Privilege("Update Role - Privilege", "PRIVILEGE_UPDATE_ROLE"));
            Privilege deleteRolePrivilege = this.privilegeService.save(new Privilege("Delete Role - Privilege", "PRIVILEGE_DELETE_ROLE"));

            Privilege fetchGroups = this.privilegeService.save(new Privilege("Fetch Groups - Privilege", "PRIVILEGE_FETCH_GROUP"));
            Privilege addGroupPrivilege = this.privilegeService.save(new Privilege("Add Group - Privilege", "PRIVILEGE_ADD_GROUP"));
            Privilege updateGroupPrivilege = this.privilegeService.save(new Privilege("Update Group - Privilege", "PRIVILEGE_UPDATE_GROUP"));
            Privilege deleteGroupPrivilege = this.privilegeService.save(new Privilege("Delete Group - Privilege", "PRIVILEGE_DELETE_GROUP"));

            Privilege fetchUsers = this.privilegeService.save(new Privilege("Fetch Users - Privilege", "PRIVILEGE_FETCH_USER"));
            Privilege addUserPrivilege = this.privilegeService.save(new Privilege("Add User - Privilege", "PRIVILEGE_ADD_USER"));
            Privilege updateUserPrivilege = this.privilegeService.save(new Privilege("Update User - Privilege", "PRIVILEGE_UPDATE_USER"));
            Privilege deleteUserPrivilege = this.privilegeService.save(new Privilege("Delete User - Privilege", "PRIVILEGE_DELETE_USER"));

            Role b2bAdmin = this.roleService.save(new Role("B2B Admin Role", "ADMIN_ROLE",Arrays.asList(
                    fetchPrivileges,addPrivilege,updatePrivilege,deletePrivilege,
                    fetchRoles,addRolePrivilege,updateRolePrivilege,deleteRolePrivilege,
                    fetchGroups,addGroupPrivilege,updateGroupPrivilege,deleteGroupPrivilege,
                    fetchUsers,addUserPrivilege,updateUserPrivilege,deleteUserPrivilege
            )));
            Role b2bManager = this.roleService.save(new Role("B2B Manager Role", "MANAGER_ROLE",Arrays.asList(
                    fetchPrivileges,
                    fetchRoles,
                    fetchGroups,
                    fetchUsers,addUserPrivilege,updateUserPrivilege,deleteUserPrivilege
            )));
            Role b2bBasic = this.roleService.save(new Role("B2B Basic Role", "BASIC_ROLE",Arrays.asList(
                    fetchPrivileges,
                    fetchRoles,
                    fetchGroups,
                    fetchUsers
            )));

            Group newAdminGroup = this.groupService.save(new Group("Admin Group", "ADMIN_GROUP", Arrays.asList(
                    b2bAdmin
            )));
            Group managerGroup  = this.groupService.save(new Group("Manager Group", "MANAGER_GROUP", Arrays.asList(
                    b2bManager
            )));
            Group basicGroup  = this.groupService.save(new Group("Basic Group", "BASIC_GROUP", Arrays.asList(
                    b2bBasic
            )));

            UserDTO user = new UserDTO("Super","Admin", masterEmail, masterPassword, Arrays.asList(newAdminGroup));
            userService.addUser(user);

            logger.info("============================ Admin User Added - " + masterEmail + " ==========================");
        }
    }
}
