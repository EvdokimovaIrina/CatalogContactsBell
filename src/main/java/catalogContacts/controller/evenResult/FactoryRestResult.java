package catalogContacts.controller.evenResult;

import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class FactoryRestResult {
    public RestResult getSuccessResult(EventType eventType,Object object) {
        if (object != null) {

           return new RestResult(eventType,object);
        }
        return new RestResult(EventType.ERROR, "Ошибка получения данных");
    }

    public RestResult getFailResult(Exception e) {
        return new RestResult(EventType.ERROR,"Ошибка получения данных");
    }
}
