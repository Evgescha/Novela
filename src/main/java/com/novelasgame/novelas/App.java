package com.novelasgame.novelas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.novelasgame.novelas.storage.StorageProperties;

/**
 * Hello world!
 *
 */
@SpringBootApplication

@EnableConfigurationProperties(StorageProperties.class)
public class App {
//    private static GameService gameService;
//    private static LabelService labelService;
//    private static CommandService commandService;
//    private static RoleServiceImpl roleServiceImpl;
//    private static UserServiceImpl userServiceImpl;
//    private static UserGameServiceImpl userGameServiceImpl;
//
//    @Autowired
//    private GameService gameService0;
//    @Autowired
//    private LabelService labelService0;
//    @Autowired
//    private CommandService commandService0;
//    @Autowired
//    private RoleServiceImpl roleServiceImpl2;
//    @Autowired
//    private UserServiceImpl userServiceImpl2;
//    @Autowired
//    private UserGameServiceImpl userGameServiceImpl2;
//
//    @PostConstruct
//    public void init() {
//        this.gameService = this.gameService0;
//        this.labelService = this.labelService0;
//        this.commandService = this.commandService0;
//        
//        this.roleServiceImpl = this.roleServiceImpl2;
//        this.userServiceImpl = this.userServiceImpl2;
//        this.userGameServiceImpl = this.userGameServiceImpl2;
//        
//    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        System.out.println("Hello World!");
                
    }
//    @Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}
    
}
