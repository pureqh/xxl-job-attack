# xxl-job-attack
xxl-job漏洞综合利用工具<br>

![image](https://github.com/user-attachments/assets/0f5c92bf-7308-4c2f-8bf9-b03e5099ecf1)


# 该工具可检测以下漏洞：
1、默认口令<br>
2、api接口未授权Hessian反序列化（只检测是否存在接口，是否存在漏洞需要打内存马验证）<br> 
3、Executor未授权命令执行<br>
4、默认accessToken身份绕过<br>

# 关于内存马
1、内存马使用了xslt，为了提高可用性提供了冰蝎Filter和Vagent两种内存马<br>
2、如需自定义可替换resources下的ser文件，其中filter.ser为冰蝎filter内存马、agent.ser为冰蝎agent内存马、xslt.ser会落地为/tmp/2.xslt,
<br>
3、内容为使用exec执行/tmp/agent.jar、exp.ser则是加载/tmp/2.xslt<br>
4、vagent内存马连接配置:冰蝎:http://ip:port/xxl-job-admin/api/luckydayb,其他类型内存马类似，
将favicon改为luckyday即可<br>
5、Behinder内存马连接配置: <br>
 密码: Qqpniuqs<br>
 请求路径: /luckydayb<br>
 请求头: Referer: Piyzr<br>
 脚本类型: JSP<br>
 内存马类名: org.apache.SessionNauoFilter<br>
 注入器类名: com.fasterxml.jackson.eo.JSONUtil<br>
6、由于agent发送文件较大，所以可能导致包发不过去，建议多试几次或者将超时时间延长<br>
7、由于Hessian反序列化基本上都是直接发二进制包，所以理论上讲其他的Hessian反序列化漏洞也可以打<br>

# 声明
仅供学习和授权的测试，任何人不得用来做违法犯罪活动，出现问题均与本人无关
