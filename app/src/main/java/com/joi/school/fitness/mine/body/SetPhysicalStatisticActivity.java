package com.joi.school.fitness.mine.body;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joi.school.fitness.BaseActivity;
import com.joi.school.fitness.R;
import com.joi.school.fitness.user.FitnessUser;
import com.joi.school.fitness.user.PhysicalStatistic;
import com.joi.school.fitness.user.UserEngine;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import es.dmoral.toasty.Toasty;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 19:21
 */
public class SetPhysicalStatisticActivity extends BaseActivity {

    private EditText mHeightEditText;
    private EditText mWeightEditText;
    private EditText mHeartRateEditText;
    private EditText mLungCapacityEditText;
    private View mSubmitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_physical_statistic);

        initView();
    }

    private void initView() {
        mHeightEditText = findViewById(R.id.et_height);
        mWeightEditText = findViewById(R.id.et_weight);
        mHeartRateEditText = findViewById(R.id.et_heart_rate);
        mLungCapacityEditText = findViewById(R.id.et_lung_capacity);
        mSubmitButton = findViewById(R.id.bt_submit);
        // 测试用
        mHeightEditText.setText("180");
        mWeightEditText.setText("75");
        mHeartRateEditText.setText("90");
        mLungCapacityEditText.setText("5200");

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                final PhysicalStatistic physicalStatistic = new PhysicalStatistic();
                physicalStatistic.setUser(UserEngine.getCurrentUser());
                physicalStatistic.setHeight(Double.valueOf(mHeightEditText.getText().toString()));
                physicalStatistic.setWeight(Double.valueOf(mWeightEditText.getText().toString()));
                physicalStatistic.setHeartRate(Double.valueOf(mHeartRateEditText.getText().toString()));
                physicalStatistic.setLungCapacity(Double.valueOf(mLungCapacityEditText.getText().toString()));
                physicalStatistic.save(new SaveListener<String>() {
                    @Override
                    public void done(String id, BmobException e) {
                        physicalStatistic.setObjectId(id);

                        FitnessUser fitnessUser = new FitnessUser();
                        fitnessUser.setLatestStatistic(physicalStatistic);
                        fitnessUser.update(UserEngine.getCurrentUser().getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                dismissLoadingDialog();
                                if (e != null){
                                    Toasty.error(getApplicationContext(), R.string.unknown_error, Toast.LENGTH_SHORT, true).show();
                                    return;
                                }
                                Toasty.normal(getApplicationContext(), R.string.hint_set_physical_statistic_complete).show();
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }
}
