package com.steve28.stedit.highlighter

import androidx.compose.ui.graphics.Color

object PythonHighlighter: Highlighter {
    private const val keywords = "(def|while|for|in|is|None|True|False|yield|pass|class|return|and|or|not|as|assert|break|try|raise|except|finally|if|else|lambda|del|nonlocal|global|import|from|with)"
    private const val builtin = "(ArithmeticError|AssertionError|AttributeError|BaseException|BlockingIOError|BrokenPipeError|BufferError|BytesWarning|ChildProcessError|ConnectionAbortedError|ConnectionError|ConnectionRefusedError|ConnectionResetError|DeprecationWarning|EOFError|Ellipsis|EnvironmentError|Exception|FileExistsError|FileNotFoundError|FloatingPointError|FutureWarning|GeneratorExit|IOError|ImportError|ImportWarning|IndentationError|IndexError|InterruptedError|IsADirectoryError|KeyError|KeyboardInterrupt|LookupError|MemoryError|ModuleNotFoundError|NameError|NotADirectoryError|NotImplemented|NotImplementedError|OSError|OverflowError|PendingDeprecationWarning|PermissionError|ProcessLookupError|RecursionError|ReferenceError|ResourceWarning|RuntimeError|RuntimeWarning|StopAsyncIteration|StopIteration|SyntaxError|SyntaxWarning|SystemError|SystemExit|TabError|TimeoutError|TypeError|UnboundLocalError|UnicodeDecodeError|UnicodeEncodeError|UnicodeError|UnicodeTranslateError|UnicodeWarning|UserWarning|ValueError|Warning|WindowsError|ZeroDivisionError|_|__build_class__|__debug__|__doc__|__import__|__loader__|__name__|__package__|__spec__|abs|all|any|ascii|bin|bool|breakpoint|bytearray|bytes|callable|chr|classmethod|compile|complex|copyright|credits|delattr|dict|dir|divmod|enumerate|eval|exec|exit|filter|float|format|frozenset|getattr|globals|hasattr|hash|help|hex|id|input|int|isinstance|issubclass|iter|len|license|list|locals|map|max|memoryview|min|next|object|oct|open|ord|pow|print|property|quit|range|repr|reversed|round|set|setattr|slice|sorted|staticmethod|str|sum|super|tuple|type|vars|zip)"

    private val Builtin = Keyword.fixed(builtin, Color.Magenta)
    private val Comment = Keyword("#[^\\n]*".toRegex(), Color(0xFF006FDD), 3)
    private val PKeyword = Keyword.fixed(keywords, Color(0xFF44A48E), 2)
    private val FuncName = Keyword("(?<=def\\s)(\\w+)(?=\\s*\\()".toRegex(), Color(0xFF5E5EFF))
    private val PString = Keyword("[rbf]?(['\"])((\\\\\\1)|(?!(\\1|\\\\\\1)).)*\\1".toRegex(), Color(0xFF8080FF))

    override val keyword = listOf(PKeyword, Builtin, FuncName, PString, Comment)
}