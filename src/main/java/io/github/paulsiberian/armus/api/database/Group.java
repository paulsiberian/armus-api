/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GROUPS", schema = "PUBLIC", catalog = "DATABASE")
public class Group {

    private Long id;
    private String acronym;
    private String directionOfTraining;
    private Institute institute;
    private List<Student> students;
    private List<Discipline> disciplines;

    public Group() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setDirectionOfTraining(String directionOfTraining) {
        this.directionOfTraining = directionOfTraining;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "ACRONYM")
    public String getAcronym() {
        return acronym;
    }

    @Column(name = "DIRECTION_OF_TRAINING")
    public String getDirectionOfTraining() {
        return directionOfTraining;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTITUTE_ID", referencedColumnName = "ID")
    public Institute getInstitute() {
        return institute;
    }

    @OneToMany(mappedBy = "group")
    public List<Student> getStudents() {
        return students;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "GROUPS_DISCIPLINES",
            joinColumns = @JoinColumn(name = "GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "DISCIPLINE_ID"))
    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) &&
                Objects.equals(acronym, group.acronym) &&
                Objects.equals(directionOfTraining, group.directionOfTraining) &&
                Objects.equals(institute, group.institute) &&
                Objects.equals(students, group.students) &&
                Objects.equals(disciplines, group.disciplines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, acronym, directionOfTraining, institute, students, disciplines);
    }

    @Override
    public String toString() {
        return acronym;
    }
}
