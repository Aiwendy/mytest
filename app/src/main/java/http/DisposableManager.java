package http;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Function: 事件订阅管理
 */

public class DisposableManager {
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static CompositeDisposable instance() {
        return compositeDisposable;
    }
}
