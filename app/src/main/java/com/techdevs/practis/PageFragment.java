package com.techdevs.practis;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import io.noties.markwon.Markwon;


public class PageFragment extends Fragment {

    private Page mPage;
    private EditText mTitle, mContent;
    CharacterStyle styleItalic;
    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mTitle=view.findViewById(R.id.pageTitleText);
        mContent=view.findViewById(R.id.pageContentText);

        //final Markwon markwon = Markwon.create(getActivity());
        //markwon.setMarkdown(mContent, "**Hello there**");
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

    }

    public void changeTextColor(){
        int selectionStart = mContent.getSelectionStart();
        int selectionEnd = mContent.getSelectionEnd();
        String selectedText = mContent.getText().toString().substring(selectionStart, selectionEnd);
        System.out.println(selectedText);

    }
    public void makeTextBold(){
        int selectionStart = mContent.getSelectionStart();
        int selectionEnd = mContent.getSelectionEnd();

        String startingText = mContent.getText().toString()
                .substring(0, selectionStart);
        String selectedText = mContent.getText().toString()
                .substring(selectionStart, selectionEnd);
        String endingText = mContent.getText().toString()
                .substring(selectionEnd);

        mContent.setText(Html.fromHtml(startingText + "<b>"
                + selectedText + "</b>" + endingText));
    }
    public void makeTextItalic(){
        String wholeText = mContent.getText().toString();
        styleItalic = new StyleSpan(Typeface.ITALIC);
        int start = mContent.getSelectionStart();
        int end =mContent.getSelectionEnd();

        SpannableStringBuilder sb = new SpannableStringBuilder(wholeText);

        sb.setSpan(styleItalic, start, end, 0);
        mContent.setText(sb);
    }
    public String getTitle(){
        return mTitle.getText().toString();
    }public String getContent(){
        return mContent.getText().toString();
    }
}