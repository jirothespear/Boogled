package Utility;

/**
* Utility/PlayerUtilityHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Friday, May 10, 2024 12:17:25 PM CST
*/

public final class PlayerUtilityHolder implements org.omg.CORBA.portable.Streamable
{
  public Utility.PlayerUtility value = null;

  public PlayerUtilityHolder ()
  {
  }

  public PlayerUtilityHolder (Utility.PlayerUtility initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Utility.PlayerUtilityHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Utility.PlayerUtilityHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Utility.PlayerUtilityHelper.type ();
  }

}
