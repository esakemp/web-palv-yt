
package wad.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Exam extends AbstractPersistable<Long> {
    @NotNull
    private ExamType examType;
    @NotNull
    private boolean requiresAssignment;
    @ManyToOne
    @NotNull
    private Course course;
    
    @NotBlank
    private String examiner;
    @NotBlank
    private String location;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    @NotNull
    private Date examDate;

    public ExamType getExamType() {
        
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public boolean isRequiresAssignment() {
        return requiresAssignment;
    }

    public void setRequiresAssignment(boolean requiresAssignment) {
        this.requiresAssignment = requiresAssignment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    @Override
    public String toString() {
        return "Exam{" + "examType=" + examType + ", requiresAssignment=" + requiresAssignment + ", course=" + course + ", examiner=" + examiner + ", location=" + location + ", date=" + examDate + '}';
    }
    
}
