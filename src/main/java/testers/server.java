package testers;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import server.controller.ServerUtilityImpl;

import java.util.Properties;

public class server {

    public static void main(String[] args) throws InvalidName, AdapterInactive, WrongPolicy, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, NotFound {

        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialHost","192.168.56.1");
        props.put("org.omg.CORBA.ORBInitialPort","1099");

        ORB orb = ORB.init(args, props);

        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootpoa.the_POAManager().activate();

        ServerUtilityImpl servantImpl = new ServerUtilityImpl();
        servantImpl.setOrb(orb);

        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servantImpl);
        Utility.PlayerUtility cref = Utility.PlayerUtilityHelper.narrow(ref);

        org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        String name = "Ciao";
        NameComponent path[] = ncRef.to_name(name);
        ncRef.rebind(path, cref);

        orb.run();
    }
}
