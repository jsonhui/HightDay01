package com.qmxy.kuaidu.frg;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qmxy.kuaidu.R;
import com.qmxy.kuaidu.act.AboutActivity;
import com.qmxy.kuaidu.act.YjActivity;
import com.qmxy.kuaidu.utils.commontool.HttpUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {
    @BindView(R.id.circle_image_one)
    CircleImageView circle_image;
    @BindView(R.id.ll_me_three)
    LinearLayout layoutThree;
    @BindView(R.id.ll_me_five)
    LinearLayout layoutFive;
    @BindView(R.id.ll_me_six)
    LinearLayout layoutSix;
    @BindView(R.id.ll_me_four)
    LinearLayout layoutFour;
    @BindView(R.id.text_cache1)
    TextView cache;
    @BindView(R.id.yh_text)
    TextView yhText;
    public ProgressDialog progressDialog;

    public static ThreeFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putSerializable("path", path);
        ThreeFragment fragment = new ThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ThreeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.bind(this, view);
        final SharedPreferences preferences = getContext().getSharedPreferences("phone", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("flag", 2);
        editor.commit();
        /**初始化缓存大小数据**/
        try {
            String cacheSize = HttpUtils.getTotalCacheSize(getContext());
            cache.setText("当前缓存大小为" + cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = (String) getArguments().getSerializable("path");
        circle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getInt("flag", 1) == 1) {
                    Toast.makeText(getContext(), "你已经登录！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    SMSSDK.initSDK(getContext(), "15b8133bc624b", "070f0241fb8b83fa2bb2bb4e1aa4c6d8");
                    //打开注册页面
                    RegisterPage registerPage = new RegisterPage();
                    registerPage.setRegisterCallback(new EventHandler() {
                        public void afterEvent(int event, int result, Object data) {
                            // 解析注册结果
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                @SuppressWarnings("unchecked")
                                HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                                //String country = (String) phoneMap.get("country");
                                String phone = (String) phoneMap.get("phone");
                                editor.putInt("flag", 1);
                                editor.putString("phone", phone);
                                //提交当前数据
                                editor.commit();
                                yhText.setText(preferences.getString("phone", "登录"));
                                // 提交用户信息（此方法可以不调用）
                                //registerUser(country, phone);
                            }
                        }
                    });
                    registerPage.show(getContext());
                }
            }
        });


//        circle_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), LoginActivity.class));
//            }
//        });
        /**cache的监听**/
        layoutThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setMessage("你确定清除缓存吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                new AsyncTask<Void, Integer, String>() {

                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        progressDialog = ProgressDialog.show(getContext(), "", "正在清除...");
                                        //LogTool.log(getClass(), progressDialog.toString());
                                    }

                                    @Override
                                    protected String doInBackground(Void... params) {
                                        /**清除缓存**/
                                        try {
                                            Thread.sleep(3000);
                                            HttpUtils.clearAllCache(getContext());
                                            String cacheSize = HttpUtils.getTotalCacheSize(getContext());
                                            return cacheSize;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        return "";
                                    }

                                    @Override
                                    protected void onPostExecute(String temp) {
                                        progressDialog.dismiss();
                                        if (!"".equals(temp)) {
                                            cache.setText("当前缓存大小为" + temp);
                                        } else {
                                            cache.setText("当前缓存大小为" + "???");
                                        }
                                    }
                                }.execute();
                            }
                        })
                        .show();
            }
        });
//        layoutSix.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editor.putInt("flag", 2);
//                editor.commit();
//                yhText.setText("登录");
//            }
//        });
        layoutFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), YjActivity.class));
            }
        });
        layoutSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getInt("flag", 0) == 2) {
                    Toast.makeText(getContext(), "你还没有登录过！", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(getContext())
                        .setMessage("你确定要退出吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                progressDialog = ProgressDialog.show(getContext(), "", "正在退出...");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            editor.putInt("flag", 2);
                                            editor.commit();
                                            Thread.sleep(3000);
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialog.dismiss();
                                                    yhText.setText("登录");
                                                }
                                            });
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
//                                new AsyncTask<Void, Integer, String>() {
//                                    @Override
//                                    protected void onPreExecute() {
//                                        super.onPreExecute();
//                                        progressDialog = ProgressDialog.show(getContext(), "", "正在清除...");
//                                        //LogTool.log(getClass(), progressDialog.toString());
//                                    }
//
//                                    @Override
//                                    protected String doInBackground(Void... params) {
//                                        /**清除缓存**/
//                                        try {
//                                            Thread.sleep(3000);
//                                            HttpUtils.clearAllCache(getContext());
//                                            String cacheSize = HttpUtils.getTotalCacheSize(getContext());
//                                            return cacheSize;
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                        return "";
//                                    }
//
//                                    @Override
//                                    protected void onPostExecute(String temp) {
//                                        progressDialog.dismiss();
//                                        if (!"".equals(temp)) {
//                                            cache.setText("当前缓存大小为" + temp);
//                                        } else {
//                                            cache.setText("当前缓存大小为" + "???");
//                                        }
//                                    }
//                                }.execute();
                            }
                        })
                        .show();
            }
        });
        layoutFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
        return view;
    }

}
