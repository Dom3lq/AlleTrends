package pojos;

import pl.allegro.webapi.service_php.ItemsListType;
import pl.allegro.webapi.service_php.PriceInfoType;

public abstract class Statistics {

	private long itemsCount = 0;

	private long newItemsCount = 0;
	private long usedItemsCount = 0;
	private long undefinedItemsCount = 0;

	private long freeDeliveryItemsCount = 0;
	private long allegroStandardItemsCount = 0;

	private long bidsCount = 0;

	private long newBidsCount = 0;
	private long usedBidsCount = 0;
	private long undefinedBidsCount = 0;

	private long freeDeliveryBidsCount = 0;
	private long allegroStandardBidsCount = 0;

	private Double sale = 0D;

	private Double newSale = 0D;
	private Double usedSale = 0D;
	private Double undefinedSale = 0D;

	private Double freeDeliverySale = 0D;
	private Double allegroStandardSale = 0D;

	private long biddersCount = 0;

	private long newBiddersCount = 0;
	private long usedBiddersCount = 0;
	private long undefinedBiddersCount = 0;

	private long freeDeliveryBiddersCount = 0;
	private long allegroStandardBiddersCount = 0;

	private long leftCount = 0;

	private long newLeftCount = 0;
	private long usedLeftCount = 0;
	private long undefinedLeftCount = 0;

	private long freeDeliveryLeftCount = 0;
	private long allegroStandardLeftCount = 0;

	public void setSale(Double sale) {
		this.sale = sale;
	}

	public Double getSale() {
		return sale;
	}

	public long getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(long itemsCount) {
		this.itemsCount = itemsCount;
	}

	public long getBidsCount() {
		return bidsCount;
	}

	public void setBidsCount(long bidsCount) {
		this.bidsCount = bidsCount;
	}

	public long getBiddersCount() {
		return biddersCount;
	}

	public void setBiddersCount(long biddersCount) {
		this.biddersCount = biddersCount;
	}

	public long getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(long leftCount) {
		this.leftCount = leftCount;
	}

	public synchronized void incrementItemsCount() {
		this.itemsCount++;
	}

	public synchronized void incrementNewItemsCount() {
		this.newItemsCount++;
	}

	public synchronized void incrementUsedItemsCount() {
		this.usedItemsCount++;
	}

	public synchronized void incrementUndefinedItemsCount() {
		this.undefinedItemsCount++;
	}

	public synchronized void incrementFreeDeliveryItemsCount() {
		this.freeDeliveryItemsCount++;
	}

	public synchronized void incrementAllegroStandardItemsCount() {
		this.allegroStandardItemsCount++;
	}

	public synchronized void incrementBidsCountBy(int bidsCount) {
		this.bidsCount += bidsCount;
	}

	public synchronized void incrementNewBidsCountBy(int newBidsCount) {
		this.newBidsCount += newBidsCount;
	}

	public synchronized void incrementUsedBidsCountBy(int usedBidsCount) {
		this.usedBidsCount += usedBidsCount;
	}

	public synchronized void incrementUndefinedBidsCountBy(int undefinedBidsCount) {
		this.undefinedBidsCount += undefinedBidsCount;
	}

	public synchronized void incrementFreeDeliveryBidsCountBy(int freeDeliveryBidsCount) {
		this.freeDeliveryBidsCount += freeDeliveryBidsCount;
	}

	public synchronized void incrementAllegroStandardBidsCountBy(int allegroStandardBidsCount) {
		this.allegroStandardBidsCount += allegroStandardBidsCount;
	}

	public synchronized void incrementBiddersCountBy(int biddersCount) {
		this.biddersCount += biddersCount;
	}

	public synchronized void incrementNewBiddersCountBy(int newBiddersCount) {
		this.newBiddersCount += newBiddersCount;
	}

	public synchronized void incrementUsedBiddersCountBy(int usedBiddersCount) {
		this.usedBiddersCount += usedBiddersCount;
	}

	public synchronized void incrementUndefinedBiddersCountBy(int undefinedBiddersCount) {
		this.undefinedBiddersCount += undefinedBiddersCount;
	}

	public synchronized void incrementFreeDeliveryBiddersCountBy(int freeDeliveryBiddersCount) {
		this.freeDeliveryBiddersCount += freeDeliveryBiddersCount;
	}

	public synchronized void incrementAllegroStandardBiddersCountBy(int allegroStandardBiddersCount) {
		this.allegroStandardBiddersCount += allegroStandardBiddersCount;
	}

	public synchronized void incrementLeftCountBy(int leftCount) {
		this.leftCount += leftCount;
	}

