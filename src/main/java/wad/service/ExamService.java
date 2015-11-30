package wad.service;

import java.text.DateFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import wad.domain.Course;
import wad.domain.CourseType;
import wad.domain.Exam;
import wad.domain.ExamType;
import wad.repository.CourseRepository;
import wad.repository.ExamRepository;

@Service
public class ExamService {


    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getExams() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        
        return examRepository.findByExamDateAfterOrderByExamDateAsc(date);
    }

    public Exam getExam(Long id) {
        return examRepository.findOne(id);
    }

    @Transactional
    public void save(Exam exam) {
        if (examdateOverlaps(exam)) {
            return;
        }
        examRepository.save(exam);
    }

    public void delete(Long id) {
        examRepository.delete(id);
    }

    public List<Exam> searchByCourseName(String search) {
        search = "%" + search + "%";
        return examRepository.findByCourse_NameLike(search);
    }

    public List<Exam> searchByDateRange(Date start, Date end) {

        return examRepository.findByExamDateBetweenOrderByExamDateAsc(start, end);
    }

    private boolean examdateOverlaps(Exam exam) {

        for (Exam e : examRepository.findAll()) {

            if (e.getCourse().equals(exam.getCourse())) {
                if (!e.getExamDate().before(exam.getExamDate())) {
                    if (!e.getExamDate().after(exam.getExamDate())) {
                        return true;
                    }

                }

            }

        }

        return false;
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
