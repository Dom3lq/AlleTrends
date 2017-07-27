package com.example;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.allegro.webapi.service_php.CatInfoType;
import pl.allegro.webapi.service_php.ItemsListType;
import pl.allegro.webapi.service_php.ParameterInfoType;
import pojos.Category;
import pojos.CategoryFailedPortion;
import pojos.CategoryPortion;
import pojos.CategoryRaport;
import pojos.Keyword;
import pojos.KeywordRaport;
import pojos.ParameterName;
import pojos.ParameterValue;
import pojos.ParameterValueRaport;
import pojos.ParsingThread;
import pojos.Raport;
import pojos.Seller;
import pojos.SellerRaport;
import repositories.CategoryFailedPortionRepository;
import repositories.CategoryRaportRepository;
import repositories.CategoryRepository;
import repositories.KeywordRaportRepository;
import repositories.KeywordRepository;
import repositories.ParameterNameRepository;
import repositories.ParameterValueRaportRepository;
import repositories.ParameterValueRepository;
import repositories.RaportRepository;
import repositories.SellerRaportRepository;
import repositories.SellerRepository;

@Component
public class TrendsBot implements Runnable {

	private final int threadsNumber = 100;
	private final Boolean parsingKeywords = false;
	private final Boolean parsingSellers = true;
	private final Boolean parsingParameters = false;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryRaportRepository categoryRaportRepository;

	@Autowired
	private RaportRepository raportRepository;

	@Autowired
	private KeywordRepository keywordRepository;

	@Autowired
	private KeywordRaportRepository keywordRaportRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private SellerRaportRepository sellerRaportRepository;

	@Autowired
	private CategoryFailedPortionRepository categoryFailedPortionRepository;

	@Autowired
	private ParameterNameRepository parameterNameRepository;

	@Autowired
	private ParameterValueRepository parameterValueRepository;

	@Autowired
	private ParameterValueRaportRepository parameterValueRaportRepository;

	private Map<Integer, CategoryRaport> categoryRaports;

	private Map<String, Keyword> keywords;
	private Map<String, KeywordRaport> keywordRaports;

	private Map<Integer, Seller> sellers;
	private Map<String, SellerRaport> sellerRaports;

	private Map<String, ParameterName> parameterNames;
	private Map<String, ParameterValue> parameterValues;
	private Map<String, ParameterValueRaport> parameterValueRaports;

	private Raport raport;
	private List<CategoryFailedPortion> categoryFailedPortions;

	@Override
	public void run() {
		saveInfo();
	}

	private void saveInfo() {

		raport = new Raport(System.currentTimeMillis() / 1000L);
		raportRepository.save(raport);

		if (parsingSellers)
			getSellers();
		if (parsingKeywords)
			getKeywords();
		if (parsingParameters)
			getParameters();

		categoryFailedPortions = new ArrayList<CategoryFailedPortion>();

		System.out.println("Getting categories");
		Map<Integer, Category> categories = getCategories();

		System.out.println("Getting threads");
		List<Thread> threads = portionCategoriesAndParseToThreads(categories);

		System.out.println("Starting threads");
		startThreads(threads);

		System.out.println("Joining threads");
		joinThreads(threads);

		saveCategoryRaports();

		if (parsingSellers)
			saveSellers();
		if (parsingKeywords)
			saveKeywords();
		if (parsingParameters)
			saveParameters();

		this.categoryFailedPortionRepository.save(categoryFailedPortions);

		completeRaport();
	}

	private Map<CategoryRaport, List<CategoryRaport>> getCategoryRaportsChildsMap(Map<Integer, Category> categories) {
		Map<CategoryRaport, List<CategoryRaport>> categoryRaportsChilds = new HashMap<CategoryRaport, List<CategoryRaport>>();

		categories.forEach((id, cat) -> {
			CategoryRaport parent = categoryRaports.get(cat.getParent().getId());
			CategoryRaport child = categoryRaports.get(id);

			List<CategoryRaport> childs = categoryRaportsChilds.get(parent);

			if (childs == null) {
				childs = new ArrayList<CategoryRaport>();
				categoryRaportsChilds.put(parent, childs);
			}
			childs.add(child);
		});

		return categoryRaportsChilds;
	}

	private void completeRaport() {
		raport.setIsComplete(true);
		raportRepository.save(raport);

		System.out
				.println("Raport completed with efficienty of " + (raport.getActualSize() * 100 / raport.getTotalSize())
						+ " % after " + ((getCurrentUnixTime() - raport.getTime()) / 60) + ":"
						+ ((getCurrentUnixTime() - raport.getTime()) % 60));
	}

