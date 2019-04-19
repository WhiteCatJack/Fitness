package com.joi.school.fitness.tools.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

/**
 * @author Joi
 * createAt 2019/3/22 0022 17:24
 */
@SuppressLint("Registered")
public abstract class BaseFragment extends Fragment {

    private ProgressDialog progressDialog;

    @Override
    public void onDestroy() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onDestroy();
    }

    private void checkAndInitLoadingDialog() {
        if (progressDialog != null) {
            return;
        }
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    protected void showLoadingDialog() {
        checkAndInitLoadingDialog();
        progressDialog.show();
    }

    protected void dismissLoadingDialog() {
        checkAndInitLoadingDialog();
        progressDialog.dismiss();
    }
}
