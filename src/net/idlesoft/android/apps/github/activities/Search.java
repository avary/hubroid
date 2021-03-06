/**
 * Hubroid - A GitHub app for Android
 *
 * Copyright (c) 2011 Eddie Ringle.
 *
 * Licensed under the New BSD License.
 */

package net.idlesoft.android.apps.github.activities;

import com.flurry.android.FlurryAgent;

import net.idlesoft.android.apps.github.R;
import net.idlesoft.android.apps.github.activities.tabs.SearchRepos;
import net.idlesoft.android.apps.github.activities.tabs.SearchUsers;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class Search extends TabActivity {
    private static final String TAG_SEARCH_REPOS = "search_repos";

    private static final String TAG_SEARCH_USERS = "search_users";

    private TabHost mTabHost;

    private View buildIndicator(final int textRes) {
        final TextView indicator = (TextView) getLayoutInflater().inflate(R.layout.tab_indicator,
                getTabWidget(), false);
        indicator.setText(textRes);
        return indicator;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == 5005) {
            Toast.makeText(Search.this, "That user has recently been deleted.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.search);

        /* Hide the Search button in the top bar */
        ((ImageButton) findViewById(R.id.btn_search)).setVisibility(View.GONE);

        mTabHost = getTabHost();

        final Intent intent = new Intent(Search.this, SearchRepos.class);
        mTabHost.addTab(mTabHost.newTabSpec(TAG_SEARCH_REPOS).setIndicator(
                buildIndicator(R.string.search_repos)).setContent(intent));

        intent.setClass(getApplicationContext(), SearchUsers.class);
        mTabHost.addTab(mTabHost.newTabSpec(TAG_SEARCH_USERS).setIndicator(
                buildIndicator(R.string.search_users)).setContent(intent));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

        FlurryAgent.onStartSession(this, "K8C93KDB2HH3ANRDQH1Z");
    }

    @Override
    public void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }
}
