package com.example.scribesupport.scribe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.example.scribesupport.R;

import java.util.List;


public class personAdapter extends FirebaseRecyclerAdapter<person, personAdapter.personsViewholder> {


    private List<person> dataList;

    private Context context;

    public personAdapter(@NonNull FirebaseRecyclerOptions<person> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void
    onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull person model) {

        holder.username.setText(model.getUserName());
        holder.address.setText(model.getAddress());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VolunteerProfileViewActivity.class);
                intent.putExtra("name", model.getUserName());
                intent.putExtra("pin", model.getPin());
                intent.putExtra("gender", model.getGender());
                intent.putExtra("phone number", model.getPhone());
                intent.putExtra("email", model.getEmail());
                intent.putExtra("address", model.getAddress());
                intent.putExtra("dob", model.getDob());
               // intent.putExtra("langs", model.getSelectedLanguages().toArray().toString());
                context.startActivity(intent);
            }
        });

    }


    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);
        return new personAdapter.personsViewholder(view);
    }


    class personsViewholder extends RecyclerView.ViewHolder {
        TextView username, address;
        CardView recCard;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);

            username = itemView.findViewById(R.id.username1);
            address = itemView.findViewById(R.id.address1);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }
}
