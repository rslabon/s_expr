package parser.specialform;

import java.util.HashMap;
import java.util.Map;

public class SpecialForms {
    public final static Map<String, SpecialForm> INSTANCE = new HashMap<String, SpecialForm>() {{
        put(Define.DEFINE, new Define());
        put(Lambda.LAMBDA, new Lambda());
    }};
}
