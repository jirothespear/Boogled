package Utility;


/**
* Utility/PlayerUtilityPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Wednesday, May 15, 2024 9:43:22 AM CST
*/

public abstract class PlayerUtilityPOA extends org.omg.PortableServer.Servant
 implements Utility.PlayerUtilityOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("login", new java.lang.Integer (0));
    _methods.put ("userCallback", new java.lang.Integer (1));
    _methods.put ("logout", new java.lang.Integer (2));
    _methods.put ("startGame", new java.lang.Integer (3));
    _methods.put ("checkWord", new java.lang.Integer (4));
    _methods.put ("showScore", new java.lang.Integer (5));
    _methods.put ("getQueueTime", new java.lang.Integer (6));
    _methods.put ("getRoundTime", new java.lang.Integer (7));
    _methods.put ("getLetterChoice", new java.lang.Integer (8));
    _methods.put ("getGameID", new java.lang.Integer (9));
    _methods.put ("getRoundCount", new java.lang.Integer (10));
    _methods.put ("getLeaderboardUsernames", new java.lang.Integer (11));
    _methods.put ("getLeaderboardPoints", new java.lang.Integer (12));
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
       case 0:  // Utility/PlayerUtility/login
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

       case 1:  // Utility/PlayerUtility/userCallback
       {
         Utility.ClientCallback clientCallback = Utility.ClientCallbackHelper.read (in);
         String username = in.read_wstring ();
         this.userCallback (clientCallback, username);
         out = $rh.createReply();
         break;
       }

       case 2:  // Utility/PlayerUtility/logout
       {
         try {
           Utility.ClientCallback clientCallback = Utility.ClientCallbackHelper.read (in);
           String username = in.read_wstring ();
           this.logout (clientCallback, username);
           out = $rh.createReply();
         } catch (Utility.LogoutException $ex) {
           out = $rh.createExceptionReply ();
           Utility.LogoutExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // Utility/PlayerUtility/startGame
       {
         try {
           String user = in.read_wstring ();
           String $result = null;
           $result = this.startGame (user);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (Utility.GameStartException $ex) {
           out = $rh.createExceptionReply ();
           Utility.GameStartExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // Utility/PlayerUtility/checkWord
       {
         try {
           String answer = in.read_wstring ();
           String playerID = in.read_wstring ();
           String gameID = in.read_wstring ();
           this.checkWord (answer, playerID, gameID);
           out = $rh.createReply();
         } catch (Utility.InvalidWordException $ex) {
           out = $rh.createExceptionReply ();
           Utility.InvalidWordExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // Utility/PlayerUtility/showScore
       {
         String user = in.read_wstring ();
         String gameID = in.read_wstring ();
         int $result = (int)0;
         $result = this.showScore (user, gameID);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 6:  // Utility/PlayerUtility/getQueueTime
       {
         String username = in.read_wstring ();
         this.getQueueTime (username);
         out = $rh.createReply();
         break;
       }

       case 7:  // Utility/PlayerUtility/getRoundTime
       {
         String username = in.read_wstring ();
         String gameID = in.read_wstring ();
         this.getRoundTime (username, gameID);
         out = $rh.createReply();
         break;
       }

       case 8:  // Utility/PlayerUtility/getLetterChoice
       {
         String gameID = in.read_wstring ();
         String $result = null;
         $result = this.getLetterChoice (gameID);
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 9:  // Utility/PlayerUtility/getGameID
       {
         String username = in.read_wstring ();
         String $result = null;
         $result = this.getGameID (username);
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 10:  // Utility/PlayerUtility/getRoundCount
       {
         String gameID = in.read_wstring ();
         String $result = null;
         $result = this.getRoundCount (gameID);
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 11:  // Utility/PlayerUtility/getLeaderboardUsernames
       {
         String $result[] = null;
         $result = this.getLeaderboardUsernames ();
         out = $rh.createReply();
         Utility.playerLeaderboardHelper.write (out, $result);
         break;
       }

       case 12:  // Utility/PlayerUtility/getLeaderboardPoints
       {
         int $result[] = null;
         $result = this.getLeaderboardPoints ();
         out = $rh.createReply();
         Utility.playerLeaderboardPointsHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Utility/PlayerUtility:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public PlayerUtility _this() 
  {
    return PlayerUtilityHelper.narrow(
    super._this_object());
  }

  public PlayerUtility _this(org.omg.CORBA.ORB orb) 
  {
    return PlayerUtilityHelper.narrow(
    super._this_object(orb));
  }


} // class PlayerUtilityPOA
