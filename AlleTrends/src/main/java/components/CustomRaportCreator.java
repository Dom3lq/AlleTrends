package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import alle.trends.ApiConnector;
import pl.allegro.webapi.service_php.FilterOptionsType;
import pl.allegro.webapi.service_php.FilterValueType;
import pl.allegro.webapi.service_php.FiltersListType;
import pl.allegro.webapi.service_php.RangeValueType;
import pojos.Filter;

@SpringComponent
@UIScope
@SuppressWarnings("serial")
public class CustomRaportCreator extends Panel {

	ApiConnector ac;
	List<Filter> filters;
	List<Filter> availableFilters;
	Grid<Filter> filtersG;
	Grid<Filter> availableFiltersG;
	ListDataProvider<Filter> filtersDP;
	ListDataProvider<Filter> availableFiltersDP;
	Panel filterConfigurationPanel;

	@PostConstruct
	public void init() {
		this.setSizeFull();
		ac = new ApiConnector();
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();
		content.setMargin(true);
		content.setSpacing(true);
		this.setContent(content);

		filters = new ArrayList<Filter>();
		availableFilters = new ArrayList<Filter>();

		filtersDP = DataProvider.ofCollection(filters);
		availableFiltersDP = DataProvider.ofCollection(availableFilters);

		availableFiltersG = new Grid<Filter>();
		availableFiltersG.setSizeFull();
		availableFiltersG.setDataProvider(availableFiltersDP);
		availableFiltersG.addColumn(filter -> filter.getFilterListType().getFilterName()).setCaption("Dostępny Filtr");
		availableFiltersG.setSelectionMode(SelectionMode.SINGLE);
		availableFiltersG.addItemClickListener(
				event -> filterConfigurationPanel.setContent(buildFilterConfigurationPanel(event.getItem())));
		availableFiltersG.setStyleGenerator(
				cellRef -> cellRef.isMustBe() ? "highlightgreen" : cellRef.isMayBe() ? "highlightyellow" : null);
		content.addComponent(availableFiltersG);

		filterConfigurationPanel = new Panel();
		filterConfigurationPanel.setSizeFull();
		content.addComponent(filterConfigurationPanel);

		filtersG = new Grid<Filter>();
		filtersG.setSizeFull();
		filtersG.setDataProvider(filtersDP);
		filtersG.addColumn(filter -> filter.getFilterListType().getFilterName()).setCaption("Filtr");
		filtersG.setSelectionMode(SelectionMode.NONE);
		filtersG.setStyleGenerator(cellRef -> cellRef.isMustntBe() ? "highlightred" : null);
		content.addComponent(filtersG);

		refreshAvailableFilters();
		availableFiltersDP.refreshAll();
	}

