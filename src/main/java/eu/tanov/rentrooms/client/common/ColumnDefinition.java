package eu.tanov.rentrooms.client.common;

import com.google.gwt.user.client.ui.Widget;

/**
 * Based on http://code.google.com/webtoolkit/articles/mvp-architecture-2.html
 */
public abstract class ColumnDefinition<T> {
	public abstract Widget render(T t);

	public boolean isClickable() {
		return false;
	}

	public boolean isSelectable() {
		return false;
	}
}
