package pojos;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import pl.allegro.webapi.service_php.ItemsListType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Statistics {

	@Id
	@GeneratedValue
	private int id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ALL_STATISTICS_ID")
	private TypedStatistics allStatistics = new TypedStatistics();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "NEW_STATISTICS_ID")
	private TypedStatistics newStatistics = new TypedStatistics();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USED_STATISTICS_ID")
	private TypedStatistics usedStatistics = new TypedStatistics();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "UNDEFINED_STATISTICS_ID")
	private TypedStatistics undefinedStatistics = new TypedStatistics();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "FREE_DELIVERY_STATISTICS_ID")
	private TypedStatistics freeDeliveryStatistics = new TypedStatistics();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ALLEGRO_STANDARD_STATISTICS_ID")
	private TypedStatistics allegroStandardStatistics = new TypedStatistics();

	public void incrementBy(ItemsListType spi) {
		getAllStatistics().incrementBy(spi);

		switch (spi.getConditionInfo()) {
		case "new":
			newStatistics.incrementBy(spi);
			break;
		case "used":
			usedStatistics.incrementBy(spi);
			break;
		default:
			undefinedStatistics.incrementBy(spi);
			break;
		}

		if ((spi.getAdditionalInfo() % 2) == 1) {
			allegroStandardStatistics.incrementBy(spi);
		}

		if (spi.getAdditionalInfo() >= 2) {
			freeDeliveryStatistics.incrementBy(spi);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypedStatistics getNewStatistics() {
		return newStatistics;
	}

	public void setNewStatistics(TypedStatistics newStatistics) {
		this.newStatistics = newStatistics;
	}

	public TypedStatistics getUsedStatistics() {
		return usedStatistics;
	}

	public void setUsedStatistics(TypedStatistics usedStatistics) {
		this.usedStatistics = usedStatistics;
	}

	public TypedStatistics getUndefinedStatistics() {
		return undefinedStatistics;
	}

	public void setUndefinedStatistics(TypedStatistics undefinedStatistics) {
		this.undefinedStatistics = undefinedStatistics;
	}

	public TypedStatistics getFreeDeliveryStatistics() {
		return freeDeliveryStatistics;
	}

	public void setFreeDeliveryStatistics(TypedStatistics freeDeliveryStatistics) {
		this.freeDeliveryStatistics = freeDeliveryStatistics;
	}

	public TypedStatistics getAllegroStandardStatistics() {
		return allegroStandardStatistics;
	}

	public void setAllegroStandardStatistics(TypedStatistics allegroStandardStatistics) {
		this.allegroStandardStatistics = allegroStandardStatistics;
	}

	public TypedStatistics getAllStatistics() {
		return allStatistics;
	}

	public void setAllStatistics(TypedStatistics allStatistics) {
		this.allStatistics = allStatistics;
	}
}
