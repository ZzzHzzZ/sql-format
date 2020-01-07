package format.type;

import format.constants.TokenType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 17:00
 * Represents an identifier.
 * <p>
 * Identifiers may have aliases or typecasts.
 */
public class Identifier extends TokenList {
    public Identifier() {
    }

    public Identifier(List<Token> tokens) {
        super(tokens);
    }

    public Boolean isWildcard() {
        //Return ``True`` if this identifier contains a wildcard.
        List<Object> tokenNextBy = this.tokenNextBy(null, null, Collections.singletonList(TokenType.WILDCARD), null, null);
        return tokenNextBy != null;
    }

    public String getTypecast() {
        //Returns the typecast or ``None`` of this object as a string.
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Collections.singletonList("::"), TokenType.PUNCTUATION);
        List<Object> tokenNextBy = this.tokenNextBy(null, m, null, null, null);
        tokenNextBy = this.tokenNext((Integer) tokenNextBy.get(0), false, null, null);
        if (tokenNextBy != null) {
            return ((Token) tokenNextBy.get(1)).getValue();
        }
        return null;
    }

    public String getOrdering() {
        //Returns the ordering or ``None`` as uppercase string.
        List<Object> tokenNextBy = this.tokenNextBy(null, null, Collections.singletonList(TokenType.ORDER), null, null);
        if (tokenNextBy != null) {
            return ((Token) tokenNextBy.get(1)).getNormalized();
        }
        return null;
    }

}
