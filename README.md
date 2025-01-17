# xxl-job-attack
xxl-job漏洞综合利用工具

![image](https://github.com/user-attachments/assets/e3ec370a-590c-4d6b-b06b-c9e056105606)

# 该工具可检测以下漏洞：
默认口令\n
api接口未授权Hessian反序列化<br>
Executor未授权命令执行<br>
默认accessToken身份绕过<br>

# 关于内存马
内存马使用了xslt，由于测试直接打入内存马有时会失败，所以选择直接打入agent内存马<br>
如需自定义可替换resources下的ser文件，其中agent.ser为agent内存马、xslt.ser会落地为/tmp/2.xslt<br>
内容为使用exec执行/tmp/agent.jar、exp.ser则是加载/tmp/2.xslt<br>
默认注入vagent内存马，连接信息冰蝎:http://ip:port/xxl-job-admin/api/luckydayb,其他类型内存马类似，
将favicon改为luckyday即可<br>
由于agent发送文件较大，所以可能导致包发不过去，建议多试几次或者将超时时间延长<br>
由于打的是Hessian反序列化，所以理论上讲Hessian反序列化也可以打<br>
