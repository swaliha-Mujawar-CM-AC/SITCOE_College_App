package com.empire.sitpoly.ui.gallery;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.empire.sitpoly.activities.GalleryActivity;
import com.empire.sitpoly.adapter.GalleryAdapter;
import com.empire.sitpoly.adapter.NoticeAdapter;
import com.empire.sitpoly.data.NoticeData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class PrivateNoticeFragment extends Fragment {


    private RecyclerView convoRecycler, otherRecycler;
    private GalleryAdapter adapter;

    DatabaseReference reference;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_private_notice, container, false);


        convoRecycler = view.findViewById(R.id.convo_recycler);
        otherRecycler = view.findViewById(R.id.others_recycler);

        reference = FirebaseDatabase.getInstance().getReference().child("gallery");


        getConvoImage();

        getOtherImage();

        return view;
    }


    private void getOtherImage() {
        reference.child("Other Events").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String data = (String) snapshot1.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(),imageList);
                otherRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                otherRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getConvoImage() {

        reference.child("Convocation").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String data = (String) snapshot1.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(),imageList);
                convoRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                convoRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}