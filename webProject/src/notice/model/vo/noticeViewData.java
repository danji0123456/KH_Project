package notice.model.vo;

import java.util.ArrayList;

public class noticeViewData {
	private notice n;
	private ArrayList<noticeComment> list;
	
	
	
	public noticeViewData(notice n, ArrayList<noticeComment> list) {
		super();
		this.n = n;
		this.list = list;
	}
	
	
	public noticeViewData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public notice getN() {
		return n;
	}
	public void setN(notice n) {
		this.n = n;
	}
	public ArrayList<noticeComment> getList() {
		return list;
	}
	public void setList(ArrayList<noticeComment> list) {
		this.list = list;
	}
	
	
}
