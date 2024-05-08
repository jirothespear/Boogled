package testers;

import Utility.ClientCallback;
import Utility.ClientCallbackHelper;
import Utility.ServerUtility;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.util.Properties;

public class client {

    ClientCallbackImpl clientCallback;

    static ServerUtility serverUtility;
    public static void main(String[] args) throws InvalidName, AdapterInactive, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, NotFound, WrongPolicy, ServantNotActive {


        try {
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost","192.168.56.1");
            props.put("org.omg.CORBA.ORBInitialPort","1099");

            ORB orb = ORB.init(args, props);


            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Ciao";
            serverUtility = Utility.ServerUtilityHelper.narrow(ncRef.resolve_str(name));

            ClientCallbackImpl ciaoCallbackImpl = new ClientCallbackImpl();
            ciaoCallbackImpl.setORB(orb);

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(ciaoCallbackImpl);
            ClientCallback cref = ClientCallbackHelper.narrow(ref);

            serverUtility.loginCallback(cref);
            serverUtility.startGame("user");
            serverUtility.checkWord("skbidi", "user", "1");
            Thread.currentThread().join();

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }

    }


}
