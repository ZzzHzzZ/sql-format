package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.Token;
import format.type.TokenList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 14:01
 */
public class AsGroupFunction implements GroupFunction {
    @Override
    public Object match(Token token, Map<String, Object> otherParam) {
        return token.isKeyword() && token.getNormalized().equals("AS");
    }

    @Override
    public Object validPrev(Token token, Map<String, Object> otherParam) {
        return token.getNormalized().equals("NULL") || !token.isKeyword();
    }

    @Override
    public Object validNext(Token token, Map<String, Object> otherParam) {
        if (token == null) {
            return false;
        }
        otherParam.put(OtherParamConstants.T, Arrays.asList(TokenType.DML, TokenType.DDL));
        Boolean imtBool = (Boolean) new ImtFunction().invoke(token, otherParam);
        return !imtBool;
    }

    @Override
    public Object post(TokenList tlist, Map<String, Object> otherParam) {
        List<Integer> result = Arrays.asList((Integer) otherParam.get(OtherParamConstants.PIDX), (Integer) otherParam.get(OtherParamConstants.NIDX));
        return result;
    }
}
