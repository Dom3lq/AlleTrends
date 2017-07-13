package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.ParameterValueRaport;

@Repository
public interface ParameterValueRaportRepository extends JpaRepository<ParameterValueRaport, Integer> {

}
