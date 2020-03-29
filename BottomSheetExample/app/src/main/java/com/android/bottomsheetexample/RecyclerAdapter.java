package com.android.bottomsheetexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private List<String> RecyclerList;

    public RecyclerAdapter(List<String> list) { this.RecyclerList = list; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        int res = R.layout.main_listitem;
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(res, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.TextView.setText(this.RecyclerList.get(position));
    }

    @Override
    public int getItemCount() { return this.RecyclerList.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView TextView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.TextView = itemView.findViewById(R.id.listitem_textview);
        }
    }
}
