package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.ParameterName;

@Repository
public interface ParameterNameRepository extends JpaRepository<ParameterName, Integer>{

}
