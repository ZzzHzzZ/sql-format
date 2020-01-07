package format.type;


import format.constants.TokenType;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 21:17
 */
public class Having extends TokenList {
    public Having() {
    }

    public Having(List<Token> tokens) {
        super(tokens);
    }

    public static Map<List<String>, TokenType> M_OPEN = new HashMap<>();
    public static Map<List<String>, TokenType> M_CLOSE = new HashMap<>();

    static {
        M_OPEN.put(Collections.singletonList("HAVING"), TokenType.KEYWORD);
        M_CLOSE.put(Arrays.asList("ORDER BY", "LIMIT"), TokenType.KEYWORD);
    }
}
