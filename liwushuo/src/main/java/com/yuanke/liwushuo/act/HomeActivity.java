package com.yuanke.liwushuo.act;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.yuanke.liwushuo.R;
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
        manager = getSupportFragmentManager();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                //只要点击了按钮，就获得事务处理器
                transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.home_rg_one:
                        if (oneFragment == null) {
                            oneFragment = OneFragment.newInstance();
                            transaction.add(R.id.home_fl, oneFragment, "one");
                            if (twoFragment != null) {
                                transaction.hide(twoFragment);
                            }
                            if (threeFragment != null) {
                                transaction.hide(threeFragment);
                            }
                            if (fourFragment != null) {
                                transaction.hide(fourFragment);
                            }
                        } else {
                            transaction.show(oneFragment);
                            if (twoFragment != null) {
                                transaction.hide(twoFragment);
                            }
                            if (threeFragment != null) {
                                transaction.hide(threeFragment);
                            }
                            if (fourFragment != null) {
                                transaction.hide(fourFragment);
                            }
                        }
                        break;
                    case R.id.home_rg_two:
                        if (twoFragment == null) {
                            twoFragment = TwoFragment.newInstance();
                            transaction.add(R.id.home_fl, twoFragment, "two");
                            if (oneFragment != null) {
                                transaction.hide(oneFragment);
                            }
                            if (threeFragment != null) {
                                transaction.hide(threeFragment);
                            }
                            if (fourFragment != null) {
                                transaction.hide(fourFragment);
                            }
                        } else {
                            transaction.show(twoFragment);
                            if (oneFragment != null) {
                                transaction.hide(oneFragment);
                            }
                            if (threeFragment != null) {
                                transaction.hide(threeFragment);
                            }
                            if (fourFragment != null) {
                                transaction.hide(fourFragment);
                            }
                        }
                        break;
                    case R.id.home_rg_three:
                        if (threeFragment == null) {
                            threeFragment = ThreeFragment.newInstance();
                            transaction.add(R.id.home_fl, threeFragment, "three");
                            if (oneFragment != null) {
                                transaction.hide(oneFragment);
                            }
                            if (twoFragment != null) {
                                transaction.hide(twoFragment);
                            }
                            if (fourFragment != null) {
                                transaction.hide(fourFragment);
                            }
                        } else {
                            transaction.show(threeFragment);
                            if (oneFragment != null) {
                                transaction.hide(oneFragment);
                            }
                            if (twoFragment != null) {
                                transaction.hide(twoFragment);
                            }
                            if (fourFragment != null) {
                                transaction.hide(fourFragment);
                            }
                        }
                        break;
                    case R.id.home_rg_four:
                        if (fourFragment == null) {
                            fourFragment = FourFragment.newInstance();
                            transaction.add(R.id.home_fl, fourFragment, "four");
                            if (oneFragment != null) {
                                transaction.hide(oneFragment);
                            }
                            if (twoFragment != null) {
                                transaction.hide(twoFragment);
                            }
                            if (threeFragment != null) {
                                transaction.hide(threeFragment);
                            }
                        } else {
                            transaction.show(fourFragment);
                            if (oneFragment != null) {
                                transaction.hide(oneFragment);
                            }
                            if (twoFragment != null) {
                                transaction.hide(twoFragment);
                            }
                            if (threeFragment != null) {
                                transaction.hide(threeFragment);
                            }
                        }
                        break;
                }
                // 由于都需要提交事务处理器，这里统一提交。
                transaction.commit();
            }
        });
        group.check(R.id.home_rg_one);
    }

}
