import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class RestClient {
    public static void main(String[] args) {
        try {

            while(true){

                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpPost postRequest = new HttpPost(
                        "http://localhost:8080/band");

                ArrayList<NameValuePair> postParameters;

                postParameters = new ArrayList<NameValuePair>();
                Random rand = new Random();

                Integer  step = rand.nextInt(50) + 1;
                Integer bpms = rand.nextInt(39)+52;
                Integer distances = rand.nextInt(4)+1;
                Float latitude = rand.nextFloat()*7+3;
                Float longitude = rand.nextFloat()*10+5;
                Integer calories =rand.nextInt(20)+5;
                Date fecha = getFechaEntreAyerYHoy();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(fecha);
                String fecha_registro = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" +
                                        calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                                        calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

                postParameters.add(new BasicNameValuePair("steps",step.toString()));
                postParameters.add(new BasicNameValuePair("bpm", bpms.toString()));
                postParameters.add(new BasicNameValuePair("distance", distances.toString()));
                postParameters.add(new BasicNameValuePair("latitude", latitude.toString()));
                postParameters.add(new BasicNameValuePair("longitude", longitude.toString()));
                postParameters.add(new BasicNameValuePair("calories", calories.toString()));
                postParameters.add(new BasicNameValuePair("fecha_registro", fecha_registro));
                postParameters.add(new BasicNameValuePair("user", "2"));

                postRequest.setEntity(new UrlEncodedFormEntity(postParameters));
                postRequest.addHeader("accept", "application/json");

                HttpResponse response = httpClient.execute(postRequest);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + response.getStatusLine().getStatusCode());
                }

                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

                String output;
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                httpClient.getConnectionManager().shutdown();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }




        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    public static Date getFechaEntreAyerYHoy() {
        Date resp;
        Calendar ayer = Calendar.getInstance();
        ayer.setTime(new Date());
        ayer.add(Calendar.DAY_OF_YEAR, -1);

        int numeroAleatorio = (int) (Math.random()*100+1);

        if (numeroAleatorio % 2 == 0) {
            resp = new Date();
        } else {
            resp = ayer.getTime();
        }

        double h = Math.random()*23+1;
        double m = Math.random()*59+1;
        double s = Math.random()*59+1;

        resp.setHours((int)h);
        resp.setMinutes((int)m);
        resp.setSeconds((int)s);

        System.out.println(resp);

        return resp;

    }
}