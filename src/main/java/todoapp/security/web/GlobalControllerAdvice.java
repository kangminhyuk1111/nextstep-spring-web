package todoapp.security.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import todoapp.web.model.SiteProperties;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final SiteProperties siteProperties;

    public GlobalControllerAdvice(final SiteProperties siteProperties) {
        this.siteProperties = siteProperties;
    }

    @ModelAttribute("site")
    public SiteProperties siteProperties() {
        return siteProperties;
    }
}
