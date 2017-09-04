package catalogContacts.servlet;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest pRequest, HttpServletResponse pResponse, Authentication authentication)
            throws ServletException, IOException {

        SavedRequest savedRequest = requestCache.getRequest(pRequest, pResponse);

        if (savedRequest == null) {
            clearAuthenticationAttributes(pRequest);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParam != null && StringUtils.hasText(pRequest.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(pRequest, pResponse);
            clearAuthenticationAttributes(pRequest);
            return;
        }

        clearAuthenticationAttributes(pRequest);
    }

    public void setRequestCache(RequestCache pRequestCache) {
        this.requestCache = pRequestCache;
    }
}
