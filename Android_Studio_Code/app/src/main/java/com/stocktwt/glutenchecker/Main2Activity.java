package com.stocktwt.glutenchecker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Main2Activity extends ActionBarActivity {

    private Toolbar toolbar;

    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private static final String TAG = Main2Activity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mPager.setBackgroundColor(getResources().getColor(R.color.colorback));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
        mTabs.setBackgroundColor(getResources().getColor(R.color.tabs));


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }


    public static class MyFragment extends Fragment {
        private TextView textView;
        public EditText name;
        public String entername;
        private Button enter;
        private String response;

        private EditText frag2_name;
        private EditText frag2_email;
        private EditText frag2_food_name;
        private EditText frag2_gluten_free;
        private Button submit;

        private TextView count;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);

            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            Bundle bundle = getArguments();
            if (bundle.getInt("position") == 0) {
                View layout = inflater.inflate(R.layout.fragment_my, container, false);
                enter = (Button) layout.findViewById(R.id.enter);
                name = (EditText) layout.findViewById(R.id.name);
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        entername = name.getText().toString();

                        if(entername.length()>0) {
                            String tag_string_reg = "req_gluten";

                            final ProgressDialog pDialog;
                            pDialog = new ProgressDialog(getActivity());
                            pDialog.setCancelable(false);

                            pDialog.setMessage("Retreiving data...");
                            if (!pDialog.isShowing()) {
                                pDialog.show();
                            }


                            StringRequest strReg = new StringRequest(Request.Method.POST, AppConfig.URL_API,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.d(TAG, response);
                                                if (pDialog.isShowing()) {
                                                    pDialog.dismiss();
                                                }
                                                JSONObject jObj = new JSONObject(response);
                                                boolean error = jObj.getBoolean("error");

                                                final String gluten = jObj.getString("gluten");
                                                Toast.makeText(getActivity(), gluten, Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, "Logging error: " + error.getMessage());
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    //Posting weight params to weight url
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("tag", "gluten_check");
                                    params.put("name", entername);
                                    return params;
                                }
                            };
                            AppController.getInstance().addToRequestQueue(strReg, tag_string_reg);
                        }
                        else{
                            Toast.makeText(getActivity(), "Please Enter a Food Item", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return layout;
            } else if (bundle.getInt("position") == 1) {
                View layout = inflater.inflate(R.layout.frag2, container, false);
                frag2_name = (EditText) layout.findViewById(R.id.edit_name);
                frag2_email = (EditText) layout.findViewById(R.id.edit_email);
                frag2_food_name = (EditText) layout.findViewById(R.id.edit_food);
                frag2_gluten_free = (EditText) layout.findViewById(R.id.edit_glutenfree);
                submit = (Button) layout.findViewById(R.id.submitbutton);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String name, email, food_name, gluten_free;
                        name = frag2_name.getText().toString();
                        email = frag2_email.getText().toString();
                        food_name = frag2_food_name.getText().toString();
                        gluten_free = frag2_gluten_free.getText().toString();


                        if (name.length()==0 || email.length()==0 || food_name.length()==0 ||gluten_free.length()==0){
                            Toast.makeText(getActivity(), "Please make sure form is completly filled out", Toast.LENGTH_LONG).show();

                        }else {
                            String tag_string_reg = "store_sub";

                            final ProgressDialog pDialog;
                            pDialog = new ProgressDialog(getActivity());
                            pDialog.setCancelable(false);

                            pDialog.setMessage("Submitting data...");
                            if (!pDialog.isShowing()) {
                                pDialog.show();
                            }

                            StringRequest strReg = new StringRequest(Request.Method.POST, AppConfig.URL_API,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.d(TAG, response);
                                                if (pDialog.isShowing()) {
                                                    pDialog.dismiss();
                                                }
                                                JSONObject jObj = new JSONObject(response);

                                                final String response_sub = jObj.getString("response_sub");
                                                Toast.makeText(getActivity(), response_sub, Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, "Logging error: " + error.getMessage());
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    //Posting weight params to weight url
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("tag", "sub_insert");
                                    params.put("name", name);
                                    params.put("email", email);
                                    params.put("food_name", food_name);
                                    params.put("gluten_free", gluten_free);
                                    return params;
                                }
                            };
                            AppController.getInstance().addToRequestQueue(strReg, tag_string_reg);
                            startActivity(new Intent(getActivity(), Main2Activity.class));
                        }
                    }
                });
                return layout;
            } else if (bundle.getInt("position") == 2) {
                View layout = inflater.inflate(R.layout.frag3, container, false);
                count = (TextView) layout.findViewById(R.id.count);
                String tag_string_reg = "store_sub";



                StringRequest strReg = new StringRequest(Request.Method.POST, AppConfig.URL_API,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d(TAG, response);



                                    JSONObject jObj = new JSONObject(response);

                                    final String database_count = jObj.getString("count");
                                    count.setText(database_count);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Logging error: " + error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        //Posting weight params to weight url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tag", "count");
                        return params;
                    }
                };
                AppController.getInstance().addToRequestQueue(strReg, tag_string_reg);
                return layout;
            }

           return null;
        }

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
