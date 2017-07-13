package views;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

@SpringView
@SuppressWarnings("serial")
public class XkomConverterView extends VerticalLayout implements View {

	public final static String NAME = "xkom-converter-view";
	TextField linkTB;
	Document auctionPage;
	private TextField name;
	private TextField procesor;
	private TextField ram;
	private TextField maxRam;
	private TextField hardDrive;
	private TextField internalOpticalDrives;
	private TextField ramSlotsOgolem;
	private TextField ramSlotsWolne;
	private TextField screenType;
	private TextField screenPrzekatna;
	private TextField screenResolution;
	private TextField graphicCard;
	private TextField graphicCardRam;
	private TextField sound;
	private TextField camera;
	private TextField connection;
	private TextField inOutTypes;
	private TextField battery;
	private TextField system;
	private TextField height;
	private TextField width;
	private TextField deepth;
	private TextField weight;
	private TextField additionalInfo;
	private TextField accesories;
	private TextField gwarancy;
	private Button button;
	private TextField priceField;

	@PostConstruct
	public void init() {
		this.setSizeFull();
		this.setSpacing(true);
		this.setMargin(true);

		linkTB = new TextField("Link");
		linkTB.setWidth("100%");
		this.addComponent(linkTB);

		button = new Button("Pobierz");
		this.addComponent(button);

		Panel contentPanel = buildContent();
		this.addComponent(contentPanel);
		this.setExpandRatio(contentPanel, 1);
	}

	private Panel buildContent() {
		Panel panel = new Panel();
		panel.setSizeFull();

		FormLayout content = new FormLayout();
		content.setMargin(true);
		content.setSpacing(true);
		panel.setContent(content);

		name = new TextField("Nazwa");
		name.setWidth("100%");
		content.addComponent(name);

		procesor = new TextField("Procesor");
		procesor.setWidth("100%");
		content.addComponent(procesor);

		ram = new TextField("Pamięć ram");
		ram.setWidth("100%");
		content.addComponent(ram);

		maxRam = new TextField("Maksymalna obsługiwana ilość pamięci RAM");
		maxRam.setWidth("100%");
		content.addComponent(maxRam);

		ramSlotsOgolem = new TextField("Ilość gniazd pamięci ogółem");
		ramSlotsOgolem.setWidth("100%");
		content.addComponent(ramSlotsOgolem);

		ramSlotsWolne = new TextField("Ilość wolnych gniazd pamięci");
		ramSlotsWolne.setWidth("100%");
		content.addComponent(ramSlotsWolne);

		hardDrive = new TextField("Dysk twardy");
		hardDrive.setWidth("100%");
		content.addComponent(hardDrive);

		internalOpticalDrives = new TextField("Wbudowany napędy optyczne");
		internalOpticalDrives.setWidth("100%");
		content.addComponent(internalOpticalDrives);

		screenType = new TextField("Typ ekranu");
		screenType.setWidth("100%");
		content.addComponent(screenType);

		screenPrzekatna = new TextField("Przekątna ekranu");
		screenPrzekatna.setWidth("100%");
		content.addComponent(screenPrzekatna);

		screenResolution = new TextField("Rozdzielczość ekranu");
		screenResolution.setWidth("100%");
		content.addComponent(screenResolution);

		graphicCard = new TextField("Karta graficzna");
		graphicCard.setWidth("100%");
		content.addComponent(graphicCard);

		graphicCardRam = new TextField("Wielkość pamięci karty graficznej");
		graphicCardRam.setWidth("100%");
		content.addComponent(graphicCardRam);

		sound = new TextField("Dźwięk");
		sound.setWidth("100%");
		content.addComponent(sound);

		camera = new TextField("Kamera internetowa");
		camera.setWidth("100%");
		content.addComponent(camera);

		connection = new TextField("Łączność");
		connection.setWidth("100%");
		content.addComponent(connection);

		inOutTypes = new TextField("Rodzaje wejść/wyjść");
		inOutTypes.setWidth("100%");
		content.addComponent(inOutTypes);

		battery = new TextField("Bateria");
		battery.setWidth("100%");
		content.addComponent(battery);

		system = new TextField("Zainstalowany system operacyjny");
		system.setWidth("100%");
		content.addComponent(system);

		height = new TextField("Wysokość");
		height.setWidth("100%");
		content.addComponent(height);

		width = new TextField("Szerokość");
		width.setWidth("100%");
		content.addComponent(width);

		deepth = new TextField("Głębokość");
		deepth.setWidth("100%");
		content.addComponent(deepth);

		weight = new TextField("Waga");
		weight.setWidth("100%");
		content.addComponent(weight);

		additionalInfo = new TextField("Dodatkowe informacje");
		additionalInfo.setWidth("100%");
		content.addComponent(additionalInfo);

		accesories = new TextField("Dołączone akcesoria");
		accesories.setWidth("100%");
		content.addComponent(accesories);

		gwarancy = new TextField("Gwarancja");
		gwarancy.setWidth("100%");
		content.addComponent(gwarancy);

		priceField = new TextField("Cena");
		priceField.setWidth("100%");
		content.addComponent(priceField);

		button.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (!linkTB.isEmpty()) {
					try {
						auctionPage = Jsoup.connect(linkTB.getValue()).get();

						name.setValue(auctionPage.select("h1[itemprop=name]").first().text());

						procesor.setValue(getSpec("Procesor"));

						ram.setValue(getSpec("Pamięć RAM"));
						maxRam.setValue(getSpec("Maksymalna obsługiwana ilość pamięci RAM"));
						String ramSlots = getSpec("Ilość gniazd pamięci (ogółem / wolne)");
						ramSlotsOgolem.setValue(ramSlots.split("/")[0]);
						ramSlotsWolne.setValue(ramSlots.split("/")[1]);
						hardDrive.setValue(getSpec("Dysk twardy"));
						internalOpticalDrives.setValue(getSpec("Wbudowane napędy optyczne"));
						screenType.setValue(getSpec("Typ ekranu"));
						screenPrzekatna.setValue(getSpec("Przekątna ekranu"));
						screenResolution.setValue(getSpec("Rozdzielczość ekranu"));
						graphicCard.setValue(getSpec("Karta graficzna"));
						graphicCardRam.setValue(getSpec("Wielkość pamięci karty graficznej"));
						sound.setValue(getSpec("Dźwięk"));
						camera.setValue(getSpec("Kamera internetowa"));
						connection.setValue(getSpec("Łączność"));
						inOutTypes.setValue(getSpec("Rodzaje wejść / wyjść"));
						battery.setValue(getSpec("Bateria"));
						system.setValue(getSpec("Zainstalowany system operacyjny"));
						height.setValue(getSpec("Wysokość"));
						width.setValue(getSpec("Szerokość"));
						deepth.setValue(getSpec("Głębokość"));
						weight.setValue(getSpec("Waga"));
						additionalInfo.setValue(getSpec("Dodatkowe informacje"));
						accesories.setValue(getSpec("Dołączone akcesoria"));
						gwarancy.setValue(getSpec("Gwarancja"));
						priceField.setValue(auctionPage.select("meta[itemprop=price]").attr("content"));

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else
					Notification.show("Wpisz link!");
			}

			private String getSpec(String n) {
				Element specName = auctionPage.getElementsContainingOwnText(n).select("th").first();
				if (specName != null)
					return specName.nextElementSibling().text();
				else
					return "";
			}
		});

		return panel;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
