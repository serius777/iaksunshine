package com.hudapc.iaksunshine.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hudapc.iaksunshine.R;
import com.hudapc.iaksunshine.databinding.RowForecastBinding;
import com.hudapc.iaksunshine.model.DummyForeCast;
import com.hudapc.iaksunshine.model.ForeCast;
import com.hudapc.iaksunshine.model.Temperature;
import com.hudapc.iaksunshine.model.Weather;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lilmechine on 11/12/17.
 */

public class RVForeCast extends RecyclerView.Adapter<RVForeCast.VHForeCast>
{
    //ArrayList<DummyForeCast> mData;
    List<ForeCast> mDataForeCast;
    RVForeCast.OnClickForeCast mListener;

    public RVForeCast(ArrayList<ForeCast> list)
    {
        mDataForeCast = list;
    }

    public void setListener(OnClickForeCast listener)
    {
        mListener = listener;
    }

    @Override
    public VHForeCast onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tmpView = inflater.inflate(R.layout.row_forecast, parent, false);
        VHForeCast holder = new VHForeCast(tmpView);
        return holder;
    }

    @Override
    public void onBindViewHolder(VHForeCast holder, int position)
    {
        ForeCast tmp = mDataForeCast.get(position);
        holder.bind(tmp);
    }

    @Override
    public int getItemCount()
    {
        return mDataForeCast.size();
    }

    public class VHForeCast extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        //row_forecast.xml
        //RowForcastBinding
        RowForecastBinding binding;

        public VHForeCast(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            binding = RowForecastBinding.bind(itemView);
        }

        @Override
        public void onClick(View view)
        {
            ForeCast tmp = mDataForeCast.get(getAdapterPosition());
            mListener.onClick(tmp);
        }

        public void bind(ForeCast item)
        {
            //Nama Hari dd/mm/yyy
            Date date = new Date();
            date.setTime(item.getDt());
            Format format = new SimpleDateFormat("E");
            Weather itemWeather = item.getWeather().get(0);
            Temperature itemTemp = item.getTemp();
            //binding.ivIcon.setImageBitmap();
            binding.tvDay.setText(format.format(date));
            binding.tvWeather.setText(itemWeather.getDescription());
            binding.tvMax.setText("" + itemTemp.getMax());
            binding.tvMin.setText("" + itemTemp.getMin());
        }
    }

    public interface OnClickForeCast
    {
        public void onClick(ForeCast item);
    }
}
