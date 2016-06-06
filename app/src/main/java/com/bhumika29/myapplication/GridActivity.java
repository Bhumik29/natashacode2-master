package com.bhumika29.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.buddy.sdk.Buddy;
import com.buddy.sdk.BuddyCallback;
import com.buddy.sdk.BuddyResult;
import com.buddy.sdk.models.PagedResult;
import com.buddy.sdk.models.Picture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Natasha on 5/5/2016.
 */
public class GridActivity extends AppCompatActivity {
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);

        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);

        //Grid view click event
       mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                System.out.println("Grid pic selected");
                //Get item at position
                GridItem item = (GridItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(GridActivity.this, DetailsActivity.class);
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);
                EditText postComments  = (EditText) findViewById(R.id.comment);
                // Interesting data to pass across are the thumbnail size/location, the
                // resourceId of the source bitmap, the picture description, and the
                // orientation (to avoid returning back to an obsolete configuration if
                // the device rotates again in the meantime)

                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);
                //Pass the image title and url to DetailsActivity
                intent.putExtra("image", item.getImage()).putExtra("postComments" , postComments.getText().toString());

                //Start details activity
               startActivity(intent);
            }
        });

        //Start download
        new AsyncHttpTask().execute();
        mProgressBar.setVisibility(View.VISIBLE);

    }



    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;

            try {
                Map<String, Object> parameters = new HashMap<String, Object>();
                Buddy.get("/pictures", parameters, new BuddyCallback<PagedResult>(PagedResult.class) {
                    @Override
                    public void completed(BuddyResult<PagedResult> result) {
                        List<Picture> resultList = result.getResult().convertPageResults(Picture.class);
                        if (result.getIsSuccess()) {
                            GridItem item;
                            com.buddy.sdk.models.Picture pic;
                            for (int i = 0; i < resultList.size(); i++){
                                try{
                                    item = new GridItem();
                                    pic = (com.buddy.sdk.models.Picture) resultList.get(i);
                                    item.setImage(pic.getJsonObject().get("signedUrl").getAsString());
                                    item.setTitle(pic.title);
                                    mGridData.add(item);
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }

    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

   /* public void post(View view) {
        Intent intent = new Intent(GridActivity.this, DetailsActivity.class);

        EditText postComments  = (EditText) findViewById(R.id.comment);

        intent.putExtra("postComments" , postComments.getText().toString());

        startActivity(intent);
    }*/


}
