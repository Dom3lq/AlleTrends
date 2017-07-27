package components;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import alle.trends.ApiConnector;
import pl.allegro.webapi.service_php.DoShowUserResponse;

@SuppressWarnings("serial")
public class UserMainInfoPanel extends Panel {

	private Label userLoginL;
	private Label userIdL;
	private Label userCountryL;
	private Label userRatingL;
	private Label userCreateDateL;
	private Label userLoginDateL;
	private CheckBoxGroup<String> rightBooleans;
	private CheckBoxGroup<String> leftBooleans;

	public UserMainInfoPanel() {
		userLoginL = new Label();
		userLoginL.setStyleName(ValoTheme.LABEL_H3);
		userLoginL.setIcon(VaadinIcons.USER);
		userLoginL.setCaption("Nazwa");

		userIdL = new Label();
		userIdL.setStyleName(ValoTheme.LABEL_LIGHT);
		userIdL.setIcon(VaadinIcons.FLAG);
		userIdL.setCaption("Id");

		userCountryL = new Label();
		userCountryL.setStyleName(ValoTheme.LABEL_H3);
		userCountryL.setIcon(VaadinIcons.MAP_MARKER);
		userCountryL.setCaption("Kraj");

		userRatingL = new Label();
		userRatingL.setStyleName(ValoTheme.LABEL_H3);
		userRatingL.setIcon(VaadinIcons.BOOKMARK);
		userRatingL.setCaption("Ocena");

		HorizontalLayout mainInfoLayout = new HorizontalLayout();
		mainInfoLayout.setMargin(true);
		mainInfoLayout.setSpacing(true);
		mainInfoLayout.setSizeUndefined();
		mainInfoLayout.addComponents(userLoginL, userIdL, userCountryL, userRatingL);

		userCreateDateL = new Label();
		userCreateDateL.setCaption("Created");
		userCreateDateL.setStyleName(ValoTheme.LABEL_H4);

		userLoginDateL = new Label();
		userLoginDateL.setCaption("Last login");
		userLoginDateL.setStyleName(ValoTheme.LABEL_H4);

		HorizontalLayout datesInfoLayout = new HorizontalLayout();
		datesInfoLayout.setMargin(true);
		datesInfoLayout.setSpacing(true);
		datesInfoLayout.setSizeUndefined();
		datesInfoLayout.addComponents(userCreateDateL, userLoginDateL);

		leftBooleans = new CheckBoxGroup<String>();
		leftBooleans.setEnabled(false);
		leftBooleans.setSizeUndefined();
		leftBooleans.setItems("Nowy użytkownik", "Pełna aktywacja konta", "Konto zamknięte", "Konto zablokowane",
				"Rozwiązana umowa z Allegro", "Posiada aktywną stronę \"O mnie\"");

		rightBooleans = new CheckBoxGroup<String>();
		rightBooleans.setEnabled(false);
		rightBooleans.setSizeUndefined();
		rightBooleans.setItems("Super sprzedawca", "Eko-użytkownik", "Konto Junior", "Sklep Allegro", "Konto Firma",
				"Standard Allegro");

		HorizontalLayout booleansLayout = new HorizontalLayout();
		booleansLayout.setMargin(true);
		booleansLayout.setSpacing(true);
		booleansLayout.setSizeFull();
		booleansLayout.addComponents(leftBooleans, rightBooleans);

		Panel booleansPanel = new Panel();
		booleansPanel.setSizeUndefined();
		booleansPanel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		booleansPanel.setContent(booleansLayout);

		VerticalLayout userInfoLayout = new VerticalLayout();
		userInfoLayout.setMargin(true);
		userInfoLayout.setSpacing(true);
		userInfoLayout.setSizeUndefined();
		userInfoLayout.addComponents(mainInfoLayout, datesInfoLayout, booleansPanel);

		this.setContent(userInfoLayout);
		this.setSizeUndefined();
	}

	public boolean refreshUserInfo(String userName) {
		ApiConnector ac = new ApiConnector();
		DoShowUserResponse userInfo = ac.getUserInfo(userName);

		if (userInfo == null) {
			return false;
		} else {
			userLoginL.setValue(userInfo.getUserLogin());
			userIdL.setValue("(" + userInfo.getUserId() + ")");

			String countryName = ac.getCountryName(userInfo.getUserCountry());
			if (countryName != null) {
				userCountryL.setValue(countryName);
				userCountryL.setVisible(true);
			} else
				userCountryL.setVisible(false);

			userRatingL.setValue(String.valueOf(userInfo.getUserRating()));

			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

			LocalDateTime createDate = Instant.ofEpochMilli(userInfo.getUserCreateDate() * 1000)
					.atZone(ZoneId.systemDefault()).toLocalDateTime();
			userCreateDateL.setValue(createDate.format(format));

			LocalDateTime loginDate = Instant.ofEpochMilli(userInfo.getUserLoginDate() * 1000)
					.atZone(ZoneId.systemDefault()).toLocalDateTime();
			userLoginDateL.setValue(loginDate.format(format));

			leftBooleans.deselectAll();

			if (userInfo.getUserBlocked() == 1)
				leftBooleans.select("Konto zablokowane");
			if (userInfo.getUserIsNewUser() == 1)
				leftBooleans.select("Nowy użykownik");
			if (userInfo.getUserClosed() == 1)
				leftBooleans.select("Konto zamknięte");
			if (userInfo.getUserHasPage() == 1)
				leftBooleans.select("Posiada aktywną stronę \"O mnie\"");
			if (userInfo.getUserNotActivated() == 0)
				leftBooleans.select("Pełna aktywacja konta");
			if (userInfo.getUserTerminated() == 1)
				leftBooleans.select("Rozwiązana umowa z Allegro");

			rightBooleans.deselectAll();

			if (userInfo.getUserCompanyIcon() == 1)
				rightBooleans.select("Konto Firma");
			if (userInfo.getUserHasShop() == 1)
				rightBooleans.select("Sklep Allegro");
			if (userInfo.getUserIsAllegroStandard() == 1)
				rightBooleans.select("Standard Allegro");
			if (userInfo.getUserIsEco() == 1)
				rightBooleans.select("Eko-użytkownik");
			if (userInfo.getUserIsSseller() == 1)
				rightBooleans.select("Super sprzedawca");
			if (userInfo.getUserJuniorStatus() == 1)
				rightBooleans.select("Konto Junior");

			return true;
		}
	}
}
