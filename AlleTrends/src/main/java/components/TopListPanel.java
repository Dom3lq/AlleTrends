package components;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class TopListPanel<T> extends Panel {

	private VerticalLayout content;
	private HorizontalLayout listLayout;
	private Panel listPanel;
	private Label headLabel;
	private Function<? super T, String> nameProvider;
	private Function<? super T, String> additionalInfoProvider;
	private Function<? super T, Long> valueProvider;
	private Function<? super T, String> valueInfoProvider;
	private Function<? super T, Long> changeProvider;
	private ItemsProvider<? extends T> itemsProvider;
	private Consumer<? super T> itemClickListener;

	private UI ui;

	public TopListPanel(String name) {
		ui = UI.getCurrent();

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

	public TopListPanel(Builder<T> builder) {
		this(builder.name);
		this.nameProvider = builder.nameProvider;
		this.additionalInfoProvider = builder.additionalInfoProvider;
		this.valueProvider = builder.valueProvider;
		this.valueInfoProvider = builder.valueInfoProvider;
		this.changeProvider = builder.changeProvider;
		this.itemsProvider = builder.itemsProvider;
		this.itemClickListener = builder.itemClickListener;

	}

	public void refreshItems() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					List<? extends T> raports = itemsProvider.getItems();

					ui.access(new Runnable() {

						@Override
						public void run() {

							listLayout.removeAllComponents();
							raports.stream().map(s -> {
								TopPanelItem item = new TopPanelItem();

								if (nameProvider != null)
									item.setName(nameProvider.apply(s));

								if (additionalInfoProvider != null)
									item.setAdditionalInfo(additionalInfoProvider.apply(s));

								if (valueProvider != null)
									if (valueInfoProvider != null)
										item.setValue(valueProvider.apply(s), valueInfoProvider.apply(s));
									else
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
							content.getParent().setVisible(true);

						}

					});

				} catch (Exception e) {

				}

			}

		}).start();

	}

	public static class Builder<T> {
		private Function<? super T, String> nameProvider;
		private Function<? super T, String> additionalInfoProvider;
		private Function<? super T, Long> valueProvider;
		private Function<? super T, String> valueInfoProvider;
		private Function<? super T, Long> changeProvider;
		private ItemsProvider<T> itemsProvider;
		private Consumer<? super T> itemClickListener;
		private String name;

		public Builder(final String name) {
			this.name = name;
		}

		public Builder<T> setNameProvider(final Function<? super T, String> nameProvider) {
			this.nameProvider = nameProvider;
			return this;
		}

		public Builder<T> setAdditionalInfoProvider(final Function<? super T, String> additionalInfoProvider) {
			this.additionalInfoProvider = additionalInfoProvider;
			return this;
		}

		public Builder<T> setValueProvider(final Function<? super T, Long> valueProvider) {
			this.valueProvider = valueProvider;
			return this;
		}

		public Builder<T> setChangeProvider(final Function<? super T, Long> changeProvider) {
			this.changeProvider = changeProvider;
			return this;
		}

		public Builder<T> setItemsProvider(ItemsProvider<T> itemsProvider) {
			this.itemsProvider = itemsProvider;
			return this;
		}

		public Builder<T> setItemClickListener(final Consumer<? super T> itemClickListener) {
			this.itemClickListener = itemClickListener;
			return this;
		}

		public Builder<T> setValueInfoProvider(Function<? super T, String> valueInfoProvider) {
			this.valueInfoProvider = valueInfoProvider;
			return this;
		}

		public TopListPanel<T> get() {
			return new TopListPanel<T>(this);
		}
	}
}
