package wad.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Course;
import wad.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course addCourse(Course course) {
        for (Course kurssi : courseRepository.findAll()) {

            if (kurssi.getName().equals(course.getName())) {
                return course;
            }
        }
        return courseRepository.save(course);
    }
}
