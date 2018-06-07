package com.ample.dumi.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by admin on 09/21/2017.
 */

public class NexaSlabBoldTextView extends AppCompatTextView
{
    public NexaSlabBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public NexaSlabBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NexaSlabBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "NexaBold.otf");
        setTypeface(tf);
    }

}
