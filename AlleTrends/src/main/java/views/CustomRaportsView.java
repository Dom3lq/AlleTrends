package views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

import components.CustomRaportCreator;

@SuppressWarnings("serial")
@SpringView(name = CustomRaportsView.NAME)
public class CustomRaportsView extends VerticalLayout implements View {

	public static final String NAME = "custom-raport-view";

	@Autowired
	CustomRaportCreator customRaportCreator;
	
	@PostConstruct
	public void init() {
		this.setSizeFull();
		this.setMargin(true);
		this.setSpacing(true);
		
		this.addComponent(customRaportCreator);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
