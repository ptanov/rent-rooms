package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.GwtEvent;

public class EditRoomCancelledEvent extends GwtEvent<EditRoomCancelledEventHandler>{
  public static Type<EditRoomCancelledEventHandler> TYPE = new Type<EditRoomCancelledEventHandler>();
  
  @Override
  public Type<EditRoomCancelledEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditRoomCancelledEventHandler handler) {
    handler.onEditRoomCancelled(this);
  }
}
