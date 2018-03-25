package sih.firebasesendnotif;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sih.firebasesendnotif.Classes.ScheduleData;

/**
 * Created by ali on 20/3/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHoder> {
    List<ScheduleData> list;
    Context context;

    public RecyclerAdapter(List<ScheduleData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        ScheduleData mylist = list.get(position);
        holder.date.setText("Water will be released on " + mylist.getDate());
        //holder.email.setText(mylist.getEmail());
        holder.time.setText("at " +mylist.getTime());
        holder.duration.setText("for a duration of " + mylist.getDuration() + " hours");

    }
    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if(list.size()==0){

                arr = 0;

            }
            else{

                arr=list.size();
            }



        }catch (Exception e){



        }

        return arr;

    }
    class MyHoder extends RecyclerView.ViewHolder{
        TextView date,time,duration;


        public MyHoder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);

            time= (TextView) itemView.findViewById(R.id.time);
            duration= (TextView) itemView.findViewById(R.id.duration);

        }
    }
}
