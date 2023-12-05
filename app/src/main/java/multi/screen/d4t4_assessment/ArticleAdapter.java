
package multi.screen.d4t4_assessment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_card_layout, parent, false);
        }


        Article currentArticle = getItem(position);

        TextView title = convertView.findViewById(R.id.Title);
        TextView Desc = convertView.findViewById(R.id.Desc);
        Button openBtn = convertView.findViewById(R.id.OpenArticlebtn);

        if (currentArticle != null) {
            title.setText(currentArticle.getTitle());
            Desc.setText(currentArticle.getDescription());

            openBtn.setOnClickListener(v -> openArticle(currentArticle.getUrl()));
        }


        return convertView;
    }

    private void openArticle(String url) {
        // Open in a web browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        getContext().startActivity(browserIntent);
    }
}
