# xxl-job-attack
xxl-job漏洞综合利用工具


# 该工具可检测以下漏洞：
默认口令\n
api接口未授权Hessian反序列化<br>
Executor未授权命令执行<br>
默认accessToken身份绕过<br>

# 关于内存马
内存马使用了xslt，由于测试直接打入内存马有时会失败，所以选择直接打入agent内存马\n
如需自定义可替换resources下的ser文件，其中agent.ser为agent内存马、xslt.ser会落地为/tmp/2.xslt\n
