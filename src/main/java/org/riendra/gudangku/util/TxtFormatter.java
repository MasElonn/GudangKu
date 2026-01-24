package org.riendra.gudangku.util;

import javafx.scene.control.TextFormatter;

public class TxtFormatter {
    public static TextFormatter<Integer> intFormatter = new TextFormatter<>(change -> {
        if (change.getControlNewText().matches("\\d*")) {
            return change;
        }
        return null;
    });
    public static TextFormatter<Double> doubleFormatter = new TextFormatter<>(change -> {
        if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
            return change;
        }
        return null;
    });
}
