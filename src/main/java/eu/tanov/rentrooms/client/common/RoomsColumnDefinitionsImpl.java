package eu.tanov.rentrooms.client.common;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import eu.tanov.rentrooms.shared.model.RoomDTO;

@SuppressWarnings("serial")
/**
 * Based on http://code.google.com/webtoolkit/articles/mvp-architecture-2.html
 */
public class RoomsColumnDefinitionsImpl extends 
    ArrayList<ColumnDefinition<RoomDTO>> {
  
  private static RoomsColumnDefinitionsImpl instance = null;
  
  public static RoomsColumnDefinitionsImpl getInstance() {
    if (instance == null) {
      instance = new RoomsColumnDefinitionsImpl();
    }
    
    return instance;
  }

	protected RoomsColumnDefinitionsImpl() {
		this.add(new ColumnDefinition<RoomDTO>() {
			@Override
			public Widget render(RoomDTO c) {
				return new CheckBox();
			}

			public boolean isSelectable() {
				return true;
			}
		});

		this.add(new ColumnDefinition<RoomDTO>() {
			public Widget render(RoomDTO c) {
				return new HTML(c.getName());
			}

			public boolean isClickable() {
				return true;
			}
		});
	}
}
