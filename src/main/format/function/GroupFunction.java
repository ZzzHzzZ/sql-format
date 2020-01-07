package format.function;

import format.type.Token;
import format.type.TokenList;

import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 13:50
 */
public interface GroupFunction {
    Object match(Token token, Map<String, Object> otherParam);

    Object validPrev(Token token, Map<String, Object> otherParam);

    Object validNext(Token token, Map<String, Object> otherParam);

    Object post(TokenList tlist, Map<String, Object> otherParam);
}

