package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.CategoryFailedPortion;

@Repository
public interface CategoryFailedPortionRepository extends JpaRepository<CategoryFailedPortion, Integer> {

}
