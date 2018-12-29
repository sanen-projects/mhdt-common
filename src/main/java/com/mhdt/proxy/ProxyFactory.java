package com.mhdt.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * The agent factory <br>
 * Simple implementation of dynamic proxy objects<br>
 *  if necessary, requires a {@link ProxyHandel} instance<br>
 *  You can also set the name of the method specified for execution
 * @author 懒得出风头 <br>
 * Date： 2017/09/21 <br>
 * Time: 10:50
 */
public class ProxyFactory implements InvocationHandler{
	
	Object target;
	
	ProxyHandel proxyHander;
	
	private ProxyFactory() {
		
	}
	
	/**
	 * 通过代理创建对象
	 * @param target - 代理目标
	 * @param handel - handel
	 * @param clss - 接口
	 * @return
	 */
	public static Object getInstance(Object target,ProxyHandel handel,Class<?>... clss) {
		ProxyFactory proxy = new ProxyFactory();
		proxy.target = target;
		proxy.proxyHander = handel;
		
		return Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				clss==null?target.getClass().getInterfaces():clss,
				proxy);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		if(proxyHander!=null)
			proxyHander.preHandel(proxy,method,args);
		
		Object result = method.invoke(target, args);
		
		if(proxyHander!=null)
			proxyHander.afterHandel(result,proxy,method,args);
		
		return result;
	}
}
