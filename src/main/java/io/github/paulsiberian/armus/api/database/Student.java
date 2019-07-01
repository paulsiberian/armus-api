/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "STUDENTS", schema = "PUBLIC", catalog = "DATABASE")
public class Student {

    private Long id;
    private Person person;
    private Group group;

    public Student() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setGroup(Group group) {
        this.group = group;
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
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")
    public Group getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(person, student.person) &&
                Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, group);
    }

    @Override
    public String toString() {
        return person +
                " (группа " + group +
                ')';
    }
}
