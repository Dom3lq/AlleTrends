package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	long countByParent(Category parent);

}
