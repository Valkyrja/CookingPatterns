package org.cookingpatterns.UtilsAndExtentions;

import android.content.Context;
import android.os.Vibrator;

import java.util.Arrays;

/**
 * Created by Amarril van Gravyard on 06.12.2015.
 */
public class Utils
{
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }

    public static void HapticFeedbackShort(Context context)
    {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
}
