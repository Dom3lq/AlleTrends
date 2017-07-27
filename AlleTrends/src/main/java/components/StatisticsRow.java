package components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import pojos.TypedStatistics;

@SuppressWarnings("serial")
public class StatisticsRow extends VerticalLayout {

	StatisticsCell auctionsCell, leftCell, biddersCell, bidsCell, saleCell;

	public StatisticsRow(String string) {
		Label label = new Label(string);
		label.setStyleName(ValoTheme.LABEL_BOLD);

		auctionsCell = new StatisticsCell();
		leftCell = new StatisticsCell();
		biddersCell = new StatisticsCell();
		bidsCell = new StatisticsCell();
		saleCell = new StatisticsCell();

		this.setSizeUndefined();
		this.setMargin(false);
		this.addComponents(label, auctionsCell, leftCell, biddersCell, bidsCell, saleCell);
		this.setComponentAlignment(label, Alignment.TOP_CENTER);
	}

	public void setContent(TypedStatistics stats, TypedStatistics allStats) {
		auctionsCell.setContent(stats.getItemsCount(), allStats.getItemsCount());
		leftCell.setContent(stats.getLeftCount(), allStats.getLeftCount());
		biddersCell.setContent(stats.getBiddersCount(), allStats.getBiddersCount());
		bidsCell.setContent(stats.getBidsCount(), allStats.getBidsCount());
		saleCell.setContent(stats.getSale(), allStats.getSale());
	}

	public void setContent(TypedStatistics stats, TypedStatistics allStats, TypedStatistics pastStats, TypedStatistics pastAllStats) {
		auctionsCell.setContent(stats.getItemsCount(), allStats.getItemsCount(), pastStats.getItemsCount(), pastAllStats.getItemsCount());
		leftCell.setContent(stats.getLeftCount(), allStats.getLeftCount(), pastStats.getLeftCount(), pastAllStats.getLeftCount());
		biddersCell.setContent(stats.getBiddersCount(), allStats.getBiddersCount(), pastStats.getBiddersCount(), pastAllStats.getBiddersCount());
		bidsCell.setContent(stats.getBidsCount(), allStats.getBidsCount(), pastStats.getBidsCount(), pastAllStats.getBidsCount());
		saleCell.setContent(stats.getSale(), allStats.getSale(), pastStats.getSale(), pastAllStats.getSale());
	}
}
