package com.ys.mgr.web;


import com.ys.mgr.form.PutSysResourceForm;
import com.ys.mgr.form.request.SysRoleForm;
import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysRole;
import com.ys.mgr.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by fsj on 2017/5/19.
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取所有角色
     * @return 所有角色列表
     * @throws Exception
     */
    @GetMapping
    public MyResponseForm<PageData<SysRole>> list(SysRoleForm sysRoleForm) throws Exception {
        return new MyResponseForm<>(sysRoleService.selectPageData(sysRoleForm)).returnSuccess();
    }

    /**
     * 新增角色
     * @return 新增的角色
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public MyResponseForm<SysRole> add(SysRole sysRole) throws Exception {
        sysRoleService.insert(sysRole);
        return new MyResponseForm<>(sysRole).returnSuccess("新增角色成功");
    }

    /**
     * 获取角色详细信息
     * @param id
     * @return 角色详细信息
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MyResponseForm<SysRole> detail(@PathVariable(name = "id") Integer id) throws Exception {
        return new MyResponseForm<>(sysRoleService.selectById(id));
    }

    /**
     * 删除角色
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public MyResponseForm<Integer> del(@PathVariable(name = "id") Integer id) throws Exception {
        return new MyResponseForm<>(sysRoleService.deleteById(id));
    }

    /**
     * 更新角色
     * @return 更新后的角色
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MyResponseForm<SysRole> update(@PathVariable(name = "id") Integer id,
                                        @ModelAttribute("sysRoleModel") SysRole sysRole) throws Exception {
        if (!id.equals(sysRole.getId())) {
            return new MyResponseForm<SysRole>().returnError("参数异常");
        }
        sysRoleService.update(sysRole);
        return new MyResponseForm<>(sysRole).returnSuccess("修改角色成功");
    }

    /**
     * 获取角色拥有的权限
     * @return 角色拥有的权限ID集合
     * @throws Exception
     */
    @RequestMapping(value = "/resourcesIds/{id}", method = RequestMethod.GET)
    public MyResponseForm<List<Integer>> selectedResourcesIds(@PathVariable(name = "id") Integer id) throws Exception {
        return new MyResponseForm<>(sysRoleService.selectedResourcesIds(id)).returnSuccess();
    }

    /**
     * 获取角色拥有的权限
     * @return 角色拥有的权限ID集合
     * @throws Exception
     */
    @RequestMapping(value = "/resourcesIds/{id}", method = RequestMethod.POST)
    public MyResponseForm<String> setResourcesIds(@PathVariable(name = "id") Integer id, PutSysResourceForm putSysResourceForm) throws Exception {
        sysRoleService.insertSysRoleResource(id, putSysResourceForm.getSelectedResourceIds());
        return new MyResponseForm<>("").returnSuccess("设置权限成功");
    }

    /**
     * 当前controller的每次请求之前都会执行@ModelAttribute注解的方法；
     * 如果是更新操作(PUT)，就先将原来的数据从数据库里面查出来，spring会放到指定名称的model中；
     * 再把提交的内容绑定到该model中，实现只更新修改的内容；
     * PUT的对象要用@ModelAttribute注解，并且名称要一致；
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @ModelAttribute("sysRoleModel")
    public SysRole initModel(HttpServletRequest request,
                            @PathVariable(name = "id", required = false) Integer id) throws Exception {
        if (id != null && RequestMethod.PUT.name().equalsIgnoreCase(request.getMethod())) {
            // 只更新的时候需要用到
            return sysRoleService.selectById(id);
        }
        return null;
    }

}
