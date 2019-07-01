/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.database;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEE_POSITIONS", schema = "PUBLIC", catalog = "DATABASE")
public class EmployeePosition {

    private Long id;
    private String position;
    private Integer workloadStandad;
    private List<Employee> employees;

    public EmployeePosition() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setWorkloadStandad(Integer workloadStandad) {
        this.workloadStandad = workloadStandad;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "POSITION")
    public String getPosition() {
        return position;
    }

    @Column(name = "WORKLOAD_STANDARD", nullable = false, columnDefinition = "INTEGER default 0")
    public Integer getWorkloadStandad() {
        if (workloadStandad == null) {
            return 0;
        }
        return workloadStandad;
    }

    @OneToMany(mappedBy = "position")
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePosition that = (EmployeePosition) o;
        return getId().equals(that.getId()) &&
                getPosition().equals(that.getPosition()) &&
                getWorkloadStandad().equals(that.getWorkloadStandad());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPosition(), getWorkloadStandad());
    }

    @Override
    public String toString() {
        return position;
    }
}
