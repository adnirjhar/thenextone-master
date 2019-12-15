package com.thenextone.config.models;

import java.util.List;

public class ConfigDTO {

    private Integer id;
    private String name;
    private String code;
    private String type;
    private ConfigDTO parent;
    private List<ConfigDTO> children;

    public ConfigDTO(String name, String code, String type, ConfigDTO parent, List<ConfigDTO> children) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.parent = parent;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ConfigDTO getParent() {
        return parent;
    }

    public void setParent(ConfigDTO parent) {
        this.parent = parent;
    }

    public List<ConfigDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ConfigDTO> children) {
        this.children = children;
    }
}
