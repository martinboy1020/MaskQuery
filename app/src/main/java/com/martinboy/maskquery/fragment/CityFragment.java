package com.martinboy.maskquery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.martinboy.maskquery.MainActivity;
import com.martinboy.maskquery.R;
import com.martinboy.maskquery.adapter.CityAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CityFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerViewCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_city, container, false);
        recyclerViewCity = rootView.findViewById(R.id.recycler_view_city);
        swipeRefresh = rootView.findViewById(R.id.swipe_refresh);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null && getActivity().getResources() != null) {
            String[] cityArray = getActivity().getResources().getStringArray(R.array.city);
            CityAdapter cityAdapter = new CityAdapter(getActivity(), cityArray);
            recyclerViewCity.setAdapter(cityAdapter);
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getActivity() != null && getActivity() instanceof MainActivity) {

                    if (!((MainActivity) getActivity()).isUpdating) {
                        ((MainActivity) getActivity()).startRefreshMaskData(true);
                    }

                    swipeRefresh.setRefreshing(false);

                }
            }
        });

        Toast.makeText(getActivity(), "於此頁面置頂時下拉即可更新最新資料", Toast.LENGTH_SHORT).show();

    }
}
