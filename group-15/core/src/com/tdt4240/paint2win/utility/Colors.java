package com.tdt4240.paint2win.utility;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ObjectMap;

public final class Colors {

    private static final ObjectMap<String, Color> map = new ObjectMap<String, Color>();

    /**
     * Fills the hashmap with objects
     */
    static {
        reset();
    }

    /** Convenience method to lookup a color by {@code name}. The invocation of this method is equivalent to the expression
     * {@code Colors.getColors().get(name)}
     *
     * @param name the name of the color
     * @return the color to which the specified {@code name} is mapped, or {@code null} if there was no mapping for {@code name}
     *         . */
    public static Color get(String name) {
        return map.get(name);
    }

    /** Resets the color map to the predefined colors. */
    public static void reset () {
        map.clear();
        map.put("CLEAR", Color.CLEAR);
        map.put("BLACK", Color.BLACK);

        map.put("WHITE", Color.WHITE);
        map.put("LIGHT_GRAY", Color.LIGHT_GRAY);
        map.put("GRAY", Color.GRAY);
        map.put("DARK_GRAY", Color.DARK_GRAY);

        map.put("BLUE", Color.BLUE);
        map.put("NAVY", Color.NAVY);
        map.put("ROYAL", Color.ROYAL);
        map.put("SLATE", Color.SLATE);
        map.put("SKY", Color.SKY);
        map.put("CYAN", Color.CYAN);
        map.put("TEAL", Color.TEAL);

        map.put("GREEN", Color.GREEN);
        map.put("CHARTREUSE", Color.CHARTREUSE);
        map.put("LIME", Color.LIME);
        map.put("FOREST", Color.FOREST);
        map.put("OLIVE", Color.OLIVE);

        map.put("YELLOW", Color.YELLOW);
        map.put("GOLD", Color.GOLD);
        map.put("GOLDENROD", Color.GOLDENROD);
        map.put("ORANGE", Color.ORANGE);

        map.put("BROWN", Color.BROWN);
        map.put("TAN", Color.TAN);
        map.put("FIREBRICK", Color.FIREBRICK);

        map.put("RED", Color.RED);
        map.put("SCARLET", Color.SCARLET);
        map.put("CORAL", Color.CORAL);
        map.put("SALMON", Color.SALMON);
        map.put("PINK", Color.PINK);
        map.put("MAGENTA", Color.MAGENTA);

        map.put("PURPLE", Color.PURPLE);
        map.put("VIOLET", Color.VIOLET);
        map.put("MAROON", Color.MAROON);
    }

    private Colors () {
    }

}
