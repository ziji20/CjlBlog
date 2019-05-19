package com.cjl.realm;


import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cjl.entity.Blogger;
import com.cjl.service.BloggerService;

public class MyRealm extends AuthorizingRealm{

	@Resource
	private BloggerService bloggerService;

	/**
	 * ��֤��ǰ��¼���û�
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String userName=(String)token.getPrincipal();//��ȡ�����Ϣ(�û�������½����)
		Blogger blogger=bloggerService.getByUserName(userName);
		if (blogger!=null) {
			SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger); // ��ǰ�û���Ϣ�浽session��
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(blogger.getUserName(),blogger.getPassword(),"ziji");
			return authcInfo;
		}
		return null;
	}
	
	/**
	 * Ϊ����ǰ��¼���û������ɫ��Ȩ
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return null;
	}
}
