package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.GwtEvent;

public class EditRoomEvent extends GwtEvent<EditRoomEventHandler>{
  public static Type<EditRoomEventHandler> TYPE = new Type<EditRoomEventHandler>();
  private final String id;
  
  public EditRoomEvent(String id) {
    this.id = id;
  }
  
  public String getId() { return id; }
  
  @Override
  public Type<EditRoomEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditRoomEventHandler handler) {
    handler.onEditRoom(this);
  }
}
