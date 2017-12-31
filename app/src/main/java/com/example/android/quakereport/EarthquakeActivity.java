/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-01-01&latitude=37.9471084&longitude=23.7421567&maxradiuskm=150&minmag=4&limit=25";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

/**
 * Update the UI with the given earthquake information.
 */
    private void updateUi(List<Earthquake> earthquakes) {
        // Create a fake list of earthquake locations.
        // ArrayList<Earthquake> earthquakes = Utils.extractEarthquakes();
        /*
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        earthquakes.add(new Earthquake("5.2", "San Francisco", "Apr 25, 2017"));
        earthquakes.add(new Earthquake("5.1", "221km W of Puerto Chacabuco, Chile", "27 May, 2017 07:38"));
        earthquakes.add(new Earthquake("4.5", "216km ENE of Socorro Island, Mexico", "27 May, 2017 07:03:50"));
        earthquakes.add(new Earthquake("4.7", "206km NE of Socorro Island, Mexico", "27 May, 2017 06:53"));
        earthquakes.add(new Earthquake("4.6", "69km N of Claveria, Philippines", "27 May, 2017 00:54"));
        earthquakes.add(new Earthquake("4.7", "54km SE of Pondaguitan, Philippines", "26 May, 2017 22:32"));
        earthquakes.add(new Earthquake("5.1", "32km NE of Port-Olry, Vanuatu", "26 May, 2017 22:32"));
        earthquakes.add(new Earthquake("4.7", "North of Svalbard", "26 May, 2017 20:50"));
*/
        // Create an {@link EarthquakeAdapter}, whose data source is a list of
        // {@link Earthquakes}s. The adapter knows how to create list item views for each item
        // in the list.
        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthquakeAdapter);
    }
    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first earthquake in the response.
     *
     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
     * an output type. Our task will take a String URL, and return an Earthquake. We won't do
     * progress updates, so the second generic is just Void.
     *
     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
     * The doInBackground() method runs on a background thread, so it can run long-running code
     * (like network activity), without interfering with the responsiveness of the app.
     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
     * UI thread, so it can use the produced data to update the UI.
     */
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link Earthquake}s as the result.
         */
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            // Perform the HTTP request for earthquake data and process the response.
            List<Earthquake> earthquakesData = Utils.fetchEarthquakeData(urls[0]);
            return earthquakesData;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of earthquake data from a previous
         * query to USGS. Then we update the adapter with the new list of earthquakes,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<Earthquake> result) {
            // Update the information displayed to the user.
            // If there is no result, do nothing.
            if (!(result.size() > 0)) {
                return;
            }
            updateUi(result);
        }

    }
}
