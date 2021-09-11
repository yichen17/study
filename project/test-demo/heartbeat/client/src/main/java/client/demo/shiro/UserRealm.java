package client.demo.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/13 9:55
 * @describe 用于用户认证
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Value("${yichen.username}")
    private String username;
    @Value("${yichen.password}")
    private String password;

    /**
     * 编写授权逻辑
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        //获取当前登录对象
//        Subject subject = SecurityUtils.getSubject();
//        Account account = (Account) subject.getPrincipal();
//
//        //设置角色
//        Set<String> roles = new HashSet<>();
//        roles.add(account.getRole());
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
//
//        //设置权限
//        info.addStringPermission(account.getPerms());
//        return info;
        return null;
    }

    /**
     * 编写认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if(this.username.equals(token.getUsername())&&this.password.equals(String.valueOf(token.getPassword()))){
            log.info("用户 =》 {} 登陆成功",token.getUsername());
            return new SimpleAuthenticationInfo(this.username,this.password,"UserRealm");
        }
        else{
            log.info("登陆失败，用户名为{}",token.getUsername());
        }
        return null;
    }
}
