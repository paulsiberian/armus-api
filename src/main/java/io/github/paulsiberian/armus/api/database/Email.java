/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "EMAILS", schema = "PUBLIC", catalog = "DATABASE")
public class Email {

    private Long id;
    private String value;
    private Employee employee;

    public Email() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    public Employee getEmployee() {
        return employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return getId().equals(email.getId()) &&
                getValue().equals(email.getValue()) &&
                getEmployee().equals(email.getEmployee());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getEmployee());
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", employee=" + employee +
                '}';
    }
}
