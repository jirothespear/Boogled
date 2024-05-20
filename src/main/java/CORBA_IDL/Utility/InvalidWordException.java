package Utility;


/**
* Utility/InvalidWordException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Monday, May 20, 2024 10:38:18 PM CST
*/

public final class InvalidWordException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public InvalidWordException ()
  {
    super(InvalidWordExceptionHelper.id());
  } // ctor

  public InvalidWordException (String _reason)
  {
    super(InvalidWordExceptionHelper.id());
    reason = _reason;
  } // ctor


  public InvalidWordException (String $reason, String _reason)
  {
    super(InvalidWordExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class InvalidWordException
