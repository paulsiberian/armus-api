/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PERSONS", schema = "PUBLIC", catalog = "DATABASE")
public class Person {

    private Long id;
    private String surname;
    private String name;
    private String patronymic;
    private List<Phone> phones;
    private List<Email> emails;

    public Person() {
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

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
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

    @OneToMany(mappedBy = "person")
    public List<Phone> getPhones() {
        return phones;
    }

    @OneToMany(mappedBy = "person")
    public List<Email> getEmails() {
        return emails;
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
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(name, person.name) &&
                Objects.equals(patronymic, person.patronymic) &&
                Objects.equals(phones, person.phones) &&
                Objects.equals(emails, person.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, phones, emails);
    }

    @Override
    public String toString() {
        return surname + ' ' + name + ' ' + patronymic;
    }
}
