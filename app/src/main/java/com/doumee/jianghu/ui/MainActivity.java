package com.doumee.jianghu.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.doumee.jianghu.R;
import com.doumee.jianghu.adapter.ConversationListAdapterEx;
import com.doumee.jianghu.adapter.MainViewPageFragmentAdapter;
import com.doumee.jianghu.ui.fragment.CircleFragment;
import com.doumee.jianghu.ui.fragment.DiscoverFragment;
import com.doumee.jianghu.ui.fragment.MessageFragment;
import com.doumee.jianghu.ui.fragment.MineFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * @author mashanshui
 * @date 2018-10-19
 * @desc TODO
 */
public class MainActivity extends XActivity {

    @BindView(R.id.main_viewPage)
    QMUIViewPager mainViewPage;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;


    //BottomNavigationView的监听事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_message:
                    //点击菜单项时跳转ViewPage
                    mainViewPage.setCurrentItem(0);
                    return true;
                case R.id.menu_item_circle:
                    mainViewPage.setCurrentItem(1);
                    return true;
                case R.id.menu_item_discover:
                    mainViewPage.setCurrentItem(2);
                    return true;
                case R.id.menu_item_mine:
                    mainViewPage.setCurrentItem(3);
                    return true;
                default:
                    break;
            }
            return false;
        }

    };

    @Override
    public void initData(Bundle savedInstanceState) {
        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{ContextCompat.getColor(context, R.color.grey),
                ContextCompat.getColor(context, R.color.colorPrimary)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        bottomNavigationView.setItemTextColor(csl);
        bottomNavigationView.setItemIconTintList(csl);
        MainViewPageFragmentAdapter adapter = new MainViewPageFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(MessageFragment.newInstance("", ""));
        adapter.addFragment(CircleFragment.newInstance("", ""));
        adapter.addFragment(DiscoverFragment.newInstance("", ""));
        adapter.addFragment(MineFragment.newInstance("", ""));
        mainViewPage.setAdapter(adapter);
        mainViewPage.setOffscreenPageLimit(5);
        mainViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 将当前的页面对应的底部标签设为选中状态
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    @SuppressLint("RestrictedApi")
    public void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {  if(menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }
}
