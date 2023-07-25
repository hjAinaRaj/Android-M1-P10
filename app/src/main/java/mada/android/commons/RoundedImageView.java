package mada.android.commons;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import mada.android.R;

public class RoundedImageView extends AppCompatImageView {

    private Path clipPath = new Path();
    private float cornerRadius;

    public RoundedImageView(Context context) {
        super(context);
        init(null);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundedImageView);
            cornerRadius = a.getDimension(R.styleable.RoundedImageView_cornerRadius, 0f);
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        clipPath.reset();
        float[] radii = new float[]{
                cornerRadius, cornerRadius,     // Top left corner
                cornerRadius, cornerRadius,     // Top right corner
                0f, 0f,                         // Bottom right corner
                0f, 0f                          // Bottom left corner
        };
        clipPath.addRoundRect(0f, 0f, getWidth(), getHeight(), radii, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
