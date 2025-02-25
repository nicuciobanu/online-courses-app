package com.online_courses.service;

import com.online_courses.dao.OnlineCoursesDAO;
import com.online_courses.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineCourseServiceImpl implements OnlineCourseService {
    private final OnlineCoursesDAO onlineCoursesDAO;

    @Autowired
    public OnlineCourseServiceImpl(OnlineCoursesDAO onlineCoursesDAO) {
        this.onlineCoursesDAO = onlineCoursesDAO;
    }

    @Override
    public void createInstructor(Instructor instructor) {
        onlineCoursesDAO.saveInstructor(instructor);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return onlineCoursesDAO.findAllInstructors();
    }

    @Override
    public Instructor getInstructorById(int id) {
        return onlineCoursesDAO.findInstructorById(id);
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return onlineCoursesDAO.updateInstructor(instructor);
    }

    @Override
    public void deleteInstructorById(int id) {
        onlineCoursesDAO.removeInstructorById(id);
    }

    @Override
    public Instructor assignInstructorDetailToInstructor(int instructorId, int instructorDetailId) {
        Instructor instructor = onlineCoursesDAO.findInstructorById(instructorId);
        InstructorDetail instructorDetail = onlineCoursesDAO.findInstructorDetailById(instructorDetailId);

        instructor.setInstructorDetail(instructorDetail);

        return onlineCoursesDAO.updateInstructor(instructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return onlineCoursesDAO.findInstructorDetailById(id);
    }

    @Override
    public void coursesInstructorDetail(InstructorDetail instructorDetail) {
        onlineCoursesDAO.saveInstructorDetail(instructorDetail);
    }

    @Override
    public void updateInstructorDetail(InstructorDetail instructorDetail) {
        onlineCoursesDAO.updateInstructorDetail(instructorDetail);
    }

    @Override
    public void deleteInstructorDetailById(int id) {
        onlineCoursesDAO.removeInstructorDetailById(id);
    }

    @Override
    public List<Course> getCoursesByInstructorId(int id) {
        return onlineCoursesDAO.findCoursesByInstructorId(id);
    }

    @Override
    public Course getCourseById(int id) {
        return onlineCoursesDAO.findCourseById(id);
    }

    @Override
    public void createCourse(Course course) {
        onlineCoursesDAO.saveCourse(course);
    }

    @Override
    public Course updateCourse(Course course) {
        return onlineCoursesDAO.updateCourse(course);
    }

    @Override
    public void deleteCourseById(int id) {
        onlineCoursesDAO.removeCourseById(id);
    }

    @Override
    public void assignCourseToInstructor(int instructorId, int courseId) {
        Instructor instructor = onlineCoursesDAO.findInstructorById(instructorId);
        Course course = onlineCoursesDAO.findCourseById(courseId);

        course.setInstructor(instructor);

        onlineCoursesDAO.saveCourse(course);
    }

    @Override
    public Course addReviewToCourse(Review review, int courseId) {
        Course course = onlineCoursesDAO.findCourseById(courseId);
        course.addReview(review);

        return onlineCoursesDAO.updateCourse(course);
    }

    @Override
    public void createStudent(Student student) {
        onlineCoursesDAO.saveStudent(student);
    }

    @Override
    public Student getStudentById(int id) {
        return onlineCoursesDAO.findStudentById(id);
    }

    @Override
    public Student updateStudent(Student student) {
        return onlineCoursesDAO.updateStudent(student);
    }

    @Override
    public void deleteStudentById(int id) {
        onlineCoursesDAO.removeStudentById(id);
    }

    @Override
    public Course assignStudentToCourse(int courseId, int studentId) {
        Course course = onlineCoursesDAO.findCourseById(courseId);
        Student student = onlineCoursesDAO.findStudentById(studentId);

        course.addStudent(student);

        return onlineCoursesDAO.updateCourse(course);
    }
}
