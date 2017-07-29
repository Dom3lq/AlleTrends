package repositories;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import pojos.Seller;

@Repository
public interface SellerRepository extends PagingAndSortingRepository<Seller, Integer> {

	Seller findOneByName(String name);

	@Query("SELECT seller, SUM(seller_raport.allStatistics.sale) as total_sale "
			+ "FROM Seller seller, SellerRaport seller_raport "
			+ "WHERE seller_raport.seller.id = seller.id AND seller_raport.categoryRaport.raport.time = ?1 "
			+ "GROUP BY seller " + "ORDER BY total_sale DESC")
	List<Object[]> findByRaportTimeOrderBySale(long time, Pageable pageable);

	@Cacheable("topSale")
	default List<Object[]> findTop20ByRaportTimeOrderBySale(long time) {
		return findByRaportTimeOrderBySale(time, new PageRequest(0, 20));
	}
	
	@Query("SELECT seller, SUM(seller_raport.allStatistics.bidsCount) as total_bids_count "
			+ "FROM Seller seller, SellerRaport seller_raport "
			+ "WHERE seller_raport.seller.id = seller.id AND seller_raport.categoryRaport.raport.time = ?1 "
			+ "GROUP BY seller " + "ORDER BY total_bids_count DESC")
	List<Object[]> findByRaportTimeOrderByBidsCount(long time, Pageable pageable);

	@Cacheable("topSale")
	default List<Object[]> findTop20ByRaportTimeOrderByBidsCount(long time) {
		return findByRaportTimeOrderByBidsCount(time, new PageRequest(0, 20));
	}

	@Query("SELECT seller, (((SUM(seller_raport.allStatistics.sale) * 100) / SUM(previous_seller_raport.allStatistics.sale)) - 100) as sale_growth "
			+ "FROM Seller seller, SellerRaport seller_raport, SellerRaport previous_seller_raport "
			+ "WHERE seller_raport.seller.id = seller.id AND seller_raport.categoryRaport.raport.time = ?1 "
			+ "AND previous_seller_raport.seller.id = seller.id AND previous_seller_raport.categoryRaport.raport.time = ?2 "
			+ "GROUP BY seller " + "ORDER BY sale_growth DESC")
	List<Object[]> findByRaportTimeOrderBySaleGrowth(long time, long prevTime, Pageable pageable);

	@Cacheable("topSaleGrowth")
	default List<Object[]> findTop20ByRaportTimeOrderBySaleGrowth(long time, long prevTime) {
		return findByRaportTimeOrderBySaleGrowth(time, prevTime, new PageRequest(0, 20));
	}

	@Query("SELECT seller, SUM(seller_raport.allStatistics.itemsCount) as total_items_count "
			+ "FROM Seller seller, SellerRaport seller_raport "
			+ "WHERE seller_raport.seller.id = seller.id AND seller_raport.categoryRaport.raport.time = ?1 "
			+ "GROUP BY seller " + "ORDER BY total_items_count DESC")
	List<Object[]> findByRaportTimeOrderByItemsCount(long time, Pageable pageable);

	@Cacheable("topItemsCount")
	default List<Object[]> findTop20ByRaportTimeOrderByItemsCount(long time) {
		return findByRaportTimeOrderByItemsCount(time, new PageRequest(0, 20));
	}
}