	private VerticalLayout buildFilterConfigurationPanel(Filter filter) {
		filter.getFilterOptionsType().setFilterId(filter.getFilterListType().getFilterId());

		VerticalLayout content = new VerticalLayout();
		content.setSpacing(true);
		content.setMargin(true);
		content.setSizeFull();

		VerticalLayout valueLayout = new VerticalLayout();
		valueLayout.setWidth("100%");
		valueLayout.setHeightUndefined();
		valueLayout.setSpacing(true);
		valueLayout.setMargin(true);
		content.addComponent(valueLayout);

		String[] filterValueIds;
		switch (filter.getFilterListType().getFilterControlType()) {
		case "combobox":
			RadioButtonGroup<FilterValueType> single = new RadioButtonGroup<>(
					filter.getFilterListType().getFilterName());
			single.setSizeUndefined();
			single.setItems(filter.getFilterListType().getFilterValues());
			single.setItemCaptionGenerator(FilterValueType::getFilterValueName);
			valueLayout.addComponent(single);
			valueLayout.setComponentAlignment(single, Alignment.MIDDLE_CENTER);

			single.addSelectionListener(event -> {
				if (event.getSelectedItem().isPresent())
					filter.getFilterOptionsType()
							.setFilterValueId(new String[] { event.getSelectedItem().get().getFilterValueId() });
			});

			break;
		case "checkbox":
			CheckBoxGroup<FilterValueType> multi = new CheckBoxGroup<>(filter.getFilterListType().getFilterName());
			multi.setSizeUndefined();
			multi.setItems(filter.getFilterListType().getFilterValues());
			multi.setItemCaptionGenerator(FilterValueType::getFilterValueName);
			valueLayout.addComponent(multi);
			valueLayout.setComponentAlignment(multi, Alignment.MIDDLE_CENTER);

			multi.addSelectionListener(event -> {
				filter.getFilterOptionsType()
						.setFilterValueId(event.getAllSelectedItems().stream().map(fvt -> fvt.getFilterValueId())
								.collect(Collectors.toList())
								.toArray(new String[filter.getFilterListType().getFilterValues().length]));
			});

			break;
		case "textbox":

			TextField tf1 = new TextField(filter.getFilterListType().getFilterName());
			tf1.setSizeUndefined();
			valueLayout.addComponent(tf1);

			if (filter.getFilterListType().isFilterIsRange()) {
				tf1.setCaption(filter.getFilterListType().getFilterName() + " pomiędzy");

				TextField tf2 = new TextField();
				tf2.setSizeUndefined();
				valueLayout.addComponent(tf2);

				filter.getFilterOptionsType().setFilterValueRange(new RangeValueType());

				tf1.setValueChangeMode(ValueChangeMode.EAGER);
				tf1.addValueChangeListener(event -> {
					filter.getFilterOptionsType().getFilterValueRange().setRangeValueMin(event.getValue());
				});

				tf2.setValueChangeMode(ValueChangeMode.EAGER);
				tf2.addValueChangeListener(event -> {
					filter.getFilterOptionsType().getFilterValueRange().setRangeValueMax(event.getValue());
				});
			} else {
				filterValueIds = new String[1];
				filter.getFilterOptionsType().setFilterValueId(filterValueIds);

				tf1.setValueChangeMode(ValueChangeMode.EAGER);
				tf1.addValueChangeListener(event -> {
					filterValueIds[0] = event.getValue();
				});

				tf1.setCaption(filter.getFilterListType().getFilterName());
				valueLayout.setComponentAlignment(tf1, Alignment.MIDDLE_CENTER);
			}

			break;
		}

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setWidth("100%");
		buttonLayout.setHeightUndefined();
		buttonLayout.setSpacing(true);
		buttonLayout.setMargin(true);
		content.addComponent(buttonLayout);

		Button removeB = new Button("Dodaj");
		removeB.setSizeUndefined();
		removeB.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		removeB.setIcon(VaadinIcons.PLUS_CIRCLE);
		removeB.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (!availableFilters.contains(filter))
					availableFilters.add(filter);
				if (availableFilters.contains(filter))
					filters.remove(filter);

				refreshAvailableFilters();

				filtersDP.refreshAll();
				availableFiltersDP.refreshAll();
			}

		});
		buttonLayout.addComponent(removeB);
		buttonLayout.setComponentAlignment(removeB, Alignment.MIDDLE_CENTER);

		Button addB = new Button("Dodaj");
		addB.setSizeUndefined();
		addB.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		addB.setIcon(VaadinIcons.PLUS_CIRCLE);
		addB.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (!filters.contains(filter))
					filters.add(filter);
				if (availableFilters.contains(filter))
					availableFilters.remove(filter);

				refreshAvailableFilters();

				filtersDP.refreshAll();
				availableFiltersDP.refreshAll();
			}

		});

		buttonLayout.addComponent(addB);
		buttonLayout.setComponentAlignment(addB, Alignment.MIDDLE_CENTER);

		VerticalLayout expandingGap = new VerticalLayout();
		expandingGap.setSizeFull();
		content.addComponent(expandingGap);
		content.setExpandRatio(expandingGap, 1);

		return content;
	}

	private void refreshAvailableFilters() {
		if (checkFiltersRelations()) {
			List<FiltersListType> filtersAvailable = ac.getFiltersAvailable(
					filters.stream().map(f -> f.getFilterOptionsType()).collect(Collectors.toList()));
			if (filtersAvailable != null) {
				List<Filter> filters = filtersAvailable.stream().map(fa -> {
					Filter filter = new Filter();
					filter.setFilterListType(fa);
					filter.setFilterOptionsType(new FilterOptionsType());
					return filter;
				}).collect(Collectors.toList());

				availableFilters.clear();
				availableFilters.addAll(filters);

				availableFilters.forEach(filter -> setRelations(filter));
			}
		}
	}

	private void setRelations(Filter filter) {
		if (filter.getFilterListType().getFilterRelations() != null) {
			if (filter.getFilterListType().getFilterRelations().getRelationAnd() != null)
				filter.setMustBeFilters(
						Arrays.asList(filter.getFilterListType().getFilterRelations().getRelationAnd()).stream()
								.map(s -> availableFilters.stream()
										.filter(af -> af.getFilterListType().getFilterId().equals(s)).findFirst())
								.filter(f -> f.isPresent()).map(f -> f.get()).collect(Collectors.toList()));

			if (filter.getFilterListType().getFilterRelations().getRelationOr() != null)
				filter.setMustBeAtLeastOneFilter(
						Arrays.asList(filter.getFilterListType().getFilterRelations().getRelationOr()).stream()
								.map(s -> availableFilters.stream()
										.filter(af -> af.getFilterListType().getFilterId().equals(s)).findFirst())
								.filter(f -> f.isPresent()).map(f -> f.get()).collect(Collectors.toList()));

			if (filter.getFilterListType().getFilterRelations().getRelationExclude() != null)
				filter.setMustntBeFilters(
						Arrays.asList(filter.getFilterListType().getFilterRelations().getRelationExclude()).stream()
								.map(s -> availableFilters.stream()
										.filter(af -> af.getFilterListType().getFilterId().equals(s)).findFirst())
								.filter(f -> f.isPresent()).map(f -> f.get()).collect(Collectors.toList()));
		}
	}

	private boolean checkFiltersRelations() {
		boolean check = true;
		for (Filter f : filters) {
			f.setMustBe(false);
			f.setMustntBe(false);
			f.setMayBe(false);

			if (f.getMustBeFilters().size() > 0)
				if (!filters.containsAll(f.getMustBeFilters())) {
					for (Filter m : f.getMustBeFilters())
						m.setMustBe(true);

					check = false;
				}

			if (f.getMustBeAtLeastOneFilter().size() > 0)
				if (f.getMustBeAtLeastOneFilter().stream().filter(o -> filters.contains(o)).count() < 1) {
					for (Filter m : f.getMustBeAtLeastOneFilter())
						m.setMayBe(true);

					check = false;
				}

			if (f.getMustntBeFilters().size() > 0)
				if (f.getMustntBeFilters().stream().filter(n -> filters.contains(n)).count() > 0) {
					for (Filter m : f.getMustntBeFilters())
						m.setMustntBe(true);

					check = false;
				}
		}
		return check;
	}
}
