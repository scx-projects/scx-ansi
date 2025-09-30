package cool.scx.ansi;

/// ANSI 8Bit 前景色
///
/// @author scx567888
/// @version 0.0.1
public final class Ansi8BitColor implements AnsiElement {

    private final int color;

    public Ansi8BitColor(int color) {
        if (color < 0 || color > 255) {
            throw new IllegalArgumentException("color 必须介于 0 到 255 之间 !!! color : " + color);
        }
        this.color = color;
    }

    @Override
    public String code() {
        return "38;5;" + this.color;
    }

}
