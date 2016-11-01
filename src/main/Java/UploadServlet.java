// Import required java libraries

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {

    private boolean isMultipart;
    private int maxFileSize = 500 * 1024;
    private int maxMemSize = 500 * 1024;
    private File file;


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text");
        java.io.PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.println("Need multipart transfer type! File not uploaded!");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        File tmp = new File("temp");

        if (tmp.exists()) {
            tmp.delete();
            tmp.mkdir();
        } else {
            tmp.mkdir();
        }
        factory.setRepository(tmp);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            out.println("UPLOAD");

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();

                    // Write the file
                    file = new File(fileName);
                    System.out.println(file.getAbsolutePath());
                    fi.write(file);
                    out.println("Uploaded file: " + fileName);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name", "File upload sample. Based on Servlet and AJAX.");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}