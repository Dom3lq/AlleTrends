package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pojos.Raport;
import pojos.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {

	@Query(value = "SELECT s.*, sum(ss.sale) AS total_sale " 
			+ "FROM analyzer.seller s "
			+ "INNER JOIN analyzer.seller_sale ss ON ss.name = s.seller_id AND ctg.raport_id = ?1"
			+ "INNER JOIN analyzer.category_daily_raport ctg ON ss.category_daily_raport_id = ctg.id"
			+ "GROUP by s.name " 
			+ "ORDER by total_sale DESC "
			+ "LIMIT 10 OFFSET 0", nativeQuery = true)
	List<Seller> findTopFiveBestSellers(int raportId);
}
