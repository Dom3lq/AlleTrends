package pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Raport {

	@Id
	private Long time;

	private Boolean isComplete = false;
	private long actualSize = 0, totalSize = 0;

	@OneToMany(mappedBy = "raport", fetch = FetchType.LAZY)
	private List<CategoryRaport> categoryRaports = new ArrayList<CategoryRaport>();

	public Raport() {
	}

	public Raport(final Long time) {
		this.time = time;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public List<CategoryRaport> getCategoryRaports() {
		return categoryRaports;
	}

	public void setCategoryDailyRaports(List<CategoryRaport> categoryDailyRaports) {
		this.categoryRaports = categoryDailyRaports;
	}

	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	public long getActualSize() {
		return actualSize;
	}

	public void setActualSize(long actualSize) {
		this.actualSize = actualSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public synchronized void incrementActualSizeBy(int length) {
		this.actualSize += length;
	}
}
