			
# mhdt-common


<a href="http://www.sanen.online"><img src="http://download.sanen.online/assets/img/logo.png"/></a>

## 更新日志

### 1.3.2
* 扩展 Platform.exec方法执行命令
* 扩展 ImageUtility 获取图片像素，高斯模糊
* 扩展 DegistTool.md5，可以自定义加盐且新增多个重载
* 扩展 Validate ， Reflect 对枚举支持。
* 扩展 Platform类。
* 扩展 SimpleRandom.nextChinese(int count); 随机中文，常用词库取自 笑傲江湖.txt。
* 修改 合并StringUtility.join 各种重载为一个方法。
* 扩充 Validate类。
* 扩展 Urls.formatNet。
* 扩展 StringUtility.format 格式化字符串。
* 扩展 Urls #post / #delete / #put / #get 适应restful请求。
* String[] UImanager.getSystemDefaultFonts(); 获取系统字体 。
* 扩展 Properties Properties.screening(String prefix) ,根据前缀筛选属性。
* 修改 Properties注释标识为#	。
* 扩展 StringUtility.join支持集合和字符串连接。
* 扩展 LogManager.setDebugEnable(boolean flag)。
* 扩展 FileUtility.getPureName(String fileName)。
* 扩展 FileUtility.getFormat(String fileName)。
* 修改	Log包输出格式	。
 <br><br>


2018/11/12 ~ ?
			
			
### 1.3.1

* 扩展 Statistics.countLines 统计后缀文件数及行数，如统计项目java代码行数和文件数。
* 修改 Urls.request支持 Https请求。
* 扩展 FreeMarker 作为freemarker的工具类使用，使用前依赖jar包。
* 修改 DegistTool.md5为 二进制/十六进制 编码 。
* 修改 Urls.request第二个参数为Object，可以是map也可以是Object.toString(post请求中作为参数传递使用)。
* 扩展 com.mhdt.toolkit下ADDPreferences作为系统级偏好设置工具类。
* 扩展 Bean.structured(Object obj); 获取对象的构造 返回值 Map<String,Class<?>>。
* 扩展 Reflect.isStatic(Field field);Reflect.isStatic(Method method); 判断字段，方法是否静态。
* 扩展 Collections.toArray(Class<R> cls,Collection<T> list,Function<T, R> function) 集合转数组 - 2018/07/03
* 新增 AppclassLoad.getClassName(String packageName, boolean childPackage) 获取包下所有类名。
* 新增 RequestResult.perform(Consumer<RequestResult> consumer) RequestResult的函数式接口。
* 重新设计Log接口且不完全实现。
* 扩展 StringUtility.getStackMessage(Throwable e); 获取异常的堆栈信息。
* 新增 com.mhdt.toolkit.Assets  断言。<br><br>


2018/04/12~2018/10/14

### 1.3.0
* 扩展 Log.etDebugEnabled(boolean flag) 、 boolean isDebugEnabled()。
* 	修改 ProxyHandel接口参数和target类参数一致，afterHandel可以获取target接口返回值。
* 扩展 public static Field[] getFields(Class<?> cls)。
* 扩展 Counter接口及实现类 SimpleCounter 计数。
* 扩展 在annotation包中为Cdm添加注解 NoUpdate 、NoInsert、TableName等，以CDM文档中的注解为主。
* 扩展 Template模板类，加载模板文件替换其中参数(@{paremer})获取最终结果。
* 修改 Urls.request(String url,Map<String,Object> data)方法data参数为map。
* 扩展 RequestResult.parse(byte[] bytes)来配合Http请求，对service返回的json解析。
* 修改 bean注入优先执行set方法，其次字段注入。
* 修改 Reflect.getField在完全匹配失败的情况下会采取不再关注属性名大小写 ;父类检索实现改为递归。
* 修改 Bean.setInject set注入以value值查找方法。
* 修改 Bean.getInject 优先通过get方法获取。
* 扩展 DegistTool.md5(File file) ,文件MD5。
* 扩展 Urls.downLoad用于文件下载，详情看该方法的重载。
	
