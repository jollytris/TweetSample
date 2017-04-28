package com.jollytris.tweetsample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TWITTER_KEY = "cUcv6EXhkprPBiX4L2Mq2hnVU";
    private static final String TWITTER_SECRET = "fFvoKg0u8k4zqrCjLAvhYGT7GM6gfi5M9iUOKqm70BJqz8XkeU";

    EditText editText;
    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFabric(this);

        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    listView.setAdapter(retrieveTimeLineByHashtag(MainActivity.this, text));
                }
            }
        });
    }

    public static TweetTimelineListAdapter retrieveTimeLineByHashtag(Context context, String hashtag) {
        SearchTimeline searchTimeline = new SearchTimeline.Builder().query(hashtag).build();
        return new TweetTimelineListAdapter.Builder(context).setTimeline(searchTimeline).build();
    }

    public void initFabric(Context context) {
        if (!Fabric.isInitialized()) {
            TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
            Fabric.with(context, new Twitter(authConfig));
        } else {
            Log.d(TAG, "Fabric already initialized");
        }
    }
}
