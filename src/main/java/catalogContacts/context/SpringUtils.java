package catalogContacts.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service("springUtil")
public class SpringUtils implements ApplicationContextAware{
        private static ApplicationContext context;

        public static ApplicationContext getContext() {
            return context;
        }
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
        }

}
