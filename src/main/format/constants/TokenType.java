package format.constants;


/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/17 15:27
 */
public enum TokenType {
    TOKEN("Token", 0),
    STATEMENT("Statement", 1),
    ASSIGNMENT("Assignment", 1),
    COMMENT("Comment", 1),
    MULTILINE("Comment.Multiline", 2),
    MHINT("Comment.Multiline.Hint", 3),
    SINGLE("Comment.Single", 2),
    SHINT("Comment.Single.Hint", 3),
    ERROR("Error", 1),
    GENERIC("Generic", 1),
    COMMAND("Generic.Command", 2),
    KEYWORD("Keyword", 1),
    ORDER("Keyword.Order", 2),
    DDL("Keyword.DDL", 2),
    DML("Keyword.DML", 2),
    CTE("Keyword.CTE", 2),
    LITERAL("Literal", 1),
    NUMBER("Literal.Number", 2),
    FLOAT("Literal.Number.float", 3),
    HEXADECIMAL("Literal.Number.Hexadecimal", 3),
    INTEGER("Literal.Number.integer", 3),
    STRING("Literal.String", 2),
    STRINGSINGLE("Literal.String.Single", 3),
    SYMBOL("Literal.String.Symbol", 3),
    NAME("Name", 1),
    BUILTIN("Name.Builtin", 2),
    PLACEHOLDER("Name.Placeholder", 2),
    OPERATOR("Operator", 1),
    COMPARISON("Operator.Comparison", 2),
    OTHER("Other", 1),
    PUNCTUATION("Punctuation", 1),
    TEXT("Text", 1),
    WHITESPACE("Text.Whitespace", 2),
    NEWLINE("Text.Whitespace.Newline", 3),
    WILDCARD("Wildcard", 1),
    FUNCTION("function", 0);


    private String name;
    private Integer type;

    private TokenType(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

