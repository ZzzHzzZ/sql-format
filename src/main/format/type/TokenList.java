package format.type;

import format.Utils;
import format.constants.ClassConstants;
import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.function.ImtFunction;
import format.function.MyFunction;
import format.function.TokenNextFunction;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/18 18:55
 * A group of tokens.
 * <p>
 * It has an additional instance attribute ``tokens`` which holds a
 * list of child-tokens.
 */
public class TokenList extends Token {

    protected List<Token> tokens;

    public TokenList() {
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public TokenList(List<Token> tokens) {
        this.tokens = new ArrayList<>();
        if (tokens != null) {
            this.tokens.addAll(tokens);
            for (Token token : tokens) {
                token.setParent(this);
            }
        }
        this.group = true;
        this.value = this.toString();
    }

    @Override
    public String toString() {
        List<Token> token = this.flatten();
        StringBuilder sb = new StringBuilder();
        for (Token token1 : token) {
            sb.append(token1.getValue());
        }
        return sb.toString();
    }

    @Override
    public List<Token> flatten() {
        //This method is recursively called for all child tokens.
        List<Token> result = new ArrayList<>();
        for (Token token : tokens) {
            if (token.isGroup()) {
                result.addAll(token.flatten());
            } else {
                result.add(token);
            }
        }
        return result;
    }

    public List<Token> getSublist() {
        List<Token> result = new ArrayList<>();
        for (Token token : this.tokens) {
            if (token.isGroup()) {
                result.add(token);
            }
        }
        return result;
    }

    private List<Object> tokenMatching(List<MyFunction> myFunctions, Map<String, Object> otherParam, boolean isMatch) {
        int start;
        if (otherParam.get(OtherParamConstants.START) == null) {
            start = 0;
        } else if (otherParam.get(OtherParamConstants.START).equals(OtherParamConstants.NULL)) {
            return null;
        } else {
            start = (Integer) otherParam.get(OtherParamConstants.START);
        }

        if (otherParam.get(OtherParamConstants.REVERSE) != null && (boolean) otherParam.get(OtherParamConstants.REVERSE)) {
            List<Integer> reverseList = new ArrayList<>();
            for (int i = 0; i < start - 1; i++) {
                reverseList.add(i);
            }
            Collections.reverse(reverseList);
            for (Integer idx : reverseList) {
                Token token = this.tokens.get(idx);
                for (MyFunction myFunction : myFunctions) {
                    if ((boolean) myFunction.invoke(token, otherParam) == isMatch) {
                        List<Object> result = new ArrayList<>();
                        result.add(idx);
                        result.add(token);
                        return result;
                    }
                }
            }
        } else {
            Integer end;
            if (otherParam.get(OtherParamConstants.END) != null) {
                end = (Integer) otherParam.get(OtherParamConstants.END);
            } else {
                end = this.tokens.size();
            }
            for (int idx = start; idx < end; idx++) {
                Token token = this.tokens.get(idx);
                for (MyFunction myFunction : myFunctions) {
                    if ((boolean) myFunction.invoke(token, otherParam) == isMatch) {
                        List<Object> result = new ArrayList<>();
                        result.add(idx);
                        result.add(token);
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public Object tokenFirst(Boolean skipWs, Boolean skipCm) {
        /**
         * Returns the first child token.
         *
         * If *skip_ws* is ``True`` (the default), whitespace
         * tokens are ignored.
         *
         * if *skip_cm* is ``True`` (default: ``False``), comments are
         * ignored too.
         * */
        if (skipWs == null) {
            skipWs = true;
        }
        if (skipCm == null) {
            skipCm = false;
        }
        Map<String, Object> otherParam = new HashMap<>();
        otherParam.put(OtherParamConstants.SKIP_WS, skipWs);
        otherParam.put(OtherParamConstants.SKIP_CM, skipCm);
        List<MyFunction> funs = new ArrayList<>();
        MyFunction tokenNextFunction = new TokenNextFunction();
        funs.add(tokenNextFunction);
        List<Object> result = this.tokenMatching(funs, otherParam, true);
        if (result != null) {
            return result.get(1);
        }
        return null;
    }

    public List<Object> tokenNextBy(List<Class> i, Map<List<String>, TokenType> m, List<TokenType> t, Integer idx, Integer end) {
        Map<String, Object> otherParam = new HashMap<>();
        otherParam.put(OtherParamConstants.I, i);
        otherParam.put(OtherParamConstants.M, m);
        otherParam.put(OtherParamConstants.T, t);
        otherParam.put(OtherParamConstants.END, end);
        if (idx == null) {
            idx = -1;
        }
        idx += 1;
        otherParam.put(OtherParamConstants.START, idx);
        otherParam.put(OtherParamConstants.REVERSE, false);
        MyFunction imtFunction = new ImtFunction();
        List<MyFunction> funs = new ArrayList<>();
        funs.add(imtFunction);
        return this.tokenMatching(funs, otherParam, true);
    }

    public List<Object> tokenNotMatching(List<MyFunction> myFunctions, Integer idx) {
        Map<String, Object> otherParam = new HashMap<>();
        otherParam.put(OtherParamConstants.START, idx);
        otherParam.put(OtherParamConstants.IS_WHITESPACE, true);
        return this.tokenMatching(myFunctions, otherParam, false);
    }

    public List<Token> groupableTokens() {
        return this.tokens;
    }

    public Object tokenMatching(List<MyFunction> funcs, Integer idx) {
        Map<String, Object> otherParam = new HashMap<>();
        otherParam.put(OtherParamConstants.START, idx);
        List<Object> result = this.tokenMatching(funcs, otherParam, true);
        if (result != null) {
            return result.get(1);
        }
        return null;
    }

    public List<Object> tokenPrev(Integer idx, Boolean skipWs, Boolean skipCm) {
        if (skipWs == null) {
            skipWs = true;
        }
        if (skipCm == null) {
            skipCm = false;
        }
        return this.tokenNext(idx, skipWs, skipCm, true);
    }

    public List<Object> tokenNext(Integer idx, Boolean skipWs, Boolean skipCm, Boolean reverse) {
        if (skipWs == null) {
            skipWs = true;
        }
        if (skipCm == null) {
            skipCm = false;
        }
        if (reverse == null) {
            reverse = false;
        }
        if (idx == null) {
            return null;
        }
        idx += 1;
        Map<String, Object> otherParam = new HashMap<>();
        otherParam.put(OtherParamConstants.SKIP_WS, skipWs);
        otherParam.put(OtherParamConstants.SKIP_CM, skipCm);
        otherParam.put(OtherParamConstants.START, idx);
        otherParam.put(OtherParamConstants.REVERSE, reverse);
        return this.tokenMatching(Collections.singletonList(new TokenNextFunction()), otherParam, true);
    }

    public Integer tokenIndex(Token token, Integer start) {
        if (start == null) {
            start = 0;
        }
        List<Token> tokens = this.tokens.subList(start, this.tokens.size());
        return start + tokens.indexOf(token);
    }

    public Token groupTokens(Class grpCls, Integer start, Integer end, Boolean includeEnd, Boolean extend) {
        if (includeEnd == null) {
            includeEnd = true;
        }
        if (extend == null) {
            extend = false;
        }
        Token startToken = this.tokens.get(start);
        end = includeEnd ? end + 1 : end;
        List<Token> subtokens;
        TokenList grp;
        if (extend && startToken.getClass() == grpCls) {
            subtokens = this.tokens.subList(start + 1, end);
            grp = (TokenList) startToken;
            grp.getTokens().addAll(subtokens);
            List<Token> thisToken = new ArrayList<>(this.tokens.subList(0, start + 1));
            thisToken.addAll(this.tokens.subList(end, this.tokens.size()));
            this.tokens = thisToken;
            grp.setValue(grp.toString());
        } else {
            subtokens = this.tokens.subList(start, end);
            String className = grpCls.getName();
            grp = null;
            if (className.contains(ClassConstants.ASSIGNMENT)) {
                grp = new Assignment(subtokens);
            } else if (className.contains(ClassConstants.BEGIN)) {
                grp = new Begin(subtokens);
            } else if (className.contains(ClassConstants.CASE)) {
                grp = new Case(subtokens);
            } else if (className.contains(ClassConstants.COMMAND)) {
                grp = new Command(subtokens);
            } else if (className.contains(ClassConstants.COMMENT)) {
                grp = new Comment(subtokens);
            } else if (className.contains(ClassConstants.COMPARISON)) {
                grp = new Comparison(subtokens);
            } else if (className.contains(ClassConstants.FOR)) {
                grp = new For(subtokens);
            } else if (className.contains(ClassConstants.FUNCTION)) {
                grp = new Function(subtokens);
            } else if (className.contains(ClassConstants.HAVING)) {
                grp = new Having(subtokens);
            } else if (className.contains(ClassConstants.IDENTIFIER_LIST)) {
                grp = new IdentifierList(subtokens);
            } else if (className.contains(ClassConstants.IDENTIFIER)) {
                grp = new Identifier(subtokens);
            } else if (className.contains(ClassConstants.IF)) {
                grp = new If(subtokens);
            } else if (className.contains(ClassConstants.OPERATION)) {
                grp = new Operation(subtokens);
            } else if (className.contains(ClassConstants.PARENTHESIS)) {
                grp = new Parenthesis(subtokens);
            } else if (className.contains(ClassConstants.SQUARE_BRACKETS)) {
                grp = new SquareBrackets(subtokens);
            } else if (className.contains(ClassConstants.STATEMENT)) {
                grp = new Statement(subtokens);
            } else if (className.contains(ClassConstants.VALUES)) {
                grp = new Values(subtokens);
            } else if (className.contains(ClassConstants.WHERE)) {
                grp = new Where(subtokens);
            }
            List<Token> thisToken = new ArrayList<>(this.tokens.subList(0, start));
            thisToken.add(grp);
            thisToken.addAll(this.tokens.subList(end, this.tokens.size()));
            this.tokens = thisToken;
            grp.setParent(this);
        }
        for (Token token : subtokens) {
            token.setParent(grp);
        }

        return grp;
    }

    public void insertBefore(Object where, Token token) {
        //Inserts *token* before *where*.
        Integer whereInt;
        if (!(where instanceof Integer)) {
            whereInt = this.tokenIndex((Token) where, null);
        } else {
            whereInt = (Integer) where;
        }
        token.setParent(this);
        this.tokens.add(whereInt, token);
    }

    public void insertAfter(Object where, Token token, Boolean skipWs) {
        if (skipWs == null) {
            skipWs = true;
        }
        Integer whereInt;
        if (!(where instanceof Integer)) {
            whereInt = this.tokenIndex((Token) where, null);
        } else {
            whereInt = (Integer) where;
        }
        List<Object> tokenNext = this.tokenNext(whereInt, skipWs, null, null);
        token.setParent(this);
        if (tokenNext == null) {
            this.tokens.add(token);
        } else {
            this.tokens.add((Integer) tokenNext.get(0), token);
        }
    }

    public Boolean hasAlias() {
        //Returns ``True`` if an alias is present.
        return this.getAlias() != null;
    }

    public String getAlias() {
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Collections.singletonList("AS"), TokenType.KEYWORD);
        List<Object> tokenNextBy = this.tokenNextBy(null, m, null, null, null);
        if (tokenNextBy != null) {
            return this.getFirstName((Integer) tokenNextBy.get(0) + 1, null, true, null);
        }
        tokenNextBy = this.tokenNextBy(null, null, Collections.singletonList(TokenType.WHITESPACE), null, null);
        if (this.tokens.size() > 2 && tokenNextBy != null) {
            return this.getFirstName(null, true, null, null);
        }

        return null;
    }


    public String getName() {
        /**
         * Returns the name of this identifier.
         *
         * This is either it's alias or it's real name. The returned valued can
         * be considered as the name under which the object corresponding to
         * this identifier is known within the current statement.
         * */
        String result = this.getAlias();
        if (result != null) {
            return result;
        }
        return this.getRealName();
    }

    public String getParentName() {
        /**
         * Return name of the parent object if any.
         *
         * A parent object is identified by the first occurring dot.
         * */
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Collections.singletonList("."), TokenType.PUNCTUATION);
        List<Object> tokenNextBy = this.tokenNextBy(null, m, null, null, null);
        List<Object> tokenPrev = this.tokenPrev((Integer) tokenNextBy.get(0), null, null);
        if (tokenPrev != null) {
            return Utils.removeQuotes(((Token) tokenPrev.get(1)).getValue());
        } else {
            return null;
        }
    }

    public String getRealName() {
        //Returns the real name (object name) of this identifier.
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Collections.singletonList("."), TokenType.PUNCTUATION);
        List<Object> tokenNextBy = this.tokenNextBy(null, m, null, null, null);
        return this.getFirstName((Integer) tokenNextBy.get(0), null, null, true);
    }

    private String getFirstName(Integer idx, Boolean reverse, Boolean keywords, Boolean realName) {
        if (reverse == null) {
            reverse = false;
        }
        if (keywords == null) {
            keywords = false;
        }
        if (realName == null) {
            realName = false;
        }
        List<Token> tokens = idx == null ? this.tokens : this.tokens.subList(idx, this.tokens.size());
        if (reverse) {
            Collections.reverse(tokens);
        }
        List<TokenType> types = new ArrayList<>();
        types.add(TokenType.NAME);
        types.add(TokenType.WILDCARD);
        types.add(TokenType.SYMBOL);
        if (keywords) {
            types.add(TokenType.KEYWORD);
        }

        for (Token token : tokens) {
            if (types.contains(token.getTtype())) {
                return Utils.removeQuotes(token.getValue());
            } else {
                if (token instanceof Function || token instanceof Identifier) {
                    TokenList tkList = (TokenList) token;
                    if (realName) {
                        return tkList.getRealName();
                    } else {
                        return tkList.getName();
                    }
                }
            }
        }
        return null;
    }
}
