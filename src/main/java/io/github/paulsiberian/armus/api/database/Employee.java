/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEES", schema = "PUBLIC", catalog = "DATABASE")
public class Employee {

    private Long id;
    private Person person;
    private Cathedra cathedra;
    private EmployeePosition position;
    private List<Discipline> disciplines;

    public Employee() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    public Person getPerson() {
        return person;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CATHEDRA_ID", referencedColumnName = "ID")
    public Cathedra getCathedra() {
        return cathedra;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_ID", referencedColumnName = "ID")
    public EmployeePosition getPosition() {
        return position;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "EMPLOYEES_DISCIPLINES",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "DISCIPLINE_ID"))
    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(person, employee.person) &&
                Objects.equals(cathedra, employee.cathedra) &&
                Objects.equals(position, employee.position) &&
                Objects.equals(disciplines, employee.disciplines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, cathedra, position, disciplines);
    }

    @Override
    public String toString() {
        return person +
                " (кафедра " + cathedra +
                ", должность " + position +
                ')';
    }
}
