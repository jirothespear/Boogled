package Utility;

/**
* Utility/ServerUtilityHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Sunday, May 5, 2024 8:14:30 PM CST
*/

public final class ServerUtilityHolder implements org.omg.CORBA.portable.Streamable
{
  public Utility.ServerUtility value = null;

  public ServerUtilityHolder ()
  {
  }

  public ServerUtilityHolder (Utility.ServerUtility initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Utility.ServerUtilityHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Utility.ServerUtilityHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Utility.ServerUtilityHelper.type ();
  }

}
