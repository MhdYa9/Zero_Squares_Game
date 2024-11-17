package structure;

import java.util.HashMap;
import java.util.Map;

public interface Printable {

    public static final String RESET = "\033[0m";  // Reset color
    public static final String RED = "\033[41m";   // Red background
    public static final String GREEN = "\033[42m"; // Green background
    public static final String YELLOW = "\033[43m"; // Yellow background
    public static final String BLUE = "\033[44m";  // Blue background
    public static final String PURPLE = "\033[45m"; // Purple background
    public static final String CYAN = "\033[46m";  // Cyan background
    public static final String WHITE = "\033[47m"; // White background
    public static final String BLACK = "\033[40m"; // Black background

    public static final String BLUE_BORDER = "\033[34m";       // Blue border
    public static final String RED_BORDER = "\033[31m";        // Red border
    public static final String GREEN_BORDER = "\033[32m";      // Green border
    public static final String YELLOW_BORDER = "\033[33m";     // Yellow border
    public static final String PURPLE_BORDER = "\033[35m";     // Purple border
    public static final String CYAN_BORDER = "\033[36m";       // Cyan border
    public static final String BLACK_BORDER = "\033[30m";      // Black border
    public static final String WHITE_BORDER = "\033[37m";  // White text


    public static final String [] colors = {RED,GREEN,YELLOW,BLUE,PURPLE,CYAN,WHITE,BLACK,RESET};
    public static final String [] borders = {RED_BORDER,GREEN_BORDER,YELLOW_BORDER,BLUE_BORDER,PURPLE_BORDER,CYAN_BORDER,WHITE_BORDER,BLACK_BORDER};

    public static final Map<String, Integer> mp = new HashMap<String,Integer>() {{
        put("r", 0);
        put("g", 1);
        put("y",2);
        put("b",3);
        put("p",4);
        put("c",5);
        put("o",8);
        put("w",6);
        put("*",7);
    }};

    public void print();

}
