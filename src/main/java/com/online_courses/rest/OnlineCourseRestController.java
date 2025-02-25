package com.online_courses.rest;

import com.online_courses.entity.*;
import com.online_courses.kafka.KafkaProducerService;
import com.online_courses.kafka.model.ActionType;
import com.online_courses.kafka.model.OnlineCourseEvent;
import com.online_courses.service.OnlineCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OnlineCourseRestController {
    private final OnlineCourseService onlineCourseService;
    private final KafkaProducerService kafkaProducerService;

    public OnlineCourseRestController(OnlineCourseService onlineCourseService, KafkaProducerService kafkaProducerService) {
        this.onlineCourseService = onlineCourseService;
        this.kafkaProducerService = kafkaProducerService;
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
        // send online course create event to kafka topic
        sendOnlineCourseEvent(ActionType.CREATE, instructorToStore);

        onlineCourseService.createInstructor(instructorToStore);
    }

    /**
     * PUT /api/courses/instructors
     * */
    @PutMapping("/courses/instructors")
    public void updateInstructor(@RequestBody Instructor instructorToUpdate) {
        // send online course update event to kafka topic
        sendOnlineCourseEvent(ActionType.UPDATE, instructorToUpdate);

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

        // send online course delete event to kafka topic
        sendOnlineCourseEvent(ActionType.DELETE, instructor);

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
        sendOnlineCourseEvent(ActionType.CREATE, instructorDetail);

        onlineCourseService.coursesInstructorDetail(instructorDetail);
    }

    /**
     * PUT /api/courses/instructors/instructor-details
     * */
    @PutMapping("/courses/instructors/instructor-details")
    public void updateInstructorDetails(@RequestBody InstructorDetail instructorDetail) {
        sendOnlineCourseEvent(ActionType.UPDATE, instructorDetail);

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

        sendOnlineCourseEvent(ActionType.DELETE, instructorDetail);

        onlineCourseService.deleteInstructorDetailById(id);
    }

    /**
     * PUT /api/courses/instructors/instructor-details/{instructorId}/{instructorDetailId}
     * */
    @PutMapping("/courses/instructors/instructor-details/{instructorId}/{instructorDetailId}")
    public Instructor assignInstructorDetailToInstructor(@PathVariable int instructorId, @PathVariable int instructorDetailId) {
        Instructor instructor = onlineCourseService.assignInstructorDetailToInstructor(instructorId, instructorDetailId);

        sendOnlineCourseEvent(ActionType.UPDATE, instructor);

        return instructor;
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
    public void createCourse(@RequestBody Course course) {
        sendOnlineCourseEvent(ActionType.CREATE, course);

        onlineCourseService.createCourse(course);
    }

    /**
     * PUT /api/courses
     * */
    @PutMapping("/courses")
    public Course updateCourse(@RequestBody Course course) {
        sendOnlineCourseEvent(ActionType.UPDATE, course);

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

        sendOnlineCourseEvent(ActionType.DELETE, course);

        onlineCourseService.deleteCourseById(id);
    }

    /**
     * PUT /api/courses/{instructorId}/{courseId}
     * */
    @PutMapping("/courses/{instructorId}/{courseId}")
    public Course assignCourseToInstructor(@PathVariable int instructorId, @PathVariable int courseId) {
        Course course = onlineCourseService.assignCourseToInstructor(instructorId, courseId);

        sendOnlineCourseEvent(ActionType.UPDATE, course);

        return course;
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
    public Course addReviewToCourse(@RequestBody Review review, @PathVariable int courseId) {
        Course courseWithReview = onlineCourseService.addReviewToCourse(review, courseId);

        sendOnlineCourseEvent(ActionType.CREATE, courseWithReview);

        return courseWithReview;
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
        sendOnlineCourseEvent(ActionType.CREATE, student);

        onlineCourseService.createStudent(student);
    }

    /**
     * PUT /api/courses/students
     * */
    @PutMapping("/courses/students")
    public Student updateStudent(@RequestBody Student student) {
        sendOnlineCourseEvent(ActionType.UPDATE, student);

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

        sendOnlineCourseEvent(ActionType.DELETE, student);

        onlineCourseService.deleteStudentById(id);
    }

    /**
     * PUT /api/courses/students/{courseId}/{studentId}
     * */
    @PutMapping("courses/students/{courseId}/{studentId}")
    public Course assignStudentToCourse(@PathVariable int courseId, @PathVariable int studentId) {
        Course course = onlineCourseService.assignStudentToCourse(courseId, studentId);

        sendOnlineCourseEvent(ActionType.UPDATE, course);

        return course;
    }

    public <T>void sendOnlineCourseEvent(ActionType action, T obj) {
        kafkaProducerService.sendMessage(new OnlineCourseEvent(action, obj));
    }
}
