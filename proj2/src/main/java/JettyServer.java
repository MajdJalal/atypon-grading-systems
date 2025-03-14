import com.atypon.controller.GradesServlet;
import com.atypon.controller.LoginServlet;
import com.atypon.controller.StatsServlet;
import com.atypon.controller.TestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("proj2/src/main/webapp");
        webAppContext.setWelcomeFiles(new String[]{"index.jsp"});
        webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        webAppContext.setParentLoaderPriority(true);

        // 🔥 Enable session support
        webAppContext.setSessionHandler(new SessionHandler());

        // Add Servlets
        webAppContext.addServlet(new ServletHolder(new LoginServlet()), "/login");
        webAppContext.addServlet(new ServletHolder(new LoginServlet()), "/logout");
        webAppContext.addServlet(new ServletHolder(new GradesServlet()), "/grades");
        webAppContext.addServlet(new ServletHolder(new StatsServlet()), "/stats");

        server.setHandler(webAppContext);
        server.start();
        server.join();
    }
}