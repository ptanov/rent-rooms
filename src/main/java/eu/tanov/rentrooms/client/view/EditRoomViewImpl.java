package eu.tanov.rentrooms.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditRoomViewImpl extends Composite implements EditRoomView {

	@UiTemplate("EditRoomView.ui.xml")
	interface EditRoomViewUiBinder extends UiBinder<Widget, EditRoomViewImpl> {
	}

	private static EditRoomViewUiBinder uiBinder = GWT.create(EditRoomViewUiBinder.class);

	@UiField
	protected TextBox roomName;
	@UiField
	protected Button saveButton;

	private Presenter presenter;

	public EditRoomViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("saveButton")
	public void onSaveButtonClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onSaveButtonClicked();
		}
	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public HasValue<String> getName() {
		return roomName;
	}
}
