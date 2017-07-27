package components;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import pojos.Statistics;

@SuppressWarnings("serial")
@ViewScope
@SpringComponent
public class StatisticsPanel extends Panel {

	StatisticsRow allRow, newRow, usedRow, undefinedRow, freeDeliveryRow, allegroStandardRow;
	HorizontalLayout content;
	VerticalLayout informationLayout;
	Label headLabel;

	@PostConstruct
	public void init() {
		informationLayout = new VerticalLayout();
		informationLayout.setHeight("100%");
		informationLayout.setWidthUndefined();
		informationLayout.setSpacing(true);
		informationLayout.setMargin(false);

		headLabel = new Label();
		headLabel.setStyleName(ValoTheme.LABEL_H3);
		headLabel.setContentMode(ContentMode.HTML);
		informationLayout.addComponent(headLabel);
		Arrays.asList("Aukcje", "Dostępne przedmioty", "Kupujący", "Kupione przedmioty", "Sprzedaż").stream().map(

				s -> {

					Label l = new Label(s);
					l.setStyleName(ValoTheme.LABEL_BOLD);
					return l;
				}).forEach(l -> informationLayout.addComponent(l));

		allRow = new StatisticsRow("Wszystkie");
		newRow = new StatisticsRow("Nowe");
		usedRow = new StatisticsRow("Używane");
		undefinedRow = new StatisticsRow("Pozostałe");
		freeDeliveryRow = new StatisticsRow("Darmowa dostawa");
		allegroStandardRow = new StatisticsRow("Standard Allegro");

		content = new HorizontalLayout();
		content.setSizeUndefined();
		content.setSpacing(true);
		content.setMargin(true);
		content.addComponents(informationLayout, allRow, newRow, usedRow, undefinedRow, freeDeliveryRow,
				allegroStandardRow);

		this.setContent(content);
		this.setCaption("Statystyki");
		this.setIcon(VaadinIcons.BAR_CHART_H);
		this.setSizeFull();
		this.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	}

	public void setContent(String name, Statistics statistics) {
		headLabel.setValue("<font color=\"blue\">" + name + "</font>");

		allRow.setContent(statistics.getAllStatistics(), statistics.getAllStatistics());
		newRow.setContent(statistics.getNewStatistics(), statistics.getAllStatistics());
		usedRow.setContent(statistics.getUsedStatistics(), statistics.getAllStatistics());
		undefinedRow.setContent(statistics.getUndefinedStatistics(), statistics.getAllStatistics());
		freeDeliveryRow.setContent(statistics.getFreeDeliveryStatistics(), statistics.getAllStatistics());
		allegroStandardRow.setContent(statistics.getAllegroStandardStatistics(), statistics.getAllStatistics());
	}

	public void setContent(String name, Statistics statistics, Statistics pastStatistics) {
		headLabel.setValue("<font color=\"blue\">" + name + "</font>");

		allRow.setContent(statistics.getAllStatistics(), statistics.getAllStatistics(),
				pastStatistics.getAllStatistics(), pastStatistics.getAllStatistics());
		newRow.setContent(statistics.getNewStatistics(), statistics.getAllStatistics(),
				pastStatistics.getNewStatistics(), pastStatistics.getAllStatistics());
		usedRow.setContent(statistics.getUsedStatistics(), statistics.getAllStatistics(),
				pastStatistics.getUsedStatistics(), pastStatistics.getAllStatistics());
		undefinedRow.setContent(statistics.getUndefinedStatistics(), statistics.getAllStatistics(),
				pastStatistics.getUndefinedStatistics(), pastStatistics.getAllStatistics());
		freeDeliveryRow.setContent(statistics.getFreeDeliveryStatistics(), statistics.getAllStatistics(),
				pastStatistics.getFreeDeliveryStatistics(), pastStatistics.getAllStatistics());
		allegroStandardRow.setContent(statistics.getAllegroStandardStatistics(), statistics.getAllStatistics(),
				pastStatistics.getAllegroStandardStatistics(), pastStatistics.getAllStatistics());
	}
}
