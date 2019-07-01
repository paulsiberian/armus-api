/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "DISCIPLINES", schema = "PUBLIC", catalog = "DATABASE")
public class Discipline {

    private Long id;
    private String name;
    private List<Employee> employees;
    private List<Group> groups;

    public Discipline() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "EMPLOYEES_DISCIPLINES",
            joinColumns = @JoinColumn(name = "DISCIPLINE_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    public List<Employee> getEmployees() {
        return employees;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "GROUPS_DISCIPLINES",
            joinColumns = @JoinColumn(name = "DISCIPLINE_ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(employees, that.employees) &&
                Objects.equals(groups, that.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employees, groups);
    }

    @Override
    public String toString() {
        return name;
    }
}
