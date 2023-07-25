package mada.android.commons.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class cairoBoldTextView extends AppCompatTextView
{

    public cairoBoldTextView(Context context)
    {
        super(context);
        init();
    }

    public cairoBoldTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public cairoBoldTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setLineSpacing(0,0.6f);

        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cairo-Bold.ttf");
            setTypeface(tf);
        }
    }

}
