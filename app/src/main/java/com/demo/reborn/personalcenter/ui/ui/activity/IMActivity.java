package com.demo.reborn.personalcenter.ui.ui.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.R;
import com.demo.reborn.personalcenter.ui.adapter.ChatAdapter;
import com.demo.reborn.personalcenter.ui.adapter.CommonFragmentPagerAdapter;
import com.demo.reborn.personalcenter.ui.enity.FullImageInfo;
import com.demo.reborn.personalcenter.ui.enity.MessageInfo;
import com.demo.reborn.personalcenter.ui.ui.fragment.ChatFunctionFragment;
import com.demo.reborn.personalcenter.ui.util.Constants;
import com.demo.reborn.personalcenter.ui.util.GlobalOnItemClickManagerUtils;
import com.demo.reborn.personalcenter.ui.util.MessageCenter;
import com.demo.reborn.personalcenter.ui.widget.ChatContextMenu;
import com.demo.reborn.personalcenter.ui.widget.EmotionInputDetector;
import com.demo.reborn.personalcenter.ui.widget.NoScrollViewPager;
import com.demo.reborn.personalcenter.ui.widget.StateButton;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class IMActivity extends AppCompatActivity {
    private static final String TAG = "IMActivity";
    RecyclerView chatList;
    ImageView emotionVoice;
    EditText editText;
    TextView voiceText;
    ImageView emotionButton;
    ImageView emotionAdd;
    StateButton emotionSend;
    NoScrollViewPager viewpager;
    public String  rec_id;
    RelativeLayout emotionLayout;

    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
 //   private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;

    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private List<MessageInfo> messageInfos;
    //录音相关
    int animationRes = 0;
    int res = 0;
    AnimationDrawable animationDrawable = null;
    private ImageView animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        rec_id = id;
        String real_name = intent.getStringExtra("real_name");
        Toast.makeText(this,rec_id+"===>"+real_name,Toast.LENGTH_SHORT).show();
        findViewByIds();
        EventBus.getDefault().register(this);
        initWidget();
        handleIncomeAction();
    }

    private void findViewByIds() {
        chatList = (RecyclerView) findViewById(R.id.chat_list);
        emotionVoice = (ImageView) findViewById(R.id.emotion_voice);
        editText = (EditText) findViewById(R.id.edit_text);
        voiceText = (TextView) findViewById(R.id.voice_text);
        emotionButton = (ImageView) findViewById(R.id.emotion_button);
        emotionAdd = (ImageView) findViewById(R.id.emotion_add);
        emotionSend = (StateButton) findViewById(R.id.emotion_send);
        emotionLayout = (RelativeLayout) findViewById(R.id.emotion_layout);
        viewpager = (NoScrollViewPager) findViewById(R.id.viewpager);
    }

    private void handleIncomeAction() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        MessageCenter.handleIncoming(bundle, getIntent().getType(), this);
    }

    private void initWidget() {
        fragments = new ArrayList<>();
//        chatEmotionFragment = new ChatEmotionFragment();
//        fragments.add(chatEmotionFragment);
//        chatFunctionFragment = new ChatFunctionFragment();
//        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        mDetector = EmotionInputDetector.with(this)
                 .setEmotionView(emotionLayout)
                  .setViewPager(viewpager)

                   .bindToContent(chatList)
                   .bindToEditText(editText)
                  .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend,rec_id)
                .bindToVoiceButton(emotionVoice)
                  .bindToVoiceText(voiceText)
