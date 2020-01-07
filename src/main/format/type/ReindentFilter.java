package format.type;

import format.constants.TokenType;

import java.util.*;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/26 14:21
 */
public class ReindentFilter {

    private String n = "\n";
    private Integer width = 2;
    private String c = " ";
    private Integer indent = 0;
    private Integer offset = 0;
    private Integer wrapAfter = 0;
    private Boolean commaFirst = false;
    private Boolean indentColumns = false;
    private TokenList currStmt = null;
    private TokenList lastStmt = null;
    private Token lastFunc = null;

    public ReindentFilter() {
    }

    public ReindentFilter(String n, Integer width, String c, Integer indent, Integer offset, Integer wrapAfter, Boolean commaFirst, Boolean indentColumns, TokenList currStmt, TokenList lastStmt, Token lastFunc) {
        this.n = n;
        this.width = width;
        this.c = c;
        this.indent = indent;
        this.offset = offset;
        this.wrapAfter = wrapAfter;
        this.commaFirst = commaFirst;
        this.indentColumns = indentColumns;
        this.currStmt = currStmt;
        this.lastStmt = lastStmt;
        this.lastFunc = lastFunc;
    }

    public Integer leadingWs() {
        return this.offset + this.indent * this.width;
    }

    private void process(TokenList tlist) {
        Class c = tlist.getClass();
        if (c == Where.class) {
            processWhere(tlist);
        } else if (c == Parenthesis.class) {
            processParenthesis(tlist);
        } else if (c == Function.class) {
            processFunction(tlist);
        } else if (c == IdentifierList.class) {
            processIdentifierList(tlist);
        } else if (c == Case.class) {
            processCase(tlist);
        } else if (c == Values.class) {
            processValues(tlist);
        } else {
            processDefault(tlist, null);
        }
    }

    private void processWhere(TokenList tlist) {
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Arrays.asList("WHERE"), TokenType.KEYWORD);
        List<Object> tokenNextBy = tlist.tokenNextBy(null, m, null, null, null);
        tlist.insertBefore(tokenNextBy.get(0), this.nl(null));

