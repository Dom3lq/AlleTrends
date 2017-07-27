package views;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import alle.trends.TradelyzerUI;
import pojos.Shop;
import pojos.User;
import repositories.UserRepository;

@SuppressWarnings("serial")
@SpringView(name = LoginView.NAME)
public class LoginView extends VerticalLayout implements View {

	public static final String NAME = "login-view";

	@Autowired
	private UserRepository userRepository;

	TextField nameField;
	PasswordField passwordField;

	@PostConstruct
	void init() {
		this.setSizeFull();
		Panel loginPanel = new Panel();
		loginPanel.setSizeUndefined();

		FormLayout form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);

		Label header = new Label("Welcome");
		header.setStyleName(ValoTheme.LABEL_H2);
		form.addComponent(header);
		form.setComponentAlignment(header, Alignment.MIDDLE_CENTER);

		nameField = new TextField("Email");
		nameField.setIcon(VaadinIcons.USER);
		nameField.setRequiredIndicatorVisible(true);
		form.addComponent(nameField);

		passwordField = new PasswordField("Password");
		passwordField.setIcon(VaadinIcons.PASSWORD);
		passwordField.setRequiredIndicatorVisible(true);
		form.addComponent(passwordField);

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setWidth("100%");
		buttonsLayout.setHeightUndefined();

		Button loginButton = new Button("Login");
		loginButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		loginButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				authenticate(nameField.getValue(), passwordField.getValue());
			}

			private void authenticate(String name, String password) {
				if (name.isEmpty())
					nameField.focus();
				else {
					if (password.isEmpty())
						passwordField.focus();
					else {
						try {
							byte[] digest = MessageDigest.getInstance("SHA-256").digest(password.getBytes());

							User user = userRepository.findOneByNameAndPassword(name, digest);
							if (user != null) {
								((TradelyzerUI) UI.getCurrent()).authorizeUser(user);

								Shop shop = user.getShop();
								if (shop != null)
									VaadinSession.getCurrent().setAttribute(Shop.class, shop);

								if (user.getConfirmed())
									UI.getCurrent().getNavigator().navigateTo(CategoriesView.NAME);
								else
									UI.getCurrent().getNavigator().navigateTo(ConfirmMailView.NAME);
							} else
								Notification.show("Zła nazwa użytkownika bądź hasło.");
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		loginButton.setClickShortcut(KeyCode.ENTER);
		buttonsLayout.addComponent(loginButton);

		Button registerButton = new Button("Register");
		registerButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		registerButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(RegistrationView.NAME);
			}
		});
		buttonsLayout.addComponent(registerButton);
		form.addComponent(buttonsLayout);

		loginPanel.setContent(form);
		this.addComponent(loginPanel);
		this.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		nameField.focus();
	}

}
