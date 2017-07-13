package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.Shop;
import pojos.User;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

	Shop findOneByUser(User user);
}
