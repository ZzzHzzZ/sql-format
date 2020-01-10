package format;

import format.type.ReindentFilter;
import format.type.Statement;
import format.type.Token;

import java.util.List;

/**
 * @Author: zhanghaozhe
 * @Date: 2020/1/7 17:22
 */
public class Example {

    public static void main(String[] args) {
        String sql = "SELECT b, s, y, sum(y) OVER (PARTITION BY s) AS s_g, sum(y) OVER (PARTITION BY b) AS b_c FROM test.zzz111 LIMIT 10";
        List<Token> tokenUnits = Lexer.generateList(sql);
        Statement statement = new StatementSplitter().process(tokenUnits);
        Grouping.group(statement);
        StripWhitespaceFilter.process(statement, null);
        new ReindentFilter().reindent(statement);
        System.out.println(statement.toString());
    }
}
