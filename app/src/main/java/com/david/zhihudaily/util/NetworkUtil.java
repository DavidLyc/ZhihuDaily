package com.david.zhihudaily.util;

public class NetworkUtil {

    public static boolean isNetworkAvailable() {
        String ip = "223.5.5.5";  //阿里巴巴公共ip
        ShellUtils.CommandResult cmdResult = ShellUtils.execCmd(String.format("ping -c 1 %s", ip), false);
        return cmdResult.result == 0;
    }
}
