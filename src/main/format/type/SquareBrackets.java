package format.type;

import format.constants.TokenType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 21:08
 */
public class SquareBrackets extends TokenList {

    public SquareBrackets() {
    }

    public SquareBrackets(List<Token> tokens) {
        super(tokens);
    }

    public static Map<List<String>, TokenType> M_OPEN = new HashMap<>();
    public static Map<List<String>, TokenType> M_CLOSE = new HashMap<>();

    static {
        M_OPEN.put(Collections.singletonList("["), TokenType.PUNCTUATION);
        M_CLOSE.put(Collections.singletonList("]"), TokenType.PUNCTUATION);
    }

    @Override
    public List<Token> groupableTokens() {
        return this.tokens.subList(1, this.tokens.size() - 1);
    }
}
