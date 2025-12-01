package com.example.demo.T3.checked;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class StudentRating {

    static ArrayList<Student> students = new ArrayList<>();


    public static String getStudentRating(String studentData, String courseInfo) {
                String[] strings = studentData.split(";");
        for (int i = 0; i < strings.length; i++) {
            students.add(
                    new Student(
                            strings[i].substring(0, strings[i].indexOf(",")),
                            strings[i].substring(strings[i].indexOf(",") + 1, strings[i].lastIndexOf(",")),
                            Integer.parseInt(strings[i].substring(strings[i].lastIndexOf(",") + 1))
                    ));
        }
        String fff = courseInfo.substring(0, courseInfo.indexOf(","));
        int ggg = Integer.parseInt(courseInfo.substring(courseInfo.indexOf(",") + 1));
        students.removeIf(student -> (!student.course.equalsIgnoreCase(fff) ||
                student.score <= ggg));

        Comparator<Student> comp = Comparator.comparingInt((Student x) -> x.score).reversed().thenComparing(x -> x.name);

        List<Student> studentList = students.stream().sorted(comp).toList();
            StringBuilder result = new StringBuilder();
        for (Student student : studentList) {
            result.append(student.name);
            result.append(",");
            result.append(student.score);
            result.append("\n");
        }
        if (result.isEmpty()) result.append("Никто");
        return result.toString();
    }
}

class Student {
    String name;
    String course;
    int score;

    public Student(String name, String course, int score) {
        this.name = name;
        this.course = course;
        this.score = score;
    }
}

public class RatingByCourse {
    public static void main(String[] args) {
        System.out.println(StudentRating.getStudentRating("Анна,Математика,85;" +
                        "Анна,Химия,90;" +
                        "Борис,Математика,80;" +
                        "Борис,История,80;" +
                        "Евгений,Математика,95;" +
                        "Евгений,История,85",
                "Математика,80"));
    }
}
