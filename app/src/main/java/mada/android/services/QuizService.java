package mada.android.services;

import mada.android.datainterface.QuizInterface;
import mada.android.models.quiz.QuizList;
import mada.android.models.quiz.Quiz;
import mada.android.tools.ws.RetrofitClientInstance;
import retrofit2.Call;

public class QuizService {
    private QuizInterface quizInterface = null;

    public QuizService() {
        if(quizInterface == null){
            quizInterface = RetrofitClientInstance.getRetrofitInstance()
                    .create(QuizInterface.class);
        }

    }

    public Call<QuizList> get() throws Exception{
        Call<QuizList> call = quizInterface.get();
        return call;
    }


    public Call<Quiz> get(String id) throws Exception{
        Call<Quiz> call = quizInterface.getItemById(id);
        return call;
    }

}
