package Utility;

/**
* Utility/GameStartExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Monday, May 6, 2024 1:08:13 PM CST
*/

public final class GameStartExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public Utility.GameStartException value = null;

  public GameStartExceptionHolder ()
  {
  }

  public GameStartExceptionHolder (Utility.GameStartException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Utility.GameStartExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Utility.GameStartExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Utility.GameStartExceptionHelper.type ();
  }

}
