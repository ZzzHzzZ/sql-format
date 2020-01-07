package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.Token;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/24 17:28
 */
public class GroupCommentsFunction implements MyFunction {

    @Override
    public Object invoke(Token token, Map<String, Object> otherParam) {
        if (token.isWhitespace()) {
            return true;
        }
        Map<String, Object> tempMap = new HashMap<>(otherParam);
        tempMap.put(OtherParamConstants.T, Collections.singletonList(TokenType.COMMENT));
        if ((boolean) new ImtFunction().invoke(token, tempMap)) {
            return true;
        }
        return false;
    }
}
