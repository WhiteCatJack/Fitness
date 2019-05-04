package com.joi.school.fitness.tools.transform;

import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.bean.SportRecommend;

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
public class SportRecommendWrapper {
    private SportRecommend task;
    private List<String> sportIdList;
    private List<Sport> sportList;
    private List<Integer> timeInMinuteList;

    private final String JSON_KEY_SPORT_LIST = "sportList";
    private final String JSON_KEY_TIME_LIST = "timeList";

    public SportRecommendWrapper(SportRecommend task) throws JSONException {
        this.task = task;
        wrap();
    }

    public SportRecommend getTask() {
        return task;
    }

    public List<String> getSportIdList() {
        return sportIdList;
    }

    public List<Integer> getTimeInMinuteList() {
        return timeInMinuteList;
    }

    private void wrap() throws JSONException {
        // e.g.: {"sportList":["88bda80fdc","799a624463","3b3e243214"],"timeList":[42,42,42]}
        String jsonData = task.getData();
        JSONObject jsonObject = new JSONObject(jsonData);
        sportIdList = new ArrayList<>();
        JSONArray array = jsonObject.optJSONArray(JSON_KEY_SPORT_LIST);
        for (int i = 0; i < array.length(); i++) {
            String sportId = (String) array.get(i);
            sportIdList.add(sportId);
        }
        array = jsonObject.optJSONArray(JSON_KEY_TIME_LIST);
        for (int i = 0; i < array.length(); i++) {
            Double timeInMinute = Double.valueOf((String) array.get(i));
            timeInMinuteList.add(timeInMinute.intValue());
        }
    }

    public static List<SportRecommendWrapper> fromOriginal(List<SportRecommend> taskList) {
        if (taskList == null) {
            return null;
        }
        List<SportRecommendWrapper> wrapperList = new ArrayList<>();
        for (SportRecommend elem : taskList) {
            if (elem == null) {
                continue;
            }
            SportRecommendWrapper wrapper = null;
            try {
                wrapper = new SportRecommendWrapper(elem);
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

    public SportRecommendWrapper setSportList(List<Sport> sportList) {
        this.sportList = sportList;
        return this;
    }

    public List<Sport> getSportList() {
        return sportList;
    }
}
