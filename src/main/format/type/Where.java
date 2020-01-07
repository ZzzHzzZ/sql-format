package format.type;

import format.constants.TokenType;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 21:15
 */
public class Where extends TokenList {
    public Where() {
    }

    public Where(List<Token> tokens) {
        super(tokens);
    }

    public static Map<List<String>, TokenType> M_OPEN = new HashMap<>();
    public static Map<List<String>, TokenType> M_CLOSE = new HashMap<>();

    static {
        M_OPEN.put(Collections.singletonList("WHERE"), TokenType.KEYWORD);
        M_CLOSE.put(Arrays.asList("ORDER BY", "GROUP BY", "LIMIT", "UNION", "UNION ALL", "EXCEPT",
                "HAVING", "RETURNING", "INTO"), TokenType.KEYWORD);
    }
}
