package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.*;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 14:26
 */
public class IdentifierListGroupFunction implements GroupFunction {
    @Override
    public Object match(Token token, Map<String, Object> otherParam) {
        return token.match(TokenType.PUNCTUATION, Collections.singletonList(","), null);
    }

    @Override
    public Object validPrev(Token token, Map<String, Object> otherParam) {
        List<TokenType> t = Arrays.asList(TokenType.NUMBER, TokenType.INTEGER, TokenType.FLOAT, TokenType.STRING, TokenType.STRINGSINGLE, TokenType.SYMBOL
                , TokenType.NAME, TokenType.PLACEHOLDER, TokenType.KEYWORD, TokenType.COMMENT, TokenType.WILDCARD);
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Arrays.asList("null", "role"), TokenType.KEYWORD);
        List<Class> i = Arrays.asList(Function.class, Case.class, Identifier.class, Comparison.class, IdentifierList.class, Operation.class);
        otherParam.put(OtherParamConstants.T, t);
        otherParam.put(OtherParamConstants.M, m);
        otherParam.put(OtherParamConstants.I, i);
        return new ImtFunction().invoke(token, otherParam);
    }

    @Override
    public Object validNext(Token token, Map<String, Object> otherParam) {
        List<TokenType> t = Arrays.asList(TokenType.NUMBER, TokenType.INTEGER, TokenType.FLOAT, TokenType.STRING, TokenType.STRINGSINGLE, TokenType.SYMBOL
                , TokenType.NAME, TokenType.PLACEHOLDER, TokenType.KEYWORD, TokenType.COMMENT, TokenType.WILDCARD);
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Arrays.asList("null", "role"), TokenType.KEYWORD);
        List<Class> i = Arrays.asList(Function.class, Case.class, Identifier.class, Comparison.class, IdentifierList.class, Operation.class);
        otherParam.put(OtherParamConstants.T, t);
        otherParam.put(OtherParamConstants.M, m);
        otherParam.put(OtherParamConstants.I, i);
        return new ImtFunction().invoke(token, otherParam);
    }

    @Override
    public Object post(TokenList tlist, Map<String, Object> otherParam) {
        List<Integer> result = Arrays.asList((Integer) otherParam.get(OtherParamConstants.PIDX), (Integer) otherParam.get(OtherParamConstants.NIDX));
        return result;
    }
}
