package com.thenextone.core.models;

public enum PermissionType {
    GROUP ("GROUP"),
    ROLE ("ROLE"),
    PRIVILEGE ("PRIVILEGE");

    private final String name;

    PermissionType(String name) { this.name = name;}
}
