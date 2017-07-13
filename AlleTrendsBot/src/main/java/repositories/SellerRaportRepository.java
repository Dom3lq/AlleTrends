package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.CategoryRaport;
import pojos.Seller;
import pojos.SellerRaport;

@Repository
public interface SellerRaportRepository extends JpaRepository<SellerRaport, Integer> {

	SellerRaport findOneBySellerAndCategoryRaport(Seller seller, CategoryRaport categoryRaport);
}
