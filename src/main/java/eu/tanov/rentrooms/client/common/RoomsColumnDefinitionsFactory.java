package eu.tanov.rentrooms.client.common;

import java.util.ArrayList;
import java.util.List;

import eu.tanov.rentrooms.shared.model.RoomDTO;

/**
 * Based on http://code.google.com/webtoolkit/articles/mvp-architecture-2.html
 */
public class RoomsColumnDefinitionsFactory<T> {
	public static List<ColumnDefinition<RoomDTO>> getRoomsColumnDefinitions() {
		return RoomsColumnDefinitionsImpl.getInstance();
	}

	public static List<ColumnDefinition<RoomDTO>> getTestRoomsColumnDefinitions() {
		return new ArrayList<ColumnDefinition<RoomDTO>>();
	}
}
