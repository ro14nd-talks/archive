package devoxx;

import java.lang.management.ManagementFactory;

import javax.management.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MBeanServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(new Chat(), new ObjectName("devoxx:type=chat"));
            System.out.println("Registering Devoxx MBeans");
            System.out.println("Registering Devoxx MBeans");

            server.registerMBean(new Chat(), new ObjectName("devoxx:type=chat"));

            server.registerMBean(new RandomGraph(), new ObjectName("devoxx:type=chart"));
        } catch (Exception e) {
            throw new ServletException("Error registering MBeans",e);
        }
    }
}
