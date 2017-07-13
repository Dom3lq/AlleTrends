package pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	private int id;

	private String name;
	private int positiong;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<CategoryRaport> categoryRaports = new ArrayList<CategoryRaport>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId")
	private Category parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private List<Category> childs = new ArrayList<Category>();

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<ParameterName> parameterNames = new ArrayList<ParameterName>();

	public Category() {
	}

	public Category(int id, String name, int position) {
		this.setId(id);
		this.setName(name);
		this.setPositiong(position);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPositiong() {
		return positiong;
	}

	public void setPositiong(int positiong) {
		this.positiong = positiong;
	}

	public List<CategoryRaport> getCategoryRaports() {
		return categoryRaports;
	}

	public void setCategoryRaports(List<CategoryRaport> categoryRaports) {
		this.categoryRaports = categoryRaports;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChilds() {
		return childs;
	}

	public void setChilds(List<Category> childs) {
		this.childs = childs;
	}

	public List<ParameterName> getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(List<ParameterName> parameterNames) {
		this.parameterNames = parameterNames;
	}

}
