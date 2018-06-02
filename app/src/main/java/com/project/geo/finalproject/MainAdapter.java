package com.project.geo.finalproject;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.project.geo.finalproject.Model.PostClass;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    List<PostClass> list;
    private String email, gender, birthdate;
    Boolean Valid = false;
    String[] Target;
    View mview;


    public MainAdapter(List<PostClass> list, String email, String gender, String birthdate) {
        this.list = list;
        this.email = email;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainAdapter.ViewHolder holder, final int position) {

        try {

                String TypeStr = list.get(position).getType();
                if (list.size() != 0) {
                    if (TypeStr.equals("post")) {
                        holder.Price.setVisibility(View.GONE);
                        holder.Type.setVisibility(View.GONE);
                        holder.Content.setText(list.get(position).getContent());
                        holder.Full_name.setText(list.get(position).getFname() + " " + list.get(position).getLname());
                        holder.Date.setText(list.get(position).getDate());
                        holder.username.setText(list.get(position).getUsername());
                        if (list.get(position).getImage_url() != null) {
                            holder.imageView.setVisibility(View.VISIBLE);
                            Picasso.get().load(list.get(position).getImage_url()).into(holder.imageView);
                        }
                        if (list.get(position).getImage_user_url() != null)
                            Picasso.get().load(list.get(position).getImage_user_url()).resize(30, 50).centerCrop().into(holder.imageView2);


                    } else if (TypeStr.equals("adv")) {

                        Target = list.get(position).getTargetPeople().split(" ");

                        if (Target.length > 0) {
                            for (int i = 0; i < Target.length; i++) {
                                if (Target[i].equals(gender) || Target[i] == "Children") {
                                    Valid = true;
                                }
                            }
                            if (Valid == true) {
                                holder.Price.setVisibility(View.VISIBLE);
                                holder.Type.setVisibility(View.VISIBLE);
                                holder.Content.setText(list.get(position).getContent());
                                holder.Full_name.setText(list.get(position).getFname() + " " + list.get(position).getLname());
                                holder.Date.setText(list.get(position).getDate());
                                holder.username.setText(list.get(position).getUsername());
                                holder.Price.setText(list.get(position).getPrice() + " EGP");
                                holder.Type.setText(list.get(position).getProductType());
                                if (list.get(position).getImage_url() != null) {
                                    holder.imageView.setVisibility(View.VISIBLE);
                                    Picasso.get().load(list.get(position).getImage_url()).into(holder.imageView);
                                }
                                if (list.get(position).getImage_user_url() != null)
                                    Picasso.get().load(list.get(position).getImage_user_url()).resize(30, 50).centerCrop().into(holder.imageView2);

                            }
                        }
                    }

                if (mview==null)
                {
                    Date currentTime = Calendar.getInstance().getTime();
                    holder.Price.setVisibility(View.GONE);
                    holder.Type.setVisibility(View.GONE);
                    holder.Content.setText(" No Posts or Adv in this location !!!");
                    holder.Full_name.setText("AppPoint  Team ");
                    holder.Date.setText( currentTime.toString());
                    holder.username.setText("appPoint.team@gmail.com");
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SetImage(final Context ctx, final String image) {
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Content,Full_name,Type, Price, username,Date;
        final public TableRow TR;
        final ImageView imageView;
        final CircleImageView imageView2;

        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            Full_name = itemView.findViewById(R.id.full_name);
            Content = itemView.findViewById(R.id.Content);
            Type = itemView.findViewById(R.id.ProductType);
            Price = itemView.findViewById(R.id.Price);
            TR = itemView.findViewById(R.id.TabRow);
            Date = itemView.findViewById(R.id.Date);
            username = itemView.findViewById(R.id.username);
            imageView = (ImageView) itemView.findViewById(R.id.imageView4);
            imageView2 = (CircleImageView) itemView.findViewById(R.id.imageView2);
            GetPath Get = new GetPath();
            String S = Get.readFromFile(itemView.getContext());
            if (S.isEmpty() == false) {
                Typeface face = Typeface.createFromAsset(itemView.getContext().getAssets(), S);
                Full_name.setTypeface(face);
                Content.setTypeface(face);
                Type.setTypeface(face);
                Price.setTypeface(face);
                Date.setTypeface(face);
                username.setTypeface(face);
            }


        }
    }
}
