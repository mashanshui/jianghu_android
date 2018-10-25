package com.doumee.jianghu.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.allen.library.SuperTextView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.doumee.jianghu.JsonBean;
import com.doumee.jianghu.R;
import com.doumee.jianghu.present.PerfectInformationPresent;
import com.doumee.jianghu.util.GetJsonDataUtil;
import com.google.gson.Gson;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * @author mashanshui
 * @date 2018-10-19
 * @desc 完善资料
 */
public class PerfectInformationActivity extends XActivity<PerfectInformationPresent> implements SuperTextView.OnSuperTextViewClickListener {

    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.rg_identity)
    RadioGroup rgIdentity;
    @BindView(R.id.stv_select_old_address)
    SuperTextView stvSelectOldAddress;
    @BindView(R.id.stv_select_now_address)
    SuperTextView stvSelectNowAddress;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton btnConfirm;
    @BindView(R.id.btn_go_login)
    Button btnGoLogin;
    @BindView(R.id.user_agreement)
    QMUISpanTouchFixTextView userAgreement;

    private ThreadPoolExecutor threadPoolExecutor;

    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private ArrayList<JsonBean> options1Items;
    private ArrayList<ArrayList<String>> options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items;
    private boolean isLoaded = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(JsonDataActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
//                    Toast.makeText(JsonDataActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void initData(Bundle savedInstanceState) {
        btnConfirm.setChangeAlphaWhenPress(true);
        userAgreement.setMovementMethodDefault();
        userAgreement.setText(generateSp2("登录注册代表您已阅读《江湖app隐私协议》"));
        stvSelectNowAddress.setOnSuperTextViewClickListener(this);
        stvSelectOldAddress.setOnSuperTextViewClickListener(this);
        threadPoolExecutor = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        loadCityData();
    }

    private void loadCityData() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                getP().initJsonData();
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_perfect_information;
    }

    @Override
    public PerfectInformationPresent newP() {
        return new PerfectInformationPresent(context);
    }


    @Override
    public void onClickListener(SuperTextView superTextView) {
        switch (superTextView.getId()) {
            case R.id.stv_select_now_address:
                if (isLoaded) {
                    showPickerView();
                } else {
                    IToast.showShort("数据异常");
                }
                break;
            case R.id.stv_select_old_address:
                if (isLoaded) {
                    showPickerView();
                } else {
                    IToast.showShort("数据异常");
                }
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.btn_confirm, R.id.btn_go_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                break;
            case R.id.btn_go_login:
                break;
            default:
                break;
        }
    }

    private SpannableString generateSp2(String text) {
        String highlight1 = "江湖app隐私协议";
        SpannableString sp = new SpannableString(text);
        int start = 0, end;
        int index;
        while ((index = text.indexOf(highlight1, start)) > -1) {
            end = index + highlight1.length();
            sp.setSpan(new QMUITouchableSpan(ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.colorPrimary)
                    , Color.TRANSPARENT, Color.TRANSPARENT) {
                @Override
                public void onSpanClick(View widget) {
//                    Router.newIntent(context).to(RegisterAgreementActivity.class).launch();
                }
            }, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end;
        }
        return sp;
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
            }
        })

                .setTitleText("请选择区域")
                .setSubmitColor(ContextCompat.getColor(context, R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(context, R.color.grey))//取消按钮文字颜色
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    public void updateCityData(ArrayList<JsonBean> jsonBeans, ArrayList<ArrayList<String>> options2Items, ArrayList<ArrayList<ArrayList<String>>> options3Items) {
        options1Items = jsonBeans;
        this.options2Items = options2Items;
        this.options3Items = options3Items;
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public void updateCityDataFail() {
        mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        threadPoolExecutor.shutdown();
    }
}
