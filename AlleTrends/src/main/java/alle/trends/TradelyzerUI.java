package alle.trends;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import components.AllegroLoginWindow;
import components.MyMenuBar;
import connection.ApiConnector;
import pojos.Shop;
import pojos.User;
import repositories.UserRepository;
import views.ConfirmMailView;
import views.LoginView;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
@SpringUI
@Theme("valo")
public class TradelyzerUI extends UI {

	private VerticalLayout root;
	private Panel viewContainer;
	public MyMenuBar menuBar;

	@Autowired
	SpringViewProvider viewProvider;
	@Autowired
	private SpringNavigator navi;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AllegroLoginWindow allegroLoginWindow;

	@Override
	protected void init(VaadinRequest request) {

		root = new VerticalLayout();
		root.setSizeFull();
		root.setSpacing(false);
		root.setMargin(false);
		setContent(root);

		menuBar = new MyMenuBar();
		root.addComponent(menuBar);

		viewContainer = new Panel();
		viewContainer.setSizeFull();
		root.addComponent(viewContainer);
		root.setExpandRatio(viewContainer, 1);

		addNavigator();

		Responsive.makeResponsive(this);

		this.getPage().setTitle("Tradelyzer");

		String confirmKey = request.getParameter("confirmkey");

		if (confirmKey != null) {
			User user = userRepository.findOneByConfirmationKey(confirmKey);
			if (user != null) {
				user.setConfirmed(true);
				authorizeUser(user);
				Notification.show("Mail zweryfikowany.");
			}
		}
	}

	public void authorizeUser(User user) {
		VaadinSession.getCurrent().setAttribute(User.class, userRepository.save(user));
		menuBar.userMenuItem.setText(user.getName());
		menuBar.allegroUserMenu.setVisible(true);
	}

	private void addNavigator() {
		navi.init(this, viewContainer);

		UI.getCurrent().setNavigator(navi);

	}

	public User getUser(View view) {
		User user = VaadinSession.getCurrent().getAttribute(User.class);
		if (user == null)
			navi.navigateTo(LoginView.NAME);
		else if (!user.getConfirmed()) {
			navi.navigateTo(ConfirmMailView.NAME);
			return null;
		}

		return user;
	}

	public Shop getShop(View view) {
		if (getUser(view) != null) {

			Shop shop = (Shop) VaadinSession.getCurrent().getAttribute(Shop.class);
			ApiConnector ac = new ApiConnector();
			if (shop == null) {
				allegroLoginWindow.setNextView(view);
				this.addWindow(allegroLoginWindow);
			} else if (shop.getSessionPortTime() + 3500 < ac.getSystemTime()) {
				allegroLoginWindow.setNextView(view);
				this.addWindow(allegroLoginWindow);
				return null;
			}
			return shop;
		} else
			return null;
	}

}
