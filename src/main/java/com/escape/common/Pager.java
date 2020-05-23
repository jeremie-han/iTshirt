package com.escape.common;


public class Pager
{
	public int		totalItem		= 0;
	public int		curPageNum		= 1;
	public int		pageSize		= 50;
	public int		pageListSize	= 10;
	public int		totalPage		= 0;
	public int		startItem		= 0;
	public int		lastItem		= 0;
	public int		frstPageNum		= 0;
	public int		lastPageNum		= 0;
	public int		prevPageNum		= 0;
	public int		nextPageNum		= 0;
	public int[]	pageNumList		= null;

	
	public Pager(int totalItem, int curPageNum, int pageSize, int pageListSize){
		this.totalItem = totalItem;
		this.curPageNum = curPageNum;
		this.pageSize = pageSize;
		this.pageListSize = pageListSize;
		makePage();
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public int getCurPageNum() {
		return curPageNum;
	}

	private void makePage(){
		totalPage = totalItem / pageSize;
		if( totalItem % pageSize > 0 ) totalPage++;
		if( curPageNum < 1 ) curPageNum = 1;
		if( totalPage < curPageNum ) curPageNum = 1;
		frstPageNum = (((curPageNum - 1) / pageListSize) * pageListSize) + 1;
		lastPageNum = frstPageNum + pageListSize - 1;
		if( lastPageNum > totalPage ) lastPageNum = totalPage;
		prevPageNum = frstPageNum - 1;
		nextPageNum = lastPageNum + 1;
		if( nextPageNum > totalPage ) nextPageNum = 0;
		if( curPageNum == 1 ) {
			startItem = (curPageNum - 1) * pageSize + 1;
			lastItem = startItem + pageSize - 1;
		} else {
			startItem = (curPageNum - 1) * pageSize + 1;
			lastItem = startItem + pageSize - 1;
		}
		if( lastItem > totalItem ) lastItem = totalItem;
		pageNumList = new int[lastPageNum - frstPageNum + 1];
		for( int i = 0; i < pageNumList.length; i++ ) {
			pageNumList[i] = frstPageNum + i;
		}
	}

	
	public String getPagingPrintString(String scriptFunction, String prevImgStr, String nextImgStr){
		if( curPageNum == 0 || pageSize == 0 ) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		totalItem = totalItem < 1 ? 1 : totalItem;
		int tTotalPage = totalItem / pageSize;
		if( totalItem % pageSize != 0 ) tTotalPage++;
		int firstPage = (((curPageNum - 1) / pageListSize) * pageListSize == 0) ? 1 : ((curPageNum - 1) / pageListSize) * pageListSize;
		int endPage = (firstPage == 1) ? pageListSize : firstPage + pageListSize;
		
		if( firstPage == 1 ) {
			buffer.append("<li class=\"pn_arrow pn_prev\"><a href=\"javascript:alert('첫번째 페이지입니다.')\">&lt;&lt;</a></li>"); // firstPage
//			if(1 == curPageNum) {
				buffer.append("<li class=\"pn_arrow pn_prev\"><a href=\"javascript:alert('첫번째 페이지입니다.')\">" + prevImgStr + "</a></li>");	
//			}else {
//				buffer.append("<li class=\"pn_arrow pn_prev\"><a href=\"javascript:" + scriptFunction + "('" + firstPage + "'); \">" + prevImgStr + "</a></li>");				
//			}
			
		} else if( firstPage > 1 ) {
			int prePage = firstPage - pageListSize;
			if( prePage < 1 )
				prePage = 1;
			else
				prePage++;
			
			buffer.append("<li class=\"pn_arrow pn_prev\"><a href=\"javascript:" + scriptFunction + "('1'); \">&lt;&lt;</li></a></li>");
			
			buffer.append("<li class=\"pn_arrow pn_prev\"><a href=\"javascript:" + scriptFunction + "('" + prePage + "'); \">" + prevImgStr + "</a></li>");
		}

		if( firstPage > 1 ) firstPage++;

		for( int i = firstPage; i <= endPage && i <= tTotalPage; i++ ) {
			if( i == curPageNum ) {
				buffer.append("<li class=\"active\"><a href=\"javascript:" + scriptFunction + "('" + (i) + "');\">" + i + "</a></li>");
			} else {
				buffer.append("<li><a href=\"javascript:" + scriptFunction + "('" + (i) + "');\">" + i + "</a></li>");
			}
		}

		if( curPageNum > 0 && endPage >= tTotalPage ) {
//			if(lastPageNum >= (curPageNum+1)) {
//				buffer.append("<li class=\"pn_arrow pn_next\"><a href=\"javascript:" + scriptFunction + "('" + tTotalPage + "');\">" + nextImgStr + "</a></li>");
//			}else {
				buffer.append("<li class=\"pn_arrow pn_next\"><a href=\"javascript:alert('마지막 페이지입니다.')\">" + nextImgStr + "</a></li>");	
//			}
			
			buffer.append("<li class=\"pn_arrow pn_next\"><a href=\"javascript:alert('마지막 페이지입니다.')\">&gt;&gt;</a></li>");
		} else if( curPageNum > 0 ) {
			int nextPage = endPage + 1;
			buffer.append("<li class=\"pn_arrow pn_next\"><a href=\"javascript:" + scriptFunction + "('" + nextPage + "');\">" + nextImgStr + "</a></li>");
			buffer.append("<li class=\"pn_arrow pn_next\"><a href=\"javascript:" + scriptFunction + "('" + tTotalPage + "');\">&gt;&gt;</a></li>");
		}

		return buffer.toString();
	}
	
	public String getPreNextString(String scriptFunction, String prevImgStr, String nextImgStr){
		if( curPageNum == 0 || pageSize == 0 ) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		totalItem = totalItem < 1 ? 1 : totalItem;
		int tTotalPage = totalItem / pageSize;
		if( totalItem % pageSize != 0 ) tTotalPage++;
		int firstPage = (((curPageNum - 1) / pageListSize) * pageListSize == 0) ? 1 : ((curPageNum - 1) / pageListSize) * pageListSize;
		int endPage = (firstPage == 1) ? pageListSize : firstPage + pageListSize;


		if( firstPage == 1 ) {
			buffer.append("<em>" + prevImgStr + "</em>");
		} else if( firstPage > 1 ) {
			int prePage = firstPage - pageListSize;
			if( prePage < 1 )
				prePage = 1;
			else
				prePage++;
			buffer.append("<em><a href=\"javascript:" + scriptFunction + "('" + prePage + "');\">" + prevImgStr + "</a></em>");
		}

		if( firstPage > 1 ) firstPage++;

		for( int i = firstPage; i <= endPage && i <= tTotalPage; i++ ) {
			if( i == curPageNum ) {
				//buffer.append("<em><b>" + i + "</b><em>");
			} else {
				//buffer.append("<em><a href=\"javascript:" + scriptFunction + "('" + (i) + "');\">" + i + "</a><em>");
			}
		}

		if( curPageNum > 0 && endPage >= tTotalPage ) {
			buffer.append("<em>" + nextImgStr + "</em>");
		} else if( curPageNum > 0 ) {
			int nextPage = endPage + 1;
			buffer.append("<em><a href=\"javascript:" + scriptFunction + "('" + nextPage + "');\">" + nextImgStr + "</a></em>");
		}

		return buffer.toString();
	}
	
	public String getPagingString(String scriptFunction){
		if( curPageNum == 0 || pageSize == 0 ) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		totalItem = totalItem < 1 ? 1 : totalItem;
		int tTotalPage = totalItem / pageSize;
		if( totalItem % pageSize != 0 ) tTotalPage++;
		int firstPage = (((curPageNum - 1) / pageListSize) * pageListSize == 0) ? 1 : ((curPageNum - 1) / pageListSize) * pageListSize;
		int endPage = (firstPage == 1) ? pageListSize : firstPage + pageListSize;
		if( firstPage == 1 ) {
			buffer.append("<li class=\"l_arrow\"><a href=\"javascript:" + scriptFunction + "('1'); \"> < </a></li>");
		} else if( firstPage > 1 ) {
			int prePage = firstPage - pageListSize;
			if( prePage < 1 )
				prePage = 1;
			else
				prePage++;
			buffer.append("<li class=\"l_arrow\"><a href=\"javascript:" + scriptFunction + "('" + prePage + "'); \"> < </a></li>");
		}
		if( firstPage > 1 ) firstPage++;
		for( int i = firstPage; i <= endPage && i <= tTotalPage; i++ ) {
			if( i == curPageNum ) {
				buffer.append("<li class=\"active\"><a href=\"javascript:" + scriptFunction + "('" + (i) + "');\">" + i + "</a></li>");
			} else {
				buffer.append("<li><a href=\"javascript:" + scriptFunction + "('" + (i) + "');\">" + i + "</a></li>");
			}
		}
		if( curPageNum > 0 && endPage >= tTotalPage ) {
			buffer.append("<li class=\"r_arrow\"><a href=\"javascript:" + scriptFunction + "('" + tTotalPage + "');\"> > </a></li>");
		} else if( curPageNum > 0 ) {
			int nextPage = endPage + 1;
			buffer.append("<li class=\"r_arrow\"><a href=\"javascript:" + scriptFunction + "('" + nextPage + "');\"> > </a></li>");
		}
		return buffer.toString();
	}
	
	public String getPagingFullString(String scriptFunction){
		int total = (0 == this.totalItem) ? 1 : this.totalItem;
		int totalPage = (int)Math.ceil((double)total / (double)pageSize);
		StringBuffer sb = new StringBuffer();
		sb.append("<li class=\"ll_arrow\"><a href=\"javascript:" + scriptFunction + "('1');\"> << </a></li>");
		sb.append(getPagingString(scriptFunction));
		sb.append("<li class=\"rr_arrow\"><a href=\"javascript:" + scriptFunction + "('"+totalPage+"');\"> >> </a></li>");
		return sb.toString();
	}
}
