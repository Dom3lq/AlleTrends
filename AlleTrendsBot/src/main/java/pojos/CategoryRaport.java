package pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class CategoryRaport extends Statistics {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "raportId")
	private Raport raport;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId")
	private Category category;

	private long actualSize = 0;
	private long totalSize = 0;

	@OneToMany(mappedBy = "categoryRaport", fetch = FetchType.LAZY)
	private List<KeywordRaport> keywordRaports = new ArrayList<KeywordRaport>();

	@OneToMany(mappedBy = "categoryRaport", fetch = FetchType.LAZY)
	private List<SellerRaport> sellerRaports = new ArrayList<SellerRaport>();

	@OneToMany(mappedBy = "categoryRaport", fetch = FetchType.LAZY)
	private List<ParameterValueRaport> parameterValueRaports = new ArrayList<ParameterValueRaport>();

	@OneToMany(mappedBy = "categoryRaport", fetch = FetchType.LAZY)
	private List<CategoryFailedPortion> categoryFailedPortions = new ArrayList<CategoryFailedPortion>();

	public CategoryRaport() {
	}

	public CategoryRaport(final Raport raport, final Category category) {
		this.setRaport(raport);
		this.setCategory(category);
		raport.getCategoryRaports().add(this);
	}

	public void setCategory(Category cat) {
		this.category = cat;
	}

	public Category getCategory() {
		return category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public synchronized void incrementActualSizeBy(long size) {
		this.actualSize += size;
	}

	public synchronized void incrementActualSizeBy(int length) {
		this.actualSize += length;
	}

	public Raport getRaport() {
		return raport;
	}

	public void setRaport(Raport raport) {
		this.raport = raport;
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

	public List<ParameterValueRaport> getParameterValueRaport() {
		return parameterValueRaports;
	}

	public void setParameterValueRaport(List<ParameterValueRaport> parameterValueRaports) {
		this.parameterValueRaports = parameterValueRaports;
	}

	public List<CategoryFailedPortion> getCategoryFailedPortions() {
		return categoryFailedPortions;
	}

	public void setCategoryFailedPortions(List<CategoryFailedPortion> categoryFailedPortions) {
		this.categoryFailedPortions = categoryFailedPortions;
	}

}