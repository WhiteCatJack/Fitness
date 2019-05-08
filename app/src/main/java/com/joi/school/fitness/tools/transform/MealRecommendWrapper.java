package com.joi.school.fitness.tools.transform;

import com.joi.school.fitness.tools.bean.Meal;
import com.joi.school.fitness.tools.bean.MealRecommend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class MealRecommendWrapper {
    private MealRecommend meal;
    private List<String> breakfastMealIdList;
    private List<String> lunchMealIdList;
    private List<String> dinnerMealIdList;
    private List<Meal> breakfastMealList;
    private List<Meal> lunchMealList;
    private List<Meal> dinnerMealList;

    private final String JSON_KEY_MEAL_LIST = "mealList";
    private final String JSON_KEY_MEAL_BREAKFAST_LIST = "breakfast";
    private final String JSON_KEY_MEAL_LUNCH_LIST = "lunch";
    private final String JSON_KEY_MEAL_DINNER_LIST = "dinner";

    public MealRecommendWrapper(MealRecommend meal) throws JSONException {
        this.meal = meal;
        wrap();
    }

    public MealRecommend getMeal() {
        return meal;
    }

    private void wrap() throws JSONException {
        // e.g.: {"mealList":{"breakfast":["ENZX6662","0Nvh3338"],"lunch":["xWeAQQQX"],"dinner":["e61RHHHk"]}}
        String jsonData = meal.getData();
        JSONObject jsonObject = new JSONObject(jsonData).optJSONObject(JSON_KEY_MEAL_LIST);
        breakfastMealIdList = new ArrayList<>();
        JSONArray array = jsonObject.optJSONArray(JSON_KEY_MEAL_BREAKFAST_LIST);
        for (int i = 0; i < array.length(); i++) {
            String mealId = (String) array.get(i);
            breakfastMealIdList.add(mealId);
        }
        lunchMealIdList = new ArrayList<>();
        array = jsonObject.optJSONArray(JSON_KEY_MEAL_LUNCH_LIST);
        for (int i = 0; i < array.length(); i++) {
            String mealId = (String) array.get(i);
            lunchMealIdList.add(mealId);
        }
        dinnerMealIdList = new ArrayList<>();
        array = jsonObject.optJSONArray(JSON_KEY_MEAL_DINNER_LIST);
        for (int i = 0; i < array.length(); i++) {
            String mealId = (String) array.get(i);
            dinnerMealIdList.add(mealId);
        }
    }

    public static List<MealRecommendWrapper> fromOriginal(List<MealRecommend> mealRecommendList) {
        if (mealRecommendList == null) {
            return null;
        }
        List<MealRecommendWrapper> wrapperList = new ArrayList<>();
        for (MealRecommend elem : mealRecommendList) {
            if (elem == null) {
                continue;
            }
            MealRecommendWrapper wrapper = null;
            try {
                wrapper = new MealRecommendWrapper(elem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (wrapper == null) {
                continue;
            }
            wrapperList.add(wrapper);
        }
        return wrapperList;
    }

    public List<String> getBreakfastMealIdList() {
        return breakfastMealIdList;
    }

    public List<String> getLunchMealIdList() {
        return lunchMealIdList;
    }

    public List<String> getDinnerMealIdList() {
        return dinnerMealIdList;
    }

    public List<Meal> getBreakfastMealList() {
        return breakfastMealList;
    }

    public void setBreakfastMealList(List<Meal> breakfastMealList) {
        this.breakfastMealList = breakfastMealList;
    }

    public List<Meal> getLunchMealList() {
        return lunchMealList;
    }

    public void setLunchMealList(List<Meal> lunchMealList) {
        this.lunchMealList = lunchMealList;
    }

    public List<Meal> getDinnerMealList() {
        return dinnerMealList;
    }

    public void setDinnerMealList(List<Meal> dinnerMealList) {
        this.dinnerMealList = dinnerMealList;
    }
}
