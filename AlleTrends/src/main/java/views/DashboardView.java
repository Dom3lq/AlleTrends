package views;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import components.ItemsProvider;
import components.TopListPanel;
import pojos.CategoryRaport;
import pojos.Raport;
import pojos.Seller;
import repositories.CategoryRaportRepository;
import repositories.RaportRepository;
import repositories.SellerRepository;

@SuppressWarnings("serial")
@SpringView(name = DashboardView.NAME)
public class DashboardView extends VerticalLayout implements View {

	public final static String NAME = "";

	private TopListPanel<CategoryRaport> topSaleCategoriesPanel;
	private TopListPanel<CategoryRaport> topBidsCategoriesPanel;
	private TopListPanel<CategoryRaport> topBiddersCategoriesPanel;
	private TopListPanel<Object[]> topBidsPerAuctionPanel;
	private TopListPanel<Object[]> topSaleGrowthCategoriesPanel;

	private TopListPanel<Object[]> topBidsCountSellersPanel;
	private TopListPanel<Object[]> topSaleSellersPanel;
	private TopListPanel<Object[]> topSaleGrowthSellersPanel;
	private TopListPanel<Object[]> topItemsCountSellersPanel;

	@Autowired
	private CategoryRaportRepository categoryRaportRepository;

	@Autowired
	private RaportRepository raportRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@PostConstruct
	public void init() {
		List<Raport> raports = raportRepository.findByIsCompleteTrueOrderByTimeDesc();

		topSaleCategoriesPanel = new TopListPanel.Builder<CategoryRaport>("Główne kategorie o najwyższej sprzedaży.")
				.setNameProvider(catRap -> catRap.getCategory().getName())
				.setAdditionalInfoProvider(catRap -> "Id: " + catRap.getCategory().getId())
				.setValueProvider(catRap -> catRap.getAllStatistics().getSale())
				.setItemsProvider(new ItemsProvider<CategoryRaport>() {

					@Override
					public List<CategoryRaport> getItems() {
						if (!raports.isEmpty())
							return categoryRaportRepository
									.findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsSaleDesc(0,
											raports.get(0));
						else
							return null;
					}

				}).setItemClickListener(catRap -> {
					UI.getCurrent().getNavigator().navigateTo(CategoriesView.NAME + "/" + catRap.getCategory().getId());
				}).get();

		topBidsCategoriesPanel = new TopListPanel.Builder<CategoryRaport>(
				"Główne kategorie o największej ilości sprzedanych przedmiotów.")
						.setNameProvider(catRap -> catRap.getCategory().getName())
						.setAdditionalInfoProvider(catRap -> "Id: " + catRap.getCategory().getId())
						.setValueProvider(catRap -> catRap.getAllStatistics().getBidsCount())
						.setItemsProvider(new ItemsProvider<CategoryRaport>() {

							@Override
							public List<CategoryRaport> getItems() {
								if (!raports.isEmpty())
									return categoryRaportRepository
											.findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsBidsCountDesc(0,
													raports.get(0));
								else
									return null;
							}

						}).setItemClickListener(catRap -> {
							UI.getCurrent().getNavigator()
									.navigateTo(CategoriesView.NAME + "/" + catRap.getCategory().getId());
						}).get();

		topBiddersCategoriesPanel = new TopListPanel.Builder<CategoryRaport>(
				"Główne kategorie o największej ilości kupujących.")
						.setNameProvider(catRap -> catRap.getCategory().getName())
						.setAdditionalInfoProvider(catRap -> "Id: " + catRap.getCategory().getId())
						.setValueProvider(catRap -> catRap.getAllStatistics().getBiddersCount())
						.setItemsProvider(new ItemsProvider<CategoryRaport>() {

							@Override
							public List<CategoryRaport> getItems() {
								if (!raports.isEmpty())
									return categoryRaportRepository
											.findTop20ByCategoryParentIdAndRaportOrderByAllStatisticsBiddersCountDesc(0,
													raports.get(0));
								else
									return null;
							}

						}).setItemClickListener(catRap -> {
							UI.getCurrent().getNavigator()
									.navigateTo(CategoriesView.NAME + "/" + catRap.getCategory().getId());
						}).get();

		topBidsPerAuctionPanel = new TopListPanel.Builder<Object[]>(
				"Główne kategorie o największej ilości sprzedanych przedmiotów na aukcję.")
						.setNameProvider(ob -> ((CategoryRaport) ob[0]).getCategory().getName())
						.setAdditionalInfoProvider(ob -> "Id: " + ((CategoryRaport) ob[0]).getCategory().getId())
						.setValueProvider(ob -> (Long) ob[1]).setItemsProvider(new ItemsProvider<Object[]>() {

							@Override
							public List<Object[]> getItems() {
								if (!raports.isEmpty())
									return categoryRaportRepository.findTopBidsPerAuction(0, raports.get(0).getTime());
								else
									return null;
							}

						}).setItemClickListener(ob -> {
							UI.getCurrent().getNavigator().navigateTo(
									CategoriesView.NAME + "/" + ((CategoryRaport) ob[0]).getCategory().getId());
						}).get();

		topSaleGrowthCategoriesPanel = new TopListPanel.Builder<Object[]>(
				"Główne kategorie o największym wzroście sprzedaży.")
						.setNameProvider(ob -> ((CategoryRaport) ob[0]).getCategory().getName())
						.setAdditionalInfoProvider(ob -> "Id: " + ((CategoryRaport) ob[0]).getCategory().getId())
						.setValueProvider(ob -> ((CategoryRaport) ob[0]).getAllStatistics().getSale())
						.setChangeProvider(ob -> (Long) ob[1]).setItemsProvider(new ItemsProvider<Object[]>() {

							@Override
							public List<Object[]> getItems() {
								if (raports.size() > 1)
									return categoryRaportRepository.findTopSaleGrowth(0, raports.get(0).getTime(),
											raports.get(1).getTime());
								else
									return null;
							}

						}).setItemClickListener(ob -> {
							UI.getCurrent().getNavigator().navigateTo(
									CategoriesView.NAME + "/" + ((CategoryRaport) ob[0]).getCategory().getId());
						}).get();

		topBidsCountSellersPanel = new TopListPanel.Builder<Object[]>(
				"Sprzedawcy o największej ilości sprzedanych przedmiotów.")
						.setNameProvider(ob -> ((Seller) ob[0]).getName())
						.setAdditionalInfoProvider(ob -> "Id: " + ((Seller) ob[0]).getId())
						.setValueProvider(ob -> ((Long) ob[1])).setItemsProvider(new ItemsProvider<Object[]>() {

							@Override
							public List<Object[]> getItems() {
								if (!raports.isEmpty())
									return sellerRepository
											.findTop20ByRaportTimeOrderByBidsCount(raports.get(0).getTime());
								else
									return null;
							}

						}).setValueInfoProvider(ob -> "szt.").setItemClickListener(ob -> {
							UI.getCurrent().getNavigator().navigateTo(
									CategoriesView.NAME + "/" + ((CategoryRaport) ob[0]).getCategory().getId());
						}).get();

		topSaleSellersPanel = new TopListPanel.Builder<Object[]>("Sprzedawcy o największej sprzedaży.")
				.setNameProvider(ob -> ((Seller) ob[0]).getName())
				.setAdditionalInfoProvider(ob -> "Id: " + ((Seller) ob[0]).getId())
				.setValueProvider(ob -> ((Long) ob[1])).setItemsProvider(new ItemsProvider<Object[]>() {

					@Override
					public List<Object[]> getItems() {
						if (!raports.isEmpty())
							return sellerRepository.findTop20ByRaportTimeOrderBySale(raports.get(0).getTime());
						else
							return null;
					}

				}).setItemClickListener(ob -> {
					UI.getCurrent().getNavigator()
							.navigateTo(CategoriesView.NAME + "/" + ((CategoryRaport) ob[0]).getCategory().getId());
				}).get();

		topSaleGrowthSellersPanel = new TopListPanel.Builder<Object[]>("Sprzedawcy o największym wzroście sprzedaży.")
				.setNameProvider(ob -> ((Seller) ob[0]).getName())
				.setAdditionalInfoProvider(ob -> "Id: " + ((Seller) ob[0]).getId())
				.setChangeProvider(ob -> (Long) ob[1]).setItemsProvider(new ItemsProvider<Object[]>() {

					@Override
					public List<Object[]> getItems() {
						if (raports.size() > 1)
							return sellerRepository.findTop20ByRaportTimeOrderBySaleGrowth(raports.get(0).getTime(),
									raports.get(1).getTime());
						else
							return null;
					}

				}).setItemClickListener(ob -> {
					UI.getCurrent().getNavigator()
							.navigateTo(CategoriesView.NAME + "/" + ((CategoryRaport) ob[0]).getCategory().getId());
				}).get();

		topSaleGrowthSellersPanel = new TopListPanel.Builder<Object[]>("Sprzedawcy o największym wzroście sprzedaży.")
				.setNameProvider(ob -> ((Seller) ob[0]).getName())
				.setAdditionalInfoProvider(ob -> "Id: " + ((Seller) ob[0]).getId())
				.setChangeProvider(ob -> (Long) ob[1]).setItemsProvider(new ItemsProvider<Object[]>() {

					@Override
					public List<Object[]> getItems() {
						if (raports.size() > 1)
							return sellerRepository.findTop20ByRaportTimeOrderBySaleGrowth(raports.get(0).getTime(),
									raports.get(1).getTime());
						else
							return null;
					}

				}).setItemClickListener(ob -> {
					UI.getCurrent().getNavigator()
							.navigateTo(CategoriesView.NAME + "/" + ((CategoryRaport) ob[0]).getCategory().getId());
				}).get();

		topItemsCountSellersPanel = new TopListPanel.Builder<Object[]>("Sprzedawcy o największej ilości aukcji.")
				.setNameProvider(ob -> ((Seller) ob[0]).getName())
				.setAdditionalInfoProvider(ob -> "Id: " + ((Seller) ob[0]).getId()).setValueProvider(ob -> (Long) ob[1])
				.setItemsProvider(new ItemsProvider<Object[]>() {

					@Override
					public List<Object[]> getItems() {
						if (!raports.isEmpty())
							return sellerRepository.findTop20ByRaportTimeOrderByItemsCount(raports.get(0).getTime());
						else
							return null;
					}

				}).setItemClickListener(ob -> {
					UI.getCurrent().getNavigator()
							.navigateTo(CategoriesView.NAME + "/" + ((CategoryRaport) ob[0]).getCategory().getId());
				}).get();

		this.setWidth("100%");
		this.setHeightUndefined();
		this.addComponents(topSaleCategoriesPanel, topBidsCategoriesPanel, topBiddersCategoriesPanel,
				topBidsPerAuctionPanel, topSaleGrowthCategoriesPanel, topBidsCountSellersPanel, topSaleSellersPanel,
				topSaleGrowthSellersPanel, topItemsCountSellersPanel);
		this.components.forEach(c -> {
			c.setWidth("80%");
			c.setVisible(false);
			this.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
		});

	}

	private void refreshContent() {
		topSaleCategoriesPanel.refreshItems();
		topBidsCategoriesPanel.refreshItems();
		topBiddersCategoriesPanel.refreshItems();
		topBidsPerAuctionPanel.refreshItems();
		topSaleGrowthCategoriesPanel.refreshItems();
		topBidsCountSellersPanel.refreshItems();
		topSaleSellersPanel.refreshItems();
		topItemsCountSellersPanel.refreshItems();
		topSaleGrowthSellersPanel.refreshItems();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshContent();
	}
}
