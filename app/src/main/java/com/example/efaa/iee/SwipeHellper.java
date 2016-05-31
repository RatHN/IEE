package com.example.efaa.iee;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;


public class SwipeHellper extends ItemTouchHelper.Callback {

    private RecyclerView mRecyclerView;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        return 0;
        mRecyclerView = recyclerView;
        if (viewHolder instanceof ClaseRecyclerAdaptador.ClaseViewHolder) {
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(0, swipeFlags);
        } else
            return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false; // ---------------------------
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int i = viewHolder.getAdapterPosition();
        /*((ClaseRecyclerAdaptador.ClaseViewHolder)viewHolder).onClick();
        ((ClaseRecyclerAdaptador) mRecyclerView.getAdapter()).LISTA.remove(i);
        mRecyclerView.getAdapter().notifyItemRemoved(i);*/
        ClaseRecyclerAdaptador.ClaseViewHolder viewHolder1 = (ClaseRecyclerAdaptador.ClaseViewHolder) viewHolder;
        View swipableView = viewHolder1.getSwipableView();
        Context context = mRecyclerView.getContext();

        viewHolder1.remover(context, swipableView, i);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        super.onSelectedChanged(viewHolder, actionState);
        if (viewHolder != null) {
            getDefaultUIUtil().onSelected(((ClaseRecyclerAdaptador.ClaseViewHolder) viewHolder).getSwipableView());
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        super.clearView(recyclerView, viewHolder);
        getDefaultUIUtil().clearView(((ClaseRecyclerAdaptador.ClaseViewHolder) viewHolder).getSwipableView());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        getDefaultUIUtil().onDraw(c, recyclerView, ((ClaseRecyclerAdaptador.ClaseViewHolder) viewHolder)
                .getSwipableView(), dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        getDefaultUIUtil().onDrawOver(c, recyclerView, ((ClaseRecyclerAdaptador.ClaseViewHolder) viewHolder)
                .getSwipableView(), dX, dY, actionState, isCurrentlyActive);
    }
}
