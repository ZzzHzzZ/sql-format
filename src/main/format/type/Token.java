package format.type;


import format.constants.TokenType;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/17 10:47
 */
public class Token {
    protected boolean group = false;
    protected Token parent = null;
    protected TokenType ttype;
    protected String value;

    public Token() {
    }

    public Token(TokenType ttype, String value) {
        this.ttype = ttype;
        this.value = value;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public boolean isKeyword() {
        return ttype != null && ttype.getName().contains("Keyword");
    }

    public boolean isWhitespace() {
        return ttype != null && ttype.getName().contains("Whitespace");
    }

    public String getNormalized() {
        return isKeyword() ? value.toUpperCase() : value;
    }

    public Token getParent() {
        return parent;
    }

    public void setParent(Token parent) {
        this.parent = parent;
    }

    public TokenType getTtype() {
        return ttype;
    }

    public void setTtype(TokenType ttype) {
        this.ttype = ttype;
    }

    public String getValue() {
        return this.ttype == TokenType.KEYWORD ? value.toUpperCase() : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Token> flatten() {
        return Collections.singletonList(this);
    }

    @Override
    public String toString() {
        return this.value;
    }

    public Boolean match(TokenType ttype, List<String> values, Boolean regex) {
        /**
         * Checks whether the token matches the given arguments.
         *ttype* is a token type. If this token doesn't match the given token
         type.
         *values* is a list of possible values for this token. The values
         are OR'ed together so if only one of the values matches ``True``
         is returned. Except for keyword tokens the comparison is
         case-sensitive. For convenience it's OK to pass in a single string.
         If *regex* is ``True`` (default is ``False``) the given values are
         treated as regular expressions.
         */
        if (regex == null) {
            regex = false;
        }
        Boolean typeMatched = ttype.equals(this.ttype);
        if (!typeMatched || values == null || values.isEmpty()) {
            return typeMatched;
        }

        if (regex) {
            int flag = this.isKeyword() ? Pattern.CASE_INSENSITIVE : 0;
            for (String value : values) {
                Pattern p = Pattern.compile(value, flag);
                Matcher m = p.matcher(this.getNormalized());
                if (m.find()) {
                    return true;
                }
            }
            return false;
        }

        if (this.isKeyword()) {
            values = values.stream().map(String::toUpperCase).collect(Collectors.toList());
        }

        return values.contains(this.getNormalized());
    }

    public Boolean within(Class groupCls) {
        /**
         * Returns ``True`` if this token is within *group_cls*.
         *
         * Use this method for example to check if an identifier is within
         * a function: ``t.within(Function).
         */
        Token parent = this.parent;
        while (parent != null) {
            if (parent.getClass() == groupCls) {
                return true;
            }
            parent = parent.parent;
        }
        return false;
    }

    public Boolean isChildOf(Token other) {
        //Returns ``True`` if this token is a direct child of *other*.
        return this.parent == other;
    }

    public Boolean hasAncestor(Token other) {
        //Returns ``True`` if *other* is in this tokens ancestry.
        Token parent = this.parent;
        while (parent != null) {
            if (parent == other) {
                return true;
            }
            parent = parent.parent;
        }
        return false;
    }
}
