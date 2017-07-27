package views;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

import alle.trends.TradelyzerUI;
import pojos.User;
import repositories.UserRepository;

@SpringView(name = RegistrationView.NAME)
@SuppressWarnings("serial")
public class RegistrationView extends VerticalLayout implements View {

	public static final String NAME = "register-view";

	@Autowired
	UserRepository userRepository;

	TextField nameField;
	TextField allegroLoginField;
	PasswordField allegroPasswordField, confirmAllegroPasswordField;
	PasswordField passwordField, confirmPasswordField;

	@PostConstruct
	void init() {
		this.setSizeFull();
		Panel loginPanel = new Panel();
		loginPanel.setSizeUndefined();

		FormLayout form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);

		Label header = new Label("Register");
		header.setStyleName(ValoTheme.LABEL_H2);
		form.addComponent(header);
		form.setComponentAlignment(header, Alignment.MIDDLE_CENTER);

		nameField = new TextField("Email");
		nameField.setIcon(VaadinIcons.USER);
		nameField.setRequiredIndicatorVisible(true);
		form.addComponent(nameField);

		passwordField = new PasswordField("Password");
		passwordField.setIcon(VaadinIcons.LOCK);
		passwordField.setRequiredIndicatorVisible(true);
		form.addComponent(passwordField);

		confirmPasswordField = new PasswordField("Confirm Password");
		confirmPasswordField.setIcon(VaadinIcons.ROTATE_LEFT);
		confirmPasswordField.setRequiredIndicatorVisible(true);
		form.addComponent(confirmPasswordField);

		HorizontalLayout buttonsLayout = new HorizontalLayout();

		Button createButton = new Button("Create");
		createButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		createButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				register(nameField.getValue(), passwordField.getValue(), confirmPasswordField.getValue());
			}

			private void register(String name, String password, String confirm) {
				if (name.isEmpty())
					nameField.focus();
				else if (userRepository.exists(name))
					Notification.show("This email is already registered.");
				else if (password.isEmpty())
					passwordField.focus();
				else if (confirm.isEmpty())
					confirmPasswordField.focus();
				else if (!password.equals(confirm))
					Notification.show("Passwords mismatch.");
				else {
					try {
						User user = new User();
						user.setName(name);
						byte[] digest = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
						user.setPassword(digest);
						user.setConfirmed(false);

						SecureRandom random = new SecureRandom();
						user.setConfirmationKey(new BigInteger(130, random).toString(32));

						userRepository.save(user);

						((TradelyzerUI) UI.getCurrent()).authorizeUser(user);

						UI.getCurrent().getNavigator().navigateTo(ConfirmMailView.NAME);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}

				}

			}

		});
		buttonsLayout.addComponent(createButton);

		Button cancelButton = new Button("Cancel");
		cancelButton.setStyleName(ValoTheme.BUTTON_DANGER);
		cancelButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
			}
		});
		buttonsLayout.addComponent(cancelButton);
		form.addComponent(buttonsLayout);

		loginPanel.setContent(form);
		this.addComponent(loginPanel);
		this.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
