/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "INSTITUTES", schema = "PUBLIC", catalog = "DATABASE")
public class Institute {

    private Long id;
    private String name;
    private String description;
    private List<Cathedra> cathedras;
    private List<Group> groups;

    public Institute() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCathedras(List<Cathedra> cathedras) {
        this.cathedras = cathedras;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    @OneToMany(mappedBy = "institute")
    public List<Cathedra> getCathedras() {
        return cathedras;
    }

    @OneToMany(mappedBy = "institute")
    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institute institute = (Institute) o;
        return Objects.equals(id, institute.id) &&
                Objects.equals(name, institute.name) &&
                Objects.equals(description, institute.description) &&
                Objects.equals(cathedras, institute.cathedras) &&
                Objects.equals(groups, institute.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, cathedras, groups);
    }

    @Override
    public String toString() {
        return name;
    }
}
