package com.thenextone.core.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    public CustomPermissionEvaluator() {}

    @Override
    public boolean hasPermission(Authentication authentication, Object permissionType, Object permissionValue) {
        final String type = permissionType.toString();
        final List<String> values = Arrays.asList(permissionValue.toString().split(","));

        if (!authentication.isAuthenticated()) return false;

        Object authenticationObj = authentication.getPrincipal();
        if (!(authenticationObj instanceof SpringUser)) {
            return false;
        }

        SpringUser user = (SpringUser)authentication.getPrincipal();
        switch(type) {
            case "GROUP":
                return this.userHasGroup(user, values);
            case "ROLE":
                return this.userHasRole(user, values);
            case "PRIVILEGE":
                return this.userHasPrivilege(user, values);
            default:
                return false;
        }
    }

    private boolean userHasGroup(SpringUser user, List<String> groups) {
        return user.getGroups().stream().filter(group -> {
            return groups.stream().filter(groupCode -> {
                return groupCode.equals(group.getCode());
            }).count() > 0;
        }).count() > 0;
    }

    private boolean userHasRole(SpringUser user, List<String> roleCodes) {
        return user.getRoles().stream().filter(role -> {
            return roleCodes.stream().filter(roleCode -> {
                return roleCode.equals(role.getCode());
            }).count() > 0;
        }).count() > 0;
    }

    private boolean userHasPrivilege(SpringUser user, List<String> privilegeCodes) {
        return user.getPrivileges().stream().filter(privilege -> {
            return privilegeCodes.stream().filter(privilegeCode -> {
                return privilegeCode.equals(privilege.getCode());
            }).count() > 0;
        }).count() > 0;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
