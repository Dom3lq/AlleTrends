package views;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import components.StatisticsLayout;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;

import pojos.Category;
import pojos.CategoryRaport;
import pojos.Raport;
import repositories.CategoryRaportRepository;
import repositories.RaportRepository;

@SuppressWarnings("serial")
@SpringView(name = DashboardView.NAME)
@UIScope
@Transactional
public class DashboardView extends VerticalLayout implements View {

	public final static String NAME = "";

	@Autowired
	RaportRepository raportRepository;

	@Autowired
	CategoryRaportRepository categoryRaportRepository;

	List<Raport> raports;
	Raport selectedRaport;

	Panel topPanel, botPanel, categoriesTreePanel, statisticsPanel, keyWordsPanel, sellersPanel;
	VerticalLayout botLayout;
	HorizontalLayout botTopLayout, botBotLayout, headerLayout;

	@PostConstruct
	public void init() {
		this.setSizeFull();
		this.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		topPanel = new Panel();
		topPanel.setWidth("100%");
		topPanel.setHeightUndefined();
		topPanel.setStyleName(ValoTheme.PANEL_BORDERLESS);

		botPanel = new Panel();
		botPanel.setSizeFull();
		botPanel.setStyleName(ValoTheme.PANEL_BORDERLESS);

		botLayout = new VerticalLayout();
		botLayout.setSizeFull();
		botLayout.setMargin(false);
		botLayout.setSpacing(true);

		botTopLayout = new HorizontalLayout();
		botTopLayout.setSizeFull();
		botTopLayout.setMargin(false);
		botTopLayout.setSpacing(true);

		botBotLayout = new HorizontalLayout();
		botBotLayout.setSizeFull();
		botBotLayout.setMargin(false);
		botBotLayout.setSpacing(true);

		headerLayout = new HorizontalLayout();
		headerLayout.setWidth("100%");
		headerLayout.setHeightUndefined();
		headerLayout.setSpacing(false);
		headerLayout.setMargin(true);
		topPanel.setContent(headerLayout);

		Label header = new Label("<font color=\"blue\">Statystyki kategorii</font>", ContentMode.HTML);
		header.setStyleName(ValoTheme.LABEL_H2);
		headerLayout.addComponent(header);

		categoriesTreePanel = new Panel("Kategorie");
		categoriesTreePanel.setIcon(VaadinIcons.LIST);
		categoriesTreePanel.setSizeFull();
		botTopLayout.addComponent(categoriesTreePanel);

		statisticsPanel = new Panel("Statystyki");
		statisticsPanel.setIcon(VaadinIcons.BAR_CHART_H);
		statisticsPanel.setSizeFull();
		botTopLayout.addComponent(statisticsPanel);

		botTopLayout.setExpandRatio(categoriesTreePanel, 2);
		botTopLayout.setExpandRatio(statisticsPanel, 4);

		keyWordsPanel = new Panel("Słowa kluczowe");
		keyWordsPanel.setIcon(VaadinIcons.KEY);
		keyWordsPanel.setSizeFull();
		botBotLayout.addComponent(keyWordsPanel);

		sellersPanel = new Panel("Sprzedawcy");
		sellersPanel.setIcon(VaadinIcons.SHOP);
		sellersPanel.setSizeFull();
		botBotLayout.addComponent(sellersPanel);

		botLayout.addComponents(botTopLayout, botBotLayout);

		botPanel.setContent(botLayout);
		this.addComponents(topPanel, botPanel);
		this.setExpandRatio(botPanel, 1);
	}

	private void buildRaportDates() {
		this.raports = raportRepository.findAllByOrderByTimeDesc();

		ComboBox<Raport> raportBox = new ComboBox<>("Data");
		raportBox.setIcon(VaadinIcons.CALENDAR_CLOCK);
		raportBox.setItems(this.raports);
		raportBox.setItemCaptionGenerator(r -> {
			LocalDateTime date = Instant.ofEpochMilli(r.getTime() * 1000).atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			return date.format(format);
		});

		raportBox.addValueChangeListener(new ValueChangeListener<Raport>() {

			@Override
			public void valueChange(ValueChangeEvent<Raport> event) {
				selectedRaport = event.getValue();
				buildCategoriesTreePanel(null);
			}
		});

		headerLayout.addComponent(raportBox);
		headerLayout.setComponentAlignment(raportBox, Alignment.MIDDLE_RIGHT);

		if (raports.size() > 0)
			raportBox.setSelectedItem(this.raports.get(0));
	}

	private void buildCategoriesTreePanel(Category parent) {
		VerticalLayout categoriesLayout = new VerticalLayout();
		categoriesLayout.setSizeFull();

		categoriesLayout.addComponent(buildCategoriesTree(parent));

		List<CategoryRaport> categoryRaports;
		if (parent != null)
			categoryRaports = categoryRaportRepository.findByRaportAndCategoryParent(this.selectedRaport, parent);
		else
			categoryRaports = categoryRaportRepository.findByRaportAndCategoryParentIsNull(this.selectedRaport);

		if (categoryRaports.size() > 0) {
			ListSelect<CategoryRaport> categoriesListSelect = buildCategoriesListSelect(categoryRaports);
			categoriesLayout.addComponent(categoriesListSelect);
			categoriesLayout.setExpandRatio(categoriesListSelect, 1);
		}

		categoriesTreePanel.setContent(categoriesLayout);
	}

