package bus.route.challenge.config;

import bus.route.challenge.integration.handler.ClearRoutesHandler;
import bus.route.challenge.integration.handler.RouteMessageHandler;
import bus.route.challenge.service.RouteService;
import bus.route.challenge.service.impl.DefaultRouteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.FileListFilter;
import org.springframework.integration.file.filters.FileSystemPersistentAcceptOnceFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.metadata.SimpleMetadataStore;

import java.io.File;
import java.util.List;

import static bus.route.challenge.BusRouteChallengeConstants.ROUTES_COUNT;

@Configuration
public class AppConfig {

    @Bean
    public RouteService routeService() {
        return new DefaultRouteService();
    }

    //  Spring integration configs
    @Bean
    public FileListFilter<File> fileSystemPersistentAcceptOnceFileListFilter(@Value("${file.name}") String fileName) {
        return new FileSystemPersistentAcceptOnceFileListFilter(new SimpleMetadataStore(), fileName);
    }

    @Bean
    public FileListFilter<File> simplePatternFileListFilter(@Value("${file.name}") String fileName) {
        return new SimplePatternFileListFilter(fileName);
    }

    @Bean
    public CompositeFileListFilter<File> compositeFileListFilter(List<FileListFilter<File>> fileListFilters) {
        return new CompositeFileListFilter<>(fileListFilters);
    }

    @Bean
    public IntegrationFlow processFileFlow(@Value("${hotfolder.path}") String hotFolderPath,
                                           GenericHandler<File> clearRoutesHandler,
                                           GenericHandler<String> routeMessageHandler,
                                           CompositeFileListFilter<File> compositeFileListFilter) {
        return IntegrationFlows
                .from(Files.inboundAdapter(new File(hotFolderPath))
                                .filter(compositeFileListFilter),
                        e -> e.poller(Pollers.fixedDelay(1000)))
                .log(LoggingHandler.Level.INFO, "fileLoaded", m -> m.getHeaders().get(FileHeaders.RELATIVE_PATH))
                .handle(File.class, clearRoutesHandler)
                .split(Files.splitter()
                        .firstLineAsHeader(ROUTES_COUNT))
                .handle(String.class, routeMessageHandler)
                .get();
    }


    @Bean
    public GenericHandler<String> routeMessageHandler(RouteService routeService) {
        return new RouteMessageHandler(routeService);
    }

    @Bean
    public GenericHandler<File> clearRoutesHandler(RouteService routeService) {
        return new ClearRoutesHandler(routeService);
    }
}
