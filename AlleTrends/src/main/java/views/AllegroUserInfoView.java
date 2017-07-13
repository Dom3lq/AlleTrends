package views;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import alle.trends.ApiConnector;
import pl.allegro.webapi.service_php.DoShowUserResponse;

@SuppressWarnings("serial")
@SpringView(name = AllegroUserInfoView.NAME)
public class AllegroUserInfoView extends VerticalLayout implements View {

	public final static String NAME = "allegro-user-info";

	private TextField searchUserTF;
	private Button searchUserB;
	private Panel mainInfoPanel;
	private HorizontalLayout botHorizontalLayout, topHorizontalLayout;
	private VerticalLayout topVerticalLayout;
	private Label userLoginL, userIdL, userCountryL, userCreateDateL, userLoginDateL, userRatingL;
	private CheckBoxGroup<String> leftBooleans, rightBooleans;

	@PostConstruct
	public void init() {
		this.setSizeFull();
		this.setMargin(true);
		this.setSpacing(true);

		topVerticalLayout = new VerticalLayout();
		topVerticalLayout.setMargin(true);
		topVerticalLayout.setSizeFull();
		this.addComponent(topVerticalLayout);

		topHorizontalLayout = new HorizontalLayout();
		topHorizontalLayout.setMargin(false);
		topHorizontalLayout.setSizeFull();
		topVerticalLayout.addComponent(topHorizontalLayout);
		topVerticalLayout.setComponentAlignment(topHorizontalLayout, Alignment.MIDDLE_CENTER);

		HorizontalLayout searchUserLayout = new HorizontalLayout();
		searchUserLayout.setMargin(false);
		searchUserLayout.setSizeUndefined();
		topHorizontalLayout.addComponent(searchUserLayout);
		topHorizontalLayout.setComponentAlignment(searchUserLayout, Alignment.MIDDLE_CENTER);

		searchUserTF = new TextField();
		searchUserTF.setDescription("User to search");
		searchUserTF.setValueChangeMode(ValueChangeMode.LAZY);
		searchUserTF.addValueChangeListener(event -> {
			String userName = event.getValue();
			getUserInfo(userName);
		});
		searchUserLayout.addComponent(searchUserTF);

		searchUserB = new Button();
		searchUserB.setIcon(VaadinIcons.SEARCH);
		searchUserB.setSizeUndefined();
		searchUserB.setClickShortcut(KeyCode.ENTER);
		searchUserB.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUserInfo(searchUserTF.getValue());
			}

		});
		searchUserLayout.addComponent(searchUserB);

		botHorizontalLayout = new HorizontalLayout();
		botHorizontalLayout.setSizeFull();
		botHorizontalLayout.setMargin(true);
		botHorizontalLayout.setSpacing(true);
		this.addComponent(botHorizontalLayout);
		this.setExpandRatio(botHorizontalLayout, 1);

		mainInfoPanel = new Panel();
		mainInfoPanel.setWidthUndefined();
		mainInfoPanel.setHeight("100%");
		botHorizontalLayout.addComponent(mainInfoPanel);

		VerticalLayout userInfoLayout = new VerticalLayout();
		userInfoLayout.setMargin(true);
		userInfoLayout.setSpacing(true);
		userInfoLayout.setSizeUndefined();
		mainInfoPanel.setContent(userInfoLayout);

		HorizontalLayout mainInfoLayout = new HorizontalLayout();
		mainInfoLayout.setMargin(true);
		mainInfoLayout.setSpacing(true);
		mainInfoLayout.setSizeUndefined();
		userInfoLayout.addComponent(mainInfoLayout);

		userLoginL = new Label();
		userLoginL.setStyleName(ValoTheme.LABEL_H3);
		userLoginL.setIcon(VaadinIcons.USER);
		userLoginL.setCaption("Nazwa");
		mainInfoLayout.addComponent(userLoginL);

		userIdL = new Label();
		userIdL.setStyleName(ValoTheme.LABEL_LIGHT);
		mainInfoLayout.addComponent(userIdL);

		userCountryL = new Label();
		userCountryL.setStyleName(ValoTheme.LABEL_H3);
		userCountryL.setIcon(VaadinIcons.MAP_MARKER);
		userCountryL.setCaption("Kraj");
		mainInfoLayout.addComponent(userCountryL);

		userRatingL = new Label();
		userRatingL.setStyleName(ValoTheme.LABEL_H3);
		userRatingL.setIcon(VaadinIcons.BOOKMARK);
		userRatingL.setCaption("Ocena");
		mainInfoLayout.addComponent(userRatingL);

		HorizontalLayout datesInfoLayout = new HorizontalLayout();
		datesInfoLayout.setMargin(true);
		datesInfoLayout.setSpacing(true);
		datesInfoLayout.setSizeUndefined();
		userInfoLayout.addComponent(datesInfoLayout);

		userCreateDateL = new Label();
		userCreateDateL.setCaption("Created");
		userCreateDateL.setStyleName(ValoTheme.LABEL_H4);
		datesInfoLayout.addComponent(userCreateDateL);

		userLoginDateL = new Label();
		userLoginDateL.setCaption("Last login");
		userLoginDateL.setStyleName(ValoTheme.LABEL_H4);
		datesInfoLayout.addComponent(userLoginDateL);

		Panel booleansPanel = new Panel();
		booleansPanel.setSizeUndefined();
		booleansPanel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		userInfoLayout.addComponent(booleansPanel);

		HorizontalLayout booleansLayout = new HorizontalLayout();
		booleansLayout.setMargin(true);
		booleansLayout.setSpacing(true);
		booleansLayout.setSizeFull();
		booleansPanel.setContent(booleansLayout);

		leftBooleans = new CheckBoxGroup<String>();
		leftBooleans.setEnabled(false);
		leftBooleans.setSizeUndefined();
		booleansLayout.addComponent(leftBooleans);
		leftBooleans.setItems("Nowy użytkownik", "Pełna aktywacja konta", "Konto zamknięte", "Konto zablokowane",
				"Rozwiązana umowa z Allegro", "Posiada aktywną stronę \"O mnie\"");

		rightBooleans = new CheckBoxGroup<String>();
		rightBooleans.setEnabled(false);
		rightBooleans.setSizeUndefined();
		booleansLayout.addComponent(rightBooleans);
		rightBooleans.setItems("Super sprzedawca", "Eko-użytkownik", "Konto Junior", "Sklep Allegro", "Konto Firma",
				"Standard Allegro");

		VerticalLayout botRightLayout = new VerticalLayout();
		botRightLayout.setSizeFull();
		botRightLayout.setSpacing(true);
		botRightLayout.setMargin(true);
		botHorizontalLayout.addComponent(botRightLayout);
		botHorizontalLayout.setExpandRatio(botRightLayout, 1);
	}

	private void getUserInfo(String userName) {
		ApiConnector ac = new ApiConnector();
		DoShowUserResponse userInfo = ac.getUserInfo(userName);

		if (userInfo == null) {
			Notification.show("User " + userName + " not found");
			botHorizontalLayout.setVisible(false);
			topVerticalLayout.setSizeFull();
		} else {
			botHorizontalLayout.setVisible(true);
			topVerticalLayout.setHeightUndefined();

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

		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		botHorizontalLayout.setVisible(false);
		searchUserTF.focus();
	}

}
