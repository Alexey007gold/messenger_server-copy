package com.alexkoveckiy.common.protocol;

public class RoutedAction<T extends ActionData> extends Action<T> {

	private RoutingData routingData;

	public RoutedAction() {
	}

	public RoutedAction(ActionHeader header, T data) {
		super(header, data);
	}

    public RoutingData getRoutingData() {
        return routingData;
    }

    public void setRoutingData(RoutingData routingData) {
	    if (this.routingData != null)
	        throw new IllegalStateException("RoutingData is already set!");

	    this.routingData = routingData;
    }
}
