	package cn.tedu.action;

import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * 所有控制器的基类，用于封装常用的session request等，用于提供给子类复用
 * 
 * ActionSupport是Struts2提供的Action基础类,用于复用公共代码的类
 * @author Student
 *
 */
public class BaseAction extends ActionSupport 
implements SessionAware,RequestAware,ApplicationAware{

	protected Map<String,Object> session;
	protected Map<String,Object> request;
	protected Map<String,Object> application;
	
	public void setSession(Map<String, Object> session) {
		this.session=session;
		
	}
	public void setRequest(Map<String, Object> request) {
		this.request=request;
		
	}
	public void setApplication(Map<String, Object> application) {
		this.application=application;
		
	}	
}


