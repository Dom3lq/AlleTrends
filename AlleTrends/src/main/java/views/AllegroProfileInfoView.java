package views;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import alle.trends.TradelyzerUI;
import connection.ApiConnector;
import pl.allegro.webapi.service_php.DoGetMyDataResponse;
import pl.allegro.webapi.service_php.UserDataStruct;
import pojos.Shop;

@SpringView(name = AllegroProfileInfoView.NAME)
@SuppressWarnings("serial")
public class AllegroProfileInfoView extends VerticalLayout implements View {

	public static final String NAME = "allegro-profile-info-view";

	private Panel userDataPanel;

	private TextField userId, userLogin, userRating, userFirstName, userLastName, userMaidenName, userCompany,
			userCountryId, userStateId, userPostcode, userCity, userAddress, userEmail, userPhone, userPhone2,
			siteCountryId;

	private CheckBox userSsStatus, userJuniorStatus, userHasShop, userCompanyIcon, userIsAllegroStandard;
	private DateField userBirthDate;

	ApiConnector ac = new ApiConnector();

	@PostConstruct
	public void init() {
		this.addComponent(buildContent());
		this.setSpacing(true);
		this.setVisible(false);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Shop shop = ((TradelyzerUI) UI.getCurrent()).getShop(this);
		if (shop != null) {
			if (shop.getSessionPortTime() + 3500 >= ac.getSystemTime()) {
				DoGetMyDataResponse resp = ac.getUserData(shop.getSessionPort());
				UserDataStruct userData = resp.getUserData();

				userId.setValue(String.valueOf(userData.getUserId()));
				userLogin.setValue(userData.getUserLogin());
				userRating.setValue(String.valueOf(userData.getUserRating()));
				userFirstName.setValue(userData.getUserFirstName());
				userLastName.setValue(userData.getUserLastName());
				userMaidenName.setValue(userData.getUserMaidenName());
				userCompany.setValue(userData.getUserCompany());
				userCountryId.setValue(String.valueOf(userData.getUserCountryId()));
				userStateId.setValue(String.valueOf(userData.getUserStateId()));
				userPostcode.setValue(userData.getUserPostcode());
				userCity.setValue(userData.getUserCity());
				userAddress.setValue(userData.getUserAddress());
				userEmail.setValue(userData.getUserEmail());
				userPhone.setValue(userData.getUserPhone());
				userPhone2.setValue(userData.getUserPhone2());
				userSsStatus.setValue(userData.getUserSsStatus() == 1);
				siteCountryId.setValue(String.valueOf(userData.getSiteCountryId()));
				userJuniorStatus.setValue(userData.getUserJuniorStatus() == 1);

				LocalDate birthDate = Instant.ofEpochSecond(userData.getUserBirthDate()).atZone(ZoneId.systemDefault())
						.toLocalDate();
				userBirthDate.setValue(birthDate);

				userHasShop.setValue(userData.getUserHasShop() == 1);
				userCompanyIcon.setValue(userData.getUserCompanyIcon() == 1);
				userIsAllegroStandard.setValue(userData.getUserIsAllegroStandard() == 1);

				this.setVisible(true);
			}
		}
	}

	private VerticalLayout buildContent() {
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setSpacing(true);
		content.setMargin(true);

		content.addComponent(buildUserDataPanel());
		return content;
	}

	private Panel buildUserDataPanel() {
		userDataPanel = new Panel("Dane użytkownika");
		userDataPanel.setWidth("100%");
		userDataPanel.setHeight("100%");

		FormLayout userDataForm = new FormLayout();
		userDataForm.setMargin(true);
		userDataPanel.setContent(userDataForm);

		userId = new TextField("Id");
		userId.setReadOnly(true);
		userId.setWidthUndefined();
		userId.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userId);

		userLogin = new TextField("Login");
		userLogin.setReadOnly(true);
		userLogin.setWidthUndefined();
		userLogin.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userLogin);

		userRating = new TextField("Ocena");
		userRating.setReadOnly(true);
		userRating.setWidthUndefined();
		userRating.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userRating);

		userFirstName = new TextField("Imie");
		userFirstName.setReadOnly(true);
		userFirstName.setWidthUndefined();
		userFirstName.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userFirstName);

		userLastName = new TextField("Nazwisko");
		userLastName.setReadOnly(true);
		userLastName.setWidthUndefined();
		userLastName.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userLastName);

		userMaidenName = new TextField("Nazwisko panieńskie matki");
		userMaidenName.setReadOnly(true);
		userMaidenName.setWidthUndefined();
		userMaidenName.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userMaidenName);

		userCompany = new TextField("Nazwa firmy");
		userCompany.setReadOnly(true);
		userCompany.setWidthUndefined();
		userCompany.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userCompany);

		userCountryId = new TextField("Id kraju");
		userCountryId.setReadOnly(true);
		userCountryId.setWidthUndefined();
		userCountryId.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userCountryId);

		userStateId = new TextField("Id województwa");
		userStateId.setReadOnly(true);
		userStateId.setWidthUndefined();
		userStateId.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userStateId);

		userPostcode = new TextField("Kod pocztowy");
		userPostcode.setReadOnly(true);
		userPostcode.setWidthUndefined();
		userPostcode.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userPostcode);

		userCity = new TextField("Miasto");
		userCity.setReadOnly(true);
		userCity.setWidthUndefined();
		userCity.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userCity);

		userAddress = new TextField("Adres");
		userAddress.setReadOnly(true);
		userAddress.setWidthUndefined();
		userAddress.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userAddress);

		userEmail = new TextField("Email");
		userEmail.setReadOnly(true);
		userEmail.setWidthUndefined();
		userEmail.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userEmail);

		userPhone = new TextField("Telefon");
		userPhone.setReadOnly(true);
		userPhone.setWidthUndefined();
		userPhone.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userPhone);

		userPhone2 = new TextField("Drugi telefon");
		userPhone2.setReadOnly(true);
		userPhone2.setWidthUndefined();
		userPhone2.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(userPhone2);

		siteCountryId = new TextField("Id serwisu");
		siteCountryId.setReadOnly(true);
		siteCountryId.setWidthUndefined();
		siteCountryId.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		userDataForm.addComponent(siteCountryId);

		userBirthDate = new DateField("Data urodzenia");
		userBirthDate.setReadOnly(true);
		userBirthDate.setStyleName(ValoTheme.DATEFIELD_BORDERLESS);
		userDataForm.addComponent(userBirthDate);

		userSsStatus = new CheckBox("Super Sprzedawca");
		userSsStatus.setReadOnly(true);
		userDataForm.addComponent(userSsStatus);

		userJuniorStatus = new CheckBox("Junior");
		userJuniorStatus.setReadOnly(true);
		userDataForm.addComponent(userJuniorStatus);

		userHasShop = new CheckBox("Sklep Allegro");
		userHasShop.setReadOnly(true);
		userDataForm.addComponent(userHasShop);

		userCompanyIcon = new CheckBox("Firma");
		userCompanyIcon.setReadOnly(true);
		userDataForm.addComponent(userCompanyIcon);

		userIsAllegroStandard = new CheckBox("Standard Allegro");
		userIsAllegroStandard.setReadOnly(true);
		userDataForm.addComponent(userIsAllegroStandard);

		return userDataPanel;
	}

}
