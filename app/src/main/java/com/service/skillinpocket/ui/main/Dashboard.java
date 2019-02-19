package com.service.skillinpocket.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.service.skillinpocket.BR;
import com.service.skillinpocket.BuildConfig;
import com.service.skillinpocket.R;
import com.service.skillinpocket.databinding.ActivityDashboardBinding;
import com.service.skillinpocket.databinding.ActivityMainBinding;
import com.service.skillinpocket.ui.about.AboutFragment;
import com.service.skillinpocket.ui.base.BaseActivity;
import com.service.skillinpocket.ui.feed.FeedActivity;
import com.service.skillinpocket.ui.login.LoginActivity;
import com.service.skillinpocket.ui.main.rating.RateUsDialog;
import com.service.skillinpocket.utils.ScreenUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class Dashboard extends BaseActivity<ActivityDashboardBinding, DashboardViewModel> implements MainNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private DashboardViewModel mDashboardViewModel;
    private ActivityDashboardBinding mActivityDashboardBinding;
    private Toolbar mToolbar;
    private SwipePlaceHolderView mCardsContainerView;
    private  BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDashboardBinding = getViewDataBinding();
        mDashboardViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        mToolbar = mActivityDashboardBinding.toolbar;
        mCardsContainerView = mActivityDashboardBinding.cardsContainer;
        navigation = mActivityDashboardBinding.navigation;
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setSupportActionBar(mToolbar);
        setupCardContainerView();
        subscribeToLiveData();
    }

    private void setupCardContainerView() {
        int screenWidth = ScreenUtils.getScreenWidth(this);
        int screenHeight = ScreenUtils.getScreenHeight(this);

        mCardsContainerView.getBuilder()
                .setDisplayViewCount(3)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth((int) (0.90 * screenWidth))
                        .setViewHeight((int) (0.75 * screenHeight))
                        .setPaddingTop(20)
                        .setSwipeRotationAngle(10)
                        .setRelativeScale(0.01f));

        mCardsContainerView.addItemRemoveListener(count -> {
            if (count == 0) {
                // reload the contents again after 1 sec delay
                new Handler(getMainLooper()).postDelayed(() -> {
                    //Reload once all the cards are removed
                    mDashboardViewModel.loadQuestionCards();
                }, 800);
            } else {
                mDashboardViewModel.removeQuestionCard();
            }
        });
    }
    private void subscribeToLiveData() {
        mDashboardViewModel.getQuestionCardData().observe(this, questionCardDatas -> mDashboardViewModel.setQuestionDataList(questionCardDatas));
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navItemLogout:
                    mDashboardViewModel.logout();
                    return true;
                case R.id.navItemAbout:
                    showAboutFragment();
                    return true;
                case R.id.navItemRateUs:
                    RateUsDialog.newInstance().show(getSupportFragmentManager());
                    return true;
                case R.id.navItemFeed:
                    startActivity(FeedActivity.newIntent(Dashboard.this));
                    return true;

            }
            return false;
        }
    };



    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    public DashboardViewModel getViewModel() {
        mDashboardViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DashboardViewModel.class);
        return mDashboardViewModel;
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    private void showAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
    }
}
