package com.example.karol.wbt.ConnectionPackage;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
import javax.net.ssl.SSLSocketFactory;

public class SSLConnector {

    private static SSLConnector instance = null;
    public static SSLSocket sslsocket ;
    private InputStream keyin;
    private KeyStore ks;
    private char[] keystorepass = "dzikidzik".toCharArray();

    protected SSLConnector(InputStream keyin) {

        try {
            LoadKey(keyin);

            org.apache.http.conn.ssl.SSLSocketFactory socketFactory = new org.apache.http.conn.ssl.SSLSocketFactory(ks);

            socketFactory.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            sslsocket = (SSLSocket)
                    socketFactory.createSocket(new Socket("188.116.11.90",7632), "188.116.11.90", 7632, false);

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
    public void closeConnection() throws IOException {
        sslsocket.close();
        instance = null;
    }
    private void LoadKey(InputStream keyin) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {

        this.keyin = keyin;
        ks = KeyStore.getInstance("PKCS12");
        ks.load(keyin,keystorepass);

    }
    public void sendMessageToServer(String messageToSend) throws IOException {
        if (sslsocket == null){
            Log.d("TAG_SSLCONNECTION", "sslSocket się zesrał");
        }
        PrintWriter pw = new PrintWriter(sslsocket.getOutputStream());
        pw.write(messageToSend);
        pw.write("\n");
        pw.flush();
    }
    public JSONObject getMessageFromServer() throws JSONException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
        String serverAnswer = br.readLine();
        return new JSONObject(serverAnswer);
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
