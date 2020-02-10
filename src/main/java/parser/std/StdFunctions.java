package parser.std;

import java.util.HashMap;
import java.util.Map;

public class StdFunctions {
    public final static Map<String, StdFunction> function = new HashMap<String, StdFunction>() {{
        put(Define.DEFINE, new Define());
        put(Lambda.LAMBDA, new Lambda());
    }};
}
