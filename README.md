##### Volley是Volley工程
##### Volleyutils是volley的简单封装

## Volley Utils使用指南
---
1、引入maven仓库 在工程build中
    配置maven参考 maven{ url 'http://maven.etongwl.com/'}

2、在工程buld中引入VolleyUtils
    compile 'com.etongwl.volleyutils:VolleyUtils:1.0.0'

3、实现HttpConfig中抽象方法setUrlByCode,这个方法配置请求的URL路径
    例如
    publicvoid setUrlByCode(){
            urls.put("login",  "http://xxxxxxxx/mdcom/mobile/mobileUsers/login.jhtml");
    }

4、使用HttpRequestManager发起网络请求（请求底层使用的Volley，请求参数是Map中设置）