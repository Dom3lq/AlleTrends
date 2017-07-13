package components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class StatisticsLayout extends HorizontalLayout {

	public StatisticsLayout() {
		this.setSizeFull();
		this.setMargin(false);
		this.setSpacing(true);
		this.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
	}

	public HorizontalLayout addRow(String caption, String value) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setSizeFull();
		topLayout.setSpacing(true);
		topLayout.setMargin(false);

		Label captionLabel = new Label(caption);
		captionLabel.setStyleName(ValoTheme.LABEL_TINY);
		topLayout.addComponent(captionLabel);
		topLayout.setComponentAlignment(captionLabel, Alignment.MIDDLE_CENTER);

		HorizontalLayout botLayout = new HorizontalLayout();
		botLayout.setSizeFull();
		botLayout.setSpacing(false);
		botLayout.setMargin(false);

		Label valueLabel = new Label(value);
		valueLabel.setStyleName(ValoTheme.LABEL_TINY);
		botLayout.addComponent(valueLabel);

		layout.addComponent(topLayout);
		layout.addComponent(botLayout);

		this.addComponent(layout);

		return botLayout;
	}

	public void addRow(String caption, Double value, Double pastValue) {
		HorizontalLayout layout = addRow(caption, String.valueOf(value));

		Label pastValueLabel = new Label("-", ContentMode.HTML);
		pastValueLabel.setStyleName(ValoTheme.LABEL_TINY);

		if (pastValue != 0) {
			long increase = Math.round(value * 100 / pastValue - 100);
			if (increase < 0) {
				pastValueLabel.setValue(
						VaadinIcons.TRENDIND_DOWN.getHtml() + " <font color=\"red\">" + increase + "%</font>");
			} else if (increase > 0) {
				pastValueLabel.setValue(
						VaadinIcons.TRENDING_UP.getHtml() + " <font color=\"green\">" + increase + "%</font>");
			}

		}

		layout.addComponent(pastValueLabel);
	}

	public void addRow(String caption, Long value, Long pastValue) {
		HorizontalLayout layout = addRow(caption, String.valueOf(value));

		Label pastValueLabel = new Label("-", ContentMode.HTML);
		pastValueLabel.setStyleName(ValoTheme.LABEL_TINY);

		if (pastValue != 0) {
			long increase = (value * 100 / pastValue - 100);
			if (increase < 0) {
				pastValueLabel.setValue(
						VaadinIcons.TRENDIND_DOWN.getHtml() + " <font color=\"red\">" + increase + "%</font>");
			} else if (increase > 0) {
				pastValueLabel.setValue(
						VaadinIcons.TRENDING_UP.getHtml() + " <font color=\"green\">" + increase + "%</font>");
			}

		}

		layout.addComponent(pastValueLabel);
	}

}
