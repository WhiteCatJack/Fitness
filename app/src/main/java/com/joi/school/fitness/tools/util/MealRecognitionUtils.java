package com.joi.school.fitness.tools.util;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.joi.school.fitness.tools.bean.Meal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/4 0004 12:52
 */
public class MealRecognitionUtils {
    private static AipImageClassify sInstance;

    public static AipImageClassify getsInstance() {
        return sInstance;
    }

    public static void setsInstance(AipImageClassify sInstance) {
        MealRecognitionUtils.sInstance = sInstance;
    }

    public static Meal getResult(String json) {
        Meal meal;
        try {
            JSONObject jsonObject = (JSONObject) new JSONObject(json).getJSONArray("result").get(0);
            meal = new Meal();
            meal.setMealName(jsonObject.getString("name"));
            meal.setCalories(jsonObject.getDouble("calorie"));
            meal.setMealImageUrl(jsonObject.getJSONObject("baike_info").getString("image_url"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return meal;
    }
}
