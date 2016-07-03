package com.yuanke.alter_animation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AlterAnimation extends Activity {


    private CharSequence[] items = {"LOL", "DOTA", "CF", "DNF", "WOW"};
    private boolean[] checkedItems = {true, false, false, false, false};


    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_animation);
        adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, items);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                btn1();
                break;
            case R.id.btn2:
                btn2();
                break;
            case R.id.btn3:
                btn3();
                break;
            case R.id.btn4:
                btn4();
                break;
            case R.id.btn5:
                btn5();
                break;
            case R.id.btn6:
                btn6();
                break;
            case R.id.btn7:
                btn7();
                break;
        }
    }

    private void btn7() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //动态加载布局
        View view = getLayoutInflater().inflate(R.layout.layout, null);
        ImageView image = (ImageView) view.findViewById(R.id.iv);
        AnimationDrawable background = (AnimationDrawable) image.getBackground();
        background.stop();
        background.start();
        builder.setView(view);
        //显示
        builder.show();
    }

    private EditText etUsername, etPsw;

    /**
     * 登陆弹出框
     */
    private void btn6() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //添加图片
        builder.setIcon(R.mipmap.ic_launcher);
        //添加标题
        builder.setTitle("提示");
        //添加按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String name = etUsername.getText().toString();
                String psw = etPsw.getText().toString();
                Toast.makeText(getApplicationContext(), name + "   " + psw, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //动态加载布局
        View view = getLayoutInflater().inflate(R.layout.alertdialog_login, null);
        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPsw = (EditText) view.findViewById(R.id.et_psw);
        builder.setView(view);
        //显示
        builder.show();
    }

    /**
     * 带多选列表弹出框
     */
    private void btn5() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //添加图片
        builder.setIcon(R.mipmap.ic_launcher);
        //添加标题
        builder.setTitle("提示");
        //添加按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //设置适配器
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        //显示
        dialog.show();

    }

    /**
     * 带多选列表弹出框
     */
    private void btn4() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //添加图片
        builder.setIcon(R.mipmap.ic_launcher);
        //添加标题
        builder.setTitle("提示");
        //添加按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String msg = "";
                for (int i = 0; i < items.length; i++) {
                    if (checkedItems[i]) {
                        msg = msg + "," + items[i];
                    }
                }
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
        //设置单选列表
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            /**
             *
             * @param dialog  当前对象
             * @param which    下标
             * @param isChecked   是否选中
             */
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked;
            }
        });

        AlertDialog dialog = builder.create();
        //显示
        dialog.show();

    }

    /**
     * 带单选列表弹出框
     */
    private void btn3() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //添加图片
        builder.setIcon(R.mipmap.ic_launcher);
        //添加标题
        builder.setTitle("提示");
        //添加按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "点击确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setNeutralButton("中立", null);
        //设置单选列表
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            /**
             *
             * @param dialog
             * @param which 下标
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        //显示
        dialog.show();

    }

    /**
     * 带列表弹出框
     */
    private void btn2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //添加图片
        builder.setIcon(R.mipmap.ic_launcher);
        //添加标题
        builder.setTitle("提示");
        //添加按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "点击确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setNeutralButton("中立", null);
        //设置列表
        builder.setItems(items, new DialogInterface.OnClickListener() {
            /**dialog当前builder对象
             * @param
             * @param which  列表的下标
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "" + which, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        //显示
        dialog.show();
    }

    /**
     * 带中立，取消，确定的弹出框
     */
    private void btn1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //添加图片
        builder.setIcon(R.mipmap.ic_launcher);
        //添加标题
        builder.setTitle("提示");
        //添加内容
        builder.setMessage("亲，你真的要退出吗？");
        //设置点击屏幕的其它位置和返回键关闭Dialog
        builder.setCancelable(true);
        //添加按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "点击确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setNeutralButton("中立", null);

        AlertDialog dialog = builder.create();
        //显示
        dialog.show();
    }


}
