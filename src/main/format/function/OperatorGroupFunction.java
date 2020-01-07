package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 13:59
 */
public class OperatorGroupFunction implements GroupFunction {
    @Override
    public Object match(Token token, Map<String, Object> otherParam) {
        otherParam.put(OtherParamConstants.T, Arrays.asList(TokenType.OPERATOR, TokenType.WILDCARD));
        return new ImtFunction().invoke(token, otherParam);
    }

    @Override
    public Object validPrev(Token token, Map<String, Object> otherParam) {
        List<TokenType> t = Arrays.asList(TokenType.NUMBER, TokenType.INTEGER, TokenType.FLOAT, TokenType.STRING, TokenType.STRINGSINGLE, TokenType.SYMBOL
                , TokenType.NAME, TokenType.PLACEHOLDER);
        List<Class> i = Arrays.asList(SquareBrackets.class, Parenthesis.class, Function.class, Identifier.class, Operation.class);
        otherParam.put(OtherParamConstants.I, i);
        otherParam.put(OtherParamConstants.T, t);
        return new ImtFunction().invoke(token, otherParam);
    }

    @Override
    public Object validNext(Token token, Map<String, Object> otherParam) {
        List<TokenType> t = Arrays.asList(TokenType.NUMBER, TokenType.INTEGER, TokenType.FLOAT, TokenType.STRING, TokenType.STRINGSINGLE, TokenType.SYMBOL
                , TokenType.NAME, TokenType.PLACEHOLDER);
        List<Class> i = Arrays.asList(SquareBrackets.class, Parenthesis.class, Function.class, Identifier.class, Operation.class);
        otherParam.put(OtherParamConstants.I, i);
        otherParam.put(OtherParamConstants.T, t);
        return new ImtFunction().invoke(token, otherParam);
    }

    @Override
    public Object post(TokenList tlist, Map<String, Object> otherParam) {
        Integer tidx = (Integer) otherParam.get(OtherParamConstants.TIDX);
        List<Token> tokens = tlist.getTokens();
        tokens.get(tidx).setTtype(TokenType.OPERATOR);
        List<Integer> result = Arrays.asList((Integer) otherParam.get(OtherParamConstants.PIDX), (Integer) otherParam.get(OtherParamConstants.NIDX));
        return result;
    }
}
