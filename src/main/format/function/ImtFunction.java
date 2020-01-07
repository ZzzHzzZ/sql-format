package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.Token;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/18 15:33
 */
public class ImtFunction implements MyFunction {

    /**
     * Helper function to simplify comparisons Instance, Match and TokenType
     * :param token:
     * :param i: Class or Tuple/List of Classes
     * :param m: Tuple of TokenType & Value. Can be list of Tuple for multiple
     * :param t: TokenType or Tuple/List of TokenTypes
     * :return:  bool
     */
    @Override
    public Object invoke(Token token, Map<String, Object> otherParam) {
        if (token == null) {
            return false;
        }
        if (otherParam.get(OtherParamConstants.I) != null) {
            List<Class> i = (List) otherParam.get(OtherParamConstants.I);
            for (Class aClass : i) {
                if (token.getClass() == aClass) {
                    return true;
                }
            }
        }
        if (otherParam.get(OtherParamConstants.M) != null) {
            boolean regex = false;
            Map<List<String>, TokenType> m = (Map) otherParam.get(OtherParamConstants.M);
            if (m.get(null) != null) {
                //regex is true
                regex = true;
            }
            for (Map.Entry<List<String>, TokenType> entry : m.entrySet()) {
                if (entry.getKey() == null) {
                    //regex is true
                    continue;
                }
                if (token.match(entry.getValue(), entry.getKey(), regex)) {
                    return true;
                }
            }
        }
        if (otherParam.get(OtherParamConstants.T) != null) {
            List<TokenType> t = (List) otherParam.get(OtherParamConstants.T);
            TokenType ttype = token.getTtype();
            if (ttype != null) {
                for (TokenType tokenType : t) {
                    if (ttype == tokenType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
