package material;

import android.content.Context;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义CoordinatorLayout的Behavior，泛型为观察者View(要跟着别人动的那个)
 * <p>
 * 必须重写两个方法，layoutDependOn和onDependentViewChanged
 *
 */

public class RecycleViewFollowBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    private int i = 0;

    /**
     * 构造方法
     */
    public RecycleViewFollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 判断child的布局是否依赖dependency
     * <p>
     * 根据逻辑来判断返回值，返回false表示不依赖，返回true表示依赖
     * <p>
     * 在一个交互行为中，dependent view的变化决定了另一个相关View的行为。
     * 在这个例子中，Button就是dependent view，因为AppBarLayout跟随着它。
     * 实际上dependent view就相当于我们前面介绍的被观察者
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        // child.setY(dependency.getY());
       // child.scrollTo(0, dependency.getScrollY());
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
