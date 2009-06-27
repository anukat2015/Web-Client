/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package frontend.view.datasource;

import dk.semantic_web.diy.http.HttpClient;
import dk.semantic_web.diy.http.HttpResponse;
import frontend.controller.resource.datasource.DataSourceResource;
import frontend.view.FrontEndView;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Pumba
 */
public class DataSourceView extends FrontEndView
{

        public DataSourceView(DataSourceResource resource)
    {
	super(resource);
    }

    @Override
    public void display(HttpServletRequest request, HttpServletResponse response) throws IOException, TransformerException, ParserConfigurationException
    {
	setStyleSheet(new File(getServlet().getServletConfig().getServletContext().getRealPath("/xslt/sparql2google-wire.xsl")));

	String resultUrlString = request.getParameter("result-url");
	if (resultUrlString != null)
	{
	    URL resultUrl = new URL(resultUrlString);

	    dk.semantic_web.diy.http.HttpRequest remoteRequest = new dk.semantic_web.diy.http.HttpRequest();
	    remoteRequest.setMethod("get");
	    remoteRequest.setServerName(resultUrl.getHost());
	    remoteRequest.setPathInfo(resultUrl.getPath());
	    remoteRequest.setPathInfo(resultUrl.getPath());	
	    //remoteRequest.setParameter("center", lat + "," + lng);
	    //remoteRequest.setParameter("span", spanHeight + "," + spanWidth);
	    HttpResponse remoteResponse = HttpClient.send(remoteRequest);
	    //InputStreamReader reader = new InputStreamReader(;
	    InputStream stream = remoteResponse.getInputStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) sb.append(line + "\n");
	    stream.close();
	    String responseString = sb.toString();

	    setDocument(responseString);

	    //getTransformer().setParameter("query-string", queryString);
	    //getResolver().setArgument("result", result);

	    super.display(request, response);
	
	    response.setStatus(HttpServletResponse.SC_OK);
	}
	else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
