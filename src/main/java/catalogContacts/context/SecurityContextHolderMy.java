package catalogContacts.context;

/**
 *
 */
public class SecurityContextHolderMy {
    private static final ThreadLocal<Integer> threadLocalScope = new  ThreadLocal<>();

    public final static Integer getLoggedUserID() {
        return threadLocalScope.get();
    }

    public final static void setLoggedUserID(Integer id) {
        threadLocalScope.set(id);
    }
}