//                .build();
                   ;
        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);

        chatAdapter = new ChatAdapter(messageInfos);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatList.setLayoutManager(layoutManager);
        chatList.setAdapter(chatAdapter);
        chatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        chatAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        chatAdapter.addItemClickListener(itemClickListener);
        LoadData();
    }

    /**
     * item点击事件
     */
    private ChatAdapter.onItemClickListener itemClickListener = new ChatAdapter.onItemClickListener() {
        @Override
        public void onHeaderClick(int position) {
            Toast.makeText(IMActivity.this, "onHeaderClick", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onImageClick(View view, int position) {
            int location[] = new int[2];
            view.getLocationOnScreen(location);
            FullImageInfo fullImageInfo = new FullImageInfo();
            fullImageInfo.setLocationX(location[0]);
            fullImageInfo.setLocationY(location[1]);
            fullImageInfo.setWidth(view.getWidth());
            fullImageInfo.setHeight(view.getHeight());
            fullImageInfo.setImageUrl(messageInfos.get(position).getFilepath());
            EventBus.getDefault().postSticky(fullImageInfo);
            startActivity(new Intent(IMActivity.this, FullImageActivity.class));
            overridePendingTransition(0, 0);
        }

        @Override
        public void onVoiceClick(final ImageView imageView, final int position) {
//            if (animView != null) {
//                animView.setImageResource(res);
//                animView = null;
//            }
//            switch (messageInfos.get(position).getType()) {
//                case 1:
//                    animationRes = R.drawable.voice_left;
//                    res = R.mipmap.icon_voice_left3;
//                    break;
//                case 2:
//                    animationRes = R.drawable.voice_right;
//                    res = R.mipmap.icon_voice_right3;
//                    break;
//            }
//            animView = imageView;
//            animView.setImageResource(animationRes);
//            animationDrawable = (AnimationDrawable) imageView.getDrawable();
//            animationDrawable.start();
//            MediaManager.playSound(messageInfos.get(position).getFilepath(), new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    animView.setImageResource(res);
//                }
//            });
        }

        @Override
        public void onFileClick(View view, int position) {

//            MessageInfo messageInfo = messageInfos.get(position);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            File file = new File(messageInfo.getFilepath());
//            Uri fileUri = FileProvider.getUriForFile(IMActivity.this, Constants.AUTHORITY, file);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }
//            intent.setDataAndType(fileUri, messageInfo.getMimeType());
//            startActivity(intent);
        }

        @Override
        public void onLinkClick(View view, int position) {
//            MessageInfo messageInfo = messageInfos.get(position);
//            Link link = (Link) messageInfo.getObject();
//            Uri uri = Uri.parse(link.getUrl());
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
        }

        @Override
        public void onLongClickImage(View view, int position) {

            ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),messageInfos.get(position));
//            chatContextMenu.setAnimationStyle();
            chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                    RelativePopupWindow.HorizontalPosition.CENTER);

        }

        @Override
        public void onLongClickText(View view, int position) {
            ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),messageInfos.get(position));
            chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                    RelativePopupWindow.HorizontalPosition.CENTER);
        }

        @Override
        public void onLongClickItem(View view, int position) {
            ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),messageInfos.get(position));
            chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                    RelativePopupWindow.HorizontalPosition.CENTER);
        }

        @Override
        public void onLongClickFile(View view, int position) {
            ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),messageInfos.get(position));
            chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                    RelativePopupWindow.HorizontalPosition.CENTER);
        }

        @Override
        public void onLongClickLink(View view, int position) {
            ChatContextMenu chatContextMenu = new ChatContextMenu(view.getContext(),messageInfos.get(position));
            chatContextMenu.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE,
                    RelativePopupWindow.HorizontalPosition.CENTER);
        }
    };

    /**
     * 构造聊天数据
     */
    private void LoadData() {
        messageInfos = new ArrayList<>();

        MessageInfo messageInfo1 = new MessageInfo();
        messageInfo1.setFilepath("http://www.trueme.net/bb_midi/welcome.wav");
        messageInfo1.setVoiceTime(3000);
        messageInfo1.setFileType(Constants.CHAT_FILE_TYPE_VOICE);
        messageInfo1.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo1.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        messageInfo1.setHeader(Constants.HEADURL_LOCALSH);
        messageInfos.add(messageInfo1);



        chatAdapter.addAll(messageInfos);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        messageInfo.setHeader(Constants.HEADURL_LOCALSH);
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
        messageInfos.add(messageInfo);
        chatAdapter.notifyItemInserted(messageInfos.size() - 1);
//        chatAdapter.add(messageInfo);
        chatList.scrollToPosition(chatAdapter.getItemCount() - 1);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                chatAdapter.notifyDataSetChanged();
            }
        }, 2000);

        Handler handler=new Handler();
        Runnable runnable=new Runnable(){
            @Override
            public void run() {
                MessageInfo message = new MessageInfo();
                // TODO Auto-generated method stub
                //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
                message.setContent("这是模拟消息回复");
                message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                message.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
                message.setHeader("http://img0.imgtn.bdimg.com/it/u=401967138,750679164&fm=26&gp=0.jpg");
                messageInfos.add(message);
                chatAdapter.notifyItemInserted(messageInfos.size() - 1);
                chatList.scrollToPosition(chatAdapter.getItemCount() - 1);
                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(runnable,2000);
//         new Handler().postDelayed(new Runnable() {
//            public void run() {
//                MessageInfo message = new MessageInfo();
//
//               //// TODO: 2019/4/27
//                //像后台发送请求
//                message.setContent("这是模拟消息回复");
//                message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
//                message.setFileType(Constants.CHAT_FILE_TYPE_TEXT);
//                message.setHeader("http://img0.imgtn.bdimg.com/it/u=401967138,750679164&fm=26&gp=0.jpg");
//                messageInfos.add(message);
//                chatAdapter.notifyItemInserted(messageInfos.size() - 1);
//                chatList.scrollToPosition(chatAdapter.getItemCount() - 1);
//            }
//        }, 3000);



    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(this);
        EventBus.getDefault().unregister(this);
    }
}
