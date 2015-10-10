package com.wallspeed.fragments;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallspeed.R;
import com.wallspeed.global.FragmentData;

/**
 * Created by ThoLH on 9/19/15.
 */
public class ExitFragment extends BaseFragment {

    @Override
    public View onCreateView(Context context, ViewGroup container) {
        return LayoutInflater.from(context).inflate(R.layout.exit_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        view.findViewById(R.id.btnNextFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getBaseActivity().presentFragment(BaseActivity.FragmentType.PROFILE, null, -1, BaseActivity.TRANSLATION);
                getActivity().getFragmentManagerLayout().showFragment(FragmentData.FragmentType.PROFILE, null, 0, false, false);
            }
        });
    }

    @Override
    public void onSetupActionBar() {
        super.onSetupActionBar();
        ActionBar actionBar = getActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ExitFragment");
        }
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            setResult(Activity.RESULT_OK, new Intent());
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
