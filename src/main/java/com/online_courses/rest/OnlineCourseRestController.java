package com.online_courses.rest;

import com.online_courses.entity.*;
import com.online_courses.service.OnlineCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OnlineCourseRestController {
    private final OnlineCourseService onlineCourseService;

    public OnlineCourseRestController(OnlineCourseService onlineCourseService) {
        this.onlineCourseService = onlineCourseService;
    }

    /**
     * GET /api/courses/instructors
     * */
    @GetMapping("/courses/instructors")
    public List<Instructor> getAllInstructors() {
        return onlineCourseService.getAllInstructors();
    }

    /**
     * GET /api/courses/instructors/{id}
     * */
    @GetMapping("/courses/instructors/{id}")
    public Instructor getInstructorById(@PathVariable int id) {
        Instructor instructor = onlineCourseService.getInstructorById(id);

        if(instructor == null) {
            throw new NotFoundException("Instructor id not found - " + id);
        }

        return instructor;
    }

    /**
     * POST /api/courses/instructors
     * */
    @PostMapping("/courses/instructors")
    public void createInstructor(@RequestBody Instructor instructorToStore) {
       onlineCourseService.createInstructor(instructorToStore);
    }

    /**
     * PUT /api/courses/instructors
     * */
    @PutMapping("/courses/instructors")
    public void updateInstructor(@RequestBody Instructor instructorToUpdate) {
        onlineCourseService.updateInstructor(instructorToUpdate);
    }

    /**
     * DELETE /courses/instructors/{id}
     * */
    @DeleteMapping("/courses/instructors/{id}")
    public void deleteInstructor(@PathVariable int id) {
        Instructor instructor = onlineCourseService.getInstructorById(id);

        if(instructor == null) {
            throw new UnexpectedException("Non-existent instructor id - " + id);
        }

        onlineCourseService.deleteInstructorById(id);
    }

    /**
     * GET /courses/instructors/instructor-details/{id}
     * */
    @GetMapping("/courses/instructors/instructor-details/{id}")
    public InstructorDetail getInstructorDetailsById(@PathVariable int id) {
        InstructorDetail instructorDetail = onlineCourseService.findInstructorDetailById(id);

        if(instructorDetail == null) {
            throw new NotFoundException("Instructor detail id not found - " + id);
        }

        return instructorDetail;
    }

    /**
     * POST /api/courses/instructors/instructor-details
     * */
    @PostMapping("/courses/instructors/instructor-details")
    public void createInstructorDetail(@RequestBody InstructorDetail instructorDetail) {
        onlineCourseService.coursesInstructorDetail(instructorDetail);
    }

    /**
     * PUT /api/courses/instructors/instructor-details
     * */
    @PutMapping("/courses/instructors/instructor-details")
    public void updateInstructorDetails(@RequestBody InstructorDetail instructorDetail) {
        onlineCourseService.updateInstructorDetail(instructorDetail);
    }


    /**
     * DELETE /api/courses/instructors/instructor-details/{id}
     * */
    @DeleteMapping("/courses/instructors/instructor-details/{id}")
    public void deleteInstructorDetail(@PathVariable int id) {
        InstructorDetail instructorDetail = onlineCourseService.findInstructorDetailById(id);

        if(instructorDetail == null) {
            throw new UnexpectedException("Non-existent instructor detail id - " + id);
        }

        onlineCourseService.deleteInstructorDetailById(id);
    }

    /**
     * PUT /api/courses/instructors/instructor-details/{instructorId}/{instructorDetailId}
     * */
    @PutMapping("/courses/instructors/instructor-details/{instructorId}/{instructorDetailId}")
    public Instructor assignInstructorDetailToInstructor(@PathVariable int instructorId, @PathVariable int instructorDetailId) {

        return onlineCourseService.assignInstructorDetailToInstructor(instructorId, instructorDetailId);
    }

    /**
     * GET /api/courses/{id}
     * */
    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable int id) {
        Course course = onlineCourseService.getCourseById(id);

        if(course == null) {
            throw new NotFoundException("Instructor id not found - " + id);
        }

        return course;
    }

    /**
     * POST /api/courses
     * */
    @PostMapping("/courses")
    public void createCourse(@RequestBody Course courseToStore) {
        onlineCourseService.createCourse(courseToStore);
    }

    /**
     * PUT /api/courses
     * */
    @PutMapping("/courses")
    public Course updateCourse(@RequestBody Course course) {
        return onlineCourseService.updateCourse(course);
    }

    /**
     * DELETE /api/courses/{id}
     * */
    @DeleteMapping("/courses/{id}")
    public void removeCourseById(@PathVariable int id) {
        Course course = onlineCourseService.getCourseById(id);

        if(course == null) {
            throw new UnexpectedException("Non-existent course id - " + id);
        }

        onlineCourseService.deleteCourseById(id);
    }

    /**
     * PUT /api/courses/{instructorId}/{courseId}
     * */
    @PutMapping("/courses/{instructorId}/{courseId}")
    public void assignCourseToInstructor(@PathVariable int instructorId, @PathVariable int courseId) {
        onlineCourseService.assignCourseToInstructor(instructorId, courseId);
    }

    /**
     * GET /api/courses/instructors/courses/{id}
     * */
    @GetMapping("/courses/instructors/courses/{id}")
    public List<Course> getCoursesByInstructorId(@PathVariable int id) {
        Instructor instructor = onlineCourseService.getInstructorById(id);

        if(instructor == null) {
            throw new UnexpectedException("Non-existent course id - " + id);
        }

        return onlineCourseService.getCoursesByInstructorId(instructor.getId());
    }

    /**
     * POST /api/courses/reviews/{courseId}
     * */
    @PostMapping("/courses/reviews/{courseId}")
    public void addReviewToCourse(@RequestBody Review review, @PathVariable int courseId) {
       onlineCourseService.addReviewToCourse(review, courseId);
    }

    /**
     * GET /api/courses/students/{id}
     * */
    @GetMapping("/courses/students/{id}")
    public Student getStudentById(@PathVariable int id) {
        Student student = onlineCourseService.getStudentById(id);

        if(student == null) {
            throw new NotFoundException("Student id not found - " + id);
        }

        return student;
    }

    /**
     * POST /api/courses/students
     * */
    @PostMapping("/courses/students")
    public void createStudent(@RequestBody Student student) {
        student.setId(0);

        onlineCourseService.createStudent(student);
    }

    /**
     * PUT /api/courses/students
     * */
    @PutMapping("/courses/students")
    public Student updateStudent(@RequestBody Student student) {
        return onlineCourseService.updateStudent(student);
    }

    /**
     * DELETE /api/courses/students/{id}
     * */
    @DeleteMapping("/courses/students/{id}")
    public void removeStudentById(@PathVariable int id) {
        Student student = onlineCourseService.getStudentById(id);

        if(student == null) {
            throw new UnexpectedException("Non-existent student id - " + id);
        }

        onlineCourseService.deleteStudentById(id);
    }

    /**
     * PUT /api/courses/students/{courseId}/{studentId}
     * */
    @PutMapping("courses/students/{courseId}/{studentId}")
    public void assignStudentToCourse(@PathVariable int courseId, @PathVariable int studentId) {
        onlineCourseService.assignStudentToCourse(courseId, studentId);
    }
}
