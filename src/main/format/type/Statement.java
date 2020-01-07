package format.type;


import format.constants.TokenType;

import java.util.List;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 15:57
 * Represents a SQL statement.
 */
public class Statement extends TokenList {
    public Statement() {
    }

    public Statement(List<Token> tokens) {
        super(tokens);
    }

    public String getType() {
        /**
         * Returns the type of a statement.
         *
         * The returned value is a string holding an upper-cased reprint of
         * the first DML or DDL keyword. If the first token in this group
         * isn't a DML or DDL keyword "UNKNOWN" is returned.
         *
         * Whitespaces and comments at the beginning of the statement
         * are ignored.
         * */
        Object firstToken = this.tokenFirst(null, true);
        if (firstToken == null) {
            return "UNKNOWN";
        }
        Token token = (Token) firstToken;
        if (token.getTtype().equals(TokenType.DML) || token.getTtype().equals(TokenType.DDL)) {
            return token.getNormalized();
        } else if (token.getTtype().equals(TokenType.CTE)) {
            Integer fidx = this.tokenIndex(token, null);
            List<Object> tokenNext = this.tokenNext(fidx, true, null, null);
            Integer tidx = (Integer) tokenNext.get(0);
            token = (Token) tokenNext.get(1);
            if (token instanceof Identifier || token instanceof IdentifierList) {
                tokenNext = this.tokenNext(tidx, true, null, null);
                if (tokenNext != null) {
                    Token dmlKeyword = (Token) tokenNext.get(1);
                    if (TokenType.DML.equals(dmlKeyword.getTtype())) {
                        return dmlKeyword.getNormalized();
                    }
                }
            }
        }
        return "UNKNOWN";
    }
}
