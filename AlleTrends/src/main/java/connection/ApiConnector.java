package connection;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import pl.allegro.webapi.service_php.CatInfoType;
import pl.allegro.webapi.service_php.DoGetCatsDataRequest;
import pl.allegro.webapi.service_php.DoGetItemFieldsRequest;
import pl.allegro.webapi.service_php.DoGetItemsInfoRequest;
import pl.allegro.webapi.service_php.DoGetItemsListRequest;
import pl.allegro.webapi.service_php.DoGetMyDataRequest;
import pl.allegro.webapi.service_php.DoGetMyDataResponse;
import pl.allegro.webapi.service_php.DoGetMySellItemsRequest;
import pl.allegro.webapi.service_php.DoGetMySoldItemsRequest;
import pl.allegro.webapi.service_php.DoGetSellFormFieldsForCategoryRequest;
import pl.allegro.webapi.service_php.DoGetSystemTimeRequest;
import pl.allegro.webapi.service_php.DoLoginEncRequest;
import pl.allegro.webapi.service_php.DoLoginEncResponse;
import pl.allegro.webapi.service_php.DoQuerySysStatusRequest;
import pl.allegro.webapi.service_php.FieldsValue;
import pl.allegro.webapi.service_php.FilterOptionsType;
import pl.allegro.webapi.service_php.ItemsListType;
import pl.allegro.webapi.service_php.SellFormType;
import pl.allegro.webapi.service_php.SellItemStruct;
import pl.allegro.webapi.service_php.ServicePort;
import pl.allegro.webapi.service_php.ServiceService;
import pl.allegro.webapi.service_php.ServiceServiceLocator;
import pl.allegro.webapi.service_php.SoldItemStruct;

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

	public int getItemsSize(String category) {

		DoGetItemsListRequest req = new DoGetItemsListRequest();
		req.setCountryId(CONSTS.countryKey);
		req.setWebapiKey(CONSTS.webapiKey);
		req.setResultSize(0);
		req.setResultScope(7);

		FilterOptionsType[] filter = new FilterOptionsType[1];

		FilterOptionsType fotcat = new FilterOptionsType();

		fotcat.setFilterId("category");
		String[] categories = new String[1];
		categories[0] = new String(category);
		fotcat.setFilterValueId(categories);
		filter[0] = fotcat;

		req.setFilterOptions(filter);

		try {
			return allegro.doGetItemsList(req).getItemsCount();
		} catch (RemoteException e) {
			e.printStackTrace();
			return getItemsSize(category);
		}
	}

	public ItemsListType[] getSinglePageItems(DoGetItemsListRequest req, int offset) {
		ItemsListType[] itemsList;
		req.setResultOffset(offset);

		try {
			itemsList = allegro.doGetItemsList(req).getItemsList();
			if (itemsList == null)
				itemsList = getSinglePageItems(req, offset);
		} catch (RemoteException e) {
			e.printStackTrace();
			itemsList = getSinglePageItems(req, offset);
		}
		return itemsList;
	}

	public DoGetItemsListRequest getItemsRequest(String category) {
		DoGetItemsListRequest req = new DoGetItemsListRequest();
		req.setCountryId(CONSTS.countryKey);
		req.setWebapiKey(CONSTS.webapiKey);
		req.setResultSize(1000);
		req.setResultScope(3);

		FilterOptionsType[] filter = new FilterOptionsType[1];

		FilterOptionsType fotcat = new FilterOptionsType();

		fotcat.setFilterId("category");
		String[] categories = new String[1];
		categories[0] = new String(category);
		fotcat.setFilterValueId(categories);
		filter[0] = fotcat;

		req.setFilterOptions(filter);
		return req;
	}

	public SellItemStruct[] getMySellItems(String sessionId) {
		return getMySellItems(sessionId, 0);
	}

	public SellItemStruct[] getMySellItems(String sessionId, int category) {
		DoGetMySellItemsRequest parameters = new DoGetMySellItemsRequest();
		parameters.setSessionId(sessionId);
		parameters.setPageSize(1000);
		parameters.setPageNumber(10000);
		if (category != 0)
			parameters.setCategoryId(category);

		try {
			return allegro.doGetMySellItems(parameters).getSellItemsList();
		} catch (RemoteException e) {
			AxisFault fault = new AxisFault(e.getMessage());
			System.out.println(fault.toString());
			e.printStackTrace();
		}

		return null;
	}

	public CatInfoType[] getCategories() {
		DoGetCatsDataRequest request = new DoGetCatsDataRequest();
		request.setCountryId(CONSTS.countryKey);
		request.setWebapiKey(CONSTS.webapiKey);

		try {
			return allegro.doGetCatsData(request).getCatsList();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public FieldsValue[] getItemFields(String sessionId, Long itemId) {

		DoGetItemFieldsRequest req = new DoGetItemFieldsRequest();
		req.setItemId(itemId);
		req.setSessionId(sessionId);

		try {
			return allegro.doGetItemFields(req).getItemFields();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	public DoLoginEncResponse doLogin(String userLogin, String userPassword) {
		DoQuerySysStatusRequest sysStat = new DoQuerySysStatusRequest();
		sysStat.setCountryId(CONSTS.countryKey);
		sysStat.setWebapiKey(CONSTS.webapiKey);
		sysStat.setSysvar(3);

		try {
			DoLoginEncRequest doLoginRequest = new DoLoginEncRequest();
			doLoginRequest.setUserLogin(userLogin);

			byte[] digest = MessageDigest.getInstance("SHA-256").digest(userPassword.getBytes());
			String based = new String(Base64.encodeBase64(digest), StandardCharsets.UTF_8);

			doLoginRequest.setUserHashPassword(based);
			doLoginRequest.setCountryCode(CONSTS.countryKey);
			doLoginRequest.setLocalVersion(allegro.doQuerySysStatus(sysStat).getVerKey());
			doLoginRequest.setWebapiKey(CONSTS.webapiKey);

			return allegro.doLoginEnc(doLoginRequest);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Long getSystemTime() {
		DoGetSystemTimeRequest req = new DoGetSystemTimeRequest();
		req.setCountryId(CONSTS.countryKey);
		req.setWebapiKey(CONSTS.webapiKey);

		try {
			return allegro.doGetSystemTime(req).getServerTime();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public DoGetMyDataResponse getUserData(String sessionPort) {
		DoGetMyDataRequest req = new DoGetMyDataRequest();
		req.setSessionHandle(sessionPort);

		try {
			return allegro.doGetMyData(req);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public SoldItemStruct[] getMySoldItems(String sessionPort) {
		DoGetMySoldItemsRequest req = new DoGetMySoldItemsRequest();
		req.setSessionId(sessionPort);
		req.setPageSize(1000);
		req.setPageNumber(10000);

		try {
			return allegro.doGetMySoldItems(req).getSoldItemsList();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<SellFormType> getCategoryFormFields(int cat) {
		DoGetSellFormFieldsForCategoryRequest req = new DoGetSellFormFieldsForCategoryRequest();
		req.setCategoryId(cat);
		req.setCountryId(CONSTS.countryKey);
		req.setWebapiKey(CONSTS.webapiKey);

		try {
			return Arrays.asList(
					allegro.doGetSellFormFieldsForCategory(req).getSellFormFieldsForCategory().getSellFormFieldsList());
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getItemsDescriptions(String sessionId, Long[] allAuctionsIds) {
		DoGetItemsInfoRequest req = new DoGetItemsInfoRequest();
		req.setItemsIdArray(ArrayUtils.toPrimitive(allAuctionsIds));
		req.setSessionHandle(sessionId);
		req.setGetDesc(1);

		try {
			return Arrays.asList(allegro.doGetItemsInfo(req).getArrayItemListInfo()).stream()
					.map(e -> e.getItemInfo().getItDescription()).collect(Collectors.toList());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getGratisItems(String sessionId) {

		Long[] allAuctionsIds = Arrays.asList(getMySellItems(sessionId)).stream().map(i -> i.getItemId())
				.toArray(size -> new Long[size]);

		List<String> descriptions = getItemsDescriptions(sessionId, allAuctionsIds);

		if (descriptions != null) {
			return descriptions;
		} else
			return null;
	}

}
