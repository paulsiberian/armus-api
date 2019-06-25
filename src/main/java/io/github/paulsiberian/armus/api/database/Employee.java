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
    private String surname;
    private String name;
    private String patronymic;
    private Cathedra cathedra;
    private EmployeePosition position;
    private List phones;
    private List emails;
    private List disciplines;

    public Employee() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    @Column(name = "PATRONYMIC")
    public String getPatronymic() {
        return patronymic;
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

    @OneToMany(mappedBy = "employee")
    public List<Phone> getPhones() {
        return phones;
    }

    @OneToMany(mappedBy = "employee")
    public List<Email> getEmails() {
        return emails;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "EMPLOYEES_DISCIPLINES",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "DISCIPLINE_ID"))
    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    @Transient
    public String initials() {
        StringBuilder builder = new StringBuilder();
        builder.append(name.charAt(0)).append('.');
        if (!patronymic.isEmpty()) builder.append(' ').append(patronymic.charAt(0)).append('.');
        return builder.toString();
    }

    @Transient
    public String fullname() {
        StringBuilder builder = new StringBuilder();
        builder.append(surname).append(' ').append(name);
        if (!patronymic.isEmpty()) builder.append(' ').append(patronymic);
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getId().equals(employee.getId()) &&
                getSurname().equals(employee.getSurname()) &&
                getName().equals(employee.getName()) &&
                getPatronymic().equals(employee.getPatronymic()) &&
                getCathedra().equals(employee.getCathedra()) &&
                getPosition().equals(employee.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSurname(), getName(), getPatronymic(), getCathedra(), getPosition());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", cathedra=" + cathedra +
                ", position=" + position +
                '}';
    }
}
