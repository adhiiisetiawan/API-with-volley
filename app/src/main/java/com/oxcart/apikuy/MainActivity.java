package com.oxcart.apikuy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    String url = "https://api.football-data.org/v2/competitions/2021/standings";
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Standings> standingsList;
    private RecyclerView.Adapter adapter;
    private StandingsAdapter standingsAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = findViewById(R.id.list_competition_standings);
        standingsList = new ArrayList<>();
        adapter = new StandingsAdapter(getApplicationContext(), standingsList);
//        standingsAdapter = new StandingsAdapter(getApplicationContext(), standingsList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray standing = response.getJSONArray("standings");

                    JSONObject table = standing.getJSONObject(2);
                    JSONArray jsonArray = table.getJSONArray("table");
                    for (int j = 0; j < jsonArray.length(); j++) {

                        Standings mStandings = new Standings();
                        JSONObject jsonObject = jsonArray.getJSONObject(j);

                        mStandings.setTablePosition(jsonObject.getInt("position"));
                        JSONObject team = jsonObject.getJSONObject("team");

                        mStandings.setClubName(team.getString("name"));
                        mStandings.setLogoClub(team.getString("crestUrl"));

                        mStandings.setPlayedGames(jsonObject.getInt("playedGames"));
                        mStandings.setGoalDifference(jsonObject.getInt("goalDifference"));
                        mStandings.setPoints(jsonObject.getInt("points"));
                        standingsList.add(mStandings);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap();
                header.put("Content-Type", "application/json");
                header.put("X-Auth-Token", "21ee24c4ee2e486b9226f2b1e4a4ba6e");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
//        final MenuItem search = menu.findItem(R.id.action_search);
//        searchView = (SearchView) search.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (!searchView.isIconified()) {
//                    searchView.setIconified(true);
//                }
//                search.collapseActionView();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<Standings> filterModList = (ArrayList<Standings>) filter(standingsList, newText);
//                standingsAdapter.setFilter(filterModList);
//                return true;
//            }
//        });
//        return true;
    }

//    private List<Standings> filter(List<Standings> standList, String query) {
//        query = query.toLowerCase();
//        final List<Standings> filterModelList = new ArrayList<>();
//        for (Standings modal : standList) {
//            final String clubName = modal.getClubName().toLowerCase();
//            if (clubName.startsWith(query)) {
//                filterModelList.add(modal);
//            }
//        }
//        return filterModelList;
//    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Standings> standingsArrayList = new ArrayList<>();
        for (Standings standing : standingsList) {
            String clubName = standing.getClubName().toLowerCase();
            if (clubName.contains(newText)) {
                standingsArrayList.add(standing);
            }
        }

        standingsAdapter.setFilter(standingsArrayList);
        return true;
    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        newText = newText.toLowerCase();
//        ArrayList<Standings> standingsArrayList = new ArrayList<>();
//        for (Standings standing : standingsList) {
//            String clubName = standing.getClubName().toLowerCase();
//            if (clubName.contains(newText)) {
//                standingsArrayList.add(standing);
//            }
//        }
//
//        standingsAdapter.setFilter(standingsArrayList);
//        return true;
//    }
}
