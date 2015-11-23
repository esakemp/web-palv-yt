
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Course;


public interface CourseRepository extends JpaRepository<Course, Long>{
    
}
