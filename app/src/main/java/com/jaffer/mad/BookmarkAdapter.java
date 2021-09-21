package com.jaffer.mad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>{

    private ArrayList<Article> articleArrayList;
    private Context mContext;

    public BookmarkAdapter(ArrayList<Article> articleArrayList, Context mContext) {
        this.articleArrayList = articleArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.bookmark_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        Article currentArticle = articleArrayList.get(position);
        holder.bindTo(currentArticle);
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView bookmarkArticleTitle, bookmarkAuthur, bookmarkDescription;
        private ImageView bookmarkImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookmarkArticleTitle = itemView.findViewById(R.id.bookmark_article_title);
            bookmarkAuthur = itemView.findViewById(R.id.bookmark_article_author);
            bookmarkDescription = itemView.findViewById(R.id.bookmark_article_description);
            bookmarkImage = itemView.findViewById(R.id.bookmark_image);
        }

        public void bindTo(Article article){
            bookmarkArticleTitle.setText(article.getArticleTitle());
            bookmarkAuthur.setText(article.getAuthor());
            bookmarkDescription.setText(article.getDescription());

            Picasso.with(mContext).load(article.getImageUrl()).into(bookmarkImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, article.getArticleTitle(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
