package eu.tanov.rentrooms.client.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Based on http://code.google.com/webtoolkit/articles/mvp-architecture-2.html
 */
public class SelectionModel<T> {
	private List<T> selectedItems = new ArrayList<T>();

	public List<T> getSelectedItems() {
		return selectedItems;
	}

	public void addSelection(T item) {
		selectedItems.add(item);
	}

	public void removeSelection(T item) {
		selectedItems.remove(item);
	}

	public boolean isSelected(T item) {
		return selectedItems.contains(item);
	}
}