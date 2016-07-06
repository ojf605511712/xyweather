package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/21.
 * The max temperature and the min temperature
 */
public class Temperature implements Serializable{

    private float max;
    private float min;

    public float getMax() {
        return max;
    }
    public void setMax(float max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }
    public void setMin(float min) {
        this.min = min;
    }
}
