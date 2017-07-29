package components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class StatisticsCell extends VerticalLayout {

	Label valueLabel, partLabel, valueGrowthLabel, partGrowthLabel;
	HorizontalLayout topLayout, botLayout;

	public StatisticsCell() {
		valueLabel = new Label();
		partLabel = new Label();
		valueGrowthLabel = new Label();
		partGrowthLabel = new Label();

		valueLabel.setSizeUndefined();
		partLabel.setSizeUndefined();
		valueGrowthLabel.setSizeUndefined();
		partGrowthLabel.setSizeUndefined();

		valueLabel.setStyleName(ValoTheme.LABEL_LIGHT);
		valueGrowthLabel.setStyleName(ValoTheme.LABEL_TINY);
		partLabel.setStyleName(ValoTheme.LABEL_COLORED);
		partGrowthLabel.setStyleName(ValoTheme.LABEL_TINY);

		topLayout = new HorizontalLayout();
		topLayout.setSizeUndefined();
		topLayout.addComponents(valueLabel, valueGrowthLabel);
		topLayout.setSpacing(true);

		botLayout = new HorizontalLayout();
		botLayout.setSizeUndefined();
		botLayout.addComponents(partLabel);

		this.setSizeFull();
		this.setMargin(false);
		this.addComponents(topLayout, botLayout);
		this.setComponentAlignment(topLayout, Alignment.TOP_CENTER);
		this.setComponentAlignment(botLayout, Alignment.BOTTOM_CENTER);
		this.setStyleName(ValoTheme.LAYOUT_CARD);
	}

	public void setContent(long value, long allValue, long pastValue, long pastAllValue) {
		setContent(value, allValue);

		if (pastValue != 0) {
			long valueGrowth = (value * 100 / pastValue) - 100;

			valueGrowthLabel.setVisible(true);
			valueGrowthLabel.setContentMode(ContentMode.HTML);
			if (valueGrowth < 0) {
				valueGrowthLabel.setValue(VaadinIcons.TRENDIND_DOWN.getHtml() + " <font color=\"red\">"
						+ String.valueOf(valueGrowth) + "%</font>");
			} else if (valueGrowth > 0) {
				valueGrowthLabel.setValue(VaadinIcons.TRENDING_UP.getHtml() + " <font color=\"green\">+"
						+ String.valueOf(valueGrowth) + "%</font>");
			} else {
				valueGrowthLabel.setValue(VaadinIcons.ARROWS_LONG_RIGHT.getHtml() + " <font color=\"yellow\">"
						+ String.valueOf(valueGrowth) + "%</font>");
			}
		}

		if (pastAllValue != 0 && allValue != 0) {
			long pastPart = (pastValue * 100 / pastAllValue);
			long part = (value * 100 / allValue);
			if (pastPart != 0) {
				long partGrowth = (part / pastPart) - 100;

				partGrowthLabel.setVisible(true);
				partGrowthLabel.setContentMode(ContentMode.HTML);
				if (partGrowth < 0) {
					partGrowthLabel.setValue(VaadinIcons.TRENDIND_DOWN.getHtml() + " <font color=\"red\">"
							+ String.valueOf(partGrowth) + "%</font>");
				} else if (partGrowth > 0) {
					partGrowthLabel.setValue(VaadinIcons.TRENDING_UP.getHtml() + " <font color=\"green\">+"
							+ String.valueOf(partGrowth) + "%</font>");
				} else {
					partGrowthLabel.setValue(VaadinIcons.ARROWS_LONG_RIGHT.getHtml() + " <font color=\"yellow\">"
							+ String.valueOf(partGrowth) + "%</font>");
				}
			}
		}
	}

	public void setContent(long value, long allValue) {
		valueLabel.setValue(String.format("%,8d", value));

		if (allValue != 0) {
			long part = (value * 100 / allValue);
			partLabel.setValue(part + "%");
		} else
			partLabel.setValue(" ");

		valueGrowthLabel.setVisible(false);
		partGrowthLabel.setVisible(true);
	}

}
