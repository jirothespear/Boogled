package CORBA_IDL.Utility;


/**
* Utility/playerLeaderboardPointsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Utility.idl
* Tuesday, May 21, 2024 9:07:47 AM CST
*/

public final class playerLeaderboardPointsHolder implements org.omg.CORBA.portable.Streamable
{
  public int value[] = null;

  public playerLeaderboardPointsHolder ()
  {
  }

  public playerLeaderboardPointsHolder (int[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = playerLeaderboardPointsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    playerLeaderboardPointsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return playerLeaderboardPointsHelper.type ();
  }

}