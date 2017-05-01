package com.example.karol.wbt.ConnectionPackage;

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
                    socketFactory.createSocket(new Socket("185.157.80.59",7632), "185.157.80.59", 7632, false);

            sslsocket.startHandshake();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
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
            }
            return instance;
        }
    }//koniec getInstance()


}//koniec klasy SSLConnector
