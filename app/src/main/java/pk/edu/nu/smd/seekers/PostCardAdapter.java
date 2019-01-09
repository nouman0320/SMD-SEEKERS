package pk.edu.nu.smd.seekers;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*
* RecyclerView.Adapter
* RecyclerView.ViewHolder
* */
public class PostCardAdapter extends RecyclerView.Adapter<PostCardAdapter.PostCardViewHolder> {

    private Context mCtx;
    private List<PostCard> PostCardList;

    public PostCardAdapter(Context mCtx, List<PostCard> postCardList) {
        this.mCtx = mCtx;
        PostCardList = postCardList;
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
        PostCard card = PostCardList.get(position);

        holder.username_textview.setText(card.getUsername());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new
                        Intent(v.getContext(),PostDetailViewActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return PostCardList.size();
    }

    class PostCardViewHolder extends RecyclerView.ViewHolder{

        TextView username_textview;

        CardView card_view;

        public PostCardViewHolder(View itemView) {
            super(itemView);

            username_textview = itemView.findViewById(R.id.username_textview);
            card_view = itemView.findViewById(R.id.card_view);

        }


    }



}
