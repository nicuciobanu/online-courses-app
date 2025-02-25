package com.online_courses.dao;

import com.online_courses.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OnlineCoursesDAOImpl implements OnlineCoursesDAO {

    private EntityManager entityManager;

    @Autowired
    public OnlineCoursesDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveInstructor(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    public List<Instructor> findAllInstructors() {
        TypedQuery<Instructor> query = entityManager.createQuery("FROM Instructor", Instructor.class);
        List<Instructor> instructors = query.getResultList();

        return instructors;
    }

    @Override
    public Instructor describeInstructorById(int id) {
        TypedQuery<Instructor> query = entityManager.createQuery(
                "SELECT i FROM Instructor i "
                        + "JOIN FETCH i.courses "
                        + "JOIN FETCH i.instructorDetail "
                        + "WHERE i.id = :data", Instructor.class
        );

        query.setParameter("data", id);

        Instructor instructor = query.getSingleResult();

        return instructor;
    }

    @Override
    @Transactional
    public Instructor updateInstructor(Instructor instructor) {
        return entityManager.merge(instructor);
    }

    @Override
    @Transactional
    public void saveInstructorDetail(InstructorDetail instructorDetail) {
        entityManager.persist(instructorDetail);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public void removeInstructorById(int id) {
        Instructor instructor = entityManager.find(Instructor.class, id);
        List<Course> courses = instructor.getCourses();

        /**
        * Before deleting the instructor, we need to dissociate the instructor from the courses.
        * */
        if(courses != null) {
            for(Course course : courses) {
                course.setInstructor(null);
            }
        }

        entityManager.remove(instructor);
    }

    @Override
    @Transactional
    public InstructorDetail updateInstructorDetail(InstructorDetail instructorDetail) {
        InstructorDetail updatedDetail = entityManager.merge(instructorDetail);

        return updatedDetail;
    }

    @Override
    @Transactional
    public void removeInstructorDetailById(int id) {
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class, id);

        if(instructorDetail.getInstructor() != null) {
            instructorDetail.getInstructor().setInstructorDetail(null);
        }

        entityManager.remove(instructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        TypedQuery<Course> query = entityManager.createQuery("FROM Course WHERE instructor.id = :data", Course.class);
        query.setParameter("data", id);

        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        return entityManager.merge(course);
    }

    @Override
    @Transactional
    public void removeCourseById(int id) {
        Course course = entityManager.find(Course.class, id);

        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void saveReview(Review review) {
        entityManager.persist(review);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c "
                        + "JOIN FETCH c.reviews "
                        + "WHERE c.id = :data", Course.class);

        query.setParameter("data", id);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void saveStudent(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student findStudentById(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    @Transactional
    public Student updateStudent(Student student) {
        return entityManager.merge(student);
    }

    @Override
    @Transactional
    public void removeStudentById(int id) {
        Student student = entityManager.find(Student.class, id);

        entityManager.remove(student);
    }
}