```
发现Scheduler接口实现糟糕，有时间重写的时候从以下几点出发
	1. 单线程和多线程的观察者模式，任务驱动（即有待处理任务->线程工作;没有则线程进入休眠）。
	2. 支持回调。
```
	  
* 扩展 Platform.getOSName获取平台信息。
* 扩展 Print.info 支持代码定位，且在第三方支持（JANSI）下区分字体颜色，idea下可直接使用，eclipse需在商店下载 ANSI Console以支持ANSI字体。<br><br>

2017/10/22 ~ 2018/03/26
	
### 1.2.9
* 扩展 DegistTool.byteToHexStr(byte[] bytes) 。
* 扩展 DegistTool.byteToHexStr(byte bt)			将字节转换为十六进制字符串 。
* 修改 PathUtil类的正确性,让其对web项目的适应性更强 。
* 修改 DegistTool.	byte[] hexStringToBytes(String hexString)  16进制字符串转为字节数组。
* 扩展 Urls.byte[] request(String url,String data) - 发送post/get请求。
* 扩展 Urls.String getDistrictByIp(String ip) - 通过ip地址获取省市信息 。
* 扩展 proxy包-> ProxyFactory代理工厂 、ProxyHandel代理处理接口 。
* 扩展 AuthcodeUtil 验证码工具类，可以生成指定字符串的验证码图片。
* 扩展 StringUtility.indexOf(String content, String pattern) 扩展indexOf支持【正则】索引，如果索引到【正则】匹配到的字符则返回下标，否则返回-1；
* 扩展 RandomXS128 (摘自libgdx引擎) 更快的随机算法，同时SimpleRandom继承自RandomXS128。
* 扩展 RequestResult 作为一般性的请求结果返回，通过构造模式来生成。
* 修改 Logger类，在系统抛出异常记录到日志时自动附带触发时间。
* 修改 PathUtil.underProject 、PathUtil.getClassPath 在打包运行状态下的异常，原因是获取路径为null导致。
* 修改 FileIO.getContent所有重构方法，如果获取不到字符串则返回空字符串(之前是返回null),这在作为IO基类的时候容易缺少空值判断导致其它异常。
* 修改 Properties通过key获取值，key不再区分大小写 <br><br>


2017/07/04 ~ 2017/10/20


### 1.2.8
* 修改 Bean.populate方法字段无法对应的异常提示。 - 2017/02/16
* 扩展 boolean Validate.isEnglish(Object content) 判断是否纯英文。 
* 扩展 boolean isEnglishOrNum(Object content) 判断纯数字/英文。 
* 扩展 SimpleRandom 作为随机辅助类。
* 扩展 FileIO类重载方法 void remove(File file)。 - 2017/03/07
* 扩展 FileUtily.findByPrefix(File folder, Object prefix) 根据文件名前缀索引文件。
* 扩展 AppclassLoad.FileClassLoader作为加载器使用。
* 扩展 Reflect.getMethodByName(Class<?> cls,String methodName)。
* 扩展 StringUtillty.capitalizeFirstLetter(String name)  字符串首字母大写。
* 扩展 Urls public static boolean open(String url)  打开链接。
* 扩展 MathUtility.keepDecimalPoint(double f,int digit) 保留小数一定位数的小数点且默认对后一位四舍五入。
* 扩展 ImageUtilityADDrotate(Image src, int angel)旋转图片。
* 修改 Logger类路径问题，让其适应web项目。<br><br>


2017/03/03 ~ 2017/07/04

