package views;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import components.MyMenuBar;
import pojos.User;

@SpringView(name = ConfirmMailView.NAME)
@SuppressWarnings("serial")
public class ConfirmMailView extends VerticalLayout implements View {

	public final static String NAME = "confirm-mail-view";

	private Label header, info;
	private Button sendAgain;
	MyMenuBar menuBar;

	@PostConstruct
	public void init() {
		Panel infoPanel = new Panel();
		infoPanel.setWidth("25%");
		infoPanel.setHeightUndefined();
		this.addComponent(infoPanel);
		this.setComponentAlignment(infoPanel, Alignment.MIDDLE_CENTER);

		FormLayout form = new FormLayout();
		form.setHeightUndefined();
		form.setSpacing(true);
		infoPanel.setContent(form);

		VerticalLayout headerLayout = new VerticalLayout();
		headerLayout.setWidth("100%");
		headerLayout.setHeightUndefined();
		headerLayout.setSpacing(true);
		form.addComponent(headerLayout);

		header = new Label("Welcome");
		header.setStyleName(ValoTheme.LABEL_H3);
		headerLayout.addComponent(header);
		headerLayout.setComponentAlignment(header, Alignment.MIDDLE_CENTER);

		info = new Label("", ContentMode.HTML);
		info.setWidth("100%");
		form.addComponent(info);
		form.setComponentAlignment(info, Alignment.MIDDLE_CENTER);

		VerticalLayout buttonLayout = new VerticalLayout();
		buttonLayout.setWidth("100%");
		buttonLayout.setHeightUndefined();
		buttonLayout.setSpacing(true);
		form.addComponent(buttonLayout);

		sendAgain = new Button("Send again");
		sendAgain.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonLayout.addComponent(sendAgain);
		buttonLayout.setComponentAlignment(sendAgain, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		User user = VaadinSession.getCurrent().getAttribute(User.class);
		if (user == null)
			UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
		else {
			info.setValue("We've sent you an email to adress: <strong>" + user.getName()
					+ "</strong>. Please confirm your registration by clicking link included in email. If you didn't get the email, please click the button below.");
		}

	}
}
