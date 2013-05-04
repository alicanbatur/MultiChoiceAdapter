/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.manuelpeinado.multichoiceadapter.demo.basicsample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.manuelpeinado.multichoiceadapter.MultiChoiceBaseAdapter;
import com.manuelpeinado.multichoiceadapter.demo.R;

public class BasicUsageAdapter extends MultiChoiceBaseAdapter {

    protected static final String TAG = BasicUsageAdapter.class.getSimpleName();
    private List<String> items;

    public BasicUsageAdapter(Bundle savedInstanceState, List<String> items) {
        super(savedInstanceState);
        this.items = items;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.my_action_mode, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.menu_discard) {
            discardSelectedItems();
            return true;
        }
        return false;
    }

    private void discardSelectedItems() {
        // http://stackoverflow.com/a/4950905/244576
        List<Long> positions = new ArrayList<Long>(getCheckedItems());
        Collections.sort(positions, Collections.reverseOrder());
        for (long position : positions) {
            items.remove((int)position);
        }
        finishActionMode();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    protected View getViewImpl(int position, View convertView, ViewGroup parent) {
        CheckedTextView textView;
        if (convertView != null) {
            textView = (CheckedTextView) convertView;
        } else {
            int layout = R.layout.mca__simple_list_item_checkable_1;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            textView = (CheckedTextView) inflater.inflate(layout, parent, false);
        }
        String item = getItem(position);
        textView.setText(item);
        return textView;
    }
}
