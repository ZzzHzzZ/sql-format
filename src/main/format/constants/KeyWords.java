package format.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: zhanghaozhe
 * @Date: 2019/12/17 16:02
 */
public class KeyWords {
    public static final Map<String, TokenType> SQL_REGEX_MAP = new LinkedHashMap<>();
    public static final Map<String, TokenType> KEYWORDS_COMMON = new LinkedHashMap<>();
    public static final Map<String, TokenType> KEYWORDS_ORACLE = new LinkedHashMap<>();
    public static final Map<String, TokenType> KEYWORDS = new LinkedHashMap<>();

    static {
        SQL_REGEX_MAP.put("(--|//|# )\\+.*?(\\r\\n|\\r|\\n|$)", TokenType.SHINT);
        SQL_REGEX_MAP.put("/\\*\\+[\\s\\S]*?\\*/", TokenType.MHINT);
        SQL_REGEX_MAP.put("(--|//|# ).*?(\\r\\n|\\r|\\n|$)", TokenType.SINGLE);
        SQL_REGEX_MAP.put("/\\*[\\s\\S]*?\\*/", TokenType.MULTILINE);
        SQL_REGEX_MAP.put("(\\r\\n|\\r|\\n)", TokenType.NEWLINE);
        SQL_REGEX_MAP.put("\\s+?", TokenType.WHITESPACE);
        SQL_REGEX_MAP.put(":=", TokenType.ASSIGNMENT);
        SQL_REGEX_MAP.put("::", TokenType.PUNCTUATION);
        SQL_REGEX_MAP.put("\\*", TokenType.WILDCARD);
        SQL_REGEX_MAP.put("`(``|[^`])*`", TokenType.NAME);
        SQL_REGEX_MAP.put("´(´´|[^´])*´", TokenType.NAME);
        SQL_REGEX_MAP.put("(\\$(?:[_A-ZÀ-Ü]\\w*)?\\$)[\\s\\S]*?\\1", TokenType.LITERAL);
        SQL_REGEX_MAP.put("\\?", TokenType.PLACEHOLDER);
        SQL_REGEX_MAP.put("%(\\(\\w+\\))?s", TokenType.PLACEHOLDER);
        SQL_REGEX_MAP.put("(?<!\\w)[$:?]\\w+", TokenType.PLACEHOLDER);
        SQL_REGEX_MAP.put("\\\\\\w+", TokenType.COMMAND);
        SQL_REGEX_MAP.put("(CASE|IN|VALUES|USING|FROM)\\b", TokenType.KEYWORD);
        SQL_REGEX_MAP.put("(@|##|#)[A-ZÀ-Ü]\\w+", TokenType.NAME);
        SQL_REGEX_MAP.put("[A-ZÀ-Ü]\\w*(?=\\s*\\.)", TokenType.NAME);
        SQL_REGEX_MAP.put("(?<=\\.)[A-ZÀ-Ü]\\w*", TokenType.NAME);
        SQL_REGEX_MAP.put("[A-ZÀ-Ü]\\w*(?=\\()", TokenType.NAME);
        SQL_REGEX_MAP.put("-?0x[\\dA-F]+", TokenType.HEXADECIMAL);
        SQL_REGEX_MAP.put("-?\\d*(\\.\\d+)?E-?\\d+", TokenType.FLOAT);
        SQL_REGEX_MAP.put("-?(\\d+(\\.\\d*)|\\.\\d+)", TokenType.FLOAT);
        SQL_REGEX_MAP.put("-?\\d+(?![_A-ZÀ-Ü])", TokenType.INTEGER);
        SQL_REGEX_MAP.put("'(''|\\\\\\\\|\\\\'|[^'])*'", TokenType.STRINGSINGLE);
        SQL_REGEX_MAP.put("\"(\"\"|\\\\\\\\|\\\\\"|[^\"])*\"", TokenType.SYMBOL);
        SQL_REGEX_MAP.put("(\"\"|\".*?[^\\\\]\")", TokenType.SYMBOL);
        SQL_REGEX_MAP.put("(?<![\\w\\])])(\\[[^\\]]+\\])", TokenType.NAME);
        SQL_REGEX_MAP.put("((LEFT\\s+|RIGHT\\s+|FULL\\s+)?(INNER\\s+|OUTER\\s+|STRAIGHT\\s+)?|(CROSS\\s+|NATURAL\\s+)?)?JOIN\\b", TokenType.KEYWORD);
        SQL_REGEX_MAP.put("END(\\s+IF|\\s+LOOP|\\s+WHILE)?\\b", TokenType.KEYWORD);
        SQL_REGEX_MAP.put("NOT\\s+NULL\\b", TokenType.KEYWORD);
        SQL_REGEX_MAP.put("UNION\\s+ALL\\b", TokenType.KEYWORD);
        SQL_REGEX_MAP.put("CREATE(\\s+OR\\s+REPLACE)?\\b", TokenType.DDL);
        SQL_REGEX_MAP.put("DOUBLE\\s+PRECISION\\b", TokenType.BUILTIN);
        SQL_REGEX_MAP.put("GROUP\\s+BY\\b", TokenType.KEYWORD);
        SQL_REGEX_MAP.put("ORDER\\s+BY\\b", TokenType.KEYWORD);

        SQL_REGEX_MAP.put("[0-9_A-ZÀ-Ü][_$#\\w]*", TokenType.FUNCTION);

        SQL_REGEX_MAP.put("[;:()\\[\\],\\.]", TokenType.PUNCTUATION);
        SQL_REGEX_MAP.put("[<>=~!]+", TokenType.COMPARISON);
        SQL_REGEX_MAP.put("[+/@#%^&|`?^-]+", TokenType.OPERATOR);

        KEYWORDS_COMMON.put("SELECT", TokenType.DML);
        KEYWORDS_COMMON.put("INSERT", TokenType.DML);
        KEYWORDS_COMMON.put("DELETE", TokenType.DML);
        KEYWORDS_COMMON.put("UPSERT", TokenType.DML);
        KEYWORDS_COMMON.put("REPLACE", TokenType.DML);
        KEYWORDS_COMMON.put("DROP", TokenType.DDL);
        KEYWORDS_COMMON.put("CREATE", TokenType.DDL);
        KEYWORDS_COMMON.put("ALTER", TokenType.DDL);

        KEYWORDS_COMMON.put("WHERE", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("FROM", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("INNER", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("JOIN", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("STRAIGHT_JOIN", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("AND", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("OR", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("LIKE", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("ON", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("IN", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("SET", TokenType.KEYWORD);

        KEYWORDS_COMMON.put("BY", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("GROUP", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("ORDER", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("LEFT", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("OUTER", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("FULL", TokenType.KEYWORD);

        KEYWORDS_COMMON.put("IF", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("END", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("THEN", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("LOOP", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("AS", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("ELSE", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("FOR", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("WHILE", TokenType.KEYWORD);

        KEYWORDS_COMMON.put("CASE", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("WHEN", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("MIN", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("MAX", TokenType.KEYWORD);
        KEYWORDS_COMMON.put("DISTINCT", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("ARCHIVE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("ARCHIVELOG", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("BACKUP", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("BECOME", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("BLOCK", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("BODY", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("CANCEL", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("CHANGE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("COMPILE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("CONTENTS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("CONTROLFILE", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("DATAFILE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("DBA", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("DISMOUNT", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("DOUBLE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("DUMP", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("EVENTS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("EXCEPTIONS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("EXPLAIN", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("EXTENT", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("EXTERNALLY", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("FLUSH", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("FREELIST", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("FREELISTS", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("INDICATOR", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("INITRANS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("INSTANCE", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("LAYER", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("LINK", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("LISTS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("LOGFILE", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("MANAGE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MANUAL", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MAXDATAFILES", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MAXINSTANCES", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MAXLOGFILES", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MAXLOGHISTORY", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MAXLOGMEMBERS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MAXTRANS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MINEXTENTS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MODULE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("MOUNT", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("NOARCHIVELOG", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("NOCACHE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("NOCYCLE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("NOMAXVALUE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("NOMINVALUE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("NOORDER", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("NORESETLOGS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("NORMAL", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("NOSORT", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("OPTIMAL", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("OWN", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("PACKAGE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("PARALLEL", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("PCTINCREASE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("PCTUSED", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("PLAN", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("PRIVATE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("PROFILE", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("QUOTA", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("RECOVER", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("RESETLOGS", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("RESTRICTED", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("REUSE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("ROLES", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("SAVEPOINT", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("SCN", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("SECTION", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("SEGMENT", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("SHARED", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("SNAPSHOT", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("SORT", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("STATEMENT_ID", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("STOP", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("SWITCH", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("TABLES", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("TABLESPACE", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("THREAD", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("TIME", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("TRACING", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("TRANSACTION", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("TRIGGERS", TokenType.KEYWORD);

        KEYWORDS_ORACLE.put("UNLIMITED", TokenType.KEYWORD);
        KEYWORDS_ORACLE.put("UNLOCK", TokenType.KEYWORD);

        KEYWORDS.put("ABORT", TokenType.KEYWORD);
        KEYWORDS.put("ABS", TokenType.KEYWORD);
        KEYWORDS.put("ABSOLUTE", TokenType.KEYWORD);
        KEYWORDS.put("ACCESS", TokenType.KEYWORD);
        KEYWORDS.put("ADA", TokenType.KEYWORD);
        KEYWORDS.put("ADD", TokenType.KEYWORD);
        KEYWORDS.put("ADMIN", TokenType.KEYWORD);
        KEYWORDS.put("AFTER", TokenType.KEYWORD);
        KEYWORDS.put("AGGREGATE", TokenType.KEYWORD);
        KEYWORDS.put("ALIAS", TokenType.KEYWORD);
        KEYWORDS.put("ALL", TokenType.KEYWORD);
        KEYWORDS.put("ALLOCATE", TokenType.KEYWORD);
        KEYWORDS.put("ANALYSE", TokenType.KEYWORD);
        KEYWORDS.put("ANALYZE", TokenType.KEYWORD);
        KEYWORDS.put("ANY", TokenType.KEYWORD);
        KEYWORDS.put("ARRAYLEN", TokenType.KEYWORD);
        KEYWORDS.put("ARE", TokenType.KEYWORD);
        KEYWORDS.put("ASC", TokenType.ORDER);
        KEYWORDS.put("ASENSITIVE", TokenType.KEYWORD);
        KEYWORDS.put("ASSERTION", TokenType.KEYWORD);
        KEYWORDS.put("ASSIGNMENT", TokenType.KEYWORD);
        KEYWORDS.put("ASYMMETRIC", TokenType.KEYWORD);
        KEYWORDS.put("AT", TokenType.KEYWORD);
        KEYWORDS.put("ATOMIC", TokenType.KEYWORD);
        KEYWORDS.put("AUDIT", TokenType.KEYWORD);
        KEYWORDS.put("AUTHORIZATION", TokenType.KEYWORD);
        KEYWORDS.put("AUTO_INCREMENT", TokenType.KEYWORD);
        KEYWORDS.put("AVG", TokenType.KEYWORD);

        KEYWORDS.put("BACKWARD", TokenType.KEYWORD);
        KEYWORDS.put("BEFORE", TokenType.KEYWORD);
        KEYWORDS.put("BEGIN", TokenType.KEYWORD);
        KEYWORDS.put("BETWEEN", TokenType.KEYWORD);
        KEYWORDS.put("BITVAR", TokenType.KEYWORD);
        KEYWORDS.put("BIT_LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("BOTH", TokenType.KEYWORD);
        KEYWORDS.put("BREADTH", TokenType.KEYWORD);

        KEYWORDS.put("CACHE", TokenType.KEYWORD);
        KEYWORDS.put("CALL", TokenType.KEYWORD);
        KEYWORDS.put("CALLED", TokenType.KEYWORD);
        KEYWORDS.put("CARDINALITY", TokenType.KEYWORD);
        KEYWORDS.put("CASCADE", TokenType.KEYWORD);
        KEYWORDS.put("CASCADED", TokenType.KEYWORD);
        KEYWORDS.put("CAST", TokenType.KEYWORD);
        KEYWORDS.put("CATALOG", TokenType.KEYWORD);
        KEYWORDS.put("CATALOG_NAME", TokenType.KEYWORD);
        KEYWORDS.put("CHAIN", TokenType.KEYWORD);
        KEYWORDS.put("CHARACTERISTICS", TokenType.KEYWORD);
        KEYWORDS.put("CHARACTER_LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("CHARACTER_SET_CATALOG", TokenType.KEYWORD);
        KEYWORDS.put("CHARACTER_SET_NAME", TokenType.KEYWORD);
        KEYWORDS.put("CHARACTER_SET_SCHEMA", TokenType.KEYWORD);
        KEYWORDS.put("CHAR_LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("CHARSET", TokenType.KEYWORD);
        KEYWORDS.put("CHECK", TokenType.KEYWORD);
        KEYWORDS.put("CHECKED", TokenType.KEYWORD);
        KEYWORDS.put("CHECKPOINT", TokenType.KEYWORD);
        KEYWORDS.put("CLASS", TokenType.KEYWORD);
        KEYWORDS.put("CLASS_ORIGIN", TokenType.KEYWORD);
        KEYWORDS.put("CLOB", TokenType.KEYWORD);
        KEYWORDS.put("CLOSE", TokenType.KEYWORD);
        KEYWORDS.put("CLUSTER", TokenType.KEYWORD);
        KEYWORDS.put("COALESCE", TokenType.KEYWORD);
        KEYWORDS.put("COBOL", TokenType.KEYWORD);
        KEYWORDS.put("COLLATE", TokenType.KEYWORD);
        KEYWORDS.put("COLLATION", TokenType.KEYWORD);
        KEYWORDS.put("COLLATION_CATALOG", TokenType.KEYWORD);
        KEYWORDS.put("COLLATION_NAME", TokenType.KEYWORD);
        KEYWORDS.put("COLLATION_SCHEMA", TokenType.KEYWORD);
        KEYWORDS.put("COLLECT", TokenType.KEYWORD);
        KEYWORDS.put("COLUMN", TokenType.KEYWORD);
        KEYWORDS.put("COLUMN_NAME", TokenType.KEYWORD);
        KEYWORDS.put("COMPRESS", TokenType.KEYWORD);
        KEYWORDS.put("COMMAND_FUNCTION", TokenType.KEYWORD);
        KEYWORDS.put("COMMAND_FUNCTION_CODE", TokenType.KEYWORD);
        KEYWORDS.put("COMMENT", TokenType.KEYWORD);
        KEYWORDS.put("COMMIT", TokenType.DML);
        KEYWORDS.put("COMMITTED", TokenType.KEYWORD);
        KEYWORDS.put("COMPLETION", TokenType.KEYWORD);
        KEYWORDS.put("CONCURRENTLY", TokenType.KEYWORD);
        KEYWORDS.put("CONDITION_NUMBER", TokenType.KEYWORD);
        KEYWORDS.put("CONNECT", TokenType.KEYWORD);
        KEYWORDS.put("CONNECTION", TokenType.KEYWORD);
        KEYWORDS.put("CONNECTION_NAME", TokenType.KEYWORD);
        KEYWORDS.put("CONSTRAINT", TokenType.KEYWORD);
        KEYWORDS.put("CONSTRAINTS", TokenType.KEYWORD);
        KEYWORDS.put("CONSTRAINT_CATALOG", TokenType.KEYWORD);
        KEYWORDS.put("CONSTRAINT_NAME", TokenType.KEYWORD);
        KEYWORDS.put("CONSTRAINT_SCHEMA", TokenType.KEYWORD);
        KEYWORDS.put("CONSTRUCTOR", TokenType.KEYWORD);
        KEYWORDS.put("CONTAINS", TokenType.KEYWORD);
        KEYWORDS.put("CONTINUE", TokenType.KEYWORD);
        KEYWORDS.put("CONVERSION", TokenType.KEYWORD);
        KEYWORDS.put("CONVERT", TokenType.KEYWORD);
        KEYWORDS.put("COPY", TokenType.KEYWORD);
        KEYWORDS.put("CORRESPONTING", TokenType.KEYWORD);
        KEYWORDS.put("COUNT", TokenType.KEYWORD);
        KEYWORDS.put("CREATEDB", TokenType.KEYWORD);
        KEYWORDS.put("CREATEUSER", TokenType.KEYWORD);
        KEYWORDS.put("CROSS", TokenType.KEYWORD);
        KEYWORDS.put("CUBE", TokenType.KEYWORD);
        KEYWORDS.put("CURRENT", TokenType.KEYWORD);
        KEYWORDS.put("CURRENT_DATE", TokenType.KEYWORD);
        KEYWORDS.put("CURRENT_PATH", TokenType.KEYWORD);
        KEYWORDS.put("CURRENT_ROLE", TokenType.KEYWORD);
        KEYWORDS.put("CURRENT_TIME", TokenType.KEYWORD);
        KEYWORDS.put("CURRENT_TIMESTAMP", TokenType.KEYWORD);
        KEYWORDS.put("CURRENT_USER", TokenType.KEYWORD);
        KEYWORDS.put("CURSOR", TokenType.KEYWORD);
        KEYWORDS.put("CURSOR_NAME", TokenType.KEYWORD);
        KEYWORDS.put("CYCLE", TokenType.KEYWORD);

        KEYWORDS.put("DATA", TokenType.KEYWORD);
        KEYWORDS.put("DATABASE", TokenType.KEYWORD);
        KEYWORDS.put("DATETIME_INTERVAL_CODE", TokenType.KEYWORD);
        KEYWORDS.put("DATETIME_INTERVAL_PRECISION", TokenType.KEYWORD);
        KEYWORDS.put("DAY", TokenType.KEYWORD);
        KEYWORDS.put("DEALLOCATE", TokenType.KEYWORD);
        KEYWORDS.put("DECLARE", TokenType.KEYWORD);
        KEYWORDS.put("DEFAULT", TokenType.KEYWORD);
        KEYWORDS.put("DEFAULTS", TokenType.KEYWORD);
        KEYWORDS.put("DEFERRABLE", TokenType.KEYWORD);
        KEYWORDS.put("DEFERRED", TokenType.KEYWORD);
        KEYWORDS.put("DEFINED", TokenType.KEYWORD);
        KEYWORDS.put("DEFINER", TokenType.KEYWORD);
        KEYWORDS.put("DELIMITER", TokenType.KEYWORD);
        KEYWORDS.put("DELIMITERS", TokenType.KEYWORD);
        KEYWORDS.put("DEREF", TokenType.KEYWORD);
        KEYWORDS.put("DESC", TokenType.ORDER);
        KEYWORDS.put("DESCRIBE", TokenType.KEYWORD);
        KEYWORDS.put("DESCRIPTOR", TokenType.KEYWORD);
        KEYWORDS.put("DESTROY", TokenType.KEYWORD);
        KEYWORDS.put("DESTRUCTOR", TokenType.KEYWORD);
        KEYWORDS.put("DETERMINISTIC", TokenType.KEYWORD);
        KEYWORDS.put("DIAGNOSTICS", TokenType.KEYWORD);
        KEYWORDS.put("DICTIONARY", TokenType.KEYWORD);
        KEYWORDS.put("DISABLE", TokenType.KEYWORD);
        KEYWORDS.put("DISCONNECT", TokenType.KEYWORD);
        KEYWORDS.put("DISPATCH", TokenType.KEYWORD);
        KEYWORDS.put("DO", TokenType.KEYWORD);
        KEYWORDS.put("DOMAIN", TokenType.KEYWORD);
        KEYWORDS.put("DYNAMIC", TokenType.KEYWORD);
        KEYWORDS.put("DYNAMIC_FUNCTION", TokenType.KEYWORD);
        KEYWORDS.put("DYNAMIC_FUNCTION_CODE", TokenType.KEYWORD);

        KEYWORDS.put("EACH", TokenType.KEYWORD);
        KEYWORDS.put("ENABLE", TokenType.KEYWORD);
        KEYWORDS.put("ENCODING", TokenType.KEYWORD);
        KEYWORDS.put("ENCRYPTED", TokenType.KEYWORD);
        KEYWORDS.put("END-EXEC", TokenType.KEYWORD);
        KEYWORDS.put("ENGINE", TokenType.KEYWORD);
        KEYWORDS.put("EQUALS", TokenType.KEYWORD);
        KEYWORDS.put("ESCAPE", TokenType.KEYWORD);
        KEYWORDS.put("EVERY", TokenType.KEYWORD);
        KEYWORDS.put("EXCEPT", TokenType.KEYWORD);
        KEYWORDS.put("EXCEPTION", TokenType.KEYWORD);
        KEYWORDS.put("EXCLUDING", TokenType.KEYWORD);
        KEYWORDS.put("EXCLUSIVE", TokenType.KEYWORD);
        KEYWORDS.put("EXEC", TokenType.KEYWORD);
        KEYWORDS.put("EXECUTE", TokenType.KEYWORD);
        KEYWORDS.put("EXISTING", TokenType.KEYWORD);
        KEYWORDS.put("EXISTS", TokenType.KEYWORD);
        KEYWORDS.put("EXPLAIN", TokenType.KEYWORD);
        KEYWORDS.put("EXTERNAL", TokenType.KEYWORD);
        KEYWORDS.put("EXTRACT", TokenType.KEYWORD);

        KEYWORDS.put("FALSE", TokenType.KEYWORD);
        KEYWORDS.put("FETCH", TokenType.KEYWORD);
        KEYWORDS.put("FILE", TokenType.KEYWORD);
        KEYWORDS.put("FINAL", TokenType.KEYWORD);
        KEYWORDS.put("FIRST", TokenType.KEYWORD);
        KEYWORDS.put("FORCE", TokenType.KEYWORD);
        KEYWORDS.put("FOREACH", TokenType.KEYWORD);
        KEYWORDS.put("FOREIGN", TokenType.KEYWORD);
        KEYWORDS.put("FORTRAN", TokenType.KEYWORD);
        KEYWORDS.put("FORWARD", TokenType.KEYWORD);
        KEYWORDS.put("FOUND", TokenType.KEYWORD);
        KEYWORDS.put("FREE", TokenType.KEYWORD);
        KEYWORDS.put("FREEZE", TokenType.KEYWORD);
        KEYWORDS.put("FULL", TokenType.KEYWORD);
        KEYWORDS.put("FUNCTION", TokenType.KEYWORD);

        KEYWORDS.put("GENERAL", TokenType.KEYWORD);
        KEYWORDS.put("GENERATED", TokenType.KEYWORD);
        KEYWORDS.put("GET", TokenType.KEYWORD);
        KEYWORDS.put("GLOBAL", TokenType.KEYWORD);
        KEYWORDS.put("GO", TokenType.KEYWORD);
        KEYWORDS.put("GOTO", TokenType.KEYWORD);
        KEYWORDS.put("GRANT", TokenType.KEYWORD);
        KEYWORDS.put("GRANTED", TokenType.KEYWORD);
        KEYWORDS.put("GROUPING", TokenType.KEYWORD);

        KEYWORDS.put("HANDLER", TokenType.KEYWORD);
        KEYWORDS.put("HAVING", TokenType.KEYWORD);
        KEYWORDS.put("HIERARCHY", TokenType.KEYWORD);
        KEYWORDS.put("HOLD", TokenType.KEYWORD);
        KEYWORDS.put("HOUR", TokenType.KEYWORD);
        KEYWORDS.put("HOST", TokenType.KEYWORD);

        KEYWORDS.put("IDENTIFIED", TokenType.KEYWORD);
        KEYWORDS.put("IDENTITY", TokenType.KEYWORD);
        KEYWORDS.put("IGNORE", TokenType.KEYWORD);
        KEYWORDS.put("ILIKE", TokenType.KEYWORD);
        KEYWORDS.put("IMMEDIATE", TokenType.KEYWORD);
        KEYWORDS.put("IMMUTABLE", TokenType.KEYWORD);

        KEYWORDS.put("IMPLEMENTATION", TokenType.KEYWORD);
        KEYWORDS.put("IMPLICIT", TokenType.KEYWORD);
        KEYWORDS.put("INCLUDING", TokenType.KEYWORD);
        KEYWORDS.put("INCREMENT", TokenType.KEYWORD);
        KEYWORDS.put("INDEX", TokenType.KEYWORD);

        KEYWORDS.put("INDITCATOR", TokenType.KEYWORD);
        KEYWORDS.put("INFIX", TokenType.KEYWORD);
        KEYWORDS.put("INHERITS", TokenType.KEYWORD);
        KEYWORDS.put("INITIAL", TokenType.KEYWORD);
        KEYWORDS.put("INITIALIZE", TokenType.KEYWORD);
        KEYWORDS.put("INITIALLY", TokenType.KEYWORD);
        KEYWORDS.put("INOUT", TokenType.KEYWORD);
        KEYWORDS.put("INPUT", TokenType.KEYWORD);
        KEYWORDS.put("INSENSITIVE", TokenType.KEYWORD);
        KEYWORDS.put("INSTANTIABLE", TokenType.KEYWORD);
        KEYWORDS.put("INSTEAD", TokenType.KEYWORD);
        KEYWORDS.put("INTERSECT", TokenType.KEYWORD);
        KEYWORDS.put("INTO", TokenType.KEYWORD);
        KEYWORDS.put("INVOKER", TokenType.KEYWORD);
        KEYWORDS.put("IS", TokenType.KEYWORD);
        KEYWORDS.put("ISNULL", TokenType.KEYWORD);
        KEYWORDS.put("ISOLATION", TokenType.KEYWORD);
        KEYWORDS.put("ITERATE", TokenType.KEYWORD);

        KEYWORDS.put("KEY", TokenType.KEYWORD);
        KEYWORDS.put("KEY_MEMBER", TokenType.KEYWORD);
        KEYWORDS.put("KEY_TYPE", TokenType.KEYWORD);

        KEYWORDS.put("LANCOMPILER", TokenType.KEYWORD);
        KEYWORDS.put("LANGUAGE", TokenType.KEYWORD);
        KEYWORDS.put("LARGE", TokenType.KEYWORD);
        KEYWORDS.put("LAST", TokenType.KEYWORD);
        KEYWORDS.put("LATERAL", TokenType.KEYWORD);
        KEYWORDS.put("LEADING", TokenType.KEYWORD);
        KEYWORDS.put("LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("LESS", TokenType.KEYWORD);
        KEYWORDS.put("LEVEL", TokenType.KEYWORD);
        KEYWORDS.put("LIMIT", TokenType.KEYWORD);
        KEYWORDS.put("LISTEN", TokenType.KEYWORD);
        KEYWORDS.put("LOAD", TokenType.KEYWORD);
        KEYWORDS.put("LOCAL", TokenType.KEYWORD);
        KEYWORDS.put("LOCALTIME", TokenType.KEYWORD);
        KEYWORDS.put("LOCALTIMESTAMP", TokenType.KEYWORD);
        KEYWORDS.put("LOCATION", TokenType.KEYWORD);
        KEYWORDS.put("LOCATOR", TokenType.KEYWORD);
        KEYWORDS.put("LOCK", TokenType.KEYWORD);
        KEYWORDS.put("LOWER", TokenType.KEYWORD);

        KEYWORDS.put("MAP", TokenType.KEYWORD);
        KEYWORDS.put("MATCH", TokenType.KEYWORD);
        KEYWORDS.put("MAXEXTENTS", TokenType.KEYWORD);
        KEYWORDS.put("MAXVALUE", TokenType.KEYWORD);
        KEYWORDS.put("MESSAGE_LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("MESSAGE_OCTET_LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("MESSAGE_TEXT", TokenType.KEYWORD);
        KEYWORDS.put("METHOD", TokenType.KEYWORD);
        KEYWORDS.put("MINUTE", TokenType.KEYWORD);
        KEYWORDS.put("MINUS", TokenType.KEYWORD);
        KEYWORDS.put("MINVALUE", TokenType.KEYWORD);
        KEYWORDS.put("MOD", TokenType.KEYWORD);
        KEYWORDS.put("MODE", TokenType.KEYWORD);
        KEYWORDS.put("MODIFIES", TokenType.KEYWORD);
        KEYWORDS.put("MODIFY", TokenType.KEYWORD);
        KEYWORDS.put("MONTH", TokenType.KEYWORD);
        KEYWORDS.put("MORE", TokenType.KEYWORD);
        KEYWORDS.put("MOVE", TokenType.KEYWORD);
        KEYWORDS.put("MUMPS", TokenType.KEYWORD);

        KEYWORDS.put("NAMES", TokenType.KEYWORD);
        KEYWORDS.put("NATIONAL", TokenType.KEYWORD);
        KEYWORDS.put("NATURAL", TokenType.KEYWORD);
        KEYWORDS.put("NCHAR", TokenType.KEYWORD);
        KEYWORDS.put("NCLOB", TokenType.KEYWORD);
        KEYWORDS.put("NEW", TokenType.KEYWORD);
        KEYWORDS.put("NEXT", TokenType.KEYWORD);
        KEYWORDS.put("NO", TokenType.KEYWORD);
        KEYWORDS.put("NOAUDIT", TokenType.KEYWORD);
        KEYWORDS.put("NOCOMPRESS", TokenType.KEYWORD);
        KEYWORDS.put("NOCREATEDB", TokenType.KEYWORD);
        KEYWORDS.put("NOCREATEUSER", TokenType.KEYWORD);
        KEYWORDS.put("NONE", TokenType.KEYWORD);
        KEYWORDS.put("NOT", TokenType.KEYWORD);
        KEYWORDS.put("NOTFOUND", TokenType.KEYWORD);
        KEYWORDS.put("NOTHING", TokenType.KEYWORD);
        KEYWORDS.put("NOTIFY", TokenType.KEYWORD);
        KEYWORDS.put("NOTNULL", TokenType.KEYWORD);
        KEYWORDS.put("NOWAIT", TokenType.KEYWORD);
        KEYWORDS.put("NULL", TokenType.KEYWORD);
        KEYWORDS.put("NULLABLE", TokenType.KEYWORD);
        KEYWORDS.put("NULLIF", TokenType.KEYWORD);

        KEYWORDS.put("OBJECT", TokenType.KEYWORD);
        KEYWORDS.put("OCTET_LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("OF", TokenType.KEYWORD);
        KEYWORDS.put("OFF", TokenType.KEYWORD);
        KEYWORDS.put("OFFLINE", TokenType.KEYWORD);
        KEYWORDS.put("OFFSET", TokenType.KEYWORD);
        KEYWORDS.put("OIDS", TokenType.KEYWORD);
        KEYWORDS.put("OLD", TokenType.KEYWORD);
        KEYWORDS.put("ONLINE", TokenType.KEYWORD);
        KEYWORDS.put("ONLY", TokenType.KEYWORD);
        KEYWORDS.put("OPEN", TokenType.KEYWORD);
        KEYWORDS.put("OPERATION", TokenType.KEYWORD);
        KEYWORDS.put("OPERATOR", TokenType.KEYWORD);
        KEYWORDS.put("OPTION", TokenType.KEYWORD);
        KEYWORDS.put("OPTIONS", TokenType.KEYWORD);
        KEYWORDS.put("ORDINALITY", TokenType.KEYWORD);
        KEYWORDS.put("OUT", TokenType.KEYWORD);
        KEYWORDS.put("OUTPUT", TokenType.KEYWORD);
        KEYWORDS.put("OVERLAPS", TokenType.KEYWORD);
        KEYWORDS.put("OVERLAY", TokenType.KEYWORD);
        KEYWORDS.put("OVERRIDING", TokenType.KEYWORD);
        KEYWORDS.put("OWNER", TokenType.KEYWORD);

        KEYWORDS.put("QUARTER", TokenType.KEYWORD);

        KEYWORDS.put("PAD", TokenType.KEYWORD);
        KEYWORDS.put("PARAMETER", TokenType.KEYWORD);
        KEYWORDS.put("PARAMETERS", TokenType.KEYWORD);
        KEYWORDS.put("PARAMETER_MODE", TokenType.KEYWORD);
        KEYWORDS.put("PARAMATER_NAME", TokenType.KEYWORD);
        KEYWORDS.put("PARAMATER_ORDINAL_POSITION", TokenType.KEYWORD);
        KEYWORDS.put("PARAMETER_SPECIFIC_CATALOG", TokenType.KEYWORD);
        KEYWORDS.put("PARAMETER_SPECIFIC_NAME", TokenType.KEYWORD);
        KEYWORDS.put("PARAMATER_SPECIFIC_SCHEMA", TokenType.KEYWORD);
        KEYWORDS.put("PARTIAL", TokenType.KEYWORD);
        KEYWORDS.put("PASCAL", TokenType.KEYWORD);
        KEYWORDS.put("PCTFREE", TokenType.KEYWORD);
        KEYWORDS.put("PENDANT", TokenType.KEYWORD);
        KEYWORDS.put("PLACING", TokenType.KEYWORD);
        KEYWORDS.put("PLI", TokenType.KEYWORD);
        KEYWORDS.put("POSITION", TokenType.KEYWORD);
        KEYWORDS.put("POSTFIX", TokenType.KEYWORD);
        KEYWORDS.put("PRECISION", TokenType.KEYWORD);
        KEYWORDS.put("PREFIX", TokenType.KEYWORD);
        KEYWORDS.put("PREORDER", TokenType.KEYWORD);
        KEYWORDS.put("PREPARE", TokenType.KEYWORD);
        KEYWORDS.put("PRESERVE", TokenType.KEYWORD);
        KEYWORDS.put("PRIMARY", TokenType.KEYWORD);
        KEYWORDS.put("PRIOR", TokenType.KEYWORD);
        KEYWORDS.put("PRIVILEGES", TokenType.KEYWORD);
        KEYWORDS.put("PROCEDURAL", TokenType.KEYWORD);
        KEYWORDS.put("PROCEDURE", TokenType.KEYWORD);
        KEYWORDS.put("PUBLIC", TokenType.KEYWORD);

        KEYWORDS.put("RAISE", TokenType.KEYWORD);
        KEYWORDS.put("RAW", TokenType.KEYWORD);
        KEYWORDS.put("READ", TokenType.KEYWORD);
        KEYWORDS.put("READS", TokenType.KEYWORD);
        KEYWORDS.put("RECHECK", TokenType.KEYWORD);
        KEYWORDS.put("RECURSIVE", TokenType.KEYWORD);
        KEYWORDS.put("REF", TokenType.KEYWORD);
        KEYWORDS.put("REFERENCES", TokenType.KEYWORD);
        KEYWORDS.put("REFERENCING", TokenType.KEYWORD);
        KEYWORDS.put("REINDEX", TokenType.KEYWORD);
        KEYWORDS.put("RELATIVE", TokenType.KEYWORD);
        KEYWORDS.put("RENAME", TokenType.KEYWORD);
        KEYWORDS.put("REPEATABLE", TokenType.KEYWORD);
        KEYWORDS.put("RESET", TokenType.KEYWORD);
        KEYWORDS.put("RESOURCE", TokenType.KEYWORD);
        KEYWORDS.put("RESTART", TokenType.KEYWORD);
        KEYWORDS.put("RESTRICT", TokenType.KEYWORD);
        KEYWORDS.put("RESULT", TokenType.KEYWORD);
        KEYWORDS.put("RETURN", TokenType.KEYWORD);
        KEYWORDS.put("RETURNED_LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("RETURNED_OCTET_LENGTH", TokenType.KEYWORD);
        KEYWORDS.put("RETURNED_SQLSTATE", TokenType.KEYWORD);
        KEYWORDS.put("RETURNING", TokenType.KEYWORD);
        KEYWORDS.put("RETURNS", TokenType.KEYWORD);
        KEYWORDS.put("REVOKE", TokenType.KEYWORD);
        KEYWORDS.put("RIGHT", TokenType.KEYWORD);
        KEYWORDS.put("ROLE", TokenType.KEYWORD);
        KEYWORDS.put("ROLLBACK", TokenType.DML);
        KEYWORDS.put("ROLLUP", TokenType.KEYWORD);
        KEYWORDS.put("ROUTINE", TokenType.KEYWORD);
        KEYWORDS.put("ROUTINE_CATALOG", TokenType.KEYWORD);
        KEYWORDS.put("ROUTINE_NAME", TokenType.KEYWORD);
        KEYWORDS.put("ROUTINE_SCHEMA", TokenType.KEYWORD);
        KEYWORDS.put("ROW", TokenType.KEYWORD);
        KEYWORDS.put("ROWS", TokenType.KEYWORD);
        KEYWORDS.put("ROW_COUNT", TokenType.KEYWORD);
        KEYWORDS.put("RULE", TokenType.KEYWORD);

        KEYWORDS.put("SAVE_POINT", TokenType.KEYWORD);
        KEYWORDS.put("SCALE", TokenType.KEYWORD);
        KEYWORDS.put("SCHEMA", TokenType.KEYWORD);
        KEYWORDS.put("SCHEMA_NAME", TokenType.KEYWORD);
        KEYWORDS.put("SCOPE", TokenType.KEYWORD);
        KEYWORDS.put("SCROLL", TokenType.KEYWORD);
        KEYWORDS.put("SEARCH", TokenType.KEYWORD);
        KEYWORDS.put("SECOND", TokenType.KEYWORD);
        KEYWORDS.put("SECURITY", TokenType.KEYWORD);
        KEYWORDS.put("SELF", TokenType.KEYWORD);
        KEYWORDS.put("SENSITIVE", TokenType.KEYWORD);
        KEYWORDS.put("SEQUENCE", TokenType.KEYWORD);
        KEYWORDS.put("SERIALIZABLE", TokenType.KEYWORD);
        KEYWORDS.put("SERVER_NAME", TokenType.KEYWORD);
        KEYWORDS.put("SESSION", TokenType.KEYWORD);
        KEYWORDS.put("SESSION_USER", TokenType.KEYWORD);
        KEYWORDS.put("SETOF", TokenType.KEYWORD);
        KEYWORDS.put("SETS", TokenType.KEYWORD);
        KEYWORDS.put("SHARE", TokenType.KEYWORD);
        KEYWORDS.put("SHOW", TokenType.KEYWORD);
        KEYWORDS.put("SIMILAR", TokenType.KEYWORD);
        KEYWORDS.put("SIMPLE", TokenType.KEYWORD);
        KEYWORDS.put("SIZE", TokenType.KEYWORD);
        KEYWORDS.put("SOME", TokenType.KEYWORD);
        KEYWORDS.put("SOURCE", TokenType.KEYWORD);
        KEYWORDS.put("SPACE", TokenType.KEYWORD);
        KEYWORDS.put("SPECIFIC", TokenType.KEYWORD);
        KEYWORDS.put("SPECIFICTYPE", TokenType.KEYWORD);
        KEYWORDS.put("SPECIFIC_NAME", TokenType.KEYWORD);
        KEYWORDS.put("SQL", TokenType.KEYWORD);
        KEYWORDS.put("SQLBUF", TokenType.KEYWORD);
        KEYWORDS.put("SQLCODE", TokenType.KEYWORD);
        KEYWORDS.put("SQLERROR", TokenType.KEYWORD);
        KEYWORDS.put("SQLEXCEPTION", TokenType.KEYWORD);
        KEYWORDS.put("SQLSTATE", TokenType.KEYWORD);
        KEYWORDS.put("SQLWARNING", TokenType.KEYWORD);
        KEYWORDS.put("STABLE", TokenType.KEYWORD);
        KEYWORDS.put("START", TokenType.DML);

        KEYWORDS.put("STATEMENT", TokenType.KEYWORD);
        KEYWORDS.put("STATIC", TokenType.KEYWORD);
        KEYWORDS.put("STATISTICS", TokenType.KEYWORD);
        KEYWORDS.put("STDIN", TokenType.KEYWORD);
        KEYWORDS.put("STDOUT", TokenType.KEYWORD);
        KEYWORDS.put("STORAGE", TokenType.KEYWORD);
        KEYWORDS.put("STRICT", TokenType.KEYWORD);
        KEYWORDS.put("STRUCTURE", TokenType.KEYWORD);
        KEYWORDS.put("STYPE", TokenType.KEYWORD);
        KEYWORDS.put("SUBCLASS_ORIGIN", TokenType.KEYWORD);
        KEYWORDS.put("SUBLIST", TokenType.KEYWORD);
        KEYWORDS.put("SUBSTRING", TokenType.KEYWORD);
        KEYWORDS.put("SUCCESSFUL", TokenType.KEYWORD);
        KEYWORDS.put("SUM", TokenType.KEYWORD);
        KEYWORDS.put("SYMMETRIC", TokenType.KEYWORD);
        KEYWORDS.put("SYNONYM", TokenType.KEYWORD);
        KEYWORDS.put("SYSID", TokenType.KEYWORD);
        KEYWORDS.put("SYSTEM", TokenType.KEYWORD);
        KEYWORDS.put("SYSTEM_USER", TokenType.KEYWORD);

        KEYWORDS.put("TABLE", TokenType.KEYWORD);
        KEYWORDS.put("TABLE_NAME", TokenType.KEYWORD);
        KEYWORDS.put("TEMP", TokenType.KEYWORD);
        KEYWORDS.put("TEMPLATE", TokenType.KEYWORD);
        KEYWORDS.put("TEMPORARY", TokenType.KEYWORD);
        KEYWORDS.put("TERMINATE", TokenType.KEYWORD);
        KEYWORDS.put("THAN", TokenType.KEYWORD);
        KEYWORDS.put("TIMESTAMP", TokenType.KEYWORD);
        KEYWORDS.put("TIMEZONE_HOUR", TokenType.KEYWORD);
        KEYWORDS.put("TIMEZONE_MINUTE", TokenType.KEYWORD);
        KEYWORDS.put("TO", TokenType.KEYWORD);
        KEYWORDS.put("TOAST", TokenType.KEYWORD);
        KEYWORDS.put("TRAILING", TokenType.KEYWORD);
        KEYWORDS.put("TRANSATION", TokenType.KEYWORD);
        KEYWORDS.put("TRANSACTIONS_COMMITTED", TokenType.KEYWORD);
        KEYWORDS.put("TRANSACTIONS_ROLLED_BACK", TokenType.KEYWORD);
        KEYWORDS.put("TRANSATION_ACTIVE", TokenType.KEYWORD);
        KEYWORDS.put("TRANSFORM", TokenType.KEYWORD);
        KEYWORDS.put("TRANSFORMS", TokenType.KEYWORD);
        KEYWORDS.put("TRANSLATE", TokenType.KEYWORD);
        KEYWORDS.put("TRANSLATION", TokenType.KEYWORD);
        KEYWORDS.put("TREAT", TokenType.KEYWORD);
        KEYWORDS.put("TRIGGER", TokenType.KEYWORD);
        KEYWORDS.put("TRIGGER_CATALOG", TokenType.KEYWORD);
        KEYWORDS.put("TRIGGER_NAME", TokenType.KEYWORD);
        KEYWORDS.put("TRIGGER_SCHEMA", TokenType.KEYWORD);
        KEYWORDS.put("TRIM", TokenType.KEYWORD);
        KEYWORDS.put("TRUE", TokenType.KEYWORD);
        KEYWORDS.put("TRUNCATE", TokenType.KEYWORD);
        KEYWORDS.put("TRUSTED", TokenType.KEYWORD);
        KEYWORDS.put("TYPE", TokenType.KEYWORD);

        KEYWORDS.put("UID", TokenType.KEYWORD);
        KEYWORDS.put("UNCOMMITTED", TokenType.KEYWORD);
        KEYWORDS.put("UNDER", TokenType.KEYWORD);
        KEYWORDS.put("UNENCRYPTED", TokenType.KEYWORD);
        KEYWORDS.put("UNION", TokenType.KEYWORD);
        KEYWORDS.put("UNIQUE", TokenType.KEYWORD);
        KEYWORDS.put("UNKNOWN", TokenType.KEYWORD);
        KEYWORDS.put("UNLISTEN", TokenType.KEYWORD);
        KEYWORDS.put("UNNAMED", TokenType.KEYWORD);
        KEYWORDS.put("UNNEST", TokenType.KEYWORD);
        KEYWORDS.put("UNTIL", TokenType.KEYWORD);
        KEYWORDS.put("UPPER", TokenType.KEYWORD);
        KEYWORDS.put("USAGE", TokenType.KEYWORD);
        KEYWORDS.put("USE", TokenType.KEYWORD);
        KEYWORDS.put("USER", TokenType.KEYWORD);
        KEYWORDS.put("USER_DEFINED_TYPE_CATALOG", TokenType.KEYWORD);
        KEYWORDS.put("USER_DEFINED_TYPE_NAME", TokenType.KEYWORD);
        KEYWORDS.put("USER_DEFINED_TYPE_SCHEMA", TokenType.KEYWORD);
        KEYWORDS.put("USING", TokenType.KEYWORD);

        KEYWORDS.put("VACUUM", TokenType.KEYWORD);
        KEYWORDS.put("VALID", TokenType.KEYWORD);
        KEYWORDS.put("VALIDATE", TokenType.KEYWORD);
        KEYWORDS.put("VALIDATOR", TokenType.KEYWORD);
        KEYWORDS.put("VALUES", TokenType.KEYWORD);
        KEYWORDS.put("VARIABLE", TokenType.KEYWORD);
        KEYWORDS.put("VERBOSE", TokenType.KEYWORD);
        KEYWORDS.put("VERSION", TokenType.KEYWORD);
        KEYWORDS.put("VIEW", TokenType.KEYWORD);
        KEYWORDS.put("VOLATILE", TokenType.KEYWORD);

        KEYWORDS.put("WEEK", TokenType.KEYWORD);
        KEYWORDS.put("WHENEVER", TokenType.KEYWORD);
        KEYWORDS.put("WITH", TokenType.CTE);
        KEYWORDS.put("WITHOUT", TokenType.KEYWORD);
        KEYWORDS.put("WORK", TokenType.KEYWORD);
        KEYWORDS.put("WRITE", TokenType.KEYWORD);

        KEYWORDS.put("YEAR", TokenType.KEYWORD);

        KEYWORDS.put("ZONE", TokenType.KEYWORD);

        KEYWORDS.put("ARRAY", TokenType.BUILTIN);
        KEYWORDS.put("BIGINT", TokenType.BUILTIN);
        KEYWORDS.put("BINARY", TokenType.BUILTIN);
        KEYWORDS.put("BIT", TokenType.BUILTIN);
        KEYWORDS.put("BLOB", TokenType.BUILTIN);
        KEYWORDS.put("BOOLEAN", TokenType.BUILTIN);
        KEYWORDS.put("CHAR", TokenType.BUILTIN);
        KEYWORDS.put("CHARACTER", TokenType.BUILTIN);
        KEYWORDS.put("DATE", TokenType.BUILTIN);
        KEYWORDS.put("DEC", TokenType.BUILTIN);
        KEYWORDS.put("DECIMAL", TokenType.BUILTIN);
        KEYWORDS.put("FLOAT", TokenType.BUILTIN);
        KEYWORDS.put("INT", TokenType.BUILTIN);
        KEYWORDS.put("INT8", TokenType.BUILTIN);
        KEYWORDS.put("INTEGER", TokenType.BUILTIN);
        KEYWORDS.put("INTERVAL", TokenType.BUILTIN);
        KEYWORDS.put("LONG", TokenType.BUILTIN);
        KEYWORDS.put("NUMBER", TokenType.BUILTIN);
        KEYWORDS.put("NUMERIC", TokenType.BUILTIN);
        KEYWORDS.put("REAL", TokenType.BUILTIN);
        KEYWORDS.put("ROWID", TokenType.BUILTIN);
        KEYWORDS.put("ROWLABEL", TokenType.BUILTIN);
        KEYWORDS.put("ROWNUM", TokenType.BUILTIN);
        KEYWORDS.put("SERIAL", TokenType.BUILTIN);
        KEYWORDS.put("SERIAL8", TokenType.BUILTIN);
        KEYWORDS.put("SIGNED", TokenType.BUILTIN);
        KEYWORDS.put("SMALLINT", TokenType.BUILTIN);
        KEYWORDS.put("SYSDATE", TokenType.NAME);
        KEYWORDS.put("TEXT", TokenType.BUILTIN);
        KEYWORDS.put("TINYINT", TokenType.BUILTIN);
        KEYWORDS.put("UNSIGNED", TokenType.BUILTIN);
        KEYWORDS.put("VARCHAR", TokenType.BUILTIN);
        KEYWORDS.put("VARCHAR2", TokenType.BUILTIN);
        KEYWORDS.put("VARYING", TokenType.BUILTIN);
    }

    public static TokenType isKeyword(String value) {
        String upper = value.toUpperCase();
        if (KEYWORDS_COMMON.get(upper) != null) {
            return KEYWORDS_COMMON.get(upper);
        }
        if (KEYWORDS_ORACLE.get(upper) != null) {
            return KEYWORDS_ORACLE.get(upper);
        }
        if (KEYWORDS.get(upper) != null) {
            return KEYWORDS.get(upper);
        }
        return TokenType.NAME;
    }
}