	public synchronized void incrementNewLeftCountBy(int newLeftCount) {
		this.newLeftCount += newLeftCount;
	}

	public synchronized void incrementUsedLeftCountBy(int usedLeftCount) {
		this.usedLeftCount += usedLeftCount;
	}

	public synchronized void incrementUndefinedLeftCountBy(int undefinedLeftCount) {
		this.undefinedLeftCount += undefinedLeftCount;
	}

	public synchronized void incrementFreeDeliveryLeftCountBy(int freeDeliveryLeftCount) {
		this.freeDeliveryLeftCount += freeDeliveryLeftCount;
	}

	public synchronized void incrementAllegroStandardLeftCountBy(int allegroStandardLeftCount) {
		this.allegroStandardLeftCount += allegroStandardLeftCount;
	}

	public synchronized void incrementSaleBy(Float sale) {
		this.sale += sale;
	}

	public synchronized void incrementNewSaleBy(Float newSale) {
		this.newSale += newSale;
	}

	public synchronized void incrementUsedSaleBy(Float usedSale) {
		this.usedSale += usedSale;
	}

	public synchronized void incrementUndefinedSaleBy(Float undefinedSale) {
		this.undefinedSale += undefinedSale;
	}

	public synchronized void incrementFreeDeliverySaleBy(Float freeDeliverySale) {
		this.freeDeliverySale += freeDeliverySale;
	}

	public synchronized void incrementAllegroStandardSaleBy(Float allegroStandardSale) {
		this.allegroStandardSale += allegroStandardSale;
	}

	public long getNewItemsCount() {
		return newItemsCount;
	}

	public void setNewItemsCount(long newItemsCount) {
		this.newItemsCount = newItemsCount;
	}

	public long getUsedItemsCount() {
		return usedItemsCount;
	}

	public void setUsedItemsCount(long usedItemsCount) {
		this.usedItemsCount = usedItemsCount;
	}

	public long getUndefinedItemsCount() {
		return undefinedItemsCount;
	}

	public void setUndefinedItemsCount(long undefinedItemsCount) {
		this.undefinedItemsCount = undefinedItemsCount;
	}

	public long getFreeDeliveryItemsCount() {
		return freeDeliveryItemsCount;
	}

	public void setFreeDeliveryItemsCount(long freeDeliveryItemsCount) {
		this.freeDeliveryItemsCount = freeDeliveryItemsCount;
	}

	public long getAllegroStandardItemsCount() {
		return allegroStandardItemsCount;
	}

	public void setAllegroStandardItemsCount(long allegroStandardItemsCount) {
		this.allegroStandardItemsCount = allegroStandardItemsCount;
	}

	public long getNewBidsCount() {
		return newBidsCount;
	}

	public void setNewBidsCount(long newBidsCount) {
		this.newBidsCount = newBidsCount;
	}

	public long getUsedBidsCount() {
		return usedBidsCount;
	}

	public void setUsedBidsCount(long usedBidsCount) {
		this.usedBidsCount = usedBidsCount;
	}

	public long getUndefinedBidsCount() {
		return undefinedBidsCount;
	}

	public void setUndefinedBidsCount(long undefinedBidsCount) {
		this.undefinedBidsCount = undefinedBidsCount;
	}

	public long getFreeDeliveryBidsCount() {
		return freeDeliveryBidsCount;
	}

	public void setFreeDeliveryBidsCount(long freeDeliveryBidsCount) {
		this.freeDeliveryBidsCount = freeDeliveryBidsCount;
	}

	public long getAllegroStandardBidsCount() {
		return allegroStandardBidsCount;
	}

	public void setAllegroStandardBidsCount(long allegroStandardBidsCount) {
		this.allegroStandardBidsCount = allegroStandardBidsCount;
	}

	public Double getNewSale() {
		return newSale;
	}

	public void setNewSale(Double newSale) {
		this.newSale = newSale;
	}

	public Double getUsedSale() {
		return usedSale;
	}

	public void setUsedSale(Double usedSale) {
		this.usedSale = usedSale;
	}

	public Double getUndefinedSale() {
		return undefinedSale;
	}

	public void setUndefinedSale(Double undefinedSale) {
		this.undefinedSale = undefinedSale;
	}

	public Double getFreeDeliverySale() {
		return freeDeliverySale;
	}

	public void setFreeDeliverySale(Double freeDeliverySale) {
		this.freeDeliverySale = freeDeliverySale;
	}

	public Double getAllegroStandardSale() {
		return allegroStandardSale;
	}

