package eu.tanov.rentrooms.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;

import eu.tanov.rentrooms.client.common.ColumnDefinition;

public class RoomsViewImpl<T> extends Composite implements RoomsView<T> {

	@UiTemplate("RoomsView.ui.xml")
	interface RoomsViewUiBinder extends UiBinder<Widget, RoomsViewImpl<?>> {
	}

	private static RoomsViewUiBinder uiBinder = GWT.create(RoomsViewUiBinder.class);

	@UiField
	protected FlexTable roomsTable;
	@UiField
	protected Button addButton;
	@UiField
	protected Button deleteButton;

	private Presenter<T> presenter;
	private List<ColumnDefinition<T>> columnDefinitions;
	private List<T> rowData;

	public RoomsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setPresenter(Presenter<T> presenter) {
		this.presenter = presenter;
	}

	public void setColumnDefinitions(List<ColumnDefinition<T>> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	public void setRowData(List<T> rowData) {
		roomsTable.removeAllRows();
		this.rowData = rowData;

		for (int i = 0; i < rowData.size(); ++i) {
			final T t = rowData.get(i);
			for (int j = 0; j < columnDefinitions.size(); ++j) {
				final ColumnDefinition<T> columnDefinition = columnDefinitions.get(j);
				roomsTable.setWidget(i, j, columnDefinition.render(t));
			}
		}
	}

	@UiHandler("addButton")
	void onAddButtonClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onAddButtonClicked();
		}
	}

//
//  @UiHandler("deleteButton")
//  void onDeleteButtonClicked(ClickEvent event) {
//    if (presenter != null) {
//      presenter.onDeleteButtonClicked();
//    }
//  }
//  
	private TableCellElement findNearestParentCell(Node node) {
		while ((node != null)) {
			if (Element.is(node)) {
				Element elem = Element.as(node);

				String tagName = elem.getTagName();
				if ("td".equalsIgnoreCase(tagName) || "th".equalsIgnoreCase(tagName)) {
					return elem.cast();
				}
			}
			node = node.getParentNode();
		}
		return null;
	}

	@UiHandler("roomsTable")
	public void onTableClicked(ClickEvent event) {
		if (presenter != null) {
			final HTMLTable.Cell cell = roomsTable.getCellForEvent(event);

			if (cell != null) {
				if (shouldFireClickEvent(cell)) {
					presenter.onItemClicked(rowData.get(cell.getRowIndex()));
				}

				if (shouldFireSelectEvent(cell)) {
					presenter.onItemSelected(rowData.get(cell.getRowIndex()));
				}
			}
		}
	}

	private boolean shouldFireClickEvent(HTMLTable.Cell cell) {
		boolean shouldFireClickEvent = false;

		if (cell != null) {
			ColumnDefinition<T> columnDefinition = columnDefinitions.get(cell.getCellIndex());

			if (columnDefinition != null) {
				shouldFireClickEvent = columnDefinition.isClickable();
			}
		}

		return shouldFireClickEvent;
	}

	private boolean shouldFireSelectEvent(HTMLTable.Cell cell) {
		boolean shouldFireSelectEvent = false;

		if (cell != null) {
			ColumnDefinition<T> columnDefinition = columnDefinitions.get(cell.getCellIndex());

			if (columnDefinition != null) {
				shouldFireSelectEvent = columnDefinition.isSelectable();
			}
		}

		return shouldFireSelectEvent;
	}

	public Widget asWidget() {
		return this;
	}
}
