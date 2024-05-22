package CORBA_IDL.Utility;


/**
* Utility/LoginException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./Utility.idl
* Wednesday, May 22, 2024 11:19:37 AM SGT
*/

public final class LoginException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public LoginException ()
  {
    super(LoginExceptionHelper.id());
  } // ctor

  public LoginException (String _reason)
  {
    super(LoginExceptionHelper.id());
    reason = _reason;
  } // ctor


  public LoginException (String $reason, String _reason)
  {
    super(LoginExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class LoginException
