package com.mhdt;

/**
 * 分页对象
 * @author 懒得出风头
 * Date: 2016/06/30
 * Time： 16:04
 */
public class Page {
	
	/** 页显示条数 */
	private int pageSize;
	
	/** 当前页数 */
	private int currentPage;
	
	/** 最大页数 */
	private int maxPage;
	
	/** 数据总数  */
	private int count;
	
	private int start;
	
	private int end;
	
	public Page() {
		
	}
	
	public Page(Integer currentPage,int pageSize){
		if(currentPage==null)
			this.currentPage = 1;
		
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	
	/**
	 * 设置最大数，同时计算出最大 页数，起始结束下标
	 * @param count
	 */
	public void setCount(Integer count) {
		if(count==null) {
			setCount(0);
			return;
		}
		
		this.count = count;
		if(count<=pageSize) maxPage = 1;
		if(count>pageSize){
			if(count%pageSize==0){
				maxPage = count/pageSize;
			}else{
				maxPage = count/pageSize+1;
			}
		}
		
		caculateIndex();
	}
	
	/**
	 * 计算起始和结束页
	 * 1 2 3 4 5 [6] 7 8 9 10
	 */
	private void caculateIndex() {
		end = currentPage+4;
		start = currentPage-5;
		amend();
		
		int amend = 10-(end-start+1);
		if(amend>0){
			
			if(start==1){
				end+=amend;
			}else if(end==maxPage){
				start-=amend;
			}
			amend();
		}
	}
	
	private void amend(){
		if(end>maxPage)end = maxPage;
		if(start<=0)start =1;
	}

	/**
	 * liimit 起始位置
	 * @return
	 */
	public int getStartIndex() {
		return (currentPage-1)*pageSize;
	}
	
	/** 总数 */
	public int getCount() {
		return count;
	}
	
	/** 一页几条数据  */
	public int getPageSize() {
		return pageSize;
	}
	
	/** 当前页 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	/** 最大页 */
	public int getMaxPage() {
		return maxPage;
	}

	/** 分页起始 */
	public int getStart() {
		return start;
	}

	/** 分页结束  */
	public int getEnd() {
		return end;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}


	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Page [pageSize=" + pageSize + ", currentPage=" + currentPage + ", maxPage=" + maxPage + ", count="
				+ count + ", start=" + start + ", end=" + end + "]";
	}
	
	
}
