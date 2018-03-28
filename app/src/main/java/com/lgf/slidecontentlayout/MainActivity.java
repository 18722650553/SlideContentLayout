package com.lgf.slidecontentlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgf.slidecontentlayout.slidecontentlayout.IInterceptChecker;
import com.lgf.slidecontentlayout.slidecontentlayout.SlideContentLayout;
import com.lgf.slidecontentlayout.test.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,IInterceptChecker {

    private static final String TAG = "MainActivity";

    private RelativeLayout mDataContainerLayout;
    private SlideContentLayout mSlideContentLayout;
    private CustomRecyclerView mDataRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    private DataAdapter mDataAdapter;

    private Button mTitleLayoutActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData(){
        mDatas.clear();
        for (int i = 0; i < 20; i ++){
            mDatas.add(i + "");
        }
    }

    private void initViews(){
        mDataContainerLayout = (RelativeLayout) findViewById(R.id.rl_data_content_layout);
        mSlideContentLayout = (SlideContentLayout) findViewById(R.id.slide_layout);
        //设置事件拦截器
        mSlideContentLayout.setInterceptChecker(this);
        mDataRecyclerView = (CustomRecyclerView) findViewById(R.id.recyclerview_data_list);
        mDataAdapter = new DataAdapter();
        mDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataRecyclerView.setAdapter(mDataAdapter);

        mTitleLayoutActivityBtn = (Button) findViewById(R.id.btn_test1);
        mTitleLayoutActivityBtn.setOnClickListener(this);
//        mSlideContentLayout.setY(600);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test1:

                break;
        }
    }

    @Override
    public boolean checkIfIntercept() {
        View firstChild = mDataRecyclerView.getChildAt(0);
        boolean shouldIntercept;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mDataRecyclerView.getLayoutManager();
        int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (firstVisiblePosition == 0 && firstChild.getTop() == 0) {
            shouldIntercept = true;
        } else {
            shouldIntercept = false;
        }
        return shouldIntercept;
    }

    private class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{

        @Override
        public DataAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_list_item_layout, parent, false);
            DataViewHolder dataViewHolder = new DataViewHolder(layout);
            return dataViewHolder;
        }

        @Override
        public void onBindViewHolder(DataAdapter.DataViewHolder holder, int position) {
            Log.i(TAG, "onBindViewHolder position:" + position);
            Log.i(TAG, "onBindViewHolder getItemCount:" + getItemCount());

            holder.contentTV.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        class DataViewHolder extends RecyclerView.ViewHolder{

            TextView contentTV;

            public DataViewHolder(View itemView) {
                super(itemView);
                contentTV = (TextView) itemView.findViewById(R.id.tv_data_list_item_content);
            }

        }
    }
}
