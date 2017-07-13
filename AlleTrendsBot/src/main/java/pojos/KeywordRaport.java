package pojos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class KeywordRaport extends Statistics {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "keywordId")
	private Keyword keyword;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryRaportId")
	private CategoryRaport categoryRaport;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public CategoryRaport getRaport() {
		return this.categoryRaport;
	}

	public void setCategoryRaport(CategoryRaport categoryRaport) {
		this.categoryRaport = categoryRaport;
	}
}
