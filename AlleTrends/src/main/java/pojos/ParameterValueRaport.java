package pojos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ParameterValueRaport extends Statistics {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parameterValueId")
	private ParameterValue parameterValue;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryRaportId")
	private CategoryRaport categoryRaport;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CategoryRaport getCategoryRaport() {
		return categoryRaport;
	}

	public void setCategoryRaport(CategoryRaport categoryRaport) {
		this.categoryRaport = categoryRaport;
	}

	public ParameterValue getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(ParameterValue parameterValue) {
		this.parameterValue = parameterValue;
	}
}
