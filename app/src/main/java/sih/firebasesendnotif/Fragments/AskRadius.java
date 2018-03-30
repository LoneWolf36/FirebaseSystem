package sih.firebasesendnotif.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import sih.firebasesendnotif.R;

/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AskRadius.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskRadius#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskRadius extends Fragment {

    EditText radius ;
    Button submitrad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.fragment_ask_radius,container,false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radius = (EditText)getView().findViewById(R.id.radius);
        submitrad = (Button)getView().findViewById(R.id.submit_rad);

        submitrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(),NGONearYou.class);
                //intent.putExtra("radius",radius.getText().toString().trim());
                //startActivity(intent);
            }
        });
    }

}
