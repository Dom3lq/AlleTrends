package components;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class TopListPanel<T> extends Panel {

	VerticalLayout content;
	HorizontalLayout listLayout;
	Panel listPanel;
	Label headLabel;

	private Function<? super T, String> nameProvider;
	private Function<? super T, String> additionalInfoProvider;
	private Function<? super T, Long> valueProvider;
	private Function<? super T, Long> changeProvider;
	private ItemsProvider<T> itemsProvider;
	private Consumer<? super T> itemClickListener;

	public TopListPanel(String name) {
		headLabel = new Label();
		headLabel.setValue(name);
		headLabel.setStyleName(ValoTheme.LABEL_H3);

		listLayout = new HorizontalLayout();
		listLayout.setSizeUndefined();

		listPanel = new Panel();
		listPanel.setHeightUndefined();
		listPanel.setWidth("100%");
		listPanel.setContent(listLayout);
		listPanel.setStyleName(ValoTheme.PANEL_BORDERLESS);

		content = new VerticalLayout();
		content.setHeightUndefined();
		content.setWidth("100%");
		content.addComponents(headLabel, listPanel);

		this.setContent(content);
		this.setWidth("100%");
		this.setHeightUndefined();
	}

	public void refreshItems() {
		List<T> raports = itemsProvider.getItems();
		listLayout.removeAllComponents();
		raports.stream().map(s -> {
			TopPanelItem item = new TopPanelItem();

			if (nameProvider != null)
				item.setName(nameProvider.apply(s));

			if (additionalInfoProvider != null)
				item.setAdditionalInfo(additionalInfoProvider.apply(s));

			if (valueProvider != null)
				item.setValue(valueProvider.apply(s));

			if (changeProvider != null)
				item.setChange(changeProvider.apply(s));

			if (itemClickListener != null)
				item.addLayoutClickListener(new LayoutClickListener() {

					@Override
					public void layoutClick(LayoutClickEvent event) {
						itemClickListener.accept(s);
					}

				});

			return item;
		}).forEach(i -> listLayout.addComponent(i));

	}

	public TopListPanel<T> setNameProvider(final Function<? super T, String> nameProvider) {
		this.nameProvider = nameProvider;
		return this;
	}

	public TopListPanel<T> setAdditionalInfoProvider(final Function<? super T, String> additionalInfoProvider) {
		this.additionalInfoProvider = additionalInfoProvider;
		return this;
	}

	public TopListPanel<T> setValueProvider(final Function<? super T, Long> valueProvider) {
		this.valueProvider = valueProvider;
		return this;
	}

	public TopListPanel<T> setChangeProvider(final Function<? super T, Long> changeProvider) {
		this.changeProvider = changeProvider;
		return this;
	}

	public TopListPanel<T> setItemsProvider(ItemsProvider<T> itemsProvider) {
		this.itemsProvider = itemsProvider;
		return this;
	}

	public TopListPanel<T> setItemClickListener(final Consumer<? super T> itemClickListener) {
		this.itemClickListener = itemClickListener;
		return this;
	}
}
