package mada.android.models.quiz;

import android.content.res.Resources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class QuizViewModel extends ViewModel {
    private Quiz currentQuiz;
    private MutableLiveData<Boolean> displayAnswers = new MutableLiveData<>();

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
    public int submitAnswers(Resources resources) throws Exception {
        setDisplayAnswers(true);
        return currentQuiz.submitResults(resources);
    }
    public LiveData<Boolean> getDisplayAnswersLiveData(){
        return displayAnswers;
    }

    public void setDisplayAnswers(boolean displayAnswers) {
        this.displayAnswers.setValue(displayAnswers);
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }
}
