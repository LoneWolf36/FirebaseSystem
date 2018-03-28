package sih.firebasesendnotif;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import sih.firebasesendnotif.Classes.QueryData;

import static android.content.Context.MODE_PRIVATE;

public class MyQueryDataRecyclerViewAdapter extends RecyclerView.Adapter<MyQueryDataRecyclerViewAdapter.ViewHolder> {

    private final List<QueryData> mValues;
    private Context context;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");
    SharedPreferences prefs;
    String city_name;
    private FirebaseAuth mAuth;
    EditText txtDate, txtTime,txtDuration;

    public MyQueryDataRecyclerViewAdapter(List<QueryData> items, Context context) {
        mValues = items;
        this.context=context;
        prefs =context.getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        //getContext().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        city_name = prefs.getString("city_name", "");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_querydata_list, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        mAuth = FirebaseAuth.getInstance();
        //prefs= PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        prefs = parent.getContext().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        city_name = prefs.getString("city_name", "");
        Log.d("city name",city_name);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final QueryData pick_q=mValues.get(position);
        holder.question.setText(pick_q.getQuestion());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;

        public ViewHolder(View view) {
            super(view);
            question=(TextView)view.findViewById(R.id.question);
                    }
    }
}