### 1.2.7	
* 修改 StringUtility.parseNumber(String content)在解析类似二零零七年时的错误. 	 
* 扩展 Reflect.Type[] getGeneric(Field field)获取字段泛型(没有获取泛型返回null)。
* 扩展 Reflect.newArray(Class<? extends T> cls,int len),根据泛型创建数组(指定大小)。
* 扩展 annotation包,@Column @Nodb 注解
* 扩展 Reflect.[ Field getFieldByColumnValue(Object obj,String column)] - 对@Column注解支持。
* 修改 Print(控制台打印类)输出格式 
* 修改 FileIO.writeArray(File file, int[][] array)----->>writeArray(File file, Object[][] array)。
* 修改 Bean.parse(Class<T> beanClass, Map<String, Object> map) 异常没有抛出导致Bug不可判断。
* 修改 Scheduler在waitAndShutDown();之前，可以一直接受任务注册。
* 修改 Scheduler单线程工作状态下waitAndShutDown任务还没执行完即强行关闭.
* 扩展 toolkit包下添加Collections工具类实现类似Mysql语法的
* 扩展 <T> ArrayList<T> limit(Collection<T> collecttion,int start,int count); <br><br>


2016/12/05 ~ 2017/02/16

### 1.2.6
* 修改 FileIO.getContent(File file)文件不能完整读取问题。
* 修改 String.formatNumber(String number)支持大写1-10 。
* 修改 DateUtility.stringToInt 导致带时分秒的字符串时间不能处理成int值。
* 修改 StringUtility.parseNumber(String content)在处理列如:1亿3千零5万时会出现的解析结果错误。
* 修改 DateUtility.stringToInt(String str)支持更多的时间格式 (To do)。
* 修改 StringUtily.parseNumber解析不正确;
* 扩展 Scheduler包下Scheduler类(调度器),提交Runable接口执行任务。
* 扩展 DegistTool.sha(String pwd); sha同md5为单向加密。
* 扩展 Filed[] Reflect.getFields(Object bean)获取对象所有字段; 
* 扩展 StringUtility.contains(String source,char c),contains(String source, String matchStr)返回字符串中包含匹配字符数量; 
* 修改 boolean Validate.isNum(String str);
* 修改 formatNumber方法名改为formatArabNumber;<br><br> 


2016/10/11 - 2016/12/02
	
### 1.2.5 
	
* 修改 Logger类日志路径问题.
* 修改 StringUtility类的parseNum(String number),将多个方法进行了整合,对字符串分析后将其中包含数字进行统一小写(0-9)返回的是浮点数转换后的字符串
* 修改 Validate.isUrl(String url),之前的范围过宽导致错误太过明显,更新之后限制了前后缀
* 修改 Validate.hasField(Object obj,String fieldName)存在BUG <br>
* 扩展 Validate.link(String url)(验证url是否可以访问)返回Object数组,第一个为boolean同islink(String url),第二个参数为状态码,如：200,404
* 扩展 PathUtil.underWebContent()获取WebContant下绝对路径
* 扩展 Logger类配置参数file_size,来限制单个日志文件大小,超出部分将新建文件.
<br><br> 

-2016/09/23 -  2016/10/08

### 1.2.4 
* 扩展 Validate. isLink(String html) 验证域名是否可访问(需要联网)。-2016/08/10
* 扩展 StringUtilly. toString(InputStream in) 将流转换字符串。-2016/08/10
* 扩展 Reflect.getField(Object obj, String fieldName),即使对象为子类现在也可以获取其所有字段。<br><br> -2016/08/10

### 1.2.3 
* 新增 Cache接口,以及3种实现方式。<br>
	FIFOCache 先进先出。<br>
	LFUCache  根据使用频率（次数）淘汰。<br>
	LRUCache  根据最近使用记录淘汰。<br><br>- 2016/08/08

### 1.2.2 
* 新增 Logger,功能基本类似Log4J,异常自动捕捉,配置文件自动生成,目前可以配置2个参数,path（日志路径），isApend(是否追加)。<br><br>  - 2016/08/07/28

### 1.2.1
若干BUG 修改,完善了工具类的一些实现方式。

### 1.2.0 
BUG修复,方法完善,对包名类名做了些调整。<br><br>- 2016/07/22


	
	


 
	


