package com.example.sms;

import com.example.sms.dao.CourseDAO;
import com.example.sms.model.Course;
import com.example.sms.model.Student;
import com.example.sms.service.StudentService;
import com.example.sms.util.JdbcDemo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        StudentService service = ctx.getBean("studentService", StudentService.class);

        JdbcDemo.printDbVersion(
                "jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root",
                "root"
        );

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Student Management ===");
            System.out.println("1. Add Student (sync)");
            System.out.println("2. Add Student (async)");
            System.out.println("3. View All Students");
            System.out.println("4. View Students Sorted by Name (Streams)");
            System.out.println("5. Filter Students by Min Age (Streams)");
            System.out.println("6. Delete Student by ID");
            System.out.println("7. Add courses");
            System.out.println("8. View the courses");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            int opt;
            try {
                opt = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            try {
                switch (opt) {
                    case 1 -> {
                        Student s = readStudent(sc);
                        service.addStudent(s);
                        System.out.println("Saved student (sync).");
                    }
                    case 2 -> {
                        Student s = readStudent(sc);
                        service.addStudentAsync(s).join();
                        System.out.println("Saved student (async).");
                    }
                    case 3 -> {
                        List<Student> all = service.getAll();
                        all.forEach(System.out::println);
                    }
                    case 4 -> service.getSortedByName().forEach(System.out::println);
                    case 5 -> {
                        System.out.print("Min age: ");
                        int minAge = Integer.parseInt(sc.nextLine());
                        service.filterByMinAge(minAge).forEach(System.out::println);
                    }
                    case 6 -> {
                        System.out.print("Enter ID to delete: ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean deleted = service.delete(id);
                        System.out.println(deleted ? "Deleted." : "Not found.");
                    }
                    case 7 ->{
                        CourseDAO courseDAO = new CourseDAO();
                        System.out.print("Enter Course ID: ");
                        int cid = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Course Name: ");
                        String cname = sc.nextLine();
                        System.out.print("Enter Duration (weeks): ");
                        int dur = Integer.parseInt(sc.nextLine());
                        courseDAO.save(new Course(cid, cname, dur));
                        System.out.println("Course added successfully.");
                    }
                    case 8 ->{
                        CourseDAO courseDAO = new CourseDAO();
                        courseDAO.getAll().forEach(System.out::println);
                    }
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Unknown option.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private static Student readStudent(Scanner sc) {
        System.out.print("ID (int): ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Age (int): ");
        int age = Integer.parseInt(sc.nextLine());

        return new Student(id, name, age);
    }
}