	public void setAllegroStandardSale(Double allegroStandardSale) {
		this.allegroStandardSale = allegroStandardSale;
	}

	public void incrementBy(ItemsListType spi) {
		int bidsCount = spi.getBidsCount();
		Float sale = getSale(spi);

		incrementBiddersCountBy(spi.getBiddersCount());
		incrementBidsCountBy(bidsCount);
		incrementItemsCount();
		incrementLeftCountBy(spi.getLeftCount());
		incrementSaleBy(sale);

		if (spi.getConditionInfo().equals("new")) {
			incrementNewBidsCountBy(bidsCount);
			incrementNewItemsCount();
			incrementNewSaleBy(sale);
			incrementNewBiddersCountBy(spi.getBiddersCount());
			incrementNewLeftCountBy(spi.getLeftCount());
		} else if (spi.getConditionInfo().equals("used")) {
			incrementUsedBidsCountBy(bidsCount);
			incrementUsedItemsCount();
			incrementUsedSaleBy(sale);
			incrementUsedBiddersCountBy(spi.getBiddersCount());
			incrementUsedLeftCountBy(spi.getLeftCount());
		} else {
			incrementUndefinedBidsCountBy(bidsCount);
			incrementUndefinedItemsCount();
			incrementUndefinedSaleBy(sale);
			incrementUndefinedBiddersCountBy(spi.getBiddersCount());
			incrementUndefinedLeftCountBy(spi.getLeftCount());
		}

		if (spi.getAdditionalInfo() % 2 == 1) {
			incrementAllegroStandardBidsCountBy(bidsCount);
			incrementAllegroStandardItemsCount();
			incrementAllegroStandardSaleBy(sale);
			incrementAllegroStandardBiddersCountBy(spi.getBiddersCount());
			incrementAllegroStandardLeftCountBy(spi.getLeftCount());
		}

		if (spi.getAdditionalInfo() >= 2) {
			incrementFreeDeliveryBidsCountBy(bidsCount);
			incrementFreeDeliveryItemsCount();
			incrementFreeDeliverySaleBy(sale);
			incrementFreeDeliveryBiddersCountBy(spi.getBiddersCount());
			incrementFreeDeliveryLeftCountBy(spi.getLeftCount());
		}
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

	public long getNewBiddersCount() {
		return newBiddersCount;
	}

	public void setNewBiddersCount(long newBiddersCount) {
		this.newBiddersCount = newBiddersCount;
	}

	public long getUsedBiddersCount() {
		return usedBiddersCount;
	}

	public void setUsedBiddersCount(long usedBiddersCount) {
		this.usedBiddersCount = usedBiddersCount;
	}

	public long getUndefinedBiddersCount() {
		return undefinedBiddersCount;
	}

	public void setUndefinedBiddersCount(long undefinedBiddersCount) {
		this.undefinedBiddersCount = undefinedBiddersCount;
	}

	public long getFreeDeliveryBiddersCount() {
		return freeDeliveryBiddersCount;
	}

	public void setFreeDeliveryBiddersCount(long freeDeliveryBiddersCount) {
		this.freeDeliveryBiddersCount = freeDeliveryBiddersCount;
	}

	public long getAllegroStandardBiddersCount() {
		return allegroStandardBiddersCount;
	}

	public void setAllegroStandardBiddersCount(long allegroStandardBiddersCount) {
		this.allegroStandardBiddersCount = allegroStandardBiddersCount;
	}

	public long getNewLeftCount() {
		return newLeftCount;
	}

	public void setNewLeftCount(long newLeftCount) {
		this.newLeftCount = newLeftCount;
	}

	public long getUsedLeftCount() {
		return usedLeftCount;
	}

	public void setUsedLeftCount(long usedLeftCount) {
		this.usedLeftCount = usedLeftCount;
	}

	public long getUndefinedLeftCount() {
		return undefinedLeftCount;
	}

	public void setUndefinedLeftCount(long undefinedLeftCount) {
		this.undefinedLeftCount = undefinedLeftCount;
	}

	public long getFreeDeliveryLeftCount() {
		return freeDeliveryLeftCount;
	}

	public void setFreeDeliveryLeftCount(long freeDeliveryLeftCount) {
		this.freeDeliveryLeftCount = freeDeliveryLeftCount;
	}

	public long getAllegroStandardLeftCount() {
		return allegroStandardLeftCount;
	}

	public void setAllegroStandardLeftCount(long allegroStandardLeftCount) {
		this.allegroStandardLeftCount = allegroStandardLeftCount;
	}
}
