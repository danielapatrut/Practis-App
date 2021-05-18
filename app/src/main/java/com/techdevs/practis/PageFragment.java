package com.techdevs.practis;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;

import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import jp.wasabeef.richeditor.RichEditor;


public class PageFragment extends Fragment {

    private Page mPage;
    private EditText mTitle, mContent;
    public RichEditor editor;

    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mTitle=view.findViewById(R.id.pageTitleText);
        //mContent=view.findViewById(R.id.pageContentText);
        editor = view.findViewById(R.id.editor);
        editor.setPlaceholder("Start typing...");
        editor.setEditorFontSize(24);

        //dynamically change page title from activity
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((NewPageActivity)getActivity()).setPageTitle(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        if(((NewPageActivity)getActivity()).isFromList()) {
            if (!mPage.getTitle().equals("")) {
                mTitle.setText(mPage.getTitle());
            }
            if (!mPage.getContent().equals("")) {
                //mContent.setText(mPage.getContent());
                editor.setHtml(mPage.getContent());
            }
        }

    }

    public String getTitle(){
        return mTitle.getText().toString();
    }
    public String getContent(){
        return editor.getHtml();
    }

    public void setmTitle(String mTitle) {
        this.mTitle.setText(mTitle);
    }

    public void setmPage(Page mPage) {
        this.mPage = mPage;
    }
}