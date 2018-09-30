package com.qianfeng.realm;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.qianfeng.dao.IUsersDao;
import com.qianfeng.entity.Users;


public class MyRealm extends AuthorizingRealm{
	
	@Autowired
    private IUsersDao userDao;
	
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		
		//获取输入的用户名
		String name = (String) principals.getPrimaryPrincipal();
		

		
		List<String> roleList = userDao.findRoleByUname(name);
		List<String> permitList = userDao.findPermitByUname(name);
		
		Set<String> roleSet = new HashSet<>(roleList);
		Set<String> permitSet = new HashSet<>(permitList);
		//授权信息对象
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//设置用户对应的角色
		info.setRoles(roleSet);
		//设置对应的权限
		info.setStringPermissions(permitSet);
		
		return info;
	}
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		//获取身份信息
		String no = (String) token.getPrincipal();
		//从数据库中根据用户名获取密码信息
		Users user = userDao.findByName(no);
		if(user == null) {
			throw new UnknownAccountException("用户名错误");
		}
		
		String password = user.getPassword();
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(no, password, this.getName());
		
		return info;
	}

}
