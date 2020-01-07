package format.function;

import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.type.Token;
import format.type.TokenList;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/25 14:13
 */
public class AssignmentGroupFunction implements GroupFunction {
    @Override
    public Object match(Token token, Map<String, Object> otherParam) {
        return token.match(TokenType.ASSIGNMENT, Collections.singletonList(":="), null);
    }

    @Override
    public Object validPrev(Token token, Map<String, Object> otherParam) {
        return token != null && !token.getTtype().getName().contains(TokenType.KEYWORD.getName());
    }

    @Override
    public Object validNext(Token token, Map<String, Object> otherParam) {
        return token != null && !token.getTtype().getName().contains(TokenType.KEYWORD.getName());
    }

    @Override
    public Object post(TokenList tlist, Map<String, Object> otherParam) {
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Collections.singletonList(";"), TokenType.PUNCTUATION);
        Integer nidx = (Integer) otherParam.get(OtherParamConstants.NIDX);
        List<Object> tokenNextBy = tlist.tokenNextBy(null, m, null, nidx, null);
        if (tokenNextBy != null) {
            nidx = (Integer) tokenNextBy.get(0);
        }
        List<Integer> result = Arrays.asList((Integer) otherParam.get(OtherParamConstants.PIDX), nidx);
        return result;
    }
}
