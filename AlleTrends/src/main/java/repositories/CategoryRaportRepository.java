package repositories;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import pojos.Category;
import pojos.CategoryRaport;
import pojos.Raport;

@Repository
public interface CategoryRaportRepository extends PagingAndSortingRepository<CategoryRaport, Integer> {

	public List<CategoryRaport> findByRaportAndCategoryParent(Raport raport, Category parent);

	public List<CategoryRaport> findByRaportAndCategoryParentIsNull(Raport raport);

	public CategoryRaport findOneByRaportAndCategory(Raport pastRaport, Category category);

	public List<CategoryRaport> findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsSaleDesc(int id, Raport raport);

	public List<CategoryRaport> findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsBidsCountDesc(int id,
			Raport raport);

	public List<CategoryRaport> findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsBiddersCountDesc(int id,
			Raport raport);

	@Cacheable("topBidsPerAuction")
	@Query("SELECT catRap, (catRap.allStatistics.bidsCount/catRap.allStatistics.itemsCount) as bpa FROM CategoryRaport catRap "
			+ "WHERE catRap.category.parent.id = ?1 AND catRap.raport.time = ?2 " + "ORDER BY bpa DESC")
	public List<Object[]> findTopBidsPerAuction(int id, long time);

	@Cacheable("topSaleGrowth")
	@Query("SELECT catRap, " + "((catRap.allStatistics.sale * 100 / prevCatRap.allStatistics.sale) - 100) as growth "
			+ "FROM CategoryRaport catRap, CategoryRaport prevCatRap "
			+ "WHERE catRap.category.parent.id = ?1 AND catRap.raport.time = ?2 "
			+ "AND prevCatRap.category.id = catRap.category.id AND prevCatRap.raport.time = ?3 "
			+ "ORDER BY growth DESC")
	public List<Object[]> findTopSaleGrowth(int id, long time, long prevTime);

	public CategoryRaport findOneByRaportAndCategoryParentIsNull(Raport raport);

}
