package components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class TopPanelItem extends VerticalLayout {

	private Label nameLabel, additionalInfoLabel, valueLabel, changeLabel;

	public TopPanelItem() {
		super();
		nameLabel = new Label();
		nameLabel.setStyleName(ValoTheme.LABEL_H4);
		nameLabel.setVisible(false);

		additionalInfoLabel = new Label();
		additionalInfoLabel.setStyleName(ValoTheme.LABEL_LIGHT);
		additionalInfoLabel.setVisible(false);

		valueLabel = new Label();
		valueLabel.setStyleName(ValoTheme.LABEL_BOLD);
		valueLabel.setVisible(false);

		changeLabel = new Label();
		changeLabel.setStyleName(ValoTheme.LABEL_COLORED);
		changeLabel.setVisible(false);
		changeLabel.setContentMode(ContentMode.HTML);

		this.addComponents(nameLabel, additionalInfoLabel, valueLabel, changeLabel);
		this.setSizeUndefined();
		this.setStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		this.setMargin(true);
	}

	public TopPanelItem setName(String name) {
		nameLabel.setValue(name);
		nameLabel.setVisible(true);

		return this;
	}

	public TopPanelItem setAdditionalInfo(String nameAdditionalInfo) {
		additionalInfoLabel.setValue(nameAdditionalInfo);
		additionalInfoLabel.setVisible(true);

		return this;
	}

	public TopPanelItem setValue(long value, String info) {
		valueLabel.setValue(String.format("%,8d", value) + " " + info);
		valueLabel.setVisible(true);

		return this;
	}

	public TopPanelItem setValue(long value) {
		valueLabel.setValue(String.format("%,8d", value));
		valueLabel.setVisible(true);

		return this;
	}

	public TopPanelItem setChange(long change) {
		if (change > 0) {
			changeLabel.setValue(
					VaadinIcons.TRENDING_UP.getHtml() + " <font color=\"green\">+" + String.valueOf(change) + "%</font>");
		} else if (change < 0) {
			changeLabel.setValue(
					VaadinIcons.TRENDIND_DOWN.getHtml() + " <font color=\"red\">" + String.valueOf(change)
							+ "%</font>");

		} else {
			changeLabel.setValue(VaadinIcons.ARROWS_LONG_RIGHT.getHtml() + " <font color=\"yellow\">"
					+ String.valueOf(change) + "%</font>");
		}
		changeLabel.setVisible(true);

		return this;
	}

	public String getName() {
		return nameLabel.getValue();
	}

	public String getAdditionalInfo() {
		return additionalInfoLabel.getValue();
	}

	public String getValue() {
		return valueLabel.getValue();
	}

	public String getChange() {
		return changeLabel.getValue();
	}
}
