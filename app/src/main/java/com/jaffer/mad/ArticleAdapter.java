package com.jaffer.mad;

import android.content.Context;
import android.content.Intent;
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

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private ArrayList<Article> articleArrayList;
    private Context mContext;

    public ArticleAdapter(ArrayList<Article> articleArrayList, Context mContext) {
        this.articleArrayList = articleArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.article_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {
        Article currentArticle = articleArrayList.get(position);
        holder.bindTo(currentArticle);

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView articleTitle, author, claps, description;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleTitle = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            claps = itemView.findViewById(R.id.claps);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
        }

        public void bindTo(Article article){
            articleTitle.setText(article.getArticleTitle());
            author.setText(article.getAuthor());
            description.setText(article.getDescription());
            claps.setText(article.getClapCount());

            Picasso.with(mContext).load(article.getImageUrl()).into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String webpage = article.getUrl();
                    Intent webViewIntent = new Intent(mContext, BrowserActivity.class);
                    webViewIntent.putExtra("URL", webpage);
                    mContext.startActivity(webViewIntent);
//                    Toast.makeText(mContext, article.getUrl(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
