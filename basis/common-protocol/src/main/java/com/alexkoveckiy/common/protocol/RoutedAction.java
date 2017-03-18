package com.alexkoveckiy.common.protocol;

import java.io.Serializable;

public class RoutedAction<T extends Serializable> extends Action<T> {
	private static final long serialVersionUID = -318012176965757271L;

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
