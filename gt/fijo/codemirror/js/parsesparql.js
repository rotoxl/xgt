var SparqlParser = Editor.Parser = (function() {
  function wordRegexp(words) {
    return new RegExp("^(?:" + words.join("|") + ")$", "i");
  }
  var ops = wordRegexp(['ABS','ACOS','ADDDATE','ADDTIME','AES_DECRYPT','AES_ENCRYPT','ASCII','ASIN','ATAN2 ATAN','ATAN','AVG','BENCHMARK','DISTINCT','BIN','BIT_AND','BIT_COUNT','BIT_LENGTH','BIT_OR','BIT_XOR','CAST','CEILING CEIL','CHAR_LENGTH','CHAR',
	'CHARACTER_LENGTH','CHARSET','COALESCE','COERCIBILITY','COLLATION','COMPRESS','CONCAT_WS','CONCAT','CONNECTION_ID','CONV','CONVERT_TZ','COS','COT','COUNT','CRC32','CURDATE','CURRENT_DATE','CURRENT_TIME','CURRENT_TIMESTAMP','CURRENT_USER','CURTIME','DATABASE','DATE_ADD','DATE_FORMAT','DATE_SUB','DATE','DATEDIFF','DAY','DAYNAME','DAYOFMONTH',
	'DAYOFWEEK','DAYOFYEAR','DECODE','DEFAULT','DEGREES','DES_DECRYPT','DES_ENCRYPT','ELT','ENCODE','ENCRYPT','EXP','EXPORT_SET','EXTRACT','FIELD','FIND_IN_SET','FLOOR','FORMAT','FOUND_ROWS','FROM_DAYS','FROM_UNIXTIME','GET_FORMAT','GET_LOCK','GREATEST','GROUP_CONCAT','HEX','HOUR','IF','IFNULL','INET_ATON','INET_NTOA',
	'INSERT','INSTR','INTERVAL','IS_FREE_LOCK','IS_USED_LOCK','ISNULL','LAST_DAY','LAST_INSERT_ID','LCASE','LEAST','len', 'LEFT','LENGTH','LN','LOAD_FILE','LOCALTIME','LOCALTIMESTAMP','LOCATE','LOG10','LOG2','LOG','LOWER','LPAD','LTRIM','MAKE_SET','MAKEDATE','MAKETIME','MASTER_POS_WAIT','MAX','MD5','MICROSECOND',
	'MID','MIN','MINUTE','MOD','MONTH','MONTHNAME','NOW','NULLIF','OCT','OCTET_LENGTH','OLD_PASSWORD','ORD','PASSWORD','PERIOD_ADD','PERIOD_DIFF','PI','POSITION','POW','POWER','PROCEDURE ANALYSE','QUARTER','QUOTE','RADIANS','RAND','RELEASE_LOCK','REPEAT','REPLACE','REVERSE','RIGHT','ROUND',
	'RPAD','RTRIM','SEC_TO_TIME','SECOND','SESSION_USER','SHA1','SHA','SIGN','SIN','SOUNDEX','SOUNDS LIKE','SPACE','SQRT','STD','STDDEV','STR_TO_DATE','STRCMP','SUBDATE','SUBSTRING_INDEX','SUBSTRING','SUBSTR','SUBTIME','SUM','SYSDATE','SYSTEM_USER','TAN','TIME_FORMAT','TIME_TO_SEC','TIME','TIMEDIFF',
	'TIMESTAMP','TO_DAYS','TRIM','TRUNCATE','UCASE','UNCOMPRESS','UNCOMPRESSED_LENGTH','UNHEX','UNIX_TIMESTAMP','UPPER','USER','UTC_DATE','UTC_TIME','UTC_TIMESTAMP','UUID','VALUES','VARIANCE','WEEK','WEEKDAY','WEEKOFYEAR','YEAR','YEARWEEK',
	
	//las nuestras
	'else', 'then', 'end',
	
	'ref','cursor',
	'SYS_REFCURSOR','varchar', 'varchar2', 'clob', 'smallint', 'datetime', 'money', 'smallint', 'int', 'null', 'number', 'integer',
	'true', 'false',
	'cd', 'ds', 'mododetalle', 'mododetallesiguiente', 'icono', 'key']);
  var keywords = wordRegexp(["base", "prefix", "select", "distinct", "reduced", "construct", "describe","ask", "from", "named", "where", "order", "limit", "offset", "filter", "optional","graph", "by", "asc", "desc",
			     
				//las nuestras
				'into',
				
				'create', 'alter', 'procedure', 'view', 'package ', 'function', 'set', 'on', 'begin', 'group',  'return',
				'type', 
				'insert', 'update', 'grant', 'left', 'join', 'right', 'join', 'or', 'replace',
				'union', 'group', 'having', 'limit', 'like', 'in', 'out', 'case', 'when', 'exception', 'as', 'is', 'and', 
				'open', 'for'			
				
	    
			     ]);
  var operatorChars = /[*+\-<>=&|]/;

  var tokenizeSparql = (function() {
    function normal(source, setState) {
      var ch = source.next();
      if (ch == "$" || ch == "?") {
        source.nextWhileMatches(/[\w\d]/);
        return "sp-var";
      }
      else if (ch == "<" && !source.matches(/[\s\u00a0=]/)) {
        source.nextWhileMatches(/[^\s\u00a0>]/);
        if (source.equals(">")) source.next();
        return "sp-uri";
      }
      else if (ch == "\"" || ch == "'") {
        setState(inLiteral(ch));
        return null;
      }
      else if (/[{}\(\),\.;\[\]]/.test(ch)) {
        return "sp-punc";
      }
      else if (ch == "#") {
        while (!source.endOfLine()) source.next();
        return "sp-comment";
      }
      else if (operatorChars.test(ch)) {
        source.nextWhileMatches(operatorChars);
        return "sp-operator";
      }
      else if (ch == ":") {
        source.nextWhileMatches(/[\w\d\._\-]/);
        return "sp-prefixed";
      }
      else {
        source.nextWhileMatches(/[_\w\d]/);
        if (source.equals(":")) {
          source.next();
          source.nextWhileMatches(/[\w\d_\-]/);
          return "sp-prefixed";
        }
        var word = source.get(), type;
        if (ops.test(word))
          type = "sp-operator";
        else if (keywords.test(word))
          type = "sp-keyword";
        else
          type = "sp-word";
        return {style: type, content: word};
      }
    }

    function inLiteral(quote) {
      return function(source, setState) {
        var escaped = false;
        while (!source.endOfLine()) {
          var ch = source.next();
          if (ch == quote && !escaped) {
            setState(normal);
            break;
          }
          escaped = !escaped && ch == "\\";
        }
        return "sp-literal";
      };
    }

    return function(source, startState) {
      return tokenizer(source, startState || normal);
    };
  })();

  function indentSparql(context) {
    return function(nextChars) {
      var firstChar = nextChars && nextChars.charAt(0);
      if (/[\]\}]/.test(firstChar))
        while (context && context.type == "pattern") context = context.prev;

      var closing = context && firstChar == matching[context.type];
      if (!context)
        return 0;
      else if (context.type == "pattern")
        return context.col;
      else if (context.align)
        return context.col - (closing ? context.width : 0);
      else
        return context.indent + (closing ? 0 : indentUnit);
    }
  }

  function parseSparql(source) {
    var tokens = tokenizeSparql(source);
    var context = null, indent = 0, col = 0;
    function pushContext(type, width) {
      context = {prev: context, indent: indent, col: col, type: type, width: width};
    }
    function popContext() {
      context = context.prev;
    }

    var iter = {
      next: function() {
        var token = tokens.next(), type = token.style, content = token.content, width = token.value.length;

        if (content == "\n") {
          token.indentation = indentSparql(context);
          indent = col = 0;
          if (context && context.align == null) context.align = false;
        }
        else if (type == "whitespace" && col == 0) {
          indent = width;
        }
        else if (type != "sp-comment" && context && context.align == null) {
          context.align = true;
        }

        if (content != "\n") col += width;

        if (/[\[\{\(]/.test(content)) {
          pushContext(content, width);
        }
        else if (/[\]\}\)]/.test(content)) {
          while (context && context.type == "pattern")
            popContext();
          if (context && content == matching[context.type])
            popContext();
        }
        else if (content == "." && context && context.type == "pattern") {
          popContext();
        }
        else if ((type == "sp-word" || type == "sp-prefixed" || type == "sp-uri" || type == "sp-var" || type == "sp-literal") &&
                 context && /[\{\[]/.test(context.type)) {
          pushContext("pattern", width);
        }

        return token;
      },

      copy: function() {
        var _context = context, _indent = indent, _col = col, _tokenState = tokens.state;
        return function(source) {
          tokens = tokenizeSparql(source, _tokenState);
          context = _context;
          indent = _indent;
          col = _col;
          return iter;
        };
      }
    };
    return iter;
  }

  return {make: parseSparql, electricChars: "}]"};
})();
