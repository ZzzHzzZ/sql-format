package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 13:54
 */
public class PeriodGroupFunction implements GroupFunction {
    @Override
    public Object match(Token token, Map<String, Object> otherParam) {
        return token.match(TokenType.PUNCTUATION, Collections.singletonList("."), null);
    }

    @Override
    public Object validPrev(Token token, Map<String, Object> otherParam) {
        otherParam.put(OtherParamConstants.I, Arrays.asList(SquareBrackets.class, Identifier.class));
        otherParam.put(OtherParamConstants.T, Arrays.asList(TokenType.NAME, TokenType.SYMBOL));
        return new ImtFunction().invoke(token, otherParam);
    }

    @Override
    public Object validNext(Token token, Map<String, Object> otherParam) {
        return true;
    }

    @Override
    public Object post(TokenList tlist, Map<String, Object> otherParam) {
        Integer pidx = (Integer) otherParam.get(OtherParamConstants.PIDX);
        Integer tidx = (Integer) otherParam.get(OtherParamConstants.TIDX);
        Integer nidx = (Integer) otherParam.get(OtherParamConstants.NIDX);
        List<Token> tokens = tlist.getTokens();
        Token next = nidx == null ? null : tokens.get(nidx);
        otherParam.put(OtherParamConstants.I, Arrays.asList(SquareBrackets.class, Function.class));
        otherParam.put(OtherParamConstants.T, Arrays.asList(TokenType.NAME, TokenType.SYMBOL, TokenType.WILDCARD));
        Boolean validNext = (Boolean) new ImtFunction().invoke(next, otherParam);
        if (validNext) {
            return Arrays.asList(pidx, nidx);
        } else {
            return Arrays.asList(pidx, tidx);
        }
    }
}
