import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.stage.manager.impl.StageCacheManager;
import org.stage.session.StageSession;

/**
 * class Test
 * function:
 *
 * @Author chens
 * @Date 2018/7/7
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:stage-spring.xml");

        StageCacheManager stageCacheManager = applicationContext.getBean(StageCacheManager.class);

        if (stageCacheManager != null) {
            System.out.println(stageCacheManager.getStage(StageSession.WORLD_STAGE_KEY));
        }
    }
}
