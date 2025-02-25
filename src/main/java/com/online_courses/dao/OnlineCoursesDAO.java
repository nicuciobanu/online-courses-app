package com.online_courses.dao;

import com.online_courses.entity.*;

import java.util.List;

public interface OnlineCoursesDAO {
    void saveInstructor(Instructor instructor);
    List<Instructor> findAllInstructors();
    Instructor findInstructorById(int id);
    Instructor describeInstructorById(int id);
    Instructor updateInstructor(Instructor instructor);
    void removeInstructorById(int id);
    void saveInstructorDetail(InstructorDetail instructorDetail);
    InstructorDetail findInstructorDetailById(int id);
    InstructorDetail updateInstructorDetail(InstructorDetail instructorDetail);
    void removeInstructorDetailById(int id);
    List<Course> findCoursesByInstructorId(int theId);
    void saveCourse(Course course);
    Course findCourseById(int id);
    Course updateCourse(Course course);
    void removeCourseById(int id);
    void saveReview(Review review);
    Course findCourseAndReviewsByCourseId(int id);
    void saveStudent(Student student);
    Student findStudentById(int id);
    Student updateStudent(Student student);
    void removeStudentById(int id);
}
