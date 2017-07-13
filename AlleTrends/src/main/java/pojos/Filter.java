package pojos;

import java.util.ArrayList;
import java.util.List;

import pl.allegro.webapi.service_php.FilterOptionsType;
import pl.allegro.webapi.service_php.FiltersListType;

public class Filter {

	private FilterOptionsType filterOptionsType = new FilterOptionsType();
	private FiltersListType filterListType = new FiltersListType();
	private List<Filter> mustBeFilters = new ArrayList<Filter>();
	private List<Filter> mustBeAtLeastOneFilter = new ArrayList<Filter>();
	private List<Filter> mustntBeFilters = new ArrayList<Filter>();
	private boolean mustBe, mayBe, mustntBe;

	public FilterOptionsType getFilterOptionsType() {
		return filterOptionsType;
	}

	public void setFilterOptionsType(FilterOptionsType filterOptionsType) {
		this.filterOptionsType = filterOptionsType;
	}

	public FiltersListType getFilterListType() {
		return filterListType;
	}

	public void setFilterListType(FiltersListType filterListType) {
		this.filterListType = filterListType;
	}

	public List<Filter> getMustBeFilters() {
		return mustBeFilters;
	}

	public void setMustBeFilters(List<Filter> mustBeFilters) {
		this.mustBeFilters = mustBeFilters;
	}

	public List<Filter> getMustBeAtLeastOneFilter() {
		return mustBeAtLeastOneFilter;
	}

	public void setMustBeAtLeastOneFilter(List<Filter> mustBeAtLeastOneFilter) {
		this.mustBeAtLeastOneFilter = mustBeAtLeastOneFilter;
	}

	public List<Filter> getMustntBeFilters() {
		return mustntBeFilters;
	}

	public void setMustntBeFilters(List<Filter> mustntBeFilters) {
		this.mustntBeFilters = mustntBeFilters;
	}

	public boolean isMustBe() {
		return mustBe;
	}

	public void setMustBe(boolean mustBe) {
		this.mustBe = mustBe;
	}

	public boolean isMayBe() {
		return mayBe;
	}

	public void setMayBe(boolean mayBe) {
		this.mayBe = mayBe;
	}

	public boolean isMustntBe() {
		return mustntBe;
	}

	public void setMustntBe(boolean mustntBe) {
		this.mustntBe = mustntBe;
	}
}
