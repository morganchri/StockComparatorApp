package edu.neu.team28finalproject.datatransferobjects;

public enum IndicatorResolution {
    RES_1("1"),
    RES_5("5"),
    RES_15("15"),
    RES_30("30"),
    RES_60("60"),
    RES_D("D"),
    RES_W("W"),
    RES_M("M");

    private String resolution;

    private IndicatorResolution(String resolution) {
        this.resolution = resolution;
    };

    public String getResolution() {
        return resolution;
    }
}