	private void joinThreads(List<Thread> threads) {
		for (Thread t : threads)
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	private void startThreads(List<Thread> threads) {
		for (Thread t : threads)
			t.start();
	}

	private List<Thread> portionCategoriesAndParseToThreads(Map<Integer, Category> categories) {
		List<Integer> childsIds = getChildCategories(categories).keySet().stream().map(cks -> cks.intValue())
				.collect(Collectors.toList());
		;

		ApiConnector ac = new ApiConnector();
		List<Long[]> cats = new ArrayList<Long[]>();

		raport.setTotalSize(0L);

		childsIds.parallelStream().forEach(childId -> {
			long id = childId;
			long size = ac.getItemsSize(String.valueOf(id));
			long a = size / 100 * 100;
			long b = size - a;

			categoryRaports.get(Math.toIntExact(id)).setTotalSize(size);

			synchronized (raport) {
				raport.setTotalSize(raport.getTotalSize() + size);
				System.out.println("Portioning: " + String.format("%,8d", raport.getTotalSize()));
			}

			synchronized (cats) {
				cats.add(new Long[] { id, size, a, b });
			}
		});

		List<ParsingThread> parsingThreads = portionCategories(cats);

		return parsingThreads.stream().map(t -> new Thread(t)).collect(Collectors.toList());
	}

	private List<ParsingThread> portionCategories(List<Long[]> cats) {
		raport.setActualSize(0L);

		List<ParsingThread> parsingThreads = new ArrayList<ParsingThread>();
		List<CategoryPortion> portions = new ArrayList<CategoryPortion>();
		long startOffset = 0;
		long offsetPerPortion = raport.getTotalSize() / threadsNumber / 100 * 100;
		long left = offsetPerPortion;

		for (Long[] c : cats) {

			while (startOffset + left <= c[2]) {
				portions.add(new CategoryPortion(c[0], startOffset, startOffset + left));
				startOffset = startOffset + left;
				left = offsetPerPortion;

				parsingThreads.add(new ParsingThread(portions));
				portions = new ArrayList<CategoryPortion>();
			}

			portions.add(new CategoryPortion(c[0], startOffset, c[2] + c[3]));
			left = left - (c[2] - startOffset);
			startOffset = 0;
		}

		parsingThreads.add(new ParsingThread(portions));

		parsingThreads = parsingThreads.stream().sorted((p1, p2) -> Long.compare(p1.getSize(), p2.getSize()))
				.collect(Collectors.toList());
		return parsingThreads;
	}

	private Map<Integer, Category> getChildCategories(Map<Integer, Category> categories) {
		Map<Integer, Category> childs = new HashMap<Integer, Category>(categories);

		for (Category c : categories.values()) {
			if (c.getParent() != null)
				if (childs.containsKey(c.getParent().getId()))
					childs.remove(c.getParent().getId());
		}

		return childs;
	}

	private void saveCategoryRaports() {
		categoryRaportRepository.save(categoryRaports.values());
	}

	private void saveParameters() {
		parameterNameRepository.save(parameterNames.values());
		parameterValueRepository.save(parameterValues.values());
		parameterValueRaportRepository.save(parameterValueRaports.values());
	}

	private void saveKeywords() {
		keywordRepository.save(keywords.values());
		keywordRaportRepository.save(keywordRaports.values());
	}

	private void saveSellers() {
		sellerRepository.save(sellers.values());
		sellerRaportRepository.save(sellerRaports.values());
	}

	private Map<Integer, Category> getCategories() {
		ApiConnector ac = new ApiConnector();

		CatInfoType[] apiCategories = ac.getCategories();
		Map<Integer, Category> categories = new HashMap<Integer, Category>();

		Category allegroCategory = categoryRepository.findOne(0);
		if (allegroCategory == null) {
			allegroCategory = new Category(0, "Allegro", 0);
			categoryRepository.save(allegroCategory);
		}

		categories.put(0, allegroCategory);

		for (CatInfoType apiCategory : apiCategories) {
			Category category = categoryRepository.findOne(apiCategory.getCatId());

			if (category == null) {
				category = new Category(apiCategory.getCatId(), apiCategory.getCatName(), apiCategory.getCatPosition());
				categoryRepository.save(category);
			}

			categories.put(category.getId(), category);
		}

		this.categoryRaports = new HashMap<Integer, CategoryRaport>();

		CategoryRaport allegroCategoryRaport = new CategoryRaport(raport, allegroCategory);
		this.categoryRaports.put(allegroCategoryRaport.getCategory().getId(), allegroCategoryRaport);

		for (CatInfoType apiCategory : apiCategories) {

			Category category = categories.get(apiCategory.getCatId());

			Category parent = categories.get(apiCategory.getCatParent());
			category.setParent(parent);
			categories.put(apiCategory.getCatId(), category);
			categoryRepository.save(category);
			CategoryRaport categoryRaport = new CategoryRaport(raport, category);

			this.categoryRaports.put(categoryRaport.getCategory().getId(), categoryRaport);

		}
		return categories;
	}

	private void getParameters() {
		parameterNames = new HashMap<String, ParameterName>();
		parameterValues = new HashMap<String, ParameterValue>();
		parameterValueRaports = new HashMap<String, ParameterValueRaport>();

		parameterValueRepository.findAll().forEach(parameterValue -> {
			ParameterName parameterName = parameterValue.getParameterName();

			parameterValues.put(parameterValue.getKey(), parameterValue);

			if (!parameterNames.containsKey(parameterName.getKey()))
				parameterNames.put(parameterName.getKey(), parameterName);
		});
	}

	private void getKeywords() {
		keywords = new HashMap<String, Keyword>();
		keywordRaports = new HashMap<String, KeywordRaport>();
		keywordRepository.findAll().forEach(k -> keywords.put(k.getWord(), k));
	}

	private void getSellers() {
		sellers = new HashMap<Integer, Seller>();
		sellerRaports = new HashMap<String, SellerRaport>();
		sellerRepository.findAll().forEach(s -> sellers.put(s.getId(), s));
	}

	public void getItemsFromApi(long catId, long startOffset, long endOffset) {
		System.out.println("CatId: " + catId + " SO: " + startOffset + " EO: " + endOffset);
		ApiConnector ac = new ApiConnector();

		List<CategoryRaport> categoryAndParentsRaports = getCategoryAndParentsRaports(catId);

		for (long i = startOffset; i < endOffset; i += 100) {
			ItemsListType[] items = ac.get100Items(String.valueOf(catId), Math.toIntExact(i));

			if (items != null) {
				LocalTime pst = LocalTime.now();
				parseItems(categoryAndParentsRaports, items);
				incrementAndLogProgress(items.length, ChronoUnit.MILLIS.between(pst, LocalTime.now()));
			} else {
				List<CategoryFailedPortion> categoryRaportFailedPortions = categoryAndParentsRaports.stream()
						.map(catRaport -> new CategoryFailedPortion(catRaport, startOffset, endOffset))
						.collect(Collectors.toList());

				this.categoryFailedPortions.addAll(categoryRaportFailedPortions);
			}
		}
		categoryRaportRepository.save(categoryAndParentsRaports);
	}

	private void parseItems(List<CategoryRaport> categoryAndParents, ItemsListType[] items) {

		for (ItemsListType spi : items) {
			for (CategoryRaport cdr : categoryAndParents) {
				cdr.incrementBy(spi);
			}

			if (parsingSellers)
				parseSeller(spi, categoryAndParents);
			if (parsingKeywords)
				parseKeywords(spi, categoryAndParents);
			if (parsingParameters)
				parseParameters(spi, categoryAndParents);

		}

		incrementCategoryRaportsProgress(categoryAndParents, items);

	}

	private void incrementCategoryRaportsProgress(List<CategoryRaport> categoryAndParents, ItemsListType[] items) {
		for (CategoryRaport cdr : categoryAndParents) {
			cdr.incrementActualSizeBy(items.length);
		}
	}

	private void parseParameters(ItemsListType spi, List<CategoryRaport> categoryRaports) {
		for (CategoryRaport categoryRaport : categoryRaports)
			if (spi.getParametersInfo() != null) {
				Category cat = categoryRaport.getCategory();
				for (ParameterInfoType param : spi.getParametersInfo()) {
					String name = param.getParameterName();
					String[] values = param.getParameterValue();

					ParameterName parameterName;
					synchronized (parameterNames) {
						parameterName = parameterNames.get(cat.getId() + name.toLowerCase());

						if (parameterName == null) {
							parameterName = new ParameterName();
							parameterName.setName(name);
							parameterName.setCategory(cat);
							parameterNames.put(cat.getId() + name.toLowerCase(), parameterName);
						}
					}

					for (String value : values) {

						ParameterValue parameterValue;
						synchronized (parameterValues) {
							parameterValue = parameterValues
									.get(cat.getId() + name.toLowerCase() + value.toLowerCase());

							if (parameterValue == null) {
								parameterValue = new ParameterValue();
								parameterValue.setName(value);
								parameterValue.setParameterName(parameterName);
								parameterValues.put(cat.getId() + name.toLowerCase() + value.toLowerCase(),
										parameterValue);
							}
						}

						ParameterValueRaport parameterValueRaport;
						synchronized (parameterValueRaports) {
							parameterValueRaport = parameterValueRaports.get(name + value + categoryRaport.getId());

							if (parameterValueRaport == null) {
								parameterValueRaport = new ParameterValueRaport();
								parameterValueRaport.setCategoryRaport(categoryRaport);
								parameterValueRaport.setParameterValue(parameterValue);
								parameterValueRaports.put(cat.getId() + name + value + categoryRaport.getId(),
										parameterValueRaport);
							}
						}
						parameterValueRaport.incrementBy(spi);

					}
				}
			}
	}

	private void incrementAndLogProgress(int length, long parsingTime) {
		raport.incrementActualSizeBy(length);

		Long actualTime = (getCurrentUnixTime() - raport.getTime()) / 60;
		Long timeLeft = ((actualTime * raport.getTotalSize() / raport.getActualSize()) - actualTime);
		Long totalTime = (actualTime * raport.getTotalSize() / raport.getActualSize());

		String info = "Size: " + String.format("%,8d", raport.getActualSize()) + " / "
				+ String.format("%,8d", raport.getTotalSize());

		info += " | Progress: " + (raport.getActualSize() * 100 / raport.getTotalSize()) + "%";

		info += " | Left time: " + (timeLeft / 60) + ":" + (timeLeft % 60) + " / " + (totalTime / 60) + ":"
				+ (totalTime % 60);

		info += " | Parsing time: " + parsingTime + " miliseconds";

		if (parsingSellers)
			info += " | Sellers: " + String.format("%,8d", sellers.size()) + " | SellerRaports: "
					+ String.format("%,8d", sellerRaports.size());

		if (parsingKeywords)
			info += " | Keywords: " + String.format("%,8d", keywords.size()) + " | KeywordRaports: "
					+ String.format("%,8d", keywordRaports.size());

		if (parsingParameters)
			info += " | ParameterNames: " + String.format("%,8d", parameterNames.size()) + " | ParameterValues: "
					+ String.format("%,8d", parameterValues.size()) + " | ParameterValueRaports: "
					+ String.format("%,8d", parameterValueRaports.size());

		System.out.println(info);
	}

	private long getCurrentUnixTime() {
		return System.currentTimeMillis() / 1000L;
	}

	private void parseKeywords(ItemsListType spi, List<CategoryRaport> categoryRaports) {
		for (String word : spi.getItemTitle().split(" ")) {
			word = word.toLowerCase().trim();
			Keyword keyword;
			synchronized (keywords) {
				keyword = keywords.get(word);
				if (keyword == null) {
					keyword = new Keyword();
					keyword.setWord(word);
					keywords.put(word, keyword);
				}
			}

			for (CategoryRaport categoryRaport : categoryRaports) {
				KeywordRaport keywordRaport;
				synchronized (keywordRaports) {
					keywordRaport = keywordRaports.get(keyword.getWord() + categoryRaport.getId());
					if (keywordRaport == null) {
						keywordRaport = new KeywordRaport();
						keywordRaport.setCategoryRaport(categoryRaport);
						keywordRaport.setKeyword(keyword);
						keywordRaports.put(keyword.getWord() + categoryRaport.getId(), keywordRaport);
					}
				}
				keywordRaport.incrementBy(spi);
			}
		}
	}

	private void parseSeller(ItemsListType spi, List<CategoryRaport> categoryRaports) {
		if (spi.getSellerInfo() != null) {
			Seller seller;

			synchronized (sellers) {
				seller = sellers.get(spi.getSellerInfo().getUserId());
				if (seller == null) {
					seller = new Seller();
					seller.setName(spi.getSellerInfo().getUserLogin());
					seller.setId(spi.getSellerInfo().getUserId());
					sellers.put(spi.getSellerInfo().getUserId(), seller);
				}
			}

			for (CategoryRaport categoryRaport : categoryRaports) {
				SellerRaport sellerRaport;
				synchronized (sellerRaports) {
					sellerRaport = sellerRaports.get(seller.getName() + categoryRaport.getId());
					if (sellerRaport == null) {
						sellerRaport = new SellerRaport();
						sellerRaport.setSeller(seller);
						sellerRaport.setCategoryRaport(categoryRaport);
						sellerRaports.put(seller.getName() + categoryRaport.getId(), sellerRaport);
					}
				}

				sellerRaport.incrementBy(spi);
			}
		}
	}

	private List<CategoryRaport> getCategoryAndParentsRaports(long catId) {
		List<CategoryRaport> categoryAndParents = new ArrayList<CategoryRaport>();

		CategoryRaport cdr = categoryRaports.get(Math.toIntExact(catId));
		categoryAndParents.add(cdr);

		while (cdr.getCategory().getParent() != null) {
			cdr = categoryRaports.get(cdr.getCategory().getParent().getId());
			categoryAndParents.add(cdr);
		}

		return categoryAndParents;
	}

}
