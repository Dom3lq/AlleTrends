package pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Seller {

	@Id
	private int id;

	private String name;

	@OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
	private List<SellerRaport> sellerSales = new ArrayList<SellerRaport>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SellerRaport> getSellerSales() {
		return sellerSales;
	}

	public void setSellerSales(List<SellerRaport> sellerSales) {
		this.sellerSales = sellerSales;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
