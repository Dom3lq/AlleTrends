package views;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

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

	@Autowired
	CategoryRaportRepository categoryRaportRepository;

	@Autowired
	RaportRepository raportRepository;

	@Autowired
	SellerRepository sellerRepository;

	@Autowired
	CategoriesView categoriesView;

	@PostConstruct
	public void init() {
		List<Raport> raports = raportRepository.findByIsCompleteTrueOrderByTimeDesc();

		topSaleCategoriesPanel = new TopListPanel<CategoryRaport>("Główne kategorie o najwyższej sprzedaży.")
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
				});

		topBidsCategoriesPanel = new TopListPanel<CategoryRaport>(
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
						});

		topBiddersCategoriesPanel = new TopListPanel<CategoryRaport>(
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
						});

		this.setWidth("100%");
		this.setHeightUndefined();
		this.addComponents(topSaleCategoriesPanel, topBidsCategoriesPanel, topBiddersCategoriesPanel);
		this.components.forEach(c -> {
			c.setWidth("80%");
			this.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
		});
	}

	@Async
	private void refreshContent() {
		topSaleCategoriesPanel.refreshItems();
		topBidsCategoriesPanel.refreshItems();
		topBiddersCategoriesPanel.refreshItems();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshContent();
	}
}
