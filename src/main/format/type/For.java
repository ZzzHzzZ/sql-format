package format.type;


import format.constants.TokenType;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 21:10
 */
public class For extends TokenList {
    public For() {
    }

    public For(List<Token> tokens) {
        super(tokens);
    }

    public static Map<List<String>, TokenType> M_OPEN = new HashMap<>();
    public static Map<List<String>, TokenType> M_CLOSE = new HashMap<>();

    static {
        M_OPEN.put(Arrays.asList("FOR","FOREACH"), TokenType.KEYWORD);
        M_CLOSE.put(Collections.singletonList("END LOOP"), TokenType.KEYWORD);
    }
}
