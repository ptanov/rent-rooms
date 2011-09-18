package eu.tanov.rentrooms.client.common;

import java.util.ArrayList;

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
      public void render(RoomDTO c, StringBuilder sb) {
        sb.append("<input type='checkbox'/>");
      }

      public boolean isSelectable() {
        return true;
      }
    });

    this.add(new ColumnDefinition<RoomDTO>() {
      public void render(RoomDTO c, StringBuilder sb) {        
        sb.append("<div id='" + c.getName() + "'>" + c.getName() + "</div>");
      }

      public boolean isClickable() {
        return true;
      }
    });
  }
}
