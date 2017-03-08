package com.alexkoveckiy.common.protocol;

import java.io.Serializable;

/**
 * Created by alex on 08.03.17.
 */
public class RoutingData implements Serializable {
    private static final long serialVersionUID = -5850459221736408718L;

    private String number;

    public RoutingData(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
