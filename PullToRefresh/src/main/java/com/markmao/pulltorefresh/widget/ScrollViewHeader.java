package com.markmao.pulltorefresh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.markmao.pulltorefresh.R;

public class ScrollViewHeader extends RelativeLayout {

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	private final int ROTATE_ANIM_DURATION = 180;
	private int topMargin = 0;
	private int state = STATE_NORMAL;
	private TextView refreshTv = null;
	private TextView refreshTimeTv;
	private ProgressBar refreshProgress = null;
	private ImageView refreshArrow = null;
	private Animation animationUp = null;
	private Animation animationDown = null;
	
	public ScrollViewHeader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		if(!isInEditMode()) 
			initView(context);
		refreshTimeTv = null;
	}

	public ScrollViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		if(!isInEditMode())
			initView(context);
		refreshTimeTv = null;
	}

	public ScrollViewHeader(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		if(!isInEditMode())
			initView(context);
		refreshTimeTv = null;
	}

	/**
	 * 初始化相关的view
	 */
	public void initView(Context context) {
		animationDown = new RotateAnimation(-180f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animationDown.setDuration(ROTATE_ANIM_DURATION);
		animationDown.setFillAfter(true);
		animationUp = new RotateAnimation(0, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animationUp.setDuration(ROTATE_ANIM_DURATION);
		animationUp.setFillAfter(true);
		setPadding(10, 25, 10, 25);
		View view = LayoutInflater.from(context).inflate(R.layout.header_layout, this, true);
		refreshTv = (TextView) view.findViewById(R.id.refresh_text);
		refreshTimeTv = (TextView) view.findViewById(R.id.refresh_time);
		refreshProgress = (ProgressBar) view.findViewById(R.id.refresh_progress);
		refreshArrow = (ImageView) view.findViewById(R.id.refresh_arrow);
	}
	
	/**
	 * 设置scrollviewHeader的状态
	 * @param state
	 */
	public void setState(int state) {
		if(this.state == state) {
			return ;
		}
		switch (state) {
		case STATE_NORMAL:
			refreshTv.setText("下拉刷新");
			refreshArrow.setVisibility(View.VISIBLE);
			refreshProgress.setVisibility(View.INVISIBLE);
			if(this.state == STATE_READY) {
				refreshArrow.startAnimation(animationDown);
			} else if(this.state == STATE_REFRESHING) {
				refreshArrow.clearAnimation();
			}
			break;
		case STATE_READY:
			refreshTv.setText("松开刷新");
			refreshArrow.setVisibility(View.VISIBLE);
			refreshProgress.setVisibility(View.INVISIBLE);
			refreshArrow.startAnimation(animationUp);
			break;
		case STATE_REFRESHING:
			refreshTv.setText("正在加载...");
			refreshProgress.setVisibility(View.VISIBLE);
			refreshArrow.clearAnimation();
			refreshArrow.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
		this.state = state;
	}
	
	/**
	 * 更新header的margin
	 * @param margin
	 */
	public void updateMargin(int margin) {
		//这里用Linearlayout的原因是Headerview的父控件是scrollcontainer是一个linearlayout 
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.getLayoutParams();
		params.topMargin = margin;
		topMargin = margin;
		setLayoutParams(params);
	}
	
	/**
	 * 获取header的margin
	 * @return
	 */
	public int getTopMargin() {
		return topMargin;
	}
}