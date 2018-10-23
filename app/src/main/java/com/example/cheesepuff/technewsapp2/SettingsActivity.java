package com.example.cheesepuff.technewsapp2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    // create a PreferenceFragment and also with PreferenceChangeListner
    public static class TechnewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            //create searchKeyword preference from resource
            Preference searchKeyword = findPreference(getString(R.string.settings_search_keyword_key));
            // bing together value by custom class bindPrefenceSummaryToValue
            bindPreferenceSummaryToValue(searchKeyword);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);

        }

        // override onPreferenceChange
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            // code in this method takes care of updating the display preference summary after it has been changed
            String stringValue = value.toString();

            // a switch statement to determine if it's a ListPreference / regular preference
            if(preference instanceof ListPreference) {
                // if it is a ListPreference then to create object
                ListPreference listPreference = (ListPreference) preference;

                // get the current preferece index from stringValue
                int prefIndex = listPreference.findIndexOfValue(stringValue);

                // if the index is equal / greater than 0
                if (prefIndex >= 0){

                    // create a label from the list entry
                    CharSequence[] labels = listPreference.getEntries();

                    // set its value by current index of labels
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                // if else set its value by stringValue
                preference.setSummary(stringValue);
            }

            // return true condition
            return true;
        }

        // custom class to bind the value from preference
        private void bindPreferenceSummaryToValue(Preference preference){
            // get the current preference state
            preference.setOnPreferenceChangeListener(this);

            // create current preference from the default R resource
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext());

            // get the string
            String preferenceString = preferences.getString(preference.getKey(), "");

            // set a new preference
            onPreferenceChange(preference, preferenceString);
        }

    }
}