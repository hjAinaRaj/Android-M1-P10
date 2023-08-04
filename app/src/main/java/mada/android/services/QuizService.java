package mada.android.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mada.android.datainterface.DestinationInterface;
import mada.android.datainterface.FavoriteDestinationInterface;
import mada.android.datainterface.QuizInterface;
import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.models.destination.FavoriteDestination;
import mada.android.models.destination.QuizList;
import mada.android.models.quiz.Quiz;
import mada.android.tools.ws.FilterItem;
import mada.android.tools.ws.Pagination;
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
