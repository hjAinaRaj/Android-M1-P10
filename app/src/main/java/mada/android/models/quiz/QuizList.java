package mada.android.models.quiz;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import mada.android.models.defaultResponses.MetaResponse;
import mada.android.models.quiz.Quiz;

public class QuizList {
    @SerializedName("data")
    private ArrayList<Quiz> data;
    @SerializedName("meta")
    private MetaResponse meta;

    public QuizList() {
    }

    public QuizList(ArrayList<Quiz> data, MetaResponse meta) {
        this.data = data;
        this.meta = meta;
    }

    public ArrayList<Quiz> getData() {
        return data;
    }

    public void setData(ArrayList<Quiz> data) {
        this.data = data;
    }

    public MetaResponse getMeta() {
        return meta;
    }

    public void setMeta(MetaResponse meta) {
        this.meta = meta;
    }
}
