package nfcs.model.schedule;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public class DateRange {
	private Date from;
	private Date to;
	private Long ticker;

	public Long getTicker() {
		return ticker;
	}

	public void setTicker(Long ticker) {
		if(ticker == null)
			throw new NullPointerException("ticker must have an instance");
		else if(ticker == 0)
			throw new IllegalArgumentException("ticker must be a nonzero number");
		else
			this.ticker = ticker;
	}

	public DateRange(Date from, Date to, Long ticker) {
		this.setRange(from, to);
		this.setTicker(ticker);
	}
	
	public DateRange() {
		
	}

	public List<Date> getDateList() {
		List<Date> l = new Vector<Date>();
		for (Long counter = this.getFrom().getTime(); counter <= this.getTo().getTime(); counter += 1000 * this.getTicker())
			l.add(new Date(counter));
		return l;
	}

	public void setRange(Date from, Date to) {
		if (from.after(to))
			throw new IllegalArgumentException("<from> must be before <to>");
		this.from = from;
		this.to = to;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}
}
