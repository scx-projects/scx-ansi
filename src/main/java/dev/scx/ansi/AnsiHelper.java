package dev.scx.ansi;

import dev.scx.ffi.ScxFFI;
import dev.scx.ffi.type.IntRef;

import java.lang.foreign.MemorySegment;

/// ANSI Helper
///
/// @author scx567888
/// @version 0.0.1
class AnsiHelper {

    static final int STD_OUTPUT_HANDLE = -11;

    static final int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 0x0004;

    /// Windows 10 支持 ANSI 但是默认并没有开启, 这个方法用来开启 Windows 10 的 ANSI 支持
    static void enableWindows10AnsiSupport() {
        Kernel32 KERNEL32 = ScxFFI.createFFI(Kernel32.class, "kernel32");
        // 获取 标准输出设备句柄
        var hOut = KERNEL32.GetStdHandle(STD_OUTPUT_HANDLE);

        // 获取 当前输出模式
        var lpModeMapper = new IntRef();
        KERNEL32.GetConsoleMode(hOut, lpModeMapper);

        //设置 标准输出设备 支持 ANSI, 这里按位或操作 防止覆盖原有模式
        KERNEL32.SetConsoleMode(hOut, lpModeMapper.value() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
    }

    /// 检测是否支持 ANSI
    ///
    /// @return 是否支持
    static boolean checkAnsiSupport() {
        var osName = System.getProperty("os.name");
        var osVersion = System.getProperty("os.version");

        // 不是 Windows 表示支持
        if (!osName.startsWith("Windows")) {
            return true;
        }

        // 不是 Windows 10 以上则表示不支持
        if (!osVersion.startsWith("10") && !osVersion.startsWith("11")) {
            return false;
        }

        //尝试启用 Windows 10 ANSI 支持
        try {
            enableWindows10AnsiSupport();
            return true;
        } catch (Exception e) {
            // 如果开启失败 则表示不支持
            return false;
        }

    }

    /// 提供一些 Kernel32 标准的接口
    interface Kernel32 {

        // https://learn.microsoft.com/zh-cn/windows/console/getstdhandle
        MemorySegment GetStdHandle(int nStdHandle);

        // https://learn.microsoft.com/zh-cn/windows/console/getconsolemode
        int GetConsoleMode(MemorySegment hConsoleHandle, IntRef lpMode);

        // https://learn.microsoft.com/zh-cn/windows/console/setconsolemode
        int SetConsoleMode(MemorySegment hConsoleHandle, long dwMode);

    }

}
