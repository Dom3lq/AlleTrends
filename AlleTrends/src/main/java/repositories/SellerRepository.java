package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

	public Seller findOneByName(String name);
}
