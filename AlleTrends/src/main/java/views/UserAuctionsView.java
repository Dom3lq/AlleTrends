package views;

import javax.annotation.PostConstruct;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import alle.trends.TradelyzerUI;
import connection.ApiConnector;
import pl.allegro.webapi.service_php.SellItemStruct;
import pl.allegro.webapi.service_php.SoldItemStruct;
import pojos.Shop;

@SpringView(name = UserAuctionsView.NAME)
@SuppressWarnings("serial")
public class UserAuctionsView extends VerticalLayout implements View {

	public final static String NAME = "user-auctions-view";

	private Grid<SellItemStruct> auctionsGrid;

	private Grid<SoldItemStruct> pastAuctionsGrid;

	private Button cloneButton;

	private Button addTextButton;

	private Button cancelButton;

	private Button changePriceButton;

	private Button changeQuantityButton;

	@PostConstruct
	public void init() {
		addComponent(buildContent());
		setVisible(false);
	}

	private VerticalLayout buildContent() {
		VerticalLayout content = new VerticalLayout();
		content.setSpacing(true);
		content.setMargin(true);

		HorizontalLayout gridContainer = new HorizontalLayout();
		gridContainer.setSpacing(true);
		gridContainer.setMargin(true);
		content.addComponent(gridContainer);

		auctionsGrid = new Grid<SellItemStruct>("Aktywne aukcje");
		auctionsGrid.setSelectionMode(SelectionMode.MULTI);
		auctionsGrid.addColumn(item -> {
			Image image = new Image();
			image.setSizeUndefined();
			image.setSource(new ExternalResource(item.getItemThumbnailUrl()));
			return image;
		}).setCaption("Zdjęcie");
		auctionsGrid.addColumn(SellItemStruct::getItemTitle).setCaption("Nazwa");
		auctionsGrid.addColumn(SellItemStruct::getItemCategoryId).setCaption("Kategoria");
		auctionsGrid.addColumn(SellItemStruct::getItemStartTime).setCaption("Data utworzenia");
		gridContainer.addComponent(auctionsGrid);
		auctionsGrid.addItemClickListener(new ItemClickListener<SellItemStruct>() {

			@Override
			public void itemClick(ItemClick<SellItemStruct> event) {
				int gridSelectedItemsSize = event.getSource().getSelectedItems().size();
				if (gridSelectedItemsSize > 0) {
					if (gridSelectedItemsSize == 1)
						cloneButton.setEnabled(true);
					else
						cloneButton.setEnabled(false);

					addTextButton.setEnabled(true);
					cancelButton.setEnabled(true);
					changePriceButton.setEnabled(true);
					changeQuantityButton.setEnabled(true);

					pastAuctionsGrid.setEnabled(false);
				} else {
					cloneButton.setEnabled(false);
					addTextButton.setEnabled(false);
					cancelButton.setEnabled(false);
					changePriceButton.setEnabled(false);
					changeQuantityButton.setEnabled(false);

					pastAuctionsGrid.setEnabled(true);
				}
			}

		});

		pastAuctionsGrid = new Grid<SoldItemStruct>("Zakończone aukcje");
		pastAuctionsGrid.setSelectionMode(SelectionMode.SINGLE);
		pastAuctionsGrid.addColumn(item -> {
			Image image = new Image();
			image.setSizeUndefined();
			image.setSource(new ExternalResource(item.getItemThumbnailUrl()));
			return image;
		}).setCaption("Zdjęcie");
		pastAuctionsGrid.addColumn(SoldItemStruct::getItemTitle).setCaption("Nazwa");
		pastAuctionsGrid.addColumn(SoldItemStruct::getItemCategoryId).setCaption("Kategoria");
		pastAuctionsGrid.addColumn(SoldItemStruct::getItemStartTime).setCaption("Data utworzenia");
		gridContainer.addComponent(pastAuctionsGrid);
		pastAuctionsGrid.addItemClickListener(new ItemClickListener<SoldItemStruct>() {

			@Override
			public void itemClick(ItemClick<SoldItemStruct> event) {
				int gridSelectedItemsSize = event.getSource().getSelectedItems().size();
				if (gridSelectedItemsSize > 0)
					auctionsGrid.setEnabled(false);
				else
					auctionsGrid.setEnabled(true);
			}

		});

		HorizontalLayout buttonsContainer = new HorizontalLayout();
		buttonsContainer.setSpacing(true);
		buttonsContainer.setMargin(true);
		content.addComponent(buttonsContainer);

		cloneButton = new Button("Kopiuj", VaadinIcons.COPY);
		cloneButton.setEnabled(false);
		buttonsContainer.addComponent(cloneButton);

		addTextButton = new Button("Dodaj tekst", VaadinIcons.PENCIL);
		addTextButton.setEnabled(false);
		buttonsContainer.addComponent(addTextButton);

		cancelButton = new Button("Zakończ", VaadinIcons.FILE_REMOVE);
		cancelButton.setEnabled(false);
		buttonsContainer.addComponent(cancelButton);

		changePriceButton = new Button("Zmień cenę", VaadinIcons.CASH);
		changePriceButton.setEnabled(false);
		buttonsContainer.addComponent(changePriceButton);

		changeQuantityButton = new Button("Zmień ilość przedmiotów", VaadinIcons.CONTROLLER);
		changeQuantityButton.setEnabled(false);
		buttonsContainer.addComponent(changeQuantityButton);

		return content;
	}

	private void refreshGrid() {
		ApiConnector ac = new ApiConnector();
		Shop shop = ((TradelyzerUI) UI.getCurrent()).getShop(this);
		if (shop != null) {
			auctionsGrid.setItems(ac.getMySellItems(shop.getSessionPort()));
			pastAuctionsGrid.setItems(ac.getMySoldItems(shop.getSessionPort()));
			this.setVisible(true);
		} else
			this.setVisible(false);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshGrid();
	}

}
