package Utility;


/**
* Utility/ClientCallbackPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Thursday, May 9, 2024 12:22:12 PM CST
*/

public abstract class ClientCallbackPOA extends org.omg.PortableServer.Servant
 implements Utility.ClientCallbackOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("startRound", new java.lang.Integer (0));
    _methods.put ("roundEnd", new java.lang.Integer (1));
    _methods.put ("gameFinish", new java.lang.Integer (2));
    _methods.put ("getScore", new java.lang.Integer (3));
    _methods.put ("getRoundTime", new java.lang.Integer (4));
    _methods.put ("getQueueTime", new java.lang.Integer (5));
    _methods.put ("getGameTime", new java.lang.Integer (6));
    _methods.put ("getLetterChoice", new java.lang.Integer (7));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Utility/ClientCallback/startRound
       {
         String $result = null;
         $result = this.startRound ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 1:  // Utility/ClientCallback/roundEnd
       {
         this.roundEnd ();
         out = $rh.createReply();
         break;
       }

       case 2:  // Utility/ClientCallback/gameFinish
       {
         this.gameFinish ();
         out = $rh.createReply();
         break;
       }

       case 3:  // Utility/ClientCallback/getScore
       {
         int time = in.read_long ();
         int $result = (int)0;
         $result = this.getScore (time);
         out = $rh.createReply();
         out.write_ulong ($result);
         break;
       }

       case 4:  // Utility/ClientCallback/getRoundTime
       {
         int time = in.read_long ();
         this.getRoundTime (time);
         out = $rh.createReply();
         break;
       }

       case 5:  // Utility/ClientCallback/getQueueTime
       {
         int time = in.read_long ();
         this.getQueueTime (time);
         out = $rh.createReply();
         break;
       }

       case 6:  // Utility/ClientCallback/getGameTime
       {
         int time = in.read_long ();
         this.getGameTime (time);
         out = $rh.createReply();
         break;
       }

       case 7:  // Utility/ClientCallback/getLetterChoice
       {
         String letters = in.read_wstring ();
         String $result = null;
         $result = this.getLetterChoice (letters);
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Utility/ClientCallback:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ClientCallback _this() 
  {
    return ClientCallbackHelper.narrow(
    super._this_object());
  }

  public ClientCallback _this(org.omg.CORBA.ORB orb) 
  {
    return ClientCallbackHelper.narrow(
    super._this_object(orb));
  }


} // class ClientCallbackPOA
