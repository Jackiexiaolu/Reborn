package com.demo.reborn.personalcenter.ui.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.reborn.R;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_Receive_Friends_Message;
import com.demo.reborn.data.json.Api1_Search_Users;
import com.demo.reborn.data.json.Api1_Send_Friends_Message;
import com.demo.reborn.personalcenter.ui.enity.MessageInfo;
import com.demo.reborn.personalcenter.ui.util.AudioRecorderUtils;
import com.demo.reborn.personalcenter.ui.util.Constants;
import com.demo.reborn.personalcenter.ui.util.PopupWindowFactory;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


/**
 * 作者：Rance on 2016/12/13 15:19
 * 邮箱：rance935@163.com
 * 输入框管理类
 */
public class EmotionInputDetector {
    private static final String TAG = "EmotionInputDetector";
    private static final String SHARE_PREFERENCE_NAME = "com.dss886.emotioninputdetector";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";

    private final FinancialDataRepository mData;
    private Activity mActivity;
    private InputMethodManager mInputManager;
    private SharedPreferences sp;
    private View mEmotionLayout;
    private EditText mEditText;
    private TextView mVoiceText;
    private View mContentView;
    private ViewPager mViewPager;
    private View mSendButton;
    private View mAddButton;
    private Boolean isShowEmotion = false;
    private Boolean isShowAdd = false;
    private boolean isShowVoice = false;
    private AudioRecorderUtils mAudioRecorderUtils;
    private PopupWindowFactory mVoicePop;
    private TextView mPopVoiceText;

    private EmotionInputDetector() {
        mData = FinancialDataRepository.getINSTANCE();
    }

    public static EmotionInputDetector with(Activity activity) {
        EmotionInputDetector emotionInputDetector = new EmotionInputDetector();
        emotionInputDetector.mActivity = activity;
        emotionInputDetector.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        emotionInputDetector.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return emotionInputDetector;
    }

    public EmotionInputDetector bindToContent(View contentView) {
        mContentView = contentView;
        return this;
    }

