package format;

import format.constants.TokenType;
import format.type.Statement;
import format.type.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/24 14:00
 */
public class StatementSplitter {
    private Boolean inDeclare = false;
    private Boolean isCreate = false;
    private Integer beginDepth = 0;
    private Boolean consumeWs = false;
    private List<Token> tokens = new ArrayList<>();
    private Integer level = 0;

    private Integer changeSplitlevel(TokenType ttype, String value) {
        //Get the new split level (increase, decrease or remain equal)
        if (!ttype.getName().contains(TokenType.KEYWORD.getName())) {
            return 0;
        }

        String unified = value.toUpperCase();

        if (ttype == TokenType.DDL && unified.startsWith("CREATE")) {
            this.isCreate = true;
            return 0;
        }

        if (unified.equals("DECLARE") && this.isCreate && this.beginDepth == 0) {
            this.inDeclare = true;
            return 1;
        }

        if (unified.equals("BEGIN")) {
            this.beginDepth += 1;
            if (this.isCreate) {
                return 1;
            }
            return 0;
        }

        if (unified.equals("END")) {
            this.beginDepth = Math.max(0, this.beginDepth - 1);
            return -1;
        }

        if (Arrays.asList("IF", "FOR", "WHILE").contains(unified)
                && this.isCreate && this.beginDepth > 0) {
            return 1;
        }

        if (Arrays.asList("END IF", "END FOR", "END WHILE").contains(unified)) {
            return -1;
        }

        return 0;
    }

    public Statement process(List<Token> tokens) {
        List<TokenType> EOS_TTYPE = Arrays.asList(TokenType.WHITESPACE, TokenType.SINGLE);

        for (Token token : tokens) {
            if (this.consumeWs && !EOS_TTYPE.contains(token.getTtype())) {
                return new Statement(this.tokens);
            }

            //Change current split level (increase, decrease or remain equal)
            this.level += this.changeSplitlevel(token.getTtype(), token.getValue());

            //Append the token to the current statement
            this.tokens.add(new Token(token.getTtype(), token.getValue()));

            //Check if we get the end of a statement
            if (this.level <= 0 && token.getTtype().equals(TokenType.PUNCTUATION) && token.getValue().equals(";")) {
                this.consumeWs = true;
            }
        }
        if (!this.tokens.isEmpty()) {
            return new Statement(this.tokens);
        }
        return null;
    }
}
