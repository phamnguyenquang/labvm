package BackEnd;

import javax.swing.JOptionPane;

public class vmHandler {
	private CommandExecutor bw;
	private pciConfiguration pci;
	private String pciAddressesComm;

	public vmHandler(pciConfiguration pciHandler, CommandExecutor cmd) {
		pci = pciHandler;
		bw = cmd;
	}

	public void startVM(String path) {
		if (path == "" || path == null) {
			JOptionPane.showMessageDialog(null, "Please select an OS", "Error", 0);
		} else {
			pciAddressesComm = "";
			pci.vfioBind();
			for (int i = 0; i < pci.PCISlotNumber(); ++i) {
				pciAddressesComm += ("-device vfio-pci,host=" + pci.getPCIaddressAt(i) + " ");
			}
			System.out.println(
					"sudo kvm -vga qxl -m 2048 -hda " + path + " -sdl -usb -usbdevice tablet -cpu host -smp 4,sockets=1,cores=4,threads=1 " + pciAddressesComm);
			bw.startCommand(
					"sudo kvm -vga qxl -m 1024 -hda " + path + " -sdl -usb -usbdevice tablet -cpu host -smp 4,sockets=1,cores=4,threads=1 " + pciAddressesComm);
		}
	}

	public void startSnapShotFrom(String path) {
		if (path == "" || path == null) {
			JOptionPane.showMessageDialog(null, "Please select an OS", "Error", 0);
		} else {
			pciAddressesComm = "";
			pci.vfioBind();
			for (int i = 0; i < pci.PCISlotNumber(); ++i) {
				pciAddressesComm += ("-device vfio-pci,host=" + pci.getPCIaddressAt(i) + " ");
			}
			System.out.println(
					"sudo kvm -vga qxl -m 2048 -hda " + path + " -sdl -usb -usbdevice tablet -cpu host -smp 4,sockets=1,cores=4,threads=1 " + pciAddressesComm);
			bw.startCommand(
					"sudo kvm -vga qxl -m 1024 -hda " + path + " -usb -usbdevice tablet -cpu host -smp 4,sockets=1,cores=4,threads=1 " + pciAddressesComm + " -snapshot");
		}
	}

}