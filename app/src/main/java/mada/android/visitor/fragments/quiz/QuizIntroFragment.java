package mada.android.visitor.fragments.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.models.quiz.QuizQuestion;
import mada.android.models.quiz.QuizViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizIntroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizIntroFragment extends Fragment {
    private QuizViewModel viewModel;
    private YouTubePlayerView youtubePlayerView;
    private TextView titleTxtView;
    private TextView descriptionTxtView;


    public QuizIntroFragment() {
        // Required empty public constructor
    }


    public static QuizIntroFragment newInstance( ) {
        QuizIntroFragment fragment = new QuizIntroFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_quiz_intro,container, false);


        this.youtubePlayerView = v.findViewById(R.id.quizIntroVideoView);
        this.titleTxtView = v.findViewById(R.id.quizIntroTitleView);
        this.descriptionTxtView = v.findViewById(R.id.quizIntroDescriptionTxtView);


        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(viewModel.getCurrentQuiz().getVideo(), 0);
            }
        });
        titleTxtView.setText(viewModel.getCurrentQuiz().getTitle());
        descriptionTxtView.setText(viewModel.getCurrentQuiz().getDescription());

        return v;
    }
}