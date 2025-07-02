package cool.scx.ansi.test;

import cool.scx.ansi.*;
import org.testng.annotations.Test;

public class AnsiTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    @Test
    public static void test1() {
        var ansi = Ansi.ansi();
        for (var color : AnsiColor.values()) {
            for (var ansiBackground : AnsiBackground.values()) {
                ansi.add("abc", color, ansiBackground);
            }
            ansi.ln();
        }
        ansi.println();
    }

    @Test
    public static void test2() {
        var ansi = Ansi.ansi();
        for (int i = 0; i < 256; i = i + 1) {
            for (int j = 0; j < 256; j = j + 1) {
                ansi.add("0", new Ansi8BitColor(i), new Ansi8BitBackground(j), AnsiStyle.BOLD, AnsiStyle.ITALIC, AnsiStyle.UNDERLINE);
            }
            ansi.ln();
        }
        ansi.println();
    }

    @Test
    public static void test3() {
        var ansi = Ansi.ansi();
        for (AnsiStyle value : AnsiStyle.values()) {
            for (int i = 0; i < 10; i = i + 1) {
                ansi.add(value.name() + "  ", value);
            }
            ansi.ln();
        }
        ansi.println();
    }

}
