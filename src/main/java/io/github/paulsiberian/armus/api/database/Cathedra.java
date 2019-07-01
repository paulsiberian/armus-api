/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CATHEDRAS", schema = "PUBLIC", catalog = "DATABASE")
public class Cathedra {

    private Long id;
    private String name;
    private String description;
    private Institute institute;
    private List<Employee> employees;

    public Cathedra() {
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

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTITUTE_ID", referencedColumnName = "ID")
    public Institute getInstitute() {
        return institute;
    }

    @OneToMany(mappedBy = "cathedra")
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cathedra cathedra = (Cathedra) o;
        return getId().equals(cathedra.getId()) &&
                getName().equals(cathedra.getName()) &&
                getDescription().equals(cathedra.getDescription()) &&
                getInstitute().equals(cathedra.getInstitute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getInstitute());
    }

    @Override
    public String toString() {
        return name +
                " (институт " + institute +
                ')';
    }
}
