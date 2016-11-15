package nfcs.model.schedule;

import java.text.SimpleDateFormat;
import java.util.*;

public class Daily extends Schedule {
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	public Daily() {
		super();
		this.setPeriodType(Calendar.DAY_OF_YEAR);
	}
	
	public Daily(Date occurs) {
		super();
		this.setBegins(occurs);
		this.setPeriodType(Calendar.DAY_OF_YEAR);
	}

	@Override
	public boolean compareDates(Date date1, Date date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(date1).equals(sdf.format(date2));
	}

	@Override
	public boolean isOverdue(String event, Date date) {
		return super.isOverdue(event, date);
	}

	@Override
	public Date nextOccurence(String event, Date date) {
		return super.nextOccurence(event, date);
	}

	@Override
	public List<Date> getOccurences(String event, DateRange during) {
		return super.getOccurences(event, during);
	}

}
