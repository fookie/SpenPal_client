package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import spenpal.DatabaseManager;
import spenpal.SpenPalMain;

public class ServerConnector {
	SSLSocket socket;
	// public String serverAddr = "128.199.130.45";
	public String serverAddr = "127.0.0.1";
	public int serverPort = 12313;

	/**
	 * Used to establish a connection with server
	 * 
	 * @param host
	 * @param port
	 * @throws Exception
	 */
	public ServerConnector(String ipaddr, int port) throws Exception {

		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) getSSLContext().getSocketFactory();

		socket = (SSLSocket) sslsocketfactory.createSocket();
		socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
		socket.setUseClientMode(true);
		socket.connect(new InetSocketAddress(ipaddr, port), 0);
		socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {

			@Override
			public void handshakeCompleted(HandshakeCompletedEvent arg0) {
				System.out.println("Handshake completed");
			}
		});

	}

	/**
	 * Setup SSLContext Reference:
	 * http://blog.csdn.net/a19881029/article/details/11742361
	 * 
	 * @return
	 * @throws Exception
	 */
	public static SSLContext getSSLContext() throws Exception {
		String protocol = "TLSV1";
		String clientTrustCerFile = "./certificate/clientTrust.jks";
		String clientTrustCerPwd = "storespanpal";

		// Trust Key Store
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(new FileInputStream(clientTrustCerFile), clientTrustCerPwd.toCharArray());

		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
		trustManagerFactory.init(keyStore);
		TrustManager[] tms = trustManagerFactory.getTrustManagers();

		KeyManager[] kms = null;
		SSLContext sslContext = SSLContext.getInstance(protocol);
		sslContext.init(kms, tms, null);

		return sslContext;
	}

	/**
	 * Disconnect from current server
	 */
	public boolean disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Register on server
	 *
	 * @param name
	 *            Username
	 * @param passwd
	 *            Password
	 * @return If success
	 */
	public boolean register(String name, String passwd) {
		try {
			if (socket.isConnected()) {
				DataOutputStream info = new DataOutputStream(socket.getOutputStream());
				DataInputStream data_in = new DataInputStream(socket.getInputStream());
				String request = "100 " + name + " " + passwd;
				info.writeUTF(request);
				info.flush();
				String received = data_in.readUTF();
				if (received.compareTo("success") == 0) {
					return true;
				}else
				{
					return false;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean save_query_toserver(String query) {
		try {
			if (socket.isConnected()) {
				DataOutputStream info = new DataOutputStream(socket.getOutputStream());
				DataInputStream data_in = new DataInputStream(socket.getInputStream());
				String request = "110 " + SpenPalMain.nowusr;
				info.writeUTF(request);
				info.writeUTF(query);
				info.flush();
				String time = data_in.readUTF();
				DatabaseManager.updatetime(time);
				// info.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean send_data_toserver(String datastring) {
		try {
			if (socket.isConnected()) {
				DataOutputStream info = new DataOutputStream(socket.getOutputStream());
				DataInputStream data_in = new DataInputStream(socket.getInputStream());
				String request = "110 " + SpenPalMain.nowusr + " " + datastring;
				info.writeUTF(request);
				// info.writeUTF(query);
				info.flush();
				String time = data_in.readUTF();
				DatabaseManager.updatetime(time);
				// info.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Register on server
	 *
	 * @param name
	 *            Username
	 * @param passwd
	 *            Password
	 * @return If success
	 */
	public boolean login(String name, String passwd) {
		try {
			if (socket.isConnected()) {
				DataOutputStream info = new DataOutputStream(socket.getOutputStream());
				DataInputStream data_in = new DataInputStream(socket.getInputStream());
				// String request = "101 " + name + " " + passwd;
				info.writeUTF("101 " + name + " " + passwd);
				info.flush();
				// info.close();
				System.out.println("try to login...");
				String received = data_in.readUTF();
				if (received.compareTo("success") == 0) {
					return true;
				}else
				{
					return false;
				}

			}
		} catch (IOException e) {
			System.out.println("login error");
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 *
	 * @param friendUsername
	 * @return
	 */
	public boolean addFriend(String friendUsername) {
		return true;
	}

	/**
	 * Send files to server
	 * 
	 * @param f
	 *            file to be sent
	 */
	public boolean sendFile(File f) {
		try {
			if (socket.isConnected()) {
				DataInputStream data = new DataInputStream(new FileInputStream(f));
				DataOutputStream send = new DataOutputStream(socket.getOutputStream());
				byte[] b = new byte[1024];
				while (data.available() > 0) {
					data.readFully(b, 0, b.length);
					send.write(b);
				}
				send.flush();
				data.close();
				send.close();
			} else {
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void update() {
		try {
			if (socket.isConnected()) {
				DataOutputStream info = new DataOutputStream(socket.getOutputStream());
				DataInputStream data_in = new DataInputStream(socket.getInputStream());
				// String request = "101 " + name + " " + passwd;
				// String time = DatabaseManager.gettime();
				info.writeUTF("111 " + SpenPalMain.nowusr + " " + "0");
				info.flush();
				System.out.println("things:" + data_in.readUTF());
				String rec = "delete from " + SpenPalMain.nowusr;
				while (rec.compareTo("end") != 0) {
					DatabaseManager.executeQuery(rec);
					rec = data_in.readUTF();

				}
				String received = data_in.readUTF();
				DatabaseManager.updatetime(received);
				SpenPalMain.mainframe.refresh();

			}
		} catch (IOException e) {
			System.out.println("login error");
			e.printStackTrace();
		}
	}

	public boolean isconnected() {
		return socket.isConnected();

	}

	public void editdata(int code,String id, String date) {

		String tosend = code+" " + SpenPalMain.nowusr + " " + id + " " + date;
		try {
			if (socket.isConnected()) {
				DataOutputStream info = new DataOutputStream(socket.getOutputStream());
				info.writeUTF(tosend);
			}
		} catch (IOException e) {

		}
	}

	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	//
	// }

}
