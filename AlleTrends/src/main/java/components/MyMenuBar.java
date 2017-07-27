package components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import pojos.Shop;
import pojos.User;
import views.AllegroProfileInfoView;
import views.AllegroUserInfoView;
import views.CategoriesView;
import views.DashboardView;
import views.LoginView;
import views.UserAuctionsView;

@SuppressWarnings("serial")
public class MyMenuBar extends HorizontalLayout {

	public MenuItem userMenuItem, allegroUserMenuItem, auctionsMenuItem;
	public MenuBar allegroUserMenu;

	public MyMenuBar() {
		this.setWidth("100%");
		this.setMargin(false);
		this.setStyleName(ValoTheme.MENU_TITLE);

		Button titleButton = new Button("TradeLyzer");
		titleButton.setSizeUndefined();
		titleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		titleButton.addStyleName(ValoTheme.BUTTON_HUGE);
		titleButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME);
			}
		});
		this.addComponent(titleButton);
		this.setComponentAlignment(titleButton, Alignment.MIDDLE_LEFT);

		Label expandingGap = new Label();
		expandingGap.setSizeFull();
		this.addComponent(expandingGap);

		MenuBar publicMenu = new MenuBar();
		publicMenu.setSizeUndefined();
		this.addComponent(publicMenu);
		this.setComponentAlignment(publicMenu, Alignment.MIDDLE_CENTER);

		publicMenu.addItem("Sprzedawcy", VaadinIcons.GROUP, new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(AllegroUserInfoView.NAME);
			}

		});

		publicMenu.addItem("Parametry", VaadinIcons.SLIDERS, new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {

			}

		});

		publicMenu.addItem("Słowa kluczowe", VaadinIcons.SEARCH, new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {

			}

		});

		publicMenu.addItem("Kategorie", VaadinIcons.SLIDERS, new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(CategoriesView.NAME);
			}

		});

		MenuBar userMenu = new MenuBar();
		userMenu.setSizeUndefined();
		this.addComponent(userMenu);
		this.setComponentAlignment(userMenu, Alignment.MIDDLE_CENTER);

		userMenuItem = userMenu.addItem("Login", VaadinIcons.USER, new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				User user = VaadinSession.getCurrent().getAttribute(User.class);
				if (user == null)
					UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
			}
		});

		allegroUserMenu = new MenuBar();
		allegroUserMenu.setSizeUndefined();
		allegroUserMenu.setVisible(false);
		this.addComponent(allegroUserMenu);
		this.setComponentAlignment(allegroUserMenu, Alignment.MIDDLE_CENTER);

		allegroUserMenuItem = allegroUserMenu.addItem("Login to allegro", VaadinIcons.SHOP, new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(AllegroProfileInfoView.NAME);
			}
		});

		auctionsMenuItem = allegroUserMenu.addItem("Auctions", VaadinIcons.LIST_UL, new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(UserAuctionsView.NAME);
			}
		});
		auctionsMenuItem.setVisible(false);

		this.setExpandRatio(expandingGap, 1);
	}

	public void updateInfo() {
		User user = VaadinSession.getCurrent().getAttribute(User.class);
		userMenuItem.setText(user.getName());
		Shop shop = VaadinSession.getCurrent().getAttribute(Shop.class);
		if (shop != null) {
			allegroUserMenuItem.setText(shop.getLogin());
			auctionsMenuItem.setEnabled(true);
		} else {
			allegroUserMenuItem.setText("No shop");
			auctionsMenuItem.setEnabled(false);
		}
	}

}
