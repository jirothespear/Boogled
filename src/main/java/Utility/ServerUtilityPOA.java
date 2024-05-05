package Utility;


/**
* Utility/ServerUtilityPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Sunday, May 5, 2024 4:57:07 PM CST
*/

public abstract class ServerUtilityPOA extends org.omg.PortableServer.Servant
 implements Utility.ServerUtilityOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("login", new java.lang.Integer (0));
    _methods.put ("loginCallback", new java.lang.Integer (1));
    _methods.put ("logout", new java.lang.Integer (2));
    _methods.put ("checkUser", new java.lang.Integer (3));
    _methods.put ("checkWord", new java.lang.Integer (4));
    _methods.put ("startGame", new java.lang.Integer (5));
    _methods.put ("showWinnerOfRound", new java.lang.Integer (6));
    _methods.put ("showScore", new java.lang.Integer (7));
    _methods.put ("getLetterChoice", new java.lang.Integer (8));
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
       case 0:  // Utility/ServerUtility/login
       {
         try {
           String username = in.read_wstring ();
           String passwd = in.read_wstring ();
           this.login (username, passwd);
           out = $rh.createReply();
         } catch (Utility.LoginException $ex) {
           out = $rh.createExceptionReply ();
           Utility.LoginExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // Utility/ServerUtility/loginCallback
       {
         Utility.ClientCallback clientCallback = Utility.ClientCallbackHelper.read (in);
         this.loginCallback (clientCallback);
         out = $rh.createReply();
         break;
       }

       case 2:  // Utility/ServerUtility/logout
       {
         try {
           Utility.ClientCallback clientCallback = Utility.ClientCallbackHelper.read (in);
           this.logout (clientCallback);
           out = $rh.createReply();
         } catch (Utility.LogoutException $ex) {
           out = $rh.createExceptionReply ();
           Utility.LogoutExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // Utility/ServerUtility/checkUser
       {
         String userNPasswd = in.read_wstring ();
         String gameID = in.read_wstring ();
         boolean $result = false;
         $result = this.checkUser (userNPasswd, gameID);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // Utility/ServerUtility/checkWord
       {
         String answer = in.read_wstring ();
         String gameID = in.read_wstring ();
         boolean $result = false;
         $result = this.checkWord (answer, gameID);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 5:  // Utility/ServerUtility/startGame
       {
         String user = in.read_wstring ();
         this.startGame (user);
         out = $rh.createReply();
         break;
       }

       case 6:  // Utility/ServerUtility/showWinnerOfRound
       {
         String $result = null;
         $result = this.showWinnerOfRound ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 7:  // Utility/ServerUtility/showScore
       {
         String user = in.read_wstring ();
         String gameID = in.read_wstring ();
         int $result = (int)0;
         $result = this.showScore (user, gameID);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 8:  // Utility/ServerUtility/getLetterChoice
       {
         String gameID = in.read_wstring ();
         String $result = null;
         $result = this.getLetterChoice (gameID);
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
    "IDL:Utility/ServerUtility:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ServerUtility _this() 
  {
    return ServerUtilityHelper.narrow(
    super._this_object());
  }

  public ServerUtility _this(org.omg.CORBA.ORB orb) 
  {
    return ServerUtilityHelper.narrow(
    super._this_object(orb));
  }


} // class ServerUtilityPOA
