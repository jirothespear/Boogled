package Utility;


/**
* Utility/LogoutException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Wednesday, May 15, 2024 3:24:14 PM CST
*/

public final class LogoutException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public LogoutException ()
  {
    super(LogoutExceptionHelper.id());
  } // ctor

  public LogoutException (String _reason)
  {
    super(LogoutExceptionHelper.id());
    reason = _reason;
  } // ctor


  public LogoutException (String $reason, String _reason)
  {
    super(LogoutExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class LogoutException
