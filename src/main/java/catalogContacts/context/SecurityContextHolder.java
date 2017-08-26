package catalogContacts.context;

import catalogContacts.model.User;

/**
 *
 */
public class SecurityContextHolder {
    private static final ThreadLocal<Integer> threadLocalScope = new  ThreadLocal<>();

    public final static Integer getLoggedUserID() {
        return threadLocalScope.get();
    }

    public final static void setLoggedUserID(Integer id) {
        threadLocalScope.set(id);
    }
}
