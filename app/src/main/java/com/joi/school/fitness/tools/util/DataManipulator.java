package com.joi.school.fitness.tools.util;

import android.util.Log;

import com.joi.school.fitness.tools.bean.HeatRecord;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.user.UserEngine;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/23 0023 16:22
 */
public class DataManipulator {

    public static class SportDataManipulator {
        private static final List<String> SPORT_NAME_LIST = Arrays.asList(
                "加速跑(17.5/h)", "快跑(12.9km/h)", "跳绳(快)", "蝶泳", "狗刨泳(4km/h)", "自行车(25.5km/h)", "自由泳(快)",
                "蛙泳", "HIIT(高级)", "拳击", "Zumba", "快走(8km/h)", "自行车(22.4km/h)", "篮球", "网球", "足球", "狗刨泳(2.5km/h)",
                "跳绳(慢)", "爬山", "拳击操", "HIIT(中级)", "自由泳(慢)", "仰泳", "滑雪", "慢跑(6km/h)", "有氧操(高级)",
                "爬楼梯", "自行车(19km/h)", "有氧操(中级)", "HIIT(低级)", "滑板", "有氧操(低级)", "羽毛球", "健美操", "乒乓球",
                "瑜伽", "台球"
        );

        private static final List<Integer> CALORIES_PER_UNIT_LIST = Arrays.asList(
                1367, 1005, 884, 804, 804, 724, 724, 724, 643, 643, 603, 563, 563, 563, 563, 563, 563,
                563, 563, 482, 482, 482, 482, 482, 458, 442, 434, 402, 362, 322, 322, 281, 281, 241,
                241, 161, 121
        );

        private static final String UNIT = "Kcal/h";

        private static Sport buildSport(String sportName, double caloriesPerUnit, String unit, int difficulty) {
            return new Sport()
                    .setSportName(sportName)
                    .setCaloriesPerUnit(caloriesPerUnit)
                    .setCalorieUnit(unit)
                    .setDifficulty(difficulty)
                    .setSportImageUrl("null");
        }

        public static void createSportTable() {
            ExecutorService executor = new ScheduledThreadPoolExecutor(1);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        if (SPORT_NAME_LIST.size() != CALORIES_PER_UNIT_LIST.size()) {
                            throw new BmobException("呵呵");
                        }
                        int size = SPORT_NAME_LIST.size();
                        for (int i = 0; i < size; i++) {
                            String sportName       = SPORT_NAME_LIST.get(i);
                            double caloriesPerUnit = CALORIES_PER_UNIT_LIST.get(i);
                            buildSport(sportName, caloriesPerUnit, UNIT, (int) (caloriesPerUnit / 100)).syncSave();
                        }
                    } catch (BmobException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static class MealDataManipulator {}

    public static class HeatRecordDataManipulator {
        private static final String TAG = "HeatRecordDM";

        private static double random(double l, double h) {
            return l + (h - l) * Math.random();
        }

        private static double getBreakfastIncome() {
            return random(320, 520);
        }

        private static double getLunchIncome() {
            return random(430, 810);
        }

        private static double getDinnerIncome() {
            return random(600, 700);
        }

        private static double getExerciseOutcome() {
            return random(1.2, 1.9) * 500;
        }

        private static String recordToString(HeatRecord record) {
            return "userId=[" + record.getUser().getObjectId() + "]" +
                    "heatChange=[" + record.getHeatChange() + "]" +
                    "type=[" + record.getType() + "]" +
                    "time=[" + record.getTime().getDate() + "]";
        }

        public static void createHeatRecordTable() {
            ExecutorService executor = new ScheduledThreadPoolExecutor(1);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    HeatRecord record   = new HeatRecord().setUser(UserEngine.getInstance().getCurrentUser());
                    Calendar   calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, 2019);
                    calendar.set(Calendar.MONTH, 1);
                    calendar.set(Calendar.DATE, 1);
                    calendar.set(Calendar.SECOND, 0);
                    for (int i = 0; i < 200; i++) {
                        calendar.set(Calendar.HOUR_OF_DAY, 7);
                        calendar.set(Calendar.MINUTE, 30);
                        record.setHeatChange(getBreakfastIncome());
                        record.setTime(new BmobDate(calendar.getTime()));
                        record.setType(HeatRecord.Type.TYPE_BREAKFAST);
                        Log.d(TAG, recordToString(record));
                        calendar.set(Calendar.HOUR_OF_DAY, 12);
                        calendar.set(Calendar.MINUTE, 0);
                        record.setHeatChange(getLunchIncome());
                        record.setTime(new BmobDate(calendar.getTime()));
                        record.setType(HeatRecord.Type.TYPE_LUNCH);
                        Log.d(TAG, recordToString(record));
                        calendar.set(Calendar.HOUR_OF_DAY, 17);
                        calendar.set(Calendar.MINUTE, 30);
                        record.setHeatChange(getDinnerIncome());
                        record.setTime(new BmobDate(calendar.getTime()));
                        record.setType(HeatRecord.Type.TYPE_DINNER);
                        Log.d(TAG, recordToString(record));
                        calendar.set(Calendar.HOUR_OF_DAY, 18);
                        calendar.set(Calendar.MINUTE, 0);
                        record.setHeatChange(-getExerciseOutcome());
                        record.setTime(new BmobDate(calendar.getTime()));
                        record.setType(HeatRecord.Type.TYPE_EXERCISE);
                        Log.d(TAG, recordToString(record));
                        calendar.add(Calendar.DATE, 1);
                    }
                }
            });
        }
    }
}
