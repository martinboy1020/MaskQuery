package com.martinboy.database;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MaskDataProfileViewModel extends AndroidViewModel {

    private static final String TAG = MaskDataProfileViewModel.class.getSimpleName();
    private LiveData<List<MaskEntity>> maskEntityLiveData;
    private MaskRepository maskRepository;

    public MaskDataProfileViewModel(@NonNull Application application) {
        super(application);
        maskRepository = new MaskRepository(application);
        maskEntityLiveData = maskRepository.getLiveMaskData();
    }

    public LiveData<List<MaskEntity>> getMaskEntityLiveData() {
        return maskEntityLiveData;
    }
}
