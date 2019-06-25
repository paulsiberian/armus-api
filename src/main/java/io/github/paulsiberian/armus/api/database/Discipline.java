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
    private List employees;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
