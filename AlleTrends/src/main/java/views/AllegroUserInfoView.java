package views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import components.StatisticsPanel;
import components.UserMainInfoPanel;
import pojos.Seller;
import pojos.SellerRaport;
import repositories.CategoryRaportRepository;
import repositories.RaportRepository;
import repositories.SellerRaportRepository;
import repositories.SellerRepository;

@SuppressWarnings("serial")
@SpringView(name = AllegroUserInfoView.NAME)
public class AllegroUserInfoView extends VerticalLayout implements View {

	public final static String NAME = "allegro-user-info";

	private TextField searchUserTF;
	private Button searchUserB;
	private HorizontalLayout botHorizontalLayout, topHorizontalLayout;
	private VerticalLayout topVerticalLayout;
	private UserMainInfoPanel mainInfoPanel;

	@Autowired
	StatisticsPanel statisticsPanel;

	@Autowired
	SellerRaportRepository sellerRaportRepository;

	@Autowired
	SellerRepository sellerRepository;

	@Autowired
	CategoryRaportRepository categoryRaportRepository;

	@Autowired
	RaportRepository raportRepository;

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
			if (!userName.isEmpty())
				refreshUserInfo(userName);
		});
		searchUserLayout.addComponent(searchUserTF);

		searchUserB = new Button();
		searchUserB.setIcon(VaadinIcons.SEARCH);
		searchUserB.setSizeUndefined();
		searchUserB.setClickShortcut(KeyCode.ENTER);
		searchUserB.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (!searchUserTF.isEmpty())
					refreshUserInfo(searchUserTF.getValue());
			}

		});
		searchUserLayout.addComponent(searchUserB);

		mainInfoPanel = new UserMainInfoPanel();

		botHorizontalLayout = new HorizontalLayout();
		botHorizontalLayout.setSizeFull();
		botHorizontalLayout.setMargin(true);
		botHorizontalLayout.setSpacing(true);
		this.addComponent(botHorizontalLayout);
		this.setExpandRatio(botHorizontalLayout, 1);
		botHorizontalLayout.addComponents(mainInfoPanel, statisticsPanel);

		VerticalLayout botRightLayout = new VerticalLayout();
		botRightLayout.setSizeFull();
		botRightLayout.setSpacing(true);
		botRightLayout.setMargin(true);
		botHorizontalLayout.addComponent(botRightLayout);
		botHorizontalLayout.setExpandRatio(botRightLayout, 1);
	}

	private void refreshUserInfo(String userName) {
		if (!mainInfoPanel.refreshUserInfo(userName)) {
			Notification.show("User " + userName + " not found");
			botHorizontalLayout.setVisible(false);
			topVerticalLayout.setSizeFull();
		} else {
			botHorizontalLayout.setVisible(true);
			topVerticalLayout.setHeightUndefined();

			statisticsPanel.setVisible(false);
			Seller seller = sellerRepository.findOneByName(userName);
			if (seller != null) {
				SellerRaport sellerRaport = sellerRaportRepository
						.findFirstBySellerAndCategoryRaportCategoryParentIdOrderByCategoryRaport_Raport_TimeDesc(seller,
								0);
				if (sellerRaport != null) {
					statisticsPanel.setVisible(true);
					statisticsPanel.setContent(userName, sellerRaport);
				} else
					Notification.show("Brak statystyk dotyczących sprzedawcy.");
			} else
				Notification.show("Nie znaleziono sprzedawcy.");

		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		botHorizontalLayout.setVisible(false);
		searchUserTF.focus();
	}

}
