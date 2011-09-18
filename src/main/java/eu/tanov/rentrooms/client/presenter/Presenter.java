package eu.tanov.rentrooms.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * From http://code.google.com/webtoolkit/articles/mvp-architecture-2.html
 */
public abstract interface Presenter {
	public abstract void go(final HasWidgets container);
}
