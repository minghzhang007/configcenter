package com.lewis.configcenter.common.component.transactional;

import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;


/**
 * 事务
 *
 * @author hzlimaozhi
 */
@Component
public class TransactionalComponent {

    @DBMasterTransactional
    public void masterExecute(Runnable runnable) {
        runnable.run();
    }

    @DBMasterTransactional
    public <T> T masterCall(Callable<T> callable) throws Exception {
        return callable.call();
    }

    public void safeUpdate(int update, int expect) {
        if (update != expect) {
            throw new RuntimeException();
        }
    }

    public void safeUpdate(boolean succ) {
        if (!succ) {
            throw new RuntimeException();
        }
    }
}
