package repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.Category;
import pojos.CategoryRaport;
import pojos.Raport;

@Repository
public interface CategoryRaportRepository extends JpaRepository<CategoryRaport, Integer> {

	public List<CategoryRaport> findByRaportAndCategoryParent(Raport raport, Category parent);

	public List<CategoryRaport> findByRaportAndCategoryParentIsNull(Raport raport);

	public CategoryRaport findOneByRaportAndCategory(Raport pastRaport, Category category);

	public List<CategoryRaport> findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsSaleDesc(int id, Raport raport);

	public List<CategoryRaport> findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsBidsCountDesc(int id,
			Raport raport);

	public List<CategoryRaport> findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsBiddersCountDesc(int id,
			Raport raport);

	public List<CategoryRaport> findTop20ByCategoryParentIdAndRaportAndSort(int id, Raport raport, Sort sort);

	public CategoryRaport findOneByRaportAndCategoryParentIsNull(Raport raport);

}
