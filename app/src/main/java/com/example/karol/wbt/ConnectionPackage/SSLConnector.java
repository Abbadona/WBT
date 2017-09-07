package com.example.karol.wbt.ConnectionPackage;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLSocket;

public class SSLConnector {

    private static SSLConnector instance = null;
    public static SSLSocket sslsocket ;
    private InputStream keyin;
    private KeyStore ks;
    private char[] keystorepass = "dzikidzik".toCharArray();
    private String IP_NUMBER = "188.116.11.90";

    protected SSLConnector(InputStream keyin) {

        try {
            LoadKey(keyin);

            org.apache.http.conn.ssl.SSLSocketFactory socketFactory = new org.apache.http.conn.ssl.SSLSocketFactory(ks);

            socketFactory.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            sslsocket = (SSLSocket)
                    socketFactory.createSocket(new Socket(IP_NUMBER,7632), IP_NUMBER, 7632, false);

            sslsocket.startHandshake();

        } catch (KeyStoreException e) {
            Log.d("TAG_SSL_ERROR", "KeyStore");
            e.printStackTrace();
        } catch (CertificateException e) {
            Log.d("TAG_SSL_ERROR", "CertificateException");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Log.d("TAG_SSL_ERROR", "NoSuchAlgorithmException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("TAG_SSL_ERROR", "IOException - startHandShake");
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            Log.d("TAG_SSL_ERROR", "UnrecoverableKeyException");
            e.printStackTrace();
        } catch (KeyManagementException e) {
            Log.d("TAG_SSL_ERROR", "KeyManagementException");
            e.printStackTrace();
        }

    }
    private void LoadKey(InputStream keyin) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {

        this.keyin = keyin;
        ks = KeyStore.getInstance("PKCS12");
        ks.load(keyin,keystorepass);

    }
    public void sendMessageToServer(String messageToSend) throws IOException {
        Log.d("TAG_SendToServer",messageToSend);
        PrintWriter pw = new PrintWriter(sslsocket.getOutputStream());
        pw.write(messageToSend);
        pw.write("\n");
        pw.flush();
    }
    public String getMessageFromServer() throws IOException {
        String messageToSend = new BufferedReader(new InputStreamReader(sslsocket.getInputStream())).readLine();
        Log.d("TAG_MessageFromServer",messageToSend);
        return messageToSend;
    }
    public static SSLConnector getInstance(InputStream keyin)  {
        synchronized(SSLConnector.class){
            if(instance == null) {
                instance = new SSLConnector(keyin);
                Log.d("TAG_SSLCONNECTIO", "CreateNewSSLConn");
            }
            return instance;
        }
    }//koniec getInstance()

}//koniec klasy SSLConnector
