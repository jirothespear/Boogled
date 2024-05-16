package Client_Java.runnable;

import CORBA_IDL.Utility.ClientCallback;
import CORBA_IDL.Utility.ClientCallbackHelper;
import CORBA_IDL.Utility.PlayerUtility;
import Client_Java.controllers.LoginController;
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
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import Client_Java.model.ClientCallbackImpl;

import java.util.Properties;

public class client extends Application {

    public static ClientCallbackImpl ciaoCallbackImpl;

    public  static  ClientCallback cref;

    public  static PlayerUtility serverUtility;

    public static void main(String[] args) throws InvalidName, AdapterInactive, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, NotFound, WrongPolicy, ServantNotActive {
        try {
            Properties props = new Properties();


            props.put("org.omg.CORBA.ORBInitialHost", "192.168.56.1");
            props.put("org.omg.CORBA.ORBInitialPort", "2055");

            ORB orb = ORB.init(args, props);



            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();


            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Ciao";
            serverUtility = CORBA_IDL.Utility.PlayerUtilityHelper.narrow(ncRef.resolve_str(name));

            ciaoCallbackImpl = new ClientCallbackImpl();
            ciaoCallbackImpl.setORB(orb);


            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(ciaoCallbackImpl);
            cref = ClientCallbackHelper.narrow(ref);


//            System.out.println(Arrays.toString(serverUtility.getLeaderboardUsernames()));
//            System.out.println("Obtained a address -> " + serverUtility);
            launch();


           Thread.currentThread().join();

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }

    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setServerUtility(serverUtility);
            loginController.setClientCallbackImpl(ciaoCallbackImpl);
            loginController.setClientCallback(cref);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Textfield.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
