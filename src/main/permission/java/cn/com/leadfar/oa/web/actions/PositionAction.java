package cn.com.leadfar.oa.web.actions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.annotations.Res;
import cn.com.leadfar.oa.model.Position;

@Controller("positionAction")
@Scope("prototype")
@Res(name="¸ÚÎ»²Ù×÷",sn="position",orderNumber=40,parentSn="party")
public class PositionAction extends PartyAction {
	public Object getModel() {
		
		if(model == null){
			model = new Position();
		}
		
		return model;
	}
}
