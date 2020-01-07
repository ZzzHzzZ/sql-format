package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.Comment;
import format.type.Token;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/18 16:56
 */
public class TokenNextFunction implements MyFunction {

    @Override
    public Object invoke(Token token, Map<String, Object> otherParam) {
        if (otherParam.get(OtherParamConstants.SKIP_WS) != null && (boolean) otherParam.get(OtherParamConstants.SKIP_WS)
                && token.isWhitespace()) {
            return false;
        }
        if (otherParam.get(OtherParamConstants.SKIP_CM) != null && (boolean) otherParam.get(OtherParamConstants.SKIP_CM)) {
            Map<String, Object> tempMap = new HashMap<>(otherParam);
            tempMap.put(OtherParamConstants.T, Collections.singletonList(TokenType.COMMENT));
            tempMap.put(OtherParamConstants.I, Collections.singletonList(Comment.class));
            if ((boolean) new ImtFunction().invoke(token, tempMap)) {
                return false;
            }
        }
        return true;
    }
}
