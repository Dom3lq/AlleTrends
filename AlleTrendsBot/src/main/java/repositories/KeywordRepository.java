package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, String> {

}
