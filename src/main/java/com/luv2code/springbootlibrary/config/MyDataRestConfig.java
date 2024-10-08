package com.luv2code.springbootlibrary.config;

import com.luv2code.springbootlibrary.entity.Book;
import com.luv2code.springbootlibrary.entity.History;
import com.luv2code.springbootlibrary.entity.Message;
import com.luv2code.springbootlibrary.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

  private String theAllowedOrigins =
                  "http://localhost:3000," +
                  "https://localhost:3000," +
                  "https://react-spring-boot-library.com," +
                  "https://master.d29o4ngnjdpteo.amplifyapp.com," +
                  "https://staging.d1klcilpxs5qco.amplifyapp.com";

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
      CorsRegistry cors) {
    HttpMethod[] theUnsupportedActions = {
        HttpMethod.POST,
        HttpMethod.PATCH,
        HttpMethod.DELETE,
        HttpMethod.PUT};

    config.exposeIdsFor(Book.class);
    config.exposeIdsFor(Review.class);
    config.exposeIdsFor(History.class);
    config.exposeIdsFor(Message.class);

    disableHttpMethods(Book.class, config, theUnsupportedActions);
    disableHttpMethods(Review.class, config, theUnsupportedActions);
    disableHttpMethods(History.class, config, theUnsupportedActions);
    disableHttpMethods(Message.class, config, theUnsupportedActions);

    /* Configure CORS Mapping */
    cors.addMapping(config.getBasePath() + "/**")
        .allowedOrigins(theAllowedOrigins).allowedHeaders("*");
  }

  private void disableHttpMethods(Class theClass,
      RepositoryRestConfiguration config,
      HttpMethod[] theUnsupportedActions) {
    config.getExposureConfiguration()
        .forDomainType(theClass)
        .withItemExposure((metdata, httpMethods) ->
            httpMethods.disable(theUnsupportedActions))
        .withCollectionExposure((metdata, httpMethods) ->
            httpMethods.disable(theUnsupportedActions));
  }
}