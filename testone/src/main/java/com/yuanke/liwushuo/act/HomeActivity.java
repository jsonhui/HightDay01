package com.yuanke.liwushuo.act;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.constant.Constant;
import com.yuanke.liwushuo.frg.FourFragment;
import com.yuanke.liwushuo.frg.OneFragment;
import com.yuanke.liwushuo.frg.ThreeFragment;
import com.yuanke.liwushuo.frg.TwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.home_rg)
    public RadioGroup group;


    private FragmentTransaction transaction;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        oneFragment = OneFragment.newInstance();
        twoFragment = TwoFragment.newInstance(Constant.HOT);
        threeFragment = ThreeFragment.newInstance();
        fourFragment = FourFragment.newInstance();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.home_fl, oneFragment, "one");
        transaction.add(R.id.home_fl, twoFragment, "two");
        transaction.add(R.id.home_fl, threeFragment, "three");
        transaction.add(R.id.home_fl, fourFragment, "four");
        transaction.commit();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                //只要点击了按钮，就获得事务处理器
                transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.home_rg_one:
                        transaction.show(oneFragment);
                        transaction.hide(twoFragment);
                        transaction.hide(threeFragment);
                        transaction.hide(fourFragment);
                        break;
                    case R.id.home_rg_two:
                        transaction.show(twoFragment);
                        transaction.hide(oneFragment);
                        transaction.hide(threeFragment);
                        transaction.hide(fourFragment);
                        break;
                    case R.id.home_rg_three:
                        transaction.show(threeFragment);
                        transaction.hide(oneFragment);
                        transaction.hide(twoFragment);
                        transaction.hide(fourFragment);
                        break;
                    case R.id.home_rg_four:
                        transaction.show(fourFragment);
                        transaction.hide(oneFragment);
                        transaction.hide(twoFragment);
                        transaction.hide(threeFragment);
                        break;
                }
                // 由于都需要提交事务处理器，这里统一提交。
                transaction.commit();
            }
        });
        group.check(R.id.home_rg_one);

    }

}
