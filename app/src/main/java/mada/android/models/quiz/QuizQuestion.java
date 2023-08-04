package mada.android.models.quiz;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizQuestion {

    @SerializedName("question")
    private String question;

    @SerializedName("options")
    private List<String> options;

    @SerializedName("rightAnswer")
    private int rightAnswer;


    private Integer currentAnswer;

    public QuizQuestion(){

    }
    public QuizQuestion(String question, List<String> options, int rightAnswer) {
        this.question = question;
        this.options = options;
        this.rightAnswer = rightAnswer;
    }

    public Integer getCurrentAnswer() {
        return currentAnswer;
    }

    public void setCurrentAnswer(Integer currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
