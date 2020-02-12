package parser.std;

import java.util.HashMap;
import java.util.Map;

public class StdFunctions {

    public static final Map<String, StdFunction> INSTANCE = new HashMap<String, StdFunction>() {{
        put(Add.NAME, new Add());
    }};
}
