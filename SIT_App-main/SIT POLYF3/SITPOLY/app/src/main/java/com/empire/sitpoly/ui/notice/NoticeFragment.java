package com.empire.sitpoly.ui.notice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.empire.sitpoly.activities.LoginActivity;
import com.empire.sitpoly.activities.MainActivity;
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
import java.util.zip.Inflater;


public class NoticeFragment extends Fragment {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter noticeAdapter;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

  private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        progressBar = new ProgressBar(getContext());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;

        if (!firebaseUser.isAnonymous()){

            progressBar.setVisibility(View.VISIBLE);

            recyclerView = view.findViewById(R.id.notice_recyclerview);
            progressBar = view.findViewById(R.id.notice_progressbar);


            reference = FirebaseDatabase.getInstance().getReference().child("Notice");
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);

            getNotice();

        }else {
            textView = view.findViewById(R.id.text_middle);
            textView.setText("Permissoin Detained! To view this content, Login as a Student");
        }


        return view;

    }

        private void getNotice() {

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    list = new ArrayList<>();
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        NoticeData data = snapshot1.getValue(NoticeData.class);

                       // if ( firebaseUser.getUid().contains(data.getKey())){
                            list.add(0,data);

                        //}

                    }

                    noticeAdapter = new NoticeAdapter(getContext(),list);
                    noticeAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(noticeAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
