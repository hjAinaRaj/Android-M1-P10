package mada.android.tools.ws;

import java.util.HashMap;
import java.util.Map;

public class Pagination {
    private int page = 1;
    private int pageElmtCount = 5;
    private String orderColumnName = "_id";
    private String orderMode = "asc";

    public boolean hasNext(int totalItemCount){
        return (page * pageElmtCount ) < totalItemCount;
    }

    public boolean hasPrev(int totalItemCount){
        return page > 1;
    }
    public Map<String, Object> generateQueryParams (){
        HashMap<String, Object> params = new HashMap<String, Object>(){{
            put("pagination[page]", page);
            put("pagination[pageElmtCount]", pageElmtCount);
            put("pagination[orderBy][0][column]", orderColumnName);
            put("pagination[orderBy][0][order]", orderMode);
        }};
        return params;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageElmtCount() {
        return pageElmtCount;
    }

    public void setPageElmtCount(int pageElmtCount) {
        this.pageElmtCount = pageElmtCount;
    }

    public String getOrderColumnName() {
        return orderColumnName;
    }

    public void setOrderColumnName(String orderColumnName) {
        this.orderColumnName = orderColumnName;
    }

    public String getOrderMode() {
        return orderMode;
    }

    public void setOrderMode(String orderMode) {
        this.orderMode = orderMode;
    }
}
