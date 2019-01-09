package pk.edu.nu.smd.seekers;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import pk.edu.nu.smd.seekers.Models.Post;

/*
* RecyclerView.Adapter
* RecyclerView.ViewHolder
* */
public class PostCardAdapter extends RecyclerView.Adapter<PostCardAdapter.PostCardViewHolder> {

    private Context mCtx;
    private List<Post> PostList;

    public PostCardAdapter(Context mCtx, List<Post> postList) {
        this.mCtx = mCtx;
        PostList = postList;
    }




    @NonNull
    @Override
    public PostCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.post_card, null);
        return new PostCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCardViewHolder holder, int position) {
        final Post post = PostList.get(position);

        holder.username_textview.setText(post.getUserName());
        String amount_txt = "Rs. " + post.getTotalAmount();
        holder.targetAmount_textview.setText(amount_txt);
        holder.description_textview.setText(post.getPostDescription());
        holder.backedby_textview.setText(String.valueOf(post.getBackedBy()));
        holder.days_textview.setText(String.valueOf(post.getDays()));

        double percentage = (post.getRaisedAmount()/post.getTotalAmount())*100;


        holder.progressBar.setProgress((int)percentage);
        String percent_txt = Double.toString(percentage) + "%";
        holder.percentage_textview.setText(percent_txt);


        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), PostDetailViewActivity.class);
                i.putExtra("post", post);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }

    class PostCardViewHolder extends RecyclerView.ViewHolder{

        TextView username_textview;

        TextView targetAmount_textview;
        TextView description_textview;
        TextView percentage_textview;
        TextView backedby_textview;
        TextView days_textview;
        ProgressBar progressBar;


        CardView card_view;

        public PostCardViewHolder(View itemView) {
            super(itemView);

            username_textview = itemView.findViewById(R.id.username_textview);
            card_view = itemView.findViewById(R.id.card_view);


            targetAmount_textview = itemView.findViewById(R.id.textView10);
            description_textview = itemView.findViewById(R.id.textView9);
            percentage_textview = itemView.findViewById(R.id.textView11);
            backedby_textview = itemView.findViewById(R.id.textView12);
            days_textview = itemView.findViewById(R.id.textView7);
            progressBar = itemView.findViewById(R.id.progressBar);

        }


    }



}
