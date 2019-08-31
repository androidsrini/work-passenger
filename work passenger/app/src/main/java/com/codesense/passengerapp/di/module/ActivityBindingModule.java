package com.codesense.passengerapp.di.module;


import com.codesense.passengerapp.ui.account.AccountActivity;
import com.codesense.passengerapp.ui.agreement.AgreementActivity;
import com.codesense.passengerapp.ui.business.BusinessActivity;
import com.codesense.passengerapp.ui.editmobilenumber.EditMobileNumberActivity;
import com.codesense.passengerapp.ui.getname.GetNameActivity;
import com.codesense.passengerapp.ui.home.HomeActivity;
import com.codesense.passengerapp.ui.launch.LaunchScreenActivity;
import com.codesense.passengerapp.ui.socialaccount.SocialAccountActivity;
import com.codesense.passengerapp.ui.splash.SplashActivity;
import com.codesense.passengerapp.ui.verifymobile.VerifyMobileActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract LaunchScreenActivity bindLaunchScreenActivity();

    @ContributesAndroidInjector
    abstract VerifyMobileActivity bindVerifyMobileActivity();

    @ContributesAndroidInjector
    abstract SocialAccountActivity bindSocialAccountActivity();

    @ContributesAndroidInjector
    abstract EditMobileNumberActivity bindEditMobileNumberActivity();

    @ContributesAndroidInjector
    abstract GetNameActivity bindGetNameActivity();

    @ContributesAndroidInjector
    abstract AgreementActivity bindAgreementActivity();

    @ContributesAndroidInjector
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector
    abstract AccountActivity bindAccountActivity();

    @ContributesAndroidInjector
    abstract BusinessActivity bindBusinessActivity();


}
