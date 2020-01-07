package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 14:00
 */
public class ComparisonGroupFunction implements GroupFunction {
    @Override
    public Object match(Token token, Map<String, Object> otherParam) {
        return token.getTtype() == TokenType.COMPARISON;
    }

    @Override
    public Object validPrev(Token token, Map<String, Object> otherParam) {
        List<TokenType> t = Arrays.asList(TokenType.NUMBER, TokenType.INTEGER, TokenType.FLOAT, TokenType.STRING, TokenType.STRINGSINGLE, TokenType.SYMBOL
                , TokenType.NAME, TokenType.PLACEHOLDER);
        List<Class> i = Arrays.asList(Parenthesis.class, Function.class, Identifier.class, Operation.class);
        otherParam.put(OtherParamConstants.I, i);
        otherParam.put(OtherParamConstants.T, t);
        if ((Boolean) new ImtFunction().invoke(token, otherParam)) {
            return true;
        } else if (token != null && token.isKeyword() && token.getNormalized().equals("NULL")) {
            return true;
        }
        return false;
    }

    @Override
    public Object validNext(Token token, Map<String, Object> otherParam) {
        List<TokenType> t = Arrays.asList(TokenType.NUMBER, TokenType.INTEGER, TokenType.FLOAT, TokenType.STRING, TokenType.STRINGSINGLE, TokenType.SYMBOL
                , TokenType.NAME, TokenType.PLACEHOLDER);
        List<Class> i = Arrays.asList(Parenthesis.class, Function.class, Identifier.class, Operation.class);
        otherParam.put(OtherParamConstants.I, i);
        otherParam.put(OtherParamConstants.T, t);
        if ((Boolean) new ImtFunction().invoke(token, otherParam)) {
            return true;
        } else if (token != null && token.isKeyword() && token.getNormalized().equals("NULL")) {
            return true;
        }
        return false;
    }

    @Override
    public Object post(TokenList tlist, Map<String, Object> otherParam) {
        List<Integer> result = Arrays.asList((Integer) otherParam.get(OtherParamConstants.PIDX), (Integer) otherParam.get(OtherParamConstants.NIDX));
        return result;
    }
}
