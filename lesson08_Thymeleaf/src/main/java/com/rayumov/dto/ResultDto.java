package com.rayumov.dto;

public class ResultDto {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ResultDto() {
    }

    public ResultDto(int value) {
        this.value = value;
    }

    /*
    {
    "value": 5
    }
     */
}
