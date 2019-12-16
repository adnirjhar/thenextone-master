package com.thenextone.config.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "configurations")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties("child")
    @Nullable
    private Config parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("parent")
    @Column(name = "children")
    private List<Config> child;

    public Config() {}

    public Config(String name, String code, String type, String value) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.value = value;
    }

    public Config(String name, String code, String type, String value, Config parent) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.value = value;
        this.parent = parent;
    }

    public Config(String name, String code, String type, String value, Config parent, List<Config> child) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.value = value;
        this.parent = parent;
        this.child = child;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Config getParent() {
        return parent;
    }

    public void setParent(Config parent) {
        this.parent = parent;
    }

    public List<Config> getChild() {
        return child;
    }

    public void setChild(List<Config> child) {
        this.child = child;
    }
}
