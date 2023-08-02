package mada.android.models.quiz;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class QuizViewModel extends ViewModel {
    private Quiz currentQuiz;
    private boolean displayAnswers;

    public QuizViewModel(){
        currentQuiz = new Quiz();
        currentQuiz.setVideo("6Xnz2fwf9Yk");
        currentQuiz.setDescription("Madagascar is considered by many as a paradise on Earthssssssssssssssssssssssssssss ssssssssssssssssssssssss ssssssssssssssssssssss ssssssssssssssssssss sssssssssssss sssss");
        currentQuiz.setTitle("An underrated destination");

        currentQuiz.setQuestions(new ArrayList<QuizQuestion>(){{
            add(new QuizQuestion("Which continent does Madagascar belong to?", new ArrayList<String>(){{
                add("Africa"); add("Asia"); add("America");
            }}, 0));
        }});
    }

    public boolean displayAnswers() {
        return displayAnswers;
    }

    public void setDisplayAnswers(boolean displayAnswers) {
        this.displayAnswers = displayAnswers;
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }
}
