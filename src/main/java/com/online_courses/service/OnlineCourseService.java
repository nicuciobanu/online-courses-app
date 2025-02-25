package com.online_courses.service;

import com.online_courses.entity.*;

import java.util.List;

public interface OnlineCourseService {
    void createInstructor(Instructor instructor);
    List<Instructor> getAllInstructors();
    Instructor getInstructorById(int id);
    Instructor updateInstructor(Instructor instructor);
    void deleteInstructorById(int id);
    Instructor assignInstructorDetailToInstructor(int instructorId, int instructorDetailId);
    InstructorDetail findInstructorDetailById(int id);
    void coursesInstructorDetail(InstructorDetail instructorDetail);
    void updateInstructorDetail(InstructorDetail instructorDetail);
    void deleteInstructorDetailById(int id);
    List<Course> getCoursesByInstructorId(int id);
    Course getCourseById(int id);
    void createCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourseById(int id);
    void assignCourseToInstructor(int instructorId, int courseId);
    Course addReviewToCourse(Review review, int courseId);
    void createStudent(Student student);
    Student getStudentById(int id);
    Student updateStudent(Student student);
    void deleteStudentById(int id);
    Course assignStudentToCourse(int courseId, int studentId);
}
