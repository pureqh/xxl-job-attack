package com.pureqh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author：index
 * @Date：2025/3/6 16:06
 */
public class test {
    public static void main(String[] args) {
        try {
            // 获取Runtime实例
            Runtime runtime = Runtime.getRuntime();

            // 执行命令
            Process process = runtime.exec("whoami");

            // 读取命令的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // 打印命令输出
            }

            // 等待命令执行完成
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
