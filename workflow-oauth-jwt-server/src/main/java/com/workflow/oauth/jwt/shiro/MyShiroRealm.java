/*
package com.workflow.oauth.jwt.shiro;

import com.workflow.common.entity.system.SysPermission;
import com.workflow.common.entity.system.SysUserInfo;
import com.workflow.oauth.jwt.service.SysPermissionService;
import com.workflow.oauth.jwt.service.SysUserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.List;

*/
/**
 * 对登录进行授权，对角色、权限进行验证等
 *//*

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserInfoService userinfoService;

    @Resource
    private SysPermissionService permissionService;

    */
/**
     * 授权
     * @param principalCollection
     * @return
     *//*

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer id = (Integer) principalCollection.getPrimaryPrincipal();
        List<SysPermission> permissionList = permissionService.loadUserPermission(id);
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        permissionList.forEach(p->info.addStringPermission(p.getPerUrl()));
        return info;
    }

    */
/**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     *//*

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        */
/** 获取用户信息 **//*

        SysUserInfo user = userinfoService.findByUserName(username);
        if(user==null) throw new UnknownAccountException();
        if (0==user.getEnable()) {
            throw new LockedAccountException(); // 帐号锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getId(), //用户
                user.getPassword(), //密码
                ByteSource.Util.bytes(username),
                getName() //realm name
        );
        // 把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("AdminSession", user);
        session.setAttribute("AdminSessionId", user.getId());
        return authenticationInfo;
    }
}
*/
