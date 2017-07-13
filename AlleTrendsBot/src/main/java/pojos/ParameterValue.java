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
import javax.persistence.Transient;

@Entity
public class ParameterValue {

	@Id
	@GeneratedValue
	private int id;

	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parameterNameId")
	private ParameterName parameterName;

	@OneToMany(mappedBy = "parameterValue", fetch = FetchType.LAZY)
	private List<ParameterValueRaport> parameterValueRaport = new ArrayList<ParameterValueRaport>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParameterName getParameterName() {
		return parameterName;
	}

	public void setParameterName(ParameterName parameterName) {
		this.parameterName = parameterName;
	}

	public List<ParameterValueRaport> getParameterValueBids() {
		return parameterValueRaport;
	}

	public void setParameterValueBids(List<ParameterValueRaport> parameterValueBids) {
		this.parameterValueRaport = parameterValueBids;
	}

	@Transient
	public String getKey() {
		return parameterName.getCategory().getId() + parameterName.getName().toLowerCase() + name.toLowerCase();
	}
}
