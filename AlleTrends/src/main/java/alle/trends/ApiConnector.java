package alle.trends;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import javax.xml.rpc.ServiceException;

import com.vaadin.ui.Notification;

import pl.allegro.webapi.service_php.CountryInfoType;
import pl.allegro.webapi.service_php.DoGetCountriesRequest;
import pl.allegro.webapi.service_php.DoGetItemsListRequest;
import pl.allegro.webapi.service_php.DoGetItemsListResponse;
import pl.allegro.webapi.service_php.DoGetSystemTimeRequest;
import pl.allegro.webapi.service_php.DoShowUserRequest;
import pl.allegro.webapi.service_php.DoShowUserResponse;
import pl.allegro.webapi.service_php.FilterOptionsType;
import pl.allegro.webapi.service_php.FiltersListType;
import pl.allegro.webapi.service_php.ServicePort;
import pl.allegro.webapi.service_php.ServiceService;
import pl.allegro.webapi.service_php.ServiceServiceLocator;

public class ApiConnector {

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

	public Long getSystemTime() {
		DoGetSystemTimeRequest req = new DoGetSystemTimeRequest();
		req.setCountryId(CONSTS.countryKey);
		req.setWebapiKey(CONSTS.webapiKey);

		try {
			return allegro.doGetSystemTime(req).getServerTime();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	public DoShowUserResponse getUserInfo(String userName) {
		DoShowUserRequest req = new DoShowUserRequest();
		req.setWebapiKey(CONSTS.webapiKey);
		req.setCountryId(CONSTS.countryKey);
		req.setUserLogin(userName);

		try {
			return allegro.doShowUser(req);
		} catch (RemoteException e) {
			return null;
		}
	}

	public String getCountryName(int countryId) {
		DoGetCountriesRequest req = new DoGetCountriesRequest();
		req.setCountryCode(CONSTS.countryKey);
		req.setWebapiKey(CONSTS.webapiKey);

		try {
			for (CountryInfoType c : allegro.doGetCountries(req).getCountryArray()) {
				if (c.getCountryId() == countryId)
					return c.getCountryName();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<FiltersListType> getFiltersAvailable(List<FilterOptionsType> filters) {
		return getFiltersAvailable(filters, 0);
	}

	public List<FiltersListType> getFiltersAvailable(List<FilterOptionsType> filters, int i) {
		DoGetItemsListRequest req = new DoGetItemsListRequest();
		req.setCountryId(CONSTS.countryKey);
		req.setResultSize(0);
		req.setWebapiKey(CONSTS.webapiKey);
		req.setResultScope(6);

		if (filters != null)
			if (!filters.isEmpty())
				req.setFilterOptions(filters.toArray(new FilterOptionsType[filters.size()]));

		try {
			DoGetItemsListResponse doGetItemsList = allegro.doGetItemsList(req);
			return Arrays.asList(doGetItemsList.getFiltersList());
		} catch (RemoteException e) {
			e.printStackTrace();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (i < 3f)
				return getFiltersAvailable(filters, (i + 1));
			else {
				Notification.show(e.getMessage());
				return null;
			}
		}
	}

}
