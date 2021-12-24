package com.jhoander.concretesolutionsgithubrepo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhoander.concretesolutionsgithubrepo.PullsActivity;
import com.jhoander.concretesolutionsgithubrepo.R;
import com.jhoander.concretesolutionsgithubrepo.domain.models.JsonDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JsonDataAdapter extends ArrayAdapter<JsonDataModel>{

    private final ArrayList<JsonDataModel> popular;
    private final Context context;
    public static int pos;

    public JsonDataAdapter(Context c, ArrayList<JsonDataModel> objects) {
        super(c, 0, objects);
        this.popular = objects;
        this.context = c;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        pos = position;

        View view = null;

        if( popular!=null ){

       
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

         
            view = inflater.inflate(R.layout.custom_listview, parent, false);

          
            TextView nameRepo = view.findViewById(R.id.nameRepo);
            TextView descriptionRepo = view.findViewById(R.id.descriptionRepo);
            TextView forks = view.findViewById(R.id.forksId);
            TextView stars = view.findViewById(R.id.starsId);
            TextView nameUser = view.findViewById(R.id.nameUser);
            ImageView photoUser = view.findViewById(R.id.photoUser);

        
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = (metrics.widthPixels)/5;
            int height = width;
            photoUser.getLayoutParams().width = width;
            photoUser.getLayoutParams().height = height;

            final JsonDataModel dados = popular.get( position );

           
            String fotoUsuarioURL = dados.getImageUser();
            Drawable drPoster = context.getResources().getDrawable(R.drawable.poster);
            Bitmap bitmap = ((BitmapDrawable) drPoster).getBitmap();
            Drawable redimPoster = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));


            nameRepo.setText( dados.getNameRepo() );
            nameUser.setText( dados.getNameRepo() );
            descriptionRepo.setText( dados.getDescriptionRepo() );
            forks.setText( "" + dados.getForksRepo() );
            stars.setText( "" + dados.getStarsRepo() );
           Picasso.get()
                    .load(fotoUsuarioURL)
                    .placeholder( redimPoster )
                    .error( redimPoster )
                    .resize(width, 0)
                    .into(photoUser);

            view.setOnClickListener(arg0 -> {

                Intent intent = new Intent(context, PullsActivity.class);
                String nomeRepo1 = dados.getNameRepo();
                String nomeUsuario1 = dados.getNameUser();
                String nomePulls = nomeUsuario1 + "/" + nomeRepo1 + "/";
                intent.putExtra("pulls", nomePulls);
                intent.putExtra("repo", nomeRepo1);
                context.startActivity(intent);

            });

        }

        return view;

    }
}
