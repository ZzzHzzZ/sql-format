package format;

import format.constants.ClassConstants;
import format.constants.OtherParamConstants;
import format.constants.TokenType;
import format.function.*;
import format.type.*;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/18 11:21
 */
public class Grouping {

    public static void group(Statement stmt) {
        groupCommentsWrap(stmt);

        groupBrackets(stmt);
        groupParenthesis(stmt);
        groupCase(stmt);
        groupIf(stmt);
        groupFor(stmt);
        groupBegin(stmt);

        groupFunctionsWrap(stmt);
        groupWhereWrap(stmt);
        groupPeriod(stmt);
        groupArrays(stmt);
        groupIdentifierWrap(stmt);
        groupOrder(stmt);
        groupTypeCasts(stmt);
        groupOperator(stmt);
        groupComparison(stmt);
        groupAs(stmt);
        groupAliasedWrap(stmt);
        groupAssignment(stmt);

        alignCommentsWrap(stmt);
        groupIdentifierList(stmt);
        groupValues(stmt);
    }

    private static void groupCommentsWrap(TokenList tlist) {
        List<TokenList> executeList = new ArrayList<>();
        Utils.recurseClass(Comment.class, tlist, executeList);
        for (TokenList tokenList : executeList) {
            groupComments(tokenList);
        }
    }

    private static void groupComments(TokenList tlist) {
        List<Object> tokenNextBy = tlist.tokenNextBy(null, null, Collections.singletonList(TokenType.COMMENT), null, null);
        while (tokenNextBy != null) {
            List<MyFunction> funs = new ArrayList<>();
            funs.add(new GroupCommentsFunction());
            List<Object> tokenNotMatching = tlist.tokenNotMatching(funs, (Integer) tokenNextBy.get(0));
            if (tokenNotMatching != null) {
                List<Object> tokenPrev = tlist.tokenPrev((Integer) tokenNotMatching.get(0), false, null);
                tlist.groupTokens(Comment.class, (Integer) tokenNextBy.get(0), (Integer) tokenPrev.get(0), null, null);
            }
            tokenNextBy = tlist.tokenNextBy(null, null, Collections.singletonList(TokenType.COMMENT), (Integer) tokenNextBy.get(0), null);
        }
    }

    private static void groupMatching(TokenList tlist, Class cls) {
        List<Integer> opens = new ArrayList<>();
        int tidxOffset = 0;
        List<Token> tokens = tlist.getTokens();
        for (int i = 0; i < tokens.size(); i++) {
            Integer tidx = i - tidxOffset;
            Token token = tokens.get(i);
            if (token.isWhitespace()) {
                continue;
            }

            if (token.isGroup() && token.getClass() != cls) {
                groupMatching((TokenList) token, cls);
                continue;
            }

            Map<List<String>, TokenType> M_OPEN = null;
            if (cls.getName().contains(ClassConstants.PARENTHESIS)) {
                M_OPEN = Parenthesis.M_OPEN;
            } else if (cls.getName().contains(ClassConstants.SQUARE_BRACKETS)) {
                M_OPEN = SquareBrackets.M_OPEN;
            } else if (cls.getName().contains(ClassConstants.IF)) {
                M_OPEN = If.M_OPEN;
            } else if (cls.getName().contains(ClassConstants.FOR)) {
                M_OPEN = For.M_OPEN;
            } else if (cls.getName().contains(ClassConstants.WHERE)) {
                M_OPEN = Where.M_OPEN;
            } else if (cls.getName().contains(ClassConstants.HAVING)) {
                M_OPEN = Having.M_OPEN;
            } else if (cls.getName().contains(ClassConstants.CASE)) {
                M_OPEN = Case.M_OPEN;
            } else if (cls.getName().contains(ClassConstants.BEGIN)) {
                M_OPEN = Begin.M_OPEN;
            }
            List<String> oValues = null;
            TokenType oTtype = null;
            for (Map.Entry<List<String>, TokenType> entry : M_OPEN.entrySet()) {
                //只有一个
                oValues = entry.getKey();
                oTtype = entry.getValue();
            }
            Map<List<String>, TokenType> M_CLOSE = null;
            if (cls.getName().contains(ClassConstants.PARENTHESIS)) {
                M_CLOSE = Parenthesis.M_CLOSE;
            } else if (cls.getName().contains(ClassConstants.SQUARE_BRACKETS)) {
                M_CLOSE = SquareBrackets.M_CLOSE;
            } else if (cls.getName().contains(ClassConstants.IF)) {
                M_CLOSE = If.M_CLOSE;
            } else if (cls.getName().contains(ClassConstants.FOR)) {
                M_CLOSE = For.M_CLOSE;
            } else if (cls.getName().contains(ClassConstants.WHERE)) {
                M_CLOSE = Where.M_CLOSE;
            } else if (cls.getName().contains(ClassConstants.HAVING)) {
                M_CLOSE = Having.M_CLOSE;
            } else if (cls.getName().contains(ClassConstants.CASE)) {
                M_CLOSE = Case.M_CLOSE;
            } else if (cls.getName().contains(ClassConstants.BEGIN)) {
                M_CLOSE = Begin.M_CLOSE;
            }
            List<String> cValues = null;
            TokenType cTtype = null;
            for (Map.Entry<List<String>, TokenType> entry : M_CLOSE.entrySet()) {
                //只有一个
                cValues = entry.getKey();
                cTtype = entry.getValue();
            }
            if (token.match(oTtype, oValues, null)) {
                opens.add(tidx);
            } else if (token.match(cTtype, cValues, null)) {
                Integer openIdx;
                try {
                    openIdx = opens.remove(opens.size() - 1);
                } catch (Exception e) {
                    continue;
                }
                tlist.groupTokens(cls, openIdx, tidx, null, null);
                tidxOffset += (tidx - openIdx);
            }
        }
    }

