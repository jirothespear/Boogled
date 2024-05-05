//package server.controller;
//
//import org.omg.CORBA.ORB;
//import org.omg.CORBA.ORBPackage.InvalidName;
//import org.omg.CosNaming.NameComponent;
//import org.omg.CosNaming.NamingContextExt;
//import org.omg.CosNaming.NamingContextExtHelper;
//import org.omg.CosNaming.NamingContextPackage.CannotProceed;
//import org.omg.CosNaming.NamingContextPackage.NotFound;
//import org.omg.PortableServer.POA;
//import org.omg.PortableServer.POAHelper;
//import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
//import org.omg.PortableServer.POAPackage.ServantNotActive;
//import org.omg.PortableServer.POAPackage.WrongPolicy;
//
//public class ServerMainController {
//
//    public static void main(String[] args) throws InvalidName, AdapterInactive, org.omg.CosNaming.NamingContextPackage.InvalidName, WrongPolicy, ServantNotActive, CannotProceed, NotFound {
//
//        ORB orb = ORB.init(args, null);
//
//        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
//        rootpoa.the_POAManager().activate();
//
//        ServerUtilityImpl servantImpl = new ServerUtilityImpl();
//        servantImpl.setOrb(orb);
//
//        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servantImpl);
//        Utility.ServerUtility cref = Utility.ServerUtilityHelper.narrow(ref);
//
//        org.omg.CORBA.Object objRef =
//                orb.resolve_initial_references("NameService");
//        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//
//        String name = "Ciao";
//        NameComponent path[] = ncRef.to_name(name);
//        ncRef.rebind(path, cref);
//
//        orb.run();
//    }
//}
