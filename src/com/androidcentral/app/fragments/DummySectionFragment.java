package com.androidcentral.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DummySectionFragment
  extends Fragment
{
  public static final String ARG_SECTION_TEXT = "section_text";
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903068, paramViewGroup, false);
    ((TextView)localView.findViewById(2131099716)).setText(getArguments().getString("section_text"));
    return localView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.DummySectionFragment
 * JD-Core Version:    0.7.0.1
 */