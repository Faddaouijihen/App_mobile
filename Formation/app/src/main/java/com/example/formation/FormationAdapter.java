package com.example.formation;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FormationAdapter extends RecyclerView.Adapter<FormationAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<Formation> formationList;




    public FormationAdapter(Context context, List<Formation> formationList)
    {

        this.formationList = formationList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.formation_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Formation currentFormation = formationList.get(position);
        holder.txt_titre.setText(currentFormation.getTitre());
        holder.txt_date.setText("Date début : " +currentFormation.getDate());
        holder.txt_categorie.setText("Categorie : " +currentFormation.getCategorie());
        holder.txt_nbr_heure.setText("Nbr heures : " +currentFormation.getNbr_heure()+ " heures");
        int resourceImage = context.getResources().getIdentifier(currentFormation.getCategorie().toLowerCase(), "drawable", context.getPackageName());
        holder.item_image.setImageResource(resourceImage);

        Typeface home_txt_face = Typeface.createFromAsset(context.getAssets(), "fonts/dbplus.otf");
        holder.txt_titre.setTypeface(home_txt_face);
        holder.txt_date.setTypeface(home_txt_face);
        holder.txt_categorie.setTypeface(home_txt_face);
        holder.txt_nbr_heure.setTypeface(home_txt_face);
        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CourseDetailsActivity.class);
                i.putExtra("id",currentFormation.getId());
                i.putExtra("titre",currentFormation.getTitre());
                i.putExtra("nbr_heure",currentFormation.getNbr_heure());
                i.putExtra("categorie",currentFormation.getCategorie());
                i.putExtra("description",currentFormation.getDescription());
                i.putExtra("date",currentFormation.getDate());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return formationList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_titre,txt_date,txt_nbr_heure,txt_categorie;
        LinearLayout linearlayout;
        ImageView item_image;


        public MyViewHolder(View itemView) {
            super(itemView);

            txt_titre = itemView.findViewById(R.id.txt_titre);
            txt_date = itemView.findViewById(R.id.txt_item_date);
            txt_nbr_heure = itemView.findViewById(R.id.txt_nbr_heure);
            txt_categorie = itemView.findViewById(R.id.txt_formation);
            item_image = itemView.findViewById(R.id.item_image);
            linearlayout = itemView.findViewById(R.id.linearlayout);


        }
    }
    public void filterlist(List<Formation> newlist)
    {
        formationList = new ArrayList<>();
        formationList.addAll(newlist);
        notifyDataSetChanged();

    }
}