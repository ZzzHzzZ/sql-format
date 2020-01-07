package format.type;


import format.constants.TokenType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/24 13:45
 */
public class Begin extends TokenList {
    public Begin() {
    }

    public Begin(List<Token> tokens) {
        super(tokens);
    }

    public static Map<List<String>, TokenType> M_OPEN = new HashMap<>();
    public static Map<List<String>, TokenType> M_CLOSE = new HashMap<>();

    static {
        M_OPEN.put(Collections.singletonList("BEGIN"), TokenType.KEYWORD);
        M_CLOSE.put(Collections.singletonList("END"), TokenType.KEYWORD);
    }
}
