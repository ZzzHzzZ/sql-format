package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 13:56
 */
public class ArraysGroupFunction implements GroupFunction {
    @Override
    public Object match(Token token, Map<String, Object> otherParam) {
        return token.getClass() == SquareBrackets.class;
    }

    @Override
    public Object validPrev(Token token, Map<String, Object> otherParam) {
        otherParam.put(OtherParamConstants.I, Arrays.asList(SquareBrackets.class, Identifier.class, Function.class));
        otherParam.put(OtherParamConstants.T, Arrays.asList(TokenType.NAME, TokenType.SYMBOL));
        return new ImtFunction().invoke(token, otherParam);
    }

    @Override
    public Object validNext(Token token, Map<String, Object> otherParam) {
        return true;
    }

    @Override
    public Object post(TokenList tlist, Map<String, Object> otherParam) {
        List<Integer> result = Arrays.asList((Integer) otherParam.get(OtherParamConstants.PIDX), (Integer) otherParam.get(OtherParamConstants.TIDX));
        return result;
    }
}