        this.indent += 1;
        this.processDefault(tlist, null);
        this.indent -= 1;
    }

    private void processParenthesis(TokenList tlist) {
        List<Object> isDmlDll = tlist.tokenNextBy(null, null, Arrays.asList(TokenType.DML, TokenType.DDL), null, null);
        List<Object> first = tlist.tokenNextBy(null, new HashMap<>(Parenthesis.M_OPEN), null, null, null);

        int n = isDmlDll == null ? 0 : 1;
        this.indent += n;
        if (isDmlDll != null) {
            tlist.getTokens().add(0, this.nl(null));
        }

        int o = this.getOffset((Token) first.get(1)) + 1;
        this.offset += o;

        this.processDefault(tlist, isDmlDll == null);
        this.offset -= o;
        this.indent -= n;
    }

    private void processFunction(TokenList tlist) {
        this.lastFunc = tlist.getTokens().get(0);
        this.processDefault(tlist, null);
    }

    private void processIdentifierList(TokenList tlist) {
        IdentifierList identifierList = (IdentifierList) tlist;
        List<Token> identifiers = identifierList.getIdentifiers();
        Token first;
        int numOffset;
        if (this.indentColumns) {
            first = identifiers.get(0).flatten().get(0);
            numOffset = this.c.equals("\t") ? 1 : this.width;
        } else {
            Token iden = identifiers.remove(0);
            first = iden.flatten().get(0);
            numOffset = this.c.equals("\t") ? 1 : this.getOffset(first);
        }

        if (!tlist.within(Function.class) && !tlist.within(Values.class)) {
            this.offset += numOffset;
            int position = 0;
            for (int i = 0; i < identifiers.size(); i++) {
                Token token = identifiers.get(i);
                position += (token.getValue().length() + 1);
                if (position > (this.wrapAfter - this.offset)) {
                    int adjust = 0;
                    if (this.commaFirst) {
                        adjust = -2;
                        List<Object> comma = tlist.tokenPrev(tlist.tokenIndex(token, null), null, null);
                        if (comma == null) {
                            continue;
                        }
                        token = (Token) comma.get(1);
                    }
                    tlist.insertBefore(token, this.nl(adjust));
                    if (this.commaFirst) {
                        List<Object> ws = tlist.tokenNext(tlist.tokenIndex(token, null), false, null, null);
                        if (ws != null && ((Token) ws.get(1)).getTtype() != TokenType.WHITESPACE) {
                            tlist.insertAfter(token, new Token(TokenType.WHITESPACE, " "), null);
                        }
                    }
                    position = 0;
                }
            }
            this.offset -= numOffset;
        } else {
            List<Token> tokens = tlist.getTokens();
            for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                List<Object> nextWs = tlist.tokenNext(tlist.tokenIndex(token, null), false, null, null);
                if (token.getValue().equals(",") && !((Token) nextWs.get(1)).isWhitespace()) {
                    tlist.insertAfter(token, new Token(TokenType.WHITESPACE, " "), null);
                }
            }

            int endAt = this.offset;
            for (Token identifier : identifiers) {
                endAt += (identifier.getValue().length() + 1);
            }
            int adjustedOffset = 0;
            if (this.wrapAfter > 0 && endAt > (this.wrapAfter - this.offset) && this.lastFunc != null) {
                adjustedOffset = -this.lastFunc.getValue().length() - 1;
            }

            this.offset += adjustedOffset;
            this.indent += 1;
            if (adjustedOffset < 0) {
                tlist.insertBefore(identifiers.get(0), this.nl(null));
            }
            int position = 0;
            for (int i = 0; i < identifiers.size(); i++) {
                Token token = identifiers.get(i);
                position += token.getValue().length() + 1;
                if (this.wrapAfter > 0 && position > (this.wrapAfter - this.offset)) {
                    tlist.insertBefore(token, this.nl(0));
                    position = 0;
                }
            }
            this.indent -= 1;
            this.offset -= adjustedOffset;
        }
        this.processDefault(tlist, null);
    }

    private void processCase(TokenList tlist) {
        Case c = (Case) tlist;
        List<List<List<Token>>> cases = c.getCases(null);
        List<Token> cond = cases.remove(0).get(0);
        Token first = cond.get(0).flatten().get(0);

        int offset1 = this.getOffset(tlist.getTokens().get(0));
        this.offset += offset1;

        int offset2 = this.getOffset(first);
        this.offset += offset2;

        for (List<List<Token>> aCase : cases) {
            cond = aCase.get(0);
            List<Token> value = aCase.get(1);
            Token token = cond == null ? value.get(0) : cond.get(0);
            tlist.insertBefore(token, this.nl(null));
        }

        int offset3 = "WHEN ".length();
        this.offset += offset3;
        this.processDefault(tlist, null);
        this.offset -= offset3;

        this.offset -= offset2;
        List<Object> end = tlist.tokenNextBy(null, new HashMap<>(Case.M_CLOSE), null, null, null);
        if (end != null) {
            tlist.insertBefore((Integer) end.get(0), this.nl(null));
        }
        this.offset -= offset1;
    }

    private void processValues(TokenList tlist) {
        tlist.insertBefore(0, this.nl(null));
        List<Object> tokenNextBy = tlist.tokenNextBy(Collections.singletonList(Parenthesis.class), null, null, null, null);
        Token firstToken = (Token) tokenNextBy.get(1);
        while (tokenNextBy != null) {
            Map<List<String>, TokenType> m = new HashMap<>();
            m.put(Collections.singletonList(","), TokenType.PUNCTUATION);
            List<Object> pToken = tlist.tokenNextBy(null, m, null, (Integer) tokenNextBy.get(0), null);
            if (pToken != null) {
                if (this.commaFirst) {
                    int adjust = -2;
                    int offset = this.getOffset(firstToken) + adjust;
                    tlist.insertBefore(pToken.get(1), this.nl(offset));
                } else {
                    tlist.insertAfter(pToken.get(1), this.nl(this.getOffset((Token) tokenNextBy.get(1))), null);
                }
            }
            tokenNextBy = tlist.tokenNextBy(Collections.singletonList(Parenthesis.class), null, null, (Integer) tokenNextBy.get(0), null);
        }
    }

    private Integer getOffset(Token token) {
        StringBuilder sb = new StringBuilder();
        List<Token> flattenUpToToken = flattenUpToToken(token);
        for (Token t : flattenUpToToken) {
            sb.append(t.toString());
        }
        String raw = sb.toString();
        String line;
        if (raw.isEmpty()) {
            line = "";
        } else {
            String temp = raw.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
            String[] split = temp.split("\n");
            line = split[split.length - 1];
        }
        sb = new StringBuilder();
        int leadingWs = leadingWs();
        for (int i = 0; i < leadingWs; i++) {
            sb.append(this.c);
        }
        return line.length() - sb.toString().length();
    }

    private List<Token> flattenUpToToken(Token token) {
        List<Token> result = new ArrayList<>();
        if (token.isGroup()) {
            token = token.flatten().get(0);
        }
        List<Token> flatten = this.currStmt.flatten();
        for (Token t : flatten) {
            if (t == token) {
                break;
            }
            result.add(t);
        }
        return result;
    }

    private Token nl(Integer offset) {
        if (offset == null) {
            offset = 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.n);
        int appendTime = Math.max(0, leadingWs() + offset);
        for (int i = 0; i < appendTime; i++) {
            sb.append(this.c);
        }
        return new Token(TokenType.WHITESPACE, sb.toString());
    }

    private void processDefault(TokenList tlist, Boolean stmts) {
        if (stmts == null) {
            stmts = true;
        }
        if (stmts) {
            this.splitStatements(tlist);
        }
        this.splitKwds(tlist);
        List<Token> tokens = tlist.getSublist();
        for (Token token : tokens) {
            this.process((TokenList) token);
        }
    }

    private void splitStatements(TokenList tlist) {
        List<TokenType> t = Arrays.asList(TokenType.DML, TokenType.DDL);
        List<Object> tokenNextBy = tlist.tokenNextBy(null, null, t, null, null);
        while (tokenNextBy != null) {
            Integer tidx = (Integer) tokenNextBy.get(0);
            List<Object> prev = tlist.tokenPrev((Integer) tokenNextBy.get(0), false, null);
            if (prev != null && ((Token) prev.get(1)).isWhitespace()) {
                int pidx = (Integer) prev.get(0);
                tlist.getTokens().remove(pidx);
                tidx -= 1;
            }
            if (prev != null) {
                tlist.insertBefore(tidx, this.nl(null));
                tidx += 1;
            }
            tokenNextBy = tlist.tokenNextBy(null, null, t, tidx, null);
        }
    }

    private void splitKwds(TokenList tlist) {
        List<Object> nextToken = this.nextToken(tlist, null);
        while (nextToken != null) {
            int tidx = (Integer) nextToken.get(0);
            List<Object> prev = tlist.tokenPrev(tidx, false, null);
            String uprev = prev == null ? "null" : prev.get(1).toString();

            if (prev != null && ((Token) prev.get(1)).isWhitespace()) {
                int pidx = (Integer) prev.get(0);
                tlist.getTokens().remove(pidx);
                tidx -= 1;
            }

            if (!uprev.endsWith("\n") || uprev.endsWith("\r")) {
                tlist.insertBefore(tidx, this.nl(null));
                tidx += 1;
            }

            nextToken = this.nextToken(tlist, tidx);
        }
    }

    private List<Object> nextToken(TokenList tlist, Integer idx) {
        if (idx == null) {
            idx = -1;
        }
        Map<List<String>, TokenType> m = new HashMap<>();
        m.put(Arrays.asList("FROM", "STRAIGHT_JOIN$", "JOIN$", "AND", "OR",
                "GROUP BY", "ORDER BY", "UNION", "VALUES",
                "SET", "BETWEEN", "EXCEPT", "HAVING", "LIMIT"), TokenType.KEYWORD);
        //regex is true
        m.put(null, TokenType.KEYWORD);
        List<Object> tokenNextBy = tlist.tokenNextBy(null, m, null, idx, null);
        if (tokenNextBy != null && ((Token) tokenNextBy.get(1)).getNormalized().equals("BETWEEN")) {
            tokenNextBy = this.nextToken(tlist, (Integer) tokenNextBy.get(0));

            if (tokenNextBy != null && ((Token) tokenNextBy.get(1)).getNormalized().equals("AND")) {
                tokenNextBy = this.nextToken(tlist, (Integer) tokenNextBy.get(0));
            }
        }
        return tokenNextBy;
    }

    public void reindent(Statement stmt) {
        this.currStmt = stmt;
        this.process(stmt);

        if (this.lastStmt != null) {
            String lastStmtStr = this.lastStmt.toString();
            String nl = lastStmtStr.endsWith("\n") ? "\n" : "\n\n";
            stmt.getTokens().add(0, new Token(TokenType.WHITESPACE, nl));
        }

        this.lastStmt = stmt;
    }
}
