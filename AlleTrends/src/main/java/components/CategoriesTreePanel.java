package components;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import pojos.Category;
import repositories.CategoryRepository;

@SpringComponent
@SuppressWarnings("serial")
@ViewScope
public class CategoriesTreePanel extends Panel {

	VerticalLayout categoriesLayout;
	HorizontalLayout categoriesTreeLayout;
	ListSelect<Category> categoriesListSelect;

	@Autowired
	private CategoryRepository categoryRepository;

	private ContainingCategory containingCategory;

	@PostConstruct
	public void init() {
		categoriesLayout = new VerticalLayout();
		categoriesLayout.setSizeFull();

		categoriesTreeLayout = new HorizontalLayout();
		categoriesTreeLayout.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		categoriesTreeLayout.setSpacing(false);
		categoriesTreeLayout.setMargin(false);
		categoriesTreeLayout.setWidth("100%");
		categoriesTreeLayout.setHeightUndefined();
		categoriesLayout.addComponent(categoriesTreeLayout);

		categoriesListSelect = new ListSelect<Category>();
		categoriesListSelect.setSizeFull();
		categoriesListSelect.setItemCaptionGenerator(cdr -> cdr.getName());
		categoriesListSelect.addValueChangeListener(event -> {
			event.getValue().forEach(cat -> {
				selectCategory(cat);
				updateCategoriesTreePanel(cat);
			});
		});

		categoriesLayout.addComponent(categoriesListSelect);
		categoriesLayout.setExpandRatio(categoriesListSelect, 1);

		updateCategoriesTreePanel(null);

		this.setContent(categoriesLayout);
	}

	private void selectCategory(Category cat) {
		containingCategory.selectCategory(cat);
	}

	private void updateCategoriesTreePanel(Category parent) {
		buildCategoriesTree(parent);

		List<Category> categories;
		if (parent != null)
			categories = categoryRepository.findByParent(parent);
		else
			categories = categoryRepository.findByParentIsNull();

		if (categories.size() > 0) {
			categoriesListSelect.setItems(categories);
		}
	}

	private void buildCategoriesTree(Category parent) {
		categoriesTreeLayout.removeAllComponents();

		for (Category cat = parent; cat != null; cat = cat.getParent()) {
			Button catButton = new Button();
			catButton.setCaption(cat.getName());
			catButton.setIcon(VaadinIcons.ANGLE_DOUBLE_RIGHT);
			catButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
			final Category innerCat = cat;
			catButton.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					selectCategory(innerCat);
					updateCategoriesTreePanel(innerCat);
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
				updateCategoriesTreePanel(null);
			}
		});

		categoriesTreeLayout.addComponentAsFirst(catButton);

	}

	public ContainingCategory getContainingCategory() {
		return containingCategory;
	}

	public void setContainingCategory(ContainingCategory containingCategory) {
		this.containingCategory = containingCategory;
	}

}
