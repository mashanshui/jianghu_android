package com.doumee.jianghu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.doumee.jianghu.R;
import com.doumee.jianghu.adapter.ViewTabPagerAdapter;
import com.doumee.jianghu.ui.fragment.AccountLoginFragment;
import com.doumee.jianghu.ui.fragment.FastLoginFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.mvp.XActivity;

public class LoginActivity extends XActivity {

    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    public void initData(Bundle savedInstanceState) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(AccountLoginFragment.newInstance("", ""));
        fragmentList.add(FastLoginFragment.newInstance("", ""));
        List<String> titleList = Arrays.asList("帐号登录", "快捷登录");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPage.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public Object newP() {
        return null;
    }
}
