package org.zkoss.fiddle.event;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;


public class OptionSelectedEvent extends Event{
	private int selectedIndex;
	private String inputValue;
	private Object value;
	
	public OptionSelectedEvent(String name, Component target, Object data) {
		super(name, target, data);
	}

	public OptionSelectedEvent(String name, Component target) {
		super(name, target);
	}

	public OptionSelectedEvent(String name) {
		super(name);
	}

	public OptionSelectedEvent(
			String eventName,
			Component comp,
			int selectedIndex, String inputValue, Object value) {
		super(eventName,comp,null);
		this.selectedIndex = selectedIndex;
		this.inputValue = inputValue;
		this.value = value;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	
	public String getInputValue() {
		return inputValue;
	}
	
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
