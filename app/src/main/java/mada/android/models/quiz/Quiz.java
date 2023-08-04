package mada.android.models.quiz;

import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import mada.android.R;

public class Quiz {
    @SerializedName("_id")
    private String _id;

    @SerializedName("video")
    private String video;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("questions")
    private List<QuizQuestion> questions;


    public int submitResults(Resources resources) throws Exception{
        double questionsCount = questions.size();
        double correctAnswers = 0;
        for(QuizQuestion question: questions){
            if(question.getCurrentAnswer() == null) throw new Exception (resources.getString(R.string.incomplete_answers_msg));
            if(question.getCurrentAnswer().equals(question.getRightAnswer())) correctAnswers++;
        }
        return (int)((correctAnswers/questionsCount)*100.0);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizQuestion> questions) {
        this.questions = questions;
    }
}
