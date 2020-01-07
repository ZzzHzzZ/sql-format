package format.type;


import format.constants.TokenType;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 21:25
 */
public class Case extends TokenList {
    public Case() {
    }

    public Case(List<Token> tokens) {
        super(tokens);
    }

    public static Map<List<String>, TokenType> M_OPEN = new HashMap<>();
    public static Map<List<String>, TokenType> M_CLOSE = new HashMap<>();

    static {
        M_OPEN.put(Collections.singletonList("CASE"), TokenType.KEYWORD);
        M_CLOSE.put(Collections.singletonList("END"), TokenType.KEYWORD);
    }

    public List<List<List<Token>>> getCases(Boolean skipWs) {
        if (skipWs == null) {
            skipWs = false;
        }

        Integer condition = 1;
        Integer value = 2;

        List<List<List<Token>>> result = new ArrayList<>();
        Integer mode = condition;

        for (Token token : this.tokens) {
            if (token.match(TokenType.KEYWORD, Collections.singletonList("CASE"), null)) {
                continue;
            } else if (skipWs && token.getTtype().getName().contains(TokenType.WHITESPACE.getName())) {
                continue;
            } else if (token.match(TokenType.KEYWORD, Collections.singletonList("WHEN"), null)) {
                List<List<Token>> temp = new ArrayList<>();
                List<Token> a = new ArrayList<>();
                List<Token> b = new ArrayList<>();
                temp.add(a);
                temp.add(b);
                result.add(temp);
                mode = condition;
            } else if (token.match(TokenType.KEYWORD, Collections.singletonList("THEN"), null)) {
                mode = value;
            } else if (token.match(TokenType.KEYWORD, Collections.singletonList("ELSE"), null)) {
                List<List<Token>> temp = new ArrayList<>();
                List<Token> a = new ArrayList<>();
                temp.add(null);
                temp.add(a);
                result.add(temp);
                mode = value;
            } else if (token.match(TokenType.KEYWORD, Collections.singletonList("END"), null)) {
                mode = null;
            }

            //First condition without preceding WHEN
            if (mode != null && result.isEmpty()) {
                List<List<Token>> temp = new ArrayList<>();
                List<Token> a = new ArrayList<>();
                List<Token> b = new ArrayList<>();
                temp.add(a);
                temp.add(b);
                result.add(temp);
            }

            //Append token depending of the current mode
            if (condition.equals(mode)) {
                result.get(result.size() - 1).get(0).add(token);
            } else if (value.equals(mode)) {
                result.get(result.size() - 1).get(1).add(token);
            }
        }
        return result;
    }
}
