package Utility;


/**
* Utility/playerLeaderboardHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Wednesday, May 15, 2024 9:43:22 AM CST
*/

public final class playerLeaderboardHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public playerLeaderboardHolder ()
  {
  }

  public playerLeaderboardHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Utility.playerLeaderboardHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Utility.playerLeaderboardHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Utility.playerLeaderboardHelper.type ();
  }

}
