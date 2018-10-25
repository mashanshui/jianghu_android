package com.doumee.jianghu.ui.fragment;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.doumee.jianghu.R;
import com.doumee.jianghu.ui.activity.RegisterActivity;
import com.doumee.jianghu.ui.activity.ResetActivity;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FastLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FastLoginFragment extends XFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.login)
    QMUIRoundButton login;
    @BindView(R.id.edit_password)
    Button editPassword;
    @BindView(R.id.reg)
    Button reg;
    @BindView(R.id.user_agreement)
    QMUISpanTouchFixTextView userAgreement;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;


    public FastLoginFragment() {
        // Required empty public constructor
    }

    public static FastLoginFragment newInstance(String param1, String param2) {
        FastLoginFragment fragment = new FastLoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        login.setChangeAlphaWhenPress(true);
        userAgreement.setMovementMethodDefault();
        userAgreement.setText(generateSp2("登录注册代表您已阅读《江湖app隐私协议》"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fast_login;
    }

    @Override
    public Object newP() {
        return null;
    }

    @OnClick({R.id.send, R.id.login, R.id.edit_password, R.id.reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send:
                break;
            case R.id.login:
                break;
            case R.id.edit_password:
                Router.newIntent(context).to(ResetActivity.class).launch();
                break;
            case R.id.reg:
                Router.newIntent(context).to(RegisterActivity.class).launch();
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
