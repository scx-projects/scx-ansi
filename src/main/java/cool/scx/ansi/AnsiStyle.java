package cool.scx.ansi;

/// ANSI 基本样式
///
/// @author scx567888
/// @version 0.0.1
public enum AnsiStyle implements AnsiElement {

    RESET("0"),
    BOLD("1"),
    FAINT("2"),
    ITALIC("3"),
    UNDERLINE("4"),
    BLINK("5"),
    REVERSE("7"),
    HIDDEN("8"),
    CROSSED_OUT("9"),
    DOUBLE_UNDERLINE("21"),
    OVERLINE("53");

    private final String code;

    AnsiStyle(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return this.code;
    }

}
