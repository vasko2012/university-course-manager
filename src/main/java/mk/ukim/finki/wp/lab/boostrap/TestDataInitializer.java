package mk.ukim.finki.wp.lab.boostrap;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enums.Type;
import mk.ukim.finki.wp.lab.repository.jpa.JpaCourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaStudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaTeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Fills the in-memory H2 database with sample teachers, students and courses
 * when the app runs with the "test" profile, so it can be tried out right away.
 */
@Component
@Profile("test")
public class TestDataInitializer implements CommandLineRunner {
    private final JpaTeacherRepository teacherRepository;
    private final JpaStudentRepository studentRepository;
    private final JpaCourseRepository courseRepository;

    public TestDataInitializer(JpaTeacherRepository teacherRepository,
                               JpaStudentRepository studentRepository,
                               JpaCourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) {
        if (courseRepository.count() > 0) {
            return;
        }

        List<Teacher> teachers = teacherRepository.saveAll(List.of(
                new Teacher("Elena", "Stojanovska"),
                new Teacher("Marko", "Nikolov"),
                new Teacher("Ivana", "Trajkovska")
        ));

        List<Student> students = studentRepository.saveAll(List.of(
                new Student("ana.kostova", "pass", "Ana", "Kostova"),
                new Student("bojan.ilievski", "pass", "Bojan", "Ilievski"),
                new Student("marija.petrova", "pass", "Marija", "Petrova"),
                new Student("stefan.jovanov", "pass", "Stefan", "Jovanov"),
                new Student("teodora.mitreva", "pass", "Teodora", "Mitreva")
        ));

        Course web = new Course("Web Programming", "Spring Boot, MVC and Thymeleaf", teachers.get(0), Type.WINTER);
        web.getStudents().addAll(students.subList(0, 3));

        Course databases = new Course("Databases", "Relational modelling and SQL", teachers.get(1), Type.MANDATORY);
        databases.getStudents().addAll(students.subList(1, 5));

        Course security = new Course("Computer Security", "Cryptography and network security basics", teachers.get(2), Type.ELECTIVE);
        security.getStudents().add(students.get(4));

        Course algorithms = new Course("Algorithms and Data Structures", "Lists, trees, graphs and sorting", teachers.get(1), Type.SUMMER);

        courseRepository.saveAll(List.of(web, databases, security, algorithms));
    }
}
