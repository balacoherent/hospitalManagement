package spring.oneToMany.onetomany.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.oneToMany.onetomany.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
