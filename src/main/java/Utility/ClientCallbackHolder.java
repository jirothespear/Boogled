package Utility;

/**
* Utility/ClientCallbackHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Sunday, May 5, 2024 4:57:07 PM CST
*/

public final class ClientCallbackHolder implements org.omg.CORBA.portable.Streamable
{
  public Utility.ClientCallback value = null;

  public ClientCallbackHolder ()
  {
  }

  public ClientCallbackHolder (Utility.ClientCallback initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Utility.ClientCallbackHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Utility.ClientCallbackHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Utility.ClientCallbackHelper.type ();
  }

}
