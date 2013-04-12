package cn.com.leadfar.oa.vo;

import java.util.List;

/**
 * 分页查询时，封装查询的结果
 * @author Lee
 *
 */
public class PagerVO {
	private int total;
	private List datas;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getDatas() {
		return datas;
	}
	public void setDatas(List datas) {
		this.datas = datas;
	}
}