    private static void groupBrackets(TokenList tlist) {
        groupMatching(tlist, SquareBrackets.class);
    }

    private static void groupParenthesis(TokenList tlist) {
        groupMatching(tlist, Parenthesis.class);
    }

    private static void groupCase(TokenList tlist) {
        groupMatching(tlist, Case.class);
    }

    private static void groupIf(TokenList tlist) {
        groupMatching(tlist, If.class);
    }

    private static void groupFor(TokenList tlist) {
        groupMatching(tlist, For.class);
    }

    private static void groupBegin(TokenList tlist) {
        groupMatching(tlist, Begin.class);
    }

    private static void groupFunctionsWrap(TokenList tlist) {
        List<TokenList> executeList = new ArrayList<>();
        Utils.recurseClass(Function.class, tlist, executeList);
        for (TokenList tokenList : executeList) {
            groupFunctions(tokenList);
        }
    }

    private static void groupFunctions(TokenList tlist) {
        Boolean hasCreate = false;
        Boolean hasTable = false;
        List<Token> tokens = tlist.getTokens();
        for (Token token : tokens) {
            if (token.getValue().equals("CREATE")) {
                hasCreate = true;
            }
            if (token.getValue().equals("TABLE")) {
                hasTable = true;
            }
        }
        if (hasCreate && hasTable) {
            return;
        }

        List<Object> tokenNextBy = tlist.tokenNextBy(null, null, Collections.singletonList(TokenType.NAME), null, null);
        while (tokenNextBy != null) {
            List<Object> tokenNext = tlist.tokenNext((Integer) tokenNextBy.get(0), null, null, null);
            if (tokenNext != null) {
                if (tokenNext.get(1).getClass() == Parenthesis.class) {
                    tlist.groupTokens(Function.class, (Integer) tokenNextBy.get(0), (Integer) tokenNext.get(0), null, null);
                }
            }
            tokenNextBy = tlist.tokenNextBy(null, null, Collections.singletonList(TokenType.NAME), (Integer) tokenNextBy.get(0), null);
        }
    }

    private static void groupWhereWrap(TokenList tlist) {
        List<TokenList> executeList = new ArrayList<>();
        Utils.recurseClass(Where.class, tlist, executeList);
        for (TokenList tokenList : executeList) {
            groupWhere(tokenList);
        }
    }

