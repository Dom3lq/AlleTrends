package pojos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CategoryFailedPortion {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryRaportId")
	private CategoryRaport categoryRaport;

	private long startOffset;
	private long endOffset;

	public CategoryFailedPortion(CategoryRaport categoryRaport, long startOffset, long endOffset) {
		this.categoryRaport = categoryRaport;
		this.setStartOffset(startOffset);
		this.setEndOffset(endOffset);
	}

	public long getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(long startOffset) {
		this.startOffset = startOffset;
	}

	public long getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(long endOffset) {
		this.endOffset = endOffset;
	}

}
