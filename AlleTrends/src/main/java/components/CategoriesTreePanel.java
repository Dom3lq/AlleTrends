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

@SuppressWarnings("serial")
@ViewScope
@SpringComponent
public class CategoriesTreePanel extends Panel {

	VerticalLayout content;
	HorizontalLayout categoriesTreeLayout;
	ListSelect<Category> categoriesListSelect;

	@Autowired
	private CategoryRepository categoryRepository;

	private ContainingCategoriesTreePanel containingCategoriesTreePanel;

	@PostConstruct
	public void init() {
		content = new VerticalLayout();
		content.setSizeFull();

		categoriesTreeLayout = new HorizontalLayout();
		categoriesTreeLayout.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		categoriesTreeLayout.setSpacing(false);
		categoriesTreeLayout.setMargin(false);

		categoriesListSelect = new ListSelect<Category>();
		categoriesListSelect.setSizeFull();
		categoriesListSelect.setItemCaptionGenerator(cdr -> cdr.getName());
		categoriesListSelect.addValueChangeListener(event -> {
			event.getValue().forEach(cat -> {
				selectCategory(cat);
				updateCategoriesTreePanel(cat);
			});
		});

		content.addComponent(categoriesTreeLayout);
		content.addComponent(categoriesListSelect);
		content.setExpandRatio(categoriesListSelect, 1);

		updateCategoriesTreePanel(categoryRepository.findOne(0));

		this.setContent(content);
		this.setIcon(VaadinIcons.LIST);
		this.setCaption("Kategorie");
		this.setSizeFull();
	}

	private void selectCategory(Category cat) {
		containingCategoriesTreePanel.selectCategory(cat);
	}

	public void updateCategoriesTreePanel(Category parent) {
		buildCategoriesTree(parent);

		List<Category> categories;
		categories = categoryRepository.findByParent(parent);

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

		// Button catButton = new Button();
		// catButton.setCaption("Allegro");
		// catButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		// catButton.addClickListener(new Button.ClickListener() {
		// @Override
		// public void buttonClick(ClickEvent event) {
		// updateCategoriesTreePanel(null);
		// }
		// });
		//
		// categoriesTreeLayout.addComponentAsFirst(catButton);

	}

	public ContainingCategoriesTreePanel getContainingCategory() {
		return containingCategoriesTreePanel;
	}

	public void setContainingCategory(ContainingCategoriesTreePanel containingCategory) {
		this.containingCategoriesTreePanel = containingCategory;
	}

}
