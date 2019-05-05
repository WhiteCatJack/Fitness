package com.joi.school.fitness.tools.transform;

import com.joi.school.fitness.tools.bean.ExerciseTask;

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
public class ExerciseTaskWrapper {
    private ExerciseTask task;
    private List<String> sportIdList;
    private List<Integer> timeInMinuteList;

    private final String JSON_KEY_SPORT_LIST = "sportList";
    private final String JSON_KEY_TIME_LIST = "timeList";

    public ExerciseTaskWrapper(ExerciseTask task) throws JSONException {
        this.task = task;
        wrap();
    }

    public ExerciseTask getTask() {
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
        timeInMinuteList = new ArrayList<>();
        array = jsonObject.optJSONArray(JSON_KEY_TIME_LIST);
        for (int i = 0; i < array.length(); i++) {
            int timeInMinute = (int) array.get(i);
            timeInMinuteList.add(timeInMinute);
        }
    }

    public static List<ExerciseTaskWrapper> fromOriginal(List<ExerciseTask> taskList) {
        if (taskList == null) {
            return null;
        }
        List<ExerciseTaskWrapper> wrapperList = new ArrayList<>();
        for (ExerciseTask elem : taskList) {
            if (elem == null) {
                continue;
            }
            ExerciseTaskWrapper wrapper = null;
            try {
                wrapper = new ExerciseTaskWrapper(elem);
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
}
