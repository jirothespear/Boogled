package Utility;

/**
* Utility/LoginExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Thursday, May 9, 2024 12:55:53 PM CST
*/

public final class LoginExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public Utility.LoginException value = null;

  public LoginExceptionHolder ()
  {
  }

  public LoginExceptionHolder (Utility.LoginException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Utility.LoginExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Utility.LoginExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Utility.LoginExceptionHelper.type ();
  }

}
