package com.joi.school.fitness.tools.bmobsync;

import com.joi.school.fitness.tools.bean.Advertisement;
import com.joi.school.fitness.tools.bean.Article;
import com.joi.school.fitness.tools.bean.ClientMailbox;
import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.bean.HeatRecord;
import com.joi.school.fitness.tools.bean.Meal;
import com.joi.school.fitness.tools.bean.MealRecommend;
import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.bean.PostTag;
import com.joi.school.fitness.tools.bean.ServerMailbox;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.bean.SportRecommend;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/14 0014 16:41
 */
class SyncBmobQueryFindListenerFactory {

    @SuppressWarnings("unchecked")
    static <T> FindListener<T> getFindListener(Class<T> clazz, final FindListener<T> reflect) {
        if (classEquals(Post.class, clazz)) {
            return (FindListener<T>) new FindListener<Post>() {
                @Override
                public void done(List<Post> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(Article.class, clazz)) {
            return (FindListener<T>) new FindListener<Article>() {
                @Override
                public void done(List<Article> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(Advertisement.class, clazz)) {
            return (FindListener<T>) new FindListener<Advertisement>() {
                @Override
                public void done(List<Advertisement> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(ClientMailbox.class, clazz)) {
            return (FindListener<T>) new FindListener<ClientMailbox>() {
                @Override
                public void done(List<ClientMailbox> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(ServerMailbox.class, clazz)) {
            return (FindListener<T>) new FindListener<ServerMailbox>() {
                @Override
                public void done(List<ServerMailbox> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(Sport.class, clazz)) {
            return (FindListener<T>) new FindListener<Sport>() {
                @Override
                public void done(List<Sport> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(ExerciseTask.class, clazz)) {
            return (FindListener<T>) new FindListener<ExerciseTask>() {
                @Override
                public void done(List<ExerciseTask> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(DoingExerciseTask.class, clazz)) {
            return (FindListener<T>) new FindListener<DoingExerciseTask>() {
                @Override
                public void done(List<DoingExerciseTask> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(PostTag.class, clazz)) {
            return (FindListener<T>) new FindListener<PostTag>() {
                @Override
                public void done(List<PostTag> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(HeatRecord.class, clazz)) {
            return (FindListener<T>) new FindListener<HeatRecord>() {
                @Override
                public void done(List<HeatRecord> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(SportRecommend.class, clazz)) {
            return (FindListener<T>) new FindListener<SportRecommend>() {
                @Override
                public void done(List<SportRecommend> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(MealRecommend.class, clazz)) {
            return (FindListener<T>) new FindListener<MealRecommend>() {
                @Override
                public void done(List<MealRecommend> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        } else if (classEquals(Meal.class, clazz)) {
            return (FindListener<T>) new FindListener<Meal>() {
                @Override
                public void done(List<Meal> list, BmobException e) {
                    reflect.done((List<T>) list, e);
                }
            };
        }
        return null;
    }

    private static boolean classEquals(Class classA, Class classB) {
        return classA.getName().equals(classB.getName());
    }
}
