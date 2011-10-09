package eu.tanov.rentrooms.client.view;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface EditRoomView {

	public interface Presenter {
		public void onSaveButtonClicked();
	}

	public void setPresenter(Presenter presenter);

	public HasValue<String> getName();

	public Widget asWidget();
}
