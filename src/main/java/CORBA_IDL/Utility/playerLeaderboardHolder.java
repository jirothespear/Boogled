package CORBA_IDL.Utility;


/**
* Utility/playerLeaderboardHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./Utility.idl
* Wednesday, May 22, 2024 11:19:37 AM SGT
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
    value = playerLeaderboardHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    playerLeaderboardHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return playerLeaderboardHelper.type ();
  }

}
