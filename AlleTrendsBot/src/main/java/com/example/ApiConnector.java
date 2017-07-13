package com.example;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

import pl.allegro.webapi.service_php.CatInfoType;
import pl.allegro.webapi.service_php.DoGetCatsDataRequest;
import pl.allegro.webapi.service_php.DoGetItemsListRequest;
import pl.allegro.webapi.service_php.DoGetSystemTimeRequest;
import pl.allegro.webapi.service_php.FilterOptionsType;
import pl.allegro.webapi.service_php.ItemsListType;
import pl.allegro.webapi.service_php.ServicePort;
import pl.allegro.webapi.service_php.ServiceService;
import pl.allegro.webapi.service_php.ServiceServiceLocator;
import pl.allegro.webapi.service_php.SortOptionsType;

public class ApiConnector {
	public static final String WEBAPI_KEY = "fda20357";
	public static final int COUNTRY_KEY = 1;

	private ServiceService allegroWebApiService;
	private ServicePort allegro;

	public ApiConnector() {
		allegroWebApiService = new ServiceServiceLocator();
		try {
			allegro = allegroWebApiService.getservicePort();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public int getItemsSize(String category) {

		DoGetItemsListRequest req = new DoGetItemsListRequest();
		req.setCountryId(COUNTRY_KEY);
		req.setWebapiKey(WEBAPI_KEY);
		req.setResultSize(0);
		req.setResultScope(7);

		FilterOptionsType[] filter = new FilterOptionsType[1];

		FilterOptionsType fotcat = new FilterOptionsType();
		fotcat.setFilterId("category");
		String[] categories = new String[1];
		categories[0] = new String(category);
		fotcat.setFilterValueId(categories);
		filter[0] = fotcat;

		SortOptionsType sort = new SortOptionsType();
		sort.setSortType("startingTime");
		sort.setSortOrder("asc");

		req.setSortOptions(sort);
		req.setFilterOptions(filter);

		try {
			return allegro.doGetItemsList(req).getItemsCount();
		} catch (Exception e) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return getItemsSize(category);
		}

	}

	public ItemsListType[] get100Items(String catId, int offset) {
		return get100Items(catId, offset, 0, 0);
	}

	public ItemsListType[] get100Items(String catId, int offset, int i, int j) {
		DoGetItemsListRequest req = new DoGetItemsListRequest();
		req.setCountryId(COUNTRY_KEY);
		req.setWebapiKey(WEBAPI_KEY);
		req.setResultSize(100);
		req.setResultScope(3);
		req.setResultOffset(offset);

		FilterOptionsType[] filter = new FilterOptionsType[1];

		FilterOptionsType fotcat = new FilterOptionsType();

		fotcat.setFilterId("category");
		String[] categories = new String[1];
		categories[0] = catId;
		fotcat.setFilterValueId(categories);
		filter[0] = fotcat;

		SortOptionsType sort = new SortOptionsType();
		sort.setSortType("startingTime");
		sort.setSortOrder("asc");

		req.setSortOptions(sort);
		req.setFilterOptions(filter);

		ItemsListType[] itemsList;

		try {
			itemsList = allegro.doGetItemsList(req).getItemsList();
			if (itemsList == null) {
				if (i < 5) {
					try {
						Thread.sleep(2000 + (i * 2000));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					itemsList = get100Items(catId, offset, (i + 1), j);
				} else {
					System.err.println("NULL at OFFSET: " + String.format("%,8d", offset) + " CAT ID: " + catId
							+ " CAT SIZE: " + String.format("%,8d", this.getItemsSize(catId)));
				}
			}
		} catch (RemoteException e) {
			if (j < 5) {
				try {
					Thread.sleep(2000 + (j * 2000));
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				itemsList = get100Items(catId, offset, i, (j + 1));
			} else {
				e.printStackTrace();
				return null;
			}
		}
		return itemsList;
	}

	public CatInfoType[] getCategories() {
		DoGetCatsDataRequest request = new DoGetCatsDataRequest();
		request.setCountryId(COUNTRY_KEY);
		request.setWebapiKey(WEBAPI_KEY);

		try {
			return allegro.doGetCatsData(request).getCatsList();
		} catch (RemoteException e) {
			e.printStackTrace();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return getCategories();
		}
	}

	public Long getSystemTime() {
		DoGetSystemTimeRequest req = new DoGetSystemTimeRequest();
		req.setCountryId(COUNTRY_KEY);
		req.setWebapiKey(WEBAPI_KEY);

		try {
			return allegro.doGetSystemTime(req).getServerTime();
		} catch (RemoteException e) {
			e.printStackTrace();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return getSystemTime();
		}

	}
}
