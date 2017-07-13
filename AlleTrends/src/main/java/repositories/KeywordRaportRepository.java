package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pojos.CategoryRaport;
import pojos.Keyword;
import pojos.KeywordRaport;

@Repository
public interface KeywordRaportRepository extends JpaRepository<KeywordRaport, Integer> {

	KeywordRaport findOneByKeywordAndCategoryRaport(Keyword keyword, CategoryRaport categoryRaport);
}
