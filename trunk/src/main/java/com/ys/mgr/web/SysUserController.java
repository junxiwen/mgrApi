package com.ys.mgr.web;


import com.ys.mgr.config.MyConst;
import com.ys.mgr.form.request.SysUserForm;
import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysResource;
import com.ys.mgr.po.SysUser;
import com.ys.mgr.service.SysResourceService;
import com.ys.mgr.service.SysRoleService;
import com.ys.mgr.service.SysUserService;
import com.ys.mgr.util.MyCollectionUtils;
import com.ys.mgr.util.MyStringUtils;
import com.ys.mgr.util.crypto.MyHashUtils;
import com.ys.mgr.util.http.MyHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * Created by fsj on 2017/5/19.
 */
@Slf4j
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取所有用户
     *
     * @return 所有用户列表
     * @throws Exception
     */
    @GetMapping
    public MyResponseForm<PageData<SysUser>> list(SysUserForm sysUserForm) throws Exception {
        return new MyResponseForm<>(sysUserService.selectPageData(sysUserForm)).returnSuccess();
    }

    /**
     * 新增用户
     *
     * @return 新增的用户
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public MyResponseForm<SysUser> add(SysUser sysUser) throws Exception {
        // 随机一个初始密码
        String initpwd = MyStringUtils.getRandomChar(8);
        String salt = MyStringUtils.getRandomChar(6);
        sysUser.setSalt(salt);
        sysUser.setPassword(MyHashUtils.md5(MyHashUtils.sha256(initpwd), salt, 1));
        sysUserService.insert(sysUser);
        return new MyResponseForm<>(sysUser).returnSuccess("新增用户成功，初始密码是：[ " + initpwd + " ]请登陆后自行修改!");
    }

    /**
     * 获取用户详细信息
     *
     * @param id
     * @return 用户详细信息
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MyResponseForm<SysUser> detail(@PathVariable(name = "id") Integer id) throws Exception {
        SysUser sysUser = sysUserService.selectById(id);
        return new MyResponseForm<>(sysUser);
    }

    /**
     * 更新用户
     *
     * @return 更新后的用户
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MyResponseForm<SysUser> update(@PathVariable(name = "id") Integer id,
                                          SysUserForm sysUserForm) throws Exception {
        SysUser sysUser = sysUserService.selectById(id);
        if(sysUser == null || id != sysUserForm.getId()){
            return new MyResponseForm<SysUser>().returnError("参数异常");
        }
        sysUser = getSysUserFromSysUserForm(sysUser,sysUserForm);
        sysUserService.updateSysUser(sysUser);
        return new MyResponseForm<>(sysUser).returnSuccess("修改用户成功");
    }

    private SysUser getSysUserFromSysUserForm(SysUser sysUser,SysUserForm sysUserForm){
        if(MyStringUtils.isNoneEmpty(sysUserForm.getRealname())){
            sysUser.setRealname(sysUserForm.getRealname());
        }
        if(MyStringUtils.isNotBlank(sysUserForm.getUserName())){
            sysUser.setUserName(sysUserForm.getUserName());
        }
        if(sysUserForm.getStatus() != null ){
            sysUser.setStatus(sysUserForm.getStatus());
        }
        if(MyStringUtils.isNotBlank(sysUserForm.getMobilephone())){
            sysUser.setMobilephone(sysUserForm.getMobilephone());
        }
        if(MyStringUtils.isNotBlank(sysUserForm.getDescr())){
            sysUser.setDescr(sysUserForm.getDescr());
        }
        if(MyCollectionUtils.isNotEmpty(sysUserForm.getRoleIds())){
            sysUser.setRoleIds(sysUserForm.getRoleIds());
        }
        return sysUser;
    }

    /**
     * 重置用户密码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resetPwd/{id}", method = RequestMethod.POST)
    public MyResponseForm<SysUser> retUserPassword(@PathVariable(name = "id") Integer id) throws Exception {
        // 随机一个初始密码
        String initpwd = MyStringUtils.getRandomChar(8);
        String salt = MyStringUtils.getRandomChar(6);
        SysUser sysUser = sysUserService.selectById(id);
        sysUser.setSalt(salt);
        sysUser.setPassword(MyHashUtils.md5(MyHashUtils.sha256(initpwd), salt, 1));
        sysUserService.updatePwd(sysUser);
        return new MyResponseForm<>(sysUser).returnSuccess("密码重置为：[ " + initpwd + " ]请登陆后自行修改!");
    }
    //58CO1Y48
    /**
     * 当前controller的每次请求之前都会执行@ModelAttribute注解的方法；
     * 如果是更新操作(PUT)，就先将原来的数据从数据库里面查出来，spring会放到指定名称的model中；
     * 再把提交的内容绑定到该model中，实现只更新修改的内容；
     * PUT的对象要用@ModelAttribute注解，并且名称要一致；
     *
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @ModelAttribute("sysUserModel")
    public SysUser initModel(HttpServletRequest request,
                             @PathVariable(name = "id", required = false) Integer id) throws Exception {
        if (id != null && RequestMethod.PUT.name().equalsIgnoreCase(request.getMethod())) {
            // 只更新的时候需要用到
            return sysUserService.selectById(id);
        }
        return null;
    }

    /**
     * 登陆
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public MyResponseForm<SysUser> login(HttpSession session, SysUser sysUser) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("login fromip {}", MyHttpUtils.getRealClientIP());
        }
        // TODO 最好加个验证码
        if (sysUser == null || StringUtils.isEmpty(sysUser.getUserName()) || StringUtils.isEmpty(sysUser.getPassword())) {
            return new MyResponseForm<SysUser>().returnFail("请输入用户名密码");
        }

        SysUser loginUser = sysUserService.selectByUserName(sysUser);
        // 客户端sha256之后的密码传过来，服务端可以加个盐再加个md5
        if (loginUser == null || !MyHashUtils.md5(sysUser.getPassword(), loginUser.getSalt(), 1)
                .equalsIgnoreCase(loginUser.getPassword())) {
            return new MyResponseForm<SysUser>().returnFail("用户名或密码错误");
        }

        // 如果需要共享分布式session可用spring-session解决，基于redis
        // 需要引入spring-boot-starter-data-redis和spring-session
        // application.properties中配置redis和spring.session.store-type=redis就行了
        // 还是使用HttpSession的api
        loginUser.setAccessToken(UUID.randomUUID().toString());

        // 登陆之后的user和权限信息放session中
        session.setAttribute(MyConst.SESSION_USER_NAME, loginUser);
        // 查权限信息
        List<SysResource> selectUserResources = sysResourceService.selectUserResources(loginUser.getId());
        if (log.isDebugEnabled()) {
            log.debug("selectUserResources: {}", selectUserResources);
        }
        session.setAttribute(MyConst.SESSION_RESOURCES_NAME, selectUserResources);
        session.setMaxInactiveInterval(20 * 60);// 过期时间，单位： 秒

        return new MyResponseForm<>(loginUser);
    }

    /**
     * 注销
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public MyResponseForm<String> logout(HttpSession session) throws Exception {
        session.removeAttribute(MyConst.SESSION_USER_NAME);
        session.removeAttribute(MyConst.SESSION_RESOURCES_NAME);
        return new MyResponseForm<>("已注销").returnSuccess("已注销");
    }

    /**
     * 修改密码
     *
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    public MyResponseForm<SysUser> changePwd(HttpSession session, String password, String newPassword, String rePassword) throws Exception {
        if (StringUtils.isEmpty(password)) {
            return new MyResponseForm<SysUser>().returnFail("请输入原密码");
        }
        if (newPassword == null || rePassword == null || !newPassword.equals(rePassword)) {
            return new MyResponseForm<SysUser>().returnFail("请输入两次一致的新密码");
        }

        SysUser loginUser = (SysUser) session.getAttribute(MyConst.SESSION_USER_NAME);
        if (loginUser == null || !MyHashUtils.md5(password, loginUser.getSalt(), 1).equalsIgnoreCase(loginUser.getPassword())) {
            return new MyResponseForm<SysUser>().returnFail("原密码错误");
        }
        //修改密码后重新定义盐
        String salt = MyStringUtils.getRandomChar(6);
        loginUser.setSalt(salt);
        //在客户端已经进行过256加密，故这地方不需要
        loginUser.setPassword(MyHashUtils.md5(newPassword, salt, 1));
        sysUserService.updatePwd(loginUser);
        return new MyResponseForm<SysUser>().returnSuccess("修改成功");
    }

}
