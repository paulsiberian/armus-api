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
    private Person person;

    public Email() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPerson(Person person) {
        this.person = person;
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
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    public Person getPerson() {
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return getId().equals(email.getId()) &&
                getValue().equals(email.getValue()) &&
                getPerson().equals(email.getPerson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getPerson());
    }

    @Override
    public String toString() {
        return value + ' ' + person;
    }
}
