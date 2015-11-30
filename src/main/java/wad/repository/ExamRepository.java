
package wad.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wad.domain.Exam;


public interface ExamRepository extends JpaRepository<Exam, Long>{
    List<Exam> findByExamDateAfterOrderByExamDateAsc(Date date);
    List<Exam> findByCourse_NameLike(String name);
    List<Exam> findByExamDateBetweenOrderByExamDateAsc(Date start, Date end);
}