    private static void groupWhere(TokenList tlist) {
        List<Object> tokenNextBy = tlist.tokenNextBy(null, new HashMap<>(Where.M_OPEN), null, null, null);
        while (tokenNextBy != null) {
            List<Object> end = tlist.tokenNextBy(null, new HashMap<>(Where.M_CLOSE), null, (Integer) tokenNextBy.get(0), null);
            Token endToken;
            if (end == null) {
                endToken = tlist.groupableTokens().get(tlist.groupableTokens().size() - 1);
            } else {
                endToken = tlist.getTokens().get((Integer) end.get(0) - 1);
            }
            Integer eidx = tlist.tokenIndex(endToken, null);
            tlist.groupTokens(Where.class, (Integer) tokenNextBy.get(0), eidx, null, null);
            tokenNextBy = tlist.tokenNextBy(null, new HashMap<>(Where.M_OPEN), null, (Integer) tokenNextBy.get(0), null);
        }
    }

    private static void groupPeriod(TokenList tlist) {
        group(tlist, Identifier.class, new PeriodGroupFunction(), null, null);
    }

    private static void groupArrays(TokenList tlist) {
        group(tlist, Identifier.class, new ArraysGroupFunction(), true, false);
    }

    private static void groupIdentifierWrap(TokenList tlist) {
        List<TokenList> executeList = new ArrayList<>();
        Utils.recurseClass(Identifier.class, tlist, executeList);
        for (TokenList tokenList : executeList) {
            groupIdentifier(tokenList);
        }
    }

    private static void groupIdentifier(TokenList tlist) {
        List<TokenType> t = Arrays.asList(TokenType.SYMBOL, TokenType.NAME);
        List<Object> tokenNextBy = tlist.tokenNextBy(null, null, t, null, null);
        while (tokenNextBy != null) {
            tlist.groupTokens(Identifier.class, (Integer) tokenNextBy.get(0), (Integer) tokenNextBy.get(0), null, null);
            tokenNextBy = tlist.tokenNextBy(null, null, t, (Integer) tokenNextBy.get(0), null);
        }
    }

    private static void groupOrder(TokenList tlist) {
        List<TokenType> t = Arrays.asList(TokenType.ORDER);
        List<Object> tokenNextBy = tlist.tokenNextBy(null, null, t, null, null);
        while (tokenNextBy != null) {
            Integer tidx = (Integer) tokenNextBy.get(0);
            List<Object> tokenPrev = tlist.tokenPrev(tidx, null, null);
            Map<String, Object> otherParam = new HashMap<>();
            otherParam.put(OtherParamConstants.I, Collections.singletonList(Identifier.class));
            otherParam.put(OtherParamConstants.T, Collections.singletonList(TokenType.NUMBER));
            if ((Boolean) new ImtFunction().invoke((Token) tokenPrev.get(1), otherParam)) {
                tlist.groupTokens(Identifier.class, (Integer) tokenPrev.get(0), tidx, null, null);
                tidx = (Integer) tokenPrev.get(0);
            }
            tokenNextBy = tlist.tokenNextBy(null, null, t, tidx, null);
        }
    }

    private static void groupTypeCasts(TokenList tlist) {
        group(tlist, Identifier.class, new TypeCastsGroupFunction(), true, false);
    }

    private static void groupOperator(TokenList tlist) {
        group(tlist, Operation.class, new OperatorGroupFunction(), false, null);
    }

    private static void groupComparison(TokenList tlist) {
        group(tlist, Comparison.class, new ComparisonGroupFunction(), false, null);
    }

    private static void groupAs(TokenList tlist) {
        group(tlist, Identifier.class, new AsGroupFunction(), null, null);
    }

    private static void groupAliasedWrap(TokenList tlist) {
        List<TokenList> executeList = new ArrayList<>();
        Utils.recurseClass(null, tlist, executeList);
        for (TokenList tokenList : executeList) {
            groupAliased(tokenList);
        }
    }

    private static void groupAliased(TokenList tlist) {
        List<Class> i = Arrays.asList(Parenthesis.class, Function.class, Case.class, Identifier.class, Operation.class, Comparison.class);
        List<Object> tokenNextBy = tlist.tokenNextBy(i, null, Collections.singletonList(TokenType.NUMBER), null, null);
        while (tokenNextBy != null) {
            List<Object> tokenNext = tlist.tokenNext((Integer) tokenNextBy.get(0), null, null, null);
            if (tokenNext != null) {
                if (tokenNext.get(1).getClass() == Identifier.class) {
                    tlist.groupTokens(Identifier.class, (Integer) tokenNextBy.get(0), (Integer) tokenNext.get(0), null, true);
                }
            }
            tokenNextBy = tlist.tokenNextBy(i, null, Collections.singletonList(TokenType.NUMBER), (Integer) tokenNextBy.get(0), null);
        }
    }

