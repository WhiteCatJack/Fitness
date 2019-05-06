package com.joi.school.fitness;

import android.graphics.Bitmap;

import com.joi.school.fitness.tools.bean.Meal;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/5/4 0004 17:30
 */
public interface IHomeContract {
    interface View {
        void mealRecognitionDone(Meal meal, String resultJson);
    }

    interface Presenter {
        void doMealRecognition(Bitmap bitmap);
    }
}
