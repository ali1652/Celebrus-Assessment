package multi.screen.d4t4_assessment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText Search;
    private ListView listView;
    private ArticleAdapter articleAdapter;

    private static final String BASE_URL = "https://newsapi.org/";
    private static final String API_KEY = "a9ad79896add45b7b12da4b28d6afa2c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Search = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);

        articleAdapter = new ArticleAdapter(this, new ArrayList<>());
        listView.setAdapter(articleAdapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final NewsApiService apiService = retrofit.create(NewsApiService.class);

        //Make API request
        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                if (!query.isEmpty()) {
                    // Make the API request
                    Call<NewsResponse> call = apiService.getNews(query, API_KEY,"en");
                    call.enqueue(new Callback<NewsResponse>() {
                        @Override
                        public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                            if (response.isSuccessful()) {
                                // update the adapter
                                List<Article> articles = response.body().getArticles();
                                updateAdapter(articles);
                            }
                        }

                        @Override
                        public void onFailure(Call<NewsResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Open article in browser
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Article selectedArticle = articleAdapter.getItem(position);
            if (selectedArticle != null) {
                openArticle(selectedArticle.getUrl());
            }
        });
    }

    private void updateAdapter(List<Article> articles) {
        // Clear data in adapter
        articleAdapter.clear();

        // Add articles to adapter
        if (articles != null) {
            articleAdapter.addAll(articles);
        }

        // Notify that data has changed
        articleAdapter.notifyDataSetChanged();
    }

    public void openArticle(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
