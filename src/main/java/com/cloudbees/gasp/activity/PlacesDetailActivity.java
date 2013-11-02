package com.cloudbees.gasp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cloudbees.gasp.R;
import com.cloudbees.gasp.model.PlaceDetail;
import com.cloudbees.gasp.model.PlaceEvent;

import java.util.ArrayList;

/**
 * Copyright (c) 2013 Mark Prichard, CloudBees
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class PlacesDetailActivity extends Activity {
    private final static String TAG = PlacesDetailActivity.class.getName();

    private TextView mName;
    private TextView mWebsite;
    private TextView mAddress;
    private TextView mPhone;
    private TextView mId;
    private TextView mLatitude;
    private TextView mLongitude;
    private ListView mEventsView;


    private ArrayAdapter<String> mEventAdapter;
    private final ArrayList<String> mEventList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PlaceDetail place = (PlaceDetail) getIntent().getSerializableExtra("PlaceDetail");
            Log.d(TAG, "Name: " + place.getName());
            Log.d(TAG, "Website " + place.getWebsite());
            Log.d(TAG, "Address: " + place.getFormatted_address());
            Log.d(TAG, "Id: " + place.getId());
            Log.d(TAG, "Lat: " + place.getGeometry().getLocation().getLat());
            Log.d(TAG, "Lng: " + place.getGeometry().getLocation().getLng());

            setContentView(R.layout.places_detail_layout);
            mName = (TextView) findViewById(R.id.detail_name);
            mAddress = (TextView) findViewById(R.id.detail_address);
            mPhone = (TextView) findViewById(R.id.detail_phone);
            mWebsite = (TextView) findViewById(R.id.detail_website);
            mId = (TextView) findViewById(R.id.detail_id);
            mLatitude = (TextView) findViewById(R.id.detail_latitude);
            mLongitude = (TextView) findViewById(R.id.detail_longitude);
            mEventsView = (ListView) findViewById(R.id.detail_events);

            mName.setText(place.getName());
            mAddress.setText(place.getFormatted_address());
            mPhone.setText(place.getFormatted_phone_number());
            mWebsite.setText(place.getWebsite());
            mId.setText(place.getId());
            mLatitude.setText("Latitude: " + place.getGeometry().getLocation().getLat().toString());
            mLongitude.setText("Longitude: " + place.getGeometry().getLocation().getLng().toString());

            mEventAdapter = new ArrayAdapter<String>(this, R.layout.item_label_list, mEventList);
            mEventsView.setAdapter(mEventAdapter);

            if (place.getEvents() != null) {
                for (PlaceEvent event : place.getEvents()) {
                    Log.d(TAG, "Event Id: " + event.getEvent_id());
                    Log.d(TAG, "Event Summary: " + event.getSummary());
                    mEventAdapter.add(event.getEvent_id() + ": " + event.getSummary());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
