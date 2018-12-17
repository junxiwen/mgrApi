package com.ys.mgr.web;


import com.ys.mgr.form.PutSysResourceForm;
import com.ys.mgr.form.request.SysResourceForm;
import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysResource;
import com.ys.mgr.po.TreeNode;
import com.ys.mgr.service.SysResourceService;
import com.ys.mgr.service.SysRoleService;
import com.ys.mgr.util.mapper.MyJsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by fsj on 2017/5/19.
 */
@Slf4j
@RestController
@RequestMapping("/sysResource")
public class SysResourceController {
    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取所有权限资源
     *
     * @return 所有权限资源列表
     * @throws Exception
     */
    @GetMapping
    public MyResponseForm<PageData<SysResource>> list(SysResourceForm sysResourceForm) throws Exception {
        return new MyResponseForm<>(sysResourceService.selectPageData(sysResourceForm));
    }

    /**
     * 新增权限资源
     *
     * @return 新增的权限资源
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public MyResponseForm<SysResource> add(SysResource sysResource) throws Exception {
        sysResourceService.insert(sysResource);
        return new MyResponseForm<>(sysResource).returnSuccess("新增成功");
    }

    /**
     * 获取权限资源详细信息
     *
     * @param id
     * @return 权限资源详细信息
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MyResponseForm<SysResource> detail(@PathVariable(name = "id") Integer id) throws Exception {
        return new MyResponseForm<>(sysResourceService.selectById(id));
    }


    /**
     * 删除权限资源
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public MyResponseForm<Integer> del(@PathVariable(name = "id") Integer id) throws Exception {
        return new MyResponseForm<>(sysResourceService.deleteById(id)).returnSuccess("删除成功");
    }

    /**
     * 更新权限资源
     *
     * @return 更新后的权限资源
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MyResponseForm<SysResource> update(@PathVariable(name = "id") Integer id,
                                              @ModelAttribute("sysResourceModel") SysResource sysResource) throws Exception {
        if (!id.equals(sysResource.getId())) {
            return new MyResponseForm<SysResource>().returnError("参数异常");
        }
        sysResourceService.update(sysResource);
        return new MyResponseForm<>(sysResource).returnSuccess("更新成功");
    }

    /**
     * 获取角色的设置权限列表
     *
     * @param roleId
     * @return 权限列表
     * @throws Exception
     */
    @RequestMapping(value = "/sysResourceList/{roleId}", method = RequestMethod.GET)
    public String sysResourceList(@PathVariable(name = "roleId") Integer roleId) throws Exception {
        List<TreeNode> allTreeNodes = sysResourceService.selectListByRoleId(roleId);
        List<Integer> arrayId = new ArrayList<>();
        getUserTreeNodes(allTreeNodes);
        if (allTreeNodes.size() > 0) {
            //清除掉内容管理和系统设置里没有子菜单的多余项
            for (TreeNode treeNode : allTreeNodes) {
                arrayId.add(treeNode.getId());
                List<TreeNode> children = treeNode.getChildren();
                if (children != null) {
                    for (Iterator iterator = children.iterator(); iterator.hasNext(); ) {
                        TreeNode childrenTreeNode = (TreeNode) iterator.next();
                        arrayId.add(childrenTreeNode.getId());
                    }
                }
            }
        }
        return MyJsonMapper.NON_EMPTY_MAPPER.toJson(new MyResponseForm<>(arrayId));
    }


    /**
     * 根据角色id获取权限列表（只有权限列表）
     *
     * @param roleId
     * @return 权限列表
     * @throws Exception
     */
    @RequestMapping(value = "/sysResource/{roleId}", method = RequestMethod.GET)
    public String sysResource(@PathVariable(name = "roleId") Integer roleId) throws Exception {
        List<TreeNode> allTreeNodes = sysResourceService.selectListByRoleId(roleId);
        //userId为1代表超级管理员 拥有所有权限
        if (roleId != null && roleId != 1) {
            //获取用户拥有的权限菜单
            getUserTreeNodes(allTreeNodes);
            if (allTreeNodes.size() > 0) {
                //清除掉内容管理和系统设置里没有子菜单的多余项
                TreeNode treeNode = allTreeNodes.get(0);
                List<TreeNode> children = treeNode.getChildren();
                if (children != null) {
                    for (Iterator iterator = children.iterator(); iterator.hasNext(); ) {
                        TreeNode childrenTreeNode = (TreeNode) iterator.next();
                    }
                }
            }
        }
        return MyJsonMapper.NON_EMPTY_MAPPER.toJson(new MyResponseForm<>(allTreeNodes));
    }

    private void getUserTreeNodes(List<TreeNode> allTreeNodes) {
        for (Iterator iterator = allTreeNodes.iterator(); iterator.hasNext(); ) {
            TreeNode treeNode = (TreeNode) iterator.next();
            if (!treeNode.getSelected()) {
                if (treeNode.getChecked() == null || !treeNode.getChecked()) {
                    iterator.remove();
                }
            } else {
                if (treeNode.getChildren() != null && treeNode.getChildren().size() > 0) {
                    List<TreeNode> secondTreeNodeList = treeNode.getChildren();
                    getUserTreeNodes(secondTreeNodeList);
                }
            }
        }

    }

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
    @ModelAttribute("sysResourceModel")
    public SysResource initModel(HttpServletRequest request,
                                 @PathVariable(name = "id", required = false) Integer id) throws Exception {
        if (id != null && RequestMethod.PUT.name().equalsIgnoreCase(request.getMethod())) {
            // 只更新的时候需要用到
            return sysResourceService.selectById(id);
        }
        return null;
    }

    @RequestMapping(value = "/getRoleIdByUserId", method = RequestMethod.GET)
    public MyResponseForm<Map<String, Object>> getRoleIdByUserId(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        Integer roleId = null;
        try {
            map = sysResourceService.selectRoleIdByuserId(userId);
            Object obj = map.get("roleId");
            roleId = Integer.parseInt(obj.toString());
        } catch (Exception e) {
            log.error("获取用户角色ID抛出异常，异常信息为：{}", e.getMessage());
            return new MyResponseForm<>(map).returnError("根据用户ID查询用户角色失败");
        }
        if (roleId == null) {
            return new MyResponseForm<>(map).returnError("获取失败");
        }
        return new MyResponseForm<>(map);
    }

    /**
     * 设置角色的权限列表
     *
     * @param roleId
     * @return 权限列表
     * @throws Exception
     */
    @RequestMapping(value = "/sysResource/{roleId}", method = RequestMethod.PUT)
    public MyResponseForm<String> putSysResource(@PathVariable(name = "roleId") Integer roleId, PutSysResourceForm putSysResourceForm, Integer mgrUserId) throws Exception {
        if (mgrUserId == null) {
            return new MyResponseForm<>("").returnError("参数异常，获取不到操作用户ID");
        }
        Integer role = sysRoleService.getRoleIdByUserId(mgrUserId);
        if (role == null || role != 1) {
            return new MyResponseForm<>("").returnFail("只有管理员才能设置他人权限");
        }
        if (log.isDebugEnabled()) {
            log.debug("roleId: {} selectedResourceIds: {}", roleId, putSysResourceForm);
        }
        sysResourceService.insertSysRoleResource(roleId, putSysResourceForm.getSelectedResourceIds());
        return new MyResponseForm<>("").returnSuccess("设置权限成功");
    }
}
