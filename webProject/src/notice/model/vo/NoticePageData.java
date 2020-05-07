package notice.model.vo;

import java.util.ArrayList;

public class NoticePageData {
	private ArrayList<notice> list;
	private String pageNavi;
	
	
	
	
	public NoticePageData(ArrayList<notice> list, String pageNavi) {
		super();
		this.list = list;
		this.pageNavi = pageNavi;
	}
	
	
	public NoticePageData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ArrayList<notice> getList() {
		return list;
	}
	public void setList(ArrayList<notice> list) {
		this.list = list;
	}
	public String getPageNavi() {
		return pageNavi;
	}
	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}
	
	
	
}
