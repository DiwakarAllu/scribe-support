package com.example.scribesupport.scribe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.scribesupport.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.scribesupport.signup.volunteer.HelperClass;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
public class AvailableVolunteersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    personAdapter adapter;
    DatabaseReference mbase;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    List<HelperClass> dataList;
   // personAdapter1 adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_volunteers);

        mbase = FirebaseDatabase.getInstance().getReference().child("Volunteer User Data");
        //databaseReference=FirebaseDatabase.getInstance().getReference().child("Volunteer User Data");
        recyclerView = findViewById(R.id.recycler1);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(AvailableVolunteersActivity.this, 1);
//        recyclerView.setLayoutManager(gridLayoutManager);

//        dataList = new ArrayList<>();
//
//        adapter1 = new personAdapter1(AvailableVolunteersActivity.this, dataList);
//        recyclerView.setAdapter(adapter1);
//
//
//          eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dataList.clear();
//                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
//                    HelperClass dataClass = itemSnapshot.getValue(HelperClass.class);
//
//                    dataClass.setKey(itemSnapshot.getKey());
//
//                    dataList.add(dataClass);
//
//                    // Log the data retrieved from the database
//                    Log.d("DataSnapshot", "Name: " + dataClass.getName() + ", Address: " + dataClass.getAddress());
//                }
//                adapter1.notifyDataSetChanged();
//
//                // Log the size of the dataList after populating it
//                Log.d("DataSnapshot", "DataList size: " + dataList.size());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("DataSnapshot", "Database Error: " + error.getMessage());
//            }
//        });



        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<person> options
                = new FirebaseRecyclerOptions.Builder<person>()
                .setQuery(mbase, person.class)
                .build();

        adapter = new personAdapter(options,this);
        recyclerView.setAdapter(adapter);

    }

    @Override protected void onStart()
    {
        super.onStart();
       adapter.startListening();
    }


    @Override protected void onStop()
    {
        super.onStop();
       adapter.stopListening();
    }
}

*/


//-----------------------

public class AvailableVolunteersActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private personAdapter1 adapter;
    private DatabaseReference mbase;

    List<HashMap<String, Object>> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_volunteers);

        mbase = FirebaseDatabase.getInstance().getReference().child("Volunteer User Data");
        recyclerView = findViewById(R.id.recycler1);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new personAdapter1(this, userList);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);

        retrieveDataFromFirebase();

    }

    private void retrieveDataFromFirebase() {
        mbase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate through each child node in dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> userData = (HashMap<String, Object>) snapshot.getValue();
                    userList.add(userData);
                      }
                // Notify adapter that data set has changed
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error fetching data from Firebase: " + databaseError.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
class personAdapter1 extends RecyclerView.Adapter<personAdapter1.MyViewHolder> {
    private Context context;
    private List<HashMap<String, Object>> userList;


    public personAdapter1(Context context,List<HashMap<String, Object>> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);
        return new personAdapter1.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       // Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        HashMap<String, Object> userData = userList.get(position);
        holder.username.setText((String) userData.get("userName"));
        holder.address.setText((String) userData.get("address"));
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VolunteerProfileViewActivity.class);
                //intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("userData", userData);
                intent.putExtra("name", (String) userData.get("userName"));
                intent.putExtra("pin",  (String) userData.get("pin"));
                intent.putExtra("gender",  (String) userData.get("gender"));
                intent.putExtra("phone number",  (String) userData.get("phone number"));
                intent.putExtra("email", (String) userData.get("email"));
                intent.putExtra("address", (String) userData.get("address"));
                intent.putExtra("dob", (String) userData.get("dob"));
               // intent.putExtra("langs",  userData.get("lang").toArray().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recImage;
        TextView username, address;
        CardView recCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username1);
            address = itemView.findViewById(R.id.address1);
            recCard = itemView.findViewById(R.id.recCard);
            recImage=itemView.findViewById(R.id.recImage);
        }
    }
}
