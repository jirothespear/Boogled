package testers;

import Utility.ClientCallback;
import Utility.ClientCallbackHelper;
import Utility.PlayerUtility;
import client.comproggui.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

public class client2 extends Application {

    public static ClientCallbackImpl ciaoCallbackImpl;

    public  static  ClientCallback cref;

    public  static PlayerUtility serverUtility;

    public static void main(String[] args) throws InvalidName, AdapterInactive, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, NotFound, WrongPolicy, ServantNotActive {
        try {
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost","localhost");
            props.put("org.omg.CORBA.ORBInitialPort","2055");

            ORB orb = ORB.init(args, props);

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Ciao";
            serverUtility = Utility.PlayerUtilityHelper.narrow(ncRef.resolve_str(name));

            ciaoCallbackImpl = new ClientCallbackImpl();
            ciaoCallbackImpl.setORB(orb);

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(ciaoCallbackImpl);
            cref = ClientCallbackHelper.narrow(ref);

           // serverUtility.login("renuel", "renuel");
          //  serverUtility.userCallback(cref, "renuel");
         //   serverUtility.getQueueTime("renuel");
            launch();

           Thread.currentThread().join();

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }

    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/login-view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();

            loginController.setServerUtility(serverUtility);
            loginController.setClientCallbackImpl(ciaoCallbackImpl);
            loginController.setClientCallback(cref);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/server/Textfield.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
