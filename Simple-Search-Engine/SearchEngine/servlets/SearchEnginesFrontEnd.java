/*4.1 SearchEnginesFrontEnd.java
This servlet builds the form-based front end to the search engine servlet. At first blush, 
the output looks just like the page given by the static HTML page presented in the section
on HTTP status codes. Here, however, selected values are remembered in cookies (set by the
CustomizedSearchEngines servlet that this page sends data to), so if the user comes back to 
the same page at a later time (even after quitting the browser and restarting), the page is
initialized with the values from the previous search. 
You can also download the source or try it on-line. Note that code uses ServletUtilities.java,
for the getCookieValue method (shown above) and for headWithTitle for generating part of the HTML.
It also uses the LongLivedCookie class, shown above, for creating a Cookie that automatically has
a long-term expiration date. 


package hall;  */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

public class SearchEnginesFrontEnd extends HttpServlet 
{
  public void doGet(HttpServletRequest request,HttpServletResponse response)
											throws ServletException, IOException 
  {
    Cookie[] cookies = request.getCookies();
    String searchString = ServletUtilities.getCookieValue(cookies,"searchString","Java Programming");
    String numResults = ServletUtilities.getCookieValue(cookies,"numResults","10");
    String searchEngine = ServletUtilities.getCookieValue(cookies,"searchEngine","google");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Searching the Web";
    out.println(ServletUtilities.headWithTitle(title) + "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                                          "<H1 ALIGN=\"CENTER\">Searching the Web</H1>\n" +
                         "\n" + "<FORM ACTION=\"/servlet/hall.CustomizedSearchEngines\">\n" +
                "<CENTER>\n" + "Search String:\n" + "<INPUT TYPE=\"TEXT\" NAME=\"searchString\"\n" +
                "       VALUE=\"" + searchString + "\"><BR>\n" + "Results to Show Per Page:\n" +
                "<INPUT TYPE=\"TEXT\" NAME=\"numResults\"\n" + "       VALUE=" + numResults + " SIZE=3><BR>\n" +
                "<INPUT TYPE=\"RADIO\" NAME=\"searchEngine\"\n" +
                "       VALUE=\"google\"" + checked("google", searchEngine) + ">\n" +  "Google |\n" +
                "<INPUT TYPE=\"RADIO\" NAME=\"searchEngine\"\n" + "       VALUE=\"infoseek\"" +
                checked("infoseek", searchEngine) + ">\n" +  "Infoseek |\n" +
                "<INPUT TYPE=\"RADIO\" NAME=\"searchEngine\"\n" +"       VALUE=\"lycos\"" +
                checked("lycos", searchEngine) + ">\n" + "Lycos |\n" +
                "<INPUT TYPE=\"RADIO\" NAME=\"searchEngine\"\n" +  "       VALUE=\"hotbot\"" +
                checked("hotbot", searchEngine) + ">\n" +  "HotBot\n" + "<BR>\n" +
                "<INPUT TYPE=\"SUBMIT\" VALUE=\"Search\">\n" +  "</CENTER>\n" +  "</FORM>\n" + "\n" +
                "</BODY>\n" + "</HTML>\n");
  }

  private String checked(String name1, String name2) 
  {
    if (name1.equals(name2))
      return(" CHECKED");
    else
      return("");
  }
}
