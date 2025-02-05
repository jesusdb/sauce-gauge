package com.saucedemo.pageobjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

public class ListUtils {
    public static List<String> getElementsText(List<WebElement> elements) {
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public static List<Float> getElementsPrice(List<WebElement> elements) {
        return elements.stream().map(e -> Float.parseFloat(e.getText().replace("$", ""))).collect(Collectors.toList());
    }

    public static <T extends Comparable<? super T>> boolean isSorted(List<T> list) {
        if (list == null || list.size() <= 1) {
            return true;
        }
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<? super T>> boolean isReverseSorted(List<T> list) {
        if (list == null || list.size() <= 1) {
            return true;
        }
        for (int i = 1; i < list.size(); i++) {
            // For reverse order, each previous element should be greater than or equal to the next one.
            if (list.get(i - 1).compareTo(list.get(i)) < 0) {
                return false;
            }
        }
        return true;
    }
}

