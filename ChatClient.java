import java.net.*;
import java.io.*;
import java.util.*;

class ChatClient
{
	public static void main(String[] args)
	{
		try
		{
			MulticastSocket socket = new MulticastSocket(40202);
			InetAddress address = InetAddress.getByName("239.0.202.1");
			//socket.joinGroup(address);
			
			if(args.length != 0)
			{
				System.err.println("This is a chat client, no arguments need to be specified.");
			}
			
			ChatClient chat = new ChatClient();
			ChatClientWorker worker = new ChatClientWorker();
			worker.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
			while(true)
			{
				String s = reader.readLine();
				byte[] line = s.getBytes();
				DatagramPacket p = new DatagramPacket(line, line.length, address, 40202);
				while(true)
{
				socket.send(p);
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
}

class ChatClientWorker extends Thread
{
	public void run()
	{
		try{
			MulticastSocket socket = new MulticastSocket(40202);
			InetAddress address = InetAddress.getByName("239.0.202.1");
			socket.joinGroup(address);
		
			while(true)
			{
				byte[] c = new byte[1024];
				DatagramPacket p = new DatagramPacket(c, 1024);
				socket.receive(p);
				String str = new String(p.getData(), 0, p.getData().length);
				System.out.println(p.getAddress().getHostAddress() + ": " + str);
			}
		}
		catch(Exception e)
		{
			System.err.println("Error " + e.getMessage());
		}
	}
}
