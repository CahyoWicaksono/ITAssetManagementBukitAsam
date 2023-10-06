package com.example.itassetmanagementptbukitasam.Notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.itassetmanagementptbukitasam.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TopicSendFragment extends Fragment {
    private EditText mTopic, mTitle, mBody;
    private Button mSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic_send, container, false);

        mTopic = rootView.findViewById(R.id.mTopic);
        mTitle = rootView.findViewById(R.id.mTitle);
        mBody = rootView.findViewById(R.id.mBody);
        mSend = rootView.findViewById(R.id.mSend);

        mSend.setOnClickListener(view -> {
            String topic = mTopic.getText().toString().trim();
            String title = mTitle.getText().toString().trim();
            String body = mBody.getText().toString().trim();
            if (!topic.equals("") && !title.equals("") && !body.equals("")) {
                FCMSend.Builder build = new FCMSend.Builder(topic, true)
                        .setTitle(title)
                        .setBody(body);
                String result = build.send().Result();
                Toast.makeText(getActivity(), ChekSuccess(result), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private String ChekSuccess(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String success = object.getString("success");
            if (success.equals("1")) {
                return "Success";
            } else if (success.equals("0")) {
                return "Unsuccessful";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Unsuccessful";
    }
}