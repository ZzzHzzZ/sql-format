package format.type;

import format.constants.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 17:53
 * A list of :class:`~sqlparse.sql.Identifier`\'s.
 */
public class IdentifierList extends TokenList {
    public IdentifierList() {
    }

    public IdentifierList(List<Token> tokens) {
        super(tokens);
    }

    public List<Token> getIdentifiers() {
        List<Token> result = new ArrayList<>();
        for (Token token : this.tokens) {
            if (!(token.isWhitespace() || token.match(TokenType.PUNCTUATION, Collections.singletonList(","), null))) {
                result.add(token);
            }
        }
        return result;
    }
}
