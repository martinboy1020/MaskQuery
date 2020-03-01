package com.martinboy.maskquery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.martinboy.MapActivity;
import com.martinboy.database.MaskEntity;
import com.martinboy.database.MaskRepository;
import com.martinboy.managertool.SharePreferenceManager;
import com.martinboy.maskquery.R;
import com.martinboy.maskquery.adapter.DrugAdapter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DrugFragment extends Fragment {

    private SearchView searchView;
    private MaskRepository maskRepository;
    private LiveData<List<MaskEntity>> liveData;
    private RecyclerView recycler_view_drug;
    private DrugAdapter drugAdapter;
    private String city, query;
    private List<MaskEntity> maskList;
    private LinearLayout layoutNoData;
    private List<Object> mapList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(this.getActivity()).inflate(R.layout.fragment_drug, container, false);
        layoutNoData = rootView.findViewById(R.id.layout_no_data);
        recycler_view_drug = rootView.findViewById(R.id.recycler_view_drug);
        searchView = rootView.findViewById(R.id.searchView);
        searchView.setQueryHint("地區或路名查詢");
        recycler_view_drug.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        maskRepository = new MaskRepository(Objects.requireNonNull(this.getActivity()).getApplication());
        liveData = maskRepository.getLiveMaskData();

        drugAdapter = new DrugAdapter(this, null);
        recycler_view_drug.setAdapter(drugAdapter);

        liveData.observe(this, new Observer<List<MaskEntity>>() {
            @Override
            public void onChanged(@Nullable List<MaskEntity> list) {
                if (query != null && !query.equals("")) {
                    maskList = maskRepository.getMaskDataByCityAndSearchQuery(city, query);
                } else {
                    maskList = maskRepository.getMaskDataByCity(city);
                }

                showLayout(maskList);

                if (drugAdapter != null)
                    drugAdapter.setDrugList(maskList);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    maskList = maskRepository.getMaskDataByCity(city);
                } else {
                    maskList = maskRepository.getMaskDataByCityAndSearchQuery(city, query);
                }

                saveQuery(query);

                if (drugAdapter != null)
                    drugAdapter.setDrugList(maskList);

                recycler_view_drug.post(new Runnable() {
                    @Override
                    public void run() {
                        recycler_view_drug.scrollToPosition(0);
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                maskList = maskRepository.getMaskDataByCity(city);
                if (drugAdapter != null)
                    drugAdapter.setDrugList(maskList);
                return false;
            }
        });

    }

    public void queryMap(MaskEntity maskData) {

        if (maskData != null) {

//            Toast.makeText(this.getActivity(), "經度: " + mapDataEntity.getLatitude() + " 緯度" + mapDataEntity.getLongitude(), Toast.LENGTH_SHORT).show();
//            String data = String.format("geo:%s,%s?q=%s,%s (%s)", maskData.getLongitude(), maskData.getLatitude(), maskData.getLongitude(), maskData.getLatitude(), maskData.getName());
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(data));
//            intent.setPackage("com.google.android.apps.maps");
//            startActivity(intent);
            if (this.getActivity() != null) {
                Intent intent = new Intent();
                intent.setClass(this.getActivity(), MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("maskData", maskData);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        } else {

            Toast.makeText(this.getActivity(), "目前無經緯度資料 請等待背景資料更新", Toast.LENGTH_SHORT).show();

        }

    }

    private void saveQuery(String query) {
        this.query = query;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        if (bundle != null) {
            city = bundle.getString("city", "");
        }

        if (query != null && !query.equals("")) {
            maskList = maskRepository.getMaskDataByCityAndSearchQuery(city, query);
        } else {
            maskList = maskRepository.getMaskDataByCity(city);
        }

        mapList = SharePreferenceManager.getObjectListToSharePreference(Objects.requireNonNull(getActivity()), "SP_MASK", "MapData");

        showLayout(maskList);

        if (drugAdapter != null)
            drugAdapter.setDrugList(maskList);

        searchView.clearFocus();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {

            Bundle bundle = getArguments();
            if (bundle != null) {
                city = bundle.getString("city", "");
            }

            maskList = maskRepository.getMaskDataByCity(city);
            mapList = SharePreferenceManager.getObjectListToSharePreference(Objects.requireNonNull(getActivity()), "SP_MASK", "MapData");

            showLayout(maskList);

            if (drugAdapter != null)
                drugAdapter.setDrugList(maskList);

        } else {

            recycler_view_drug.post(new Runnable() {
                @Override
                public void run() {
                    recycler_view_drug.scrollToPosition(0);
                }
            });

            saveQuery("");

            searchView.setQuery("", false);
            searchView.setIconified(true);

        }

    }

    private void showLayout(List<MaskEntity> list) {

        if (list != null && list.size() > 0) {

            layoutNoData.setVisibility(View.GONE);
            searchView.setVisibility(View.VISIBLE);
            recycler_view_drug.setVisibility(View.VISIBLE);

        } else {

            layoutNoData.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.GONE);
            recycler_view_drug.setVisibility(View.GONE);

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        liveData.removeObservers(this);
    }
}
