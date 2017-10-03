package com.rest.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.rest.request.UserRequest;
import com.rest.service.MUserService;
@Service
public class MUserServiceImpl implements MUserService{

	@Override
	public String registerUser(UserRequest req) throws UnsupportedEncodingException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://52.90.29.67/index.php/customer/account/createpost/");
		// Request parameters and other properties. 
		String fileName = "C:\\Backup\\test\\kalyani.txt";
		FileBody bin = new FileBody(new File(fileName));
		StringBody comment = new StringBody("Filename: " + fileName);
		
		/*List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("success_url", ""));
		params.add(new BasicNameValuePair("error_url", ""));
		params.add(new BasicNameValuePair("form_key", "x9wX3weUMda6UFdl"));
		
		params.add(new BasicNameValuePair("firstname", req.getFirstName()));
		params.add(new BasicNameValuePair("middlename", req.getMiddleName()));
		params.add(new BasicNameValuePair("lastname", req.getLastName()));
		params.add(new BasicNameValuePair("email", req.getEmail()));
		params.add(new BasicNameValuePair("password", req.getPassword()));
		params.add(new BasicNameValuePair("confirmation", req.getConfirmPassword()));
		InputStream instream=null;*/
		String result = "Fail";
		try {
			
			MultipartEntity reqEntity = new MultipartEntity();
			//reqEntity.addPart("bin", bin);
			//reqEntity.addPart("comment", comment);
			
			reqEntity.addPart("success_url", new StringBody(""));
			reqEntity.addPart("error_url", new StringBody(""));
			reqEntity.addPart("form_key", new StringBody("x9wX3weUMda6UFdl"));
			reqEntity.addPart("firstname", new StringBody(req.getFirstName()));
			reqEntity.addPart("middlename", new StringBody(req.getMiddleName()));
			reqEntity.addPart("lastname", new StringBody(req.getLastName()));
			reqEntity.addPart("email", new StringBody(req.getEmail()));
			reqEntity.addPart("password", new StringBody(req.getPassword()));
			reqEntity.addPart("password", new StringBody(req.getConfirmPassword()));
			
			httppost.setEntity(reqEntity);
			 System.out.println("Requesting : " + httppost.getRequestLine());
			//httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			httppost.setHeader("Content-type", "multipart/form-data");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
            CloseableHttpResponse responseBody = httpclient.execute(httppost);
            System.out.println("responseBody : " + responseBody);
			
			//HttpResponse response = httpclient.execute(httppost);
			//HttpEntity entity = response.getEntity();
			//System.out.println(entity.getContent());
			//System.out.println(entity.getContentType());
			/*if (entity != null) {
			    instream = entity.getContent();
			    result=!instream.equals(null)?"Sucess":"Fail";
			}*/
			/*if (entity != null) {
		       // String content =  EntityUtils.toString(entity);
				BufferedReader reader = new BufferedReader(new  InputStreamReader(entity.getContent()), 2048);

				if (entity != null) {
				    StringBuilder sb = new StringBuilder();
				    String line;
				    while ((line = reader.readLine()) != null) {
				        System.out.println(" line : " + line);
				        sb.append(line);
				    }
				    String getResponseString = "";
				    getResponseString = sb.toString();
				    System.out.println(" res code : " + response.getStatusLine().getStatusCode());
				//use server output getResponseString as string value.
				}
		    }*/
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
	    }
		return result;
	}

}

