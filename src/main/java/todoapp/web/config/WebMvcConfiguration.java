package todoapp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import todoapp.web.support.servlet.view.CommaSeparatedValuesView;

import java.util.ArrayList;

/**
 * Spring Web MVC 설정 정보이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // 여기서 ResourceHandler를 등록, 핸들러는 스프링 MVC에서 요청을 처리하기 위해 작성되는 객체

        // registry.addResourceHandler("/assets/**").addResourceLocations("assets/");

        // registry.addResourceHandler("/assets/**").addResourceLocations("file:./files/assets");

        // registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets");

        // registry.addResourceHandler("/assets/**")
        //        .addResourceLocations("assets/", "file:./files/assets", "classpath:/assets");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // registry.enableContentNegotiation(new CommaSeparatedValuesView());
        // registry.enableContentNegotiation();
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.
    }

    /**
     * 스프링부트가 생성한 ContentNegotiatingViewResolver를 조작할 목적으로 작성된 설정 정보이다.
     */
    @Configuration
    public static class ContentNegotiationCustomizer {

        @Autowired
        public void configurer(ContentNegotiatingViewResolver viewResolver) {
            var defaultViews = new ArrayList<>(viewResolver.getDefaultViews());
            defaultViews.add(new CommaSeparatedValuesView());

            viewResolver.setDefaultViews(defaultViews);
        }
    }
}
