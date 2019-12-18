package com.omni.omnisdk.detection;

import android.os.CountDownTimer;

public  abstract class MyCountDownTimer extends CountDownTimer {

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {

        int progress = (int) (millisUntilFinished/1000);

    }

    @Override
    public void onFinish() {
        counDownTimer(true);
    }

    public abstract void counDownTimer(boolean isFInished);
}

