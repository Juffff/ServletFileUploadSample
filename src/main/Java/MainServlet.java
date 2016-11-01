import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet("")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("name", "File upload sample. Based on Servlet and AJAX.");
        req.getRequestDispatcher("index.jsp").forward(req,resp);


    }
/*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream is = req.getInputStream();
        File f = new File("image.jpg");
        if(!f.exists()){f.createNewFile();}
        System.out.println(f.getAbsolutePath());
        FileOutputStream fileOutputStream = new FileOutputStream(f);

        while (is.available()>0){

            int a = is.read();
            System.out.printf(String.valueOf(a));

            fileOutputStream.write(a);
        }
        fileOutputStream.close();
        is.close();
    }*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("READY!");
        File f = new File("image.jpg");
        Part file = request.getPart("file");
        System.out.println(file.getName());
        String filename = getFilename(file);
        InputStream filecontent = file.getInputStream();

        if(!f.exists()){f.createNewFile();}
        System.out.println(f.getAbsolutePath());
        FileOutputStream fileOutputStream = new FileOutputStream(f);

        while (filecontent.available()>0){

            int a = filecontent.read();
            System.out.printf(String.valueOf(a));

            fileOutputStream.write(a);
        }
        fileOutputStream.close();
        filecontent.close();

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("File " + filename + " successfully uploaded");
    }


    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}
