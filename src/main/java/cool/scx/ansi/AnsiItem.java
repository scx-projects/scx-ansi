package cool.scx.ansi;

import static cool.scx.ansi.AnsiStyle.RESET;

/// ANSI Item
///
/// @author scx567888
/// @version 0.0.1
record AnsiItem(Object value, AnsiElement... elements) {

    private static final String ENCODE_START = "\033["; //起始字符
    private static final char ENCODE_JOIN = ';'; //合并字符
    private static final char ENCODE_END = 'm'; //结束字符

    private void appendAnsiElements(StringBuilder sb) {
        var isFirst = true;
        for (var element : elements) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(ENCODE_JOIN);
            }
            sb.append(element.code());
        }
    }

    public void buildEnabled(StringBuilder sb) {
        //如果没有转义符 则直接返回
        if (elements.length == 0) {
            buildDisabled(sb);
            return;
        }

        //1, 添加 ANSI 转义符
        sb.append(ENCODE_START);
        appendAnsiElements(sb);
        sb.append(ENCODE_END);

        //2, 添加文字内容
        sb.append(value);

        //3, 添加重置转义符
        sb.append(ENCODE_START);
        sb.append(RESET.code());
        sb.append(ENCODE_END);
    }

    public void buildDisabled(StringBuilder sb) {
        sb.append(value);
    }

}
