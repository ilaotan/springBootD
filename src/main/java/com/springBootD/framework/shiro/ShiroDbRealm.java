package com.springBootD.framework.shiro;

import java.util.HashSet;
import java.util.Set;

import com.springBootD.framework.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShiroDbRealm extends AuthorizingRealm{
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principals);
			SecurityUtils.getSubject().logout();
			return null;
		}
		
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		if (null != shiroUser) {
			String uid = shiroUser.getId();
			String roleId = shiroUser.getRoleid();
			// 添加角色及权限信息
			SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
			try {
				if (StringUtils.isNotBlank(shiroUser.getCode())) {
					sazi.addRole(shiroUser.getCode());
				}
//				sazi.addRoles(userPermissionService.getRolesAsString(uid));//添加多个角色
				if(shiroUser.getCode().equals(Constants.SUPER_ADMIN_CODE)){
					Set<String> set = new HashSet<String>();
					set.add("*");
					sazi.addStringPermissions(set);
				}else{
					//sazi.addStringPermissions(userPermissionService.getPermissionsAsString(uid,roleId));
					sazi.addStringPermission("user:view");
				}
				logger.debug("当前用户的权限是{}",sazi.getStringPermissions());
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return sazi;
		}else{
			return null;
		}
	}	

	/* 
	 * tanliansheng
	 * 2015年1月15日09:04:34
	 * 父类默认方法会拿null。覆盖一下父类方法 使用role的code当缓存的key
	 * @see org.apache.shiro.realm.AuthorizingRealm#getAuthorizationCacheKey(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		return "autz-"+shiroUser.getCode();
	}

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToke = (UsernamePasswordToken) authcToken;

		String username = usernamePasswordToke.getUsername();

		//md5(salt+md5(password))，处理传过来的密码
		//String pwd = Md5PwdEncoder.encodePassword(String.valueOf(authcToken.getPassword()), "");
		//String password = user.getSalt() + pwd;
		String password = "123456";
		usernamePasswordToke.setPassword(password.toCharArray());

		return new SimpleAuthenticationInfo(new ShiroUser("用户id", "roleId",
				username,"account"), String.valueOf(usernamePasswordToke.getPassword()), getName());
	}
}
