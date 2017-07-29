package views;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import components.CategoriesTreePanel;
import components.ContainingCategoriesTreePanel;
import components.StatisticsPanel;

import pojos.Category;
import pojos.CategoryRaport;
import pojos.Raport;
import pojos.Statistics;
import repositories.CategoryRaportRepository;
import repositories.CategoryRepository;
import repositories.RaportRepository;

@SuppressWarnings("serial")
@SpringView(name = CategoriesView.NAME)
public class CategoriesView extends VerticalLayout implements View, ContainingCategoriesTreePanel {

	public final static String NAME = "categories-view";

	@Autowired
	RaportRepository raportRepository;

	@Autowired
	CategoryRaportRepository categoryRaportRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoriesTreePanel categoriesTreePanel;

	List<Raport> raports;
	Raport selectedRaport;

	StatisticsPanel statisticsPanel;
	Panel topPanel, botPanel, keyWordsPanel, sellersPanel;
	HorizontalLayout botLayout, headerLayout;

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

		categoriesTreePanel.setContainingCategory(this);

		botLayout = new HorizontalLayout();
		botLayout.setSizeFull();
		botLayout.setMargin(true);
		botLayout.setSpacing(true);
		botPanel.setContent(botLayout);

		statisticsPanel = new StatisticsPanel();

		botLayout.addComponent(categoriesTreePanel);
		botLayout.addComponent(statisticsPanel);
		botLayout.setExpandRatio(categoriesTreePanel, 2);
		botLayout.setExpandRatio(statisticsPanel, 6);
		botLayout.setComponentAlignment(statisticsPanel, Alignment.TOP_CENTER);

		headerLayout = new HorizontalLayout();
		headerLayout.setWidth("100%");
		headerLayout.setHeightUndefined();
		headerLayout.setSpacing(false);
		headerLayout.setMargin(true);
		topPanel.setContent(headerLayout);

		Label header = new Label("<font color=\"blue\">Statystyki kategorii</font>", ContentMode.HTML);
		header.setStyleName(ValoTheme.LABEL_H2);
		headerLayout.addComponent(header);

		this.addComponents(topPanel, botPanel);
		this.setExpandRatio(botPanel, 1);

		buildRaportInfo();
		// buildRaportSelector();
	}

	private void buildRaportInfo() {
		this.raports = raportRepository.findByIsCompleteTrueOrderByTimeDesc();
		selectedRaport = this.raports.get(0);

		Label raportInfoLabel = new Label();
		raportInfoLabel.setStyleName(ValoTheme.LABEL_LIGHT);
		raportInfoLabel.setContentMode(ContentMode.HTML);
		LocalDateTime date = Instant.ofEpochMilli(selectedRaport.getTime() * 1000).atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		LocalDateTime fromDate = date.minusMonths(2);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		raportInfoLabel
				.setValue(VaadinIcons.CALENDAR.getHtml() + " " + fromDate.format(format) + " - " + date.format(format));
		raportInfoLabel.setSizeUndefined();

		headerLayout.addComponent(raportInfoLabel);
		headerLayout.setComponentAlignment(raportInfoLabel, Alignment.MIDDLE_RIGHT);
	}

	@Override
	public void selectCategory(Category cat) {
		setContent(categoryRaportRepository.findOneByRaportAndCategory(this.selectedRaport, cat));
	}

	public void setContent(CategoryRaport cdr) {
		refreshStatiscticsAccordionContent(cdr);
	}

	private void refreshStatiscticsAccordionContent(CategoryRaport statistics) {
		Statistics pastStatistics = getPastCategoryRaport(statistics);
		if (pastStatistics != null)
			statisticsPanel.setContent(statistics.getCategory().getName(), statistics, pastStatistics);
		else
			statisticsPanel.setContent(statistics.getCategory().getName(), statistics);
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
		String param = event.getParameters();
		if (!param.isEmpty()) {
			Category category = categoryRepository.findOne(Integer.valueOf(param));
			selectCategory(category);
			categoriesTreePanel.updateCategoriesTreePanel(category);
		}
	}
}
