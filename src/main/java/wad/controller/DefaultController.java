
package wad.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.domain.Course;
import wad.domain.CourseType;
import wad.domain.Exam;
import wad.domain.ExamType;
import wad.repository.CourseRepository;
import wad.repository.ExamRepository;


@Controller
public class DefaultController {
    
    @RequestMapping("*")
    public String redirect() {
        return "redirect:/exams";
    }
    
     @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ExamRepository examRepository;

    @PostConstruct
    public void init() throws ParseException {
        /* 
        Kurssien ja kokeiden lisäystä testaamista varten.
        */
        
        ArrayList<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCode("582353");
        course1.setName("Web-palvelinohjelmointi Java");
        
        course1.setCourseType(CourseType.Subject);
        courseRepository.save(course1);
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setCode("58131");
        course2.setName("Tietorakenteet ja algoritmit");
        
        course2.setCourseType(CourseType.Subject);
        courseRepository.save(course2);
        courses.add(course2);
        
        Course course3 = new Course();
        course3.setCode("582104");
        course3.setName("Ohjelmistotekniikan menetelmät");
        
        course3.setCourseType(CourseType.Basic);
        courseRepository.save(course3);
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        
        Exam exam1 = new Exam();
        exam1.setCourse(courses.get(0));
        exam1.setExaminer("Arto Vihavainen");
        exam1.setExamDate(formatter.parse("06.06.2016 10:00"));
        exam1.setLocation("B123");
        exam1.setExamType(ExamType.Normal);
        exam1.setRequiresAssignment(true);
        examRepository.save(exam1);
        
        
        Exam exam2 = new Exam();
        exam2.setCourse(courses.get(1));
        exam2.setExaminer("Patrik Floréen");
        exam2.setExamDate(formatter.parse("10.10.2016 12:00"));
        exam2.setLocation("A111");
        exam2.setExamType(ExamType.Separate);
        exam2.setRequiresAssignment(false);
        examRepository.save(exam2);
    }
}
