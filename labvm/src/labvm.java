import java.io.*;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.virshHandler;
import BackEnd_VMtypeHandlers.vmHandler;
import Development.SomeTest;
import GUI.OSSelectionGUI;
import XMLhandler.XMLReadWrite;

public class labvm {

	public static void main(String args[]) throws IOException {
//		GeneralVMHandler vmHandler = new virshHandler();
//		new OSSelectionGUI(vmHandler);
		SomeTest tt = new SomeTest();
		tt.makeConnection("quang", "kalimdor");
		tt.downloadFile("pxelinux.0");
		tt.destroyConnection();
	}
}
