package com.ample.dumi.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by admin on 09/21/2017.
 */

public class NexaSlabRegularTextView extends AppCompatTextView
{
    public NexaSlabRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public NexaSlabRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NexaSlabRegularTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Fontfabric - NexaRegular.otf");
        setTypeface(tf);
    }

}
