package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.Raport;

@Repository
public interface RaportRepository extends JpaRepository<Raport, Long> {

	List<Raport> findAllByOrderByTimeDesc();

	Raport findFirstByIsCompleteTrueOrderByTimeDesc();
}
