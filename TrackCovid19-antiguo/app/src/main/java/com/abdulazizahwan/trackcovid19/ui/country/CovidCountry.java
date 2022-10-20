package com.abdulazizahwan.trackcovid19.ui.country;

public class CovidCountry {
    String mCovidCountry, mCases, mTodayCases, mDeaths, mTodayDeaths, mRecovered, mCritical;

    public CovidCountry(String mCovidCountry, String mCases, String mTodayCases, String mDeaths, String mTodayDeaths, String mRecovered) {
        this.mCovidCountry = mCovidCountry;
        this.mCases = mCases;
        this.mTodayCases = mTodayCases;
        this.mDeaths = mDeaths;
        this.mTodayDeaths = mTodayDeaths;
        this.mRecovered = mRecovered;
        this.mCritical = mCritical;
    }

    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public String getmCases() {
        return mCases;
    }

    public String getmRecovered() {
        return mRecovered;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public String getmTodayCases() {
        return mTodayCases;
    }

    public String getmTodayDeaths() {
        return mTodayDeaths;
    }
}
