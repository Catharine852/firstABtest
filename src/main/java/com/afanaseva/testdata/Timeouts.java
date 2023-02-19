package com.afanaseva.utils;

public interface Timeouts {
    float FACTOR = 1.00f;
    int PAGE_LOADING = (int) (FACTOR * 60); //(int) (FACTOR * Integer.parseInt(Properties.get("pageLoadingTimeout")));
    int CLICK = (int) (FACTOR * 15);
    int AFTER_CLICK = (int) (FACTOR * 5);
    int ELEMENT_TO_BE_CLICKABLE = (int) (FACTOR * 15);
    int ELEMENT_TO_BE_VISIBLE = (int) (FACTOR * 15);
    int CALENDAR = (int) (FACTOR * 3);
}
