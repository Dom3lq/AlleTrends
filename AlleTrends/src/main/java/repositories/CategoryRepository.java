package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public List<Category> findByParent(Category parent);

	public List<Category> findByParentIsNull();

}
