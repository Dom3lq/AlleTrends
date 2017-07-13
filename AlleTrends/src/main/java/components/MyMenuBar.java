package components;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import pojos.Shop;
import pojos.User;
import views.AllegroProfileInfoView;
import views.AllegroUserInfoView;
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

		VerticalLayout titleLayout = new VerticalLayout();
		titleLayout.setSizeUndefined();
		titleLayout.setMargin(false);
		titleLayout.setSpacing(true);
		this.addComponent(titleLayout);

		titleLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME);
			}

		});

		Label title = new Label("<h2>Trade<strong>Lyzer</strong></h2>", ContentMode.HTML);
		title.setSizeUndefined();
		titleLayout.addComponent(title);
		titleLayout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

		Label expandingGap = new Label();
		expandingGap.setSizeFull();
		this.addComponent(expandingGap);

		MenuBar publicMenu = new MenuBar();
		publicMenu.setSizeUndefined();
		this.addComponent(publicMenu);

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

		 publicMenu.addItem("Słowa kluczowe", VaadinIcons.SEARCH, new
		 MenuBar.Command() {
		
		 @Override
		 public void menuSelected(MenuItem selectedItem) {
		
		 }
		
		 });

		publicMenu.addItem("Kategorie", VaadinIcons.SLIDERS, new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {

			}

		});

		MenuBar userMenu = new MenuBar();
		userMenu.setSizeUndefined();
		this.addComponent(userMenu);

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
