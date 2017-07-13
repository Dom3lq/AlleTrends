package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.Category;
import pojos.CategoryRaport;
import pojos.Raport;

@Repository
public interface CategoryRaportRepository extends JpaRepository<CategoryRaport, Integer> {

	public CategoryRaport findOneByCategoryId(int Id);

	public CategoryRaport findOneByCategoryAndRaport(Category category, Raport raport);

	public CategoryRaport findOneByCategoryIdAndRaport(long id, Raport raport);
}
