package com.service.skillinpocket.ui.feed;

import com.service.skillinpocket.data.DataManager;
import com.service.skillinpocket.ui.base.BaseViewModel;
import com.service.skillinpocket.utils.rx.SchedulerProvider;

/**
 * Created by Jyoti on 29/07/17.
 */

public class FeedViewModel extends BaseViewModel {

    public FeedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
