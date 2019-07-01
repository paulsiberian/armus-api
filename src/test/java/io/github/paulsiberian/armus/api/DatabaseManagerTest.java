/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api;

import io.github.paulsiberian.armus.api.database.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseManagerTest {
    private final SettingsManager settings  = SettingsManager.getInstance();
    private Institute institute;
    private Cathedra cathedra;
    private Employee employee;
    private Person person;
    private Phone phone;
    private Email email;
    private EmployeePosition position;

    @BeforeEach
    void setUp() {
        settings.init(DatabaseManagerTest.class);

        institute = new Institute();
        institute.setName("ИТиАС");
        institute.setDescription("Институт информационных технологий и автосатизированных систем");

        cathedra = new Cathedra();
        cathedra.setName("АИС");
        cathedra.setDescription("Кафедра автоматизации и информационных систем");
        cathedra.setInstitute(institute);

        person = new Person();
        person.setSurname("Ляховец");
        person.setName("Михаил");
        person.setPatronymic("Васильевич");

        employee = new Employee();
        employee.setPerson(person);
        employee.setCathedra(cathedra);

        phone = new Phone();
        phone.setValue("+7923AAABBCC");
        phone.setPerson(person);

        email = new Email();
        email.setValue("layhovets@mail.ru");
        email.setPerson(person);

        position = new EmployeePosition();
        position.setPosition("Заведующий кафедрой, учёный секретарь кафедры (кандидат наук)");
    }

    @AfterEach
    void finishTests() {
        DatabaseManager.shutdown();
        SettingsManager.getInstance().save();
    }

    @Test
    void saveTest() {
        System.out.println(SettingsManager.getInstance().getHibernateProperty(SettingsManager.HIBERNATE_CONNECTION_URL));
        try (var session = DatabaseManager.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.save(institute);
            session.save(cathedra);
            session.save(person);
            session.save(employee);
            session.save(phone);
            session.save(email);
            session.save(position);
            System.out.println(session.get(Phone.class, 1L));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateTest1() {
        try (var session = DatabaseManager.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            var employee = session.get(Employee.class, 1L);
            System.out.println(employee);
            var position = session.get(EmployeePosition.class, 1L);
            System.out.println(position);
            employee.setPosition(position);
            session.update(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateTest2() {
        try (var session = DatabaseManager.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            var position = session.get(EmployeePosition.class, 1L);
            System.out.println(position);
            position.setWorkloadStandad(600);
            session.update(position);
            var employee = session.get(Employee.class, 1L);
            System.out.println(employee.getPerson().fullname() + " " + employee.getPosition());
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}