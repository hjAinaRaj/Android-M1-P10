package mada.android.models.quiz;

import java.util.List;

public class QuizQuestion {
    private String question;
    private List<String> options;
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
