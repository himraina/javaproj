
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

public class SearchEngines extends HttpServlet 
{
  public void doGet(HttpServletRequest request,HttpServletResponse response)
                                               throws ServletException, IOException 
  {
    String searchString = URLEncoder.encode(request.getParameter("searchString"));
    String numResults = request.getParameter("numResults");
    String searchEngine = request.getParameter("searchEngine");
    SearchSpec[] commonSpecs = SearchSpec.getCommonSpecs();
    for(int i=0; i<commonSpecs.length; i++) 
	{
      SearchSpec searchSpec = commonSpecs[i];
      if (searchSpec.getName().equals(searchEngine)) 
	  {
        String url = response.encodeURL(SearchSpec.makeURL(searchString,numResults));
        response.sendRedirect(url);
        return;
      }
    }
    response.sendError(response.SC_NOT_FOUND, "No recognized search engine specified.");
  }

  public void doPost(HttpServletRequest request,HttpServletResponse response)
                                                  throws ServletException, IOException 
  {
    doGet(request, response);
  }
}
