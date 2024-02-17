package orchestrator.config.client;


import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import orchestrator.worker.client.GaladrielClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

@Configuration
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    public GaladrielClient galadrielClient(
            Encoder encoder,
            Decoder decoder,
            @Value("${discord.worker.feign.connection-timeout:5000}")
            Integer connectionTimeOut,
            @Value("${discord.worker.feign.read-timeout:10000}")
            Integer readTimeOut,
            @Value("${discord.worker.feign.galadriel.url}")
            String url) {

        Request.Options options = new Request.Options(
                connectionTimeOut, TimeUnit.MILLISECONDS,
                readTimeOut, TimeUnit.MILLISECONDS,
                true
        );

        return Feign.builder()
                .client(new ApacheHttpClient())
                .encoder(encoder)
                .decoder(decoder)
                .retryer(Retryer.NEVER_RETRY)
                .errorDecoder(new ErrorDecoder.Default())
                .contract(new SpringMvcContract())
                .options(options)
                .target(GaladrielClient.class, url);
    }

}