    public EmotionInputDetector bindToEditText(EditText editText) {
        mEditText = editText;
        mEditText.requestFocus();
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mEmotionLayout.isShown()) {
                    lockContentHeight();
                    hideEmotionLayout(true);

                    mEditText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unlockContentHeightDelayed();
                        }
                    }, 200L);
                }
                return false;
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mAddButton.setVisibility(View.GONE);
                    mSendButton.setVisibility(View.VISIBLE);
                } else {
                    mAddButton.setVisibility(View.VISIBLE);
                    mSendButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return this;
    }

    public EmotionInputDetector bindToEmotionButton(View emotionButton) {
        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) {
                    if (isShowAdd) {
                        mViewPager.setCurrentItem(0);
                        isShowEmotion = true;
                        isShowAdd = false;
                    } else {
                        lockContentHeight();
                        hideEmotionLayout(true);
                        isShowEmotion = false;
                        unlockContentHeightDelayed();
                    }
                } else {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showEmotionLayout();
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();
                    }
                    mViewPager.setCurrentItem(0);
                    isShowEmotion = true;
                }
            }
        });
        return this;
    }

    public EmotionInputDetector bindToAddButton(View addButton) {
        mAddButton = addButton;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) {
                    if (isShowEmotion) {
                        mViewPager.setCurrentItem(1);
                        isShowAdd = true;
                        isShowEmotion = false;
                    } else {
                        lockContentHeight();
                        hideEmotionLayout(true);
                        isShowAdd = false;
                        unlockContentHeightDelayed();
                    }
                } else {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showEmotionLayout();
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();
                    }
                    mViewPager.setCurrentItem(1);
                    isShowAdd = true;
                }
            }
        });
        return this;
    }

    public EmotionInputDetector bindToSendButton(View sendButton, final String  recId) {
        mSendButton = sendButton;
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddButton.setVisibility(View.VISIBLE);
                mSendButton.setVisibility(View.GONE);
                MessageInfo messageInfo = new MessageInfo();
                String data = mEditText.getText().toString();
                messageInfo.setContent(data);
                messageInfo.setHeader(Constants.HEADURL_LOCALSH);
                pushData(data,"05529bdeb6ab3ae8bba065abd3651168");

                messageInfo.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
                EventBus.getDefault().post(messageInfo);
                mEditText.setText("");
            }
        });
        return this;
    }


    /**
     * 发送消息
     * @param data
     * @param rec_id
     */
    public  void  pushData(String data,String  rec_id){

        //// TODO: 2019/4/26  发送信息
        mData.get_Api1_Send_friends_message(data,rec_id).
        subscribe(new Observer<Response<Api1_Send_Friends_Message>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_Send_Friends_Message> api1_post_common) {
                System.out.println(api1_post_common.body().info+"---"+api1_post_common.body().error);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }
    public  void  selectUser(String data){

        //// TODO: 2019/4/26  发送信息
        mData.get_Api1_Search_Users("金").
                subscribe(new Observer<Response<Api1_Search_Users>>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_Search_Users> api1_Api1_Search_Users) {
                        System.out.println(api1_Api1_Search_Users.body().info+"---"+api1_Api1_Search_Users.body().error);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 接受消息
     * @param send_id
     * @return
     */
    public Object pullData(String send_id){
        mData.get_Api1_Receive_friends_message(send_id).
        subscribe(new Observer<Response<Api1_Receive_Friends_Message>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_Receive_Friends_Message> api1_Receive_Friends_Message) {
                System.out.println(api1_Receive_Friends_Message.body().info+"---");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        return null;
    }




    public EmotionInputDetector bindToVoiceButton(final ImageView voiceButton) {
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowVoice) {
                    voiceButton.setImageResource(R.mipmap.icon_voice);
                } else {
                    voiceButton.setImageResource(R.mipmap.icon_keyboard);

                }

                isShowVoice = !isShowVoice;

                hideEmotionLayout(false);
                hideSoftInput();
                mVoiceText.setVisibility(mVoiceText.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                mEditText.setVisibility(mVoiceText.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        return this;
    }

    public EmotionInputDetector bindToVoiceText(TextView voiceText) {
        mVoiceText = voiceText;
//        mVoiceText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // 获得x轴坐标
//                int x = (int) event.getX();
//                // 获得y轴坐标
//                int y = (int) event.getY();
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        mVoicePop.showAtLocation(v, Gravity.CENTER, 0, 0);
//                        mVoiceText.setText("松开结束");
//                        mPopVoiceText.setText("手指上滑，取消发送");
//                        mVoiceText.setTag("1");
//                        mAudioRecorderUtils.startRecord(mActivity);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (wantToCancel(x, y)) {
//                            mVoiceText.setText("松开结束");
//                            mPopVoiceText.setText("松开手指，取消发送");
//                            mVoiceText.setTag("2");
//                        } else {
//                            mVoiceText.setText("松开结束");
//                            mPopVoiceText.setText("手指上滑，取消发送");
//                            mVoiceText.setTag("1");
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        mVoicePop.dismiss();
//                        if (mVoiceText.getTag().equals("2")) {
//                            //取消录音（删除录音文件）
//                            mAudioRecorderUtils.cancelRecord();
//                        } else {
//                            //结束录音（保存录音文件）
//                            mAudioRecorderUtils.stopRecord();
//                        }
//                        mVoiceText.setText("按住说话");
//                        mVoiceText.setTag("3");
//                        mVoiceText.setVisibility(View.GONE);
//                        mEditText.setVisibility(View.VISIBLE);
//                        break;
//                }
//                return true;
//            }
//        });
        return this;
    }

//    private boolean wantToCancel(int x, int y) {
//        // 超过按钮的宽度
//        if (x < 0 || x > mVoiceText.getWidth()) {
//            return true;
//        }
//        // 超过按钮的高度
//        if (y < -50 || y > mVoiceText.getHeight() + 50) {
//            return true;
//        }
//        return false;
//    }

    public EmotionInputDetector setEmotionView(View emotionView) {
        mEmotionLayout = emotionView;
        return this;
    }

    public EmotionInputDetector setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        return this;
    }

//    public EmotionInputDetector build() {
//        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        hideSoftInput();
//        mAudioRecorderUtils = new AudioRecorderUtils();
//
//        View view = View.inflate(mActivity, R.layout.layout_microphone, null);
//        mVoicePop = new PopupWindowFactory(mActivity, view);
//
//        //PopupWindow布局文件里面的控件
//        final ImageView mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
//        final TextView mTextView = (TextView) view.findViewById(R.id.tv_recording_time);
//        mPopVoiceText = (TextView) view.findViewById(R.id.tv_recording_text);
//        //录音回调
//        mAudioRecorderUtils.setOnAudioStatusUpdateListener(new AudioRecorderUtils.OnAudioStatusUpdateListener() {
//
//            //录音中....db为声音分贝，time为录音时长
//            @Override
//            public void onUpdate(double db, long time) {
//                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
//                mTextView.setText(Utils.long2String(time));
//            }
//
//            //录音结束，filePath为保存路径
//            @Override
//            public void onStop(long time, String filePath) {
//                mTextView.setText(Utils.long2String(0));
//                MessageInfo messageInfo = new MessageInfo();
//                messageInfo.setFileType(Constants.CHAT_FILE_TYPE_VOICE);
//                messageInfo.setFilepath(filePath);
//                messageInfo.setVoiceTime(time);
//                EventBus.getDefault().post(messageInfo);
//            }
//
//            @Override
//            public void onError() {
//                mVoiceText.setVisibility(View.GONE);
//                mEditText.setVisibility(View.VISIBLE);
//            }
//        });
//        return this;
//    }

    public boolean interceptBackPress() {
        if (mEmotionLayout.isShown()) {
            hideEmotionLayout(false);
            return true;
        }
        return false;
    }

    private void showEmotionLayout() {
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight == 0) {
            softInputHeight = sp.getInt(SHARE_PREFERENCE_TAG, 768);
        }
        hideSoftInput();
        Log.e(TAG, "showEmotionLayout: ->" + softInputHeight );
        mEmotionLayout.getLayoutParams().height = softInputHeight;
        mEmotionLayout.setVisibility(View.VISIBLE);
    }

    public void hideEmotionLayout(boolean showSoftInput) {
        if (mEmotionLayout.isShown()) {
            mEmotionLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    private void lockContentHeight() {
        Log.e(TAG, "lockContentHeight: ->" + mContentView.getHeight());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.height = mContentView.getHeight();
        params.weight = 0.0F;
    }

    private void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mContentView.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }

    private void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
    }

    public void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        if (softInputHeight < 0) {
            Log.w("EmotionInputDetector", "Warning: value of softInputHeight is below zero!");
        }
        if (softInputHeight > 0) {
            Log.e(TAG, "getSupportSoftInputHeight: ->" + softInputHeight );
            sp.edit().putInt(SHARE_PREFERENCE_TAG, softInputHeight).apply();
        }
        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

}
