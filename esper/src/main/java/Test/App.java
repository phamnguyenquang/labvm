package Test;

import java.util.ArrayList;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.scopetest.SupportSubscriber;

import LogReader.LogCombi;
import LogReader.LogEvent;
import LogReader.LogProcess;
import LogReader.Logtransform;

public class App {

	public static void main(String[] args) {

		/*
		 * Preserve the log by copying it into ArrayList<String> line by line everytime
		 * "sudo cat /var/log/*.log" is called, root session is logged first, then the
		 * command is executed
		 */

		LogCombi logCombination = new LogCombi();
		int i = logCombination.authLength();
		LogProcess lp = new LogProcess();

		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = engine.getEPAdministrator();
		engine.getEPAdministrator().getConfiguration().addEventType(LogProcess.class);
		engine.getEPAdministrator().getConfiguration().addEventType(Logtransform.class);

		String schema = "create context Test start @now end after 10 sec";

		String log = "context Test select authLine from LogProcess where authLine.contains('root')";

		String log2 = "select * from LogProcess match_recognize( "
				+ "measures A as LogProcess1, B as LogProcess2 pattern (A B) define A as A.authLine.contains('ssh'), B as B.authLine.contains('ssh'))";

		SupportSubscriber subscriber = new SupportSubscriber();

		EPStatement log2Statement = admin.createEPL(lp.getStatement());
//		EPStatement schemaCreate = engine.getEPAdministrator().createEPL(schema);
//		EPStatement logStatement = engine.getEPAdministrator().createEPL(log);

		log2Statement.setSubscriber(lp);

//		log2Statement.addListener((newData, oldData) -> {
//			String test = (String) newData[0].get("authLine");
//			String test1 = (String) newData[0].get("sysLine");
//			System.out.println(test);
//		});
		for (int i1 = 0; i1 < i; ++i1) {
			engine.getEPRuntime().sendEvent(new Logtransform(logCombination, i1));
		}

	}

}