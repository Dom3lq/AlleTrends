package pojos;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.example.AppContextManager;
import com.example.TrendsBot;

public class ParsingThread implements Runnable {

	private List<CategoryPortion> portions;

	TrendsBot trendsBot;

	public ParsingThread(final List<CategoryPortion> portions) {
		this.setPortions(portions);
		ApplicationContext ctx = AppContextManager.getAppContext();
		trendsBot = ctx.getBean(TrendsBot.class);
	}

	public List<CategoryPortion> getPortions() {
		return portions;
	}

	public void setPortions(List<CategoryPortion> portions) {
		this.portions = portions;
	}

	public long getSize() {
		return this.portions.stream().mapToLong(cp -> cp.getSize()).sum();
	}

	@Override
	public void run() {
		for (CategoryPortion cp : getPortions())
			trendsBot.getItemsFromApi(cp.getCatId(), cp.getStartOffset(), cp.getEndOffset());
	}
}
