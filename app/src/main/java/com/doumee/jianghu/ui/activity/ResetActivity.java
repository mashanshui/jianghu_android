package com.doumee.jianghu.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doumee.jianghu.R;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018-10-19
 * @desc 重置密码
 */
public class ResetActivity extends XActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.reply_password)
    EditText replyPassword;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton btnConfirm;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.log)
    Button log;
    @BindView(R.id.user_agreement)
    QMUISpanTouchFixTextView userAgreement;

    @Override
    public void initData(Bundle savedInstanceState) {
        btnConfirm.setChangeAlphaWhenPress(true);
        userAgreement.setMovementMethodDefault();
        userAgreement.setText(generateSp2("登录注册代表您已阅读《江湖app隐私协议》"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.send, R.id.btn_confirm, R.id.register, R.id.log})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send:
                break;
            case R.id.btn_confirm:
                break;
            case R.id.register:
                Router.newIntent(context).to(RegisterActivity.class).launch();
                break;
            case R.id.log:
                Router.newIntent(context).to(LoginActivity.class).launch();
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
}
