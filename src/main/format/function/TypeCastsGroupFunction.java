package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.Token;
import format.type.TokenList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 13:58
 */
public class TypeCastsGroupFunction implements GroupFunction {
    @Override
    public Object match(Token token, Map<String, Object> otherParam) {
        return token.match(TokenType.PUNCTUATION, Collections.singletonList("::"), null);
    }

    @Override
    public Object validPrev(Token token, Map<String, Object> otherParam) {
        return token != null;
    }

    @Override
    public Object validNext(Token token, Map<String, Object> otherParam) {
        return token != null;
    }

    @Override
    public Object post(TokenList tlist, Map<String, Object> otherParam) {
        List<Integer> result = Arrays.asList((Integer) otherParam.get(OtherParamConstants.PIDX), (Integer) otherParam.get(OtherParamConstants.NIDX));
        return result;
    }
}
