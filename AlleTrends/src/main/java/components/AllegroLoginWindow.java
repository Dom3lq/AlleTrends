package components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import alle.trends.TradelyzerUI;
import connection.ApiConnector;
import pl.allegro.webapi.service_php.DoLoginEncResponse;
import pojos.Shop;
import pojos.User;
import repositories.ShopRepository;
import repositories.UserRepository;

@Component
@SuppressWarnings("serial")
@UIScope
public class AllegroLoginWindow extends Window {

	private TextField allegroNameField;
	private PasswordField allegroPasswordField;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShopRepository shopRepository;
	private View nextView;

	@PostConstruct
	public void init() {
		this.center();
		this.setModal(true);

		Panel loginPanel = new Panel();
		loginPanel.setSizeUndefined();

		FormLayout form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);

		Label header = new Label("Please login to allegro");
		header.setStyleName(ValoTheme.LABEL_H3);
		form.addComponent(header);
		form.setComponentAlignment(header, Alignment.MIDDLE_CENTER);

		allegroNameField = new TextField("Login");
		allegroNameField.setIcon(VaadinIcons.USER);
		allegroNameField.setRequiredIndicatorVisible(true);
		form.addComponent(allegroNameField);

		allegroPasswordField = new PasswordField("Password");
		allegroPasswordField.setIcon(VaadinIcons.PASSWORD);
		allegroPasswordField.setRequiredIndicatorVisible(true);
		form.addComponent(allegroPasswordField);

		User user = VaadinSession.getCurrent().getAttribute(User.class);
		if (user != null) {
			Shop shop = user.getShop();
			if (shop != null) {
				allegroNameField.setValue(shop.getLogin());
				allegroNameField.setReadOnly(true);
				allegroPasswordField.focus();
			} else
				allegroNameField.focus();
		}

		Button loginButton = new Button("Login");
		loginButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		loginButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				authenticate(allegroNameField.getValue(), allegroPasswordField.getValue());
			}

		});
		loginButton.setClickShortcut(KeyCode.ENTER);
		form.addComponent(loginButton);

		loginPanel.setContent(form);
		this.setContent(loginPanel);

	}

	private void authenticate(String name, String password) {
		if (name.isEmpty())
			allegroNameField.focus();
		else {
			if (password.isEmpty())
				allegroPasswordField.focus();
			else {
				ApiConnector ac = new ApiConnector();
				DoLoginEncResponse loginResponse = ac.doLogin(allegroNameField.getValue(),
						allegroPasswordField.getValue());
				if (loginResponse != null) {
					Shop shop = VaadinSession.getCurrent().getAttribute(Shop.class);
					if (shop == null) {
						shop = new Shop();
						shop.setLogin(allegroNameField.getValue());
						shop.setId(loginResponse.getUserId());
						User user = VaadinSession.getCurrent().getAttribute(User.class);
						shop.setUser(user);
						user.setShop(shop);
						userRepository.save(user);
					}

					shop.setSessionPortTime(loginResponse.getServerTime());
					shop.setSessionPort(loginResponse.getSessionHandlePart());

					shopRepository.save(shop);

					VaadinSession.getCurrent().setAttribute(Shop.class, shop);
					MyMenuBar mb = ((TradelyzerUI) UI.getCurrent()).menuBar;
					mb.auctionsMenuItem.setVisible(true);
					mb.allegroUserMenuItem.setText(shop.getName());

					this.nextView.enter(null);
					this.close();
				} else
					Notification.show("Niepoprawna nazwa użytkownika bądź hasło");
			}
		}
	}

	public void setNextView(View view) {
		this.nextView = view;
	}

}
