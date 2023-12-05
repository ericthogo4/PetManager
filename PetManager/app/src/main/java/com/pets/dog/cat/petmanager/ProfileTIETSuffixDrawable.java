package com.pets.dog.cat.petmanager;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.google.android.material.textfield.TextInputEditText;
import android.util.TypedValue;


public class ProfileTIETSuffixDrawable extends Drawable {
    private static int DEFAULT_COLOR;
    private static final int DEFAULT_TEXT_SIZE = 15;
    private Paint editTextPaint;
    private CharSequence editTextSuffixText;
    private int intrinsicWidth;
    private int intrinsicHeight;
    private TextInputEditText textInputEditText;

    public ProfileTIETSuffixDrawable(Resources resources, CharSequence editTextSuffixText, TextInputEditText textInputEditText) {
        this.editTextSuffixText = editTextSuffixText;
        DEFAULT_COLOR=resources.getColor(R.color.mediumEmphasisTextColor);
        editTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        editTextPaint.setColor(DEFAULT_COLOR);
        editTextPaint.setTextAlign(Paint.Align.CENTER);
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                DEFAULT_TEXT_SIZE, resources.getDisplayMetrics());
        editTextPaint.setTextSize(textSize);
        intrinsicWidth = (int) (editTextPaint.measureText(this.editTextSuffixText, 0, this.editTextSuffixText.length()) + .5);
        intrinsicHeight = editTextPaint.getFontMetricsInt(null);
        this.textInputEditText =textInputEditText;
    }

    @Override
    public void draw(Canvas canvas) {
        int mLineBounds= textInputEditText.getLineBounds(0, null);
        Rect bounds = getBounds();
        canvas.drawText(editTextSuffixText, 0, editTextSuffixText.length(),
                bounds.centerX(), canvas.getClipBounds().top + mLineBounds, editTextPaint);
    }

    @Override
    public int getOpacity() {
        return editTextPaint.getAlpha();
    }

    @Override
    public int getIntrinsicWidth() {
        return intrinsicWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return intrinsicHeight;
    }

    @Override
    public void setAlpha(int alpha) {
        editTextPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter filter) {
        editTextPaint.setColorFilter(filter);
    }

}
