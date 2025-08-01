package cool.scx.ansi;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static cool.scx.ansi.AnsiColor.*;
import static cool.scx.ansi.AnsiHelper.checkAnsiSupport;

/// ANSI 用于在控制台上打印带有颜色和样式的文本
///
/// @author scx567888
/// @version 0.0.1
public final class Ansi {

    // 是否启用 ANSI 支持
    private static final boolean SUPPORTED = checkAnsiSupport();

    private final List<AnsiItem> items;

    public Ansi() {
        this.items = new ArrayList<>();
    }

    public static Ansi ansi() {
        return new Ansi();
    }

    private static AnsiElement[] filterAnsiElement(AnsiElement... elements) {
        if (elements.length < 2) {
            return elements;
        }
        //颜色 和 背景色 只留一个, 样式可以存在多个但是需要去重
        AnsiElement ansiColor = null;
        AnsiElement ansiBackground = null;
        var ansiStyleSet = EnumSet.noneOf(AnsiStyle.class);

        for (var element : elements) {
            switch (element) {
                case AnsiColor _, Ansi8BitColor _ -> ansiColor = element;
                case AnsiBackground _, Ansi8BitBackground _ -> ansiBackground = element;
                case AnsiStyle ansiStyle -> ansiStyleSet.add(ansiStyle);
                default -> {
                }
            }
        }

        //为了极致的性能优化 直接创建数组而不是 使用 List 
        int size = (ansiColor != null ? 1 : 0) + (ansiBackground != null ? 1 : 0) + ansiStyleSet.size();
        var result = new AnsiElement[size];
        int index = 0;

        if (ansiColor != null) {
            result[index] = ansiColor;
            index = index + 1;
        }
        if (ansiBackground != null) {
            result[index] = ansiBackground;
            index = index + 1;
        }
        for (var element : ansiStyleSet) {
            result[index] = element;
            index = index + 1;
        }
        return result;
    }

    private static AnsiElement[] filterAnsiElement(AnsiElement[] a1, AnsiElement a2) {
        if (a1.length == 0) {
            return new AnsiElement[]{a2};
        }
        var result = new AnsiElement[a1.length + 1];
        System.arraycopy(a1, 0, result, 0, a1.length);
        result[a1.length] = a2;
        return filterAnsiElement(result);
    }

    public Ansi add(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements)));
        return this;
    }

    public Ansi defaultColor(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, DEFAULT)));
        return this;
    }

    public Ansi black(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BLACK)));
        return this;
    }

    public Ansi red(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, RED)));
        return this;
    }

    public Ansi green(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, GREEN)));
        return this;
    }

    public Ansi yellow(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, YELLOW)));
        return this;
    }

    public Ansi blue(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BLUE)));
        return this;
    }

    public Ansi magenta(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, MAGENTA)));
        return this;
    }

    public Ansi cyan(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, CYAN)));
        return this;
    }

    public Ansi white(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, WHITE)));
        return this;
    }

    public Ansi brightBlack(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BRIGHT_BLACK)));
        return this;
    }

    public Ansi brightRed(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BRIGHT_RED)));
        return this;
    }

    public Ansi brightGreen(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BRIGHT_GREEN)));
        return this;
    }

    public Ansi brightYellow(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BRIGHT_YELLOW)));
        return this;
    }

    public Ansi brightBlue(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BRIGHT_BLUE)));
        return this;
    }

    public Ansi brightMagenta(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BRIGHT_MAGENTA)));
        return this;
    }

    public Ansi brightCyan(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BRIGHT_CYAN)));
        return this;
    }

    public Ansi brightWhite(Object o, AnsiElement... ansiElements) {
        items.add(new AnsiItem(o, filterAnsiElement(ansiElements, BRIGHT_WHITE)));
        return this;
    }

    public Ansi ln() {
        return add(System.lineSeparator());
    }

    public void print(boolean useAnsi) {
        System.out.print(this.toString(useAnsi));
    }

    public void print() {
        print(true);
    }

    public void println(boolean useAnsi) {
        ln().print(useAnsi);
    }

    public void println() {
        println(true);
    }

    public String toString(boolean useAnsi) {
        var sb = new StringBuilder();
        //系统支持 && 用户启用
        if (SUPPORTED && useAnsi) {
            for (var i : items) {
                i.buildEnabled(sb);
            }
        } else {
            for (var i : items) {
                i.buildDisabled(sb);
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(true);
    }

}
