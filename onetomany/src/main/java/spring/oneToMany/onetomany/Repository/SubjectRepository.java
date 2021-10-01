package spring.oneToMany.onetomany.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.oneToMany.onetomany.Entity.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{
    List<Subject> findBySubject(Integer id);
}
