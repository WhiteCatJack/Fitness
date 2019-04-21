package com.joi.school.fitness.tools.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.joi.school.fitness.R;

/**
 * @author Joi
 * createAt 2019/3/22 0022 17:24
 */
@SuppressLint("Registered")
public abstract class BaseFragment extends Fragment {

    private Dialog mProgressDialog;

    @Override
    public void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        super.onDestroy();
    }

    private void checkAndInitLoadingDialog() {
        if (mProgressDialog != null) {
            return;
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_progress, null, false);
        mProgressDialog = new AlertDialog.Builder(getContext()).setView(view).create();
    }

    protected void showLoadingDialog() {
        checkAndInitLoadingDialog();
        mProgressDialog.show();
    }

    protected void dismissLoadingDialog() {
        checkAndInitLoadingDialog();
        mProgressDialog.dismiss();
    }
}
