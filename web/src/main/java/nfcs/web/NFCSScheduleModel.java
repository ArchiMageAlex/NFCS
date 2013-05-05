package nfcs.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

public class NFCSScheduleModel implements ScheduleModel, Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 320500848917715273L;
	//	private Hashtable<Integer, NFCSScheduleEvent> events;
//	private List<NFCSScheduleEvent> events;
	private TreeMap<String, NFCSScheduleEvent> events;
	
	public NFCSScheduleModel() {
		events = new TreeMap<String, NFCSScheduleEvent>(); 
	}
	
	@SuppressWarnings("unchecked")
	public NFCSScheduleModel(List<NFCSScheduleEvent> events) {
		this.events = (TreeMap<String, NFCSScheduleEvent>) events;
	}
	
	@Override
	public void addEvent(ScheduleEvent event) {
		events.put(event.getId(), (NFCSScheduleEvent) event);
	}

	@Override
	public boolean deleteEvent(ScheduleEvent event) {
		return (events.remove(event.getId())) != null;
	}

	@Override
	public List<ScheduleEvent> getEvents() {
		try {
			List<ScheduleEvent> l = new ArrayList<ScheduleEvent>();
		    l.addAll(events.values());
		    return l;
		}
		catch(ClassCastException e) {
			System.out.println("111");
			return null;
		}
	}

	@Override
	public ScheduleEvent getEvent(String id) {
		return events.get(id);
	}

	@Override
	public void updateEvent(ScheduleEvent event) {
		events.put(event.getId(), (NFCSScheduleEvent) event);

	}

	@Override
	public int getEventCount() {
		return events.size();
	}

	@Override
	public void clear() {
		events.clear();
	}

}
