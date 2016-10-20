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
import com.qmxy.kuaidu.bean.User;
import com.qmxy.kuaidu.utils.commontool.HttpUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
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
    private ProgressDialog progressDialog;
    private String openId;

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
        ShareSDK.initSDK(getContext());
        final User user = new User();
        final Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.removeAccount(true);
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
        circle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getInt("flag", 1) == 1) {
                    Toast.makeText(getContext(), "你已经登录！", Toast.LENGTH_SHORT).show();
                    return;
                }
                qq.SSOSetting(false);//设置false表示使用SSO授权方式
                qq.showUser(null);//请求数据
                //qq.authorize();
            }
        });
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                user.setUserName((String) hashMap.get("nickname"));
                user.setImage((String) hashMap.get("figureurl_qq_2"));
                editor.putInt("flag", 1);
                //提交当前数据
                editor.commit();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        yhText.setText(user.getUserName());
                        Picasso.with(getContext()).load(user.getImage()).into(circle_image);
                        Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "登录失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancel(Platform platform, int i) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "登录取消！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
                //移除授权
                qq.removeAccount(true);
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
                                                    circle_image.setImageResource(R.drawable.me_miter_default_image_night);
                                                }
                                            });
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
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
