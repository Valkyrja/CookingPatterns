package org.cookingpatterns.UtilsAndExtentions;

import java.util.Arrays;

/**
 * Created by Amarril van Gravyard on 06.12.2015.
 */
public class Utils
{
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }
}
