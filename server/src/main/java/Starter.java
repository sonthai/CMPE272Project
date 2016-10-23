import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import rest.RestRouter;

public class Starter {
    public void run() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RestRouter());
        context.start();
        System.in.read();
        context.stop();
    }

    public static void main(String[] args) throws Exception {
        new Starter().run();
    }
}