    private static void groupAssignment(TokenList tlist) {
        group(tlist, Assignment.class, new AssignmentGroupFunction(), null, null);
    }

    private static void alignCommentsWrap(TokenList tlist) {
        List<TokenList> executeList = new ArrayList<>();
        Utils.recurseClass(null, tlist, executeList);
        for (TokenList tokenList : executeList) {
            alignComments(tokenList);
        }
    }

    private static void alignComments(TokenList tlist) {
        List<Object> tokenNextBy = tlist.tokenNextBy(Collections.singletonList(Comment.class), null, null, null, null);
        while (tokenNextBy != null) {
            Integer tidx = (Integer) tokenNextBy.get(0);
            List<Object> tokenPrev = tlist.tokenPrev(tidx, null, null);
            if (tokenPrev.get(1).getClass() == TokenList.class) {
                tlist.groupTokens(TokenList.class, (Integer) tokenPrev.get(0), tidx, null, true);
                tidx = (Integer) tokenPrev.get(0);
            }
            tokenNextBy = tlist.tokenNextBy(Collections.singletonList(Comment.class), null, null, tidx, null);
        }
    }

    private static void groupValues(TokenList tlist) {
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Collections.singletonList("VALUES"), TokenType.KEYWORD);
        List<Object> tokenNextBy = tlist.tokenNextBy(null, m, null, null, null);
        Integer startIdx = null;
        if (tokenNextBy != null) {
            startIdx = (Integer) tokenNextBy.get(0);
        }
        Integer endIdx = -1;
        while (tokenNextBy != null) {
            Token token = (Token) tokenNextBy.get(1);
            if (token.getClass() == Parenthesis.class) {
                endIdx = (Integer) tokenNextBy.get(0);
            }
            tokenNextBy = tlist.tokenNext((Integer) tokenNextBy.get(0), null, null, null);
        }
        if (endIdx != -1) {
            tlist.groupTokens(Values.class, startIdx, endIdx, null, true);
        }
    }

    private static void groupIdentifierList(TokenList tlist) {
        group(tlist, IdentifierList.class, new IdentifierListGroupFunction(), true, null);
    }

    private static void group(TokenList tlist, Class cls, GroupFunction groupFunction, Boolean extend, Boolean recurse) {
        if (extend == null) {
            extend = true;
        }
        if (recurse == null) {
            recurse = true;
        }
        int tidxOffset = 0;
        List<Token> tokens = tlist.getTokens();
        Integer pidx = null;
        Token prev = null;
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            int tidx = i - tidxOffset;
            if (token.isWhitespace()) {
                continue;
            }
            if (recurse && token.isGroup() && token.getClass() != cls) {
                group((TokenList) token, cls, groupFunction, extend, null);
            }

            if ((Boolean) groupFunction.match(token, new HashMap<>())) {
                List<Object> tokenNext = tlist.tokenNext(tidx, null, null, null);
                if (prev != null && (Boolean) groupFunction.validPrev(prev, new HashMap<>()) && (Boolean) groupFunction.validNext((Token) tokenNext.get(1), new HashMap<>())) {
                    Map<String, Object> otherParam = new HashMap<>();
                    otherParam.put(OtherParamConstants.PIDX, pidx);
                    otherParam.put(OtherParamConstants.TIDX, tidx);
                    otherParam.put(OtherParamConstants.NIDX, tokenNext.get(0));
                    List<Integer> index = (List) groupFunction.post(tlist, otherParam);
                    Integer fromIdx = index.get(0);
                    Integer toIdx = index.get(1);
                    Token grp = tlist.groupTokens(cls, fromIdx, toIdx, null, extend);

                    tidxOffset += (toIdx - fromIdx);
                    pidx = fromIdx;
                    prev = grp;
                    continue;
                }
            }

            pidx = tidx;
            prev = token;
        }
    }
}
