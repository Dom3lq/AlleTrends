package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findOneByNameAndPassword(String name, byte[] password);

	User findOneByConfirmationKey(String confirmationKey);
}
