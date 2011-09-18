package eu.tanov.rentrooms.client.common;

/**
 * Based on http://code.google.com/webtoolkit/articles/mvp-architecture-2.html
 */
public abstract class ColumnDefinition<T> {
	public abstract void render(T t, StringBuilder sb);

	public boolean isClickable() {
		return false;
	}

	public boolean isSelectable() {
		return false;
	}
}
