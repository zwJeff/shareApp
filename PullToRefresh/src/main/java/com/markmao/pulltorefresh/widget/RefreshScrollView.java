package com.markmao.pulltorefresh.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;


public class RefreshScrollView extends ScrollView {

	private final static int SCROLL_DURATION = 400;
	private final static float OFFSET_RADIO = 1.8f;
	private int headerHeight = 0;
	private boolean enableRefresh = true;
	private boolean refreshing = false;
	private int lastY;
	private static Scroller scroller = null;
	private OnRefreshScrollViewListener listener = null;
	private LinearLayout scrollContainer = null;
	private ScrollViewHeader headerView = null;

	public RefreshScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		if (!isInEditMode()) {
			initView(context);
		}
	}

	public RefreshScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		if (!isInEditMode()) {
			initView(context);
		}
	}

	public RefreshScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		if (!isInEditMode()) {
			initView(context);
		}
	}

	/**
	 * 初始化view
	 */
	private void initView(Context context) {
		scroller = new Scroller(context);
		headerView = new ScrollViewHeader(context);
		LinearLayout.LayoutParams headerViewParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		//scrollview只允许嵌套一个子布局
		scrollContainer = new LinearLayout(context);
		scrollContainer.addView(headerView, headerViewParams);
		scrollContainer.setOrientation(LinearLayout.VERTICAL);
		addView(scrollContainer);
		//提前获取headerView的高度
		headerView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						headerHeight = headerView.getHeight();
						headerView.updateMargin(-headerHeight);
						headerView.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
	}

	/**
	 * 设置内容区域
	 * 
	 * @param context
	 */
	public void setupContainer(Context context, View containerView) {
		scrollContainer.addView(containerView);
	}

	/**
	 * 设置scroll是否可以刷新
	 * 
	 * @param enableRefresh
	 */
	public void setEnableRefresh(boolean enableRefresh) {
		this.enableRefresh = enableRefresh;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int deltY = (int) (ev.getY() - lastY);
			lastY = (int) ev.getY();
			if (getScrollY() == 0
					&& (deltY > 0 || headerView.getTopMargin() > -headerHeight)) {
				updateHeader(deltY/OFFSET_RADIO);
				return true;
			} 
			break;
		default:
			//这里没有使用action_up的原因是，可能会受到viewpager的影响接收到action_cacel事件
			if (getScrollY() == 0) {
				if (headerView.getTopMargin() > 0 && enableRefresh && !refreshing) {
					refreshing = true;
					headerView.setState(ScrollViewHeader.STATE_REFRESHING);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(listener != null) {
								listener.onRefresh();
								refreshing = false;
								Toast.makeText(getContext(),"刷新成功！",Toast.LENGTH_SHORT).show();
								resetHeaderView();
							}
						}
					}, 3000);
				}
				resetHeaderView();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 更新headerview的高度,同时更改状态
	 * 
	 * @param deltY
	 */
	public void updateHeader(float deltY) {
		int currentMargin = (int) (headerView.getTopMargin() + deltY);
		headerView.updateMargin(currentMargin);
		if(enableRefresh && !refreshing) {
			if (currentMargin > 0) {
				headerView.setState(ScrollViewHeader.STATE_READY);
			} else {
				headerView.setState(ScrollViewHeader.STATE_NORMAL);
			}
		}
	}

	/**
	 * 重置headerview的高度
	 */
	public void resetHeaderView() {
		int margin = headerView.getTopMargin();
		if(margin == -headerHeight) {
			return ;
		}
		if(margin < 0 && refreshing) {
			//当前已经在刷新，又重新进行拖动,但未拖满,不进行操作
			return ;
		}
		int finalMargin = 0;
		if(margin <= 0 && !refreshing) {
			finalMargin = headerHeight;
		}
		//松开刷新，或者下拉刷新，又松手，没有触发刷新
		scroller.startScroll(0, -margin, 0, finalMargin + margin, SCROLL_DURATION);
		
		invalidate();
	}
	
	/**
	 * 开始刷新
	 */
	public void startRefresh() {
		refreshing = true;
		headerView.setState(ScrollViewHeader.STATE_REFRESHING);
		if(listener != null) {
			scroller.startScroll(0, 0, 0, headerHeight, SCROLL_DURATION);
			invalidate();
			listener.onRefresh();
		}
	}
	
	/**
	 * 停止刷新
	 */
	public void stopRefresh() {
		if(refreshing) {
			refreshing = false;
			resetHeaderView();
		}
	}
	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if(scroller.computeScrollOffset()) {
			headerView.updateMargin(-scroller.getCurrY());
			//继续重绘
			postInvalidate();
 		}
		super.computeScroll();
	}
	
	public void setOnRefreshScrollViewListener(OnRefreshScrollViewListener listener) {
		this.listener = listener;
	}
	
	public interface OnRefreshScrollViewListener {
		public void onRefresh();
	}
}