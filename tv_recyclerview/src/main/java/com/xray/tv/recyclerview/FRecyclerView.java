//package com.xray.tv.recyclerview;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Point;
//import android.graphics.Rect;
//import android.os.Build;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//
//import java.util.ArrayList;
//
///**
// * @author cfp
// * @version 1.0
// * @title
// * @description
// * @company 北京奔流网络信息技术有线公司
// * @created 2018/11/08
// * @changeRecord [修改记录] <br/>
// */
//public class FRecyclerView extends RecyclerView {
//
//
//    private static final String TAG = "FRecycleView";
//
//    protected int mFocusChildPosition = 0;
//
//    protected PresenterSelector mPresenterSelector;
//
//    private ObjectAdapter mObjectAdapter;
//
//    private ItemBridgeAdapter mBridgeAdapter;
//
//    private ShadowOverlayHelper mShadowOverlayHelper;
//
//    private ItemBridgeAdapter.Wrapper mShadowOverlayWrapper;
//
//
//    OnAfterFocusSearchFailedListener mOnFocusSearchFailedListener;
//
//    protected View mLastFocusedChild;
//
//    private FRootView mFRootView;
//
//    private AttachInfo mAttachInfo = new AttachInfo();
//
//    protected View mFocusedView;
//
//    protected boolean mShakeEndEnable;
//
//
//    protected Animator mShakeEndAnimator;
//
//    protected boolean getDefaultShakeEndEnable() {
//        return false;
//    }
//
//    public static boolean DEBUG = FConfig.DEBUG;
//
//    Rect mClipMargin;
//
//    public FRecyclerView(Context context) {
//        super(context);
//        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
//        setHasFixedSize(true);
//    }
//
//    public FRecyclerView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        onInitializeFromAttributes(attrs);
//        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
//        setHasFixedSize(true);
//    }
//
//    public FRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        onInitializeFromAttributes(attrs);
//        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
//        setHasFixedSize(true);
//
//    }
//
//
//    /**
//     * 设置当焦点寻找失败时的逻辑处理
//     *
//     * @param onFocusSearchFailedListener
//     */
//    public void setOnAfterFocusSearchFailedListener(OnAfterFocusSearchFailedListener onFocusSearchFailedListener) {
//        mOnFocusSearchFailedListener = onFocusSearchFailedListener;
//    }
//
//
//    public interface OnAfterFocusSearchFailedListener {
//        /***
//         * 当焦点寻找失败时的逻辑处理
//         * @param focused 当前焦点
//         * @param direction 方向
//         * @return 如果return null,则继续父类的逻辑
//         */
//        View onAfterFocusSearchFailed(View focused, int direction);
//    }
//
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//    }
//
//
//    @Override
//    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
//        onHandleFocusScale(gainFocus, direction, previouslyFocusedRect);
//        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
//        if (isFocused()) {
//            dispatchFocusToChild(direction, previouslyFocusedRect);
//        }
//    }
//
//
//    /**
//     * 在RecycleView获得焦点后，将焦点给予子view
//     *
//     * @param direction
//     * @param previouslyFocusedRect
//     */
//    protected boolean dispatchFocusToChild(int direction, Rect previouslyFocusedRect) {
//        return false;
//    }
//
//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//    }
//
//
//    @Override
//    public void onDraw(Canvas c) {
//        super.onDraw(c);
//    }
//
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//        resetClipBounds();
//    }
//
//    @Override
//    protected void onMeasure(int widthSpec, int heightSpec) {
//        super.onMeasure(widthSpec, heightSpec);
//    }
//
//
//    private void resetClipBounds() {
//        if (getWidth() > 0 && getHeight() > 0) {
//            final Rect clipMargin = getClipMarginRect();
//            if (clipMargin.left != 0 || clipMargin.right != 0 || clipMargin.top != 0 || clipMargin.bottom != 0) {
//                Rect b = new Rect(clipMargin.left, clipMargin.top, clipMargin.right + getWidth(), clipMargin.bottom + getHeight());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                    setClipBounds(b);
//                }
//            }
//        }
//
//    }
//
//    /**
//     * view初始化后调用
//     *
//     * @param attrs
//     */
//    protected void onInitializeFromAttributes(AttributeSet attrs) {
//    }
//
//
//    public void setClipMargin(int left, int top, int right, int bottom) {
//        setClipMargin(new Rect(left * -1, top * -1, right, bottom));
//        resetClipBounds();
//    }
//
//    void setClipMargin(final Rect rect) {
//        getClipMarginRect().set(rect);
//    }
//
//
//    public Rect getClipMarginRect() {
//        if (null == mClipMargin) {
//            mClipMargin = new Rect();
//        }
//        return mClipMargin;
//    }
//
//
//    @Override
//    public void requestChildFocus(View child, View focused) {
//        //20160606 这里只要有焦点变化就刷新，因为如果不刷新界面，会导致子view放大时会被遮盖的问题
//        invalidate();
//        mFocusedView = focused;
//        mFocusChildPosition = getChildAdapterPosition(child);
//        super.requestChildFocus(child, focused);
//
//        int selection = getChildLayoutPosition(focused);
//
//    }
//
//    public int getSelectPosition() {
//        return mFocusChildPosition;
//    }
//
//    public int getFocusChildPosition() {
//        return mFocusChildPosition;
//    }
//
//
//    @Override
//    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
//        if (isFocusable()) {
//            boolean handle = dispatchFocusToChild(direction, previouslyFocusedRect);
//            if (handle) {
//                return true;
//            }
//        }
//        return super.requestFocus(direction, previouslyFocusedRect);
//    }
//
//    @Override
//    public void draw(Canvas c) {
//        super.draw(c);
//    }
//
//    public boolean isDispatchDrawOrder() {
//        return isDispatchDrawOrder;
//    }
//
//    /**
//     * 设置是否允许FrecycleView自己处理draw order.如果不处理，有可能出现子view放大时，被遮挡的问题
//     *
//     * @param isDispatchDrawOrder
//     */
//    public void setDispatchDrawOrder(boolean isDispatchDrawOrder) {
//        this.isDispatchDrawOrder = isDispatchDrawOrder;
//        invalidate();
//    }
//
//    public boolean isDispatchKeyEvent() {
//        return isDispatchKeyEvent;
//    }
//
//    /**
//     * 设置是否允许FrecycleView自己处理key事件.
//     *
//     * @param isDispatchKeyEvent
//     */
//    public void setDispatchKeyEvent(boolean isDispatchKeyEvent) {
//        this.isDispatchKeyEvent = isDispatchKeyEvent;
//    }
//
//
//    @Override
//    public void scrollToPosition(int position) {
//        super.scrollToPosition(position);
//    }
//
//
//
//    @Override
//    public View focusSearch(int direction) {
//        View v = super.focusSearch(direction);
//        return v;
//    }
//
//    @Override
//    public View focusSearch(View focused, int direction) {
//        View v = super.focusSearch(focused, direction);
//
//        if (v == null && mOnFocusSearchFailedListener != null) {
//            return mOnFocusSearchFailedListener.onAfterFocusSearchFailed(focused, direction);
//        }
//
//        if (isShakeWhenReachListEnd(focused, v, direction)) {
//            shakeRecycleView();
//        }
//        return v;
//    }
//
//    public void setShakeEndEnable(boolean shakeEndEnable) {
//        mShakeEndEnable = shakeEndEnable;
//    }
//
//
//    public ShakeEndCallback findIFLayoutManager() {
//        final LayoutManager layoutManager = getLayoutManager();
//        if (layoutManager instanceof ShakeEndCallback)
//            return (ShakeEndCallback) layoutManager;
//        return null;
//    }
//
//    protected FOrientation getShakeRecycleViewOrientation(ShakeEndCallback layoutManager) {
//        if (layoutManager != null)
//            return layoutManager.getFOrientation();
//        return FOrientation.VERTICAL;
//    }
//
//    public void shakeRecycleView() {
//
//        final ShakeEndCallback layoutManager = findIFLayoutManager();
//
//        final FOrientation orientation = getShakeRecycleViewOrientation(layoutManager);
//
//        if (Companion.getDEBUG()) {
//            Log.v(TAG, "shakeRecycleView orientation is " + orientation + " layoutManager is " + layoutManager);
//        }
//
//        if (mShakeEndAnimator == null) {
//            final Animator shake = AnimationStore.defaultShakeEndAnimator(this, orientation);
//            mShakeEndAnimator = shake;
//            if (null != shakeEndListenner) {
//                shakeEndListenner.onShake(orientation);
//            }
//            shake.start();
////            setLayoutFrozen(true);
//            shake.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
////                    setLayoutFrozen(false);
//                    mShakeEndAnimator = null;
//                }
//            });
//
//            FSoundEffect.playCantMove();
//        }
//
//
//    }
//
//    /**
//     * 判断是否滚动到尽头执行抖动效果
//     * @param focused
//     * @param focusSearched
//     * @param direction
//     * @return
//     */
//    protected boolean isShakeWhenReachListEnd(View focused, View focusSearched, int direction) {
//
//        ShakeEndCallback ifLayoutManager = findIFLayoutManager();
//        return mShakeEndEnable && ifLayoutManager != null && ifLayoutManager.isReachListEnd(this, focused, focusSearched, mFocusChildPosition, direction);
//    }
//
//
//    @Override
//    public void addFocusables(ArrayList<View> views, int direction) {
//        super.addFocusables(views, direction);
//    }
//
//    /**
//     * 为了使用{@link #setObjectAdatper(ObjectAdapter)}可以选择设置PresenterSelector
//     *
//     * @param presenterSelector
//     */
//    public void setPresenterSelector(PresenterSelector presenterSelector) {
//        mPresenterSelector = presenterSelector;
//    }
//
//    /**
//     * 设置一个无关位置（position）的ObjectAdapter
//     *
//     * @param adatper
//     */
//    public void setObjectAdatper(ObjectAdapter adatper) {
//        this.mObjectAdapter = adatper;
//        updateAdapter();
//    }
//
//
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        if (Companion.getDEBUG() && isDispatchDrawOrder) {
//            Log.v(TAG, "dispatchDraw focused is " + getFocusedChild());
//        }
//        super.dispatchDraw(canvas);
//        if (isDispatchDrawOrder && getFocusedChild() != null)
//            super.drawChild(canvas, getFocusedChild(), getDrawingTime());
//    }
//
//    @Override
//    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
//        if (isDispatchDrawOrder && child == getFocusedChild())
//            return true;
//        return super.drawChild(canvas, child, drawingTime);
//    }
//
//    public void notifyObjectAdapterChanged(ObjectAdapter adapter) {
//        if (mBridgeAdapter != null) {
//            mBridgeAdapter.setAdapter(adapter);
//        }
//    }
//
//
//    void updateAdapter() {
//
//        if (mBridgeAdapter != null) {
//            // detach observer from ObjectAdapter
//            mBridgeAdapter.clear();
//            mBridgeAdapter = null;
////                mBridgeAdapter.setAdapter(mObjectAdapter);
//        }
//        // If presenter selector is null, adapter ps will be used
//        if (mObjectAdapter != null) {
//            mBridgeAdapter = new ItemBridgeAdapter(mObjectAdapter, mPresenterSelector == null ? mObjectAdapter.getPresenterSelector() : mPresenterSelector) {
//                @Override
//                protected void onCreate(ViewHolder viewHolder) {
//                    super.onCreate(viewHolder);
//                    if (Companion.getDEBUG())
//                        Log.v(TAG, "ItemBridgeAdapter onCreate viewHolder is " + viewHolder + " this is " + FRecycleView.this);
//
//                }
//
//                @Override
//                protected void onBind(ViewHolder viewHolder) {
//                    super.onBind(viewHolder);
//                }
//
//                @Override
//                public int getItemViewType(int position) {
//                    final int type = super.getItemViewType(position);
//                    if (Companion.getDEBUG())
//                        Log.v(TAG, "ItemBridgeAdapter getItemViewType is " + type + " position is " + position + " this is " + FRecycleView.this);
//                    return type;
//                }
//
//            };
//
//
//            PresenterSelector selector = mPresenterSelector == null ? mObjectAdapter.getPresenterSelector() : mPresenterSelector;
//            Log.v(TAG, "ItemBridgeAdapter PresenterSelector is " + selector + " getPresenters is " + selector.getPresenters());
//            if (selector != null) {
//                Presenter[] presenters = selector.getPresenters();
//                if (presenters != null) {
//                    ArrayList arrayList = new ArrayList();
//                    for (Presenter p : presenters) {
//                        arrayList.add(p);
//                    }
//                    mBridgeAdapter.setPresenterMapper(arrayList);
//                }
//            }
//
//        }
//
//        this.setAdapter(mBridgeAdapter);
//    }
//
//    @Override
//    public float getFocusScaleX() {
//        return 0;
//    }
//
//    @Override
//    public float getFocusScaleY() {
//        return 0;
//    }
//
//    @Override
//    public View getView() {
//        return this;
//    }
//
//    /**
//     * see {@link IFView#setFocusScale(float)}
//     *
//     * @param scale 放大倍数
//     */
//    @Override
//    public void setFocusScale(float scale) {
//    }
//
//    /**
//     * 设置View获得焦点的整体放大倍数
//     *
//     * @param scale
//     * @return
//     */
//    @Override
//    public void setFocusScaleX(float scale) {
//
//    }
//
//    /**
//     * 设置View获得焦点的放大倍数
//     *
//     * @param scale
//     * @return
//     */
//    @Override
//    public void setFocusScaleY(float scale) {
//
//    }
//
//    @Override
//    public void setFocusScaleDuration(int duration) {
//    }
//
//
//    private class InnerItemBridgeAdapter extends ItemBridgeAdapter {
//        @Override
//        protected void onCreate(ViewHolder viewHolder) {
//        }
//    }
//
//    @Override
//    public void setAdapter(Adapter adapter) {
//        super.setAdapter(adapter);
//    }
//
//    @Override
//    public void onHandleFocusScale(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
//    }
//
//    @Override
//    public Rect getFloatFocusMarginRect() {
//        return mAttachInfo.mFloatFocusMarginRect;
//    }
//
//    @Override
//    public FRootView getFRootView() {
//        return mFRootView;
//    }
//
//    @Override
//    public AttachInfo getAttachInfo() {
//        return mAttachInfo;
//    }
//
//    @Override
//    public IFView getFloatFocusFocusableView() {
//        return null;
//    }
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
//    }
//
//
////    /**
////     *为了recycleView正确处理key事件，而写的helper类，用此类方法处理keyevent,可以实现reyclceView在item寻找焦点找不到时候，根据
////     * <p/>真实逻辑，不会将事件的控制权释放，除非item之后再无数据。
////     */
////    public static class FDispatchKeyEventHelperForRecycleView {
////
////        /**代为处理key事件
////         * @param recyclerView
////         * @param event
////         * @return
////         */
////        public static boolean handleKeyEvent(RecyclerView recyclerView,KeyEvent event){
////
////                boolean b = false;
////                if(event.getAction() == KeyEvent.ACTION_DOWN && recyclerView.hasFocus()){
////                    if(DEBUG){
////                        Log.d(TAG, "FRecycleView dispatchKeyEvent return "+b+" hasFocus is "+recyclerView.hasFocus()+" findFocus is "+recyclerView.findFocus());
////                    }
////                    View focused = recyclerView.findFocus();
////                    if(focused != null) {
////                        final int direction = FViewUtil.convertKeyCodeToDirection(event.getKeyCode());
////                        if(direction != IFView.FOCUS_INVALID) {
////                            View v = recyclerView.focusSearch(focused, direction);
////                            if (v != null && v != focused) {
////                                v.requestFocus();
////                            }
////
////                            b = v != null;
////                            if (b) {
////                                recyclerView.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
////                            }
////                        }
////                    }
////                }
////                if(DEBUG){
////                    Log.d(TAG, "FRecycleView dispatchKeyEvent return " + b);
////                }
////                return b;
////            }
////        }
//
//
//    /**
//     * 请求移动浮动焦点到对应view
//     *
//     * @param child
//     * @param focused
//     */
//    @Override
//    public void requestChildMoveFloatFocus(IFView child, IFView focused) {
//        InnerViewGroupCode.handleChildMoveFloatFocusForLayout(this, child, focused);
//        //TODO
//        if (Companion.getDEBUG()) {
//            Log.d(IFloatFocus.TAG, "FRecyecleView requestChildMoveFloatFocus this is " + this);
//        }
//        letLayoutManagerTakeFloatFocusMove(child, focused);
//    }
//
//
//    @Override
//    public void scrollBy(int x, int y) {
//
//        super.scrollBy(x, y);
//
//        if (Companion.getDEBUG()) {
//            Log.d(IFloatFocus.TAG, TAG + " scrollBy x is " + x + " scrollBy y is " + y + " LayoutManager is " + getLayoutManager() + " this is " + this);
//        }
//
//
//    }
//
//    @Override
//    public void smoothScrollToPosition(int position) {
//        super.smoothScrollToPosition(position);
//        if (Companion.getDEBUG()) {
//            Log.d(IFloatFocus.TAG, TAG + " smoothScrollToPosition  position is " + position + " this is " + this);
//        }
//    }
//
//    @Override
//    public void smoothScrollBy(int dx, int dy) {
//
//        super.smoothScrollBy(dx, dy);
//
//        if (Companion.getDEBUG()) {
//            Log.d(IFloatFocus.TAG, TAG + " smoothScrollBy dx is " + dx + " smoothScrollBy dy is " + dy + " LayoutManager is " + getLayoutManager() + " this is " + this);
//        }
//
//
//    }
//
//
////    @Override
////    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
////
////        if(DEBUG){
////            Log.i(TAG,"onScrollChanged l is "+l+" t is "+t+" oldL is "+oldl+" oltT is "+oldt);
////        }
////        super.onScrollChanged(l, t, oldl, oldt);
////    }
//
//    @Override
//    public void onScrolled(int dx, int dy) {
//        super.onScrolled(dx, dy);
//        // 这里进行满屏判断，假如 recyclerview 容器可以发生滚动，证明当前 child 超过一屏
//        if (dx != 0 || dy != 0) {
//            isFullScreen = true;
//        }
//        if (Companion.getDEBUG()) {
//            Log.d(IFloatFocus.TAG, TAG + " *******onScrolled dx is " + dx + " dy is " + dy + " this is " + this);
//        }
////        if(hasPendingFloatFocusMoveTask()){
////            mFocusMoveTask.offset.offset(dx,dy);
////        }
////        if(getFRootView() != null){
////            getFRootView().offsetFloatFocus(new Point(-dx,-dy));
////        }
//    }
//
//
//    @Override
//    public void onScrollStateChanged(int state) {
//        super.onScrollStateChanged(state);
//
//        if (Companion.getDEBUG()) {
//            Log.i(IFloatFocus.TAG, TAG + " onScrollStateChanged state is " + state + " scroll X is " + getScrollX() + " this is " + this);
//        }
//        if (state == SCROLL_STATE_SETTLING) {
//
//        } else if (state == SCROLL_STATE_IDLE) {
//        }
//    }
//
//    @Override
//    public IFloatFocusManager getFloatFocusManager() {
//        return getFRootView().getFloatFocusManager();
//    }
//
//    @Override
//    public void setFloatFocusFocusedAlpha(float alpha) {
//        mAttachInfo.setFloatFocusFocusedAlpha(alpha);
//    }
//
//    /**
//     * 监听尾部抖动的监听
//     */
//    public interface OnShakeEndListenner {
//
//        /**
//         * 尾部抖动回调执行方法
//         * 这里传出一个方向，用户可以自己处理（定义动画），一般可以使用#AnimationStore.defaultShakeEndAnimator()创建和尾部一样的动画
//         *
//         * @param orientation
//         */
//        public void onShake(IFView.FOrientation orientation);
//    }
//
//    private OnShakeEndListenner shakeEndListenner;
//
//    /**
//     * 设置尾部抖动监听
//     *
//     * @param shakeEndListenner
//     */
//    public void setOnShakeEndListenner(OnShakeEndListenner shakeEndListenner) {
//        this.shakeEndListenner = shakeEndListenner;
//    }
//
//
//    public int nextSpecifiedFocusUpIndex = -1;
//    public int nextSpecifiedFocusDownIndex = -1;
//    public int nextSpecifiedFocusLeftIndex = -1;
//    public int nextSpecifiedFocusRightIndex = -1;
//
//    public int nextSpecifiedFocusUpId = -1;
//    public int nextSpecifiedFocusDownId = -1;
//    public int nextSpecifiedFocusLeftId = -1;
//    public int nextSpecifiedFocusRightId = -1;
//
//    @Override
//    public void setNextSpecifiedFocusIndex(int index) {
//        this.nextSpecifiedFocusDownIndex = index;
//        this.nextSpecifiedFocusLeftIndex = index;
//        this.nextSpecifiedFocusRightIndex = index;
//        this.nextSpecifiedFocusUpIndex = index;
//    }
//
//    @Override
//    public void setNextSpecifiedFocusIndex(int direction, int index) {
//        switch (direction) {
//            case FOCUS_DOWN:
//                this.nextSpecifiedFocusDownIndex = index;
//                break;
//            case FOCUS_UP:
//                this.nextSpecifiedFocusUpIndex = index;
//                break;
//            case FOCUS_LEFT:
//                this.nextSpecifiedFocusLeftIndex = index;
//                break;
//
//            case FOCUS_RIGHT:
//                this.nextSpecifiedFocusRightIndex = index;
//                break;
//        }
//    }
//
//
//    private final ArrayList<View> tempFocusList = new ArrayList();
//
//    @Override
//    public View getNextSpecifiedFocus(View focused, int direction) {
//        int specifiedIndex = -1;
//        int nextSpecifiedId = -1;
//        switch (direction) {
//            case FOCUS_DOWN:
//                specifiedIndex = this.nextSpecifiedFocusDownIndex;
//                nextSpecifiedId = this.nextSpecifiedFocusDownId;
//                break;
//            case FOCUS_UP:
//                specifiedIndex = this.nextSpecifiedFocusUpIndex;
//                nextSpecifiedId = this.nextSpecifiedFocusUpId;
//                break;
//            case FOCUS_LEFT:
//                specifiedIndex = this.nextSpecifiedFocusLeftIndex;
//                nextSpecifiedId = this.nextSpecifiedFocusLeftId;
//                break;
//
//            case FOCUS_RIGHT:
//                specifiedIndex = this.nextSpecifiedFocusRightIndex;
//                nextSpecifiedId = this.nextSpecifiedFocusRightId;
//                break;
//        }
//        if (specifiedIndex >= 0 && nextSpecifiedId < 0) {
//            try {
//                if (specifiedIndex >= 0 && specifiedIndex < getChildCount()) {
//                    View target = getChildAt(specifiedIndex);
//                    if (target.isFocusable()) {
//                        return target;
//                    }
//
//                    target.addFocusables(tempFocusList, direction);
//
//                    for (View v : tempFocusList) {
//                        Log.d(TAG, "getNextSpecifiedFocus tempFocusList :  " + v);
//                    }
//
//                    if (tempFocusList.size() > 0) {
//                        return tempFocusList.get(0);
//                    }
//                }
//            } finally {
//                tempFocusList.clear();
//            }
//        }
//
//        if (nextSpecifiedId > 0) {
//            return findViewById(nextSpecifiedId);
//        }
//
//        return null;
//    }
//
//
//    @Override
//    public int getNextSpecifiedFocusUpId() {
//        return nextSpecifiedFocusUpId;
//    }
//
//    @Override
//    public void setNextSpecifiedFocusUpId(int i) {
//        this.nextSpecifiedFocusUpId = i;
//    }
//
//    @Override
//    public int getNextSpecifiedFocusDownId() {
//        return nextSpecifiedFocusDownId;
//    }
//
//    @Override
//    public void setNextSpecifiedFocusDownId(int i) {
//        this.nextSpecifiedFocusDownId = i;
//    }
//
//    @Override
//    public int getNextSpecifiedFocusLeftId() {
//        return nextSpecifiedFocusLeftId;
//    }
//
//    @Override
//    public void setNextSpecifiedFocusLeftId(int i) {
//        this.nextSpecifiedFocusLeftId = i;
//    }
//
//    @Override
//    public int getNextSpecifiedFocusRightId() {
//        return this.nextSpecifiedFocusRightId;
//    }
//
//    @Override
//    public void setNextSpecifiedFocusRightId(int i) {
//        this.nextSpecifiedFocusRightId = i;
//    }
//
//    public interface ShakeEndCallback {
//        boolean isReachListEnd(RecyclerView parent, View select, View focusSearched, int selection, int direction);
//
//        IFView.FOrientation getFOrientation();
//    }
//
//
//    @Override
//    public void requestLayout() {
//        if (DEBUG) {
//            FViewUtil.debugPerformance(this, "requestLayout");
//        }
//        super.requestLayout();
//
//    }
//
//    @Override
//    public String toString() {
//        if (DEBUG) {
//            if (getTag() != null) {
//                return super.toString() + " view tag is " + getTag();
//            }
//        }
//        return super.toString();
//    }
//
//}
