package CORBA_IDL.Utility;


/**
* Utility/GameStartException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Thursday, May 23, 2024 1:26:12 AM CST
*/

public final class GameStartException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public GameStartException ()
  {
    super(GameStartExceptionHelper.id());
  } // ctor

  public GameStartException (String _reason)
  {
    super(GameStartExceptionHelper.id());
    reason = _reason;
  } // ctor


  public GameStartException (String $reason, String _reason)
  {
    super(GameStartExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class GameStartException
