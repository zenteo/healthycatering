package edu.hist.team3.catering.gui.panel;

public class LabeledObject<T> {
	private String label;
	private T object;
	
	/**
	 * Creates a new LabeledObject.
	 * @param label
	 * @param object
	 */
	public LabeledObject(String label, T object) {
		this.label = label;
		this.object = object;
	}
	
	/**
	 * Returns the label text.
	 */
	public String toString() {
		return label;
	}

	/**
	 * Returns the label
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns T?
	 * @return
	 */
	public T getObject() {
		return object;
	}

	/**
	 * Set the label text.
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Set the object T.
	 * @param object
	 */
	public void setObject(T object) {
		this.object = object;
	}
}
