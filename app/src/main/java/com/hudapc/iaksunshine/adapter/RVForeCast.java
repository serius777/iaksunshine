package com.hudapc.iaksunshine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudapc.iaksunshine.R;

/**
 * Created by lilmechine on 11/10/17.
 */

public class RVForeCast extends RecyclerView.Adapter<RVForeCast.VHForeCast>
{
    @Override
    public VHForeCast onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tmpView = inflater.inflate(R.layout.row_forecast, parent,false);
        VHForeCast holder = new VHForeCast(tmpView);
        return holder;
    }

    @Override
    public void onBindViewHolder(VHForeCast holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 20;
    }

    public class VHForeCast extends RecyclerView.ViewHolder
    {
        public VHForeCast(View itemView)
        {
            super(itemView);
        }
    }
}
