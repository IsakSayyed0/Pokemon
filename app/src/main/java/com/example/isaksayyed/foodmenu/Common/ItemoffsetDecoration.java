package com.example.isaksayyed.foodmenu.Common;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.isaksayyed.foodmenu.R;

import io.reactivex.annotations.NonNull;

public class ItemoffsetDecoration extends RecyclerView.ItemDecoration {

    private  int offset;

    public ItemoffsetDecoration(int offset) {
        this.offset = offset;
    }

    public ItemoffsetDecoration(@NonNull Context context, @DimenRes int dimens){

        this(context.getResources().getDimensionPixelSize(dimens));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
           outRect.set(offset,offset,offset,offset);
    }
}
