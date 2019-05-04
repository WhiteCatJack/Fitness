package com.joi.school.fitness.core.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.core.sport.task.ExerciseTaskListActivity;
import com.joi.school.fitness.tools.base.BaseFragment;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.bean.SportRecommend;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.transform.SportRecommendWrapper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/5/4 0004 18:56
 */
public class SportRecommendFragment extends BaseFragment {

    private View mMyTaskButton;
    private RecyclerView mTaskRecommendRecyclerView;

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_sport_recommend, container, false);

        mMyTaskButton = layout.findViewById(R.id.bt_my_task);
        mTaskRecommendRecyclerView = layout.findViewById(R.id.rv_list);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMyTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ExerciseTaskListActivity.class));
            }
        });

        getSportRecommendList();
    }

    private void getSportRecommendList() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final List<SportRecommendWrapper> resultList = new ArrayList<>();
                try {
                    SyncBmobQuery<Sport> sportQuery = new SyncBmobQuery<>(Sport.class);
                    List<Sport> sportList = sportQuery.syncFindObjects();

                    SyncBmobQuery<SportRecommend> query = new SyncBmobQuery<>(SportRecommend.class);
                    query.order("-createdAt");
                    query.setLimit(30);
                    List<SportRecommend> sportRecommendList = query.syncFindObjects();

                    for (SportRecommend sportRecommend : sportRecommendList) {
                        SportRecommendWrapper wrapper = new SportRecommendWrapper(sportRecommend);
                        List<Sport> taskSportList = new ArrayList<>();
                        List<String> sportIdList = wrapper.getSportIdList();
                        for (String id : sportIdList) {
                            for (Sport sport : sportList) {
                                if (sport.getObjectId().equals(id)) {
                                    taskSportList.add(sport);
                                }
                            }
                        }
                        wrapper.setSportList(taskSportList);
                        resultList.add(wrapper);
                    }

                } catch (BmobException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSportRecommendList(resultList);
                    }
                });
            }
        });
    }

    private void showSportRecommendList(List<SportRecommendWrapper> dataList) {

    }
}