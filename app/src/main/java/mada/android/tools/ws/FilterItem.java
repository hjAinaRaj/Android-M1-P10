package mada.android.tools.ws;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterItem implements Serializable {
    private String column;
    private String comparator;
    private Object value;
    private String type;

    public FilterItem(String column, String comparator, Object value, String type) {
        this.column = column;
        this.comparator = comparator;
        this.value = value;
        this.type = type;
    }
    public static Map<String, String> generateMap(List<FilterItem> items){
        Map<String, String> filtersMap = new HashMap<>();
        int i = 0;
        for(FilterItem item : items){
            if(item.getValue() == null) continue;
            filtersMap.put("filter["+i+"][column]", item.getColumn());
            filtersMap.put("filter["+i+"][value]", item.getValue().toString());
            filtersMap.put("filter["+i+"][comparator]",  item.getComparator());
            filtersMap.put("filter["+i+"][type]", item.getType());
            i++;
        }

        return filtersMap;
    }
    @Override
    public String toString() {
        return RetrofitClientInstance.listObjectConverter.toString(this);
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
