package pojos;

public class CategoryPortion {
	private long catId;
	private long startOffset;
	private long endOffset;

	public CategoryPortion() {

	}

	public CategoryPortion(long catId, long startOffset, long endOffset) {
		this.setCatId(catId);
		this.setStartOffset(startOffset);
		this.setEndOffset(endOffset);
	}

	public long getCatId() {
		return catId;
	}

	public void setCatId(long catId) {
		this.catId = catId;
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

	public long getSize() {
		return this.getEndOffset() - this.getStartOffset();
	}

}
