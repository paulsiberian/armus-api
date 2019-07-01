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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institute institute = (Institute) o;
        return getId().equals(institute.getId()) &&
                getName().equals(institute.getName()) &&
                getDescription().equals(institute.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return name;
    }
}
