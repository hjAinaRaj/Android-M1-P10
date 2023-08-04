package mada.android.datainterface;

import mada.android.models.quiz.QuizList;
import mada.android.models.quiz.Quiz;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuizInterface {
    @GET("/quiz")
    Call<QuizList> get();


    @GET("quiz/{id}")
    Call<Quiz> getItemById(@Path("id") String itemId);

}
