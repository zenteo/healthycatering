package edu.hist.team3.catering.gui.panel;

public class LabeledObject<T> {
	private String label;
	private T object;
	
	public LabeledObject(String label, T object) {
		this.label = label;
		this.object = object;
	}
	
	public String toString() {
		return label;
	}

	public String getLabel() {
		return label;
	}

	public T getObject() {
		return object;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
