package format;

import format.constants.TokenType;
import format.type.IdentifierList;
import format.type.Parenthesis;
import format.type.Token;
import format.type.TokenList;

import java.util.List;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/26 11:40
 */
public class StripWhitespaceFilter {

    public static void process(TokenList tlist, Integer depth) {
        if (depth == null) {
            depth = 0;
        }
        List<Token> tokens = tlist.getSublist();
        for (Token token : tokens) {
            process((TokenList) token, depth + 1);
        }
        stripws(tlist);
        if (depth == 0 && tlist.getTokens() != null && tlist.getTokens().get(tlist.getTokens().size() - 1).isWhitespace()) {
            tlist.getTokens().remove(tlist.getTokens().size() - 1);
        }
    }

    private static void stripws(TokenList tlist) {
        Class c = tlist.getClass();
        if (c == IdentifierList.class) {
            stripwsIdentifierList(tlist);
        } else if (c == Parenthesis.class) {
            stripwsParenthesis(tlist);
        } else {
            stripwsDefault(tlist);
        }
    }

    private static void stripwsDefault(TokenList tlist) {
        Boolean lastWasWs = false;
        Boolean isFirstChar = true;
        List<Token> tokens = tlist.getTokens();
        for (Token token : tokens) {
            if (token.isWhitespace()) {
                if (lastWasWs || isFirstChar) {
                    token.setValue("");
                } else {
                    token.setValue(" ");
                }
            }
            lastWasWs = token.isWhitespace();
            isFirstChar = false;
        }
    }

    private static void stripwsIdentifierList(TokenList tlist) {
        Token lastNl = null;
        List<Token> tokens = tlist.getTokens();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (lastNl != null && token.getTtype() == TokenType.PUNCTUATION && token.getValue().equals(",")) {
                tlist.getTokens().remove(lastNl);
            }
            lastNl = token.isWhitespace() ? token : null;
        }
        stripwsDefault(tlist);
    }

    private static void stripwsParenthesis(TokenList tlist) {
        while (tlist.getTokens().get(1).isWhitespace()) {
            tlist.getTokens().remove(1);
        }
        while (tlist.getTokens().get(tlist.getTokens().size() - 2).isWhitespace()) {
            tlist.getTokens().remove(tlist.getTokens().size() - 2);
        }
        stripwsDefault(tlist);
    }
}
