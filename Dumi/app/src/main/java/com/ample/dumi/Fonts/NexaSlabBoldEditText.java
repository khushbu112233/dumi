package com.ample.dumi.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by admin on 09/21/2017.
 */

public class NexaSlabBoldEditText extends AppCompatEditText
{

    public NexaSlabBoldEditText(Context context) {
        super(context);
        init();
    }

    public NexaSlabBoldEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NexaSlabBoldEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "NexaBold.otf");
        setTypeface(tf);
    }
}
