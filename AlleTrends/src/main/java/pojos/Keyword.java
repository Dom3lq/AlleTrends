package pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Keyword {

	@Id
	private String word;

	@OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY)
	private List<KeywordRaport> keywordBids = new ArrayList<KeywordRaport>();

	public String getWord() {
		return word;
	}

	public void setWord(String keyword) {
		this.word = keyword;
	}

	public List<KeywordRaport> getKeywordBids() {
		return keywordBids;
	}

	public void setKeywordBids(List<KeywordRaport> keywordBids) {
		this.keywordBids = keywordBids;
	}

}