	private com.vaadin.ui.Component buildCategoriesTree(Category parent) {
		HorizontalLayout categoriesTreeLayout = new HorizontalLayout();
		categoriesTreeLayout.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		categoriesTreeLayout.setSpacing(false);
		categoriesTreeLayout.setMargin(false);

		for (Category cat = parent; cat != null; cat = cat.getParent()) {
			Button catButton = new Button();
			catButton.setCaption(cat.getName());
			catButton.setIcon(VaadinIcons.ANGLE_DOUBLE_RIGHT);
			catButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
			final Category innerCat = cat;
			catButton.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					setContent(categoryRaportRepository.findOneByRaportAndCategory(selectedRaport, innerCat));
					buildCategoriesTreePanel(innerCat);
				}
			});
			categoriesTreeLayout.addComponentAsFirst(catButton);
		}

		Button catButton = new Button();
		catButton.setCaption("Allegro");
		catButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		catButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				buildCategoriesTreePanel(null);
			}
		});

		categoriesTreeLayout.addComponentAsFirst(catButton);

		return categoriesTreeLayout;
	}

	private ListSelect<CategoryRaport> buildCategoriesListSelect(List<CategoryRaport> cdraports) {
		ListSelect<CategoryRaport> categoriesListSelect = new ListSelect<CategoryRaport>();
		categoriesListSelect.setSizeFull();
		categoriesListSelect.setItemCaptionGenerator(cdr -> cdr.getCategory().getName());
		categoriesListSelect.setItems(cdraports);
		categoriesListSelect.addValueChangeListener(event -> {
			event.getValue().forEach(cdr -> {
				setContent(cdr);
				buildCategoriesTreePanel(cdr.getCategory());
			});
		});
		return categoriesListSelect;
	}

	private void setContent(CategoryRaport cdr) {
		statisticsPanel.setContent(buildStatiscticsPanel(cdr));
		sellersPanel.setContent(buildSellersPanel(cdr));
		keyWordsPanel.setContent(buildKeyWordsPanel(cdr));
	}

	private com.vaadin.ui.Component buildKeyWordsPanel(CategoryRaport cdr) {
		StatisticsLayout statisticsLayout = new StatisticsLayout();

		return statisticsLayout;
	}

	private com.vaadin.ui.Component buildSellersPanel(CategoryRaport cdr) {
		StatisticsLayout statisticsLayout = new StatisticsLayout();

		return statisticsLayout;
	}

	private com.vaadin.ui.Component buildStatiscticsPanel(CategoryRaport cdr) {

		CategoryRaport pastCdr = getPastCategoryRaport(cdr);

		StatisticsLayout generalStatisticsLayout = new StatisticsLayout();

		if (pastCdr != null) {
			generalStatisticsLayout.addRow("Kupujący", cdr.getBiddersCount(), pastCdr.getBiddersCount());
			generalStatisticsLayout.addRow("Aukcje", cdr.getItemsCount(), pastCdr.getItemsCount());
			generalStatisticsLayout.addRow("Kupione przedmioty", cdr.getBidsCount(), pastCdr.getBidsCount());
			generalStatisticsLayout.addRow("Dostępne przedmioty", cdr.getLeftCount(), pastCdr.getLeftCount());
			generalStatisticsLayout.addRow("Łączna sprzedaż", cdr.getSale(), pastCdr.getSale());
		} else {
			generalStatisticsLayout.addRow("Kupujący", String.valueOf(cdr.getBiddersCount()));
			generalStatisticsLayout.addRow("Aukcje", String.valueOf(cdr.getItemsCount()));
			generalStatisticsLayout.addRow("Kupione przedmioty", String.valueOf(cdr.getBidsCount()));
			generalStatisticsLayout.addRow("Dostępne przedmioty", String.valueOf(cdr.getLeftCount()));
			generalStatisticsLayout.addRow("Łączna sprzedaż", String.valueOf(cdr.getSale()));
		}

		StatisticsLayout newItemsStatisticsLayout = new StatisticsLayout();

		if (pastCdr != null) {
			// newItemsStatisticsLayout.addRow("Kupujący",
			// cdr.getBiddersCount(), pastCdr.getBiddersCount());
			newItemsStatisticsLayout.addRow("Aukcje", cdr.getNewItemsCount(), pastCdr.getNewItemsCount());
			newItemsStatisticsLayout.addRow("Kupione przedmioty", cdr.getNewBidsCount(), pastCdr.getNewBidsCount());
			// newItemsStatisticsLayout.addRow("Dostępne przedmioty",
			// cdr.getLeftCount(), pastCdr.getLeftCount());
			newItemsStatisticsLayout.addRow("Łączna sprzedaż", cdr.getNewSale(), pastCdr.getNewSale());
		} else {
			// newItemsStatisticsLayout.addRow("Kupujący",
			// String.valueOf(cdr.getBiddersCount()));
			newItemsStatisticsLayout.addRow("Aukcje", String.valueOf(cdr.getNewItemsCount()));
			newItemsStatisticsLayout.addRow("Kupione przedmioty", String.valueOf(cdr.getNewBidsCount()));
			// newItemsStatisticsLayout.addRow("Dostępne przedmioty",
			// String.valueOf(cdr.getLeftCount()));
			newItemsStatisticsLayout.addRow("Łączna sprzedaż", String.valueOf(cdr.getNewSale()));
		}

		StatisticsLayout usedItemsStatisticsLayout = new StatisticsLayout();

		if (pastCdr != null) {
			// UsedItemsStatisticsLayout.addRow("Kupujący",
			// cdr.getBiddersCount(), pastCdr.getBiddersCount());
			usedItemsStatisticsLayout.addRow("Aukcje", cdr.getUsedItemsCount(), pastCdr.getUsedItemsCount());
			usedItemsStatisticsLayout.addRow("Kupione przedmioty", cdr.getUsedBidsCount(), pastCdr.getUsedBidsCount());
			// UsedItemsStatisticsLayout.addRow("Dostępne przedmioty",
			// cdr.getLeftCount(), pastCdr.getLeftCount());
			usedItemsStatisticsLayout.addRow("Łączna sprzedaż", cdr.getUsedSale(), pastCdr.getUsedSale());
		} else {
			// UsedItemsStatisticsLayout.addRow("Kupujący",
			// String.valueOf(cdr.getBiddersCount()));
			usedItemsStatisticsLayout.addRow("Aukcje", String.valueOf(cdr.getUsedItemsCount()));
			usedItemsStatisticsLayout.addRow("Kupione przedmioty", String.valueOf(cdr.getUsedBidsCount()));
			// UsedItemsStatisticsLayout.addRow("Dostępne przedmioty",
			// String.valueOf(cdr.getLeftCount()));
			usedItemsStatisticsLayout.addRow("Łączna sprzedaż", String.valueOf(cdr.getUsedSale()));
		}

		StatisticsLayout undefinedItemsStatisticsLayout = new StatisticsLayout();

		if (pastCdr != null) {
			// UsedItemsStatisticsLayout.addRow("Kupujący",
			// cdr.getBiddersCount(), pastCdr.getBiddersCount());
			undefinedItemsStatisticsLayout.addRow("Aukcje", cdr.getUndefinedItemsCount(),
					pastCdr.getUndefinedItemsCount());
			undefinedItemsStatisticsLayout.addRow("Kupione przedmioty", cdr.getUndefinedBidsCount(),
					pastCdr.getUndefinedBidsCount());
			// UsedItemsStatisticsLayout.addRow("Dostępne przedmioty",
			// cdr.getLeftCount(), pastCdr.getLeftCount());
			undefinedItemsStatisticsLayout.addRow("Łączna sprzedaż", cdr.getUndefinedSale(),
					pastCdr.getUndefinedSale());
		} else {
			// UsedItemsStatisticsLayout.addRow("Kupujący",
			// String.valueOf(cdr.getBiddersCount()));
			undefinedItemsStatisticsLayout.addRow("Aukcje", String.valueOf(cdr.getUndefinedItemsCount()));
			undefinedItemsStatisticsLayout.addRow("Kupione przedmioty", String.valueOf(cdr.getUndefinedBidsCount()));
			// UsedItemsStatisticsLayout.addRow("Dostępne przedmioty",
			// String.valueOf(cdr.getLeftCount()));
			undefinedItemsStatisticsLayout.addRow("Łączna sprzedaż", String.valueOf(cdr.getUndefinedSale()));
		}

		Accordion acc = new Accordion();
		acc.setSizeFull();
		acc.setSelectedTab(acc.addTab(generalStatisticsLayout, "Ogólne"));
		acc.addTab(newItemsStatisticsLayout, "Przedmioty nowe");
		acc.addTab(usedItemsStatisticsLayout, "Przedmioty używane");
		acc.addTab(undefinedItemsStatisticsLayout, "Pozostałe");

		return acc;
	}

	private CategoryRaport getPastCategoryRaport(CategoryRaport cdr) {
		int pastRaportIndex = this.raports.indexOf(this.selectedRaport) + 1;
		CategoryRaport pastCdr = null;
		if (pastRaportIndex < this.raports.size()) {
			Raport pastRaport = this.raports.get(pastRaportIndex);
			pastCdr = categoryRaportRepository.findOneByRaportAndCategory(pastRaport, cdr.getCategory());
		}
		return pastCdr;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		buildRaportDates();
	}

}
