package testers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Policy;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;
import server.controller.ServerUtilityImpl;

import java.io.IOException;
import java.util.Properties;

public class ServerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("server-view.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/server/server-view.fxml"));
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Boggled Server");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Disable automatic focus traversal for the root node of the scene
        root.requestFocus();
        root.setFocusTraversable(false);
    }

    public static void main(String[] args) throws InvalidName, AdapterInactive, WrongPolicy, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, NotFound, AlreadyBound, InterruptedException, InvalidPolicy, AdapterAlreadyExists, ObjectAlreadyActive, ServantAlreadyActive, ObjectNotActive {


        Properties props = new Properties();
        //  props.put("org.omg.CORBA.ORBInitialHost", "localhost");
        // props.put("org.omg.CORBA.ORBInitialPort", "2055");


        ORB orb = ORB.init(args, null);
        POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootPOA.the_POAManager().activate();

        POAManager poaManager = rootPOA.the_POAManager();


        Policy[] policies = new Policy[]{
                rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL),
                rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
                rootPOA.create_implicit_activation_policy(
                        ImplicitActivationPolicyValue.NO_IMPLICIT_ACTIVATION),
                rootPOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
                rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
                rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
                rootPOA.create_request_processing_policy(
                        RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY),
        };


        // POA entryPOA = rootPOA.create_POA("entry", null, policies);

        //  entryPOA.the_POAManager().activate();

        ServerUtilityImpl servantImpl = new ServerUtilityImpl();
        byte[] oid = "playerUtility".getBytes();
        servantImpl.setOrb(orb);
        // entryPOA.activate_object_with_id(oid, servantImpl);

        org.omg.CORBA.Object ref = rootPOA.servant_to_reference(servantImpl);
        Utility.PlayerUtility cref = Utility.PlayerUtilityHelper.narrow(ref);

        org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        String name = "Ciao";
        NameComponent path[] = ncRef.to_name(name);
        ncRef.rebind(path, cref);



            /*
            org.omg.CORBA.Object obj = entryPOA.id_to_reference(oid);
            String serverIor = orb.object_to_string(obj);
            try (FileWriter fw = new FileWriter("my.ior")) {
                    fw.write(serverIor);
            } catch(IOException ioe) {
            }



             */

        launch();
        orb.run();


    }

}
