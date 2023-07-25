package mada.android.commons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class RoundedImageView extends AppCompatImageView {

    private Path clipPath = new Path();
    private float cornerRadius = 16f; // Desired corner radius

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

