package mada.android.models.defaultResponses;

import com.google.gson.annotations.SerializedName;

public class MetaResponse {
    @SerializedName("totalElmtCount")
    private Integer totalElmtCount;

    public MetaResponse() {
    }

    public MetaResponse(Integer totalElmtCount) {
        this.totalElmtCount = totalElmtCount;
    }

    public Integer getTotalElmtCount() {
        return totalElmtCount;
    }

    public void setTotalElmtCount(Integer totalElmtCount) {
        this.totalElmtCount = totalElmtCount;
    }
}
