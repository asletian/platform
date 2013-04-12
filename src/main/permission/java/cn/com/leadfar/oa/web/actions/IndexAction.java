package cn.com.leadfar.oa.web.actions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.model.Menu;
import cn.com.leadfar.oa.service.AclService;
import cn.com.leadfar.oa.vo.AuthMenuTreeVO;
import cn.com.leadfar.oa.vo.LoginInfoVO;
import cn.com.leadfar.oa.web.JSONUtils;

/**
 * 用来处理登录之后，主界面上的各种请求
 * @author Lee
 *
 */
@Controller("indexAction")
@Scope("prototype")
public class IndexAction extends BaseAction{
	
	@Resource
	private AclService aclService;
	
	//根据当前登录用户，查询其拥有访问权限的菜单
	public void menu(){
		LoginInfoVO user = currentUser();
		List<Menu> authMenus = aclService.findPermitMenus(user.getId());
		List<AuthMenuTreeVO> vos = new ArrayList<AuthMenuTreeVO>();
		for(Menu m:authMenus){
			AuthMenuTreeVO vo = new AuthMenuTreeVO(m);
			vos.add(vo);
		}
		JSONUtils.toJSON(vos);
	}
	
}
