package com.joi.school.fitness.home.articlelist.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.Article;
import com.joi.school.fitness.tools.constant.IntentConstants;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/14 0014 18:04
 */
public class ArticleActivity extends BaseActivity {

    private Article mArticle;
    private TextView mTitleTextView;
    private TextView mBriefTextView;
    private HtmlTextView mContentHtmlTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        if (!getDataFromIntent()) {
            finish();
            return;
        }
        initView();
        setPostData();
    }

    private void setPostData() {
        Article data = mArticle;
        mTitleTextView.setText(data.getTitle());
        mBriefTextView.setText(data.getBrief());
        mContentHtmlTextView.setHtml(data.getContentHtml(), new HtmlHttpImageGetter(mContentHtmlTextView));
    }

    private void initView() {
        mTitleTextView = findViewById(R.id.tv_title);
        mBriefTextView = findViewById(R.id.tv_brief);
        mContentHtmlTextView = findViewById(R.id.tv_content);
    }

    private boolean getDataFromIntent() {
        Intent intent = getIntent();
        mArticle = (Article) intent.getSerializableExtra(IntentConstants.INTENT_KEY_ARTICLE);
        return mArticle != null;
    }

}
