package format;

import format.type.Token;
import format.type.TokenList;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 16:52
 */
public class Utils {

    public static String removeQuotes(String val) {
        //Helper that removes surrounding quotes from strings.
        if (val == null || val.isEmpty()) {
            return val;
        }
        List<Character> quotes = Arrays.asList('\'', '"');
        if (quotes.contains(val.charAt(0)) && val.charAt(0) == val.charAt(val.length() - 1)) {
            val = val.substring(1, val.length() - 1);
        }
        return val;
    }

    public static void recurseClass(Class cls, TokenList tlist, List<TokenList> executeToken) {
        List<Token> subList = tlist.getSublist();
        for (Token token : subList) {
            if (token.getClass() != cls) {
                recurseClass(cls, (TokenList) token, executeToken);
            }
        }
        executeToken.add(tlist);
    }
}