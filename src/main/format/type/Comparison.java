package format.type;


import java.util.List;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/23 21:11
 */
public class Comparison extends TokenList {
    public Comparison() {
    }

    public Comparison(List<Token> tokens) {
        super(tokens);
    }

    public Token left() {
        return this.tokens.get(0);
    }

    public Token right() {
        return this.tokens.get(this.tokens.size()-1);
    }
}
