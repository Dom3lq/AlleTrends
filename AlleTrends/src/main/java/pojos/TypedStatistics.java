package pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import pl.allegro.webapi.service_php.ItemsListType;
import pl.allegro.webapi.service_php.PriceInfoType;

@Entity
public class TypedStatistics {

	@Id
	@GeneratedValue
	private int id;

	private long itemsCount = 0;
	private long leftCount = 0;
	private long biddersCount = 0;
	private long bidsCount = 0;
	private long sale = 0;

	@Transient
	private Float saleRest;

	public void incrementBy(ItemsListType spi) {
		int bidsCount = spi.getBidsCount();
		Float sale = getSale(spi);

		incrementBiddersCountBy(spi.getBiddersCount());
		incrementBidsCountBy(bidsCount);
		incrementItemsCount();
		incrementLeftCountBy(spi.getLeftCount());
		incrementSaleBy(sale);
	}

	private Float getSale(ItemsListType spi) {
		Float sale = 0F;
		for (PriceInfoType p : spi.getPriceInfo()) {
			if (p.getPriceType().equals("withDelivery"))
				continue;
			else if (p.getPriceType().equals("buyNow")) {
				sale = p.getPriceValue() * spi.getBidsCount();
				break;
			} else {
				sale = p.getPriceValue();
			}
		}
		return sale;
	}

	private synchronized void incrementSaleBy(Float sale) {
		this.sale += Math.round(sale);

		this.saleRest += (sale - Math.round(sale));

		if (this.saleRest >= 1) {
			this.sale += Math.round(saleRest);
			saleRest = (saleRest - Math.round(saleRest));
		}
	}

	private synchronized void incrementItemsCount() {
		this.itemsCount++;
	}

	private synchronized void incrementBidsCountBy(int bidsCount) {
		this.bidsCount += bidsCount;
	}

	private synchronized void incrementBiddersCountBy(int biddersCount) {
		this.biddersCount += biddersCount;
	}

	private synchronized void incrementLeftCountBy(int leftCount) {
		this.leftCount += leftCount;
	}

	public long getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(long itemsCount) {
		this.itemsCount = itemsCount;
	}

	public long getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(long leftCount) {
		this.leftCount = leftCount;
	}

	public long getBiddersCount() {
		return biddersCount;
	}

	public void setBiddersCount(long biddersCount) {
		this.biddersCount = biddersCount;
	}

	public long getBidsCount() {
		return bidsCount;
	}

	public void setBidsCount(long bidsCount) {
		this.bidsCount = bidsCount;
	}

	public long getSale() {
		return sale;
	}

	public void setSale(long sale) {
		this.sale = sale;
	}

	public Float getSaleRest() {
		return saleRest;
	}

	public void setSaleRest(Float saleRest) {
		this.saleRest = saleRest;
	}
}
