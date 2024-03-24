package orchestrator.config.client;


import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import orchestrator.worker.client.WorkerClient;
import orchestrator.worker.client.WorkerFactory;
import orchestrator.worker.property.WorkerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
@Slf4j
@Configuration
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    @ConditionalOnBean({WorkerProperties.class})
    public WorkerFactory workerFactory(
            Encoder encoder,
            Decoder decoder,
            @Value("${discord.worker.feign.connection-timeout:5000}")
            Integer connectionTimeOut,
            @Value("${discord.worker.feign.read-timeout:10000}")
            Integer readTimeOut,
            WorkerProperties workerProperties) {

        Request.Options options = new Request.Options(
                connectionTimeOut, TimeUnit.MILLISECONDS,
                readTimeOut, TimeUnit.MILLISECONDS,
                true
        );

        Map<UUID, WorkerClient> workerClients = workerProperties.getWorkers().stream()
                .filter(WorkerProperties.WorkerInfo::getIsEnabled)
                .map(worker -> buildWorkerClient(encoder, decoder, options, worker))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new WorkerFactory(workerClients);
    }

    private Map.Entry<UUID, WorkerClient> buildWorkerClient(Encoder encoder, Decoder decoder, Request.Options options, WorkerProperties.WorkerInfo workerInfo) {

        WorkerClient workerClient = Feign.builder()
                .client(new ApacheHttpClient())
                .encoder(encoder)
                .decoder(decoder)
                .retryer(Retryer.NEVER_RETRY)
                .errorDecoder(new ErrorDecoder.Default())
                .contract(new SpringMvcContract())
                .options(options)
                .target(WorkerClient.class, workerInfo.getUrl());

        log.info("WorkerClient spring bean successfully built for Discord BOT [%s]".formatted(workerInfo.getName()));
        return Map.entry(workerInfo.getId(), workerClient);
    }
}
