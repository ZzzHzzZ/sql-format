package format.function;


import format.type.Token;

import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/18 15:26
 */
public interface MyFunction {

    Object invoke(Token token, Map<String, Object> otherParam);
}
