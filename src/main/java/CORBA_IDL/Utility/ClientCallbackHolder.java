package CORBA_IDL.Utility;

/**
* Utility/ClientCallbackHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Wednesday, May 22, 2024 10:35:26 PM CST
*/

public final class ClientCallbackHolder implements org.omg.CORBA.portable.Streamable
{
  public ClientCallback value = null;

  public ClientCallbackHolder ()
  {
  }

  public ClientCallbackHolder (ClientCallback initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ClientCallbackHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ClientCallbackHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ClientCallbackHelper.type ();
  }

}
