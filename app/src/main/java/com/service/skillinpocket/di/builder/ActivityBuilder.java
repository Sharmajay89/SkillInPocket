/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.service.skillinpocket.di.builder;

import com.service.skillinpocket.ui.about.AboutFragmentProvider;
import com.service.skillinpocket.ui.feed.FeedActivity;
import com.service.skillinpocket.ui.feed.FeedActivityModule;
import com.service.skillinpocket.ui.feed.blogs.BlogFragmentProvider;
import com.service.skillinpocket.ui.feed.opensource.OpenSourceFragmentProvider;
import com.service.skillinpocket.ui.login.LoginActivity;
import com.service.skillinpocket.ui.login.LoginActivityModule;
import com.service.skillinpocket.ui.main.MainActivity;
import com.service.skillinpocket.ui.main.MainActivityModule;
import com.service.skillinpocket.ui.main.rating.RateUsDialogProvider;
import com.service.skillinpocket.ui.splash.SplashActivity;
import com.service.skillinpocket.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amitshekhar on 14/09/17.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            FeedActivityModule.class,
            BlogFragmentProvider.class,
            OpenSourceFragmentProvider.class})
    abstract FeedActivity bindFeedActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            AboutFragmentProvider.class,
            RateUsDialogProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();
}
