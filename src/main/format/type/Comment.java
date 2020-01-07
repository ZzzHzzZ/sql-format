package format.type;

import format.constants.TokenType;

import java.util.List;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/18 17:05
 */
public class Comment extends TokenList {

    public Comment() {
    }

    public Comment(List<Token> tokens) {
        super(tokens);
    }

    public Boolean isMultiline() {
        return this.tokens != null && TokenType.MULTILINE.equals(this.tokens.get(0).getTtype());
    }
}
