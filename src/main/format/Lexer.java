package format;


import format.constants.KeyWords;
import format.constants.TokenType;
import format.type.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/24 17:06
 */
public class Lexer {

    public static List<Token> generateList(String sql) {
        List<Token> result = new ArrayList<>();
        for (int i = 0; i < sql.length(); i++) {
            String matchSql = sql.substring(i);
            for (Map.Entry<Pattern, TokenType> entry : KeyWords.SQL_REGEX_MAP.entrySet()) {
                Pattern p = entry.getKey();
                Matcher m = p.matcher(matchSql);
                if(!m.lookingAt()){
                    continue;
                }
                TokenType ttype = entry.getValue();
                String matchString = m.group();
                if (ttype.equals(TokenType.FUNCTION)) {
                    ttype = KeyWords.isKeyword(matchString);
                }
                Token token = new Token();
                token.setValue(ttype.getName().contains("Keyword") ? matchString.toUpperCase() : matchString);
                token.setTtype(ttype);
                result.add(token);
                i = i + matchString.length() - 1;
                break;
            }
        }
        return result;
    }
}
