package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.ParameterValue;

@Repository
public interface ParameterValueRepository extends JpaRepository<ParameterValue, Integer> {

}
