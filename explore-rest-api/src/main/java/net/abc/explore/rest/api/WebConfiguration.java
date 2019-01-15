package net.abc.explore.rest.api;

import net.abc.explore.rest.api.exception.GlobalHandlerExeceptionResolver;
import net.abc.explore.rest.api.mapper.JsonMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 19:06/2019-01-05
 */
@Configurable
public class WebConfiguration {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(mediaTypeList);
        converter.setPrettyPrint(false);
        converter.setObjectMapper(new JsonMapper());
        return converter;
    }

//    @Bean
//    public GlobalHandlerExeceptionResolver globalHandlerExeceptionResolver(){
//       return new GlobalHandlerExeceptionResolver();
//    }
}
