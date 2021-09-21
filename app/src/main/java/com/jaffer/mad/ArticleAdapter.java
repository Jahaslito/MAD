package com.jaffer.mad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

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
        private ImageButton bookmarkBtn;

        private FirebaseDatabase database;
        private FirebaseAuth auth;
        private FirebaseUser user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleTitle = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            claps = itemView.findViewById(R.id.claps);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            bookmarkBtn = itemView.findViewById(R.id.bookmarkBtn);

            database = FirebaseDatabase.getInstance();
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
        }
        boolean bookmarked = false;
        String bookmarkedKey;
        public void bindTo(Article article){
            articleTitle.setText(article.getArticleTitle());
            author.setText(article.getAuthor());
            description.setText(article.getDescription());
            claps.setText(article.getClapCount());

            Picasso.with(mContext).load(article.getImageUrl()).into(image);

            DatabaseReference bookmarkRef = database.getReference("Bookmarks").child(user.getUid());
            bookmarkRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Iterator<DataSnapshot> bookmarks = snapshot.getChildren().iterator();
                    while (bookmarks.hasNext()){
                        DataSnapshot bookmark = bookmarks.next();
                        if (bookmark.child("articleTitle").getValue().equals(article.getArticleTitle())) {
                            bookmarked = true;
                            bookmarkBtn.setImageResource(R.drawable.ic_bookmark_marked);
                            bookmarkedKey = bookmark.getKey();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            bookmarkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    assert user != null;
                    DatabaseReference reference = database.getReference("Bookmarks").child(user.getUid());
                    if (!bookmarked) {
                        String key = reference.push().getKey();
                        assert key != null;
                        reference.child(key).setValue(article);
                        bookmarkBtn.setImageResource(R.drawable.ic_bookmark_marked);
                        Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
                    }else {
                        reference.child(bookmarkedKey).removeValue();
                        bookmarkBtn.setImageResource(R.drawable.ic_bookmark_unmarked);
                        bookmarked = false;
                        Toast.makeText(mContext, "Unsaved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

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
