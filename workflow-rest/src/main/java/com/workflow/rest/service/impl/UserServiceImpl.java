package com.workflow.rest.service.impl;

import com.workflow.common.base.BaseServiceImpl;
import com.workflow.common.dao.SysUserInfoDao;
import com.workflow.common.entity.SysUserInfo;
import com.workflow.rest.service.SysRegistUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<SysUserInfoDao, SysUserInfo> implements SysRegistUserService {

    /*@Autowired
    private RedisService<Integer> redisSocketService;

    @Autowired
    private RedisService<SysRegistUser> redisService;

    @Value("${REDIS_USERID_KEY}")
    private String REDIS_USERID_KEY;

    @Value("${REDIS_USER_KEY}")
    private String REDIS_USER_KEY;

    @Value("${REDIS_USER_TIME}")
    private Integer REDIS_USER_TIME;
*/
   /* @Override
    public boolean checkUserName(String username) {
        SysRegistUser user = repository.findByUsername(username);
        if (user == null) return true;
        return false;
    }

    @Override
    public boolean checkUserEmail(String email) {
        SysRegistUser user = repository.findByEmail(email);
        if (user == null) return true;
        return false;
    }

    @Override
    public SysRegistUser findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void createUser(String email, String username, String password) {
        SysRegistUser user = new SysRegistUser();
        user.setEmail(email);
        user.setUserName(username);
        user.setInitTime(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        repository.save(user);
    }



    @Override
    public String LoginUser(SysRegistUser user) {
        String token = UUID.randomUUID().toString();
        redisService.cacheString(REDIS_USER_KEY + token, user, REDIS_USER_TIME);
        redisSocketService.cacheSet(REDIS_USERID_KEY,user.getId());
//        loginId.add(user.getId());//维护一个登录用户的set
        return token;
    }

    @Override
    public SysRegistUser getUserByToken(String token) {
        SysRegistUser user = redisService.getStringAndUpDate(REDIS_USER_KEY + token, REDIS_USER_TIME);
        return user;
    }

    @Override
    public void LogoutUser(String token) {
        SysRegistUser user = getUserByToken(token);
        redisService.deleteString(REDIS_USER_KEY + token);
        redisSocketService.deleteSet(REDIS_USERID_KEY,user.getId());
//        loginId.remove(user.getId());//维护一个登录用户的set
    }

    @Override
    public void updateUser(String token, String username, String signature, Integer sex) {
        SysRegistUser cacheuser = redisService.getString(REDIS_USER_KEY + token);
        if (cacheuser == null) throw new ServiceProcessException("session过期,请重新登录");
        SysRegistUser user = repository.findOne(cacheuser.getId());
        user.setUserName(username);
        user.setSex(sex);
        user.setSignature(signature);
        repository.save(user);
        redisService.cacheString(REDIS_USER_KEY + token, user, REDIS_USER_TIME);
    }

    @Override
    public void updataUserIcon(String token, String icon) {
        SysRegistUser cacheuser = redisService.getString(REDIS_USER_KEY + token);
        if (cacheuser == null)
            throw new ServiceProcessException("用户Session过期，请重新登录");
        SysRegistUser user = repository.findOne(cacheuser.getId());
        user.setIcon(icon);
        repository.save(user);
        redisService.cacheString(REDIS_USER_KEY + token, user, REDIS_USER_TIME);
    }


    @Override
    public void updateUserPassword(String token, String oldpsd, String newpsd) {
        SysRegistUser cacheuser = redisService.getString(REDIS_USER_KEY + token);
        if (cacheuser == null)
            throw new ServiceProcessException("用户Session过期，请重新登录");
        SysRegistUser user = repository.findOne(cacheuser.getId());
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldpsd.getBytes())))
            throw new ServiceProcessException("原始密码错误,请重新输入");
        user.setPassword(DigestUtils.md5DigestAsHex(newpsd.getBytes()));
        repository.save(user);
        redisService.deleteString(REDIS_USER_KEY+token);
    }
*/

}
